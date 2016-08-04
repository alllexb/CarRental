package ua.kiev.allexb.carrental.data.dao;

import ua.kiev.allexb.carrental.data.domain.CarDomain;
import ua.kiev.allexb.carrental.data.service.ConnectionFactory;
import ua.kiev.allexb.carrental.data.service.DbUtil;
import ua.kiev.allexb.carrental.model.Car;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author allexb
 * @version 1.0 02.08.2016
 */
public class CarDAOImpl implements CarDAO {

    private static final int ONE = 1;
    private static final int ALL = Integer.MAX_VALUE;

    private Connection connection;
    private Statement statement;

    private List<CarDomain> getItems(String query, int amount) {
        ResultSet resultSet = null;
        List<CarDomain> cars = new ArrayList<CarDomain>();
        try {
            connection = ConnectionFactory.getConnection();
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery(query);
                if (resultSet!= null) {
                    for(int i = 0; i < amount & resultSet.next(); i++) {
                        CarDomain car = new CarDomain(resultSet.getLong("id"),
                                resultSet.getString("model"),
                                Car.Colour.valueOf(resultSet.getString("color")),
                                resultSet.getString("description"),
                                resultSet.getInt("year_of_manufacture"),
                                resultSet.getBigDecimal("rental_price"),
                                resultSet.getBoolean("rented"));
                        cars.add(car);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            DbUtil.close(resultSet);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return cars;
    }

    public CarDomain getByModel(String model) {
        String query = "SELECT * FROM car_tb WHERE model='" + model + "'";
        List<CarDomain> cars = getItems(query,ONE);
        return cars.isEmpty() ? null : cars.get(0);
    }

    public List<CarDomain> getAll() {
        String query = "SELECT * FROM car_tb";
        return getItems(query, ALL);
    }

    public CarDomain getById(Long id) {
        String query = "SELECT * FROM car_tb WHERE id=" + id;
        List<CarDomain> cars = getItems(query,ONE);
        return cars.isEmpty() ? null : cars.get(0);
    }

    private void dataChangeQuery(String query) {
        try {
            connection = ConnectionFactory.getConnection();
            try {
                statement = connection.createStatement();
                statement.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
    }

    public void add(CarDomain car) {
        String query = "INSERT INTO car_tb VALUES (" +
                "LAST_INSERT_ID(), '" + car.getModel() + "', '" + car.getColour() + "', '" + car.getDescription() +
                "', " + car.getYearOfManufacture() + ", " + car.getRentalPrice() + ", " + car.isRented() + ")";
        this.dataChangeQuery(query);
    }

    public void update(CarDomain car) {
        String query = "UPDATE car_tb SET " +
                "model='" + car.getModel() + "', " +
                "color='" + car.getColour() + "', " +
                "description='" + car.getDescription() + "', " +
                "year_of_manufacture=" + car.getYearOfManufacture() + ", " +
                "rental_price=" + car.getRentalPrice() + ", " +
                "rented=" + car.isRented() + " WHERE id=" + car.getId();
        this.dataChangeQuery(query);
    }

    public void remove(CarDomain car) {
        String query = "DELETE FROM car_tb WHERE id=" + car.getId() + " AND " +
                "model='" + car.getModel() + "' AND " +
                "color='" + car.getColour() + "' AND " +
                "description='" + car.getDescription() + "' AND " +
                "year_of_manufacture=" + car.getYearOfManufacture() + " AND " +
                "rental_price=" + car.getRentalPrice() + " AND " +
                "rented=" + car.isRented();
        this.dataChangeQuery(query);
    }
}
