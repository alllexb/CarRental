package ua.kiev.allexb.carrental.controller.filter;

import org.apache.log4j.Logger;
import ua.kiev.allexb.carrental.model.Administrator;
import ua.kiev.allexb.carrental.utils.ApplicationLogger;
import ua.kiev.allexb.carrental.utils.StoreAndCookieUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author allexb
 * @version 1.0 31.08.2016
 */
@WebFilter(filterName = "accessFilter")
public class AccessFilter implements Filter {

    static final Logger logger = ApplicationLogger.getLogger(AccessFilter.class);

    public AccessFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("Access Filter initialized.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("Access Filter called.");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        logger.info("Getting logined administrator.");
        Administrator loginedAdministrator = StoreAndCookieUtil.getLoginedAdministrator(httpRequest.getSession());
        if (loginedAdministrator == null) {
            logger.info("Request without authentication. Access denied.");
            String errorString;
            if(httpRequest.getServletPath().endsWith("/logout")) {
                errorString = "No logined administrator.";
            } else {
                errorString = "Access denied! Login previously.";
            }
            request.setAttribute("errorString", errorString);
            RequestDispatcher dispatcher = httpRequest.getServletContext().getRequestDispatcher("/WEB-INF/views/loginView.jsp");
            dispatcher.forward(request, response);
        } else {
            logger.info("Permit has been obtained.");
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
