package ua.kiev.allexb.carrental.controller;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import ua.kiev.allexb.carrental.model.Administrator;
import ua.kiev.allexb.carrental.utils.StoreAndCookieUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author allexb
 * @version 1.0 30.09.2016
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginServletTest extends TestCase {

    final LoginServlet servlet = new LoginServlet();

    @Mock
    HttpSession session;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    RequestDispatcher requestDispatcher;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    // if administrator is null
    public void testDoGet_null_administrator() throws Exception {
        when(request.getRequestDispatcher("/WEB-INF/views/loginView.jsp")).thenReturn(requestDispatcher);
        servlet.doGet(request, response);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    // if valid administrator
    public void testDoGet_valid_administrator() throws Exception {
        when(request.getRequestDispatcher("/WEB-INF/views/adminInfoView.jsp")).thenReturn(requestDispatcher);
        when(StoreAndCookieUtil.getLoginedAdministrator(session)).thenReturn(new Administrator());
        servlet.doGet(request, response);
        verify(request).setAttribute("errorString", "You need to logout before changing administrator account.");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    // if administrator is null
    public void testDoPost_null_administrator() throws Exception {
        when(request.getRequestDispatcher("/WEB-INF/views/loginView.jsp")).thenReturn(requestDispatcher);
        servlet.doPost(request, response);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    // if valid administrator
    public void testDoPost_valid_administrator() throws Exception {
        when(request.getRequestDispatcher("/WEB-INF/views/adminInfoView.jsp")).thenReturn(requestDispatcher);
        when(StoreAndCookieUtil.getLoginedAdministrator(session)).thenReturn(new Administrator());
        servlet.doPost(request, response);
        verify(request).setAttribute("errorString", "You need to logout before changing administrator account.");
        verify(requestDispatcher).forward(request, response);
    }

}