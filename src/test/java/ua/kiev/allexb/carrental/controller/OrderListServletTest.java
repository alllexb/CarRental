package ua.kiev.allexb.carrental.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import ua.kiev.allexb.carrental.data.dao.DAOFactory;
import ua.kiev.allexb.carrental.data.dao.OrderDAO;
import ua.kiev.allexb.carrental.data.domain.CarDomain;
import ua.kiev.allexb.carrental.data.domain.ClientDomain;
import ua.kiev.allexb.carrental.data.domain.OrderDomain;
import ua.kiev.allexb.carrental.model.Car;
import ua.kiev.allexb.carrental.model.Order;
import ua.kiev.allexb.carrental.utils.StoreAndCookieUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author allexb
 * @version 1.0 05.10.2016
 */
@RunWith(MockitoJUnitRunner.class)
public class OrderListServletTest {

    final OrderListServlet servlet = new OrderListServlet();

    @Mock
    HttpSession session;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    RequestDispatcher requestDispatcher;
    @Mock
    DAOFactory daoFactory;
    @Mock
    Connection connection;
    @Mock
    OrderDAO orderDAO;

    CarDomain[] carsDom = {
            new CarDomain(1L, "AA0000AA", "BMW", Car.Color.GREEN, "new car", 2014, new BigDecimal(10), Car.Status.RENTED),
            new CarDomain(2L, "AA1111AA", "Audi", Car.Color.RED, "car", 2015, new BigDecimal(15), Car.Status.RENTED),
            new CarDomain(3L, "AA2222AA", "Renault", Car.Color.WHITE, "car", 2012, new BigDecimal(5.5), Car.Status.RENTED),
            };

    OrderDomain[] ordDom = {
            new OrderDomain(1L, new ClientDomain(1L, "Ivan", "Ivanov", new Date(1985, 5, 10), 102562545, 10), new HashSet<>(Arrays.asList(carsDom)), new Date(), new Date(), true),
            new OrderDomain(2L, new  ClientDomain(2L, "Vasiliy", "Petrov", new Date(1988, 10, 25), 252763522, 8), new HashSet<>(Arrays.asList(carsDom)), new Date(), new Date(), true),
            new OrderDomain(3L, new  ClientDomain(3L, "Anna", "Nova", new Date(1990, 4, 27), 505553355, 7), new HashSet<>(), new Date(), new Date(), true)
    };

    List<Order> orders = Arrays.asList(ordDom).stream().map(OrderDomain::getOrder).collect(Collectors.toList());
    Throwable sqlException;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(StoreAndCookieUtil.getStoredConnection(request)).thenReturn(connection);
        when(request.getSession()).thenReturn(session);
        when(StoreAndCookieUtil.getStoredDAOFactory(session)).thenReturn(daoFactory);
        when(daoFactory.getOrderDAO(connection)).thenReturn(orderDAO);
        sqlException = new SQLException();
        when(request.getRequestDispatcher("/WEB-INF/views/orderListView.jsp")).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet() throws Exception {
        when(orderDAO.getAll()).thenReturn(new ArrayList<>(Arrays.asList(ordDom)));
        servlet.doGet(request, response);
        verify(request).setAttribute("orderList", orders);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoGet_DB_throws_exception() throws Exception {
        when(orderDAO.getAll()).thenThrow(sqlException);
        servlet.doGet(request, response);
        verify(request).setAttribute("javax.servlet.error.exception", sqlException);
        verify(request).setAttribute("javax.servlet.error.status_code", 500);
        verify(response).setStatus(500);
        verify(request).setAttribute("orderList", null);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost() throws Exception {
        when(orderDAO.getAll()).thenReturn(new ArrayList<>(Arrays.asList(ordDom)));
        servlet.doPost(request, response);
        verify(request).setAttribute("orderList", orders);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost_DB_throws_exception() throws Exception {
        when(orderDAO.getAll()).thenThrow(sqlException);
        servlet.doPost(request, response);
        verify(request).setAttribute("javax.servlet.error.exception", sqlException);
        verify(request).setAttribute("javax.servlet.error.status_code", 500);
        verify(response).setStatus(500);
        verify(request).setAttribute("orderList", null);
        verify(requestDispatcher).forward(request, response);
    }
}