package ua.kiev.allexb.carrental.controller;

import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author allexb
 * @version 1.0 11.08.2016
 */
@WebServlet(urlPatterns = {"/home", "", "/index.html", "/index.htm", "/index.jsp"})
public class HomeServlet extends HttpServlet {

    static final Logger logger = Logger.getLogger(HomeServlet.class);

    private static final long serialVersionUID = 2688609755631823746L;

    public HomeServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Request to page: " + request.getServletPath());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/homeView.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

}
