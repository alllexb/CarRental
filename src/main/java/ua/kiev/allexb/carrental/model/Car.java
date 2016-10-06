package ua.kiev.allexb.carrental.model;

import java.math.BigDecimal;

/**
 * @author allexb
 * @version 1.0 29.07.2016
 */
public class Car {

    private long id;
    private String numberPlate;
    private String model;
    private Color color;
    private String description;
    private int yearOfManufacture;
    private BigDecimal rentalPrice;
    private Status status;

    public Car() {
    }

    public Car(long id, String numberPlate, String model, Color color, String description, int yearOfManufacture, BigDecimal rentalPrice, Status status) {
        this.id = id;
        this.numberPlate = numberPlate;
        this.model = model;
        this.color = color;
        this.description = description;
        this.yearOfManufacture = yearOfManufacture;
        this.rentalPrice = rentalPrice;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;

        if (id != car.id) return false;
        if (yearOfManufacture != car.yearOfManufacture) return false;
        if (!numberPlate.equals(car.numberPlate)) return false;
        if (model != null ? !model.equals(car.model) : car.model != null) return false;
        if (color != car.color) return false;
        if (description != null ? !description.equals(car.description) : car.description != null) return false;
        if (!rentalPrice.equals(car.rentalPrice)) return false;
        return status == car.status;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + numberPlate.hashCode();
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + color.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + yearOfManufacture;
        result = 31 * result + rentalPrice.hashCode();
        result = 31 * result + status.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", numberPlate='" + numberPlate + '\'' +
                ", model='" + model + '\'' +
                ", color=" + color +
                ", description='" + description + '\'' +
                ", yearOfManufacture=" + yearOfManufacture +
                ", rentalPrice=" + rentalPrice +
                ", status=" + status +
                '}';
    }

    public enum Color {NAN, WHITE, BLACK, GREY, RED, ORANGE, YELLOW, GREEN, BLUE, PURPLE, PINK, BROWN, GOLD, SILVER, BRONZE}
    public enum Status {UNIDENTIFIED, AVAILABLE, RESERVED, RENTED, ON_SERVICE, DECOMMISSIONED}
}
