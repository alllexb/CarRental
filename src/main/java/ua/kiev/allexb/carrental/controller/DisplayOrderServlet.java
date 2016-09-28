package ua.kiev.allexb.carrental.controller;

import org.apache.log4j.Logger;
import ua.kiev.allexb.carrental.data.dao.OrderDAO;
import ua.kiev.allexb.carrental.data.dao.OrderDAOImpl;
import ua.kiev.allexb.carrental.data.domain.OrderDomain;
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
 * @version 1.0 28.09.2016
 */
@WebServlet(urlPatterns = {"/order_list/display"})
public class DisplayOrderServlet extends HttpServlet {

    static final Logger logger = ApplicationLogger.getLogger(DisplayOrderServlet.class);

    private static final long serialVersionUID = -6301755974067855695L;

    public DisplayOrderServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Order viewing form.");
        if (request.getParameter("id") == null) {
            response.sendRedirect(request.getContextPath() + "/order_list");
            return;
        }
        long id = Long.valueOf(request.getParameter("id"));
        Connection connection = StoreAndCookieUtil.getStoredConnection(request);
        try {
            OrderDAO orderDAO = new OrderDAOImpl(connection);
            OrderDomain order = orderDAO.getById(id);
            if (order != null) {
                logger.info("Order data entered correctly.");
                request.setAttribute("order", order.getOrder());
                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/displayOrderView.jsp");
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("errorString", "Order with ID: #" + id + " does not exists.");
                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/order_list");
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
