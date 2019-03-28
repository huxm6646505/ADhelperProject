-- MySQL dump 10.13  Distrib 5.6.24, for Win32 (x86)
--
-- Host: localhost    Database: halo
-- ------------------------------------------------------
-- Server version	5.6.26-log

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
-- Table structure for table `sys_admin`
--

DROP TABLE IF EXISTS `sys_admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_admin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_date` datetime DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `del_flag` varchar(255) DEFAULT NULL,
  `login_count` int(11) DEFAULT NULL,
  `login_time` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `last_modified_by` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `member_id` bigint(20) DEFAULT NULL,
  `locked` int(11) DEFAULT NULL,
  `created_by_id` bigint(20) DEFAULT NULL,
  `last_modified_by_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ibk908ihrhemi1tkin6evpvap` (`created_by`),
  KEY `FK_tgmqegdadd5mwj5uga1o4374u` (`last_modified_by`),
  KEY `FKi8pwrf4wmb1n2atcj3fjnmt0l` (`created_by_id`),
  KEY `FKjt8516ov1gjli7ir3h4ea4tbg` (`last_modified_by_id`),
  KEY `FKdb7r9l2vberxhb3161j2979u7` (`member_id`),
  KEY `FKaj2ainf1qddg8dxqj2lxjihid` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_admin`
--

LOCK TABLES `sys_admin` WRITE;
/*!40000 ALTER TABLE `sys_admin` DISABLE KEYS */;
INSERT INTO `sys_admin` VALUES (1,NULL,'2016-12-28 16:29:32','0',NULL,'2016-12-28 16:29:32',NULL,NULL,1,1,1,NULL,NULL),(2,'2016-12-20 10:49:23','2016-12-28 15:40:18','0',NULL,'2016-12-28 15:40:18',NULL,NULL,2,2,NULL,NULL,NULL),(5,'2016-12-28 15:56:07','2016-12-28 15:56:24','0',NULL,'2016-12-28 15:56:24',NULL,NULL,5,5,NULL,1,NULL);
/*!40000 ALTER TABLE `sys_admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_admin_role`
--

DROP TABLE IF EXISTS `sys_admin_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_admin_role` (
  `admin_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`admin_id`,`role_id`),
  KEY `FK4k43lklts7m27m6gyy75qvg4h` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_admin_role`
--

LOCK TABLES `sys_admin_role` WRITE;
/*!40000 ALTER TABLE `sys_admin_role` DISABLE KEYS */;
INSERT INTO `sys_admin_role` VALUES (1,2),(2,3),(5,3);
/*!40000 ALTER TABLE `sys_admin_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_function`
--

DROP TABLE IF EXISTS `sys_function`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_function` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_date` datetime DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `del_flag` varchar(255) DEFAULT NULL,
  `is_active` bit(1) NOT NULL,
  `description` varchar(250) DEFAULT NULL,
  `funcs` longtext,
  `level` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `type` int(11) NOT NULL,
  `url` varchar(200) DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `last_modified_by` bigint(20) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `shiro_permission` varchar(200) DEFAULT NULL,
  `created_by_id` bigint(20) DEFAULT NULL,
  `last_modified_by_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_3q7hknw8owbced0vw2yuuurdk` (`created_by`),
  KEY `FK_rvd61ht00k95ax0hxiygen7ji` (`last_modified_by`),
  KEY `FKcmk69t5dn4ivx3imkf0wgb3oh` (`created_by_id`),
  KEY `FKn9wxeyqat0do7ri76xc43l86n` (`last_modified_by_id`),
  KEY `FKmp2cmbi9l9c1c7618t2o0v2xb` (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_function`
--

