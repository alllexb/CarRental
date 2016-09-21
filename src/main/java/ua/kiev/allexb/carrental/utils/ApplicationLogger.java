package ua.kiev.allexb.carrental.utils;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author allexb
 * @version 1.0 11.08.2016
 */
public class ApplicationLogger {

    private static final String LOGGER_PROPERTY_FILE = RootPathUtil.getRootApplicationClassPath("log4j.properties");

    private static boolean init = false;

    public static Logger getLogger(Class clazz) {
        if (!init) {
            System.setProperty("app.root", RootPathUtil.getRootApplicationPath());
            PropertyConfigurator.configure(LOGGER_PROPERTY_FILE);
            Logger logger = Logger.getLogger(ApplicationLogger.class);
            logger.info("   ---==   START LOGGER SESSION   ==---");
            logger.info("\n\t\tProperty file location: " + LOGGER_PROPERTY_FILE);
            logger.info("Logger configured.");
            init = true;
        }
        return Logger.getLogger(clazz);
    }

}
