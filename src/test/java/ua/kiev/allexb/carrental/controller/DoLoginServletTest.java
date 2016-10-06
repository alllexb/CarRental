package ua.kiev.allexb.carrental.controller;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import ua.kiev.allexb.carrental.data.dao.AdministratorDAO;
import ua.kiev.allexb.carrental.data.dao.AdministratorDAOImpl;
import ua.kiev.allexb.carrental.data.dao.DAOFactory;
import ua.kiev.allexb.carrental.data.dao.MySqlDAOFactory;
import ua.kiev.allexb.carrental.data.domain.AdministratorDomain;
import ua.kiev.allexb.carrental.model.Administrator;
import ua.kiev.allexb.carrental.model.helpers.PasswordHelper;
import ua.kiev.allexb.carrental.utils.StoreAndCookieUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

/**
 * @author allexb
 * @version 1.0 30.09.2016
 */

@RunWith(MockitoJUnitRunner.class)
public class DoLoginServletTest extends TestCase {

    final DoLoginServlet servlet = new DoLoginServlet();

    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    HttpSession session;
    @Mock
    RequestDispatcher requestDispatcher;
    @Mock
    Connection connection;
    @Mock
    AdministratorDAO administratorDAO;
    @Mock
    DAOFactory daoFactory;
    @Mock
    Administrator admin;
    @Captor
    ArgumentCaptor<Cookie> captor;
    AdministratorDomain administratorDomain;
    Throwable sqlException;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        administratorDomain = new AdministratorDomain(1L, "admin", "admin", "a@car.net", "a", PasswordHelper.getSecurePassword("a"));
        sqlException = new SQLException();
    }

    @Test
    // Test: login = null & password = null
    public void testDoGet_null_login_and_password() throws Exception {
        when(request.getParameter("login")).thenReturn(null);
        when(request.getParameter("password")).thenReturn(null);
        when(request.getHeader("Referer")).thenReturn(null);
        when(request.getContextPath()).thenReturn("localhost:8080");
        servlet.doGet(request, response);
        verify(response).sendRedirect(request.getContextPath() + "/home");
    }

    @Test
    // Test: login = "" & password = ""
    public void testDoGet_login_and_password_is_empty() throws Exception {
        when(request.getParameter("login")).thenReturn("");
        when(request.getParameter("password")).thenReturn("");
        when(request.getHeader("Referer")).thenReturn("localhost:8080/doLogin");
        when(request.getRequestDispatcher("/WEB-INF/views/loginView.jsp")).thenReturn(requestDispatcher);
        servlet.doGet(request, response);
        verify(request).setAttribute("errorString", "Required login and password!");
        verify(request).setAttribute("admin", new Administrator(0L, null, null, null, "", ""));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    // Test: login = null & password = ""
    public void testDoGet_login_null_and_password_is_empty() throws Exception {
        when(request.getParameter("login")).thenReturn(null);
        when(request.getParameter("password")).thenReturn("");
        when(request.getHeader("Referer")).thenReturn("localhost:8080/car_list");
        when(request.getRequestDispatcher("/WEB-INF/views/loginView.jsp")).thenReturn(requestDispatcher);
        servlet.doGet(request, response);
        verify(request).setAttribute("errorString", "Required login and password!");
        verify(request).setAttribute("admin", new Administrator(0L, null, null, null, "", ""));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    // Test: login = "" & password = null
    public void testDoGet_login_is_empty_and_password_null() throws Exception {
        when(request.getParameter("login")).thenReturn("");
        when(request.getParameter("password")).thenReturn(null);
        when(request.getHeader("Referer")).thenReturn("localhost:8080/car_list");
        when(request.getRequestDispatcher("/WEB-INF/views/loginView.jsp")).thenReturn(requestDispatcher);
        servlet.doGet(request, response);
        verify(request).setAttribute("errorString", "Required login and password!");
        verify(request).setAttribute("admin", new Administrator(0L, null, null, null, "", ""));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    // Test: login = "" & password = "a"
    public void testDoGet_only_login_is_empty() throws Exception {
        when(request.getParameter("login")).thenReturn("");
        when(request.getParameter("password")).thenReturn("a");
        when(request.getHeader("Referer")).thenReturn("localhost:8080/doLogin");
        when(request.getRequestDispatcher("/WEB-INF/views/loginView.jsp")).thenReturn(requestDispatcher);
        servlet.doGet(request, response);
        verify(request).setAttribute("errorString", "Required login and password!");
        verify(request).setAttribute("admin", new Administrator(0L, null, null, null, "", "a"));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    // Test: login = "a" & password = "a" & database response - null
    public void testDoGet_DB_returns_null() throws Exception {
        when(request.getParameter("login")).thenReturn("a");
        when(request.getParameter("password")).thenReturn("a");
        when(StoreAndCookieUtil.getStoredConnection(request)).thenReturn(connection);
        when(request.getSession()).thenReturn(session);
        when(StoreAndCookieUtil.getStoredDAOFactory(request.getSession())).thenReturn(daoFactory);
        when(daoFactory.getAdministratorDao(connection)).thenReturn(administratorDAO);
        when(administratorDAO.getByLoginAndPassword("a", PasswordHelper.getSecurePassword("a"))).thenReturn(null);
        when(request.getRequestDispatcher("/WEB-INF/views/loginView.jsp")).thenReturn(requestDispatcher);
        servlet.doGet(request, response);
        verify(request).setAttribute("errorString", "Login or password invalid");
        verify(request).setAttribute("admin", new Administrator(0L, null, null, null, "a", "a"));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    // Test: login = "a" & password = "a" & database throws SQLException
    public void testDoGet_DB_throws_exception() throws Exception {
        when(request.getParameter("login")).thenReturn("a");
        when(request.getParameter("password")).thenReturn("a");
        when(StoreAndCookieUtil.getStoredConnection(request)).thenReturn(connection);
        when(request.getSession()).thenReturn(session);
        when(StoreAndCookieUtil.getStoredDAOFactory(request.getSession())).thenReturn(daoFactory);
        when(daoFactory.getAdministratorDao(connection)).thenReturn(administratorDAO);
        when(administratorDAO.getByLoginAndPassword("a", PasswordHelper.getSecurePassword("a"))).thenThrow(sqlException);
        when(request.getRequestDispatcher("/WEB-INF/views/loginView.jsp")).thenReturn(requestDispatcher);
        servlet.doGet(request, response);
        verify(request).setAttribute("javax.servlet.error.exception", sqlException);
        verify(request).setAttribute("javax.servlet.error.status_code", 500);
        verify(response).setStatus(500);
        verify(request).setAttribute("errorString", "Sorry... Data base does not respond.");
        verify(request).setAttribute("admin", new Administrator(0L, null, null, null, "a", "a"));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    // Test: login = "a" & password = "a" & database response - administratorDomain (valid)
    // Request header contains "Referer" to "localhost:8080/login"
    public void testDoGet_valid_administrator_referer_login() throws Exception {
        when(request.getParameter("login")).thenReturn("a");
        when(request.getParameter("password")).thenReturn("a");
        when(request.getHeader("Referer")).thenReturn("localhost:8080/login");
        when(StoreAndCookieUtil.getStoredConnection(request)).thenReturn(connection);
        when(request.getSession()).thenReturn(session);
        when(StoreAndCookieUtil.getStoredDAOFactory(request.getSession())).thenReturn(daoFactory);
        when(daoFactory.getAdministratorDao(connection)).thenReturn(administratorDAO);
        when(administratorDAO.getByLoginAndPassword("a", PasswordHelper.getSecurePassword("a"))).thenReturn(administratorDomain);
        when(request.getContextPath()).thenReturn("localhost:8080");
        servlet.doGet(request, response);
        verify(session).setAttribute("loginedAdmin", administratorDomain.getAdministrator());
        verify(response).sendRedirect("localhost:8080/home");
    }

    @Test
    // Test: login = "a" & password = "a" & rememberMe = "" & database response - administratorDomain (valid)
    // Request header contains "Referer" to "localhost:8080/car_list"
    public void testDoGet_valid_administrator_not_remember_referer_car_list() throws Exception {
        when(request.getParameter("login")).thenReturn("a");
        when(request.getParameter("password")).thenReturn("a");
        when(request.getHeader("Referer")).thenReturn("localhost:8080/car_list");
        when(StoreAndCookieUtil.getStoredConnection(request)).thenReturn(connection);
        when(request.getSession()).thenReturn(session);
        when(StoreAndCookieUtil.getStoredDAOFactory(request.getSession())).thenReturn(daoFactory);
        when(daoFactory.getAdministratorDao(connection)).thenReturn(administratorDAO);
        when(administratorDAO.getByLoginAndPassword("a", PasswordHelper.getSecurePassword("a"))).thenReturn(administratorDomain);
        servlet.doGet(request, response);
        verify(session).setAttribute("loginedAdmin", administratorDomain.getAdministrator());
        verify(response).addCookie(captor.capture());
        assertEquals(captor.getValue().getValue(), null);
        verify(response).sendRedirect("localhost:8080/car_list");
    }

    @Test
    // Test: login = "a" & password = "a" & rememberMe = "Y" & database response - administratorDomain (valid)
    // Request header contains "Referer" to "localhost:8080/logout"
    public void testDoGet_valid_administrator_is_remember_referer_car_list() throws Exception {
        when(request.getParameter("login")).thenReturn("a");
        when(request.getParameter("password")).thenReturn("a");
        when(request.getParameter("rememberMe")).thenReturn("Y");
        when(request.getHeader("Referer")).thenReturn("localhost:8080/logout");
        when(StoreAndCookieUtil.getStoredConnection(request)).thenReturn(connection);
        when(request.getSession()).thenReturn(session);
        when(StoreAndCookieUtil.getStoredDAOFactory(request.getSession())).thenReturn(daoFactory);
        when(daoFactory.getAdministratorDao(connection)).thenReturn(administratorDAO);
        when(administratorDAO.getByLoginAndPassword("a", PasswordHelper.getSecurePassword("a"))).thenReturn(administratorDomain);
        when(request.getContextPath()).thenReturn("localhost:8080");
        servlet.doGet(request, response);
        verify(response).addCookie(captor.capture());
        assertEquals(captor.getValue().getValue(), administratorDomain.getAdministrator().getLogin());
        verify(response).sendRedirect("localhost:8080/home");
    }

    @Test
    // Test: login = null & password = null
    public void testDoPost_null_login_and_password() throws Exception {
        when(request.getParameter("login")).thenReturn(null);
        when(request.getParameter("password")).thenReturn(null);
        when(request.getHeader("Referer")).thenReturn(null);
        when(request.getContextPath()).thenReturn("localhost:8080");
        servlet.doPost(request, response);
        verify(response).sendRedirect(request.getContextPath() + "/home");
    }

    @Test
    // Test: login = "" & password = ""
    public void testDoPost_login_and_password_is_empty() throws Exception {
        when(request.getParameter("login")).thenReturn("");
        when(request.getParameter("password")).thenReturn("");
        when(request.getHeader("Referer")).thenReturn("localhost:8080/doLogin");
        when(request.getRequestDispatcher("/WEB-INF/views/loginView.jsp")).thenReturn(requestDispatcher);
        servlet.doPost(request, response);
        verify(request).setAttribute("errorString", "Required login and password!");
        verify(request).setAttribute("admin", new Administrator(0L, null, null, null, "", ""));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    // Test: login = null & password = ""
    public void testDoPost_login_null_and_password_is_empty() throws Exception {
        when(request.getParameter("login")).thenReturn(null);
        when(request.getParameter("password")).thenReturn("");
        when(request.getHeader("Referer")).thenReturn("localhost:8080/car_list");
        when(request.getRequestDispatcher("/WEB-INF/views/loginView.jsp")).thenReturn(requestDispatcher);
        servlet.doPost(request, response);
        verify(request).setAttribute("errorString", "Required login and password!");
        verify(request).setAttribute("admin", new Administrator(0L, null, null, null, "", ""));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    // Test: login = "" & password = null
    public void testDoPost_login_is_empty_and_password_null() throws Exception {
        when(request.getParameter("login")).thenReturn("");
        when(request.getParameter("password")).thenReturn(null);
        when(request.getHeader("Referer")).thenReturn("localhost:8080/car_list");
        when(request.getRequestDispatcher("/WEB-INF/views/loginView.jsp")).thenReturn(requestDispatcher);
        servlet.doPost(request, response);
        verify(request).setAttribute("errorString", "Required login and password!");
        verify(request).setAttribute("admin", new Administrator(0L, null, null, null, "", ""));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    // Test: login = "" & password = "a"
    public void testDoPost_only_login_is_empty() throws Exception {
        when(request.getParameter("login")).thenReturn("");
        when(request.getParameter("password")).thenReturn("a");
        when(request.getHeader("Referer")).thenReturn("localhost:8080/doLogin");
        when(request.getRequestDispatcher("/WEB-INF/views/loginView.jsp")).thenReturn(requestDispatcher);
        servlet.doPost(request, response);
        verify(request).setAttribute("errorString", "Required login and password!");
        verify(request).setAttribute("admin", new Administrator(0L, null, null, null, "", "a"));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    // Test: login = "a" & password = "a" & database response - null
    public void testDoPost_DB_returns_null() throws Exception {
        when(request.getParameter("login")).thenReturn("a");
        when(request.getParameter("password")).thenReturn("a");
        when(StoreAndCookieUtil.getStoredConnection(request)).thenReturn(connection);
        when(request.getSession()).thenReturn(session);
        when(StoreAndCookieUtil.getStoredDAOFactory(request.getSession())).thenReturn(daoFactory);
        when(daoFactory.getAdministratorDao(connection)).thenReturn(administratorDAO);
        when(administratorDAO.getByLoginAndPassword("a", PasswordHelper.getSecurePassword("a"))).thenReturn(null);
        when(request.getRequestDispatcher("/WEB-INF/views/loginView.jsp")).thenReturn(requestDispatcher);
        servlet.doPost(request, response);
        verify(request).setAttribute("errorString", "Login or password invalid");
        verify(request).setAttribute("admin", new Administrator(0L, null, null, null, "a", "a"));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    // Test: login = "a" & password = "a" & database throws SQLException
    public void testDoPost_DB_throws_exception() throws Exception {
        when(request.getParameter("login")).thenReturn("a");
        when(request.getParameter("password")).thenReturn("a");
        when(StoreAndCookieUtil.getStoredConnection(request)).thenReturn(connection);
        when(request.getSession()).thenReturn(session);
        when(StoreAndCookieUtil.getStoredDAOFactory(request.getSession())).thenReturn(daoFactory);
        when(daoFactory.getAdministratorDao(connection)).thenReturn(administratorDAO);
        when(administratorDAO.getByLoginAndPassword("a", PasswordHelper.getSecurePassword("a"))).thenThrow(sqlException);
        when(request.getRequestDispatcher("/WEB-INF/views/loginView.jsp")).thenReturn(requestDispatcher);
        servlet.doPost(request, response);
        verify(request).setAttribute("javax.servlet.error.exception", sqlException);
        verify(request).setAttribute("javax.servlet.error.status_code", 500);
        verify(response).setStatus(500);
        verify(request).setAttribute("errorString", "Sorry... Data base does not respond.");
        verify(request).setAttribute("admin", new Administrator(0L, null, null, null, "a", "a"));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    // Test: login = "a" & password = "a" & database response - administratorDomain (valid)
    // Request header contains "Referer" to "localhost:8080/login"
    public void testDoPost_valid_administrator_referer_login() throws Exception {
        when(request.getParameter("login")).thenReturn("a");
        when(request.getParameter("password")).thenReturn("a");
        when(request.getHeader("Referer")).thenReturn("localhost:8080/login");
        when(StoreAndCookieUtil.getStoredConnection(request)).thenReturn(connection);
        when(request.getSession()).thenReturn(session);
        when(StoreAndCookieUtil.getStoredDAOFactory(request.getSession())).thenReturn(daoFactory);
        when(daoFactory.getAdministratorDao(connection)).thenReturn(administratorDAO);
        when(administratorDAO.getByLoginAndPassword("a", PasswordHelper.getSecurePassword("a"))).thenReturn(administratorDomain);
        when(request.getContextPath()).thenReturn("localhost:8080");
        servlet.doPost(request, response);
        verify(session).setAttribute("loginedAdmin", administratorDomain.getAdministrator());
        verify(response).sendRedirect("localhost:8080/home");
    }

    @Test
    // Test: login = "a" & password = "a" & rememberMe = "" & database response - administratorDomain (valid)
    // Request header contains "Referer" to "localhost:8080/car_list"
    public void testDoPost_valid_administrator_not_remember_referer_car_list() throws Exception {
        when(request.getParameter("login")).thenReturn("a");
        when(request.getParameter("password")).thenReturn("a");
        when(request.getHeader("Referer")).thenReturn("localhost:8080/car_list");
        when(StoreAndCookieUtil.getStoredConnection(request)).thenReturn(connection);
        when(request.getSession()).thenReturn(session);
        when(StoreAndCookieUtil.getStoredDAOFactory(request.getSession())).thenReturn(daoFactory);
        when(daoFactory.getAdministratorDao(connection)).thenReturn(administratorDAO);
        when(administratorDAO.getByLoginAndPassword("a", PasswordHelper.getSecurePassword("a"))).thenReturn(administratorDomain);
        servlet.doPost(request, response);
        verify(session).setAttribute("loginedAdmin", administratorDomain.getAdministrator());
        verify(response).addCookie(captor.capture());
        assertEquals(captor.getValue().getValue(), null);
        verify(response).sendRedirect("localhost:8080/car_list");
    }

    @Test
    // Test: login = "a" & password = "a" & rememberMe = "Y" & database response - administratorDomain (valid)
    // Request header contains "Referer" to "localhost:8080/doLogin"
    public void testDoPost_valid_administrator_is_remember_referer_car_list() throws Exception {
        when(request.getParameter("login")).thenReturn("a");
        when(request.getParameter("password")).thenReturn("a");
        when(request.getParameter("rememberMe")).thenReturn("Y");
        when(request.getHeader("Referer")).thenReturn("localhost:8080/doLogin");
        when(StoreAndCookieUtil.getStoredConnection(request)).thenReturn(connection);
        when(request.getSession()).thenReturn(session);
        when(StoreAndCookieUtil.getStoredDAOFactory(request.getSession())).thenReturn(daoFactory);
        when(daoFactory.getAdministratorDao(connection)).thenReturn(administratorDAO);
        when(administratorDAO.getByLoginAndPassword("a", PasswordHelper.getSecurePassword("a"))).thenReturn(administratorDomain);
        when(request.getContextPath()).thenReturn("localhost:8080");
        servlet.doPost(request, response);
        verify(response).addCookie(captor.capture());
        assertEquals(captor.getValue().getValue(), administratorDomain.getAdministrator().getLogin());
        verify(response).sendRedirect("localhost:8080/home");
    }

}