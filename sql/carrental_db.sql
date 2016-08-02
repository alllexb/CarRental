-- CREATE DATABASE 'carrental_db';
USE `carrental_db`;
DROP TABLE IF EXISTS `car_tb`;

CREATE TABLE `car_tb` (`id` BIGINT(10) NOT NULL AUTO_INCREMENT,
  `model` VARCHAR(50) NOT NULL,
  `color` VARCHAR(15) DEFAULT NULL,
  `description` VARCHAR(300) DEFAULT NULL,
  `year_of_manufacture` INTEGER(4) NOT NULL,
  `rental_price` DECIMAL(10) ,
  `rented` BIT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO car_tb VALUES
(LAST_INSERT_ID(), 'Ferrari', 'RED', 'Fast car!', 2015, 1500, 1),
(LAST_INSERT_ID(), 'Ford', 'BLACK', 'Slow car!', 2010, 200, 1);

SELECT * FROM `car_tb`;
