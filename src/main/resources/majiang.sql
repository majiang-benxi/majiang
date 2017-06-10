/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50610
Source Host           : 127.0.0.1:3306
Source Database       : mahjongonline

Target Server Type    : MYSQL
Target Server Version : 50610
File Encoding         : 65001

Date: 2017-06-03 11:40:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for manage_user
-- ----------------------------
DROP TABLE IF EXISTS `manage_user`;
CREATE TABLE `manage_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uname` varchar(300) DEFAULT NULL COMMENT '登录用户名',
  `upasswd` varchar(200) DEFAULT NULL COMMENT '登录密码',
  `nick_name` varchar(300) DEFAULT NULL COMMENT '昵称或者姓名',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `user_level` tinyint(5) DEFAULT NULL COMMENT '管理员等级,1:会员，2：超级管理员',
  `card_hold` int(11) DEFAULT '0' COMMENT '所持有的卡片数量,会员有',
  `state` tinyint(5) DEFAULT NULL COMMENT '用户状态:1:正常，0：删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of manage_user
-- ----------------------------
INSERT INTO `manage_user` VALUES ('1', 'test', 'e10adc3949ba59abbe56e057f20f883e', 'test', '12345678911', '2017-05-24 15:21:07', '1', '10', '0');
INSERT INTO `manage_user` VALUES ('2', 'test1', 'e10adc3949ba59abbe56e057f20f883e', 'test1', '12345678911', '2017-05-24 15:21:07', '2', '5', '1');
INSERT INTO `manage_user` VALUES ('3', 'qrewt', 'f471230f660f454d325fa1ba5d7ebc8d', '12421452', '18372626220', '2017-05-25 08:59:12', '1', '5', '0');
INSERT INTO `manage_user` VALUES ('4', 'qrewtrwe', '8d0426f564d70159f5200a1ba50efe55', 'wettertye', '18372626220', '2017-05-25 09:00:40', '1', '5', '0');
INSERT INTO `manage_user` VALUES ('5', 'ewrtge', '44c6624d9c3464eebabe2a4ea4a957f4', 'yrere', '18372626220', '2017-05-25 09:19:30', '1', '5', '0');
INSERT INTO `manage_user` VALUES ('6', '232', 'bfc69d9f1765ffb3b988624926e986a5', '4643', '18372626220', '2017-05-25 09:20:47', '1', '5', '1');
INSERT INTO `manage_user` VALUES ('7', 'awqrwer', 'a1530a3d27f08f31b1d53f42e328ca9b', 'wqerqtrweq', '18372626220', '2017-05-25 09:21:37', '1', '5', '1');
INSERT INTO `manage_user` VALUES ('8', '24w3e', '8a30f974f6143a084213d867de7fbbdb', 'rereewt', '18372626220', '2017-05-25 09:23:12', '1', '5', '1');
INSERT INTO `manage_user` VALUES ('9', '3rt23', 'bebb90911ef74334a6c2f24d8d2ddcf5', 'rererere', '18372626220', '2017-05-25 09:28:51', '1', '5', '0');
INSERT INTO `manage_user` VALUES ('10', 'wqrwe', '05b070b7ea400bb8d7b44a056689eaf8', 'ewgwegte', '18372626220', '2017-05-25 09:51:30', '1', '5', '1');
INSERT INTO `manage_user` VALUES ('11', 'qrqrqw3trw', '9a58f98f63e836cf5f724bb3e8d054db', 'tergdfhgdf', '18372626220', '2017-05-25 09:51:58', '1', '5', '1');
INSERT INTO `manage_user` VALUES ('12', 'wqwe221', '7c32aabeeb23cb6d0b1a4fc1fafeb67f', 'qweqw21', '18372626220', '2017-05-25 14:22:38', '1', '5', '1');
INSERT INTO `manage_user` VALUES ('13', 'qwq', '2daa0df7e032ffa3341ea0e025b855b4', 'ewrqr', '18372626220', '2017-05-25 14:50:39', '1', '5', '1');
INSERT INTO `manage_user` VALUES ('14', 'wer3qwr', 'df12ebcf3564e5f7806a1e4c0e80d702', 'wqrqwrqw', '18372626220', '2017-05-25 15:19:22', '1', '5', '1');
INSERT INTO `manage_user` VALUES ('15', 'wrw', '888b88097952efd90cc9c3515e98c592', '4wtw34t', '18372626221', '2017-05-25 15:23:13', '1', '5', '1');
INSERT INTO `manage_user` VALUES ('16', '3wr2', '9773a94733e3b37a94e11264c01de2ee', 'rwrwqrq', '18372626220', '2017-05-25 15:24:40', '1', '5', '1');
INSERT INTO `manage_user` VALUES ('17', '4325253', '645dee99ac48ffc240a844743a53afe5', 't43tewtwe', '18372626220', '2017-05-25 15:25:23', '1', '5', '1');
INSERT INTO `manage_user` VALUES ('18', '23423', '62bf45b1fe374f64fbeef4ab65051a10', 'wetwe', '18372626220', '2017-05-25 15:30:34', '1', '5', '1');
INSERT INTO `manage_user` VALUES ('19', 'weqr33r', '152e21a30bd87900c0e6728f35456b8a', 't4444', '18372626220', '2017-05-25 16:01:49', '1', '5', '1');
INSERT INTO `manage_user` VALUES ('20', '2134esfef', '4206e18ddb9e1b178cce06e5341f4440', 'rewerwfgew', '18372626220', '2017-05-25 16:03:00', '1', '5', '1');
INSERT INTO `manage_user` VALUES ('21', '3rq2r32', '91cd533149895effc4e747b748d5a7d6', 'ewt32', '18372626220', '2017-05-25 16:23:03', '1', '5', '1');
INSERT INTO `manage_user` VALUES ('22', '3223r23', '849a515a02933beac8c32ac1cfabd3f5', '43234t32', '18372628220', '2017-05-25 16:24:19', '1', '5', '1');
INSERT INTO `manage_user` VALUES ('23', 'r3234t', 'b45a6b05225fee4483864287c61d1da3', '34t34t', '18372626220', '2017-05-25 16:24:44', '1', '5', '1');
INSERT INTO `manage_user` VALUES ('24', '4233frwefr', '5698b232aec4cbd4955fa8aa6619c441', 'ewfwefew', '18372626220', '2017-05-25 16:26:55', '1', '5', '1');
INSERT INTO `manage_user` VALUES ('25', '2332e', 'caa23d1acd1d3e69e0b4affdf386c971', 't432f2', '18372626220', '2017-05-25 16:27:28', '1', '5', '1');
INSERT INTO `manage_user` VALUES ('26', '32235235', '2ce4e3407f92c7a872d93ff16d70f2fd', '32532', '18372626220', '2017-05-25 16:29:24', '1', '5', '1');
INSERT INTO `manage_user` VALUES ('27', '24r32r3', '9206096780e8f3c7f3a12e11b2d2bbbe', 't43t423', '18372626220', '2017-05-25 22:42:26', '1', '5', '1');
INSERT INTO `manage_user` VALUES ('28', '3qwr32r', '71dfd1248cae96ebf90eb5db53f2fa85', 'rt43t34', '18372626220', '2017-05-25 22:43:06', '1', '10', '1');
INSERT INTO `manage_user` VALUES ('29', '23523', '132f98dfb92fbffcb1220f8fcf9f47cf', '32452352', '18372626220', '2017-05-25 22:44:46', '1', '5', '1');
INSERT INTO `manage_user` VALUES ('30', '25324532', '0d518fb59e3c74d37aa746dd900d7a81', '43t34t43', '18372626220', '2017-05-25 22:46:01', '2', '5', '1');
INSERT INTO `manage_user` VALUES ('31', '2423', 'b557b7502f08b3afcbb9c9c4fb639a68', '4543', '18372626220', '2017-05-25 22:47:31', '1', '5', '1');
INSERT INTO `manage_user` VALUES ('32', '423532', '1a58a29a72ab0df44785885c6aa09f27', '台43题', '18372626220', '2017-05-25 22:48:53', '1', '5', '1');
INSERT INTO `manage_user` VALUES ('33', '3245324', '9ae8a9512467733fb50e2022f65eb29f', '4t4334', '18372626220', '2017-05-25 22:51:07', '2', '5', '1');
INSERT INTO `manage_user` VALUES ('34', 'w2r32', '141869c8661107f76b188585a3a83415', '3t43ty43', '18372626220', '2017-05-25 22:51:31', '1', '5', '1');
INSERT INTO `manage_user` VALUES ('35', '32r32rf23', '34d58448aa12ad332da0faf065fa81ba', '32df23d3', '18372626220', '2017-05-25 22:54:44', '2', '5', '1');
INSERT INTO `manage_user` VALUES ('36', '32r32r3', 'c496f06ab4eedd8cae6484a7bfb9b235', 'f43f43', '18372626220', '2017-05-25 22:55:18', '2', '5', '1');
INSERT INTO `manage_user` VALUES ('37', 'w323', '59a1d274273e7c871e930e87359dd685', '343t43', '18372626220', '2017-05-25 22:56:14', '2', '5', '1');
INSERT INTO `manage_user` VALUES ('38', 'ewdwqf', '933af42a49df5cdc7bd92fa9cb08927b', 'effewce3', '18372626220', '2017-05-25 23:03:45', '1', '5', '1');
INSERT INTO `manage_user` VALUES ('39', 'f32f43f', 'd41d8cd98f00b204e9800998ecf8427e', '43t4t54', '', '2017-05-25 23:07:25', '1', '5', '1');
INSERT INTO `manage_user` VALUES ('40', 'werf2rf32', '071a5e31608a5b194227b831955b71e3', 'f43rf34r34', '18372626220', '2017-05-25 23:08:16', '1', '5', '1');
INSERT INTO `manage_user` VALUES ('41', 'wqe2', 'aba0be3b991e199f813aad8af5f52680', 'd23d32', '', '2017-05-25 23:08:44', '1', '5', '1');
INSERT INTO `manage_user` VALUES ('42', 'dwdwq', '7f18a28918f178265feeec3e6639868b', 'dqdwq', '18372626220', '2017-05-25 23:13:02', '1', '5', '1');
INSERT INTO `manage_user` VALUES ('43', 'w232', '0f6e4c4330d5a384228fdda0512824b0', '32r32r', '18372626220', '2017-05-25 23:15:16', '1', '5', '1');
INSERT INTO `manage_user` VALUES ('44', '2e21', '30ea6f54e81ca35c587dd0a35ef354b5', 'd3232', '18372626220', '2017-05-25 23:16:39', '1', '5', '1');
INSERT INTO `manage_user` VALUES ('45', 'we232', 'af6c8f47290a77c505a12214244bb123', 'd32r3r3', '18372626220', '2017-05-25 23:21:33', '1', '5', '1');
INSERT INTO `manage_user` VALUES ('46', 'ewrtw3ter', 'ea9c2e74ab9a888bac2f8f3c03c9e73c', 'yrt5y65u65ui65', '18372626220', null, '1', null, '1');

