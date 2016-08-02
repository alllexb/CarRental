package ua.kiev.allexb.carrental.data.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author allexb
 * @version 1.0 02.08.2016
 */
public class ConnectionFactory {
    // database properties initialization
    public static final String PATH_TO_PROPERTIES = System.getProperty("user.dir") + ".git/src/main/java/ua/kiev/allexb/carrental/data/service/db.properties";
    private static final Properties properties;
    static {
        InputStream inputStream;
        Properties prop = new Properties();
        try {
            inputStream = new FileInputStream(PATH_TO_PROPERTIES);
            prop.load(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        properties = prop;
    }

    //static reference to itself
    private static ConnectionFactory instance = new ConnectionFactory();

    //created connection
    private static Connection dbConnection = null;

    //private constructor
    private ConnectionFactory() {
        try {
            String dbDriver = properties.isEmpty()? null: properties.getProperty("dbDriver");
            Class.forName(dbDriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection createConnection() {
        Connection connection = null;
        try {
            if (properties.isEmpty()) {
                System.out.println("ERROR: Database properties could not load..");
            } else {
                String connectionUrl = properties.getProperty("connectionUrl");
                String userName = properties.getProperty("userName");
                String password = properties.getProperty("password");
                connection = DriverManager.getConnection(connectionUrl, userName, password);
            }
        } catch (SQLException e) {
            System.out.println("ERROR: Unable to Connect to Database.");
        }
        return connection;
    }

    public static Connection getConnection() {
        if (dbConnection == null)
            dbConnection = instance.createConnection();
        return dbConnection;
    }
}
