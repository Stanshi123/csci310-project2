-- initialize database
-- run this file only once or when creating a whole new environment

-- create schema
CREATE DATABASE IF NOT EXISTS `scrumdb` DEFAULT CHARACTER SET utf8 ;
USE `scrumdb` ;

-- create table for users
CREATE TABLE IF NOT EXISTS `scrumdb`.`user` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  PRIMARY KEY (`user_id`));

-- create table for saved collages
CREATE TABLE IF NOT EXISTS `scrumdb`.`saved_collage` (
  `saved_collage_id` INT NOT NULL AUTO_INCREMENT,
  `collage_name` VARCHAR(100) NOT NULL,
  `collage_path` VARCHAR(200) NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`saved_collage_id`, `user_id`),
  INDEX `fk_saved_collage_user_idx` (`user_id` ASC),
  CONSTRAINT `fk_saved_collage_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `scrumdb`.`user` (`user_id`);

-- user table data
INSERT INTO `scrumdb`.`user` (`user_id`, `username`, `password`) VALUES (1, 'anisha', 'anisha');
INSERT INTO `scrumdb`.`user` (`user_id`, `username`, `password`) VALUES (2, 'chaokang', 'chaokang');
INSERT INTO `scrumdb`.`user` (`user_id`, `username`, `password`) VALUES (3, 'gavin', 'gavin');
INSERT INTO `scrumdb`.`user` (`user_id`, `username`, `password`) VALUES (4, 'ivy', 'ivy');
INSERT INTO `scrumdb`.`user` (`user_id`, `username`, `password`) VALUES (5, 'stan', 'stan');
INSERT INTO `scrumdb`.`user` (`user_id`, `username`, `password`) VALUES (6, 'william', 'william');

-- saved collage table data
INSERT INTO `scrumdb`.`saved_collage` (`saved_collage_id`, `collage_name`, `collage_path`, `user_id`) VALUES (1, 'test', 'path', 1);
