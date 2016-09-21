package ua.kiev.allexb.carrental.controller;

import org.apache.log4j.Logger;
import ua.kiev.allexb.carrental.data.dao.AdministratorDAO;
import ua.kiev.allexb.carrental.data.dao.AdministratorDAOImpl;
import ua.kiev.allexb.carrental.data.domain.AdministratorDomain;
import ua.kiev.allexb.carrental.model.Administrator;
import ua.kiev.allexb.carrental.model.helpers.PasswordHelper;
import ua.kiev.allexb.carrental.utils.ApplicationLogger;
import ua.kiev.allexb.carrental.utils.StoreAndCookieUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author allexb
 * @version 1.0 13.08.2016
 */
@WebServlet(urlPatterns = {"/doLogin"})
public class DoLoginServlet extends HttpServlet {
    private static final long serialVersionUID = -8084478976908814257L;

    static final Logger logger = ApplicationLogger.getLogger(DoLoginServlet.class);

    public DoLoginServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        logger.info("Started login procedure.");

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String rememberMeStr = request.getParameter("rememberMe");
        boolean remember = "Y".equals(rememberMeStr);

        Administrator administrator = null;
        boolean hasError = false;
        String errorString = null;

        if (login == null || password == null
                || login.length() == 0 || password.length() == 0) {
            // If user try to call "/doLogin" servlet directly, redirection to "/home"
            String header = request.getHeader("Referer");
            if (header == null || header.endsWith("/doLogin")) {
                response.sendRedirect(request.getContextPath() + "/home");
                return;
            } else {
                hasError = true;
                errorString = "Required login and password!";
            }
        } else {
            Connection connection = StoreAndCookieUtil.getStoredConnection(request);
            try {
                AdministratorDAO administratorDAO = new AdministratorDAOImpl(connection);
                AdministratorDomain domain = administratorDAO.getByLoginAndPassword(login, PasswordHelper.getSecurePassword(password));
                administrator = (domain == null) ? null : domain.getAdministrator();
                if (administrator == null) {
                    hasError = true;
                    errorString = "Login or password invalid";
                    logger.info(errorString);
                }
            } catch (SQLException ex) {
                logger.warn("Data base exception.", ex);
                hasError = true;
                errorString = "Sorry... Data base does not respond.";
                request.setAttribute("javax.servlet.error.exception", ex);
                request.setAttribute("javax.servlet.error.status_code", 500);
                response.setStatus(500);
            }
        }

        // If error, forward to /WEB-INF/views/loginView.jsp
        if (hasError) {
            administrator = new Administrator();
            administrator.setLogin(login);
            administrator.setPassword(password);

            // Store information in request attribute, before forward.
            request.setAttribute("errorString", errorString);
            request.setAttribute("admin", administrator);

            // Forward to /WEB-INF/views/loginView.jsp
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/loginView.jsp");
            dispatcher.forward(request, response);
        }

        // If no error store user information in Session snd redirect to last visited page.
        else {
            HttpSession session = request.getSession();
            StoreAndCookieUtil.storeLoginedAdministrator(session, administrator);

            // If user checked "Remember me".
            if (remember) {
                logger.info("Administrator has remembered.");
                StoreAndCookieUtil.storeAdminCookie(response, administrator);
            }

            // Else delete cookie.
            else {
                logger.info("Administrator has not remembered.");
                StoreAndCookieUtil.deleteAdminCookie(response);
            }

            // Redirect to last visited page or home.
            logger.info("Administrator logined correctly.");
            String header = request.getHeader("Referer");
            if (header.endsWith("/login") || header.endsWith("/logout") || header.endsWith("/doLogin")) {
                response.sendRedirect(request.getContextPath() + "/home");
            } else {
                response.sendRedirect(header);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
