package ua.kiev.allexb.carrental.controller.validator;

import org.apache.log4j.Logger;
import ua.kiev.allexb.carrental.model.Car;
import ua.kiev.allexb.carrental.utils.ApplicationLogger;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * @author allexb
 * @version 1.0 01.09.2016
 */
public class CarValidator extends Validator<Car> {

    static final Logger logger = ApplicationLogger.getLogger(CarValidator.class);
    private Car car = new Car();

    public CarValidator(HttpServletRequest request) {
        super(request);
    }

    public CarValidator(HttpServletRequest request, Car car) {
        super(request);
        this.car = car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public boolean validate() {
        boolean error = false;
        logger.info("Car with ID:" + car.getId() + " prepared to validation.");
        String numberPlate = request.getParameter("numberPlate") != null ? request.getParameter("numberPlate") : "";
        car.setNumberPlate(numberPlate);
        if (numberPlate.isEmpty()) {
            message += "Car always must have number plate. Entered it, please. ";
            error = true;
        }
        String model = request.getParameter("model") != null ? request.getParameter("model") : "";
        car.setModel(model);
        try {
            ua.kiev.allexb.carrental.model.Car.Color color = request.getParameter("color") != null && !request.getParameter("color").isEmpty() ? ua.kiev.allexb.carrental.model.Car.Color.valueOf(request.getParameter("color")) : ua.kiev.allexb.carrental.model.Car.Color.NAN;
            car.setColor(color);
        } catch (IllegalArgumentException ex) {
            message += "Choose correct color. ";
            error = true;
            logger.warn(ex);
        }
        String description = request.getParameter("description") != null ? request.getParameter("description") : "";
        car.setDescription(description);
        try {
            int yearOfManufacture = request.getParameter("yearOfManufacture") != null && !request.getParameter("yearOfManufacture").isEmpty() ? Integer.valueOf(request.getParameter("yearOfManufacture")) : 0;
            car.setYearOfManufacture(yearOfManufacture);
        } catch (IllegalArgumentException ex) {
            message += "Set correct year of manufacture. ";
            error = true;
            logger.warn(ex);
        }
        try {
            BigDecimal rentalPrice = request.getParameter("rentalPrice") != null && !request.getParameter("rentalPrice").isEmpty() ? BigDecimal.valueOf(Double.valueOf(request.getParameter("rentalPrice"))) : BigDecimal.ZERO;
            car.setRentalPrice(rentalPrice);
        } catch (IllegalArgumentException ex) {
            message += "Set correct rental price. ";
            error = true;
            logger.warn(ex);
        }
        boolean rented = Boolean.valueOf(request.getParameter("rented"));
        car.setRented(rented);
        logger.info("Data validation completed.");
        return !error;
    }

    @Override
    public Car getValue() {
        return car;
    }
}
