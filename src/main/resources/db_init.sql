-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema exhibition_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema exhibition_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `exhibition_db` DEFAULT CHARACTER SET utf8 ;
USE `exhibition_db` ;

-- -----------------------------------------------------
-- Table `exhibition_db`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `exhibition_db`.`user` ;

CREATE TABLE IF NOT EXISTS `exhibition_db`.`user` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(32) NOT NULL,
  `password` VARCHAR(32) NOT NULL,
  `role` VARCHAR(32) NOT NULL,
  `money` DECIMAL(9,2) UNSIGNED NOT NULL DEFAULT 1000,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `username_UNIQUE` (`login` ASC) VISIBLE)
ENGINE = InnoDB
KEY_BLOCK_SIZE = 1;


-- -----------------------------------------------------
-- Table `exhibition_db`.`exhibition`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `exhibition_db`.`exhibition` ;

CREATE TABLE IF NOT EXISTS `exhibition_db`.`exhibition` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `theme` VARCHAR(255) NOT NULL,
  `details` VARCHAR(1024) NOT NULL,
  `start_date` DATE NOT NULL,
  `end_date` DATE NOT NULL,
  `start_time` TIME NOT NULL,
  `end_time` TIME NOT NULL,
  `price` DECIMAL(9,2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `exhibition_id_UNIQUE` (`id` ASC) VISIBLE,
  FULLTEXT INDEX `idx_exhibition_theme` (`theme`) VISIBLE,
  INDEX `idx_exhibtition_start_date` (`start_date` ASC) INVISIBLE,
  INDEX `idx_exhibtition_price` (`price` ASC) VISIBLE);


-- -----------------------------------------------------
-- Table `exhibition_db`.`hall`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `exhibition_db`.`hall` ;

CREATE TABLE IF NOT EXISTS `exhibition_db`.`hall` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(60) NOT NULL,
  `details` VARCHAR(1024) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `hall_id_UNIQUE` (`id` ASC) VISIBLE);


-- -----------------------------------------------------
-- Table `exhibition_db`.`exhibition_halls`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `exhibition_db`.`exhibition_halls` ;

