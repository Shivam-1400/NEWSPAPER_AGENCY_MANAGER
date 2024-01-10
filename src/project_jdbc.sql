-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 10, 2024 at 04:31 PM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 7.3.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `project_jdbc`
--

-- --------------------------------------------------------

--
-- Table structure for table `area_master`
--

CREATE TABLE `area_master` (
  `Area` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `area_master`
--

INSERT INTO `area_master` (`Area`) VALUES
('Bathinda'),
('Chandigarh'),
('Gaya'),
('Haryana'),
('Kanpur'),
('Lucknow'),
('Meerut'),
('Mumbai'),
('New Delhi'),
('Noida'),
('Patna'),
('Punjab'),
('Telangana'),
('Varanasi');

-- --------------------------------------------------------

--
-- Table structure for table `bills`
--

CREATE TABLE `bills` (
  `GenKey` int(10) NOT NULL,
  `Phone` bigint(15) NOT NULL,
  `Start` date NOT NULL,
  `End` date NOT NULL,
  `Bill` float NOT NULL,
  `Status` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `bills`
--

INSERT INTO `bills` (`GenKey`, `Phone`, `Start`, `End`, `Bill`, `Status`) VALUES
(1, 9262, '2022-06-20', '2022-06-21', 10005, 1),
(2, 62032, '2022-06-20', '2022-06-21', 5, 0),
(3, 8528005240, '2022-06-20', '2022-06-21', 12, 1),
(4, 8874360688, '2021-11-21', '2022-06-21', 1696, 0),
(5, 9956808517, '2021-11-21', '2022-07-27', 2480, 1),
(6, 9956808517, '2022-07-27', '2022-07-28', 10, 1),
(7, 9956808517, '2022-07-28', '2022-08-03', 60, 1),
(8, 8092852606, '2022-11-14', '2022-11-15', 17, 1),
(9, 9956808517, '2022-08-03', '2022-11-01', 900, 1),
(10, 8092852606, '2022-11-15', '2022-11-16', 17, 0);

-- --------------------------------------------------------

--
-- Table structure for table `coustomers`
--

CREATE TABLE `coustomers` (
  `Phone` bigint(15) NOT NULL,
  `Name` varchar(30) NOT NULL,
  `Address` varchar(50) NOT NULL,
  `Area` varchar(50) NOT NULL,
  `Hawker` varchar(30) NOT NULL,
  `SelePaper` varchar(50) NOT NULL,
  `TotalPrice` float NOT NULL,
  `DateStart` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `coustomers`
--

INSERT INTO `coustomers` (`Phone`, `Name`, `Address`, `Area`, `Hawker`, `SelePaper`, `TotalPrice`, `DateStart`) VALUES
(9262, 'shivam', 'dtygvb', 'gaya', 'haarshit', 'harshit ,Dainik Bhaskar ', 10005, '2022-06-21'),
(62032, 'Shivam', 'wefa', 'Sitamarhi', 'Varun', 'Amar Uajal ', 5, '2022-06-21'),
(7675016415, 'Sagar', 'xyz', 'Chandigarh', 'Sagar', 'Hindustan Dainik ,Hindustan Times ', 9, '2022-06-20'),
(8092852606, 'Satyam', 'xcv', 'Punjab', 'Satyam Lal', 'The Pioneer ,zzz ', 17, '2022-11-16'),
(8528005240, 'Aman', 'Varansi', 'Varansi', 'Ankit', 'Dainik Jagran ,Hindustan Times ,Amar Ujala ', 12, '2022-06-21'),
(8874360688, 'Neha', 'Delhi', 'Delhi', 'Saksham', 'Dainik Jagran ,Indian Express ', 8, '2022-06-21'),
(9956808517, 'Shivam', 'xyz', 'Chandigarh', 'Siddhart', 'Amar Ujala ,Hindustan Dainik ', 10, '2022-11-01');

-- --------------------------------------------------------

--
-- Table structure for table `hawkers`
--

CREATE TABLE `hawkers` (
  `Name` varchar(30) NOT NULL,
  `Contact` bigint(12) NOT NULL,
  `PicPath` varchar(500) NOT NULL,
  `Address` varchar(500) NOT NULL,
  `Aadhar` bigint(16) NOT NULL,
  `AreaAssigned` varchar(100) NOT NULL,
  `DateOfJoining` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `hawkers`
--

INSERT INTO `hawkers` (`Name`, `Contact`, `PicPath`, `Address`, `Aadhar`, `AreaAssigned`, `DateOfJoining`) VALUES
('Ankit', 1212121212, 'C:\\Users\\Dell\\Pictures\\project newspaper\\profile2.jpg', 'Varansi', 9898989898, 'Varansi', '2021-11-23'),
('haarshit', 456, 'C:\\Users\\Dell\\Pictures\\project newspaper\\profile2.jpg', '456', 465, 'Gaya', '2022-05-02'),
('Radhe', 1212, 'C:\\Users\\Dell\\Pictures\\IMG_20220520_122023.jpg', '1212', 12, 'Patna', '2022-06-08'),
('Raj Shekhar', 1515151, 'C:\\Users\\Dell\\Pictures\\IMG_20220520_122023.jpg', '78787878', 1515151515, 'Lucknow', '2022-05-26'),
('Sagar', 12346, '', '564654654564', 456, 'Chandigarh', '2022-04-04'),
('Saksham', 123123123123, 'C:\\Users\\Dell\\Pictures\\project newspaper\\profile1.jpg', 'Delhi', 123123123123, 'Meerut, New Delhi, Noida', '2021-11-19'),
('Satyam Lal', 8092852606, 'C:\\Users\\Dell\\Pictures\\mst2.png', 'xyz', 12121212121, 'Punjab, Noida', '2022-11-14'),
('Siddhart', 456456456, 'C:\\Users\\Dell\\Pictures\\project newspaper\\profile2.jpg', 'Chandigarh', 45645645645, 'Chandigarh, Haryana', '2021-11-19'),
('Varun', 456, 'C:\\Users\\Dell\\Pictures\\project newspaper\\profile1.jpg', '456', 456, 'Kanpur', '2022-04-26');

-- --------------------------------------------------------

--
-- Table structure for table `newspaper_master`
--

CREATE TABLE `newspaper_master` (
  `Name` varchar(50) NOT NULL,
  `Price` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `newspaper_master`
--

INSERT INTO `newspaper_master` (`Name`, `Price`) VALUES
('abcd', 10),
('Amar Uajal', 5),
('Dainik Bhaskar', 7),
('Dainik Jagran', 4),
('Financial Express', 7),
('Hindustan Dainik', 5),
('Hindustan Times', 4),
('Mid Day', 8),
('Mint', 10),
('Navbharat Times', 6),
('O Heraldo', 4),
('The Economic Times', 7),
('The Hindu', 6),
('The Indian Express', 5),
('The Pioneer', 7),
('The Times of India', 6),
('zzz', 10);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `area_master`
--
ALTER TABLE `area_master`
  ADD PRIMARY KEY (`Area`),
  ADD UNIQUE KEY `Area` (`Area`);

--
-- Indexes for table `bills`
--
ALTER TABLE `bills`
  ADD PRIMARY KEY (`GenKey`);

--
-- Indexes for table `coustomers`
--
ALTER TABLE `coustomers`
  ADD PRIMARY KEY (`Phone`);

--
-- Indexes for table `hawkers`
--
ALTER TABLE `hawkers`
  ADD PRIMARY KEY (`Name`);

--
-- Indexes for table `newspaper_master`
--
ALTER TABLE `newspaper_master`
  ADD PRIMARY KEY (`Name`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bills`
--
ALTER TABLE `bills`
  MODIFY `GenKey` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
