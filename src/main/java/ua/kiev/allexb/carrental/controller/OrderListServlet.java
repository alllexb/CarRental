package ua.kiev.allexb.carrental.controller;

import org.apache.log4j.Logger;
import ua.kiev.allexb.carrental.data.dao.OrderDAO;
import ua.kiev.allexb.carrental.data.dao.OrderDAOImpl;
import ua.kiev.allexb.carrental.data.domain.OrderDomain;
import ua.kiev.allexb.carrental.model.Order;
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
 * @version 1.0 26.09.2016
 */
@WebServlet(urlPatterns = {"/order_list"})
public class OrderListServlet extends HttpServlet{

    static final Logger logger = Logger.getLogger(OrderListServlet.class);

    private static final long serialVersionUID = 8902364095440578059L;

    public OrderListServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Extracting of order list list from database.");
        List<Order> orderList = null;
        Connection connection = StoreAndCookieUtil.getStoredConnection(request);
        try {
            OrderDAO orderDAO = new OrderDAOImpl(connection);
            orderList = orderDAO.getAll().stream().map(OrderDomain::getOrder).collect(Collectors.toList());
            logger.info("Order list extracted.");
        } catch (SQLException ex) {
            logger.warn("Data base exception.", ex);
            request.setAttribute("javax.servlet.error.exception", ex);
            request.setAttribute("javax.servlet.error.status_code", 500);
            response.setStatus(500);
        }
        request.setAttribute("orderList", orderList);
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/orderListView.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
