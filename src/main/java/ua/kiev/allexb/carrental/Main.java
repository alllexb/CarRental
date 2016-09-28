package ua.kiev.allexb.carrental;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import ua.kiev.allexb.carrental.data.dao.*;
import ua.kiev.allexb.carrental.data.dao.util.DateUtil;
import ua.kiev.allexb.carrental.data.domain.AdministratorDomain;
import ua.kiev.allexb.carrental.data.domain.CarDomain;
import ua.kiev.allexb.carrental.data.domain.ClientDomain;
import ua.kiev.allexb.carrental.data.domain.OrderDomain;
import ua.kiev.allexb.carrental.data.service.ConnectionFactory;
import ua.kiev.allexb.carrental.model.Administrator;
import ua.kiev.allexb.carrental.model.Car;
import ua.kiev.allexb.carrental.model.Client;
import ua.kiev.allexb.carrental.model.Order;
import ua.kiev.allexb.carrental.model.helpers.PasswordHelper;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author allexb
 * @version 1.0 29.07.2016
 */
public class Main {

//    public static final String LOGGER_GONFIGFILE_PATH = "src\\main\\resources\\log4j.properties";
//    static final Logger logger = Logger.getLogger(Main.class);
//    private static final ConnectionFactory factory = ConnectionFactory.getInstance();
//
//    static {
//        System.setProperty("app.root", System.getProperty("user.dir") + "\\src\\main");
//        PropertyConfigurator.configure(LOGGER_GONFIGFILE_PATH);
//        logger.info("   ---==   START LOGGER SESSION   ==---");
//        logger.info("Logger configured.");
//    }

