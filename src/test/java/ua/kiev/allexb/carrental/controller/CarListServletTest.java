package ua.kiev.allexb.carrental.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import ua.kiev.allexb.carrental.data.dao.CarDAO;
import ua.kiev.allexb.carrental.data.dao.DAOFactory;
import ua.kiev.allexb.carrental.data.domain.CarDomain;
import ua.kiev.allexb.carrental.model.Car;
import ua.kiev.allexb.carrental.utils.StoreAndCookieUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author allexb
 * @version 1.0 05.10.2016
 */
@RunWith(MockitoJUnitRunner.class)
public class CarListServletTest {

    final CarListServlet servlet = new CarListServlet();

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
    CarDAO carDAO;

    CarDomain[] carsDom =
            { new CarDomain(1L, "AA0000AA", "BMW", Car.Color.GREEN, "new car", 2014, new BigDecimal(10), Car.Status.AVAILABLE),
              new CarDomain(2L, "AA1111AA", "Audi", Car.Color.RED, "car", 2015, new BigDecimal(15), Car.Status.RENTED),
              new CarDomain(3L, "AA2222AA", "Renault", Car.Color.WHITE, "car", 2012, new BigDecimal(5.5), Car.Status.ON_SERVICE),
            };

    List<Car> cars = new ArrayList<>(Arrays.asList(carsDom)).stream().map(CarDomain::getCar).collect(Collectors.toList());
    Throwable sqlException;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(StoreAndCookieUtil.getStoredConnection(request)).thenReturn(connection);
        when(request.getSession()).thenReturn(session);
        when(StoreAndCookieUtil.getStoredDAOFactory(session)).thenReturn(daoFactory);
        when(daoFactory.getCarDAO(connection)).thenReturn(carDAO);
        sqlException = new SQLException();
        when(request.getRequestDispatcher("/WEB-INF/views/carListView.jsp")).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet() throws Exception {
        when(carDAO.getAll()).thenReturn(new ArrayList<>(Arrays.asList(carsDom)));
        servlet.doGet(request, response);
        verify(request).setAttribute("carList", cars);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoGet_DB_throws_exception() throws Exception {
        when(carDAO.getAll()).thenThrow(sqlException);
        servlet.doGet(request, response);
        verify(request).setAttribute("javax.servlet.error.exception", sqlException);
        verify(request).setAttribute("javax.servlet.error.status_code", 500);
        verify(response).setStatus(500);
        verify(request).setAttribute("carList", null);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost() throws Exception {
        when(carDAO.getAll()).thenReturn(new ArrayList<>(Arrays.asList(carsDom)));
        servlet.doPost(request, response);
        verify(request).setAttribute("carList", cars);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost_DB_throws_exception() throws Exception {
        when(carDAO.getAll()).thenThrow(sqlException);
        servlet.doPost(request, response);
        verify(request).setAttribute("javax.servlet.error.exception", sqlException);
        verify(request).setAttribute("javax.servlet.error.status_code", 500);
        verify(response).setStatus(500);
        verify(request).setAttribute("carList", null);
        verify(requestDispatcher).forward(request, response);
    }
}