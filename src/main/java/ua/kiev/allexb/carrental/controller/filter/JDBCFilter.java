package ua.kiev.allexb.carrental.controller.filter;

import org.apache.log4j.Logger;
import ua.kiev.allexb.carrental.data.service.DataBaseUtil;
import ua.kiev.allexb.carrental.utils.ApplicationLogger;
import ua.kiev.allexb.carrental.utils.StoreAndCookieUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

/**
 * @author allexb
 * @version 1.0 18.08.2016
 */
@WebFilter(filterName = "jdbcFilter", urlPatterns = {"/*"})
public class JDBCFilter implements Filter {

    static final Logger logger = ApplicationLogger.getLogger(JDBCFilter.class);

    public JDBCFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    private boolean needJDBC(HttpServletRequest request) {
        logger.info("JDBC Filter");
        String servletPath = request.getServletPath();
        String pathInfo = request.getPathInfo();
        String urlPattern = servletPath;
        if (pathInfo != null) {
            urlPattern = servletPath + "/*";
        }

        Map<String, ? extends ServletRegistration> servletRegistrations = request.getServletContext().getServletRegistrations();

        Collection<? extends ServletRegistration> values = servletRegistrations.values();
        for (ServletRegistration registration : values) {
            Collection<String> mappings = registration.getMappings();
            if (mappings.contains(urlPattern)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (this.needJDBC(httpRequest)) {
            logger.info("Open Connection for: " + httpRequest.getServletPath());
            Connection connection = null;
            try {
                connection = DataBaseUtil.getConnection();
                connection.setAutoCommit(false);
                StoreAndCookieUtil.storeConnection(request, connection);
                chain.doFilter(request, response);
            } catch (SQLException ex) {
                logger.warn("Data base failed!", ex);
                chain.doFilter(request, response);
            } catch (Exception ex) {
                logger.warn("Connection exception!", ex);
                DataBaseUtil.rollbackConnection(connection);
                throw new ServletException(ex);
            } finally {
                DataBaseUtil.closeConnection(connection);
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
