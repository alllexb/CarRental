package ua.kiev.allexb.carrental.controller;

import org.apache.log4j.Logger;
import ua.kiev.allexb.carrental.data.dao.ClientDAO;
import ua.kiev.allexb.carrental.data.dao.ClientDAOImpl;
import ua.kiev.allexb.carrental.data.domain.ClientDomain;
import ua.kiev.allexb.carrental.model.Administrator;
import ua.kiev.allexb.carrental.model.Client;
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
@WebServlet(urlPatterns = {"/clients"})
public class ClientServlet extends HttpServlet {
    private static final long serialVersionUID = -5098121881329935823L;
    static final Logger logger = ApplicationLogger.getLogger(ClientServlet.class);

    public ClientServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Administrator loginedAdministrator = StoreAndCookieUtil.getLoginedAdministrator(request.getSession());
        if (loginedAdministrator != null) {
            logger.info("Extracting of clients list from database.");
            List<Client> clients = null;
            Connection connection = StoreAndCookieUtil.getStoredConnection(request);
            try {
                ClientDAO clientDAO = new ClientDAOImpl(connection);
                clients = clientDAO.getAll().stream().map(ClientDomain::getClient).collect(Collectors.toList());
                logger.info("Clients list extracted.");
            } catch (SQLException ex) {
                logger.warn("Data base exception.", ex);
                request.setAttribute("errorString", ex.getMessage());
            }
            request.setAttribute("clients_list", clients);
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/clientsView.jsp");
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
