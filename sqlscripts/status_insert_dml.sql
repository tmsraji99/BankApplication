/*
SQLyog Community
MySQL - 5.7.17-log : Database - mainrpo
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mainrpo` /*!40100 DEFAULT CHARACTER SET utf8 */;

/*Table structure for table `status` */

DROP TABLE IF EXISTS `status`;

CREATE TABLE `status` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` datetime DEFAULT NULL,
  `roleName` varchar(255) DEFAULT NULL,
  `status` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_5uoq7ng0nx4yjy41l0384ityf` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

/*Data for the table `status` */

insert  into `status`(`id`,`date`,`roleName`,`status`) values 
(1,'2017-05-25 15:41:53','bdmlead','Profile shortlisted by BdmLead'),
(2,'2017-05-25 15:42:04','bdmlead','BdmLead Rejected'),
(3,'2017-05-25 15:42:24','bdmlead','BdmLead ReviewPending'),
(4,'2017-05-25 15:42:40','bdmlead','Submitted to Customer'),
(5,'2017-05-25 15:42:59','bdmlead','Customer Shortlisted'),
(6,'2017-05-25 15:43:12','bdmlead','Customer Rejected'),
(7,'2017-05-25 15:43:28','bdmlead','Customer feedback'),
(8,'2017-05-25 15:43:43','bdmlead','BdmLead Query'),
(9,'2017-05-25 15:44:02','bdmlead','Offered'),
(10,'2017-05-25 15:44:18','recruiter','UploadDocuments'),
(11,'2017-05-25 15:44:35','recruiter	','Submit to BdmLead'),
(12,'2017-05-25 15:44:54','recruiter','Interview FeedBack'),
(13,'2017-05-25 15:45:12','recruiter','interview schedule'),
(14,'2017-05-30 12:38:23','bdmlead','Offer Release'),
(15,'2017-06-12 12:16:14','recruiter','Offer Status');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
