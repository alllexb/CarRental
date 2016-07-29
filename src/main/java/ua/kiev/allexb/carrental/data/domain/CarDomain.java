package ua.kiev.allexb.carrental.data.domain;

import ua.kiev.allexb.carrental.model.Car;

import java.math.BigDecimal;

/**
 * Created by bas on 29.07.2016.
 */
public class CarDomain extends AbstractDomain {

    private String model;
    private Car.Colour colour;
    private String description;
    private int yearOfManufacture;
    private BigDecimal rentalPrice;

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
}
