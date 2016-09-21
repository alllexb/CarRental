package ua.kiev.allexb.carrental.controller;

import org.apache.log4j.Logger;
import ua.kiev.allexb.carrental.model.Administrator;
import ua.kiev.allexb.carrental.model.Car;
import ua.kiev.allexb.carrental.model.Client;
import ua.kiev.allexb.carrental.model.helpers.PasswordHelper;
import ua.kiev.allexb.carrental.utils.ApplicationLogger;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

/**
 * @author allexb
 * @version 1.0 19.09.2016
 */
public class ModelExtractor {
    static final Logger logger = ApplicationLogger.getLogger(ModelExtractor.class);

    private HttpServletRequest request;
    private StringBuffer errorMessage;

    public ModelExtractor(HttpServletRequest request) {
        this.request = request;
    }

    public void setRequest(HttpServletRequest request) {
        errorMessage = new StringBuffer();
        this.request = request;
    }

    public String getErrorMessage() {
        return errorMessage.toString();
    }

    private boolean parameterCheckOut(HttpServletRequest request, String[] modelParameters) {
        Collection<String> collection = request.getParameterMap().keySet();
        for (String parameter: modelParameters) {
            if (!collection.contains(parameter)) {
                return false;
            }
        }
        return true;
    }

    public Car getCar() {
        logger.info("Make car model.");
        errorMessage = new StringBuffer();
        String[] modelParameters = {"numberPlate", "model", "color", "description", "yearOfManufacture", "rentalPrice"};
        if (!parameterCheckOut(request, modelParameters)) return null;
        Car car = new Car();
        String numberPlate = request.getParameter("numberPlate") != null ? request.getParameter("numberPlate") : "";
        car.setNumberPlate(numberPlate);
        String model = request.getParameter("model") != null ? request.getParameter("model") : "";
        car.setModel(model);
        try {
            Car.Color color = request.getParameter("color") != null && !request.getParameter("color").isEmpty() ? Car.Color.valueOf(request.getParameter("color")) : Car.Color.NAN;
            car.setColor(color);
        } catch (IllegalArgumentException ex) {
            errorMessage.append("Choose correct color. ");
            logger.warn(ex);
        }
        String description = request.getParameter("description") != null ? request.getParameter("description") : "";
        car.setDescription(description);
        try {
            int yearOfManufacture = request.getParameter("yearOfManufacture") != null && !request.getParameter("yearOfManufacture").isEmpty() ? Integer.valueOf(request.getParameter("yearOfManufacture")) : 0;
            car.setYearOfManufacture(yearOfManufacture);
        } catch (IllegalArgumentException ex) {
            errorMessage.append("Set correct year of manufacture. ");
            logger.warn(ex);
        }
        try {
            BigDecimal rentalPrice = request.getParameter("rentalPrice") != null && !request.getParameter("rentalPrice").isEmpty() ? BigDecimal.valueOf(Double.valueOf(request.getParameter("rentalPrice"))) : BigDecimal.ZERO;
            car.setRentalPrice(rentalPrice);
        } catch (IllegalArgumentException ex) {
            errorMessage.append("Set correct rental price. ");
            logger.warn(ex);
        }
        car.setRented(request.getParameter("rented") != null);
        logger.info("New car made.");
        return car;
    }

    public Client getClient() {
        logger.info("Make client model.");
        errorMessage = new StringBuffer();
        String[] modelParameters = {"firstName", "lastName", "birthday", "dLNumber" , "lengthOfDrivingExperience"};
        if (!parameterCheckOut(request, modelParameters)) return null;
        Client client = new Client();
        String firstName = request.getParameter("firstName") != null ? request.getParameter("firstName") : "";
        client.setFirstName(firstName);
        String lastName = request.getParameter("lastName") != null ? request.getParameter("lastName") : "";
        client.setLastName(lastName);
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date birthday = request.getParameter("birthday") != null && !request.getParameter("birthday").isEmpty() ? dateFormat.parse(request.getParameter("birthday")) : null;
            client.setBirthday(birthday);
        } catch (ParseException ex) {
            errorMessage.append("Set correct birthday. ");
            logger.warn(ex);
        }
        try {
            int dLNumber = request.getParameter("dLNumber") != null && !request.getParameter("dLNumber").isEmpty() ? Integer.valueOf(request.getParameter("dLNumber")) : 0;
            client.setdLNumber(dLNumber);
        } catch (IllegalArgumentException ex) {
            errorMessage.append("Set correct driver's license number. ");
            logger.warn(ex);
        }
        try {
            int lengthOfDrivingExperience = request.getParameter("lengthOfDrivingExperience") != null && !request.getParameter("lengthOfDrivingExperience").isEmpty() ? Integer.valueOf(request.getParameter("lengthOfDrivingExperience")) : 0;
            client.setLengthOfDrivingExperience(lengthOfDrivingExperience);
        } catch (IllegalArgumentException ex) {
            errorMessage.append("Set correct length of driving experience.");
            logger.warn(ex);
        }
        logger.info("New client made.");
        return client;
    }

    public Administrator getAdministrator() {
        logger.info("Make administrator model.");
        errorMessage = new StringBuffer();
        String[] modelParameters = {"firstName", "lastName", "email", "login", "password"};
        if (!parameterCheckOut(request, modelParameters)) return null;
        Administrator administrator = new Administrator();
        String firstName = request.getParameter("firstName") != null ? request.getParameter("firstName") : "";
        administrator.setFirstName(firstName);
        String lastName = request.getParameter("lastName") != null ? request.getParameter("lastName") : "";
        administrator.setLastName(lastName);
        String email = request.getParameter("email") != null ? request.getParameter("email") : "";
        administrator.setEmail(email);
        String login = request.getParameter("login") != null ? request.getParameter("login") : "";
        administrator.setLogin(login);
        String password = request.getParameter("password") != null ? PasswordHelper.getSecurePassword(request.getParameter("password")) : "";
        administrator.setPassword(password);
        logger.info("New administrator made.");
        return administrator;
    }

}
