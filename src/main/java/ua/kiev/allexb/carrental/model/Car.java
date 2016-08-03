package ua.kiev.allexb.carrental.model;

import java.math.BigDecimal;

/**
 * @author allexb
 * @version 1.0 29.07.2016
 */
public class Car {

    private long id;
    private String model;
    private Colour colour;
    private String description;
    private int yearOfManufacture;
    private BigDecimal rentalPrice;
    private boolean  rented;

    public Car() {
    }

    public Car(long id, String model, Colour colour, String description, int yearOfManufacture, BigDecimal rentalPrice, boolean rented) {
        this.id = id;
        this.model = model;
        this.colour = colour;
        this.description = description;
        this.yearOfManufacture = yearOfManufacture;
        this.rentalPrice = rentalPrice;
        this.rented = rented;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
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

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + model.hashCode();
        result = 31 * result + (colour != null ? colour.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + yearOfManufacture;
        result = 31 * result + (rentalPrice != null ? rentalPrice.hashCode() : 0);
        result = 31 * result + (rented ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", colour=" + colour +
                ", description='" + description + '\'' +
                ", yearOfManufacture=" + yearOfManufacture +
                ", rentalPrice=" + rentalPrice +
                ", rented=" + rented +
                '}';
    }

    public enum Colour {WHITE, BLACK, RED, YELLOW, BLUE};
}
