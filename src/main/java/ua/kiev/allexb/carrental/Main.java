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
import java.util.stream.Collectors;

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
        CarDomain dom;
        Car carFromDB = ((dom = carDAO.getById(2L)) == null) ? null : dom.getCar();

        System.out.println(carFromDB);
        System.out.println("----");

        List<Car> cars = carDAO.getAll().stream().map(CarDomain::getCar).collect(Collectors.toList());

        System.out.println(Arrays.toString(cars.toArray()));

        carFromDB = carDAO.getByModel("Ferrari").getCar();

        System.out.println(carFromDB);

        if ((dom = carDAO.getById(2L)) != null)
            carDAO.remove(dom);

        Car addedCar = new Car();
        addedCar.setModel("Citroen C-Elysee");
        addedCar.setColour(Car.Colour.BLACK);
        addedCar.setYearOfManufacture(2012);
        addedCar.setRentalPrice(new BigDecimal(500d));
        addedCar.setDescription("Comfortable car for you!");
        addedCar.setRented(false);

        carDAO.add(new CarDomain(addedCar));

        Car updateCar = addedCar;
        updateCar.setId(3L);
        updateCar.setRentalPrice(new BigDecimal(700D));
        carDAO.update(new CarDomain(updateCar));



    }
}
