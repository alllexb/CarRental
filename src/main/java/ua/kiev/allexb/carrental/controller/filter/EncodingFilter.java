package ua.kiev.allexb.carrental.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author allexb
 * @version 1.0 13.08.2016
 */
@WebFilter(filterName = "encodingFilter", urlPatterns = { "/*" })
public class EncodingFilter implements Filter {

    public EncodingFilter() {
    }

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        chain.doFilter(request, response);
    }

}