LOCK TABLES `sys_function` WRITE;
/*!40000 ALTER TABLE `sys_function` DISABLE KEYS */;
INSERT INTO `sys_function` VALUES (1,'2016-12-08 10:53:29','2016-12-13 10:54:46','0','',NULL,NULL,1,'系统设置',1,NULL,NULL,NULL,NULL,'sys:setting',NULL,NULL),(41,'2016-12-09 18:25:39','2016-12-13 10:55:08','0','',NULL,NULL,1,'组织管理',2,NULL,NULL,NULL,1,'setting:org',NULL,NULL),(47,'2016-12-09 18:45:37','2016-12-13 11:28:32','0','',NULL,NULL,1,'用户管理',3,'/admin/index.htm',NULL,NULL,41,'org:admin',NULL,NULL),(49,'2016-12-09 18:46:15','2016-12-15 11:12:29','0','',NULL,NULL,3,'角色管理',3,'/role/index.htm',NULL,NULL,41,'org:role',NULL,NULL),(50,'2016-12-10 11:02:34','2016-12-13 10:56:25','0','',NULL,NULL,4,'菜单管理',3,'/function/index.htm',NULL,NULL,41,'org:function',NULL,NULL);
/*!40000 ALTER TABLE `sys_function` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_member`
--

DROP TABLE IF EXISTS `sys_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_date` datetime DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `del_flag` varchar(255) DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `head_url` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `last_modified_by` bigint(20) DEFAULT NULL,
  `created_by_id` bigint(20) DEFAULT NULL,
  `last_modified_by_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_7rr3aofbsa9aj9ie7dpv7rcnx` (`created_by`),
  KEY `FK_auf8yvku6tsub5r4xp6kl9alt` (`last_modified_by`),
  KEY `FK3rxlooh1j92kw3ivy6v5qoj6` (`created_by_id`),
  KEY `FK6mhqn6vrulwjsxk4a2d7wrh4g` (`last_modified_by_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_member`
--

LOCK TABLES `sys_member` WRITE;
/*!40000 ALTER TABLE `sys_member` DISABLE KEYS */;
INSERT INTO `sys_member` VALUES (1,'2016-12-19 16:49:39','2016-12-19 16:49:39','0','2014-11-02 00:00:00',NULL,'张三',1,NULL,NULL,NULL,NULL),(2,'2016-12-20 10:49:23','2016-12-20 10:49:23','0','1990-12-29 00:00:00',NULL,'李四',1,NULL,NULL,NULL,NULL),(5,'2016-12-28 15:56:07','2016-12-28 15:56:07','0','2014-11-11 00:00:00',NULL,'zhangsan',1,NULL,NULL,1,1);
/*!40000 ALTER TABLE `sys_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_date` datetime DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `del_flag` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `last_modified_by` bigint(20) DEFAULT NULL,
  `created_by_id` bigint(20) DEFAULT NULL,
  `last_modified_by_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_qh367bwicu6iyp24hxv8jy59i` (`created_by`),
  KEY `FK_gkrfx96u5klwo6kl8venj2xrg` (`last_modified_by`),
  KEY `FK23ouv9o3uurxxafmn6usgbimc` (`created_by_id`),
  KEY `FK9r92sdg54obgpe1vxeawbpcny` (`last_modified_by_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (2,'2016-12-20 11:20:21','2016-12-28 11:40:35','0',NULL,'管理员',NULL,NULL,NULL,2),(3,'2016-12-27 16:13:53','2016-12-27 16:13:59','0',NULL,'普通用户',NULL,NULL,2,2);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_function`
--

DROP TABLE IF EXISTS `sys_role_function`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_function` (
  `role_id` bigint(20) NOT NULL,
  `function_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`function_id`),
  KEY `FK88qkpt72k568vrjh9m9cnt7qx` (`function_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_function`
--

LOCK TABLES `sys_role_function` WRITE;
/*!40000 ALTER TABLE `sys_role_function` DISABLE KEYS */;
INSERT INTO `sys_role_function` VALUES (2,1),(2,41),(2,47),(2,49),(2,50);
/*!40000 ALTER TABLE `sys_role_function` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_date` datetime DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `del_flag` varchar(255) DEFAULT NULL,
  `last_login_time` datetime DEFAULT NULL,
  `login_ip` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `last_modified_by` bigint(20) DEFAULT NULL,
  `created_by_id` bigint(20) DEFAULT NULL,
  `last_modified_by_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_k65ah1e919j1a7kjt9rhw740m` (`created_by`),
  KEY `FK_kn6j0y7diieq9j5spisdxvtir` (`last_modified_by`),
  KEY `FKkg6bp76acdeeklv8yxr2cxlft` (`created_by_id`),
  KEY `FKq4cue2a5inq6ijkrfrqrqooad` (`last_modified_by_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,NULL,NULL,NULL,NULL,NULL,'8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918','admin',NULL,NULL,NULL,NULL),(2,'2016-12-20 10:49:23','2016-12-20 10:55:49','0',NULL,NULL,'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','halo',NULL,NULL,NULL,NULL),(5,'2016-12-28 15:56:07','2016-12-28 15:56:07','0',NULL,NULL,'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','zhangsan',NULL,NULL,1,1);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-12-28 16:35:52
