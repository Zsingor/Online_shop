/*
 Navicat Premium Dump SQL

 Source Server         : ZMySQL
 Source Server Type    : MySQL
 Source Server Version : 80034 (8.0.34)
 Source Host           : localhost:3306
 Source Schema         : web

 Target Server Type    : MySQL
 Target Server Version : 80034 (8.0.34)
 File Encoding         : 65001

 Date: 01/08/2024 19:23:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for browsetime
-- ----------------------------
DROP TABLE IF EXISTS `browsetime`;
CREATE TABLE `browsetime`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `goodsid` int NULL DEFAULT NULL,
  `time` int NULL DEFAULT NULL,
  `operatetime` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `browsetime_username_index`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of browsetime
-- ----------------------------
INSERT INTO `browsetime` VALUES (1, 'root', 1006, 5, '2024-04-22 20:56:26');
INSERT INTO `browsetime` VALUES (3, 'root', 1006, 30, '2024-04-24 19:09:06');
INSERT INTO `browsetime` VALUES (4, 'root', 1017, 1, '2024-04-24 19:09:17');
INSERT INTO `browsetime` VALUES (5, 'singor', 1008, 2, '2024-04-24 19:11:28');
INSERT INTO `browsetime` VALUES (6, 'singor', 1010, 2, '2024-04-24 19:11:32');
INSERT INTO `browsetime` VALUES (7, 'singor', 1006, 3, '2024-04-24 20:23:04');
INSERT INTO `browsetime` VALUES (8, 'singor', 1017, 3, '2024-04-24 20:23:13');
INSERT INTO `browsetime` VALUES (9, 'singor', 1008, 1, '2024-04-25 16:34:37');
INSERT INTO `browsetime` VALUES (12, 'root', 1001, 2, '2024-04-26 18:32:58');
INSERT INTO `browsetime` VALUES (13, 'root', 1001, 11, '2024-05-09 22:17:55');
INSERT INTO `browsetime` VALUES (14, 'root', 1005, 2, '2024-05-09 22:19:14');
INSERT INTO `browsetime` VALUES (15, 'root', 1001, 2, '2024-05-10 19:45:28');
INSERT INTO `browsetime` VALUES (16, 'root', 1001, 17, '2024-05-10 19:46:35');
INSERT INTO `browsetime` VALUES (17, 'root', 1001, 2, '2024-05-12 19:44:26');

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `id` int NOT NULL,
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `description` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `price` float(10, 2) NOT NULL,
  `mainimg` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '无',
  `salesid` int NOT NULL,
  `type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  UNIQUE INDEX `id`(`id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (1001, '商品1', '好看的商品', 600.00, '1702025348704_O1CN01DpOUJZ2HQv9hJdF7p_!!3170729146.jpg', 100001, '数码');
INSERT INTO `goods` VALUES (1005, '商品3', '好看的商品', 500.00, '1713001335149_电商11.jpg', 100002, '数码');
INSERT INTO `goods` VALUES (1006, '商品4', '好看的商品', 40.00, 'O1CN01cK8GeJ1qRNvEtoXlG_!!4134125492.jpg', 100001, '数码');
INSERT INTO `goods` VALUES (1007, '商品5', '好看的商品', 100.00, 'O1CN01DpOUJZ2HQv9hJdF7p_!!3170729146.jpg', 100001, '数码');
INSERT INTO `goods` VALUES (1008, '商品6', '好看的商品', 60.00, 'O1CN01gceaJ31e5bi75e9QQ_!!654143820.jpg', 100001, '数码');
INSERT INTO `goods` VALUES (1010, '商品8', '好看的商品', 80.00, 'O1CN01MxqGzF1IOurOnKJkH_!!2616970884.jpg', 100001, '数码');
INSERT INTO `goods` VALUES (1011, '商品9', '好看的商品', 90.00, '5fa94baba96c0.jpg', 100001, '数码');
INSERT INTO `goods` VALUES (1012, '商品12', '好看的商品', 100.00, '电商11.jpg', 100001, '食品');
INSERT INTO `goods` VALUES (1013, '商品13', '好看的商品', 100.00, '1702024923131_母婴1.jpg', 100001, '食品');
INSERT INTO `goods` VALUES (1017, '商品17', '好看的商品', 100.00, '1702024941199_5fa94baba96c0.jpg', 100001, '食品');
INSERT INTO `goods` VALUES (1018, '商品18', '好看的商品', 200.00, '母婴1.jpg', 100001, '食品');
INSERT INTO `goods` VALUES (1019, '商品19', '好看的商品', 200.00, '电商4.jpg', 100001, '食品');
INSERT INTO `goods` VALUES (1020, '商品20', '好看的商品', 200.00, '1713255877273_电商11.jpg', 100001, '数码');

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `userid` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `identify` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `operate` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `operatedate` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of log
-- ----------------------------
INSERT INTO `log` VALUES (4, 'root', '管理员', '查询订单', '127.0.0.1', '2024-04-22 17:23:23');
INSERT INTO `log` VALUES (5, 'root', '管理员', '登录', '127.0.0.1', '2024-04-22 17:32:15');
INSERT INTO `log` VALUES (6, 'root', '管理员', '查看商品信息', '127.0.0.1', '2024-04-22 19:29:40');
INSERT INTO `log` VALUES (7, 'root', '管理员', '查看订单', '127.0.0.1', '2024-04-22 19:29:42');
INSERT INTO `log` VALUES (8, 'root', '管理员', '查询商品销售信息', '127.0.0.1', '2024-04-22 19:29:44');
INSERT INTO `log` VALUES (9, 'root', '管理员', '查询销售员业绩', '127.0.0.1', '2024-04-22 19:29:47');
INSERT INTO `log` VALUES (10, 'root', '管理员', '查看操作日志', '127.0.0.1', '2024-04-22 19:29:50');
INSERT INTO `log` VALUES (11, 'root', '管理员', '查看操作日志', '127.0.0.1', '2024-04-22 19:31:37');
INSERT INTO `log` VALUES (12, 'root', '管理员', '查看商品信息', '127.0.0.1', '2024-05-10 19:45:22');
INSERT INTO `log` VALUES (13, 'root', '管理员', '查看商品信息', '127.0.0.1', '2024-05-10 19:45:28');
INSERT INTO `log` VALUES (14, 'root', '管理员', '查看商品信息', '127.0.0.1', '2024-05-10 19:46:35');
INSERT INTO `log` VALUES (15, 'root', '管理员', '登录', '127.0.0.1', '2024-05-10 19:51:16');
INSERT INTO `log` VALUES (16, 'root', '管理员', '查看商品信息', '127.0.0.1', '2024-05-10 19:51:16');
INSERT INTO `log` VALUES (17, 'root', '管理员', '查看操作日志', '127.0.0.1', '2024-05-10 19:51:22');
INSERT INTO `log` VALUES (18, 'root', '管理员', '查看操作日志', '127.0.0.1', '2024-05-10 19:51:27');
INSERT INTO `log` VALUES (19, 'root', '管理员', '查看操作日志', '127.0.0.1', '2024-05-10 19:52:49');
INSERT INTO `log` VALUES (20, 'root', '管理员', '查看操作日志', '127.0.0.1', '2024-05-10 19:52:50');
INSERT INTO `log` VALUES (21, 'root', '管理员', '查看操作日志', '127.0.0.1', '2024-05-10 19:53:01');
INSERT INTO `log` VALUES (22, 'root', '管理员', '查看操作日志', '127.0.0.1', '2024-05-10 19:53:07');
INSERT INTO `log` VALUES (23, 'root', '管理员', '查看操作日志', '127.0.0.1', '2024-05-10 19:53:08');
INSERT INTO `log` VALUES (24, 'root', '管理员', '查看操作日志', '127.0.0.1', '2024-05-10 19:53:11');
INSERT INTO `log` VALUES (25, 'root', '管理员', '查看操作日志', '127.0.0.1', '2024-05-10 19:53:49');
INSERT INTO `log` VALUES (26, 'root', '管理员', '查看浏览日志', '127.0.0.1', '2024-05-10 19:53:50');
INSERT INTO `log` VALUES (27, 'root', '管理员', '查看操作日志', '127.0.0.1', '2024-05-10 19:53:52');
INSERT INTO `log` VALUES (28, '100001', '销售员', '登录', '127.0.0.1', '2024-05-10 20:46:03');
INSERT INTO `log` VALUES (29, '100001', '销售员', '查看商品信息', '127.0.0.1', '2024-05-10 20:46:03');
INSERT INTO `log` VALUES (30, 'root', '用户', '登录', '127.0.0.1', '2024-05-10 20:46:40');
INSERT INTO `log` VALUES (31, 'root', '用户', '登录', '127.0.0.1', '2024-05-12 19:44:23');
INSERT INTO `log` VALUES (32, 'root', '管理员', '登录', '127.0.0.1', '2024-05-12 19:46:18');
INSERT INTO `log` VALUES (33, 'root', '管理员', '查看订单', '127.0.0.1', '2024-05-12 19:46:20');
INSERT INTO `log` VALUES (34, 'root', '管理员', '查看订单', '127.0.0.1', '2024-05-12 19:46:23');
INSERT INTO `log` VALUES (35, 'root', '管理员', '查看订单', '127.0.0.1', '2024-05-12 19:49:35');
INSERT INTO `log` VALUES (36, 'root', '管理员', '查看订单', '127.0.0.1', '2024-05-12 19:50:09');
INSERT INTO `log` VALUES (37, 'root', '管理员', '查看订单', '127.0.0.1', '2024-05-12 19:50:35');
INSERT INTO `log` VALUES (38, '100001', '销售员', '登录', '127.0.0.1', '2024-05-12 19:54:40');
INSERT INTO `log` VALUES (39, '100001', '销售员', '查看商品信息', '127.0.0.1', '2024-05-12 19:54:40');
INSERT INTO `log` VALUES (40, '100001', '销售员', '查看订单', '127.0.0.1', '2024-05-12 19:55:07');
INSERT INTO `log` VALUES (41, '100001', '销售员', '查看订单', '127.0.0.1', '2024-05-12 19:56:15');
INSERT INTO `log` VALUES (42, 'root', '管理员', '登录', '127.0.0.1', '2024-05-20 21:41:13');
INSERT INTO `log` VALUES (43, 'root', '管理员', '登录', '127.0.0.1', '2024-05-20 21:41:34');

-- ----------------------------
-- Table structure for manager
-- ----------------------------
DROP TABLE IF EXISTS `manager`;
CREATE TABLE `manager`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `managername` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `managername`(`managername` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of manager
-- ----------------------------
INSERT INTO `manager` VALUES (3, 'root', 'password');

-- ----------------------------
-- Table structure for sale
-- ----------------------------
DROP TABLE IF EXISTS `sale`;
CREATE TABLE `sale`  (
  `saledate` date NOT NULL,
  `buynum` int NOT NULL,
  `goodsnum` int NOT NULL,
  `totalprice` float NOT NULL,
  UNIQUE INDEX `saledate`(`saledate` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sale
-- ----------------------------
INSERT INTO `sale` VALUES ('2023-12-03', 3, 13, 5250);
INSERT INTO `sale` VALUES ('2023-12-04', 10, 35, 6950);
INSERT INTO `sale` VALUES ('2023-12-05', 1, 5, 1500);
INSERT INTO `sale` VALUES ('2023-12-06', 2, 11, 5060);
INSERT INTO `sale` VALUES ('2023-12-07', 3, 22, 1530);
INSERT INTO `sale` VALUES ('2023-12-08', 6, 43, 2980);

-- ----------------------------
-- Table structure for sales
-- ----------------------------
DROP TABLE IF EXISTS `sales`;
CREATE TABLE `sales`  (
  `id` int NOT NULL,
  `password` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sales_id_index`(`id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sales
-- ----------------------------
INSERT INTO `sales` VALUES (100001, '12345678');
INSERT INTO `sales` VALUES (100002, '23568974');
INSERT INTO `sales` VALUES (100003, '12345678');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '无',
  `gender` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '男',
  `money` float NULL DEFAULT 0,
  `email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (5, 'singor', '123456', '无', '男', 0, 'zy2531165419@163.com');
INSERT INTO `user` VALUES (9, 'shown', '123456', '肖恩', '男', 0, 'zy2531165419@163.com');
INSERT INTO `user` VALUES (10, 'root', 'password', '无', '男', 0, 'zy2531165419@163.com');
INSERT INTO `user` VALUES (11, 'root2', 'password', 'root3', '男', 0, 'zy2531165419@163.com');

-- ----------------------------
-- Table structure for userorder
-- ----------------------------
DROP TABLE IF EXISTS `userorder`;
CREATE TABLE `userorder`  (
  `username` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `goodsid` int NOT NULL,
  `number` int NOT NULL DEFAULT 1,
  `buytime` datetime NOT NULL,
  `goodsname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of userorder
-- ----------------------------
INSERT INTO `userorder` VALUES ('shown', 1001, 4, '2023-12-02 20:08:33', '空气净化器');
INSERT INTO `userorder` VALUES ('shown', 1007, 4, '2023-12-03 10:47:41', '商品5');
INSERT INTO `userorder` VALUES ('shown', 1007, 4, '2023-12-03 10:47:41', '商品5');
INSERT INTO `userorder` VALUES ('shown', 1007, 3, '2023-12-03 10:49:26', '商品5');
INSERT INTO `userorder` VALUES ('shown', 1007, 3, '2023-12-03 10:49:26', '商品5');
INSERT INTO `userorder` VALUES ('shown', 1007, 5, '2023-12-03 10:56:26', '商品5');
INSERT INTO `userorder` VALUES ('shown', 1007, 5, '2023-12-03 10:56:26', '商品5');
INSERT INTO `userorder` VALUES ('singor', 1002, 10, '2023-12-03 16:45:42', '空气净化机');
INSERT INTO `userorder` VALUES ('singor', 1003, 3, '2023-12-03 19:23:38', '商品1');
INSERT INTO `userorder` VALUES ('singor', 1007, 4, '2023-12-03 19:23:38', '商品5');
INSERT INTO `userorder` VALUES ('singor', 1003, 4, '2023-12-03 19:34:32', '商品1');
INSERT INTO `userorder` VALUES ('singor', 1007, 6, '2023-12-03 19:34:32', '商品5');
INSERT INTO `userorder` VALUES ('singor', 1003, 5, '2023-12-03 19:54:00', '商品1');
INSERT INTO `userorder` VALUES ('singor', 1001, 7, '2023-12-03 19:54:00', '空气净化器');
INSERT INTO `userorder` VALUES ('singor', 1002, 1, '2023-12-03 19:54:27', '空气净化机');
INSERT INTO `userorder` VALUES ('singor', 1007, 1, '2023-12-04 16:18:09', '商品5');
INSERT INTO `userorder` VALUES ('singor', 1003, 4, '2023-12-04 16:40:02', '商品1');
INSERT INTO `userorder` VALUES ('singor', 1007, 6, '2023-12-04 16:40:02', '商品5');
INSERT INTO `userorder` VALUES ('singor', 1003, 3, '2023-12-04 16:42:33', '商品1');
INSERT INTO `userorder` VALUES ('singor', 1002, 6, '2023-12-04 16:42:33', '空气净化机');
INSERT INTO `userorder` VALUES ('singor', 1001, 4, '2023-12-04 17:20:27', '空气净化器');
INSERT INTO `userorder` VALUES ('singor', 1006, 5, '2023-12-04 17:20:27', '商品4');
INSERT INTO `userorder` VALUES ('singor', 1003, 3, '2023-12-04 18:38:27', '商品1');
INSERT INTO `userorder` VALUES ('singor', 1001, 2, '2023-12-04 18:38:27', '空气净化器');
INSERT INTO `userorder` VALUES ('shown', 1002, 5, '2023-12-05 12:47:32', '空气净化机');
INSERT INTO `userorder` VALUES ('singor', 1001, 7, '2023-12-06 19:19:06', '空气净化器');
INSERT INTO `userorder` VALUES ('singor', 1006, 4, '2023-12-06 19:19:06', '商品4');
INSERT INTO `userorder` VALUES ('singor', 1005, 7, '2023-12-07 19:38:15', '商品3');
INSERT INTO `userorder` VALUES ('singor', 1009, 6, '2023-12-07 19:38:15', '商品7');
INSERT INTO `userorder` VALUES ('singor', 1012, 9, '2023-12-07 19:38:15', '商品12');
INSERT INTO `userorder` VALUES ('singor', 1005, 5, '2023-12-08 13:27:12', '商品3');
INSERT INTO `userorder` VALUES ('shown', 1003, 6, '2023-12-08 13:27:43', '商品1');
INSERT INTO `userorder` VALUES ('root', 1006, 5, '2023-12-09 20:09:38', '商品4');

SET FOREIGN_KEY_CHECKS = 1;
