-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: tree
-- ------------------------------------------------------
-- Server version	5.7.20-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (1);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tree`
--

DROP TABLE IF EXISTS `tree`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tree` (
  `idtree` int(11) NOT NULL AUTO_INCREMENT,
  `idparent` int(11) NOT NULL,
  `name_leaf` varchar(255) NOT NULL,
  `number_left` int(11) NOT NULL,
  `number_right` int(11) NOT NULL,
  PRIMARY KEY (`idtree`),
  KEY `fk_parent_idx` (`idparent`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tree`
--

LOCK TABLES `tree` WRITE;
/*!40000 ALTER TABLE `tree` DISABLE KEYS */;
INSERT INTO `tree` VALUES (1,0,'Krajobraz',1,40),(2,1,'Polska',2,25),(3,1,'Francja',26,39),(4,2,'Gory',3,12),(5,2,'Rzeki',13,24),(7,3,'Miasta',27,34),(8,4,'Tatry',4,5),(9,4,'Beskidy',6,7),(10,5,'Odra',20,21),(11,5,'Wisla',22,23),(13,7,'Paryz',30,31),(15,28,'Bug',16,17),(16,5,'San',14,15),(17,7,'Lile',28,29),(19,4,'Sudety',8,9),(22,7,'Lyon',32,33),(23,3,'Morza',35,38),(27,23,'Polnocne',36,37),(28,5,'Wislok',18,19),(29,4,'Warta',10,11);
/*!40000 ALTER TABLE `tree` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'tree'
--

--
-- Dumping routines for database 'tree'
--
/*!50003 DROP PROCEDURE IF EXISTS `add_nodeOrLeaf` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `add_nodeOrLeaf`(in nameLeaf varchar(45), in idParent int(11))
BEGIN

DECLARE nRight int(11);

Select number_right into nRight FROM tree where tree.idtree = idParent;

UPDATE tree SET number_right=number_right+2 WHERE number_right >= nRight; 
UPDATE tree SET number_left=number_left+2 WHERE number_left >= nRight; 

insert into tree(idParent,name_leaf,number_left,number_right) values(idParent,nameLeaf,nRight,nRight+1);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `deleteNode` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteNode`(in idtree int(11))
BEGIN
Declare nLeft int(11);
Declare nRight int(11);
Select number_left,number_right into nLeft,nRight From tree where tree.idtree=idtree; 

Delete from tree where number_left between nLeft and nRight;

UPDATE tree SET number_right=number_right-(nRight-nLeft+1) where number_right >= nLeft;

UPDATE tree SET number_left=number_left-(nRight-nLeft+1) where number_left >= nLeft;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `moveNode` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `moveNode`(in idtree int(11),in idParent int(11), in nameLeaf varchar(45))
BEGIN

DECLARE nRight int(11);
DECLARE nLeft int(11);
Declare oldRight int(11);
Declare oldLeft int(11);

Select number_left,number_right into oldLeft,oldRight From tree where tree.idtree = idtree;
Select number_left,number_right into nLeft,nRight FROM tree where tree.idtree = idParent;
SET nRight = nLeft+(oldRight-oldLeft); 
UPDATE tree SET name_leaf=nameLeaf, idparent=idParent,number_left=nLeft-1,number_right=nRight-1 WHERE tree.idtree = idtree and nLeft>oldLeft;
UPDATE tree SET name_leaf=nameLeaf, idparent=idParent,number_left=nLeft+1,number_right=nRight+1 WHERE tree.idtree = idtree and nLeft<oldLeft;

Update tree SET number_left=number_left-(oldRight-oldLeft+1) where nLeft>oldLeft and tree.idtree!=idtree and number_left>=oldLeft and number_left<=nLeft;
Update tree SET number_right=number_right-(oldRight-oldLeft+1) where nRight>oldRight and tree.idtree!=idtree and number_right>=oldRight and number_right<=nRight;

Update tree SET number_left=number_left+(oldRight-oldLeft+1) where nLeft<oldLeft and tree.idtree!=idtree and number_left<=oldLeft and number_left>=nLeft;
Update tree SET number_right=number_right+(oldRight-oldLeft+1) where nRight<oldRight and tree.idtree!=idtree and number_right<=oldRight and number_right>=nRight;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-13  4:54:54
