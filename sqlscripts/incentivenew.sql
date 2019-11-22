CREATE TABLE `incentivenew` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `recId` bigint(20) DEFAULT NULL,
  `canId` bigint(20) DEFAULT NULL,
  `cr_Amount` decimal(8,2) DEFAULT NULL,
  `dr_Amount` decimal(8,2) DEFAULT NULL,
  `onBoraderd_date` date DEFAULT NULL,
  `isAmountDebited` int(11) DEFAULT '0',
  `abscondDate` date DEFAULT NULL,
  `typeOfIncentive` varchar(45) DEFAULT NULL,
  `newCount` int(11) DEFAULT NULL,
  `iscountable` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=932 DEFAULT CHARSET=latin1;
