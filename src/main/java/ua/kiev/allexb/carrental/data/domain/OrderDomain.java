package ua.kiev.allexb.carrental.data.domain;

import ua.kiev.allexb.carrental.model.Car;
import ua.kiev.allexb.carrental.model.Order;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author allexb
 * @version 1.0 23.07.2016
 */
public class OrderDomain extends AbstractDomain {

    private ClientDomain client;
    private Set<CarDomain> cars;
    private Date rentalStart = new Date();
    private Date rentalEnd = new Date();
    private boolean closed = false;

    public OrderDomain(Long id, ClientDomain client, Set<CarDomain> cars, Date rentalStart, Date rentalEnd, boolean closed) {
        super(id);
        this.client = client;
        this.cars = cars;
        this.rentalStart = rentalStart;
        this.rentalEnd = rentalEnd;
        this.closed = closed;
    }

    public OrderDomain(Order order) {
        this.setOrder(order);
    }

    public OrderDomain() {
    }

    public Order getOrder() {
        Set<Car> cars = this.cars.stream().map(CarDomain::getCar).collect(Collectors.toSet());
        return new Order(this.getId(), this.client.getClient(), cars, this.rentalStart, this.rentalEnd, this.closed);
    }

    public void setOrder(Order order) {
        super.setId(order.getId());
        this.client = new ClientDomain(order.getClient());
        this.cars.addAll(order.getCars().stream().map(CarDomain::new).collect(Collectors.toList()));
        this.rentalStart = order.getRentalStart();
        this.rentalEnd = order.getRentalEnd();
        this.closed = order.isClosed();
    }

    public ClientDomain getClient() {
        return client;
    }

    public void setClient(ClientDomain client) {
        this.client = client;
    }

    public Set<CarDomain> getCars() {
        return cars;
    }

    public void setCars(Set<CarDomain> cars) {
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
}
