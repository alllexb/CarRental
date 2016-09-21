package ua.kiev.allexb.carrental.controller;

import org.apache.log4j.Logger;
import ua.kiev.allexb.carrental.controller.validator.ClientValidator;
import ua.kiev.allexb.carrental.controller.validator.Validator;
import ua.kiev.allexb.carrental.data.dao.ClientDAO;
import ua.kiev.allexb.carrental.data.dao.ClientDAOImpl;
import ua.kiev.allexb.carrental.data.domain.ClientDomain;
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

/**
 * @author allexb
 * @version 1.0 19.09.2016
 */
@WebServlet(urlPatterns = {"/client_list/edit"})
public class EditClientServlet extends HttpServlet{
    static final Logger logger = ApplicationLogger.getLogger(EditClientServlet.class);

    private static final long serialVersionUID = 1677082379241851943L;

    public EditClientServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Client editing form.");
        if (request.getParameter("id") == null || request.getParameter("id").isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/client_list");
            return;
        }
        long id = Long.valueOf(request.getParameter("id"));
        Connection connection = StoreAndCookieUtil.getStoredConnection(request);
        try {
            ClientDAO clientDAO = new ClientDAOImpl(connection);
            ClientDomain client = clientDAO.getById(id);
            if (client != null) {
                logger.info("Client data entered correctly.");
                request.setAttribute("client", client.getClient());
                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/editClientView.jsp");
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("errorString", "Client with ID: #" + id + " does not exists.");
                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/client_list");
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
        logger.info("Client updating procedure started.");
        StringBuffer errorString = new StringBuffer();
        if (request.getParameter("id") == null || request.getParameter("id").isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/client_list");
            return;
        }
        ModelExtractor extractor = new ModelExtractor(request);
        Client client = extractor.getClient();
        if (client != null) {
            client.setId(Long.valueOf(request.getParameter("id")));
            Validator<Client> validator = new ClientValidator(client);
            if (validator.isValid()) {
                Connection connection = StoreAndCookieUtil.getStoredConnection(request);
                try {
                    ClientDAO clientDAO = new ClientDAOImpl(connection);
                    ClientDomain controlClient = clientDAO.getByDLNumber(client.getdLNumber());
                    if (controlClient == null || controlClient.getId() == client.getId()) {
                        logger.info("Client data entered correctly.");
                        clientDAO.update(new ClientDomain(client));
                        response.sendRedirect(request.getContextPath() + "/client_list");
                        return;
                    } else {
                        errorString.append("Client with driver's license number \"");
                        errorString.append(client.getdLNumber());
                        errorString.append("\" already exists. It's ID: #");
                        errorString.append(controlClient.getId());
                        errorString.append(".\n");
                    }
                } catch (SQLException ex) {
                    logger.warn("Data base exception.", ex);
                    request.setAttribute("javax.servlet.error.exception", ex);
                    request.setAttribute("javax.servlet.error.status_code", 500);
                    response.setStatus(500);
                }
            }
            errorString.append(validator.getErrorMessage());
            request.setAttribute("client", client);
        } else {
            errorString.append("Sorry, try to fill and submit form again. ");
            request.setAttribute("errorString", errorString.toString());
            doGet(request, response);
            return;
        }
        request.setAttribute("errorString", errorString.toString());
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/editClientView.jsp");
        dispatcher.forward(request, response);
    }
}
