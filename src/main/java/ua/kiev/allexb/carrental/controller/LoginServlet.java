package ua.kiev.allexb.carrental.controller;

import org.apache.log4j.Logger;
import ua.kiev.allexb.carrental.utils.ApplicationLogger;

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
    private static final long serialVersionUID = 7410978571600567753L;

    static final Logger logger = ApplicationLogger.getLogger(LoginServlet.class);

    public LoginServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Request to page: " + request.getServletPath());
        // Forward to /WEB-INF/views/loginView.jsp
        // (Users can not access directly into JSP pages placed in WEB-INF)
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/loginView.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
