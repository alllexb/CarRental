package ua.kiev.allexb.carrental.data.dao;

import org.apache.log4j.Logger;
import ua.kiev.allexb.carrental.data.dao.util.DateUtil;
import ua.kiev.allexb.carrental.data.domain.CarDomain;
import ua.kiev.allexb.carrental.data.domain.ClientDomain;
import ua.kiev.allexb.carrental.data.domain.OrderDomain;
import ua.kiev.allexb.carrental.data.service.DataBaseUtil;
import ua.kiev.allexb.carrental.model.Car;
import ua.kiev.allexb.carrental.utils.ApplicationLogger;

import java.sql.*;
import java.util.*;

/**
 * @author allexb
 * @version 1.0 23.07.2016
 */

public class OrderDAOImpl implements OrderDAO {

    static final Logger logger = ApplicationLogger.getLogger(OrderDAO.class);

    private Connection connection;

    public OrderDAOImpl() {
    }

    public OrderDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }


    private List<OrderDomain> getItems(PreparedStatement statement) throws SQLException{
        ResultSet resultSet = null;
        List<OrderDomain> orders = new ArrayList<>();
        Map<Long, OrderDomain> orderMap = new HashMap<>();
        try {
            if (connection == null) throw new SQLException("No connection to database.");
            resultSet = statement.executeQuery();
            if (resultSet != null) {
                while(resultSet.next()) {
                    long orderId = resultSet.getLong("order_id");
                    OrderDomain order;
                    if (orderMap.containsKey(orderId)) {
                        order = orderMap.get(orderId);
                    } else {
                        order = new OrderDomain();
                        order.setId(orderId);
                        order.setCars(new HashSet<>());
                        order.setRentalStart(resultSet.getDate("rental_start"));
                        order.setRentalEnd(resultSet.getDate("rental_end"));
                        order.setClosed(resultSet.getBoolean("closed"));
                        orderMap.put(orderId, order);
                    }
                    if (order.getClient() == null) {
                        ClientDomain clientDomain = new ClientDomain(resultSet.getLong("client_id"),
                                resultSet.getString("first_name"),
                                resultSet.getString("last_name"),
                                resultSet.getDate("birthday"),
                                resultSet.getInt("dl_number"),
                                resultSet.getInt("length_of_driving_experience"));
                        order.setClient(clientDomain);
                    }
                    CarDomain car = new CarDomain(resultSet.getLong("car_id"),
                            resultSet.getString("number_plate"),
                            resultSet.getString("model"),
                            Car.Color.valueOf(resultSet.getString("color") == null ? "NAN" : resultSet.getString("color")),
                            resultSet.getString("description"),
                            resultSet.getInt("year_of_manufacture"),
                            resultSet.getBigDecimal("rental_price"),
                            Car.Status.valueOf(resultSet.getString("status") == null ? "UNIDENTIFIED" : resultSet.getString("status")));
                    order.getCars().add(car);
                }
            }
        } finally {
            DataBaseUtil.closeResultSet(resultSet);
            DataBaseUtil.closeStatement(statement);
        }
        logger.info("Get data query occurred.");
        orders.addAll(orderMap.values());
        return orders;
    }

    @Override
    public List<OrderDomain> getByClient(ClientDomain client) throws SQLException {
        String query = new StringBuilder()
                .append("SELECT orders.id AS `order_id`, clients.id AS `client_id`, clients.first_name, ")
                .append("clients.last_name, clients.birthday, clients.dl_number, clients.length_of_driving_experience, ")
                .append("cars.id AS `car_id`, cars.number_plate, cars.model, cars.color, cars.description, ")
                .append("cars.year_of_manufacture, cars.rental_price, cars.status, orders.rental_start, orders.rental_end, ")
                .append("orders.closed ")
                .append("  FROM `order_tb` AS orders, `client_tb` AS clients, `car_tb` AS cars")
                .append("    INNER JOIN `order_cars_tb` oct ON cars.id = oct.car_id")
                .append("    INNER JOIN `order_tb` ord ON oct.order_id = ord.id")
                .append("  WHERE ord.id=orders.id AND orders.client_id=clients.id AND clients.id=? AND clients.first_name=? ")
                .append("  AND clients.last_name=? AND clients.birthday=? AND clients.dl_number=?")
                .append("  AND clients.length_of_driving_experience=? ")
                .append("  ORDER BY order_id;").toString();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, client.getId());
        statement.setString(2, client.getFirstName());
        statement.setString(3, client.getLastName());
        statement.setDate(4, DateUtil.getSQLFormatDate(client.getBirthday()));
        statement.setInt(5, client.getdLNumber());
        statement.setInt(6, client.getLengthOfDrivingExperience());
        return this.getItems(statement);
    }

    @Override
    public OrderDomain getByCar(CarDomain car) throws SQLException {
        String query = new StringBuilder()
                .append("SELECT orders.id AS `order_id`, clients.id AS `client_id`, clients.first_name, ")
                .append("clients.last_name, clients.birthday, clients.dl_number, clients.length_of_driving_experience, ")
                .append("cars.id AS `car_id`, cars.number_plate, cars.model, cars.color, cars.description, ")
                .append("cars.year_of_manufacture, cars.rental_price, cars.status, orders.rental_start, orders.rental_end, ")
                .append("orders.closed ")
                .append("  FROM `order_tb` AS orders, `client_tb` AS clients, `car_tb` AS cars")
                .append("    INNER JOIN `order_cars_tb` oct ON cars.id = oct.car_id")
                .append("    INNER JOIN `order_tb` ord ON oct.order_id = ord.id")
                .append("  WHERE ord.id=orders.id AND orders.client_id=clients.id AND order_id=")
                .append("      (SELECT order_id FROM `order_cars_tb`")
                .append("      WHERE car_id = (SELECT id FROM `car_tb` WHERE id=? AND number_plate=? AND model=?")
                .append("                      AND color=? AND description=? AND year_of_manufacture=? AND rental_price=? ")
                .append("                      LIMIT 1))")
                .append("  ORDER BY order_id;").toString();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, car.getId());
        statement.setString(2, car.getNumberPlate());
        statement.setString(3, car.getModel());
        statement.setString(4, car.getColor().name());
        statement.setString(5, car.getDescription());
        statement.setInt(6, car.getYearOfManufacture());
        statement.setBigDecimal(7, car.getRentalPrice());
