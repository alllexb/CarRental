package ua.kiev.allexb.carrental.data.service;

import java.io.*;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import ua.kiev.allexb.carrental.utils.ApplicationLogger;

/**
 * @author allexb
 * @version 1.0 02.08.2016
 */
public class ConnectionFactory {

    static final Logger logger = ApplicationLogger.getLogger(ConnectionFactory.class);

    // database properties file path
    private static final String PATH_TO_PROPERTIES = getPathToProperties("db.properties");
    private static final Properties properties;
    static {
        InputStream inputStream;
        Properties prop = new Properties();
        try {
            inputStream = new FileInputStream(PATH_TO_PROPERTIES);
            prop.load(inputStream);
            logger.info("Connection Factory Property loaded.\n\t\tProperty file location: " + PATH_TO_PROPERTIES);
        } catch (FileNotFoundException e) {
            logger.error("Property file \"dp.properties\" didn't found. Database main properties didn't initialize.");
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("IO exception during property file \"dp.properties\" loading. Database main properties didn't initialize.");
            e.printStackTrace();
        }
        properties = prop;
    }

    private static String getPathToProperties(String fileName) {
        String path =  ConnectionFactory.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String fullPath = "";
        try {
            fullPath = URLDecoder.decode(path, "UTF-8");
            String[] pathArr = fullPath.split("/WEB-INF/classes/");
            fullPath = pathArr[0];
        } catch (UnsupportedEncodingException ex) {
            logger.warn("Property file path encoding exception.");
        } catch (NullPointerException ex) {
            logger.warn("\"/WEB-INF/classes/\" directory not found.");
        }
        return new File(fullPath).getPath() + "\\WEB-INF\\classes\\" + fileName;
    }

    private static ConnectionFactory instance = new ConnectionFactory();
    private BasicDataSource ds;

    private ConnectionFactory() {
        if (properties.isEmpty()){
            logger.error("Property instance is empty. Database main properties didn't initialize. ConnectionFactory creation failed.");
            throw new PropertyLoadException("ConnectionFactory creation failed. Property didn't load.");
        }
        ds = new BasicDataSource();
        ds.setDriverClassName(properties.getProperty("dbDriver"));
        ds.setUsername(properties.getProperty("userName"));
        ds.setPassword(properties.getProperty("password"));
        ds.setUrl(properties.getProperty("connectionUrl"));

        ds.setMinIdle(Integer.parseInt(properties.getProperty("connection_pull.min_idle")));
        ds.setMaxIdle(Integer.parseInt(properties.getProperty("connection_pull.max_idle")));
        ds.setMaxOpenPreparedStatements(Integer.parseInt(properties.getProperty("connection_pull.max_open_prepared_statements")));
    }

    public static ConnectionFactory getInstance() {
        if (instance == null) {
            instance = new ConnectionFactory();
            return instance;
        } else {
            return instance;
        }
    }

    public Connection getConnection() throws SQLException {
        return this.ds.getConnection();
    }
}
