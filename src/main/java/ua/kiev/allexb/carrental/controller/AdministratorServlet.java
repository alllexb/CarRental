package ua.kiev.allexb.carrental.controller;

import org.apache.log4j.Logger;
import ua.kiev.allexb.carrental.data.dao.AdministratorDAO;
import ua.kiev.allexb.carrental.data.dao.AdministratorDAOImpl;
import ua.kiev.allexb.carrental.data.domain.AdministratorDomain;
import ua.kiev.allexb.carrental.model.Administrator;
import ua.kiev.allexb.carrental.utils.ApplicationLogger;
import ua.kiev.allexb.carrental.utils.StoreAndCookieUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author allexb
 * @version 1.0 11.08.2016
 */
@WebServlet(urlPatterns = {"/admins"})
public class AdministratorServlet extends HttpServlet {
    private static final long serialVersionUID = -928176549145443440L;
    static final Logger logger = ApplicationLogger.getLogger(AdministratorServlet.class);

    public AdministratorServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Administrator loginedAdministrator = StoreAndCookieUtil.getLoginedAdministrator(request.getSession());
        if (loginedAdministrator != null) {
            logger.info("Extracting of administrators list from database.");
            List<Administrator> administrators = null;
            Connection connection = StoreAndCookieUtil.getStoredConnection(request);
            try {
                AdministratorDAO administratorDAO = new AdministratorDAOImpl(connection);
                administrators = administratorDAO.getAll().stream().map(AdministratorDomain::getAdministrator).collect(Collectors.toList());
                logger.info("Administrators list extracted.");
            } catch (SQLException ex) {
                logger.warn("Data base exception.", ex);
                request.setAttribute("errorString", ex.getMessage());
            }
            request.setAttribute("admins_list", administrators);
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/adminsView.jsp");
            dispatcher.forward(request, response);
        } else {
            logger.info("Request without authentication. Access denied.");
            request.setAttribute("errorString", "Access denied! Login previously.");
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/loginView.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

