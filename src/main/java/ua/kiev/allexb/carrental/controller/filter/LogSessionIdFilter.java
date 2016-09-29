package ua.kiev.allexb.carrental.controller.filter;

import org.apache.log4j.MDC;
import ua.kiev.allexb.carrental.model.Administrator;
import ua.kiev.allexb.carrental.utils.StoreAndCookieUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author allexb
 * @version 1.0 29.09.2016
 */
@WebFilter(filterName = "logSessionIdFilter")
public class LogSessionIdFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        if (session != null) {
            MDC.put("sessionID", session.getId());
            Administrator loginedAdministrator = StoreAndCookieUtil.getLoginedAdministrator(session);
            if (loginedAdministrator != null) {
                MDC.put("adminLogin", loginedAdministrator.getLogin());
            } else {
                MDC.put("adminLogin", "");
            }
        } else {
            MDC.put("sessionID", "");
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