//        statement.setString(8, car.getStatus().name());
        List<OrderDomain> orderDomainList = this.getItems(statement);
        return orderDomainList.isEmpty() ? null: orderDomainList.get(0);
    }

    @Override
    public List<OrderDomain> getAll() throws SQLException {
        String query = new StringBuilder()
                .append("SELECT orders.id AS `order_id`, clients.id AS `client_id`, clients.first_name, ")
                .append("clients.last_name, clients.birthday, clients.dl_number, clients.length_of_driving_experience, ")
                .append("cars.id AS `car_id`, cars.number_plate, cars.model, cars.color, cars.description, ")
                .append("cars.year_of_manufacture, cars.rental_price, cars.status, orders.rental_start, orders.rental_end, ")
                .append("orders.closed ")
                .append("  FROM `order_tb` AS orders, `client_tb` AS clients, `car_tb` AS cars")
                .append("    INNER JOIN `order_cars_tb` oct ON cars.id = oct.car_id")
                .append("    INNER JOIN `order_tb` ord ON oct.order_id = ord.id")
                .append("  WHERE ord.id=orders.id AND orders.client_id=clients.id")
                .append("  ORDER BY order_id;").toString();
        PreparedStatement statement = connection.prepareStatement(query);
        return this.getItems(statement);
    }

    @Override
    public OrderDomain getById(Long id) throws SQLException {
        String query = new StringBuilder()
                .append("SELECT orders.id AS `order_id`, clients.id AS `client_id`, clients.first_name, ")
                .append("clients.last_name, clients.birthday, clients.dl_number, clients.length_of_driving_experience, ")
                .append("cars.id AS `car_id`, cars.number_plate, cars.model, cars.color, cars.description, ")
                .append("cars.year_of_manufacture, cars.rental_price, cars.status, orders.rental_start, orders.rental_end, ")
                .append("orders.closed ")
                .append("  FROM `order_tb` AS orders, `client_tb` AS clients, `car_tb` AS cars")
                .append("    INNER JOIN `order_cars_tb` oct ON cars.id = oct.car_id")
                .append("    INNER JOIN `order_tb` ord ON oct.order_id = ord.id")
                .append("  WHERE ord.id=orders.id AND orders.client_id=clients.id AND orders.id=?")
                .append("  ORDER BY order_id;").toString();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, id);
        List<OrderDomain> orderDomainList = this.getItems(statement);
        return orderDomainList.isEmpty() ? null: orderDomainList.get(0);
    }

    @Override
    public void add(OrderDomain order) throws SQLException {

    }

    @Override
    public void update(OrderDomain order) throws SQLException {

    }

    @Override
    public void remove(OrderDomain order) throws SQLException {

    }
}
