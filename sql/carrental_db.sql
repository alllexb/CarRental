-- CREATE DATABASE 'carrental_db';
USE `carrental_db`;
DROP TABLE IF EXISTS `car_tb`;
DROP TABLE IF EXISTS `client_tb`;
DROP TABLE IF EXISTS `administrator_tb`;

CREATE TABLE `car_tb` (`id` BIGINT(10) NOT NULL AUTO_INCREMENT,
  `model` VARCHAR(50) NOT NULL,
  `color` VARCHAR(15) DEFAULT NULL,
  `description` VARCHAR(300) DEFAULT NULL,
  `year_of_manufacture` INTEGER(4) NOT NULL,
  `rental_price` DECIMAL(10) ,
  `rented` BIT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `client_tb` (`id` BIGINT(10) NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(50) NOT NULL,
  `last_name` VARCHAR(50) NOT NULL,
  `birthday` DATE NOT NULL,
  `dl_number` INTEGER(10) NOT NULL,
  `length_of_driving_experience` INTEGER(3),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `administrator_tb` (`id` BIGINT(10) NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(50) NOT NULL,
  `last_name` VARCHAR(50) NOT NULL,
  `email` VARCHAR(50) NOT NULL,
  `login` VARCHAR(50) NOT NULL,
  `password` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO car_tb(model, color, description, year_of_manufacture, rental_price, rented) VALUES
('Ferrari', 'RED', 'Fast car!', 2015, 1500, 1),
('Ford', 'BLACK', 'Slow car!', 2010, 200, 1);

INSERT INTO client_tb(first_name, last_name, birthday, dl_number, length_of_driving_experience) VALUES
('Jone', 'Dou', '1988-03-01', 12358758, 6),
('Jane', 'Smith', '1981-07-03', 57893154, 10),
('Abigale', 'Tompson', '1966-04-25', 54879635, 30);

INSERT INTO administrator_tb(first_name, last_name, email, login, password) VALUES
('Bill', 'Jones', 'bj@carrental.com', 'admin-bj', 'ed5ba9153c1d2b3231aa43302b50a377'), -- password = bj1212!
('Eric', 'Gley', 'eg@carrental.com', 'admin-eg', 'fc5007160370fd6953a37e228c99e162'), -- password = $eric34+
('Amanda', 'Drims', 'ad@carrental.com', 'admin-ad', '086f3e957e828670d9fc24e51171eb82'); -- password = mandy#12!d

SELECT * FROM `car_tb`;
SELECT id, first_name, last_name, DATE_FORMAT(birthday, '%d-%m-%Y'), dl_number, length_of_driving_experience FROM `client_tb`;
SELECT * FROM `administrator_tb`;