    public static void main(String[] args) throws SQLException {
//        Car car = new Car();
//        car.setId(1);
//        car.setModel("Ferrari");
//        car.setColor(Car.Color.BLACK);
//        car.setYearOfManufacture(2012);
//        car.setRentalPrice(new BigDecimal(2000d));
//        car.setDescription("Very fast car!");
//        car.setRented(true);
//
//        System.out.println(car);
//
//        CarDAO carDAO = new CarDAOImpl();
//        CarDomain dom;
//        Car carFromDB = ((dom = carDAO.getById(2L)) == null) ? null : dom.getCar();
//
//        System.out.println(carFromDB);
//        System.out.println("----");
//
//        List<Car> cars = carDAO.getAll().stream().map(CarDomain::getCar).collect(Collectors.toList());
//
//        System.out.println(Arrays.toString(cars.toArray()));
//
//        carFromDB = carDAO.getByModel("Ferrari").getCar();
//
//        System.out.println(carFromDB);
//
//        if ((dom = carDAO.getById(2L)) != null)
//            carDAO.remove(dom);
//
//        Car addedCar = new Car();
//        addedCar.setModel("Citroen C-Elysee");
//        addedCar.setColor(Car.Color.BLACK);
//        addedCar.setYearOfManufacture(2012);
//        addedCar.setRentalPrice(new BigDecimal(500d));
//        addedCar.setDescription("Comfortable car for you!");
//        addedCar.setRented(false);
//
//        carDAO.add(new CarDomain(addedCar));
//
//        Car updateCar = addedCar;
//        updateCar.setId(3L);
//        updateCar.setRentalPrice(new BigDecimal(700D));
//        carDAO.update(new CarDomain(updateCar));
//
//        System.out.println("--!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!--");
//
//        ClientDAO clientDAO = new ClientDAOImpl();
//        List<Client> clientsByFullName = clientDAO.getByFullName("Jone", "Dou").stream().map(ClientDomain::getClient).collect(Collectors.toList());
//        System.out.println(Arrays.toString(clientsByFullName.toArray()));
//
//        List<Client> clients = clientDAO.getAll().stream().map(ClientDomain::getClient).collect(Collectors.toList());
//        System.out.println(Arrays.toString(clients.toArray()));
//
//        ClientDomain clientDom;
//        Client clientFromDB = ((clientDom = clientDAO.getById(4L)) == null) ? null : clientDom.getClient();
//        System.out.println(clientFromDB);
//
//        Client addedClient = new Client();
//        addedClient.setFirstName("Alex");
//        addedClient.setLastName("Brown");
//        addedClient.setBirthday(DateUtil.getSimpleFormatDate("1985-05-12"));
//        addedClient.setdLNumber(89534712);
//        addedClient.setLengthOfDrivingExperience(12);
//        clientDAO.add(new ClientDomain(addedClient));
//
//        System.out.println("--= ADD =--");
//        clients = clientDAO.getAll().stream().map(ClientDomain::getClient).collect(Collectors.toList());
//        System.out.println(Arrays.toString(clients.toArray()));
//
//        Client updateClient = addedClient;
//        updateClient.setId(6L);
//        updateClient.setBirthday(DateUtil.getSimpleFormatDate("1988-06-04"));
//        updateClient.setLastName("Irvin");
//        updateClient.setdLNumber(59874563);
//
//        clientDAO.update(new ClientDomain(updateClient));
//
//        System.out.println("--= UPDATE =--");
//        clients = clientDAO.getAll().stream().map(ClientDomain::getClient).collect(Collectors.toList());
//        System.out.println(Arrays.toString(clients.toArray()));
//
//        Client removeClient = new Client();
//        removeClient.setId(10L);
//        removeClient.setFirstName("Alex");
//        removeClient.setLastName("Brown");
//        removeClient.setBirthday(DateUtil.getSimpleFormatDate("1985-05-12"));
//        removeClient.setdLNumber(89534712);
//        removeClient.setLengthOfDrivingExperience(12);
//        clientDAO.remove(new ClientDomain(removeClient));
//
//        System.out.println("--= REMOVE =--");
//        clients = clientDAO.getAll().stream().map(ClientDomain::getClient).collect(Collectors.toList());
//        System.out.println(Arrays.toString(clients.toArray()));
//
//        System.out.println("--= Administrator =--");
//        Administrator admin = new Administrator();
//        admin.setFirstName("Alexander");
//        admin.setLastName("B.");
//        admin.setEmail("ab@carrental.com");
//        admin.setLogin("admin-ab");
//        admin.setPassword(PasswordHelper.getSecurePassword("ab!12admin"));
//        System.out.println("--= GET by Full Name =--");
//        AdministratorDAO administratorDAO = null;
//        try {
//            administratorDAO = new AdministratorDAOImpl(factory.getConnection());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        List<Administrator> adminsByFullName =  administratorDAO.getByFullName("Bill","Jones").stream().map(AdministratorDomain::getAdministrator).collect(Collectors.toList());
//        System.out.println(Arrays.toString(adminsByFullName.toArray()));
//        System.out.println("--= GET admin by login and password =--");
//        Administrator adminByLoginAndPass = administratorDAO.getByLoginAndPassword("admin-eg",PasswordHelper.getSecurePassword("$eric34+")).getAdministrator();
//        System.out.println(adminByLoginAndPass);
//        System.out.println("--= GET All admin =--");
//        List<Administrator> admins =  administratorDAO.getAll().stream().map(AdministratorDomain::getAdministrator).collect(Collectors.toList());
//        System.out.println(Arrays.toString(admins.toArray()));
//        System.out.println("--= ADD Admin =--");
//        administratorDAO.add(new AdministratorDomain(admin));
//        admins =  administratorDAO.getAll().stream().map(AdministratorDomain::getAdministrator).collect(Collectors.toList());
//        System.out.println(Arrays.toString(admins.toArray()));
//        System.out.println("--= UPDATE Admin =--");
//        AdministratorDomain adminDom;
//        Administrator adminAmanda = ((adminDom = administratorDAO.getById(3L)) == null) ? null : adminDom.getAdministrator();
//        System.out.println(adminAmanda);
//        if (adminAmanda != null) {
//            adminAmanda.setEmail("amanda@gmail.com");
//            administratorDAO.update(new AdministratorDomain(adminAmanda));
//        }
//        admins =  administratorDAO.getAll().stream().map(AdministratorDomain::getAdministrator).collect(Collectors.toList());
//        System.out.println(Arrays.toString(admins.toArray()));
//        System.out.println("--= REMOVE Admin=--");
//        if (adminAmanda != null) {
//            administratorDAO.remove(new AdministratorDomain(adminAmanda));
//        }
//        admins =  administratorDAO.getAll().stream().map(AdministratorDomain::getAdministrator).collect(Collectors.toList());
//        System.out.println(Arrays.toString(admins.toArray()));

        Connection connection = ConnectionFactory.getInstance().getConnection();
        System.out.println(connection);
        OrderDAO orderDAO = new OrderDAOImpl(connection);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Client client = null;
        try {
            client = new Client(1L, "Jone", "Dou", format.parse("1988-03-01"), 12358758, 6);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<OrderDomain> orderDomainList = orderDAO.getByClient(new ClientDomain(client));
        List<Order> orders = orderDomainList.stream().map(OrderDomain::getOrder).collect(Collectors.toList());
        orders.forEach(System.out::println);

        for(Order order: orders) {
            System.out.println("Order ID #" + order.getId() + "; Total amount: " + order.getTotalAmount());
        }

        System.out.println();
        List<OrderDomain> allOrderDomains = orderDAO.getAll();
        List<Order> allOrders = allOrderDomains.stream().map(OrderDomain::getOrder).collect(Collectors.toList());
        allOrders.forEach(System.out::println);

        System.out.println();
        OrderDomain orderById = orderDAO.getById(5L);
        System.out.println(orderById != null ? orderById.getOrder(): null);

        System.out.println();
        Car car = new Car(3, "AA7777II", "GMC", Car.Color.GREEN, "Very big car!", 2009, new BigDecimal(700), Car.Status.RENTED);
        OrderDomain orderByCar = orderDAO.getByCar(new CarDomain(car));
        System.out.println(orderByCar != null ? orderByCar.getOrder(): null);
    }
}
