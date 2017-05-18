
DROP TABLE IF EXISTS `manage_user`;
CREATE TABLE `manage_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uname` varchar(300) DEFAULT NULL COMMENT '登录用户名',
  `upasswd` varchar(200) DEFAULT NULL COMMENT '登录密码',
  `nick_name` varchar(300) DEFAULT NULL COMMENT '昵称或者姓名',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `state` tinyint(5) DEFAULT NULL COMMENT '用户状态:1:正常，0：删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `message_info`;
CREATE TABLE `message_info` (
  `id` int(11) NOT NULL,
  `mes_type` tinyint(5) DEFAULT NULL COMMENT '消息类型，1：公告，2：广播',
  `mes_position` tinyint(5) DEFAULT NULL COMMENT '广播消息发放位置：1：大厅，2：房间，3：大厅房间同时(广播才有）',
  `mes_title` varchar(100) DEFAULT NULL COMMENT '消息标题',
  `message_content` varchar(500) DEFAULT NULL COMMENT '具体消息',
  `create_time` datetime DEFAULT NULL COMMENT '消息创建时间',
  `interval_time` int(11) DEFAULT NULL COMMENT '轮播间隔时间单位（s）',
  `state` tinyint(5) DEFAULT NULL COMMENT '消息状态：0：未发送，1：已发送，2：删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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


DROP TABLE IF EXISTS `room_record`;
CREATE TABLE `room_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `room_num` int(11) DEFAULT NULL COMMENT '房间号',
  `room_rule` varchar(500) DEFAULT NULL COMMENT '房间规则字符串',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '房间创建时间',
  `room_state` tinyint(5) DEFAULT NULL COMMENT '房间状态,1:在线，2：等待，3：解散',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `update_info`;
CREATE TABLE `update_info` (
  `id` int(11) NOT NULL,
  `device_type` tinyint(5) DEFAULT NULL COMMENT '设备类型,1:android，2：ios',
  `app_version` float DEFAULT NULL COMMENT 'app版本',
  `down_url` varchar(300) DEFAULT NULL COMMENT '下载URL',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `user_action_score`;
CREATE TABLE `user_action_score` (
  `id` int(11) NOT NULL,
  `user_room_record_id` int(11) DEFAULT NULL COMMENT '用户房间记录ID',
  `action_type` int(11) DEFAULT NULL COMMENT '得分类型',
  `action_score` int(11) DEFAULT NULL COMMENT '得分',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `nick_name` varchar(300) DEFAULT '' COMMENT '昵称',
  `weixin_mark` varchar(300) NOT NULL,
  `sex` tinyint(5) DEFAULT '1' COMMENT '1：男，2：女',
  `head_imgurl` varchar(300) DEFAULT NULL COMMENT '用户头像',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `room_cart_num` int(11) DEFAULT NULL COMMENT '房卡数量',
  `score_num` int(11) DEFAULT NULL COMMENT '战绩积分',
  `last_login_ip` varchar(300) DEFAULT NULL COMMENT '上次登录ID',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `curr_room` int(11) DEFAULT NULL COMMENT '当前房间号',
  `state` tinyint(5) DEFAULT NULL COMMENT '数据记录标识，1：正常，0：删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `user_room_record`;
CREATE TABLE `user_room_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `room_record_id` int(11) NOT NULL COMMENT '房间记录ID',
  `room_num` int(11) DEFAULT NULL COMMENT '房间号',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `user_direction` tinyint(5) DEFAULT NULL COMMENT '用户方位：1：东，2：南：3：西，4：北',
  `join_time` datetime DEFAULT NULL COMMENT '加入房间时间',
  `last_score` int(11) DEFAULT NULL COMMENT '最终得分',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
