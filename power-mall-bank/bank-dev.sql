SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`(
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户名称',
  `password` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户密码',
  `status` INT(4) NOT NULL COMMENT '权限：1管理员/2普通用户/3尊贵用户',
  PRIMARY KEY (`id`) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Compact;


DROP TABLE IF EXISTS `card`;
CREATE TABLE `card`(
  `id` INT(16) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` INT(11) NOT NULL COMMENT '所属用户id',
  `password` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '信用卡密码',
  `status` INT(4) NOT NULL COMMENT '权限：1可用/2冻结/3挂失',
  `money` decimal(11, 2) NOT NULL COMMENT '卡内余额',
  PRIMARY KEY (`id`) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Compact;


DROP TABLE IF EXISTS `info_record`;
CREATE TABLE `info_record`(
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '标题',
  `content` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '内容',
  `date` datetime NULL DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Compact;



DROP TABLE IF EXISTS `financial_products`;
CREATE TABLE `financial_products`(
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '产品名称',
  `brief info` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '产品介绍',
  `card_no` INT(16) NOT NULL COMMENT '产品卡号',
  PRIMARY KEY (`id`) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Compact;


DROP TABLE IF EXISTS `financial_products_cost`;
CREATE TABLE `financial_products_cost`(
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `product_id` INT(11) NOT NULL COMMENT '所属产品id',
  `price` decimal(7, 2) NOT NULL COMMENT '每笔价格',
  `date` date NULL DEFAULT NULL COMMENT '生效日期',
  PRIMARY KEY (`id`) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Compact;


DROP TABLE IF EXISTS `pay_products_record`;
CREATE TABLE `pay_products_record`(
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `product_id` INT(11) NOT NULL COMMENT '所属产品id',
  `card_id` INT(16) NOT NULL COMMENT '交易用户卡号id',
  `user_id` INT(11) NOT NULL COMMENT '交易用户id',
  `pay_status` INT(4) NOT NULL COMMENT '交易类型：1购买/2出售',
  `pay_num` INT(16) NOT NULL COMMENT '交易量',
  `sum_money` decimal(11, 2) NOT NULL COMMENT '交易金额',
  `date` datetime NULL DEFAULT NULL COMMENT '交易时间',
  PRIMARY KEY (`id`) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Compact;

