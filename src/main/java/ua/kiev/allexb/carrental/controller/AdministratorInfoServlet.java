package ua.kiev.allexb.carrental.controller;

import org.apache.log4j.Logger;
import ua.kiev.allexb.carrental.model.Administrator;
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

/**
 * @author allexb
 * @version 1.0 13.08.2016
 */
@WebServlet(urlPatterns = { "/adminInfo" })
public class AdministratorInfoServlet extends HttpServlet {
    private static final long serialVersionUID = -2331263390335838695L;
    static final Logger logger = ApplicationLogger.getLogger(AdministratorInfoServlet.class);
    public AdministratorInfoServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        logger.info("Getting logined administrator.");
        // Check User has logged on
        Administrator loginedAdministrator = StoreAndCookieUtil.getLoginedAdministrator(session);

        // Not logged in
        if (loginedAdministrator == null) {
            logger.info("No logined administrator.");
            // Redirect to login page.
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Store info in request attribute
        request.setAttribute("admin", loginedAdministrator);

        logger.info("Redirect to administrator view page.");
        // Logined, forward to /WEB-INF/views/adminInfoView.jsp
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/adminInfoView.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
