package ua.kiev.allexb.carrental.model.entity;

import java.math.BigDecimal;

/**
 * Created by bas on 29.07.16.
 */
public class Car {

    private int carId;
    private String carModel;
    private CarColour carColour;
    private String description;
    private int yearOfManufacture;
    private BigDecimal rentalPrice;

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public CarColour getCarColour() {
        return carColour;
    }

    public void setCarColour(CarColour carColour) {
        this.carColour = carColour;
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

    @Override
    public int hashCode() {
        int result = carId;
        result = 31 * result + carModel.hashCode();
        result = 31 * result + (carColour != null ? carColour.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + yearOfManufacture;
        result = 31 * result + (rentalPrice != null ? rentalPrice.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carId=" + carId +
                ", carModel='" + carModel + '\'' +
                ", carColour=" + carColour +
                ", description='" + description + '\'' +
                ", yearOfManufacture=" + yearOfManufacture +
                ", rentalPrice=" + rentalPrice +
                '}';
    }

    public enum CarColour {WHITE, BLACK, RED, YELLOW, BLUE};
}
