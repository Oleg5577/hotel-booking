-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema hotel_booking_db
-- -----------------------------------------------------
DROP DATABASE IF EXISTS `hotel_booking_db`;
-- -----------------------------------------------------
-- Schema hotel_booking_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `hotel_booking_db` DEFAULT CHARACTER SET utf8 ;
USE `hotel_booking_db` ;

-- -----------------------------------------------------
-- Table `hotel_booking_db`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hotel_booking_db`.`role` (
  `role_id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'role id',
  `role_name` VARCHAR(30) NOT NULL DEFAULT 'client' COMMENT 'name of role (admin, client)',
  PRIMARY KEY (`role_id`),
  UNIQUE INDEX `role_name_UNIQUE` (`role_name` ASC))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hotel_booking_db`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hotel_booking_db`.`user` (
  `user_id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'user id',
  `email` VARCHAR(45) NOT NULL COMMENT 'user email for authorization',
  `password` VARCHAR(40) NOT NULL COMMENT 'user password for authorization\n',
  `name` VARCHAR(45) NOT NULL COMMENT 'user name ',
  `surname` VARCHAR(45) NOT NULL COMMENT 'user surname',
  `phone_number` VARCHAR(20) NULL COMMENT 'user phone\n',
  `fk_role_id` TINYINT UNSIGNED NOT NULL COMMENT 'reference to user role',
  PRIMARY KEY (`user_id`),
  INDEX `fk_user_role1_idx` (`fk_role_id` ASC),
  UNIQUE INDEX `login_UNIQUE` (`email` ASC),
  CONSTRAINT `fk_user_role1`
  FOREIGN KEY (`fk_role_id`)
  REFERENCES `hotel_booking_db`.`role` (`role_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hotel_booking_db`.`room_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hotel_booking_db`.`room_type` (
  `room_type_id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'room type id',
  `type_name` VARCHAR(30) NOT NULL COMMENT 'name of the type',
  PRIMARY KEY (`room_type_id`))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hotel_booking_db`.`room`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hotel_booking_db`.`room` (
  `room_id` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'room id',
  `number` SMALLINT UNSIGNED NOT NULL COMMENT 'room number',
  `size` TINYINT UNSIGNED NOT NULL COMMENT 'Number of places in the room\n\nКоличество мест в номере',
  `price` DECIMAL(10,2) UNSIGNED NOT NULL COMMENT 'room price per night',
  `fk_room_type_id` TINYINT UNSIGNED NOT NULL COMMENT 'reference to room type',
  PRIMARY KEY (`room_id`),
  INDEX `fk_room_room_type1_idx` (`fk_room_type_id` ASC),
  CONSTRAINT `fk_room_room_type1`
  FOREIGN KEY (`fk_room_type_id`)
  REFERENCES `hotel_booking_db`.`room_type` (`room_type_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hotel_booking_db`.`order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hotel_booking_db`.`order` (
  `order_id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'order id',
  `check_in` DATE NOT NULL COMMENT 'Check-in date in an order\n\nДата заезда в отель в заказе.\n',
  `check_out` DATE NOT NULL COMMENT 'Check-out date in order\n\nДата выезда из отеля в заказе.',
  `amount` DECIMAL(10,2) UNSIGNED NOT NULL COMMENT 'amount of the booked order\n\nсумма к оплате за заказ',
  `fk_room_id` SMALLINT UNSIGNED NOT NULL COMMENT 'reference to the booked room',
  `fk_user_id` INT UNSIGNED NOT NULL COMMENT 'reference to user who requested or booked a room\n\ncсылка на пользователя, который забронировал номер',
  `is_paid` TINYINT(1) NOT NULL DEFAULT 0 COMMENT 'указатель оплачен или не оплачен заказ',
  `order_status` ENUM('expect_guest_arrival', 'checked-in', 'checked-out', 'canceled') NOT NULL DEFAULT 'expect_guest_arrival',
  PRIMARY KEY (`order_id`),
  INDEX `fk_order_room1_idx` (`fk_room_id` ASC),
  INDEX `fk_order_user1_idx` (`fk_user_id` ASC),
  CONSTRAINT `fk_order_room1`
  FOREIGN KEY (`fk_room_id`)
  REFERENCES `hotel_booking_db`.`room` (`room_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_user1`
  FOREIGN KEY (`fk_user_id`)
  REFERENCES `hotel_booking_db`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hotel_booking_db`.`room_request`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hotel_booking_db`.`room_request` (
  `request_id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'request id',
  `check_in` DATE NOT NULL COMMENT 'requested check-in date in a client\'s request\n\nзапрашиваемая дата заезда в отель в заявке клиента\n',
  `check_out` DATE NOT NULL COMMENT 'requested check-out date in a client\'s request\n\nзапрашиваемая дата выезда из отеля в заявке клиента',
  `room_size` TINYINT UNSIGNED NOT NULL COMMENT 'requested number of places in the hotel room\n\nзапрашиваемое количество мест в гостиничном номере',
  `request_status` ENUM('in_progress', 'confirmed', 'denied') NOT NULL DEFAULT 'in_progress' COMMENT 'request_status.\n    \'in progress\' - запрос находится в списке на обработку\n     \'confirmed\' - запрос подтвержден и сформирован заказ\n     \'rejected\' - отказано в запросе (например нет свободных номеров)',
  `user_id` INT UNSIGNED NOT NULL,
  `room_type_id` TINYINT UNSIGNED NOT NULL,
  PRIMARY KEY (`request_id`),
  INDEX `fk_room_request_user1_idx` (`user_id` ASC),
  INDEX `fk_room_request_room_type1_idx` (`room_type_id` ASC),
  CONSTRAINT `fk_room_request_user1`
  FOREIGN KEY (`user_id`)
  REFERENCES `hotel_booking_db`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_room_request_room_type1`
  FOREIGN KEY (`room_type_id`)
  REFERENCES `hotel_booking_db`.`room_type` (`room_type_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
