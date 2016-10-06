package ua.kiev.allexb.carrental.controller;

import org.apache.log4j.Logger;
import ua.kiev.allexb.carrental.controller.validator.AdministratorValidator;
import ua.kiev.allexb.carrental.controller.validator.Validator;
import ua.kiev.allexb.carrental.data.dao.AdministratorDAO;
import ua.kiev.allexb.carrental.data.dao.DAOFactory;
import ua.kiev.allexb.carrental.data.domain.AdministratorDomain;
import ua.kiev.allexb.carrental.model.Administrator;
import ua.kiev.allexb.carrental.model.helpers.PasswordHelper;
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
 * @version 1.0 20.09.2016
 */
@WebServlet(urlPatterns = {"/admin_list/edit"})
public class EditAdministratorServlet extends HttpServlet {

    static final Logger logger = Logger.getLogger(EditClientServlet.class);

    private static final long serialVersionUID = 1502749479688638069L;

    public EditAdministratorServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Administrator editing form.");
        if (request.getParameter("id") == null || request.getParameter("id").isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin_list");
            return;
        }
        long id = Long.valueOf(request.getParameter("id"));
        Connection connection = StoreAndCookieUtil.getStoredConnection(request);
        try {
//            AdministratorDAO administratorDAO = new AdministratorDAOImpl(connection);
            DAOFactory daoFactory = StoreAndCookieUtil.getStoredDAOFactory(request.getSession());
            AdministratorDAO administratorDAO = daoFactory.getAdministratorDao(connection);
            AdministratorDomain administrator = administratorDAO.getById(id);
            if (administrator != null) {
                logger.info("Administrator data entered correctly.");
                request.setAttribute("administrator", administrator.getAdministrator());
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/editAdminView.jsp");
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("errorString", "Administrator with ID: #" + id + " does not exists.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/admin_list");
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
        logger.info("Administrator updating procedure started.");
        StringBuffer errorString = new StringBuffer();
        if (request.getParameter("id") == null || request.getParameter("id").isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin_list");
            return;
        }
        ModelExtractor extractor = new ModelExtractor(request);
        Administrator administrator = extractor.getAdministrator();
        if (administrator != null) {
            administrator.setId(Long.valueOf(request.getParameter("id")));
            Validator<Administrator> validator = new AdministratorValidator(administrator);
            if (validator.isValid()) {
                Connection connection = StoreAndCookieUtil.getStoredConnection(request);
                try {
//                    AdministratorDAO administratorDAO = new AdministratorDAOImpl(connection);
                    DAOFactory daoFactory = StoreAndCookieUtil.getStoredDAOFactory(request.getSession());
                    AdministratorDAO administratorDAO = daoFactory.getAdministratorDao(connection);
                    AdministratorDomain controlAdministrator = administratorDAO.getById(administrator.getId());
                    if (controlAdministrator != null && controlAdministrator.getLogin().equals(administrator.getLogin()) && controlAdministrator.getPassword().equals(administrator.getPassword())) {
                        boolean isLoginedAdministrator = controlAdministrator.getAdministrator().equals(StoreAndCookieUtil.getLoginedAdministrator(request.getSession()));
                        boolean isChangePassword = request.getParameter("changePassword") != null;
                        boolean isConfirm = false;
                        if (isChangePassword) {
                            String newPassword = request.getParameter("new_password");
                            String confirmNewPassword = request.getParameter("conf_new_password");
                            if (newPassword.equals(confirmNewPassword)) {
                                administrator.setPassword(PasswordHelper.getSecurePassword(newPassword));
                                isConfirm = true;
                            } else {
                                errorString.append("New password did not confirm well.");
                            }
                        }
                        if (!isChangePassword || isConfirm) {
                            logger.info("Administrator data entered correctly.");
                            administratorDAO.update(new AdministratorDomain(administrator));
                            if (isLoginedAdministrator)
                                StoreAndCookieUtil.storeLoginedAdministrator(request.getSession(), administrator);
                            response.sendRedirect(request.getContextPath() + "/admin_list");
                            return;
                        }
                    } else {
                        errorString.append("Administrator login or password is incorrect!");
                    }
                } catch (SQLException ex) {
                    logger.warn("Data base exception.", ex);
                    request.setAttribute("javax.servlet.error.exception", ex);
                    request.setAttribute("javax.servlet.error.status_code", 500);
                    response.setStatus(500);
                }
            }
            errorString.append(validator.getErrorMessage());
        } else {
            errorString.append("Sorry, try to fill and submit form again. ");
            request.setAttribute("errorString", errorString.toString());
            doGet(request, response);
            return;
        }
        request.setAttribute("administrator", administrator);
        request.setAttribute("errorString", errorString.toString());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/editAdminView.jsp");
        dispatcher.forward(request, response);
    }
}
