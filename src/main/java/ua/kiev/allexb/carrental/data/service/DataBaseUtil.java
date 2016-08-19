package ua.kiev.allexb.carrental.data.service;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author allexb
 * @version 1.0 02.08.2016
 */
public class DataBaseUtil {

    static final Logger logger = Logger.getLogger(DataBaseUtil.class);
    private static ConnectionFactory connectionFactory = ConnectionFactory.getInstance();

    public static Connection getConnection() throws SQLException {
        logger.info("Connection has taken.");
        return connectionFactory.getConnection();
    }

    public static void rollbackConnection(Connection connection) {
        if (connection != null) {
            try {
                logger.info("Connection has rolled back.");
                connection.rollback();
            } catch (SQLException ex) {
                logger.error("Connection has not rolled back.", ex);
            }
        }
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                logger.info("Connection has closed.");
                connection.close();
            } catch (SQLException ex) {
                logger.error("Connection has not closed.", ex);
            }
        }
    }

    public static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                logger.info("Statement has closed.");
                statement.close();
            } catch (SQLException ex) {
                logger.error("Statement has not closed.", ex);
            }
        }
    }

    public static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                logger.info("Result Set has closed.");
                resultSet.close();
            } catch (SQLException ex) {
                logger.error("Result Set has not closed.", ex);
            }
        }
    }
}
