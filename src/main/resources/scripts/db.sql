DROP TABLE IF EXISTS `tbl_user`;
CREATE TABLE `tbl_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) DEFAULT NULL,
  `orgid` varchar(64) DEFAULT NULL COMMENT '所属机构ID（商户通侧）',
  `password` varchar(64) DEFAULT NULL,
  `token` varchar(64) DEFAULT NULL,
  `sign` varchar(32) DEFAULT NULL,
  `admin` char(1) DEFAULT '0',
  `able` char(1) DEFAULT '1',
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_user
-- ----------------------------
INSERT INTO `tbl_user` VALUES ('2', 'admin', '9527', 'f6fdffe48c908deb0f4c3bd36c032e72', '9d4c2f636f067f8908d41131de534836895b894571df3c83', 'ef05873cdcac4f35a3ddb6251ff574e3', '1', '1', '2017-05-16 14:36:45');
INSERT INTO `tbl_user` VALUES ('3', 'sunland', null, null, '340e212a77c1a9be49b54e6a90bc4ac2bec84d2f4cbb24b4', '91ece9c12a98435398cac8dd808c410b', '0', '1', '2017-06-01 18:54:02');
INSERT INTO `tbl_user` VALUES ('4', 'testorgid', '123456', null, '8ba3f537b378e374b75741ac470240109013db30396448ea', '3888e06abf9b4d548f8551ac3349de37', '0', '1', '2017-07-24 15:37:09');
INSERT INTO `tbl_user` VALUES ('5', 'test', 'test', null, 'bbce2345d7772b060dc56ccecd93421795496119d1e80f5d', '90699a4ebde44910862e2217645c5f9f', '0', '1', '2018-02-07 16:38:54');

DROP TABLE IF EXISTS `api_example`;
CREATE TABLE `api_example` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `age` int(11) NOT NULL COMMENT '年龄',
  `ctm` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `api_example` (`username`, `age`, `ctm`) VALUES('张三', '18', NOW()) ;
INSERT INTO `api_example` (`username`, `age`, `ctm`) VALUES('李四', '20', NOW()) ;
INSERT INTO `api_example` (`username`, `age`, `ctm`) VALUES('王五', '19', NOW()) ;


