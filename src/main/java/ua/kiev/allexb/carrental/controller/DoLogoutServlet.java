package ua.kiev.allexb.carrental.controller;

import org.apache.log4j.Logger;
import ua.kiev.allexb.carrental.utils.StoreAndCookieUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author allexb
 * @version 1.0 19.08.2016
 */
@WebServlet(urlPatterns = {"/logout"})
public class DoLogoutServlet extends HttpServlet {

    static final Logger logger = Logger.getLogger(DoLogoutServlet.class);

    private static final long serialVersionUID = 8244392699971640663L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Started logout procedure.");
        StoreAndCookieUtil.deleteAdminCookie(response);
        StoreAndCookieUtil.storeLoginedAdministrator(request.getSession(), null);
        logger.info("Administrator logouted.");
        response.sendRedirect(request.getHeader("Referer"));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
