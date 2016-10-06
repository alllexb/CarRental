package ua.kiev.allexb.carrental.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import ua.kiev.allexb.carrental.data.dao.ClientDAO;
import ua.kiev.allexb.carrental.data.dao.DAOFactory;
import ua.kiev.allexb.carrental.data.domain.ClientDomain;
import ua.kiev.allexb.carrental.model.Client;
import ua.kiev.allexb.carrental.utils.StoreAndCookieUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author allexb
 * @version 1.0 05.10.2016
 */
@RunWith(MockitoJUnitRunner.class)
public class ClientListServletTest {

    final ClientListServlet servlet = new ClientListServlet();

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
    ClientDAO clientDAO;

    ClientDomain[] clDom = {
            new ClientDomain(1L, "Ivan", "Ivanov", new Date(1985, 5, 10), 102562545, 10),
            new ClientDomain(2L, "Vasiliy", "Petrov", new Date(1988, 10, 25), 252763522, 8),
            new ClientDomain(3L, "Anna", "Nova", new Date(1990, 4, 27), 505553355, 7)
    };

    List<Client> clients = Arrays.asList(clDom).stream().map(ClientDomain::getClient).collect(Collectors.toList());
    Throwable sqlException;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(StoreAndCookieUtil.getStoredConnection(request)).thenReturn(connection);
        when(request.getSession()).thenReturn(session);
        when(StoreAndCookieUtil.getStoredDAOFactory(session)).thenReturn(daoFactory);
        when(daoFactory.getClientDao(connection)).thenReturn(clientDAO);
        sqlException = new SQLException();
        when(request.getRequestDispatcher("/WEB-INF/views/clientListView.jsp")).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet() throws Exception {
        when(clientDAO.getAll()).thenReturn(new ArrayList<>(Arrays.asList(clDom)));
        servlet.doGet(request, response);
        verify(request).setAttribute("clientList", clients);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoGet_DB_throws_exception() throws Exception {
        when(clientDAO.getAll()).thenThrow(sqlException);
        servlet.doGet(request, response);
        verify(request).setAttribute("javax.servlet.error.exception", sqlException);
        verify(request).setAttribute("javax.servlet.error.status_code", 500);
        verify(response).setStatus(500);
        verify(request).setAttribute("clientList", null);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost() throws Exception {
        when(clientDAO.getAll()).thenReturn(new ArrayList<>(Arrays.asList(clDom)));
        servlet.doPost(request, response);
        verify(request).setAttribute("clientList", clients);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost_DB_throws_exception() throws Exception {
        when(clientDAO.getAll()).thenThrow(sqlException);
        servlet.doPost(request, response);
        verify(request).setAttribute("javax.servlet.error.exception", sqlException);
        verify(request).setAttribute("javax.servlet.error.status_code", 500);
        verify(response).setStatus(500);
        verify(request).setAttribute("clientList", null);
        verify(requestDispatcher).forward(request, response);
    }
}