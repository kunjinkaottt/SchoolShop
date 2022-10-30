/*
Navicat MySQL Data Transfer

Source Server         : alldata
Source Server Version : 80026
Source Host           : localhost:3306
Source Database       : shop

Target Server Type    : MYSQL
Target Server Version : 80026
File Encoding         : 65001

Date: 2022-08-01 10:41:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `aid` int NOT NULL AUTO_INCREMENT,
  `number` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`aid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('1', 'hhh', 'hhh');

-- ----------------------------
-- Table structure for `apply`
-- ----------------------------
DROP TABLE IF EXISTS `apply`;
CREATE TABLE `apply` (
  `applyid` int NOT NULL AUTO_INCREMENT,
  `mid` int NOT NULL,
  `shopid` int NOT NULL,
  `applytime` varchar(40) DEFAULT NULL,
  `applystate` int NOT NULL,
  `bid` int NOT NULL,
  PRIMARY KEY (`applyid`),
  KEY `bid` (`bid`),
  KEY `shopid` (`shopid`),
  KEY `mid` (`mid`),
  CONSTRAINT `apply_ibfk_1` FOREIGN KEY (`bid`) REFERENCES `business` (`bid`) ON DELETE CASCADE,
  CONSTRAINT `apply_ibfk_2` FOREIGN KEY (`shopid`) REFERENCES `shop` (`shopid`) ON DELETE CASCADE,
  CONSTRAINT `apply_ibfk_3` FOREIGN KEY (`mid`) REFERENCES `members` (`mid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of apply
-- ----------------------------
INSERT INTO `apply` VALUES ('9', '2', '1', '2022-01-08 14:58:15', '1', '1');
INSERT INTO `apply` VALUES ('10', '4', '5', '2022-01-11 15:00:24', '1', '6');
INSERT INTO `apply` VALUES ('11', '5', '5', '2022-01-12 15:00:59', '1', '6');
INSERT INTO `apply` VALUES ('12', '6', '5', '2022-01-13 15:01:28', '1', '6');
INSERT INTO `apply` VALUES ('13', '7', '5', '2022-01-14 15:01:47', '1', '6');
INSERT INTO `apply` VALUES ('15', '9', '8', '2022-01-16 15:02:54', '1', '7');
INSERT INTO `apply` VALUES ('19', '10', '4', '2022-01-23 15:02:30', '1', '2');
INSERT INTO `apply` VALUES ('21', '3', '1', '2022-02-11 22:17:40', '2', '1');
INSERT INTO `apply` VALUES ('29', '3', '4', '2022-03-17 00:07:38', '1', '2');
INSERT INTO `apply` VALUES ('45', '16', '1', '2022-04-08 22:33:14', '2', '1');
INSERT INTO `apply` VALUES ('47', '16', '4', '2022-04-08 23:46:51', '1', '2');
INSERT INTO `apply` VALUES ('48', '17', '1', '2022-04-08 23:57:06', '2', '1');
INSERT INTO `apply` VALUES ('49', '17', '4', '2022-04-08 23:57:18', '2', '2');
INSERT INTO `apply` VALUES ('52', '17', '17', '2022-04-09 00:07:56', '0', '9');
INSERT INTO `apply` VALUES ('53', '17', '24', '2022-04-09 00:12:45', '0', '25');
INSERT INTO `apply` VALUES ('59', '1', '4', '2022-04-12 23:47:11', '0', '2');
INSERT INTO `apply` VALUES ('61', '10', '24', '2022-04-17 15:51:02', '0', '25');
INSERT INTO `apply` VALUES ('64', '10', '1', '2022-04-17 16:38:48', '1', '1');
INSERT INTO `apply` VALUES ('79', '1', '1', '2022-04-20 15:26:38', '1', '1');
INSERT INTO `apply` VALUES ('81', '1', '24', '2022-04-26 11:59:44', '2', '25');
INSERT INTO `apply` VALUES ('83', '1', '17', '2022-04-26 12:00:06', '2', '9');
INSERT INTO `apply` VALUES ('86', '3', '7', '2022-05-13 22:33:43', '1', '16');
INSERT INTO `apply` VALUES ('87', '1', '26', '2022-05-13 22:54:52', '0', '32');
INSERT INTO `apply` VALUES ('88', '1', '34', '2022-05-13 22:55:15', '0', '37');
INSERT INTO `apply` VALUES ('89', '1', '7', '2022-05-13 22:55:49', '0', '16');
INSERT INTO `apply` VALUES ('90', '1', '19', '2022-05-14 15:43:02', '0', '43');
INSERT INTO `apply` VALUES ('91', '1', '11', '2022-06-05 16:35:49', '0', '35');

-- ----------------------------
-- Table structure for `business`
-- ----------------------------
DROP TABLE IF EXISTS `business`;
CREATE TABLE `business` (
  `bid` int NOT NULL AUTO_INCREMENT,
  `truename` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '鍚庢湡鍙互鍦ㄤ釜浜轰俊鎭坊鍔犱慨鏀?',
  `gender` int NOT NULL COMMENT '1閻?婵?閸氬孩婀￠崣顖欎簰閸︺劋閲滄禍杞颁繆閹垱鍧婇崝鐘辨叏閺€?',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '濠电偞鍨堕幐鎾磻閹炬枼妲堥柟鍨暕缁ㄨ崵鎲搁弶鎸庡仴闁诡垰绉甸幏鍛槹鎼搭喕绱樺┑鐐村灦閹尖晜绂嶅鍫濈柧闁靛繆鎳囬崑?闂備礁鎲￠悷顖炲垂闁秴鏋?   婵犵數鍋涢ˇ顓㈠礉瀹€鍕埞濞寸姴顑呯猾宥夋偣閾忕懓鐨戠紓宥嗙墵閺?',
  `password` varchar(16) NOT NULL COMMENT '注册时填入',
  `shopid` int NOT NULL COMMENT '店铺是申请的时候添加的 注册时填入',
  `state` int NOT NULL COMMENT '0未审 1过审 2不过审',
  PRIMARY KEY (`bid`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of business
-- ----------------------------
INSERT INTO `business` VALUES ('1', '王五', '1', '15711110111', '1', '1', '1');
INSERT INTO `business` VALUES ('2', '李四', '1', '13426827086', 'ls', '4', '1');
INSERT INTO `business` VALUES ('4', '劳尔', '2', '24681013579', 'le', '2', '1');
INSERT INTO `business` VALUES ('6', '佟湘玉', '2', '46546546546', 'txy', '5', '1');
INSERT INTO `business` VALUES ('7', '蟹老板', '1', '13739275251', 'xlb', '8', '1');
INSERT INTO `business` VALUES ('9', '测试商家', '2', '12312313126', 'cssj', '17', '1');
INSERT INTO `business` VALUES ('10', '顺风', '1', '23453654678', '123', '12', '1');
INSERT INTO `business` VALUES ('16', '腊十二', '2', '20221141410', '123', '7', '1');
INSERT INTO `business` VALUES ('25', '张三', '1', '20224021423', '1', '24', '1');
INSERT INTO `business` VALUES ('28', '三更', '1', '20220408003', '1', '35', '2');
INSERT INTO `business` VALUES ('32', '常威', '1', '20224191245', '1', '26', '1');
INSERT INTO `business` VALUES ('33', '来福', '1', '20224191246', '1', '32', '1');
INSERT INTO `business` VALUES ('35', '刘德华', '1', '20224201206', '1', '11', '1');
INSERT INTO `business` VALUES ('37', '叶畔湾', '1', '20225101327', '1', '34', '1');
INSERT INTO `business` VALUES ('38', 'aaa', '1', '12345678999', '1', '35', '2');
INSERT INTO `business` VALUES ('39', '亿核糖', '1', '20225111434', 'yht', '36', '1');
INSERT INTO `business` VALUES ('42', '张伟', '1', '20225122324', '123123', '35', '1');
INSERT INTO `business` VALUES ('43', '1123', '1', '20225141541', '1', '19', '1');
INSERT INTO `business` VALUES ('44', '周星星', '1', '20225141743', '1', '38', '0');

-- ----------------------------
-- Table structure for `comment`
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `cid` int NOT NULL AUTO_INCREMENT,
  `mid` int NOT NULL,
  `comment` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `grade` int NOT NULL,
  `ctime` varchar(35) DEFAULT NULL,
  `shopid` int NOT NULL,
  PRIMARY KEY (`cid`),
  KEY `mid` (`mid`),
  KEY `shopid` (`shopid`),
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`mid`) REFERENCES `members` (`mid`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`shopid`) REFERENCES `shop` (`shopid`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES ('2', '2', '评论早餐店2', '4', '2022-01-20 14:06:30', '1');
INSERT INTO `comment` VALUES ('3', '3', '评论了早餐店3', '5', '2022-01-20 14:07:00', '1');
INSERT INTO `comment` VALUES ('4', '4', '葵花点穴手', '3', '2022-02-22 14:35:15', '1');
INSERT INTO `comment` VALUES ('5', '3', '32', '2', '2022-02-12 17:11:33', '5');
INSERT INTO `comment` VALUES ('6', '2', '孙七修改评论', '4', '2022-02-13 0:20:33', '5');
INSERT INTO `comment` VALUES ('13', '1', ' 评论测试2', '3', '2022-04-18 21:32:33', '4');
INSERT INTO `comment` VALUES ('14', '1', '加一分', '1', '2022-03-14 10:28:47', '8');
INSERT INTO `comment` VALUES ('23', '3', '评论评论评论评论', '2', '2022-03-16 15:39:35', '8');
INSERT INTO `comment` VALUES ('24', '3', '评论评论评论评论评论', '1', '2022-03-16 15:39:50', '12');
INSERT INTO `comment` VALUES ('25', '3', '真·托尼老师', '5', '2022-03-17 23:37:20', '4');
INSERT INTO `comment` VALUES ('31', '1', '赵六评论', '5', '2022-04-04 22:35:50', '12');
INSERT INTO `comment` VALUES ('35', '5', '李大嘴', '4', '2022-04-10 20:14:00', '1');
INSERT INTO `comment` VALUES ('36', '6', '排山倒海', '4', '2022-04-10 20:14:10', '1');
INSERT INTO `comment` VALUES ('37', '7', '子曾经曰过', '5', '2022-04-10 20:14:20', '1');
INSERT INTO `comment` VALUES ('39', '9', '吹竖笛', '4', '2022-04-10 20:14:40', '1');
INSERT INTO `comment` VALUES ('40', '10', 'huang', '3', '2022-04-10 20:14:50', '1');
INSERT INTO `comment` VALUES ('41', '11', '黄某', '3', '2022-04-10 20:14:51', '1');
INSERT INTO `comment` VALUES ('42', '15', 'AAA', '2', '2022-04-10 20:14:52', '1');
INSERT INTO `comment` VALUES ('43', '16', 'ccc', '2', '2022-04-10 20:14:53', '1');
INSERT INTO `comment` VALUES ('47', '1', '1', '5', '2022-04-16 20:07:08', '24');
INSERT INTO `comment` VALUES ('48', '1', '1', '5', '2022-04-16 20:07:18', '17');
INSERT INTO `comment` VALUES ('49', '1', '123', '2', '2022-04-16 23:13:10', '5');
INSERT INTO `comment` VALUES ('50', '10', '拉低分数', '1', '2022-04-17 15:53:02', '24');
INSERT INTO `comment` VALUES ('52', '1', 'key test', '5', '2022-06-05 17:31:41', '1');
INSERT INTO `comment` VALUES ('54', '1', '132213', '5', '2022-05-14 15:45:07', '26');
INSERT INTO `comment` VALUES ('56', '1', '来福莱福', '4', '2022-04-26 15:02:55', '32');
INSERT INTO `comment` VALUES ('57', '3', '益合堂', '4', '2022-05-13 21:48:32', '36');
INSERT INTO `comment` VALUES ('58', '1', '我要应聘', '5', '2022-05-13 22:56:11', '7');
INSERT INTO `comment` VALUES ('59', '1', 'wsmmeiyplun', '5', '2022-06-08 16:30:20', '19');

-- ----------------------------
-- Table structure for `favorites`
-- ----------------------------
DROP TABLE IF EXISTS `favorites`;
CREATE TABLE `favorites` (
  `fid` int NOT NULL AUTO_INCREMENT,
  `mid` int NOT NULL COMMENT '外键约束',
  `shopid` int DEFAULT NULL COMMENT '澶栭敭绾︽潫',
  `gid` int DEFAULT NULL,
  `isfavorite` int NOT NULL DEFAULT '0' COMMENT '0nofavorite,1isfavorite',
  PRIMARY KEY (`fid`),
  KEY `mid` (`mid`),
  KEY `shopid` (`shopid`),
  CONSTRAINT `favorites_ibfk_1` FOREIGN KEY (`mid`) REFERENCES `members` (`mid`) ON DELETE CASCADE,
  CONSTRAINT `favorites_ibfk_2` FOREIGN KEY (`shopid`) REFERENCES `shop` (`shopid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of favorites
-- ----------------------------
INSERT INTO `favorites` VALUES ('1', '1', '1', '0', '1');
INSERT INTO `favorites` VALUES ('2', '1', '2', '0', '0');
INSERT INTO `favorites` VALUES ('3', '1', '11', '0', '0');
INSERT INTO `favorites` VALUES ('4', '1', '17', '0', '0');
INSERT INTO `favorites` VALUES ('12', '1', '19', '0', '1');
INSERT INTO `favorites` VALUES ('13', '1', '4', '0', '1');
INSERT INTO `favorites` VALUES ('14', '1', '5', '0', '0');
INSERT INTO `favorites` VALUES ('15', '1', '8', '0', '0');
INSERT INTO `favorites` VALUES ('16', '3', '19', '0', '0');
INSERT INTO `favorites` VALUES ('17', '3', '4', '0', '1');
INSERT INTO `favorites` VALUES ('18', '2', '19', '0', '0');
INSERT INTO `favorites` VALUES ('19', '2', '1', '0', '0');
INSERT INTO `favorites` VALUES ('20', '2', '8', '0', '1');
INSERT INTO `favorites` VALUES ('21', '2', '4', '0', '1');
INSERT INTO `favorites` VALUES ('22', '1', '1', '11', '1');
INSERT INTO `favorites` VALUES ('23', '2', '1', '11', '1');
INSERT INTO `favorites` VALUES ('24', '1', '1', '4', '1');
INSERT INTO `favorites` VALUES ('25', '3', '12', '0', '1');
INSERT INTO `favorites` VALUES ('26', '3', '8', '0', '1');
INSERT INTO `favorites` VALUES ('27', '3', '5', '0', '1');
INSERT INTO `favorites` VALUES ('28', '3', '11', '0', '0');
INSERT INTO `favorites` VALUES ('29', '3', '1', '0', '1');
INSERT INTO `favorites` VALUES ('30', '3', '2', '0', '1');
INSERT INTO `favorites` VALUES ('31', '3', '1', '11', '1');
INSERT INTO `favorites` VALUES ('32', '3', '1', '3', '1');
INSERT INTO `favorites` VALUES ('33', '3', '1', '4', '1');
INSERT INTO `favorites` VALUES ('34', '3', '1', '18', '1');
INSERT INTO `favorites` VALUES ('35', '3', '17', '0', '1');
INSERT INTO `favorites` VALUES ('36', '3', '19', '2', '0');
INSERT INTO `favorites` VALUES ('37', '3', '19', '1', '0');
INSERT INTO `favorites` VALUES ('38', '3', '1', '16', '1');
INSERT INTO `favorites` VALUES ('39', '3', '1', '19', '1');
INSERT INTO `favorites` VALUES ('40', '1', '19', '2', '0');
INSERT INTO `favorites` VALUES ('41', '1', '19', '1', '0');
INSERT INTO `favorites` VALUES ('42', '1', '12', '0', '1');
INSERT INTO `favorites` VALUES ('43', '15', '11', '29', '0');
INSERT INTO `favorites` VALUES ('44', '15', '11', '0', '0');
INSERT INTO `favorites` VALUES ('45', '1', '11', '29', '0');
INSERT INTO `favorites` VALUES ('46', '16', '4', '0', '1');
INSERT INTO `favorites` VALUES ('47', '1', '24', '26', '1');
INSERT INTO `favorites` VALUES ('48', '1', '24', '24', '1');
INSERT INTO `favorites` VALUES ('49', '1', '24', '27', '1');
INSERT INTO `favorites` VALUES ('50', '1', '24', '25', '1');
INSERT INTO `favorites` VALUES ('51', '1', '24', '0', '0');
INSERT INTO `favorites` VALUES ('52', '1', '7', '0', '0');
INSERT INTO `favorites` VALUES ('53', '1', '1', '3', '0');
INSERT INTO `favorites` VALUES ('54', '1', '1', '19', '1');
INSERT INTO `favorites` VALUES ('55', '1', '1', '23', '1');
INSERT INTO `favorites` VALUES ('56', '1', '1', '17', '1');
INSERT INTO `favorites` VALUES ('57', '17', '1', '0', '1');
INSERT INTO `favorites` VALUES ('58', '17', '1', '3', '0');
INSERT INTO `favorites` VALUES ('59', '17', '1', '19', '1');
INSERT INTO `favorites` VALUES ('60', '17', '1', '4', '1');
INSERT INTO `favorites` VALUES ('61', '17', '1', '23', '1');
INSERT INTO `favorites` VALUES ('62', '17', '1', '11', '1');
INSERT INTO `favorites` VALUES ('67', '2', '5', '0', '1');
INSERT INTO `favorites` VALUES ('68', '2', '2', '0', '0');
INSERT INTO `favorites` VALUES ('69', '2', '7', '0', '1');
INSERT INTO `favorites` VALUES ('70', '3', '24', '0', '1');
INSERT INTO `favorites` VALUES ('71', '3', '7', '0', '1');
INSERT INTO `favorites` VALUES ('72', '4', '8', '0', '1');
INSERT INTO `favorites` VALUES ('73', '4', '5', '0', '1');
INSERT INTO `favorites` VALUES ('74', '4', '7', '0', '1');
INSERT INTO `favorites` VALUES ('75', '4', '4', '0', '1');
INSERT INTO `favorites` VALUES ('76', '4', '19', '0', '0');
INSERT INTO `favorites` VALUES ('77', '10', '24', '0', '1');
INSERT INTO `favorites` VALUES ('78', '10', '8', '0', '1');
INSERT INTO `favorites` VALUES ('79', '10', '19', '0', '0');
INSERT INTO `favorites` VALUES ('80', '10', '8', '22', '0');
INSERT INTO `favorites` VALUES ('81', '10', '8', '21', '1');
INSERT INTO `favorites` VALUES ('82', '10', '8', '20', '1');
INSERT INTO `favorites` VALUES ('83', '1', '1', '32', '1');
INSERT INTO `favorites` VALUES ('84', '1', '33', '0', '0');
INSERT INTO `favorites` VALUES ('90', '3', '1', '32', '1');
INSERT INTO `favorites` VALUES ('91', '3', '1', '33', '1');
INSERT INTO `favorites` VALUES ('92', '3', '1', '23', '1');
INSERT INTO `favorites` VALUES ('93', '3', '1', '31', '1');
INSERT INTO `favorites` VALUES ('94', '3', '1', '17', '1');
INSERT INTO `favorites` VALUES ('95', '3', '26', '0', '1');
INSERT INTO `favorites` VALUES ('96', '3', '32', '0', '1');
INSERT INTO `favorites` VALUES ('97', '3', '33', '0', '0');
INSERT INTO `favorites` VALUES ('98', '15', '1', '0', '1');
INSERT INTO `favorites` VALUES ('99', '7', '4', '0', '1');
INSERT INTO `favorites` VALUES ('100', '7', '17', '0', '1');
INSERT INTO `favorites` VALUES ('101', '7', '32', '0', '1');
INSERT INTO `favorites` VALUES ('102', '7', '8', '0', '1');
INSERT INTO `favorites` VALUES ('103', '7', '1', '0', '1');
INSERT INTO `favorites` VALUES ('104', '7', '1', '32', '1');
INSERT INTO `favorites` VALUES ('105', '7', '1', '3', '1');
INSERT INTO `favorites` VALUES ('106', '7', '1', '19', '1');
INSERT INTO `favorites` VALUES ('107', '7', '1', '33', '1');
INSERT INTO `favorites` VALUES ('108', '1', '1', '33', '0');
INSERT INTO `favorites` VALUES ('109', '1', '1', '31', '1');
INSERT INTO `favorites` VALUES ('110', '1', '26', '36', '1');
INSERT INTO `favorites` VALUES ('111', '1', '26', '35', '1');
INSERT INTO `favorites` VALUES ('112', '1', '26', '37', '1');
INSERT INTO `favorites` VALUES ('113', '1', '8', '22', '1');
INSERT INTO `favorites` VALUES ('114', '1', '8', '21', '1');
INSERT INTO `favorites` VALUES ('115', '1', '8', '20', '1');
INSERT INTO `favorites` VALUES ('116', '1', '7', '39', '1');
INSERT INTO `favorites` VALUES ('117', '1', '7', '38', '1');
INSERT INTO `favorites` VALUES ('119', '1', '34', '0', '1');
INSERT INTO `favorites` VALUES ('120', '3', '36', '0', '1');
INSERT INTO `favorites` VALUES ('121', '1', '36', '40', '1');
INSERT INTO `favorites` VALUES ('122', '1', '36', '41', '0');

-- ----------------------------
-- Table structure for `goods`
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `gid` int NOT NULL AUTO_INCREMENT,
  `shopid` int NOT NULL COMMENT '外键约束',
  `img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `goodsname` varchar(255) NOT NULL COMMENT '商品名称',
  `price` float NOT NULL DEFAULT '0',
  `goodstype` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `isdelete` int NOT NULL DEFAULT '0' COMMENT '0未删nodelete，1删除delete',
  PRIMARY KEY (`gid`),
  KEY `shopid` (`shopid`),
  CONSTRAINT `goods_ibfk_1` FOREIGN KEY (`shopid`) REFERENCES `shop` (`shopid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES ('1', '19', 'http://localhost:8081/files/bcf09166225a4aeaa5e12bc62d9f4f82', '炸串', '10', '热销', '1');
INSERT INTO `goods` VALUES ('2', '19', 'http://localhost:8081/files/bcf09166225a4aeaa5e12bc62d9f4f82', '豆腐', '3', '小吃', '1');
INSERT INTO `goods` VALUES ('3', '1', 'http://localhost:8081/d9fa99ded0554922a8b7fcd5f53886c8.png', '荷包蛋', '2', '早餐', '0');
INSERT INTO `goods` VALUES ('4', '1', 'http://localhost:8081/2a47715516ac4082875142b0b11f587b.png', '牛奶', '2', '早餐', '0');
INSERT INTO `goods` VALUES ('5', '1', 'http://localhost:8081/files/b298f8db00ad4d1597cedf367762d0eb', '肉包', '2', '包', '1');
INSERT INTO `goods` VALUES ('9', '1', 'http://localhost:8081/2abdf5b2388848c4aecaf37614426975.jpg', '2', '2', '2', '1');
INSERT INTO `goods` VALUES ('10', '1', 'http://localhost:8081/9e3cfe73c130440886860fa62be4132f.jpg', '火影', '999', '剧集', '1');
INSERT INTO `goods` VALUES ('11', '1', 'http://localhost:8081/1a310ac4f7d7465d9dfcfe5bdf1249fc.png', '热狗', '2', '小吃', '0');
INSERT INTO `goods` VALUES ('12', '1', null, 'a', '1', 'a', '1');
INSERT INTO `goods` VALUES ('13', '1', null, 'c', '12.2', 'c', '1');
INSERT INTO `goods` VALUES ('14', '1', null, 'd', '12.45', 'd', '1');
INSERT INTO `goods` VALUES ('15', '1', null, 'e', '124', 'e', '1');
INSERT INTO `goods` VALUES ('16', '1', null, '无图测试', '12.22', '测试', '1');
INSERT INTO `goods` VALUES ('17', '1', 'http://localhost:8081/3e0929f05cdf4b068b8a41c111797229.png', '商品测试', '135.46', '商品测试', '0');
INSERT INTO `goods` VALUES ('18', '1', 'http://localhost:8081/8b8d6fdb68eb4fb3acda26457001469f.jpg', '商品测试2', '13.456', '商品测试2', '1');
INSERT INTO `goods` VALUES ('19', '1', 'http://localhost:8081/e68f7e70aa0846b199bbdf1a229d131d.png', '火腿面包', '5', '早餐', '0');
INSERT INTO `goods` VALUES ('20', '8', 'http://localhost:8081/c42d7201a35a453c8d31ec3d056dd728.png', '蟹皇堡', '15.5', '汉堡单人套餐', '0');
INSERT INTO `goods` VALUES ('21', '8', 'http://localhost:8081/c4e80876a286493c98cfee52ac169ea1.png', '香辣鸡翅', '5', '小吃', '0');
INSERT INTO `goods` VALUES ('22', '8', 'http://localhost:8081/02a8854add804a49986fdb278ed06c65.png', '薯条', '3', '小吃', '0');
INSERT INTO `goods` VALUES ('23', '1', 'http://localhost:8081/0b6be6a0b19f45648660dd90ff3e3da2.png', '泡面', '5', '面食', '0');
INSERT INTO `goods` VALUES ('24', '24', 'http://localhost:8081/577a812b45a94d50b9d94a906a4b9b5a.png', '夹克', '123', '上衣', '0');
INSERT INTO `goods` VALUES ('25', '24', 'http://localhost:8081/e4901059cd924c2d87ef64606412e56d.png', '运动鞋', '120', '鞋子', '0');
INSERT INTO `goods` VALUES ('26', '24', 'http://localhost:8081/2ef7d8fe91b14600b8cd1afba408b4b5.png', '棒球帽', '32.5', '帽子', '0');
INSERT INTO `goods` VALUES ('27', '24', 'http://localhost:8081/76e543e2c2534300a350128322674436.png', '牛仔裤', '110.11', '裤子', '0');
INSERT INTO `goods` VALUES ('28', '11', 'http://localhost:8081/19488e5971024e0394dc533e5de5fec3.png', 'fish', '15', '鱼类', '1');
INSERT INTO `goods` VALUES ('29', '11', 'http://localhost:8081/f40726444f1b45998d1e97889724734e.png', 'fish', '12.5', '鱼类', '1');
INSERT INTO `goods` VALUES ('30', '11', 'http://localhost:8081/28334366193b4cfab80a40b85099a2de.png', '营养', '16', '饭', '1');
INSERT INTO `goods` VALUES ('31', '1', 'http://localhost:8081/9a04b37b44e943e58890d6918c73f982.png', '水煮蛋', '1.5', '营养', '0');
INSERT INTO `goods` VALUES ('32', '1', 'http://localhost:8081/3e010089a33e4213a17d2aea28d4865b.png', '德芙', '6', '零食', '0');
INSERT INTO `goods` VALUES ('33', '1', 'http://localhost:8081/af17ca55ebfe42c3abe2c6bd5b9884bc.png', '绿箭', '2', '零食', '0');
INSERT INTO `goods` VALUES ('34', '1', null, '测试一下', '111', '测试商品', '1');
INSERT INTO `goods` VALUES ('35', '26', 'http://localhost:8081/9cada0ba05134d0ca7ba1c236eeef0de.png', '蓝Buff', '100', 'buff', '0');
INSERT INTO `goods` VALUES ('36', '26', 'http://localhost:8081/9e8d5b54cf114bc0b7027057a4a984d9.png', '冰壶', '20', '运动器材', '0');
INSERT INTO `goods` VALUES ('37', '26', 'http://localhost:8081/3264a7156122450da88ad6aa18bb758c.png', '力量', '55.5', 'power', '0');
INSERT INTO `goods` VALUES ('38', '7', 'http://localhost:8081/9eebe2a87329409295d1c9aa20228e47.png', '土司', '5', '面包', '0');
INSERT INTO `goods` VALUES ('39', '7', 'http://localhost:8081/2ae7b55d45be45c5b074aba10bce7e5d.png', '三明治', '5', '面包', '0');
INSERT INTO `goods` VALUES ('40', '36', 'http://localhost:8081/ec0cfb4aece74d9f86731e34b17feb35.png', '珍珠奶茶', '9', '畅销', '0');
INSERT INTO `goods` VALUES ('41', '36', 'http://localhost:8081/faa78db65a8647f7a63d3f838c9732f1.png', '柠檬茶', '6', '新品', '0');
INSERT INTO `goods` VALUES ('42', '33', null, '123', '123', '123', '1');

-- ----------------------------
-- Table structure for `members`
-- ----------------------------
DROP TABLE IF EXISTS `members`;
CREATE TABLE `members` (
  `mid` int NOT NULL AUTO_INCREMENT,
  `truename` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `gender` int DEFAULT NULL COMMENT '1男 2女',
  `username` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '娑撯偓娑擃亜褰块惍浣风娑擃亣澶勯崣?',
  `password` varchar(16) NOT NULL,
  PRIMARY KEY (`mid`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of members
-- ----------------------------
INSERT INTO `members` VALUES ('1', '赵六', '1', 'zhaoliu', '15626827085', '1');
INSERT INTO `members` VALUES ('2', '孙七', '2', 'sunqi', '98765432101', 'sq');
INSERT INTO `members` VALUES ('3', '周八', '1', 'zhouba', '13579246811', '2');
INSERT INTO `members` VALUES ('4', '白展堂', '1', 'baizhantang', '00021212102', 'bzt');
INSERT INTO `members` VALUES ('5', '李大嘴', '1', 'lidazui', '11123335445', 'ldz');
INSERT INTO `members` VALUES ('6', '郭芙蓉', '2', 'guofurong', '22234566878', 'gfr');
INSERT INTO `members` VALUES ('7', '吕秀才', '1', 'lvxiucai', '33336468879', 'lxc');
INSERT INTO `members` VALUES ('9', '章鱼哥', '1', 'zhangyuge', '76543211234', 'zyg');
INSERT INTO `members` VALUES ('10', 'huang', '2', 'huang', '15626827088', '123');
INSERT INTO `members` VALUES ('11', '黄某', '1', 'huangmou', '15766320218', '123');
INSERT INTO `members` VALUES ('15', '小A', '1', 'xiaoA', '20221161154', '1');
INSERT INTO `members` VALUES ('16', '小C', '2', 'xiaoC', '20221161155', '2');
INSERT INTO `members` VALUES ('17', '小E', '1', 'xiaoE', '20221161156', '3');
INSERT INTO `members` VALUES ('25', 'chw', '1', 'chw', '11012312311', '123');
INSERT INTO `members` VALUES ('26', 'bbb', '2', 'bbb', '12345678999', '1');
INSERT INTO `members` VALUES ('27', '依二叁', '2', 'yiersan', '20225130011', '123321');
INSERT INTO `members` VALUES ('28', '叁噩伊', '1', 'saneyi', '20225130029', '321321');
INSERT INTO `members` VALUES ('29', '1', '1', '1', '20220030513', '1');
INSERT INTO `members` VALUES ('30', '2', '2', '2', '20225130036', '2');
INSERT INTO `members` VALUES ('31', '3', '2', '3', '20220513042', '3');
INSERT INTO `members` VALUES ('32', '4', '2', '4', '20220513053', '4');
INSERT INTO `members` VALUES ('33', '5', '1', '5', '20220513054', '5');
INSERT INTO `members` VALUES ('34', '张伟', '1', '37', '20225122324', '123123');
INSERT INTO `members` VALUES ('35', '张伟', '1', '39', '20225122323', '123123');
INSERT INTO `members` VALUES ('36', '张伟', '1', '400', '20225122326', '123123');
INSERT INTO `members` VALUES ('37', '张伟', '1', '41', '20225122327', '123123');

-- ----------------------------
-- Table structure for `shop`
-- ----------------------------
DROP TABLE IF EXISTS `shop`;
CREATE TABLE `shop` (
  `shopid` int NOT NULL AUTO_INCREMENT,
  `photo` varchar(255) DEFAULT NULL,
  `shopname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '一店一名',
  `introduce` longtext,
  `openinghours` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `status` int DEFAULT NULL COMMENT '姒涙顓?濮濓絽鐖堕拃銉ょ瑹;2娴兼垶浼?3瀵板懐顫?',
  `bid` int DEFAULT NULL,
  `shoptype` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '其他',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `recruit` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '为空不显示，不为空显示(普通用户有申请按钮)',
  PRIMARY KEY (`shopid`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of shop
-- ----------------------------
INSERT INTO `shop` VALUES ('1', 'http://localhost:8081/files/20794e4bae4143bca88ccae8698fc7c3', '早餐店', '这里是店铺简介这里是店铺简介这里是店铺简介这里是店铺简介这里是店铺简介123', '每天8：00-22：00', '1', '1', '餐饮', '超长店铺地址测试，超长店铺地址测试，超长店铺地址测试，超长店铺地址测试，超长店铺地址测试，超长店铺地址测试，超长店铺地址测试，超长店铺地址测试', '招聘学生兼职5人');
INSERT INTO `shop` VALUES ('2', '', '信合', '', '', '2', '4', '其他', '学6中间映月广场对面', '');
INSERT INTO `shop` VALUES ('4', 'http://localhost:8081/files/a5ae87d9f59d454da4b94b14ac788ae5', '李四的店', '理发店，世界知名托尼老师——钢铁侠', '周一-周五10:00-22:00\n周六-周日9:00-23:00', '1', '2', '理发', '学五楼下原沙县轻食', '招聘助手5位，有经验者优先');
INSERT INTO `shop` VALUES ('5', 'http://localhost:8081/files/home', '同福客栈', '“同福客栈”是电视剧《武林外传》中的客栈名称，是本电视剧故事发生的主要地点。最初叫尚儒客栈，原为吕轻侯所有，佟湘玉来后更名为同福客栈。在这个客栈里有饭馆、厨房、天井、小院、男女寝室、二楼客房和门外街道，还有对门怡红楼。', '每天7:00-19:00', '1', '6', '客栈', '武林', null);
INSERT INTO `shop` VALUES ('6', '', '广东东莞寮步镇东莞城市学院', '', '', '3', '0', '其他', '广东东莞寮步镇东莞城市学院', '');
INSERT INTO `shop` VALUES ('7', 'http://localhost:8081/c95a8e2cb2a94bfd87a01ece048f5237.png', '每一家', '欢迎光临每一家', '周一至周五8：30-18：30，周六周日10：00-24：00', '2', '16', '零售', '荔香园二楼进门左转', '招聘前台收银员2名');
INSERT INTO `shop` VALUES ('8', 'http://localhost:8081/files/976b20f3208e4c11bf79a57075b208aa', '蟹堡王', '蟹堡王是卡通动画片《海绵宝宝》中的场景之一。片中是由蟹老板经营的餐厅，是比基尼海滩最知名的餐厅。主角海绵宝宝就是蟹堡王的员工之一。', '每天9:00-20:00', '1', '7', '餐饮', '学一楼下', null);
INSERT INTO `shop` VALUES ('11', '', '学六学五之间路段123', '', '', '2', '35', '其他', '学六学五之间第1间', '');
INSERT INTO `shop` VALUES ('12', 'http://localhost:8081/bff0d1b82e104114a6386c3deb0145f4.png', 'SF', null, '每天007', '1', '10', '快递', '广东省东莞市寮步镇东莞城市学院1', null);
INSERT INTO `shop` VALUES ('17', 'http://localhost:8081/files/8131303763dc4cc88aa9e5a9b9bd9124', '测', '本店铺是测试店铺', '每周周一至周六9:00-21:00', '2', '9', '健身房', '新增店铺测试', '招聘蹦极教练');
INSERT INTO `shop` VALUES ('19', '', '123', '123', '123', '1', '43', '理发', '学五学六之间第4间', 'nnn');
INSERT INTO `shop` VALUES ('24', 'http://localhost:8081/6b547e87b45f4c7896d7feafda0de2d3.png', '学六职业装', '服装', '996，节假日休息', '1', '25', '服饰', '学五学六之间第7间', '招聘店员');
INSERT INTO `shop` VALUES ('26', 'http://localhost:8081/52fb054ac58346cfbd09c3a35a132bf2.png', '来福', '常威在打来福', '007', '1', '32', '健身房', '更新店铺测试', '招一个常威');
INSERT INTO `shop` VALUES ('32', 'http://localhost:8081/25b0ef0df1584447b61cffd9d08ea1a2.png', '常威', '来福开了一家店', null, '2', '33', '娱乐', '学七楼下11', '本店招聘员工');
INSERT INTO `shop` VALUES ('33', '', '新店铺测试', null, '', '3', '0', '其他', '新店铺测试', '');
INSERT INTO `shop` VALUES ('34', 'http://localhost:8081/d62cd519494e419b8c6386441fddadb9.png', '叶畔湾', '', '每天上午7：00-晚上12：00', '1', '37', '其他', '学5南边隔壁荔香园斜对面', '招聘兼职员工');
INSERT INTO `shop` VALUES ('35', null, '3号楼1楼1', null, null, '2', '42', '其他', '3号楼1楼1', null);
INSERT INTO `shop` VALUES ('36', 'http://localhost:8081/a27139c56b7d49b5ae7f6d49937dfcdb.png', '益合堂', null, null, '1', '39', '餐饮', '学三学五之间第2间', '招聘学生兼职店员3名');
INSERT INTO `shop` VALUES ('37', '', '学六学七间南侧第一间', '', '', '3', '0', '其他', '学六学七间南侧第一间', '');
INSERT INTO `shop` VALUES ('38', null, '112', null, null, '3', '0', '其他', '112', null);

-- ----------------------------
-- Table structure for `staff`
-- ----------------------------
DROP TABLE IF EXISTS `staff`;
CREATE TABLE `staff` (
  `staffid` int NOT NULL AUTO_INCREMENT,
  `mid` int NOT NULL,
  `shopid` int NOT NULL,
  `bid` int NOT NULL,
  `create_time` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '閸忋儴浜撮弮鍫曟？',
  PRIMARY KEY (`staffid`),
  KEY `bid` (`bid`),
  KEY `shopid` (`shopid`),
  KEY `mid` (`mid`),
  CONSTRAINT `staff_ibfk_1` FOREIGN KEY (`bid`) REFERENCES `business` (`bid`) ON DELETE CASCADE,
  CONSTRAINT `staff_ibfk_2` FOREIGN KEY (`shopid`) REFERENCES `shop` (`shopid`) ON DELETE CASCADE,
  CONSTRAINT `staff_ibfk_3` FOREIGN KEY (`mid`) REFERENCES `members` (`mid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of staff
-- ----------------------------
INSERT INTO `staff` VALUES ('4', '4', '5', '6', '2022-01-12 16:28:47');
INSERT INTO `staff` VALUES ('5', '5', '5', '6', '2022-01-13 16:28:43');
INSERT INTO `staff` VALUES ('6', '6', '5', '6', '2022-01-14 16:28:39');
INSERT INTO `staff` VALUES ('7', '7', '5', '6', '2022-01-15 16:28:36');
INSERT INTO `staff` VALUES ('9', '9', '8', '7', '2022-01-17 16:28:25');
INSERT INTO `staff` VALUES ('21', '2', '1', '1', '2022-01-23 17:45:17');
INSERT INTO `staff` VALUES ('22', '10', '4', '2', '2022-01-27 17:05:14');
INSERT INTO `staff` VALUES ('29', '3', '4', '2', '2022-03-08 23:43:14');
INSERT INTO `staff` VALUES ('30', '16', '4', '2', '2022-04-08 23:47:18');
INSERT INTO `staff` VALUES ('33', '10', '1', '1', '2022-04-20 14:46:06');
INSERT INTO `staff` VALUES ('37', '1', '1', '1', '2022-04-20 15:26:45');
INSERT INTO `staff` VALUES ('38', '3', '7', '16', '2022-05-13 22:33:53');
