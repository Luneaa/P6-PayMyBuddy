-- MySQL Script generated by MySQL Workbench
-- Mon Aug 19 11:46:58 2024
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema paymybuddy
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `paymybuddy`;
CREATE SCHEMA IF NOT EXISTS `paymybuddy` DEFAULT CHARACTER SET utf8 ;
USE `paymybuddy` ;

-- -----------------------------------------------------
-- Table `paymybuddy`.`Users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `paymybuddy`.`Users` ;

CREATE TABLE IF NOT EXISTS `paymybuddy`.`Users` (
                                              `id_user` INT NOT NULL AUTO_INCREMENT,
                                              `username` VARCHAR(45) NOT NULL,
    `email` VARCHAR(45) NOT NULL,
    `password` varchar(255) NOT NULL,
    PRIMARY KEY (`id_user`),
    UNIQUE INDEX `idUsers_UNIQUE` (`id_user` ASC) VISIBLE,
    UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE,
    UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
    ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `paymybuddy`.`UserConnections`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `paymybuddy`.`UserConnections` ;

CREATE TABLE IF NOT EXISTS `paymybuddy`.`UserConnections` (
                                                        `id_user` INT NOT NULL,
                                                        `id_user_connected_to` INT NOT NULL,
                                                        PRIMARY KEY (`id_user`, `id_user_connected_to`),
    INDEX `idUser_idx` (`id_user` ASC) VISIBLE,
    INDEX `idUserConnectedTo_idx` (`id_user_connected_to` ASC) VISIBLE,
    CONSTRAINT `idUser`
    FOREIGN KEY (`id_user`)
    REFERENCES `paymybuddy`.`Users` (`id_user`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `idUserConnectedTo`
    FOREIGN KEY (`id_user_connected_to`)
    REFERENCES `paymybuddy`.`Users` (`id_user`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `paymybuddy`.`Transactions`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `paymybuddy`.`Transactions` ;

CREATE TABLE IF NOT EXISTS `paymybuddy`.`Transactions`(
                                                     `id_transaction` INT NOT NULL AUTO_INCREMENT,
                                                     `sender_id` INT NOT NULL,
                                                     `receiver_id` INT NOT NULL,
                                                     `date` DATETIME NOT NULL,
                                                     `description` VARCHAR(45) NULL,
    `amount` DOUBLE NOT NULL,
    PRIMARY KEY (`id_transaction`),
    UNIQUE INDEX `idTransaction_UNIQUE` (`id_transaction` ASC) VISIBLE,
    INDEX `senderId_idx` (`sender_id` ASC) VISIBLE,
    INDEX `receiverId_idx` (`receiver_id` ASC) VISIBLE,
    CONSTRAINT `senderId`
    FOREIGN KEY (`sender_id`)
    REFERENCES `paymybuddy`.`Users` (`id_user`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `receiverId`
    FOREIGN KEY (`receiver_id`)
    REFERENCES `paymybuddy`.`Users` (`id_user`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- INSERT test data
-- -----------------------------------------------------

-- Add users
INSERT INTO paymybuddy.users (email, password, username)
VALUES ('john.doe@gmail.com', '$2a$10$htxqcUTvLsd16IzXn5baeebDTWk5q2QmVfRIRoNZA.nbp5mUvicx2', 'john');
INSERT INTO paymybuddy.users (email, password, username)
VALUES ('jane.doe@gmail.com', '$2a$10$htxqcUTvLsd16IzXn5baeebDTWk5q2QmVfRIRoNZA.nbp5mUvicx2', 'jane');
INSERT INTO paymybuddy.users (email, password, username)
VALUES ('alain.connu@gmail.com', '$2a$10$htxqcUTvLsd16IzXn5baeebDTWk5q2QmVfRIRoNZA.nbp5mUvicx2', 'alain');

-- Add user connections
INSERT INTO paymybuddy.userconnections (id_user, id_user_connected_to) VALUES (1, 2);
INSERT INTO paymybuddy.userconnections (id_user, id_user_connected_to) VALUES (2, 3);

-- Add transactions
INSERT INTO paymybuddy.transactions (sender_id, receiver_id, date, description, amount)
VALUES (1, 2, '2020-01-20 10:20:30' ,'resto', 25);
INSERT INTO paymybuddy.transactions (sender_id, receiver_id, date, description, amount)
VALUES (2, 1, '2020-01-19 19:20:00' ,'cinema', 12);
INSERT INTO paymybuddy.transactions (sender_id, receiver_id, date, description, amount)
VALUES (2, 3, '2020-02-22 11:41:27', 'cine', 15);
