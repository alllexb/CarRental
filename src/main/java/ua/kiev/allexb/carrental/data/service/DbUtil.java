package ua.kiev.allexb.carrental.data.service;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

/**
 * @author allexb
 * @version 1.0 02.08.2016
 */
public class DbUtil {

    static final Logger logger = Logger.getLogger(DbUtil.class);

    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Connection didn't close.");
                logger.error(Arrays.toString(e.getStackTrace()));
            }
        }
    }

    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.error("Statement didn't close.");
                logger.error(Arrays.toString(e.getStackTrace()));
            }
        }
    }

    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                logger.error("Result Set didn't close.");
                logger.error(Arrays.toString(e.getStackTrace()));
            }
        }
    }
}
