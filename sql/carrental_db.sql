-- CREATE DATABASE 'carrental_db';
USE `carrental_db`;
DROP TABLE IF EXISTS `car_tb`;
DROP TABLE IF EXISTS `client_tb`;

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

INSERT INTO car_tb VALUES
(LAST_INSERT_ID(), 'Ferrari', 'RED', 'Fast car!', 2015, 1500, 1),
(LAST_INSERT_ID(), 'Ford', 'BLACK', 'Slow car!', 2010, 200, 1);

INSERT INTO client_tb VALUES
(LAST_INSERT_ID(), 'Jone', 'Dou', '1988-03-01', 12358758, 6),
(LAST_INSERT_ID(), 'Jane', 'Smith', '1981-07-03', 57893154, 10),
(LAST_INSERT_ID(), 'Abigale', 'Tompson', '1966-04-25', 54879635, 30);

SELECT * FROM `car_tb`;
SELECT id, first_name, last_name, DATE_FORMAT(birthday, '%d-%m-%Y'), dl_number, length_of_driving_experience FROM `client_tb`;