-- ----------------------------
-- Table structure for message_info
-- ----------------------------
DROP TABLE IF EXISTS `message_info`;
CREATE TABLE `message_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mes_type` tinyint(5) DEFAULT NULL COMMENT '消息类型，1：公告，2：广播',
  `mes_position` tinyint(5) DEFAULT NULL COMMENT '广播消息发放位置：1：大厅，2：房间，3：大厅房间同时(广播才有）',
  `mes_title` varchar(100) DEFAULT NULL COMMENT '消息标题',
  `message_content` varchar(500) DEFAULT NULL COMMENT '具体消息',
  `create_time` datetime DEFAULT NULL COMMENT '消息创建时间',
  `interval_time` int(11) DEFAULT NULL COMMENT '轮播间隔时间单位（s）',
  `state` tinyint(5) DEFAULT NULL COMMENT '消息状态：0：未发送，1：已发送，2：删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of message_info
-- ----------------------------
INSERT INTO `message_info` VALUES ('1', '1', null, '133', '2532563463', '2017-05-29 19:00:33', '2', '2');
INSERT INTO `message_info` VALUES ('2', '2', '1', '325432天鹅舞3', '二人文氛围供热不投入', '2017-05-29 19:00:33', '10', '1');
INSERT INTO `message_info` VALUES ('3', '1', null, '13124', '52643765475485', '2017-05-31 20:45:37', '12', '0');
INSERT INTO `message_info` VALUES ('4', '1', null, 'wt', 'hrt56u345346t', '2017-05-31 20:49:13', '1', '0');
INSERT INTO `message_info` VALUES ('5', '1', null, 'wqrw', 'g54tg54y56', '2017-05-31 20:57:08', '2', '0');
INSERT INTO `message_info` VALUES ('6', '1', null, 'e4t5344', '34t34t45ty45', '2017-05-31 20:57:43', '4', '0');
INSERT INTO `message_info` VALUES ('7', '1', null, 'wtwe', 'tg45yhh564335643', '2017-05-31 20:58:41', '2', '0');
INSERT INTO `message_info` VALUES ('8', '1', null, 'rw2r34t43', '5y465uu325346', '2017-05-31 21:00:14', '4', '0');
INSERT INTO `message_info` VALUES ('9', '1', null, 'wrewtery54', 'yh65iu76i76i76', '2017-05-31 21:02:21', '6', '0');
INSERT INTO `message_info` VALUES ('10', '1', null, 'wrewtery54', 'yh65iu76i76i76', '2017-05-31 21:02:39', '6', '0');
INSERT INTO `message_info` VALUES ('11', '1', null, 'wrewtery54', 'yh65iu76i76i76', '2017-05-31 21:03:03', '6', '0');
INSERT INTO `message_info` VALUES ('12', '1', null, 'wrewtery54', 'yh65iu76i76i76', '2017-05-31 21:03:12', '6', '0');
INSERT INTO `message_info` VALUES ('13', '1', null, 'wet4ey4e5', 'hy456u566666', '2017-05-31 21:04:32', '6', '0');
INSERT INTO `message_info` VALUES ('14', '1', null, 'wet4ey4e5', 'hy456u566666', '2017-05-31 21:04:35', '6', '0');
INSERT INTO `message_info` VALUES ('15', '1', null, 'wet4ey4e5', 'hy456u566666', '2017-05-31 21:04:39', '6', '0');
INSERT INTO `message_info` VALUES ('16', '1', null, 'wet4ey4e5', 'hy456u566666', '2017-05-31 21:04:54', '6', '0');
INSERT INTO `message_info` VALUES ('17', '1', null, 'wet4ey4e5', 'hy456u566666', '2017-05-31 21:05:00', '6', '0');
INSERT INTO `message_info` VALUES ('18', '1', null, 'ert4ey', 'y556u66i5i785775', '2017-05-31 21:05:31', '7', '0');
INSERT INTO `message_info` VALUES ('19', '1', null, 'ert4ey', 'y556u66i5i785775', '2017-05-31 21:05:34', '7', '0');
INSERT INTO `message_info` VALUES ('20', '1', null, 'we3te', 'y5y56u547u5656', '2017-05-31 21:06:37', '8', '0');

