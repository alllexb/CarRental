package ua.kiev.allexb.carrental.data.dao;

import org.apache.log4j.Logger;
import ua.kiev.allexb.carrental.data.domain.CarDomain;
import ua.kiev.allexb.carrental.data.service.DataBaseUtil;
import ua.kiev.allexb.carrental.model.Car;
import ua.kiev.allexb.carrental.utils.ApplicationLogger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author allexb
 * @version 1.0 02.08.2016
 */
public class CarDAOImpl implements CarDAO {

    static final Logger logger = ApplicationLogger.getLogger(CarDAO.class);

    private Connection connection;
    private Statement statement;

    public CarDAOImpl() {
    }

    public CarDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    private List<CarDomain> getItems(String query, int amount) throws SQLException {
        ResultSet resultSet = null;
        List<CarDomain> cars = new ArrayList<>();
        try {
            if (connection == null) throw new SQLException("No connection to database.");
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            if (resultSet != null) {
                for (int i = 0; i < amount & resultSet.next(); i++) {
                    CarDomain car = new CarDomain(resultSet.getLong("id"),
                            resultSet.getString("number_plate"),
                            resultSet.getString("model"),
                            Car.Color.valueOf(resultSet.getString("color") == null ? "NAN" : resultSet.getString("color")),
                            resultSet.getString("description"),
                            resultSet.getInt("year_of_manufacture"),
                            resultSet.getBigDecimal("rental_price"),
                            resultSet.getBoolean("rented"));
                    cars.add(car);
                }
            }
        } finally {
            DataBaseUtil.closeResultSet(resultSet);
            DataBaseUtil.closeStatement(statement);
        }
        logger.info("Get data query occurred.");
        return cars;
    }

    public CarDomain getByModel(String model) throws SQLException {
        String query = "SELECT * FROM car_tb WHERE model='" + model + "'";
        List<CarDomain> cars = getItems(query, ONE);
        return cars.isEmpty() ? null : cars.get(0);
    }

    @Override
    public CarDomain getByNumberPlate(String numberPlate) throws SQLException {
        String query = "SELECT * FROM car_tb WHERE number_plate='" + numberPlate + "'";
        List<CarDomain> cars = getItems(query, ONE);
        return cars.isEmpty() ? null : cars.get(0);
    }

    public List<CarDomain> getAll() throws SQLException {
        String query = "SELECT * FROM car_tb";
        return getItems(query, ALL);
    }

    public CarDomain getById(Long id) throws SQLException {
        String query = "SELECT * FROM car_tb WHERE id=" + id;
        List<CarDomain> cars = getItems(query, ONE);
        return cars.isEmpty() ? null : cars.get(0);
    }

    private void dataChangeQuery(String query, CarDomain car) throws SQLException {
        if (connection == null) throw new SQLException("No connection to database.");
        statement = connection.prepareStatement(query);
        try {
            ((PreparedStatement)statement).setString(1, car.getNumberPlate());
            ((PreparedStatement)statement).setString(2, car.getModel());
            ((PreparedStatement)statement).setString(3, car.getColor().name());
            ((PreparedStatement)statement).setString(4, car.getDescription());
            ((PreparedStatement)statement).setInt(5, car.getYearOfManufacture());
            ((PreparedStatement)statement).setBigDecimal(6, car.getRentalPrice());
            ((PreparedStatement)statement).setBoolean(7, car.isRented());
            int items = ((PreparedStatement)statement).executeUpdate();
            if (items == 0) logger.info("No entities changed.");
        } catch (Exception ex) {
            logger.info("Fail in data base changing.", ex);
            throw new SQLException(ex);
        }finally {
            DataBaseUtil.closeStatement(statement);
        }
        logger.info("Add or change data query occurred.");
    }

    public void add(CarDomain car) throws SQLException {
        String query = "INSERT INTO car_tb(number_plate, model, color, description, year_of_manufacture, rental_price, rented)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?)";
        this.dataChangeQuery(query, car);
    }

    public void update(CarDomain car) throws SQLException {
        String query = "UPDATE car_tb SET " +
                "number_plate=?, model=?, color=?, description=?, year_of_manufacture=?, rental_price=?, rented=? " +
                "WHERE id=" + car.getId();
        this.dataChangeQuery(query, car);
    }

    public void remove(CarDomain car) throws SQLException {
        String query = "DELETE FROM car_tb WHERE id=" + car.getId() + " AND  number_plate=? AND " +
                "model=? AND color=? AND description=? AND year_of_manufacture=? AND rental_price=? AND " +
                "rented=?";
        this.dataChangeQuery(query, car);
    }
}
