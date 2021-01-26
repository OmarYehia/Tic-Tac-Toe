-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: localhost    Database: tictactoe
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `game`
--

DROP TABLE IF EXISTS `game`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `game` (
  `game_id` int(11) NOT NULL AUTO_INCREMENT,
  `player1_id` int(10) DEFAULT NULL,
  `player2_id` int(10) DEFAULT NULL,
  `game_date` date DEFAULT NULL,
  `result` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`game_id`),
  KEY `player1_id_fk` (`player1_id`),
  KEY `player2_id_fk` (`player2_id`),
  CONSTRAINT `player1_id_fk` FOREIGN KEY (`player1_id`) REFERENCES `player` (`id`),
  CONSTRAINT `player2_id_fk` FOREIGN KEY (`player2_id`) REFERENCES `player` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=202 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `player`
--

DROP TABLE IF EXISTS `player`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `player` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `score` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `steps`
--

DROP TABLE IF EXISTS `steps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `steps` (
  `step_id` int(11) NOT NULL AUTO_INCREMENT,
  `game_id` int(11) NOT NULL,
  `place_i` int(1) NOT NULL,
  `place_j` int(1) NOT NULL,
  `player_id` int(10) NOT NULL,
  PRIMARY KEY (`step_id`,`game_id`),
  UNIQUE KEY `step_id_UNIQUE` (`step_id`),
  KEY `game_id_idx` (`game_id`),
  KEY `id_idx` (`player_id`),
  CONSTRAINT `game_id` FOREIGN KEY (`game_id`) REFERENCES `game` (`game_id`),
  CONSTRAINT `id` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=837 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-26 22:05:32