-- ----------------------------
-- Table structure for room_cart_change
-- ----------------------------
DROP TABLE IF EXISTS `room_cart_change`;
CREATE TABLE `room_cart_change` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `manage_user_id` int(11) DEFAULT NULL COMMENT '管理员ID',
  `manage_name` varchar(300) DEFAULT NULL COMMENT '管理员姓名',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `user_name` varchar(300) DEFAULT NULL COMMENT '玩家姓名',
  `change_num` int(11) DEFAULT NULL COMMENT '变化数量',
  `change_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_success` tinyint(2) DEFAULT NULL COMMENT '是否成功：1：成功，0：失败',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of room_cart_change
-- ----------------------------

-- ----------------------------
-- Table structure for room_record
-- ----------------------------
DROP TABLE IF EXISTS `room_record`;
CREATE TABLE `room_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `room_num` int(11) DEFAULT NULL COMMENT '房间号',
  `room_rule` varchar(500) DEFAULT NULL COMMENT '房间规则字符串',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '房间创建时间',
  `end_time` datetime DEFAULT NULL COMMENT '游戏结束时间',
  `room_state` tinyint(5) DEFAULT NULL COMMENT '房间状态,1:在线，2：等待，3：解散，4：结束',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of room_record
-- ----------------------------
INSERT INTO `room_record` VALUES ('1', '1234214', '1313', '1', '2017-05-29 14:51:43', '2017-05-29 16:48:55', '1');
INSERT INTO `room_record` VALUES ('2', '2123423', '3425', '6', '2017-05-29 15:48:55', '2017-05-29 16:48:55', '3');
INSERT INTO `room_record` VALUES ('3', '4214124', '2435', '10', '2017-05-29 15:48:55', '2017-05-29 16:48:55', '1');
INSERT INTO `room_record` VALUES ('4', '4214125', '532', '15', '2017-05-29 14:52:30', '2017-05-29 15:48:55', '2');

-- ----------------------------
-- Table structure for update_info
-- ----------------------------
DROP TABLE IF EXISTS `update_info`;
CREATE TABLE `update_info` (
  `id` int(11) NOT NULL,
  `device_type` tinyint(5) DEFAULT NULL COMMENT '设备类型,1:android，2：ios',
  `app_version` float DEFAULT NULL COMMENT 'app版本',
  `down_url` varchar(300) DEFAULT NULL COMMENT '下载URL',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of update_info
-- ----------------------------

-- ----------------------------
-- Table structure for user_action_score
-- ----------------------------
DROP TABLE IF EXISTS `user_action_score`;
CREATE TABLE `user_action_score` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `room_record_id` int(11) DEFAULT NULL COMMENT '房间记录ID',
  `round_num` int(11) DEFAULT NULL COMMENT '第几局',
  `action_type` int(11) DEFAULT NULL COMMENT '得分类型',
  `create_time` datetime NOT NULL COMMENT '记录创建时间',
  `action_score` int(11) NOT NULL COMMENT '得分',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_action_score
-- ----------------------------
INSERT INTO `user_action_score` VALUES ('1', '1', '1', '1', '2', '2017-05-29 17:02:49', '1');
INSERT INTO `user_action_score` VALUES ('2', '1', '1', '1', '3', '2017-05-29 17:02:49', '4');
INSERT INTO `user_action_score` VALUES ('3', '1', '1', '2', '3', '2017-05-29 17:02:49', '4');
INSERT INTO `user_action_score` VALUES ('4', '2', '1', '1', '2', '2017-05-29 17:02:49', '1');
INSERT INTO `user_action_score` VALUES ('5', '2', '1', '1', '3', '2017-05-29 17:02:49', '4');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `nick_name` varchar(300) DEFAULT '' COMMENT '昵称',
  `weixin_mark` varchar(300) NOT NULL,
  `sex` tinyint(5) DEFAULT '1' COMMENT '1：男，2：女',
  `head_imgurl` varchar(300) DEFAULT NULL COMMENT '用户头像',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `room_cart_num` int(11) DEFAULT '0' COMMENT '房卡数量',
  `room_cart_num_used` int(11) DEFAULT '0' COMMENT '已经使用的房卡数',
  `score_num` int(11) DEFAULT NULL COMMENT '战绩积分',
  `last_login_ip` varchar(300) DEFAULT NULL COMMENT '上次登录ID',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `login_times` int(11) DEFAULT NULL COMMENT '总的登录次数',
  `curr_room` int(11) DEFAULT NULL COMMENT '当前房间号',
  `state` tinyint(5) DEFAULT NULL COMMENT '数据记录标识，1：正常，0：删除，2，冻结',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('1', 'test1', 'test', '1', 'http://image4.app.gaopinimages.com/THUMBNAIL/240/c6/9f/ea/d7/133105769058.jpg', '2017-05-28 18:48:27', '5', '5', '20', '182.168.168.168', '2017-05-27 18:48:51', '3', null, '0');
INSERT INTO `user_info` VALUES ('2', 'test2', 'test', '1', 'http://image4.app.gaopinimages.com/THUMBNAIL/240/c6/9f/ea/d7/133105769058.jpg', '2017-05-28 18:48:27', '10', '5', '20', '182.168.168.168', '2017-05-27 18:48:51', '3', null, '1');
INSERT INTO `user_info` VALUES ('3', 'test3', 'test', '2', 'http://image4.app.gaopinimages.com/THUMBNAIL/240/c6/9f/ea/d7/133105769058.jpg', '2017-05-28 18:48:27', '10', '5', '20', '182.168.168.168', '2017-05-27 18:48:51', '3', null, '1');
INSERT INTO `user_info` VALUES ('4', 'test4', 'test', '2', 'http://image4.app.gaopinimages.com/THUMBNAIL/240/c6/9f/ea/d7/133105769058.jpg', '2017-05-28 18:48:27', '10', '5', '20', '182.168.168.168', '2017-05-27 18:48:51', '3', '1321442', '1');
INSERT INTO `user_info` VALUES ('5', 'test5', 'test', '1', 'http://image4.app.gaopinimages.com/THUMBNAIL/240/c6/9f/ea/d7/133105769058.jpg', '2017-05-28 18:48:27', '10', '5', '20', '182.168.168.168', '2017-05-27 18:48:51', '3', null, '1');
INSERT INTO `user_info` VALUES ('6', 'test6', 'test', '1', 'http://image4.app.gaopinimages.com/THUMBNAIL/240/c6/9f/ea/d7/133105769058.jpg', '2017-05-28 18:48:27', '10', '5', '20', '182.168.168.168', '2017-05-27 18:48:51', '3', null, '1');
INSERT INTO `user_info` VALUES ('7', 'test7', 'test', '1', 'http://image4.app.gaopinimages.com/THUMBNAIL/240/c6/9f/ea/d7/133105769058.jpg', '2017-05-28 18:48:27', '10', '5', '20', '182.168.168.168', '2017-05-27 18:48:51', '3', null, '1');
INSERT INTO `user_info` VALUES ('8', 'test8', 'test', '2', 'http://image4.app.gaopinimages.com/THUMBNAIL/240/c6/9f/ea/d7/133105769058.jpg', '2017-05-28 18:48:27', '10', '5', '20', '182.168.168.168', '2017-05-27 18:48:51', '3', null, '0');
INSERT INTO `user_info` VALUES ('9', 'test9', 'test', '1', 'http://image4.app.gaopinimages.com/THUMBNAIL/240/c6/9f/ea/d7/133105769058.jpg', '2017-05-28 18:48:27', '10', '5', '20', '182.168.168.168', '2017-05-27 18:48:51', '3', '43243435', '1');
INSERT INTO `user_info` VALUES ('10', 'test10', 'test', '1', 'http://image4.app.gaopinimages.com/THUMBNAIL/240/c6/9f/ea/d7/133105769058.jpg', '2017-05-28 18:48:27', '10', '5', '20', '182.168.168.168', '2017-05-27 18:48:51', '3', null, '1');
INSERT INTO `user_info` VALUES ('11', 'test11', 'test', '2', 'http://image4.app.gaopinimages.com/THUMBNAIL/240/c6/9f/ea/d7/133105769058.jpg', '2017-05-28 18:48:27', '10', '5', '20', '182.168.168.168', '2017-05-27 18:48:51', '3', null, '0');
INSERT INTO `user_info` VALUES ('12', 'test12', 'test', '1', 'http://image4.app.gaopinimages.com/THUMBNAIL/240/c6/9f/ea/d7/133105769058.jpg', '2017-05-28 18:48:27', '10', '5', '20', '182.168.168.168', '2017-05-27 18:48:51', '3', null, '0');
INSERT INTO `user_info` VALUES ('13', 'test13', 'test', '1', 'http://image4.app.gaopinimages.com/THUMBNAIL/240/c6/9f/ea/d7/133105769058.jpg', '2017-05-28 18:48:27', '10', '5', '20', '182.168.168.168', '2017-05-27 18:48:51', '3', null, '0');
INSERT INTO `user_info` VALUES ('14', 'test14', 'test', '1', 'http://image4.app.gaopinimages.com/THUMBNAIL/240/c6/9f/ea/d7/133105769058.jpg', '2017-05-28 18:48:27', '10', '5', '20', '182.168.168.168', '2017-05-27 18:48:51', '3', null, '0');
INSERT INTO `user_info` VALUES ('15', 'test15', 'test', '2', 'http://image4.app.gaopinimages.com/THUMBNAIL/240/c6/9f/ea/d7/133105769058.jpg', '2017-05-28 18:48:27', '10', '5', '20', '182.168.168.168', '2017-05-27 18:48:51', '3', null, '0');
INSERT INTO `user_info` VALUES ('16', 'test16', 'test', '1', 'http://image4.app.gaopinimages.com/THUMBNAIL/240/c6/9f/ea/d7/133105769058.jpg', '2017-05-28 18:48:27', '10', '5', '20', '182.168.168.168', '2017-05-27 18:48:51', '3', null, '1');
INSERT INTO `user_info` VALUES ('17', 'test17', 'test', '2', 'http://image4.app.gaopinimages.com/THUMBNAIL/240/c6/9f/ea/d7/133105769058.jpg', '2017-05-28 18:48:27', '10', '5', '20', '182.168.168.168', '2017-05-27 18:48:51', '3', null, '1');
INSERT INTO `user_info` VALUES ('18', 'test18', 'test', '1', 'http://image4.app.gaopinimages.com/THUMBNAIL/240/c6/9f/ea/d7/133105769058.jpg', '2017-05-28 18:48:27', '10', '5', '20', '182.168.168.168', '2017-05-27 18:48:51', '3', null, '0');

-- ----------------------------
-- Table structure for user_room_record
-- ----------------------------
DROP TABLE IF EXISTS `user_room_record`;
CREATE TABLE `user_room_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `room_record_id` int(11) NOT NULL COMMENT '房间记录ID',
  `room_num` int(11) DEFAULT NULL COMMENT '房间号',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `user_direction` tinyint(5) DEFAULT NULL COMMENT '用户方位：1：东，2：南：3：西，4：北',
  `operate_time` datetime DEFAULT NULL COMMENT '加入房间时间',
  `operate_type` tinyint(5) DEFAULT NULL COMMENT '操作类型，1：加入，2：离开',
  `user_ip` varchar(200) DEFAULT NULL COMMENT '用户IP',
  `operate_cause` varchar(200) DEFAULT NULL COMMENT '操作原因',
  `win_times` int(11) DEFAULT NULL COMMENT '赢局数',
  `lose_times` int(11) DEFAULT NULL COMMENT '输局数',
  `hu_times` int(11) DEFAULT NULL COMMENT '胡局数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_room_record
