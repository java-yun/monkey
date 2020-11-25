/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : yun_dev

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 13/11/2020 18:03:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for yun_product
-- ----------------------------
DROP TABLE IF EXISTS `yun_product`;
CREATE TABLE `yun_product`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `code` char(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '00000000' COMMENT '商品编码',
  `type` tinyint NOT NULL DEFAULT 1 COMMENT '商品类型：1-普通商品，2-活动商品',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '商品名称',
  `brand_id` int NOT NULL DEFAULT 0 COMMENT '品牌id',
  `category_id` int NOT NULL DEFAULT 0 COMMENT '类目id',
  `price` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '商品价格',
  `main_pic` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '商品主图',
  `detail_pics` varchar(8192) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '商品详情页图片列表，英文逗号隔开',
  `brief` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '商品简介',
  `description` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '商品描述',
  `audit_status` tinyint NOT NULL DEFAULT 1 COMMENT '审核状态：1-提报，2-初审通过，3-复审通过',
  `is_on_sale` tinyint NOT NULL DEFAULT 0 COMMENT '是否上架：0-否，1-是',
  `on_sale_time` datetime(0) NULL DEFAULT NULL COMMENT '上架时间',
  `off_sale_time` datetime(0) NULL DEFAULT NULL COMMENT '下架时间',
  `stock_num` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '初始库存数量',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `create_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `update_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_product_code`(`code`) USING BTREE,
  INDEX `idx_product_name`(`name`) USING BTREE,
  INDEX `idx_product_on_sale`(`on_sale_time`) USING BTREE,
  INDEX `idx_product_off_sale`(`off_sale_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
