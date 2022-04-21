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

 Date: 21/04/2022 15:52:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_operation_log`;
CREATE TABLE `sys_operation_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip',
  `request_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '接口路径',
  `request_params` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '请求参数',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '操作人Id',
  `operation_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  `request_result` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '返回结果',
  `request_method` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求方法',
  `run_time` bigint(20) NULL DEFAULT NULL COMMENT '执行时间毫秒',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `modify_time` datetime(0) NULL DEFAULT NULL,
  `create_id` bigint(20) NULL DEFAULT NULL,
  `modify_id` bigint(20) NULL DEFAULT NULL,
  `is_delete` tinyint(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '操作日志表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_operation_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
