-- Adminer 4.8.1 MySQL 5.5.5-10.6.16-MariaDB-0ubuntu0.22.04.1 dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

SET NAMES utf8mb4;

DROP TABLE IF EXISTS `appliance`;
CREATE TABLE `appliance` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `reference` varchar(100) NOT NULL,
  `wattage` int(11) NOT NULL,
  `habitat_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `habitat_id` (`habitat_id`),
  CONSTRAINT `appliance_ibfk_1` FOREIGN KEY (`habitat_id`) REFERENCES `habitat` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='http://localhost:8000/powerhome_server/adminer.php?file=plus.gif&version=4.8.1';

INSERT INTO `appliance` (`id`, `name`, `reference`, `wattage`, `habitat_id`) VALUES
(1,	'Aspirateur',	'AS9210',	700,	1);

DROP TABLE IF EXISTS `appliance_time_slot`;
CREATE TABLE `appliance_time_slot` (
  `appliance_id` int(11) NOT NULL,
  `time_slot_id` int(11) NOT NULL,
  `order` int(11) NOT NULL,
  `booked_at` datetime NOT NULL,
  KEY `appliance_id` (`appliance_id`),
  KEY `time_slot_id` (`time_slot_id`),
  CONSTRAINT `appliance_time_slot_ibfk_1` FOREIGN KEY (`appliance_id`) REFERENCES `appliance` (`id`),
  CONSTRAINT `appliance_time_slot_ibfk_2` FOREIGN KEY (`time_slot_id`) REFERENCES `time_slot` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `appliance_time_slot` (`appliance_id`, `time_slot_id`, `order`, `booked_at`) VALUES
(1,	1,	1,	'2024-02-28 10:44:19');

DROP TABLE IF EXISTS `habitat`;
CREATE TABLE `habitat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `floor` int(11) NOT NULL,
  `area` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `habitat` (`id`, `floor`, `area`) VALUES
(1,	1,	28),
(2,	1,	34);

DROP TABLE IF EXISTS `time_slot`;
CREATE TABLE `time_slot` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `begin` datetime NOT NULL,
  `end` datetime NOT NULL,
  `max_wattage` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `time_slot` (`id`, `begin`, `end`, `max_wattage`) VALUES
(1,	'2024-02-28 20:00:00',	'2024-02-28 21:00:00',	2000);

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(50) NOT NULL,
  `lastname` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `token` varchar(100) DEFAULT NULL,
  `expired_at` datetime DEFAULT NULL,
  `habitat_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `habitat_id` (`habitat_id`),
  CONSTRAINT `user_ibfk_2` FOREIGN KEY (`habitat_id`) REFERENCES `habitat` (`id`),
  CONSTRAINT `user_ibfk_3` FOREIGN KEY (`habitat_id`) REFERENCES `habitat` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `user` (`id`, `firstname`, `lastname`, `email`, `password`, `token`, `expired_at`, `habitat_id`) VALUES
(1,	'Ahmed',	'Chaouche',	'ac.chaouche@gmail.com',	'azerty',	'eef0090d0b8815354a31943767b0a32f',	'2024-03-29 09:06:55',	1);

-- 2024-02-28 09:58:36
