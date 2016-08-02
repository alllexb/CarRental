package ua.kiev.allexb.carrental.data.dao;

import ua.kiev.allexb.carrental.data.domain.CarDomain;
import ua.kiev.allexb.carrental.data.service.ConnectionFactory;
import ua.kiev.allexb.carrental.data.service.DbUtil;
import ua.kiev.allexb.carrental.model.Car;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author allexb
 * @version 1.0 02.08.2016
 */
public class CarDAOImpl implements CarDAO {

    private Connection connection;
    private Statement statement;

    public CarDomain getByModel(String model) {
        return null;
    }

    public List<CarDomain> getAll() {
        return null;
    }

    public CarDomain getById(Long id) {
        String query = "SELECT * FROM car_tb WHERE id=" + id;
        ResultSet resultSet = null;
        CarDomain car = null;
        try {
            connection = ConnectionFactory.getConnection();
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery(query);
                if (resultSet.first()) {
                    car = new CarDomain(resultSet.getLong("id"),
                            resultSet.getString("model"),
                            Car.Colour.valueOf(resultSet.getString("color")),
                            resultSet.getString("description"),
                            resultSet.getInt("year_of_manufacture"),
                            resultSet.getBigDecimal("rental_price"),
                            resultSet.getBoolean("rented"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            DbUtil.close(resultSet);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return car;
    }

    public void add(CarDomain car) {

    }

    public void update(CarDomain car) {

    }

    public void remove(CarDomain car) {

    }
}
