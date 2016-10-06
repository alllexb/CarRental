package ua.kiev.allexb.carrental.controller;

import org.apache.log4j.Logger;
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
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author allexb
 * @version 1.0 11.08.2016
 */
@WebServlet(urlPatterns = {"/car_list"})
public class CarListServlet extends HttpServlet {

    static final Logger logger = Logger.getLogger(CarListServlet.class);

    private static final long serialVersionUID = -5098121881329935823L;

    public CarListServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Extracting of car list from database.");
        List<Car> cars = null;
        Connection connection = StoreAndCookieUtil.getStoredConnection(request);
        try {
//            CarDAO carDAO = new CarDAOImpl(connection);
            DAOFactory daoFactory = StoreAndCookieUtil.getStoredDAOFactory(request.getSession());
            CarDAO carDAO = daoFactory.getCarDAO(connection);
            cars = carDAO.getAll().stream().map(CarDomain::getCar).collect(Collectors.toList());
            logger.info("Car list extracted.");
        } catch (SQLException ex) {
            logger.warn("Data base exception.", ex);
            request.setAttribute("javax.servlet.error.exception", ex);
            request.setAttribute("javax.servlet.error.status_code", 500);
            response.setStatus(500);
        }
        request.setAttribute("carList", cars);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/carListView.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
