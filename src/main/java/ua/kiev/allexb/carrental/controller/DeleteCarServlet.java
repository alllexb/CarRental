package ua.kiev.allexb.carrental.controller;

import org.apache.log4j.Logger;
import ua.kiev.allexb.carrental.data.dao.CarDAO;
import ua.kiev.allexb.carrental.data.dao.DAOFactory;
import ua.kiev.allexb.carrental.data.domain.CarDomain;
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
 * @version 1.0 29.08.2016
 */
@WebServlet(urlPatterns = {"/car_list/delete"})
public class DeleteCarServlet extends HttpServlet {

    static final Logger logger = Logger.getLogger(DeleteCarServlet.class);

    private static final long serialVersionUID = 5583615804009416532L;

    public DeleteCarServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Car deleting form.");
        if (request.getParameter("id") == null) {
            response.sendRedirect(request.getContextPath() + "/car_list");
            return;
        }
        long id = Long.valueOf(request.getParameter("id"));
        Connection connection = StoreAndCookieUtil.getStoredConnection(request);
        try {
//            CarDAO carDAO = new CarDAOImpl(connection);
            DAOFactory daoFactory = StoreAndCookieUtil.getStoredDAOFactory(request.getSession());
            CarDAO carDAO = daoFactory.getCarDAO(connection);
            CarDomain car = carDAO.getById(id);
            if (car != null) {
                logger.info("Car data entered correctly.");
                request.setAttribute("car", car.getCar());
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/deleteCarView.jsp");
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("errorString", "Car with ID: #" + id + " does not exists.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/car_list");
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
        logger.info("Car deletion procedure started.");
        if (request.getParameter("id") == null) {
            response.sendRedirect(request.getContextPath() + "/car_list");
            return;
        }
        long id = Long.valueOf(request.getParameter("id"));
        Connection connection = StoreAndCookieUtil.getStoredConnection(request);
            try {
//                CarDAO carDAO = new CarDAOImpl(connection);
                DAOFactory daoFactory = StoreAndCookieUtil.getStoredDAOFactory(request.getSession());
                CarDAO carDAO = daoFactory.getCarDAO(connection);
                CarDomain controlCar = carDAO.getById(id);
                if (controlCar != null) {
                    carDAO.remove(controlCar);
                    logger.info("Car deleted.");
                } else {
                    String errorString = "Car with with ID: #" + id + " doesn't exist.";
                    request.setAttribute("errorString", errorString);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/car_list");
                    dispatcher.forward(request, response);
                }
            } catch (SQLException ex) {
                logger.warn("Data base exception.", ex);
                request.setAttribute("javax.servlet.error.exception", ex);
                request.setAttribute("javax.servlet.error.status_code", 500);
                response.setStatus(500);
            }
        response.sendRedirect(request.getContextPath() + "/car_list");
    }
}
