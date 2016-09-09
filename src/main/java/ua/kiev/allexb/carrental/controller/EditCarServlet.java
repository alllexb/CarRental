package ua.kiev.allexb.carrental.controller;

import org.apache.log4j.Logger;
import ua.kiev.allexb.carrental.controller.validator.CarValidator;
import ua.kiev.allexb.carrental.controller.validator.Validator;
import ua.kiev.allexb.carrental.data.dao.CarDAO;
import ua.kiev.allexb.carrental.data.dao.CarDAOImpl;
import ua.kiev.allexb.carrental.data.domain.CarDomain;
import ua.kiev.allexb.carrental.model.Car;
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
 * @version 1.0 01.09.2016
 */
@WebServlet(urlPatterns = {"/car_list/edit"})
public class EditCarServlet extends HttpServlet {
    private static final long serialVersionUID = -6669657844128409978L;

    static final Logger logger = ApplicationLogger.getLogger(EditCarServlet.class);

    public EditCarServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Car editing form.");
        if (request.getParameter("id") == null) {
            response.sendRedirect(request.getContextPath() + "/car_list");
            return;
        }
        long id = Long.valueOf(request.getParameter("id"));
        Connection connection = StoreAndCookieUtil.getStoredConnection(request);
        try {
            CarDAO carDAO = new CarDAOImpl(connection);
            CarDomain car = carDAO.getById(id);
            if (car != null) {
                logger.info("Car data entered correctly.");
                request.setAttribute("car", car.getCar());
                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/editCarView.jsp");
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("errorString", "Car with ID:" + id + "does not exists.");
                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/car_list");
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
        logger.info("Car creation procedure started.");
        if (request.getParameter("id") == null) {
            response.sendRedirect(request.getContextPath() + "/car_list");
            return;
        }
        Car car = new Car();
        car.setId(Long.valueOf(request.getParameter("id")));
        Validator<Car> validator = new CarValidator(request, car);
        if (validator.validate()) {
            Connection connection = StoreAndCookieUtil.getStoredConnection(request);
            try {
                CarDAO carDAO = new CarDAOImpl(connection);
                CarDomain controlCar = carDAO.getByNumberPlate(validator.getValue().getNumberPlate());
                if (controlCar == null || controlCar.getId() == car.getId()) {
                    logger.info("Car data entered correctly.");
                    carDAO.update(new CarDomain(validator.getValue()));
                } else {
                    validator.addMessage("Car with number plate \"" + validator.getValue().getNumberPlate() + "\" already exists. It's ID: #" +
                            controlCar.getId() + ".");
                }
                if (validator.getMessage().isEmpty()) {
                    response.sendRedirect(request.getContextPath() + "/car_list");
                    return;
                }
            } catch (SQLException ex) {
                logger.warn("Data base exception.", ex);
                request.setAttribute("javax.servlet.error.exception", ex);
                request.setAttribute("javax.servlet.error.status_code", 500);
                response.setStatus(500);
            }
        }
        request.setAttribute("car", validator.getValue());
        request.setAttribute("errorString", validator.getMessage());
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/editCarView.jsp");
        dispatcher.forward(request, response);
    }
}
