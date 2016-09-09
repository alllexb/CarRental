package ua.kiev.allexb.carrental.data.domain;

import ua.kiev.allexb.carrental.model.Car;

import java.math.BigDecimal;

/**
 * @author allexb
 * @version 1.0 29.07.2016
 */
public class CarDomain extends AbstractDomain {

    private String numberPlate;
    private String model;
    private Car.Color color;
    private String description;
    private int yearOfManufacture;
    private BigDecimal rentalPrice;
    private boolean rented;

    public CarDomain(Long id, String numberPlate, String model, Car.Color color, String description, int yearOfManufacture, BigDecimal rentalPrice, boolean rented) {
        super(id);
        this.numberPlate = numberPlate;
        this.model = model;
        this.color = color;
        this.description = description;
        this.yearOfManufacture = yearOfManufacture;
        this.rentalPrice = rentalPrice;
        this.rented = rented;
    }

    public CarDomain(Car car) {
        this.setCar(car);
    }

    public CarDomain(){
    }

    public Car getCar() {
        return new Car(this.getId(), this.numberPlate, this.model, this.color, this.description, this.yearOfManufacture,
                this.rentalPrice, this.rented);
    }

    public void setCar(Car car) {
        super.setId(car.getId());
        this.numberPlate = car.getNumberPlate();
        this.model = car.getModel();
        this.color = car.getColor();
        this.description = car.getDescription();
        this.yearOfManufacture = car.getYearOfManufacture();
        this.rentalPrice = car.getRentalPrice();
        this.rented = car.isRented();
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Car.Color getColor() {
        return color;
    }

    public void setColor(Car.Color color) {
        this.color = color;
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
