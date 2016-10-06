package ua.kiev.allexb.carrental.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author allexb
 * @version 1.0 30.09.2016
 */
@RunWith(MockitoJUnitRunner.class)
public class HomeServletTest extends TestCase {

    private final HomeServlet servlet = new HomeServlet();

    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    RequestDispatcher requestDispatcher;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(request.getRequestDispatcher("/WEB-INF/views/homeView.jsp")).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet() throws Exception {
        servlet.doGet(request, response);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost() throws Exception {
        servlet.doPost(request, response);
        verify(requestDispatcher).forward(request, response);
    }
}