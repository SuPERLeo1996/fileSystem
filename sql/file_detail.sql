/*
Navicat MySQL Data Transfer

Source Server         : 虚拟机
Source Server Version : 50717
Source Host           : 192.168.1.104:3306
Source Database       : file

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2019-02-25 21:58:46
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
  `user_id` varchar(255) DEFAULT NULL COMMENT '创建人id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of file_detail
-- ----------------------------
INSERT INTO `file_detail` VALUES ('0d7ef220-d15e-4188-97b5-88f66963c7a3', 'Chrysanthemum.jpg', '/static/file/4ec5b856-19e1-4946-8f90-7bc1468ea96a.jpg', '2019-01-20 02:36:10', '17', null);
INSERT INTO `file_detail` VALUES ('1ba7bae0-3d91-4882-8eed-5a032a1217b6', 'Lighthouse.jpg', '/static/file/385df553-1824-4eb6-8d37-c0ce494aa148.jpg', '2019-01-20 13:05:46', '6', null);
INSERT INTO `file_detail` VALUES ('2e3749ae-52a0-4ed0-89c0-1e767d43e191', 'qrcode.png', '/static/file/448ea6ed-1ed3-4549-bdc0-cc73baa2fab6.png', '2019-01-27 14:12:22', '0', null);
INSERT INTO `file_detail` VALUES ('57c385bb-dfbb-458e-9ea3-83037af834ec', 'zxing.png', '/static/file/6fba2009-45db-4f0b-8fcd-398438279768.png', '2019-01-27 14:05:36', '1', null);
INSERT INTO `file_detail` VALUES ('74ab4e62-68c4-4c29-b1a0-b1d84b2e5627', '简历.doc', '/static/file/fdacdec7-798f-4947-9202-5ec75882eb0c.doc', '2019-01-20 13:21:24', '4', null);
INSERT INTO `file_detail` VALUES ('76ef7ea2-f0c7-4ce3-973a-3e75ee904732', 'zxing.png', '/static/file/1c29cfe9-6e3c-47cd-a408-cfc1e9591380.png', '2019-01-27 14:10:59', '0', null);
INSERT INTO `file_detail` VALUES ('8cdfc642-0904-4c24-90b6-657125e2a1cf', '简历.pdf', '/static/file/7991e883-7cac-46cc-8cba-2e618192d93a.pdf', '2019-01-27 14:12:06', '0', null);
INSERT INTO `file_detail` VALUES ('94848217-c1a7-4faa-b466-1a6d1277f191', 'qrcode.png', '/static/file/db378382-94e4-4d95-a64c-fa72fc5f5444.png', '2019-01-27 14:07:24', '0', null);
INSERT INTO `file_detail` VALUES ('bd1a7988-8d45-4d87-968c-f903c57f5700', '简历.pdf', '/static/file/04c0b0cb-b159-4993-9961-ef8ab84d14e7.pdf', '2019-01-20 13:27:24', '1', null);
INSERT INTO `file_detail` VALUES ('c6e5d02f-1fae-40cb-8f07-581a82deb870', 'QQ截图20181007161348.jpg', '/static/file/748376ac-496d-434c-b754-b9c51f4e0fbe.jpg', '2019-01-27 14:10:59', '0', null);
INSERT INTO `file_detail` VALUES ('cce2b21e-f6ca-463b-8dc7-47f6d5832e29', 'QQ截图20181007161348.jpg', '/static/file/6ad283a2-b52b-49da-901c-77cb73234662.jpg', '2019-01-27 14:12:22', '0', null);
INSERT INTO `file_detail` VALUES ('d34ae278-71e0-443c-af1d-a8d0a1f76d71', '简历.pdf', '/static/file/c7ce16fc-39fd-4d0a-a5bb-0470a9f119b5.pdf', '2019-01-27 14:12:22', '0', null);
INSERT INTO `file_detail` VALUES ('f814bbf5-a2b1-4234-9e2b-bbe494d127bc', '简历.doc', '/static/file/c56ceca2-2a9a-408c-bad7-647fc2d0a95a.doc', '2019-01-27 14:12:22', '0', null);
INSERT INTO `file_detail` VALUES ('f8461516-5ba4-4da0-b68a-37247ed5c420', 'qrcode.png', '/static/file/bbdba051-a7ff-4664-ac25-7970afdf2ffb.png', '2019-01-27 14:10:59', '0', null);
INSERT INTO `file_detail` VALUES ('ffdceb59-a9e0-4f41-9061-5878c1be4b97', 'zxing.png', '/static/file/504bdde8-235b-4521-9aad-5dd8849b93d4.png', '2019-01-27 14:12:22', '0', null);
