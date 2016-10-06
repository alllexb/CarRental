package ua.kiev.allexb.carrental.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import ua.kiev.allexb.carrental.data.dao.AdministratorDAO;
import ua.kiev.allexb.carrental.data.dao.DAOFactory;
import ua.kiev.allexb.carrental.data.domain.AdministratorDomain;
import ua.kiev.allexb.carrental.model.Administrator;
import ua.kiev.allexb.carrental.model.helpers.PasswordHelper;
import ua.kiev.allexb.carrental.utils.StoreAndCookieUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
public class AdministratorListServletTest {

    final AdministratorListServlet servlet = new AdministratorListServlet();

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
    AdministratorDAO administratorDAO;

    AdministratorDomain[] ad =
            { new AdministratorDomain(1L, "Jack", "Silver", "js@email.com", "js-admin", PasswordHelper.getSecurePassword("jjss12")),
              new AdministratorDomain(2L, "Jon", "Dou", "jd@spy.com", "jd-admin", PasswordHelper.getSecurePassword("stop33")),
              new AdministratorDomain(3L, "Alexa", "Smith", "as@ahho.com", "as-admin", PasswordHelper.getSecurePassword("++lexy")),
            };

    List<Administrator> a = new ArrayList<>(Arrays.asList(ad)).stream().map(AdministratorDomain::getAdministrator).collect(Collectors.toList());
    Throwable sqlException;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(StoreAndCookieUtil.getStoredConnection(request)).thenReturn(connection);
        when(request.getSession()).thenReturn(session);
        when(StoreAndCookieUtil.getStoredDAOFactory(session)).thenReturn(daoFactory);
        when(daoFactory.getAdministratorDao(connection)).thenReturn(administratorDAO);
        sqlException = new SQLException();
        when(request.getRequestDispatcher("/WEB-INF/views/adminListView.jsp")).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet() throws Exception {
        when(administratorDAO.getAll()).thenReturn(new ArrayList<>(Arrays.asList(ad)));
        servlet.doGet(request, response);
        verify(request).setAttribute("administratorList", a);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoGet_DB_throws_exception() throws Exception {
        when(administratorDAO.getAll()).thenThrow(sqlException);
        servlet.doGet(request, response);
        verify(request).setAttribute("javax.servlet.error.exception", sqlException);
        verify(request).setAttribute("javax.servlet.error.status_code", 500);
        verify(response).setStatus(500);
        verify(request).setAttribute("administratorList", null);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost() throws Exception {
        when(administratorDAO.getAll()).thenReturn(new ArrayList<>(Arrays.asList(ad)));
        servlet.doPost(request, response);
        verify(request).setAttribute("administratorList", a);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost_DB_throws_exception() throws Exception {
        when(administratorDAO.getAll()).thenThrow(sqlException);
        servlet.doPost(request, response);
        verify(request).setAttribute("javax.servlet.error.exception", sqlException);
        verify(request).setAttribute("javax.servlet.error.status_code", 500);
        verify(response).setStatus(500);
        verify(request).setAttribute("administratorList", null);
        verify(requestDispatcher).forward(request, response);
    }
}