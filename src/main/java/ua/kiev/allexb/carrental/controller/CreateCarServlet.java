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
 * @version 1.0 29.08.2016
 */
@WebServlet(urlPatterns = {"/car_list/create"})
public class CreateCarServlet extends HttpServlet {
    private static final long serialVersionUID = -2881754246718776443L;
    static final Logger logger = ApplicationLogger.getLogger(CreateCarServlet.class);

    public CreateCarServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Car creating form.");
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/createCarView.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Car creation procedure started.");
        Validator<Car> validator = new CarValidator(request, new Car());
        if (validator.validate()) {
            Connection connection = StoreAndCookieUtil.getStoredConnection(request);
            try {
                CarDAO carDAO = new CarDAOImpl(connection);
                CarDomain controlCar = carDAO.getByNumberPlate(validator.getValue().getNumberPlate());
                if (controlCar == null) {
                    logger.info("Car data entered correctly.");
                    carDAO.add(new CarDomain(validator.getValue()));
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
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/createCarView.jsp");
        dispatcher.forward(request, response);
    }
}
