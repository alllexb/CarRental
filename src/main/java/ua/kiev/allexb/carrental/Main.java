package ua.kiev.allexb.carrental;

import ua.kiev.allexb.carrental.data.dao.CarDAOImpl;
import ua.kiev.allexb.carrental.data.service.ConnectionFactory;
import ua.kiev.allexb.carrental.model.Car;

import java.math.BigDecimal;

/**
 * @author allexb
 * @version 1.0 29.07.2016
 */
public class Main {
    public static void main(String[] args) {
        Car car = new Car();
        car.setId(1);
        car.setModel("Ferrari");
        car.setColour(Car.Colour.BLACK);
        car.setYearOfManufacture(2012);
        car.setRentalPrice(new BigDecimal(2000d));
        car.setDescription("Very fast car!");
        car.setRented(true);

        System.out.println(car);

        CarDAOImpl carDAO = new CarDAOImpl();
        Car carFromDB = new Car(carDAO.getById(2L));

        System.out.println(carFromDB);

    }
}
