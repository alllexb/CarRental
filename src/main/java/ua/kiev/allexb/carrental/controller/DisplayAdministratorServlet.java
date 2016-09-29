package ua.kiev.allexb.carrental.controller;

import org.apache.log4j.Logger;
import ua.kiev.allexb.carrental.data.dao.AdministratorDAO;
import ua.kiev.allexb.carrental.data.dao.AdministratorDAOImpl;
import ua.kiev.allexb.carrental.data.domain.AdministratorDomain;
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
 * @version 1.0 26.09.2016
 */
@WebServlet(urlPatterns = {"/admin_list/display"})
public class DisplayAdministratorServlet extends HttpServlet {

    final static Logger logger = Logger.getLogger(DeleteAdministratorServlet.class);

    private static final long serialVersionUID = -7420994575316483092L;

    public DisplayAdministratorServlet() {
        super();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Administrator viewing form.");
        if (request.getParameter("id") == null) {
            response.sendRedirect(request.getContextPath() + "/admin_list");
            return;
        }
        long id = Long.valueOf(request.getParameter("id"));
        Connection connection = StoreAndCookieUtil.getStoredConnection(request);
        try {
            AdministratorDAO administratorDAO = new AdministratorDAOImpl(connection);
            AdministratorDomain administrator = administratorDAO.getById(id);
            if (administrator != null) {
                logger.info("Administrator data entered correctly.");
                request.setAttribute("administrator", administrator.getAdministrator());
                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/displayAdminView.jsp");
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("errorString", "Administrator with ID: #" + id + " does not exists.");
                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/admin_list");
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
        this.doGet(request, response);
    }
}
