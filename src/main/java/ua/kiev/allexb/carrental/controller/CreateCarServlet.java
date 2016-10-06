package ua.kiev.allexb.carrental.controller;

import org.apache.log4j.Logger;
import ua.kiev.allexb.carrental.controller.validator.CarValidator;
import ua.kiev.allexb.carrental.controller.validator.Validator;
import ua.kiev.allexb.carrental.data.dao.CarDAO;
import ua.kiev.allexb.carrental.data.dao.DAOFactory;
import ua.kiev.allexb.carrental.data.domain.CarDomain;
import ua.kiev.allexb.carrental.model.Car;
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

    static final Logger logger = Logger.getLogger(CreateCarServlet.class);

    private static final long serialVersionUID = -2881754246718776443L;

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
        StringBuffer errorString = new StringBuffer();
        ModelExtractor extractor = new ModelExtractor(request);
        Car car = extractor.getCar();
        if (car != null) {
            Validator<Car> validator = new CarValidator(car);
            if (validator.isValid()) {
                Connection connection = StoreAndCookieUtil.getStoredConnection(request);
                try {
//                    CarDAO carDAO = new CarDAOImpl(connection);
                    DAOFactory daoFactory = StoreAndCookieUtil.getStoredDAOFactory(request.getSession());
                    CarDAO carDAO = daoFactory.getCarDAO(connection);
                    CarDomain controlCar = carDAO.getByNumberPlate(car.getNumberPlate());
                    if (controlCar == null) {
                        logger.info("Car data entered correctly.");
                        carDAO.add(new CarDomain(car));
                        response.sendRedirect(request.getContextPath() + "/car_list");
                        return;
                    } else {
                        errorString.append("Car with number plate \"");
                        errorString.append(car.getNumberPlate());
                        errorString.append("\" already exists. It's ID: #");
                        errorString.append(controlCar.getId());
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
            request.setAttribute("car", car);
        } else {
            errorString.append("Sorry, try to fill and submit form again. ");
            request.setAttribute("errorString", errorString.toString());
            doGet(request, response);
            return;
        }
        request.setAttribute("errorString", errorString.toString());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/createCarView.jsp");
        dispatcher.forward(request, response);
    }
}
