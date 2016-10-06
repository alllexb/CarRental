package ua.kiev.allexb.carrental.controller;

import org.apache.log4j.Logger;
import ua.kiev.allexb.carrental.model.Administrator;
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
 * @version 1.0 13.08.2016
 */
@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    static final Logger logger = Logger.getLogger(LoginServlet.class);

    private static final long serialVersionUID = 7410978571600567753L;

    public LoginServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Request to page: " + request.getServletPath());
        Administrator loginedAdministrator = StoreAndCookieUtil.getLoginedAdministrator(request.getSession());
        RequestDispatcher dispatcher;
        if (loginedAdministrator != null) {
            request.setAttribute("errorString", "You need to logout before changing administrator account.");
            dispatcher = request.getRequestDispatcher("/WEB-INF/views/adminInfoView.jsp");
        } else {
            dispatcher = request.getRequestDispatcher("/WEB-INF/views/loginView.jsp");
        }
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
