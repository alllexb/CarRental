package ua.kiev.allexb.carrental.controller.listener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import ua.kiev.allexb.carrental.data.dao.DAOFactory;
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
//        try {
//            Class loadClass = Class.forName(context.getInitParameter("DAO-Factory-class"));
//            if (loadClass.newInstance() instanceof DAOFactory) {
//                DAOFactory dbDAOFactory = (DAOFactory) loadClass.newInstance();
//                DAOFactory.setFactoryStrategy(dbDAOFactory);
//                logger.info(loadClass.getName() + " has been initialized.");
//            }
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
//            logger.fatal("DAO Factory has not been initialized. Check up \"DAO-Factory-class\" context parameter. Its value: " + context.getInitParameter("DAO-Factory-class"), ex);
//        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
    }
}
