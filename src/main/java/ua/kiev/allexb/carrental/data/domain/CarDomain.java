package ua.kiev.allexb.carrental.data.domain;

import ua.kiev.allexb.carrental.model.Car;

import java.math.BigDecimal;

/**
 * @author allexb
 * @version 1.0 29.07.2016
 */
public class CarDomain extends AbstractDomain {

    private String model;
    private Car.Colour colour;
    private String description;
    private int yearOfManufacture;
    private BigDecimal rentalPrice;
    private boolean rented;

    public CarDomain(Long id, String model, Car.Colour colour, String description, int yearOfManufacture, BigDecimal rentalPrice, boolean rented) {
        super(id);
        this.model = model;
        this.colour = colour;
        this.description = description;
        this.yearOfManufacture = yearOfManufacture;
        this.rentalPrice = rentalPrice;
        this.rented = rented;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Car.Colour getColour() {
        return colour;
    }

    public void setColour(Car.Colour colour) {
        this.colour = colour;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYearOfManufacture() {
        return yearOfManufacture;
    }

    public void setYearOfManufacture(int yearOfManufacture) {
        this.yearOfManufacture = yearOfManufacture;
    }

    public BigDecimal getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(BigDecimal rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public boolean isRented() {
        return rented;
    }

    public void setRented(boolean rented) {
        this.rented = rented;
    }
}
