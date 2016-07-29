package ua.kiev.allexb.carrental;

import ua.kiev.allexb.carrental.model.entity.Car;

import java.math.BigDecimal;

/**
 * Created by bas on 29.07.2016.
 */
public class Main {
    public static void main(String[] args) {
        Car car = new Car();
        car.setCarId(1);
        car.setCarModel("Ferrari");
        car.setCarColour(Car.CarColour.BLACK);
        car.setYearOfManufacture(2012);
        car.setRentalPrice(new BigDecimal(2000d));
        car.setDescription("Very fast car!");

        System.out.println(car);
    }
}
