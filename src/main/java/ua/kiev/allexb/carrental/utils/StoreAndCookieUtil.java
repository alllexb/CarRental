package ua.kiev.allexb.carrental.utils;

import org.apache.log4j.Logger;
import ua.kiev.allexb.carrental.model.Administrator;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;

/**
 * @author allexb
 * @version 1.0 11.08.2016
 */
public class StoreAndCookieUtil {

    static final Logger logger = Logger.getLogger(StoreAndCookieUtil.class);

    private static final int CONNECTION_LIVING_TIME = 30*60;
    private static final String ATTRIBUTE_NAME_ADMIN_NAME = "ATTRIBUTE_FOR_STORE_ADMIN_NAME_IN_COOKIE";
    public static final String ATTRIBUTE_NAME_CONNECTION = "ATTRIBUTE_FOR_CONNECTION";

    // Store Connection in request attribute.
    // (Information stored only exist during requests)
    public static void storeConnection(ServletRequest request, Connection connection) {
        request.setAttribute(ATTRIBUTE_NAME_CONNECTION, connection);
    }

    // Get the Connection object has been stored in one attribute of the request.
    public static Connection getStoredConnection(ServletRequest request) {
        Connection connection = (Connection) request.getAttribute(ATTRIBUTE_NAME_CONNECTION);
        return connection;
    }

    // Store user info in Session.
    public static void storeLoginedAdministrator(HttpSession session, Administrator loginedAdmin) {
        // On the JSP can access ${loginedAdmin}
        session.setAttribute("loginedAdmin", loginedAdmin);
    }


    // Get the user information stored in the session.
    public static Administrator getLoginedAdministrator(HttpSession session) {
        Administrator loginedAdmin = (Administrator) session.getAttribute("loginedAdmin");
        return loginedAdmin;
    }

    // Store info in Cookie
    public static void storeAdminCookie(HttpServletResponse response, Administrator administrator) {
        logger.info("Store administrator cookie");
        Cookie cookieAdminLogin = new Cookie(ATTRIBUTE_NAME_ADMIN_NAME, administrator.getLogin());
        // 1 day (Convert to seconds)
        cookieAdminLogin.setMaxAge(CONNECTION_LIVING_TIME);
        response.addCookie(cookieAdminLogin);
    }

    public static String getAdminLoginInCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (ATTRIBUTE_NAME_ADMIN_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    // Delete cookie.
    public static void deleteAdminCookie(HttpServletResponse response) {
        Cookie cookieUserName = new Cookie(ATTRIBUTE_NAME_ADMIN_NAME, null);
        // 0 seconds (Expires immediately)
        cookieUserName.setMaxAge(0);
        response.addCookie(cookieUserName);
    }
}
