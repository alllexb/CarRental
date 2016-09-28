-- CREATE DATABASE 'carrental_db';
USE `carrental_db`;
DROP TABLE IF EXISTS `order_cars_tb`;
DROP TABLE IF EXISTS `car_tb`;
DROP TABLE IF EXISTS `order_tb`;
DROP TABLE IF EXISTS `client_tb`;
DROP TABLE IF EXISTS `administrator_tb`;

CREATE TABLE `car_tb` (`id` BIGINT(10) NOT NULL AUTO_INCREMENT,
  `number_plate` VARCHAR(15) NOT NULL,
  `model` VARCHAR(50) DEFAULT NULL,
  `color` VARCHAR(15) DEFAULT 'NAN',
  `description` VARCHAR(300) DEFAULT NULL,
  `year_of_manufacture` INTEGER(4) NOT NULL,
  `rental_price` DECIMAL(10, 2),
  `status` VARCHAR(14) DEFAULT 'UNIDENTIFIED',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `client_tb` (`id` BIGINT(10) NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(50),
  `last_name` VARCHAR(50),
  `birthday` DATE,
  `dl_number` INTEGER(10) NOT NULL,
  `length_of_driving_experience` INTEGER(3),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `administrator_tb` (`id` BIGINT(10) NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(50),
  `last_name` VARCHAR(50),
  `email` VARCHAR(50) NOT NULL,
  `login` VARCHAR(50) NOT NULL,
  `password` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `order_tb` (`id` BIGINT(10) NOT NULL AUTO_INCREMENT,
  `client_id` BIGINT(10) NOT NULL,
  `rental_start` DATE,
  `rental_end` DATE,
  `closed` BIT,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`client_id`) REFERENCES client_tb(`id`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `order_cars_tb` (
  `order_id` BIGINT(10) NOT NULL,
  `car_id` BIGINT(10) NOT NULL,
  FOREIGN KEY (`order_id`) REFERENCES order_tb(`id`) ON DELETE RESTRICT,
  FOREIGN KEY (`car_id`) REFERENCES car_tb(`id`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO car_tb(number_plate, model, color, description, year_of_manufacture, rental_price, status) VALUES
('AA0666AA', 'Ferrari', 'RED', 'Fast car!', 2015, 1500, 'RENTED'),
('AA5325CE', 'Ford', 'BLACK', 'Slow car!', 2010, 200, 'RENTED'),
('AA7777II', 'GMC', 'GREEN', 'Very big car!', 2009, 700, 'RENTED'),
('AI1551CI', 'Peugeot 207', 'ORANGE', 'Hatchback, very comfortable!', 2013, 275, 'RENTED'),
('AA1356AB', 'ZAZ Deawoo Lanos', 'GREY', 'This car is a workhorse.', 2010,	75, 'RENTED'),
('AB3355BE', 'Mercedes Vito', 'WHITE', 'Standard panel van for cargo.', 2012, 315, 'AVAILABLE'),
('AM3367CI', 'Fiat Doblo', 'RED', 'The panel van and leisure activity vehicle.', 2005, 195.55, 'AVAILABLE'),
('AX1228EE', _utf8'Сitroеn C4 Сactus', 'GREEN', 'Mini crossover, very pretty!', 2015, 400, 'RENTED'),
('CA5478AE', 'Nissan Almera', 'GREY', _utf8'Хорошая машина для семьи и работы.', 2009, 250, 'RENTED'),
('AA0777BB', 'Audi TT', 'RED', 'Fast sport car!', 2012, 450, 'AVAILABLE'),
('II3333BB', 'BMW X5', 'GOLD', 'Luxury sport utility vehicle!', 2016, 800, 'AVAILABLE');

INSERT INTO client_tb(first_name, last_name, birthday, dl_number, length_of_driving_experience) VALUES
('Jone', 'Dou', '1988-03-01', 12358758, 6),
('Jane', 'Smith', '1981-07-03', 57893154, 10),
('Abigale', 'Tompson', '1966-04-25', 54879635, 30);

INSERT INTO administrator_tb(first_name, last_name, email, login, password) VALUES
('Bill', 'Jones', 'bj@carrental.com', 'admin-bj', 'ed5ba9153c1d2b3231aa43302b50a377'), -- password = bj1212!
('Eric', 'Gley', 'eg@carrental.com', 'admin-eg', 'fc5007160370fd6953a37e228c99e162'), -- password = $eric34+
('Amanda', 'Drims', 'ad@carrental.com', 'admin-ad', '086f3e957e828670d9fc24e51171eb82'), -- password = mandy#12!d
('admin', 'admin', 'admin@admin.ad', 'admin', '21232f297a57a5a743894a0e4a801fc3'), -- password = admin
('as', 'as', 'as@as.as', 'as', 'f970e2767d0cfe75876ea857f92e319b'); -- password = as

INSERT INTO order_tb(client_id, rental_start, rental_end, closed) VALUES
(3, '2015-08-03', '2016-10-05', 0),
(2, '2016-08-03', '2017-05-12', 0),
(1, '2016-03-01', '2016-05-01', 1),
(1, '2016-05-01', '2017-03-01', 0);

INSERT INTO order_cars_tb(order_id, car_id) VALUES
(1, 1), (1, 3), (1, 5), (2, 2),(2, 4), (3, 10), (3, 6), (3, 7), (4, 8), (4, 9);

SELECT * FROM `car_tb`;
SELECT id, first_name, last_name, DATE_FORMAT(birthday, '%d-%m-%Y'), dl_number, length_of_driving_experience FROM `client_tb`;
SELECT * FROM `client_tb`;
SELECT * FROM `administrator_tb`;
SELECT * FROM `order_tb`;
SELECT * FROM `order_cars_tb`;
SELECT cars.* FROM `car_tb` cars
  INNER JOIN `order_cars_tb` oct ON cars.id = oct.car_id
  INNER JOIN `order_tb` orders ON oct.order_id = orders.id
  WHERE orders.id IN ('3')
  GROUP BY cars.id
  HAVING COUNT(DISTINCT orders.id) = 1;
SELECT clients.* FROM `client_tb` AS clients, `order_tb` AS orders WHERE orders.id = 3 AND clients.id = orders.client_id;
SELECT orders.id, orders.client_id, clients.*, orders.rental_start, orders.rental_end, orders.closed FROM `order_tb` AS orders, `client_tb` AS clients WHERE client_id=clients.id AND client_id=1 LIMIT 1;
SELECT orders.* FROM `order_tb` AS orders, `client_tb` AS clients
  WHERE orders.client_id=clients.id AND clients.id=1 AND clients.first_name='Jone' AND clients.last_name='Dou'
  AND clients.birthday='1988-03-01' AND clients.dl_number=12358758 AND clients.length_of_driving_experience=6;

SELECT orders.id AS `order_id`, clients.id AS `client_id`, clients.first_name, clients.last_name, clients.birthday,
  clients.dl_number, clients.length_of_driving_experience, cars.id AS `car_id`, cars.number_plate,
  cars.model, cars.color, cars.description, cars.year_of_manufacture, cars.rental_price,
  cars.rented, orders.rental_start, orders.rental_end, orders.closed FROM `order_tb` AS orders, `client_tb` AS clients,
  `car_tb` AS cars
    INNER JOIN `order_cars_tb` oct ON cars.id = oct.car_id
    INNER JOIN `order_tb` ord ON oct.order_id = ord.id
  WHERE ord.id=orders.id AND orders.client_id=clients.id AND clients.id=1 AND clients.first_name='Jone' AND clients.last_name='Dou'
  AND clients.birthday='1988-03-01' AND clients.dl_number=12358758 AND clients.length_of_driving_experience=6
  ORDER BY order_id;