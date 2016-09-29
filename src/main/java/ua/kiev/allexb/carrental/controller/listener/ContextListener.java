package ua.kiev.allexb.carrental.controller.listener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import ua.kiev.allexb.carrental.utils.RootPathUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author allexb
 * @version 1.0 29.09.2016
 */
@WebListener("application context listener")
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.setProperty("app.root", RootPathUtil.getRootApplicationPath());
        ServletContext context = event.getServletContext();
        String log4jConfigFile = context.getInitParameter("log4j-config-location");
        String fullPath = RootPathUtil.getRootApplicationPath(log4jConfigFile);
        PropertyConfigurator.configure(fullPath);

        Logger logger = Logger.getLogger(ContextListener.class);
        logger.info("   ---==   START LOGGER SESSION (CONTEXT)   ==---");
        logger.info("\n\t\tProperty file location: " + fullPath);
        logger.info("Logger configured.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
    }
}
