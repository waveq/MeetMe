CREATE TABLE `user` 
(
`ID` INT(11) NOT NULL AUTO_INCREMENT,

`name` VARCHAR(50) NOT NULL,

`last_name` VARCHAR(50) NOT NULL,

`login` VARCHAR(25) NOT NULL,

`password` VARCHAR(25) NOT NULL,

PRIMARY KEY(`ID`)
)
ENGINE=innoDB;



CREATE TABLE `place`
 (
`ID` INT(11) NOT NULL AUTO_INCREMENT,

PRIMARY KEY(`ID`),

`name` VARCHAR(75) NOT NULL,

`address` VARCHAR(50)
)
ENGINE=innoDB;



CREATE TABLE `event` 
(
`ID` INT(11) NOT NULL AUTO_INCREMENT,

`name` VARCHAR(45) NOT NULL,

`start_time` DATETIME NOT NULL,

`end_time` DATETIME NOT NULL,

`place` INT(10) NULL DEFAULT NULL,

PRIMARY KEY(`ID`),

INDEX `place_event` (`place`),

CONSTRAINT `place_event` FOREIGN KEY(`place`)
REFERENCES `place` (`ID`)
)
ENGINE=innoDB;



CREATE TABLE `sign` 
( 
`ID` INT(11) NOT NULL AUTO_INCREMENT,

`user` INT(10) NOT NULL,

`event` INT(10) NOT NULL,

PRIMARY KEY(ID),

INDEX `user_sign` (`user`),

INDEX `event_sign` (`event`),

CONSTRAINT `user_sign` FOREIGN KEY(`user`)
REFERENCES `user` (`ID`),

CONSTRAINT `event_sign` FOREIGN KEY(`event`)
REFERENCES `event` (`ID`) ON UPDATE CASCADE ON DELETE CASCADE
)ENGINE=innoDB;
