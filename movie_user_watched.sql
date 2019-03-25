-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 25, 2019 at 01:08 PM
-- Server version: 10.1.37-MariaDB
-- PHP Version: 7.1.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `user_database`
--

-- --------------------------------------------------------

--
-- Table structure for table `movie_user_watched`
--

CREATE TABLE `movie_user_watched` (
  `recordID` int(11) NOT NULL,
  `userName` varchar(30) NOT NULL,
  `movieID` int(11) NOT NULL,
  `timeStamp` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `movie_user_watched`
--

INSERT INTO `movie_user_watched` (`recordID`, `userName`, `movieID`, `timeStamp`) VALUES
(1, 'smithj', 15, '2019-03-20 17:00:00'),
(7, 'wenyu', 16, '2019-03-24 13:51:13'),
(8, 'wenyu', 102, '2019-03-24 19:17:26'),
(9, 'wenyu', 333, '2019-03-24 19:20:02'),
(10, 'wenyu', 255, '2019-03-24 19:23:27'),
(11, 'wenyu', 17, '2019-03-24 19:25:33'),
(12, 'wenyu', 110, '2019-03-24 19:31:59'),
(13, 'wenyu', 23, '2019-03-24 19:33:23'),
(14, 'wenyu', 25, '2019-03-24 19:34:02'),
(15, 'wenyu', 26, '2019-03-24 19:34:38'),
(16, 'wenyu', 27, '2019-03-24 19:35:12'),
(17, 'wenyu', 28, '2019-03-24 19:37:34'),
(18, 'wenyu', 36, '2019-03-24 19:38:56'),
(19, 'wenyu', 99, '2019-03-24 19:39:38');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `movie_user_watched`
--
ALTER TABLE `movie_user_watched`
  ADD PRIMARY KEY (`recordID`),
  ADD KEY `movieID` (`movieID`),
  ADD KEY `userName` (`userName`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `movie_user_watched`
--
ALTER TABLE `movie_user_watched`
  MODIFY `recordID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
