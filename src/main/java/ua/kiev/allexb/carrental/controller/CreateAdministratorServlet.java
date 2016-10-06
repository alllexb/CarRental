package ua.kiev.allexb.carrental.controller;

import org.apache.log4j.Logger;
import ua.kiev.allexb.carrental.controller.validator.AdministratorValidator;
import ua.kiev.allexb.carrental.controller.validator.Validator;
import ua.kiev.allexb.carrental.data.dao.AdministratorDAO;
import ua.kiev.allexb.carrental.data.dao.DAOFactory;
import ua.kiev.allexb.carrental.data.domain.AdministratorDomain;
import ua.kiev.allexb.carrental.model.Administrator;
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
@WebServlet(urlPatterns = {"/admin_list/create"})
public class CreateAdministratorServlet extends HttpServlet {

    static final Logger logger = Logger.getLogger(CreateAdministratorServlet.class);

    private static final long serialVersionUID = -7531444911729831583L;

    public CreateAdministratorServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Administrator creating form.");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/createAdminView.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Administrator creation procedure started.");
        StringBuffer errorString = new StringBuffer();
        ModelExtractor extractor = new ModelExtractor(request);
        Administrator administrator = extractor.getAdministrator();
        if (administrator != null) {
            Validator<Administrator> validator = new AdministratorValidator(administrator);
            if (validator.isValid()) {
                Connection connection = StoreAndCookieUtil.getStoredConnection(request);
                try {
//                    AdministratorDAO administratorDAO = new AdministratorDAOImpl(connection);
                    DAOFactory daoFactory = StoreAndCookieUtil.getStoredDAOFactory(request.getSession());
                    AdministratorDAO administratorDAO = daoFactory.getAdministratorDao(connection);
                    AdministratorDomain controlAdministrator = administratorDAO.getByLogin(administrator.getLogin());
                    if (controlAdministrator == null) {
                        logger.info("Administrator data entered correctly.");
                        administratorDAO.add(new AdministratorDomain(administrator));
                        response.sendRedirect(request.getContextPath() + "/admin_list");
                        return;
                    } else {
                        errorString.append("Administrator with login \"");
                        errorString.append(administrator.getLogin());
                        errorString.append("\" already exists. It's ID: #");
                        errorString.append(controlAdministrator.getId());
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
            request.setAttribute("administrator", administrator);
        } else {
            errorString.append("Sorry, try to fill and submit form again. ");
            request.setAttribute("errorString", errorString.toString());
            doGet(request, response);
            return;
        }
        request.setAttribute("errorString", errorString.toString());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/createAdminView.jsp");
        dispatcher.forward(request, response);
    }
}
