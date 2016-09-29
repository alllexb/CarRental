package ua.kiev.allexb.carrental.data.dao;

import org.apache.log4j.Logger;
import ua.kiev.allexb.carrental.data.domain.CarDomain;
import ua.kiev.allexb.carrental.data.service.DataBaseUtil;
import ua.kiev.allexb.carrental.model.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author allexb
 * @version 1.0 02.08.2016
 */
public class CarDAOImpl implements CarDAO {

    static final Logger logger = Logger.getLogger(CarDAO.class);

    private Connection connection;

    public CarDAOImpl() {
    }

    public CarDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    private List<CarDomain> getItems(PreparedStatement statement) throws SQLException {
        ResultSet resultSet = null;
        List<CarDomain> cars = new ArrayList<>();
        try {
            if (connection == null) throw new SQLException("No connection to database.");
            resultSet = statement.executeQuery();
            if (resultSet != null) {
                while (resultSet.next()) {
                    CarDomain car = new CarDomain(resultSet.getLong("id"),
                            resultSet.getString("number_plate"),
                            resultSet.getString("model"),
                            Car.Color.valueOf(resultSet.getString("color") == null ? "NAN" : resultSet.getString("color")),
                            resultSet.getString("description"),
                            resultSet.getInt("year_of_manufacture"),
                            resultSet.getBigDecimal("rental_price"),
                            Car.Status.valueOf(resultSet.getString("status") == null ? "UNIDENTIFIED" : resultSet.getString("status")));
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
        String query = "SELECT * FROM car_tb WHERE model=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, model);
        List<CarDomain> cars = getItems(statement);
        return cars.isEmpty() ? null : cars.get(0);
    }

    @Override
    public CarDomain getByNumberPlate(String numberPlate) throws SQLException {
        String query = "SELECT * FROM car_tb WHERE number_plate=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, numberPlate);
        List<CarDomain> cars = getItems(statement);
        return cars.isEmpty() ? null : cars.get(0);
    }

    public List<CarDomain> getAll() throws SQLException {
        String query = "SELECT * FROM car_tb";
        PreparedStatement statement = connection.prepareStatement(query);
        return getItems(statement);
    }

    public CarDomain getById(Long id) throws SQLException {
        String query = "SELECT * FROM car_tb WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, id);
        List<CarDomain> cars = getItems(statement);
        return cars.isEmpty() ? null : cars.get(0);
    }

    private void dataChangeQuery(PreparedStatement statement) throws SQLException {
        if (connection == null) throw new SQLException("No connection to database.");
        try {
            int items = statement.executeUpdate();
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
        String query = "INSERT INTO car_tb(number_plate, model, color, description, year_of_manufacture, rental_price, status)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, car.getNumberPlate());
        statement.setString(2, car.getModel());
        statement.setString(3, car.getColor().name());
        statement.setString(4, car.getDescription());
        statement.setInt(5, car.getYearOfManufacture());
        statement.setBigDecimal(6, car.getRentalPrice());
        statement.setString(7, car.getStatus().name());
        this.dataChangeQuery(statement);
    }

    public void update(CarDomain car) throws SQLException {
        String query = "UPDATE car_tb SET " +
                "number_plate=?, model=?, color=?, description=?, year_of_manufacture=?, rental_price=?, status=?" +
                "WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, car.getNumberPlate());
        statement.setString(2, car.getModel());
        statement.setString(3, car.getColor().name());
        statement.setString(4, car.getDescription());
        statement.setInt(5, car.getYearOfManufacture());
        statement.setBigDecimal(6, car.getRentalPrice());
        statement.setString(7, car.getStatus().name());
        statement.setLong(8, car.getId());
        this.dataChangeQuery(statement);
    }

    public void remove(CarDomain car) throws SQLException {
        String query = "DELETE FROM car_tb WHERE id=? AND  number_plate=? AND " +
                "model=? AND color=? AND description=? AND year_of_manufacture=? AND rental_price=? AND status=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, car.getId());
        statement.setString(2, car.getNumberPlate());
        statement.setString(3, car.getModel());
        statement.setString(4, car.getColor().name());
        statement.setString(5, car.getDescription());
        statement.setInt(6, car.getYearOfManufacture());
        statement.setBigDecimal(7, car.getRentalPrice());
        statement.setString(8, car.getStatus().name());
        this.dataChangeQuery(statement);
    }
}
