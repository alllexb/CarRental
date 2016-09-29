package ua.kiev.allexb.carrental.controller.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author allexb
 * @version 1.0 13.08.2016
 */
@WebFilter(filterName = "encodingFilter", urlPatterns = { "/*" })
public class EncodingFilter implements Filter {

    static final Logger logger = Logger.getLogger(EncodingFilter.class);

    public EncodingFilter() {
    }

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        logger.info("Encoding Filter initialized.");
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        logger.info("Encoding Filter called.");
        request.setCharacterEncoding("UTF-8");
        chain.doFilter(request, response);
    }

}