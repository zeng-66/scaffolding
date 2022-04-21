/*
 Navicat Premium Data Transfer

 Source Server         : gz-cdb-qmdcp2at.sql.tencentcdb.com_58813
 Source Server Type    : MySQL
 Source Server Version : 50628
 Source Host           : gz-cdb-qmdcp2at.sql.tencentcdb.com:58813
 Source Schema         : jd_recruit

 Target Server Type    : MySQL
 Target Server Version : 50628
 File Encoding         : 65001

 Date: 21/04/2022 16:16:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for jd_user
-- ----------------------------
DROP TABLE IF EXISTS `jd_user`;
CREATE TABLE `jd_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `unionid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户unionid',
  `openid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户openid',
  `wx_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信号',
  `nick_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `head_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `sex` tinyint(1) NULL DEFAULT 0 COMMENT '性别：0-不限/未知  1-男 2-女',
  `birthday` date NULL DEFAULT NULL COMMENT '生日',
  `country` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '国家',
  `province` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省份',
  `city` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '城市',
  `phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `register_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '注册时间',
  `last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '上次登录时间',
  `corporate_id` bigint(20) NULL DEFAULT NULL COMMENT '企业ID',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '状态：0-禁用 1-启用',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0-否 1-是',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `modify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `create_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `modify_id` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_phone`(`phone`) USING BTREE,
  UNIQUE INDEX `idx_openid`(`openid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of jd_user
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
