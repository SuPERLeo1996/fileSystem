/*
Navicat MySQL Data Transfer

Source Server         : 虚拟机
Source Server Version : 50717
Source Host           : 192.168.1.104:3306
Source Database       : file

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2019-02-25 21:58:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(255) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `role` varchar(50) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `last_ip` varchar(50) DEFAULT NULL,
  `last_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('7bf57743069443a09ab3bc513cfacec9', 'test', '123', '123', '123', null, null, null, null);
