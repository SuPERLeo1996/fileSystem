/*
Navicat MySQL Data Transfer

Source Server         : SpringMybatis
Source Server Version : 50155
Source Host           : 127.0.0.1:3306
Source Database       : file

Target Server Type    : MYSQL
Target Server Version : 50155
File Encoding         : 65001

Date: 2019-05-04 20:42:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for file_detail
-- ----------------------------
DROP TABLE IF EXISTS `file_detail`;
CREATE TABLE `file_detail` (
  `id` varchar(255) NOT NULL COMMENT '主键',
  `file_name` varchar(255) DEFAULT NULL COMMENT '文件名称',
  `file_url` varchar(255) DEFAULT NULL COMMENT '文件路径',
  `create_time` datetime DEFAULT NULL COMMENT '上传时间',
  `count` int(11) DEFAULT '0' COMMENT '下载次数',
  `user_id` varchar(255) DEFAULT '' COMMENT '创建人id',
  `file_suffix` varchar(255) DEFAULT NULL COMMENT '后缀',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
