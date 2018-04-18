-- initialize database
-- run this file only once or when creating a whole new environment

-- create schema
DROP SCHEMA `scrumdb`;

CREATE DATABASE IF NOT EXISTS `scrumdb` DEFAULT CHARACTER SET utf8 ;
USE `scrumdb` ;

-- create table for users
CREATE TABLE IF NOT EXISTS `scrumdb`.`user` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NULL,
  `password` VARCHAR(200) NULL,
  PRIMARY KEY (`user_id`));

-- create table for saved collages
CREATE TABLE IF NOT EXISTS `scrumdb`.`saved_collage` (
  `saved_collage_id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NOT NULL,
  `path` VARCHAR(200) NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`saved_collage_id`, `user_id`),
  INDEX `fk_saved_collage_user_idx` (`user_id` ASC),
  CONSTRAINT `fk_saved_collage_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `scrumdb`.`user` (`user_id`));
    
INSERT INTO user (`username`, `password`) VALUES ('TEST', '$31$16$YXsVRSoZ-P9IZVlf1O88t9N5OP2RdRZk0LZj8vJW5uY');


