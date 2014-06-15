# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 127.0.0.1 (MySQL 5.6.13)
# Database: tag2parking_db
# Generation Time: 2013-11-17 04:49:47 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table chain
# ------------------------------------------------------------

DROP TABLE IF EXISTS `chain`;

CREATE TABLE `chain` (
  `id` varchar(50) DEFAULT NULL,
  `key` varchar(50) DEFAULT NULL,
  `imei` varchar(16) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table index
# ------------------------------------------------------------

DROP TABLE IF EXISTS `index`;

CREATE TABLE `index` (
  `id` varchar(50) DEFAULT NULL,
  `table` varchar(25) DEFAULT NULL,
  `next` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `index` WRITE;
/*!40000 ALTER TABLE `index` DISABLE KEYS */;

INSERT INTO `index` (`id`, `table`, `next`)
VALUES
	(NULL,'chain','1'),
	(NULL,'key','1'),
	(NULL,'spot','1'),
	(NULL,'trace','1'),
	(NULL,'track','1');

/*!40000 ALTER TABLE `index` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table key
# ------------------------------------------------------------

DROP TABLE IF EXISTS `key`;

CREATE TABLE `key` (
  `id` varchar(50) DEFAULT NULL,
  `imei` varchar(16) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table spot
# ------------------------------------------------------------

DROP TABLE IF EXISTS `spot`;

CREATE TABLE `spot` (
  `id` varchar(50) DEFAULT NULL,
  `latitude` varchar(30) DEFAULT NULL,
  `longitude` varchar(30) DEFAULT NULL,
  `altitude` varchar(30) DEFAULT NULL,
  `key` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table trace
# ------------------------------------------------------------

DROP TABLE IF EXISTS `trace`;

CREATE TABLE `trace` (
  `id` varchar(50) DEFAULT NULL,
  `space1` varchar(10) DEFAULT NULL,
  `space2` varchar(10) DEFAULT NULL,
  `space3` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table track
# ------------------------------------------------------------

DROP TABLE IF EXISTS `track`;

CREATE TABLE `track` (
  `id` varchar(50) DEFAULT NULL,
  `key` varchar(50) DEFAULT NULL,
  `latitude` varchar(30) DEFAULT NULL,
  `longitude` varchar(30) DEFAULT NULL,
  `altitude` varchar(30) DEFAULT NULL,
  `acceleration` varchar(30) DEFAULT NULL,
  `speed` varchar(30) DEFAULT NULL,
  `trace` varchar(50) DEFAULT NULL,
  `duration` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
