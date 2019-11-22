DROP TABLE IF EXISTS `incentive_configuration`;
/
CREATE TABLE `incentive_configuration` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(45) DEFAULT NULL,
  `target` int(11) DEFAULT NULL,
  `amount` decimal(8,2) DEFAULT NULL,
  `valueType` varchar(45) DEFAULT NULL,
  `rule` varchar(45) DEFAULT NULL,
  `is_countable` int(11) DEFAULT NULL,
  `min_ctc` double DEFAULT NULL,
  `max_ctc` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `incentive_configuration`
--

INSERT INTO `incentive_configuration` VALUES (1,'AM',NULL,10.00,'%',NULL,NULL,NULL,NULL),(2,'BDM',NULL,10.00,'%',NULL,NULL,NULL,NULL),(3,'Lead',NULL,15.00,'%',NULL,NULL,NULL,NULL),(4,'recruiter',0,1000.00,'fixed','=',0,300001,500000),(5,'recruiter',1,1000.00,'fixed','=',1,500001,0),(6,'recruiter',2,3000.00,'fixed','=',1,500001,NULL),(7,'recruiter',3,5000.00,'fixed','>=',1,500001,NULL),(9,'recruiter',0,250.00,'fixed','=',0,0,240000),(10,'recruiter',0,500.00,'fixed','=',0,240001,300000);