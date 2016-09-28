package ua.kiev.allexb.carrental.data.dao;

import ua.kiev.allexb.carrental.data.domain.CarDomain;
import ua.kiev.allexb.carrental.data.domain.ClientDomain;
import ua.kiev.allexb.carrental.data.domain.OrderDomain;

import java.sql.SQLException;
import java.util.List;

/**
 * @author allexb
 * @version 1.0 23.07.2016
 */
public interface OrderDAO extends AbstractDAO<OrderDomain> {

    List<OrderDomain> getByClient(ClientDomain client) throws SQLException;

    OrderDomain getByCar(CarDomain car) throws SQLException;

}
