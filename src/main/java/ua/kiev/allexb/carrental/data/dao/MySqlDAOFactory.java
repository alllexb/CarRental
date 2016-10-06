package ua.kiev.allexb.carrental.data.dao;

import java.sql.Connection;

/**
 * @author allexb
 * @version 1.0 30.09.2016
 */
public class MySqlDAOFactory extends DAOFactory {

    @Override
    public AdministratorDAO getAdministratorDao(Connection connection) {
        return new AdministratorDAOImpl(connection);
    }

    @Override
    public CarDAO getCarDAO(Connection connection) {
        return new CarDAOImpl(connection);
    }

    @Override
    public ClientDAO getClientDao(Connection connection) {
        return new ClientDAOImpl(connection);
    }

    @Override
    public OrderDAO getOrderDAO(Connection connection) {
        return new OrderDAOImpl(connection);
    }
}
