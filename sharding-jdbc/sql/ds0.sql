/*
 Navicat Premium Data Transfer

 Source Server         : local_8
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : localhost:3306
 Source Schema         : ds0

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 28/04/2021 11:50:16
*/

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_config
-- ----------------------------
DROP TABLE IF EXISTS `t_config`;
CREATE TABLE `t_config`
(
    `id`               bigint(30) NOT NULL,
    `remark`           varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `create_time`      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `last_modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_order_0
-- ----------------------------
DROP TABLE IF EXISTS `t_order_0`;
CREATE TABLE `t_order_0`
(
    `order_id`    bigint(200) NOT NULL,
    `order_no`    varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `create_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `price`       decimal(10, 2) NULL DEFAULT NULL,
    PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_order_1
-- ----------------------------
DROP TABLE IF EXISTS `t_order_1`;
CREATE TABLE `t_order_1`
(
    `order_id`    bigint(200) NOT NULL,
    `order_no`    varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `create_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `price`       decimal(10, 2) NULL DEFAULT NULL,
    PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_order_2
-- ----------------------------
DROP TABLE IF EXISTS `t_order_2`;
CREATE TABLE `t_order_2`
(
    `order_id`    bigint(200) NOT NULL,
    `order_no`    varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `create_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `price`       decimal(10, 2) NULL DEFAULT NULL,
    PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_order_item_0
-- ----------------------------
DROP TABLE IF EXISTS `t_order_item_0`;
CREATE TABLE `t_order_item_0`
(
    `item_id`   bigint(100) NOT NULL,
    `order_no`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `item_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `price`     decimal(10, 2) NULL DEFAULT NULL,
    PRIMARY KEY (`item_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_order_item_1
-- ----------------------------
DROP TABLE IF EXISTS `t_order_item_1`;
CREATE TABLE `t_order_item_1`
(
    `item_id`   bigint(100) NOT NULL,
    `order_no`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `item_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `price`     decimal(10, 2) NULL DEFAULT NULL,
    PRIMARY KEY (`item_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_order_item_2
-- ----------------------------
DROP TABLE IF EXISTS `t_order_item_2`;
CREATE TABLE `t_order_item_2`
(
    `item_id`   bigint(100) NOT NULL,
    `order_no`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `item_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `price`     decimal(10, 2) NULL DEFAULT NULL,
    PRIMARY KEY (`item_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

SET
FOREIGN_KEY_CHECKS = 1;
