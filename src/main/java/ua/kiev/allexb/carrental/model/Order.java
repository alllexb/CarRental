package ua.kiev.allexb.carrental.model;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author allexb
 * @version 1.0 22.07.2016
 */
public class Order {

    private long id;
    private Client client;
    private Set<Car> cars = new HashSet<>();
    private Date rentalStart = new Date();
    private Date rentalEnd = new Date();
    private boolean closed = false;

    public Order() {
    }

    public Order(long id, Client client, Set<Car> cars, Date rentalStart, Date rentalEnd, boolean closed) {
        this.id = id;
        this.client = client;
        this.cars = cars;
        this.rentalStart = rentalStart;
        this.rentalEnd = rentalEnd;
        this.closed = closed;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }

    public Date getRentalStart() {
        return rentalStart;
    }

    public void setRentalStart(Date rentalStart) {
        this.rentalStart = rentalStart;
    }

    public Date getRentalEnd() {
        return rentalEnd;
    }

    public void setRentalEnd(Date rentalEnd) {
        this.rentalEnd = rentalEnd;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCars(Collection<Car> cars) {
        this.cars.addAll(cars);
    }

    public int getTimeInterval() {
        return getTimeInterval(rentalStart, rentalEnd);
    }

    private int getTimeInterval(Date startDate, Date endDate) {
        if (startDate.after(endDate)) {
            throw new IllegalArgumentException("End date should be grater or equals to start date");
        }
        long startDateTime = startDate.getTime();
        long endDateTime = endDate.getTime();
        long milPerDay = 1000*60*60*24;
        int numOfDays = (int) ((endDateTime - startDateTime) / milPerDay);
        return (numOfDays + 1);
    }

    public BigDecimal getTotalAmount() {
        BigDecimal totalAmount = new BigDecimal(0);
        for (Car car: cars) {
            totalAmount = totalAmount.add(car.getRentalPrice());
        }
        int timeInterval = this.getTimeInterval();
        totalAmount = totalAmount.multiply(BigDecimal.valueOf(timeInterval));
        return totalAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != order.id) return false;
        if (closed != order.closed) return false;
        if (!client.equals(order.client)) return false;
        if (!cars.equals(order.cars)) return false;
        if (!rentalStart.equals(order.rentalStart)) return false;
        return rentalEnd.equals(order.rentalEnd);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + client.hashCode();
        result = 31 * result + cars.hashCode();
        result = 31 * result + rentalStart.hashCode();
        result = 31 * result + rentalEnd.hashCode();
        result = 31 * result + (closed ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", client=" + client +
                ", cars=" + Arrays.toString(cars.toArray()) +
                ", rentalStart=" + rentalStart +
                ", rentalEnd=" + rentalEnd +
                ", closed=" + closed +
                '}';
    }
}
