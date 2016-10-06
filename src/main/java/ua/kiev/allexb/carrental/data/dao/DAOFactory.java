package ua.kiev.allexb.carrental.data.dao;

import java.sql.Connection;

/**
 * @author allexb
 * @version 1.0 30.09.2016
 */
public abstract class DAOFactory {

    public static final int MYSQL = 1;

    public abstract AdministratorDAO getAdministratorDao(Connection connection);
    public abstract CarDAO getCarDAO(Connection connection);
    public abstract ClientDAO getClientDao(Connection connection);
    public abstract OrderDAO getOrderDAO(Connection connection);

    public static DAOFactory getDAOFactory(
            int whichFactory) {

        switch (whichFactory) {
            case 1:
                return new MySqlDAOFactory();
            default:
                return null;
        }
    }
}