-- ----------------------------
INSERT INTO `user_room_record` VALUES ('1', '1', '1234214', '1', '1', '2017-05-29 11:06:09', '1', '192.168.168.168', '阿达沙发舒服撒', '1', '1', '1');
INSERT INTO `user_room_record` VALUES ('2', '1', '1234214', '2', '2', '2017-05-29 11:06:09', '1', '192.168.168.168', '阿达沙发舒服撒', '1', '1', '1');
INSERT INTO `user_room_record` VALUES ('3', '1', '1234214', '2', '2', '2017-05-29 11:06:09', '1', '192.168.168.168', '阿达沙发舒服撒', '1', '1', '1');
INSERT INTO `user_room_record` VALUES ('4', '1', '1234214', '4', '3', '2017-05-29 11:06:09', '1', '192.168.168.168', '阿达沙发舒服撒', '1', '1', '1');
INSERT INTO `user_room_record` VALUES ('5', '1', '1234214', '5', '4', '2017-05-29 11:06:09', '1', '192.168.168.168', '阿达沙发舒服撒', '1', '1', '1');
INSERT INTO `user_room_record` VALUES ('6', '2', '2123423', '6', '1', '2017-05-29 11:06:09', '1', '192.168.168.168', '阿达沙发舒服撒', '1', '1', '1');
INSERT INTO `user_room_record` VALUES ('7', '2', '2123423', '7', '2', '2017-05-29 11:06:09', '1', '192.168.168.168', '阿达沙发舒服撒', '1', '1', '1');
INSERT INTO `user_room_record` VALUES ('8', '2', '2123423', '8', '3', '2017-05-29 11:06:09', '1', '192.168.168.168', '阿达沙发舒服撒', '1', '1', '1');
INSERT INTO `user_room_record` VALUES ('9', '2', '2123423', '9', '4', '2017-05-29 11:06:09', '1', '192.168.168.168', '阿达沙发舒服撒', '1', '1', '1');
INSERT INTO `user_room_record` VALUES ('10', '3', '4214124', '10', '1', '2017-05-29 11:06:09', '1', '192.168.168.168', '阿达沙发舒服撒', '1', '1', '1');
INSERT INTO `user_room_record` VALUES ('11', '3', '4214124', '11', '2', '2017-05-29 11:06:09', '1', '192.168.168.168', '阿达沙发舒服撒', '1', '1', '1');
INSERT INTO `user_room_record` VALUES ('12', '3', '4214124', '12', '3', '2017-05-29 11:06:09', '1', '192.168.168.168', '阿达沙发舒服撒', '1', '1', '1');
INSERT INTO `user_room_record` VALUES ('13', '3', '4214124', '13', '4', '2017-05-29 11:06:09', '1', '192.168.168.168', '阿达沙发舒服撒', '1', '1', '1');
INSERT INTO `user_room_record` VALUES ('14', '3', '4214124', '13', '4', '2017-05-29 11:06:09', '1', '192.168.168.168', '阿达沙发舒服撒', '1', '1', '1');
INSERT INTO `user_room_record` VALUES ('15', '4', '4214125', '15', '1', '2017-05-29 11:06:09', '1', '192.168.168.168', '阿达沙发舒服撒', '1', '1', '1');
INSERT INTO `user_room_record` VALUES ('16', '4', '4214125', '16', '2', '2017-05-29 11:06:09', '1', '192.168.168.168', '阿达沙发舒服撒', '1', '1', '1');
