/*
 Navicat Premium Data Transfer

 Source Server         : monkey
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : monkey_web_dev

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 31/12/2020 17:58:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '菜单表ID',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '菜单名称',
  `code` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0000' COMMENT '菜单的编码, 1001开始',
  `p_code` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '父 code 编码',
  `level` tinyint NOT NULL DEFAULT 1 COMMENT '菜单的层级 ',
  `url` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '跳转链接',
  `order_num` smallint UNSIGNED NOT NULL DEFAULT 0 COMMENT '排序字段',
  `icon` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '图标',
  `create_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `permission` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '权限',
  `menu_type` tinyint(1) NOT NULL DEFAULT 0 COMMENT '0 菜单  1 按钮',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `IDX_code`(`code`) USING BTREE,
  INDEX `IDX_parent_code`(`p_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', '1001', '', 1, '', 1, '', 'admin', '2020-12-24 13:53:18', 'admin', '2020-12-24 13:53:18', '', 0);
INSERT INTO `sys_menu` VALUES (2, '菜单管理', '100101', '1001', 2, 'menu/showMenu', 1, '', 'admin', '2020-12-29 11:31:12', 'admin', '2020-12-30 17:50:45', 'menu:show', 0);
INSERT INTO `sys_menu` VALUES (3, '新增', '10010101', '100101', 3, '', 1, '', 'admin', '2020-12-29 17:45:15', 'admin', '2020-12-29 17:45:15', 'menu:add', 1);
INSERT INTO `sys_menu` VALUES (4, '编辑', '10010102', '100101', 3, '', 2, '', 'admin', '2020-12-30 09:36:20', 'admin', '2020-12-30 09:36:20', 'menu:edit', 1);
INSERT INTO `sys_menu` VALUES (5, '删除', '10010103', '100101', 3, '', 3, '', 'admin', '2020-12-30 09:36:52', 'admin', '2020-12-30 11:22:04', 'menu:del', 1);
INSERT INTO `sys_menu` VALUES (9, '系统权限', '100102', '1001', 2, 'role/showRole', 2, '', 'admin', '2020-12-30 10:21:22', 'admin', '2020-12-30 10:21:22', 'role:show', 0);
INSERT INTO `sys_menu` VALUES (10, '新增', '10010201', '100102', 3, '', 1, '', 'admin', '2020-12-30 17:52:42', 'admin', '2020-12-30 17:52:42', 'role:add', 1);
INSERT INTO `sys_menu` VALUES (11, '编辑', '10010202', '100102', 3, '', 2, '', 'admin', '2020-12-30 17:53:05', 'admin', '2020-12-30 17:53:05', 'role:update', 1);
INSERT INTO `sys_menu` VALUES (12, '删除', '10010203', '100102', 3, '', 3, '', 'admin', '2020-12-30 17:53:31', 'admin', '2020-12-30 17:53:31', 'role:del', 1);
INSERT INTO `sys_menu` VALUES (13, '系统用户', '100103', '1001', 2, 'user/showUser', 3, '', 'admin', '2020-12-31 17:21:36', 'admin', '2020-12-31 17:21:36', 'user:show', 0);
INSERT INTO `sys_menu` VALUES (14, '新增', '10010301', '100103', 3, '', 1, '', 'admin', '2020-12-31 17:22:42', 'admin', '2020-12-31 17:22:42', 'user:add', 1);
INSERT INTO `sys_menu` VALUES (15, '编辑', '10010302', '100103', 3, '', 2, '', 'admin', '2020-12-31 17:23:12', 'admin', '2020-12-31 17:23:12', 'user:update', 1);
INSERT INTO `sys_menu` VALUES (16, '删除', '10010303', '100103', 3, '', 3, '', 'admin', '2020-12-31 17:23:44', 'admin', '2020-12-31 17:23:44', 'user:del', 1);
INSERT INTO `sys_menu` VALUES (17, '修改密码', '10010304', '100103', 3, '', 4, '', 'admin', '2020-12-31 17:24:11', 'admin', '2020-12-31 17:24:11', 'user:repass', 1);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '角色表ID',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '角色表增加了层级关系  pid 为角色的父 id',
  `p_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '父编码',
  `role_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '角色表名称',
  `description` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '角色说明',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `create_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `update_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `remark` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `IDX_SYSRL_`(`role_name`, `create_user`, `create_time`, `update_user`, `update_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '', '', '超级管理员', '最高权限', '2020-12-24 13:55:55', 'admin', '2020-12-24 13:55:55', 'admin', '');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` int NOT NULL COMMENT '角色ID',
  `menu_id` int NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色菜单关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1, 1);
INSERT INTO `sys_role_menu` VALUES (1, 2);
INSERT INTO `sys_role_menu` VALUES (1, 3);
INSERT INTO `sys_role_menu` VALUES (1, 4);
INSERT INTO `sys_role_menu` VALUES (1, 5);
INSERT INTO `sys_role_menu` VALUES (1, 9);
INSERT INTO `sys_role_menu` VALUES (1, 10);
INSERT INTO `sys_role_menu` VALUES (1, 11);
INSERT INTO `sys_role_menu` VALUES (1, 12);
INSERT INTO `sys_role_menu` VALUES (1, 13);
INSERT INTO `sys_role_menu` VALUES (1, 14);
INSERT INTO `sys_role_menu` VALUES (1, 15);
INSERT INTO `sys_role_menu` VALUES (1, 16);
INSERT INTO `sys_role_menu` VALUES (1, 17);

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user`  (
  `user_id` int NOT NULL COMMENT '用户ID',
  `role_id` int NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户与角色关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
INSERT INTO `sys_role_user` VALUES (1, 1);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '开发者平台/商户平台用户ID',
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '密码',
  `mobile` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '手机号',
  `age` tinyint NOT NULL DEFAULT 0 COMMENT '年龄',
  `email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '邮箱',
  `photo` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '头像',
  `real_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '真实姓名',
  `del_flag` tinyint NOT NULL DEFAULT 0 COMMENT '是否可用：1-是，0否',
  `type` tinyint NOT NULL DEFAULT 1 COMMENT '用户类型：1-内部用户；2-商户用户',
  `create_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `update_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_username`(`username`) USING BTREE,
  INDEX `IDX_username`(`username`) USING BTREE,
  INDEX `IDX_type`(`type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '开发者平台/商户平台用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', 'e0b141de1c8091be350d3fc80de66528', '18955310615', 18, '', '', '', 1, 1, '', '', '2020-12-23 16:51:10', '2020-12-23 16:51:10');

SET FOREIGN_KEY_CHECKS = 1;
