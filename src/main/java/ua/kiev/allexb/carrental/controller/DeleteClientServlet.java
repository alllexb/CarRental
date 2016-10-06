package ua.kiev.allexb.carrental.controller;

import org.apache.log4j.Logger;
import ua.kiev.allexb.carrental.data.dao.ClientDAO;
import ua.kiev.allexb.carrental.data.dao.DAOFactory;
import ua.kiev.allexb.carrental.data.domain.ClientDomain;
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

/**
 * @author allexb
 * @version 1.0 19.09.2016
 */
@WebServlet(urlPatterns = {"/client_list/delete"})
public class DeleteClientServlet extends HttpServlet {

    static final Logger logger = Logger.getLogger(DeleteClientServlet.class);

    private static final long serialVersionUID = 5671024213850205487L;

    public DeleteClientServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Client deleting form.");
        if (request.getParameter("id") == null) {
            response.sendRedirect(request.getContextPath() + "/client_list");
            return;
        }
        long id = Long.valueOf(request.getParameter("id"));
        Connection connection = StoreAndCookieUtil.getStoredConnection(request);
        try {
//            ClientDAO clientDAO = new ClientDAOImpl(connection);
            DAOFactory daoFactory = StoreAndCookieUtil.getStoredDAOFactory(request.getSession());
            ClientDAO clientDAO = daoFactory.getClientDao(connection);
            ClientDomain client = clientDAO.getById(id);
            if (client != null) {
                logger.info("Client data entered correctly.");
                request.setAttribute("client", client.getClient());
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/deleteClientView.jsp");
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("errorString", "Client with ID: #" + id + " does not exists.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/client_list");
                dispatcher.forward(request, response);
            }
        } catch (SQLException ex) {
            logger.warn("Data base exception.", ex);
            request.setAttribute("javax.servlet.error.exception", ex);
            request.setAttribute("javax.servlet.error.status_code", 500);
            response.setStatus(500);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Client deletion procedure started.");
        if (request.getParameter("id") == null) {
            response.sendRedirect(request.getContextPath() + "/client_list");
            return;
        }
        long id = Long.valueOf(request.getParameter("id"));
        Connection connection = StoreAndCookieUtil.getStoredConnection(request);
        try {
//            ClientDAO clientDAO = new ClientDAOImpl(connection);
            DAOFactory daoFactory = StoreAndCookieUtil.getStoredDAOFactory(request.getSession());
            ClientDAO clientDAO = daoFactory.getClientDao(connection);
            ClientDomain controlClient = clientDAO.getById(id);
            if (controlClient != null) {
                clientDAO.remove(controlClient);
                logger.info("Client deleted.");
            } else {
                String errorString = "Client with with ID: #" + id + " doesn't exist.";
                request.setAttribute("errorString", errorString);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/client_list");
                dispatcher.forward(request, response);
            }
        } catch (SQLException ex) {
            logger.warn("Data base exception.", ex);
            request.setAttribute("javax.servlet.error.exception", ex);
            request.setAttribute("javax.servlet.error.status_code", 500);
            response.setStatus(500);
        }
        response.sendRedirect(request.getContextPath() + "/client_list");
    }
}
