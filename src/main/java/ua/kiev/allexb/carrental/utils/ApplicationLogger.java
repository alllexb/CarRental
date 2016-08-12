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
    private static final String LOGGER_PROPERTY_FILE = getRootApplicationPath("\\WEB-INF\\classes\\log4j.properties");
    private static boolean init = false;

    public static Logger getLogger(Class clazz) {
        if (!init) {
            System.setProperty("app.root", getRootApplicationPath(""));
            PropertyConfigurator.configure(LOGGER_PROPERTY_FILE);
            Logger logger = Logger.getLogger(ApplicationLogger.class);
            logger.info("   ---==   START LOGGER SESSION   ==---");
            logger.info("Logger configured.");
            init = true;
        }
        return Logger.getLogger(clazz);
    }

    private static String getRootApplicationPath(String fileName) {
        String path =  ApplicationLogger.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String fullPath = "";
        try {
            fullPath = URLDecoder.decode(path, "UTF-8");
            String[] pathArr = fullPath.split("/WEB-INF/classes/");
            fullPath = pathArr[0];
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        return new File(fullPath).getPath() + fileName;
    }
}
