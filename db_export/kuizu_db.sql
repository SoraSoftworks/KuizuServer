-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Dec 18, 2015 at 04:09 PM
-- Server version: 5.6.27-0ubuntu0.14.04.1
-- PHP Version: 5.5.9-1ubuntu4.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `quizzduel`
--

-- --------------------------------------------------------

--
-- Table structure for table `duel`
--

CREATE TABLE IF NOT EXISTS `duel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_user1` int(11) NOT NULL,
  `id_user2` int(11) NOT NULL,
  `play_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `winner_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_user1` (`id_user1`,`id_user2`,`winner_id`),
  KEY `id_user1_2` (`id_user1`),
  KEY `id_user2` (`id_user2`),
  KEY `winner_id` (`winner_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=188 ;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phone_num` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `user_id` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `email_addr` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password_` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `join_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `elo` int(11) NOT NULL DEFAULT '1000',
  `last_login` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`),
  UNIQUE KEY `phone_num` (`phone_num`),
  UNIQUE KEY `phone_num_2` (`phone_num`),
  UNIQUE KEY `email_addr` (`email_addr`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=29 ;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `duel`
--
ALTER TABLE `duel`
  ADD CONSTRAINT `duel_ibfk_1` FOREIGN KEY (`id_user1`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `duel_ibfk_2` FOREIGN KEY (`id_user2`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `duel_ibfk_3` FOREIGN KEY (`winner_id`) REFERENCES `user` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