CREATE TABLE IF NOT EXISTS `exhibition_db`.`exhibition_halls` (
  `exhibition_id` INT NOT NULL,
  `hall_id` INT NOT NULL,
  PRIMARY KEY (`exhibition_id`, `hall_id`),
  INDEX `fk_exhibition_has_hall_hall1_idx` (`hall_id` ASC) VISIBLE,
  INDEX `fk_exhibition_has_hall_exhibition_idx` (`exhibition_id` ASC) VISIBLE,
  CONSTRAINT `fk_exhibition_has_hall_exhibition`
    FOREIGN KEY (`exhibition_id`)
    REFERENCES `exhibition_db`.`exhibition` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_exhibition_has_hall_hall1`
    FOREIGN KEY (`hall_id`)
    REFERENCES `exhibition_db`.`hall` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table `exhibition_db`.`user_tickets`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `exhibition_db`.`user_tickets` ;

CREATE TABLE IF NOT EXISTS `exhibition_db`.`user_tickets` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT UNSIGNED NOT NULL,
  `exhibition_id` INT NOT NULL,
  PRIMARY KEY (`id`, `user_id`, `exhibition_id`),
  INDEX `fk_user_has_exhibition_exhibition1_idx` (`exhibition_id` ASC) VISIBLE,
  INDEX `fk_user_has_exhibition_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_has_exhibition_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `exhibition_db`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_user_has_exhibition_exhibition1`
    FOREIGN KEY (`exhibition_id`)
    REFERENCES `exhibition_db`.`exhibition` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `exhibition_db`.`user`
-- -----------------------------------------------------
START TRANSACTION;
USE `exhibition_db`;
INSERT INTO `exhibition_db`.`user` (`id`, `login`, `password`, `role`, `money`) VALUES (DEFAULT, 'admin', '21232f297a57a5a743894a0e4a801fc3', 'admin', 0);
INSERT INTO `exhibition_db`.`user` (`id`, `login`, `password`, `role`, `money`) VALUES (DEFAULT, 'Jimmy', '7c6a180b36896a0a8c02787eeafb0e4c', 'user', 1200);
INSERT INTO `exhibition_db`.`user` (`id`, `login`, `password`, `role`, `money`) VALUES (DEFAULT, 'Tom', '6cb75f652a9b52798eb6cf2201057c73', 'user', 1000);
INSERT INTO `exhibition_db`.`user` (`id`, `login`, `password`, `role`, `money`) VALUES (DEFAULT, 'Василий', '819b0643d6b89dc9b579fdfc9094f28e', 'user', 1000);
INSERT INTO `exhibition_db`.`user` (`id`, `login`, `password`, `role`, `money`) VALUES (DEFAULT, 'Степан', '218dd27aebeccecae69ad8408d9a36bf', 'user', 1000);
INSERT INTO `exhibition_db`.`user` (`id`, `login`, `password`, `role`, `money`) VALUES (DEFAULT, 'Дмитро', '218dd27aebeccecae69ad8408d9a363f', 'user', 1500);

COMMIT;


-- -----------------------------------------------------
-- Data for table `exhibition_db`.`exhibition`
-- -----------------------------------------------------
START TRANSACTION;
USE `exhibition_db`;
INSERT INTO `exhibition_db`.`exhibition` (`id`, `theme`, `details`, `start_date`, `end_date`, `start_time`, `end_time`, `price`) VALUES (DEFAULT, 'Red color', 'Exhibition about red color', '2022.11.11', '2022.11.16', '08:00:00', '18:00:00', 100);
INSERT INTO `exhibition_db`.`exhibition` (`id`, `theme`, `details`, `start_date`, `end_date`, `start_time`, `end_time`, `price`) VALUES (DEFAULT, 'Orange color', 'Exhibition about orange color', '2022.11.17', '2022.11.18', '08:00:00', '19:00:00', 200);
INSERT INTO `exhibition_db`.`exhibition` (`id`, `theme`, `details`, `start_date`, `end_date`, `start_time`, `end_time`, `price`) VALUES (DEFAULT, 'Multi color', 'Exhibition about multi-colors', '2022.11.20', '2022.11.24', '10:00:00', '20:00:00', 500);
INSERT INTO `exhibition_db`.`exhibition` (`id`, `theme`, `details`, `start_date`, `end_date`, `start_time`, `end_time`, `price`) VALUES (DEFAULT, 'BlackWhite', 'Exhibition about black-white colors', '2022.12.01', '2022.12.05', '08:00:00', '18:30:00', 200);
INSERT INTO `exhibition_db`.`exhibition` (`id`, `theme`, `details`, `start_date`, `end_date`, `start_time`, `end_time`, `price`) VALUES (DEFAULT, 'History Art', 'Exhibition about histrorical aspects of art', '2022.12.23', '2022.12.29', '08:00:00', '19:40:00', 250);
INSERT INTO `exhibition_db`.`exhibition` (`id`, `theme`, `details`, `start_date`, `end_date`, `start_time`, `end_time`, `price`) VALUES (DEFAULT, 'Місто Харків', 'Виставка присвячена місту Харкову', '2022.12.18', '2022.12.28', '09:00:00', '20:00:00', 100);

COMMIT;


-- -----------------------------------------------------
-- Data for table `exhibition_db`.`hall`
-- -----------------------------------------------------
START TRANSACTION;
USE `exhibition_db`;
INSERT INTO `exhibition_db`.`hall` (`id`, `name`, `details`) VALUES (DEFAULT, 'Red', 'Some Red Hall details');
INSERT INTO `exhibition_db`.`hall` (`id`, `name`, `details`) VALUES (DEFAULT, 'Yellow', 'Some Yellow Hall details');
INSERT INTO `exhibition_db`.`hall` (`id`, `name`, `details`) VALUES (DEFAULT, 'Orange', 'Some Orange Hall details');
INSERT INTO `exhibition_db`.`hall` (`id`, `name`, `details`) VALUES (DEFAULT, 'Green', 'Some Gree Hall details');
INSERT INTO `exhibition_db`.`hall` (`id`, `name`, `details`) VALUES (DEFAULT, 'Blue', 'Some Blue Hall details');
INSERT INTO `exhibition_db`.`hall` (`id`, `name`, `details`) VALUES (DEFAULT, 'Purple', 'Some Purple Hall details');
INSERT INTO `exhibition_db`.`hall` (`id`, `name`, `details`) VALUES (DEFAULT, 'Black', 'Some Black Hall details');
INSERT INTO `exhibition_db`.`hall` (`id`, `name`, `details`) VALUES (DEFAULT, 'White', 'Some White Hall details');

COMMIT;


-- -----------------------------------------------------
-- Data for table `exhibition_db`.`exhibition_halls`
-- -----------------------------------------------------
START TRANSACTION;
USE `exhibition_db`;
INSERT INTO `exhibition_db`.`exhibition_halls` (`exhibition_id`, `hall_id`) VALUES (1, 1);
INSERT INTO `exhibition_db`.`exhibition_halls` (`exhibition_id`, `hall_id`) VALUES (1, 2);
INSERT INTO `exhibition_db`.`exhibition_halls` (`exhibition_id`, `hall_id`) VALUES (2, 3);
INSERT INTO `exhibition_db`.`exhibition_halls` (`exhibition_id`, `hall_id`) VALUES (2, 4);
INSERT INTO `exhibition_db`.`exhibition_halls` (`exhibition_id`, `hall_id`) VALUES (3, 7);
INSERT INTO `exhibition_db`.`exhibition_halls` (`exhibition_id`, `hall_id`) VALUES (3, 8);
INSERT INTO `exhibition_db`.`exhibition_halls` (`exhibition_id`, `hall_id`) VALUES (4, 7);
INSERT INTO `exhibition_db`.`exhibition_halls` (`exhibition_id`, `hall_id`) VALUES (4, 5);
INSERT INTO `exhibition_db`.`exhibition_halls` (`exhibition_id`, `hall_id`) VALUES (5, 6);
INSERT INTO `exhibition_db`.`exhibition_halls` (`exhibition_id`, `hall_id`) VALUES (6, 4);
INSERT INTO `exhibition_db`.`exhibition_halls` (`exhibition_id`, `hall_id`) VALUES (6, 3);
INSERT INTO `exhibition_db`.`exhibition_halls` (`exhibition_id`, `hall_id`) VALUES (6, 2);

COMMIT;


-- -----------------------------------------------------
-- Data for table `exhibition_db`.`user_tickets`
-- -----------------------------------------------------
START TRANSACTION;
USE `exhibition_db`;
INSERT INTO `exhibition_db`.`user_tickets` (`id`, `user_id`, `exhibition_id`) VALUES (DEFAULT, 2, 1);
INSERT INTO `exhibition_db`.`user_tickets` (`id`, `user_id`, `exhibition_id`) VALUES (DEFAULT, 3, 1);
INSERT INTO `exhibition_db`.`user_tickets` (`id`, `user_id`, `exhibition_id`) VALUES (DEFAULT, 4, 3);
INSERT INTO `exhibition_db`.`user_tickets` (`id`, `user_id`, `exhibition_id`) VALUES (DEFAULT, 4, 2);
INSERT INTO `exhibition_db`.`user_tickets` (`id`, `user_id`, `exhibition_id`) VALUES (DEFAULT, 2, 3);
INSERT INTO `exhibition_db`.`user_tickets` (`id`, `user_id`, `exhibition_id`) VALUES (DEFAULT, 5, 5);
INSERT INTO `exhibition_db`.`user_tickets` (`id`, `user_id`, `exhibition_id`) VALUES (DEFAULT, 5, 4);
INSERT INTO `exhibition_db`.`user_tickets` (`id`, `user_id`, `exhibition_id`) VALUES (DEFAULT, 5, 3);

COMMIT;

