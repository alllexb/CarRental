package ua.kiev.allexb.carrental;

import ua.kiev.allexb.carrental.data.dao.CarDAOImpl;
import ua.kiev.allexb.carrental.data.domain.CarDomain;
import ua.kiev.allexb.carrental.data.service.ConnectionFactory;
import ua.kiev.allexb.carrental.model.Car;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        Car carFromDB = carDAO.getById(2L).getCar();

        List<Car> cars = new ArrayList<Car>();
        for(CarDomain domain: carDAO.getAll()) {
            cars.add(domain.getCar());
        }

        System.out.println(carFromDB);
        System.out.println("----");
        System.out.println(Arrays.toString(cars.toArray()));

        carFromDB = carDAO.getByModel("Ferrari").getCar();

        System.out.println(carFromDB);
    }
}
