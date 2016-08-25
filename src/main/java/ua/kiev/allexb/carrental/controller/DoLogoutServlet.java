package ua.kiev.allexb.carrental.controller;

import org.apache.log4j.Logger;
import ua.kiev.allexb.carrental.utils.ApplicationLogger;
import ua.kiev.allexb.carrental.utils.StoreAndCookieUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author allexb
 * @version 1.0 19.08.2016
 */
@WebServlet(urlPatterns = {"/logout"})
public class DoLogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 8244392699971640663L;

    static final Logger logger = ApplicationLogger.getLogger(DoLogoutServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Started logout procedure.");
        if (StoreAndCookieUtil.getLoginedAdministrator(request.getSession()) != null) {
            StoreAndCookieUtil.deleteAdminCookie(response);
            StoreAndCookieUtil.storeLoginedAdministrator(request.getSession(), null);
            logger.info("Administrator logouted.");
            response.sendRedirect(request.getHeader("Referer"));
        } else {
            logger.info("No logined administrator. Redirect to login page.");
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/loginView.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
