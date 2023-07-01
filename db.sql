/*
 Navicat Premium Data Transfer

 Source Server         : 本地数据库
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : localhost:3306
 Source Schema         : db

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 29/04/2023 12:20:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin_information
-- ----------------------------
DROP TABLE IF EXISTS `admin_information`;
CREATE TABLE `admin_information`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '管理员ID',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '管理员用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '管路员密码',
  `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '管理员昵称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_information
-- ----------------------------
INSERT INTO `admin_information` VALUES (1, 'ls', '$2a$10$8fStd7yGpAK0KaqbPX2MKupBgpb6jBtAZ6Rj6yv64VGcz2ay5.Sgu', '李四');
INSERT INTO `admin_information` VALUES (2, 'zs', '$2a$10$tv5w3NTqQyleXa0Df.T0CO0Stewqg4p/eRWoz5dMwBxp/6QPOQaIK', '张三');
INSERT INTO `admin_information` VALUES (3, 'we', '$2a$10$gGqy4Rx//k61ChuKDX9FO.hX34FBgOahFMN3t2.t7L1eqqDZ4wSHW', '王二');
INSERT INTO `admin_information` VALUES (4, 'sw', '$2a$10$qBHRVymq/Tb1tdPSmoVy3ue/ZkwJWiJ5CXbusgsqSjFCMKQTwZfMC', '孙五');
INSERT INTO `admin_information` VALUES (5, 'test', '$2a$10$W3gq2sFSjKlA6H7IGRHZNuCDa84FuxP8RNJ0qrEYuR7lx0oZvJbIi', '测试');
INSERT INTO `admin_information` VALUES (9, 'ztfb', '$2a$10$tYbCj7geHg3kgNT8U1oMAeLICphisZLA3P2EVH9ML9sYpJoJmEuxG', '分布');
INSERT INTO `admin_information` VALUES (10, 'fb', '$2a$10$6RkGFR2RPl3NzNGL9SMl.ORIh8pC.j3/IuUojvbrl0uEhjnZJ1Al6', '分布');
INSERT INTO `admin_information` VALUES (11, '张三', '$2a$10$fclbnKiZK/8RousoR77Yq.MOmj7kDYJBJOLqJck1pwyWN6Bw4LUq.', '张');

-- ----------------------------
-- Table structure for admin_register_code
-- ----------------------------
DROP TABLE IF EXISTS `admin_register_code`;
CREATE TABLE `admin_register_code`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '注册码ID',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '注册码内容（暂定注册码长度不超过255）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_register_code
-- ----------------------------
INSERT INTO `admin_register_code` VALUES (5, 'yEAzIl/ucd6PVb/PtC5mB.2bYeUr8.kNDFieeJdt2dt9GViMZIbH2');
INSERT INTO `admin_register_code` VALUES (6, '7/rzyqo5p5QEyJRiY482Yu3e1rIuZHzevwUeboaVaWlIlOhF0hyU');
INSERT INTO `admin_register_code` VALUES (7, 'qkqIk1YRMTCW9dm6noATQeayfUgC7x4k3W4/rb.4FTigaf.3YV');
INSERT INTO `admin_register_code` VALUES (8, 'OWAF.T59uVtbQV9QYIvL9OTkIsyBM/1rckSX6VKFOgBkDlVjd3i');
INSERT INTO `admin_register_code` VALUES (9, '4kTQ8ZJ/kVfR8G5fK7UIXewSTdF19wInstOYyY9v3TnMpNd0VU');
INSERT INTO `admin_register_code` VALUES (10, '7oH.TcdGBVxe1xm3x5EnMuYzeuwXN8ZKGSmC6d1Vmeus3X0');

-- ----------------------------
-- Table structure for hazard_source
-- ----------------------------
DROP TABLE IF EXISTS `hazard_source`;
CREATE TABLE `hazard_source`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '危险源ID',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '危险源类型',
  `number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '编号',
  `area` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '区域',
  `longitude` double NULL DEFAULT NULL COMMENT '经度',
  `latitude` double NULL DEFAULT NULL COMMENT '纬度',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '工作天数',
  `abnormal_number` int NULL DEFAULT NULL COMMENT '异常次数',
  `state` int NULL DEFAULT NULL COMMENT '当前是否正常工作（0为正常）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hazard_source
-- ----------------------------
INSERT INTO `hazard_source` VALUES (1, '存气罐', 'JS1241', '东一区', 118.818856, 31.8881, '2022-02-03 12:16:12', 1, 1);
INSERT INTO `hazard_source` VALUES (2, '储气罐', 'JS5123', '东二区', 118.818429, 31.887731, '2022-02-03 12:16:12', 0, 1);
INSERT INTO `hazard_source` VALUES (3, '存气罐', 'JS5135', '西一区', 118.819, 31.886772, '2022-02-03 12:16:12', 0, 1);
INSERT INTO `hazard_source` VALUES (4, '硫酸塔', 'JS1231', '东一区', 118.818429, 31.887731, NULL, 0, 1);
INSERT INTO `hazard_source` VALUES (7, '硫酸塔', 'JS1236', '东二区', 118.8204, 31.8876, '2022-08-29 15:28:25', 0, 0);

-- ----------------------------
-- Table structure for hazard_source_abnormal_log
-- ----------------------------
DROP TABLE IF EXISTS `hazard_source_abnormal_log`;
CREATE TABLE `hazard_source_abnormal_log`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '危险源日志ID',
  `report_time` datetime(0) NULL DEFAULT NULL COMMENT '上报时间',
  `exception_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '异常类型',
  `exception_data` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '异常数据',
  `exception_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '异常描述（详细信息）',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '状态',
  `hazard_source_id` int NULL DEFAULT NULL COMMENT '外键_危险源ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hazard_source_abnormal_log
-- ----------------------------
INSERT INTO `hazard_source_abnormal_log` VALUES (1, '2022-08-23 14:11:04', '出错', '压强过大', '出错', '已经报废', 1);
INSERT INTO `hazard_source_abnormal_log` VALUES (2, '2022-08-23 14:14:06', '出错', '压强过小', '出错', '正在维修', 1);
INSERT INTO `hazard_source_abnormal_log` VALUES (3, '2022-08-23 14:14:19', '出错', '转速过小', '出错', '正在维修', 2);
INSERT INTO `hazard_source_abnormal_log` VALUES (4, '2022-08-23 14:14:28', '出错', '压强过大', '出错', '维修完成', 2);
INSERT INTO `hazard_source_abnormal_log` VALUES (5, '2022-08-23 14:14:41', '出错', '转速过大', '出错', '已经报废', 3);
INSERT INTO `hazard_source_abnormal_log` VALUES (6, '2022-08-23 14:15:15', '出错', '压力过大', '出错', '已经报废', 3);
INSERT INTO `hazard_source_abnormal_log` VALUES (7, '2022-08-23 14:15:23', '出错', '压强过大', '出错', '维修完成', 4);
INSERT INTO `hazard_source_abnormal_log` VALUES (8, '2022-08-23 14:15:32', '重大异常', '重大异常', '出错', '正在维修', 4);

-- ----------------------------
-- Table structure for inspect_class
-- ----------------------------
DROP TABLE IF EXISTS `inspect_class`;
CREATE TABLE `inspect_class`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '班ID',
  `class_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '班名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of inspect_class
-- ----------------------------
INSERT INTO `inspect_class` VALUES (1, 'JS12');
INSERT INTO `inspect_class` VALUES (2, 'JS14');
INSERT INTO `inspect_class` VALUES (4, 'JS13');
INSERT INTO `inspect_class` VALUES (12, 'JS15');

-- ----------------------------
-- Table structure for inspect_employee
-- ----------------------------
DROP TABLE IF EXISTS `inspect_employee`;
CREATE TABLE `inspect_employee`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '巡检员工ID',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '巡检员工用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '巡检员工密码',
  `job_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '工号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
  `sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '性别',
  `age` int NULL DEFAULT NULL COMMENT '年龄',
  `job` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '职务',
  `tel` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电话',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '就职时间',
  `inspect_number` int NULL DEFAULT NULL COMMENT '应巡检次数',
  `inspect_finish_number` int NULL DEFAULT NULL COMMENT '实际巡检次数',
  `exception_found_number` int NULL DEFAULT NULL COMMENT '发现异常次数',
  `inspect_class_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '班名',
  `inspect_class_id` int NULL DEFAULT NULL COMMENT '外键_班ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of inspect_employee
-- ----------------------------
INSERT INTO `inspect_employee` VALUES (1, 'eels', '$2a$10$C9TCdiIxrGhnHXfnCRVHnejOdwau5NMLOHJ4bPDB3pFYK1koa4L/W', 'JS1202', '张三', '男', 19, '学生', '150123456789', '2022-09-02 10:22:35', 20, 15, 1, 'JS12', 1);
INSERT INTO `inspect_employee` VALUES (2, 'eezs', '$2a$10$3Sid5i3lqSyo5W38A6WumejXCwJ5vHPWT7QbhKJSNm5/bWYie3Bru', 'JS1204', '李四', '男', 20, '打工人', '150123456789', '2022-02-03 12:12:12', 30, 25, 2, 'JS13', 4);
INSERT INTO `inspect_employee` VALUES (3, 'zs', '$2a$10$Jef2nTv5SKhHtToqymd6g.ehjB3JlWhB1QS2ALVNF7ckloG59XZCu', 'JS13', '王二', '女', 0, '工人', '15000000', '2022-02-03 12:12:12', 25, 20, 3, 'JS13', 4);
INSERT INTO `inspect_employee` VALUES (4, 'ssd', '$2a$10$elg4Fi1tb2aqwEmyijNGH.PiuzZq8rAsBr.6Ch2tGlsWAx3tAh2du', 'JS11', '测试1', '女', 18, 'sdf', '5252', '2022-09-02 10:22:26', 60, 60, 4, 'JS14', 2);
INSERT INTO `inspect_employee` VALUES (5, 'dd', '$2a$10$ODKkT7oru5p.4R1pYKAFXud/YYMEun2YmsxzkiHZ1I9brNof5TUxu', 'JS22', '测试2', '男', 18, 'df', '53543', '2022-09-02 10:22:29', 10, 10, 5, 'JS12', 1);
INSERT INTO `inspect_employee` VALUES (6, 'cdsd', '$2a$10$EhnviDcYGgtyP1sB74YK5ulO35BeCuZBsVg/gABgEdKoVnUyLRTii', 'JS33', '测试3', '女', 18, 'fvdf', '453534', '2022-09-02 10:22:31', 7, 6, 6, 'JS14', 2);
INSERT INTO `inspect_employee` VALUES (7, 'abc', '$2a$10$ekbHl/ggOzvTpjX2xByWYeGCsEpK1MqY01nmd5ABoLyHw6CF3ZZcK', 'JS1203', '王四', '女', 18, '领导', '123456789123', '2022-09-02 10:22:33', 0, 0, 0, 'JS12', 1);
INSERT INTO `inspect_employee` VALUES (8, 'ztfb', '$2a$10$NNxwAfGB5awkezu5qyZ/RevKOYDvEDeQbIChcsOcL8Bw7lF4Xe1Wi', 'JS1213', '分布', '男', 20, '巡检人员', '15067834265', '2022-09-02 10:21:55', 0, 0, 0, 'JS12', 1);

-- ----------------------------
-- Table structure for inspect_employee_position
-- ----------------------------
DROP TABLE IF EXISTS `inspect_employee_position`;
CREATE TABLE `inspect_employee_position`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '巡检人员位置ID',
  `longitude` double NULL DEFAULT NULL COMMENT '经度',
  `latitude` double NULL DEFAULT NULL COMMENT '维度',
  `inspect_employee_id` int NULL DEFAULT NULL COMMENT '外键_巡检人员ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of inspect_employee_position
-- ----------------------------
INSERT INTO `inspect_employee_position` VALUES (1, 118.81995, 31.88645, 1);
INSERT INTO `inspect_employee_position` VALUES (2, 118.8194, 31.8865, 2);
INSERT INTO `inspect_employee_position` VALUES (3, 118.8183, 31.8873, 3);
INSERT INTO `inspect_employee_position` VALUES (4, 118.8202, 31.8882, 4);
INSERT INTO `inspect_employee_position` VALUES (5, 118.8191, 31.888769, 5);
INSERT INTO `inspect_employee_position` VALUES (6, 118.81772, 31.88751, 6);

-- ----------------------------
-- Table structure for inspect_point
-- ----------------------------
DROP TABLE IF EXISTS `inspect_point`;
CREATE TABLE `inspect_point`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '巡检点ID',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '巡检点类型',
  `number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '编号',
  `area` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '区域',
  `longitude` double NULL DEFAULT NULL COMMENT '经度',
  `latitude` double NULL DEFAULT NULL COMMENT '纬度',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '安装时间（可以据此计算出工作天数）',
  `abnormal_number` int NULL DEFAULT NULL COMMENT '异常次数',
  `finish` int NULL DEFAULT NULL COMMENT '是否完成检查（默认没有完成检查，为0）',
  `state` int NULL DEFAULT NULL COMMENT '当前是否正常工作（0为正常）',
  `inspect_route_id` int NULL DEFAULT NULL COMMENT '外键_巡检路线ID',
  `inspect_employee_id` int NULL DEFAULT NULL COMMENT '负责该巡检路线的员工ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of inspect_point
-- ----------------------------
INSERT INTO `inspect_point` VALUES (1, '储气罐', 'JS5145', '西一区', 118.818856, 31.8881, '2022-02-03 12:16:12', 0, 0, 0, 1, 0);
INSERT INTO `inspect_point` VALUES (2, '储气罐', 'JS5135', '东一区', 118.818429, 31.887731, '2022-02-03 12:16:12', 1, 0, 1, 1, 0);
INSERT INTO `inspect_point` VALUES (3, '煤气罐', 'JS1135', '东一区', 118.8199, 31.886, '2022-08-26 18:48:14', 7, 0, 1, 2, 0);
INSERT INTO `inspect_point` VALUES (4, '煤气罐', 'JS2135', '西二区', 118.818073, 31.886772, '2022-02-03 12:16:12', 0, 0, 0, 2, 0);
INSERT INTO `inspect_point` VALUES (5, '燃油罐', 'JS1234', '北一区', 118.819, 31.886126, '2022-08-26 16:18:20', 2, 0, 1, 2, 0);
INSERT INTO `inspect_point` VALUES (6, '燃气罐', 'JS4567', '北二区', 118.8192, 31.887, '2022-08-26 16:18:23', 0, 0, 0, 3, 0);
INSERT INTO `inspect_point` VALUES (7, '石油罐', 'JS4512', '南一区', 118.8185, 31.8875, '2022-08-26 18:26:40', 3, 0, 1, 1, 0);
INSERT INTO `inspect_point` VALUES (8, '硫酸塔', 'JS9998', '西一区', 118.8192, 31.8874, '2022-08-27 19:54:29', 0, 0, 0, 3, 0);
INSERT INTO `inspect_point` VALUES (9, '硫酸塔', 'JS9997', '东一区', 118.8201, 31.887, '2022-08-29 15:15:17', 0, 0, 0, 3, 0);

-- ----------------------------
-- Table structure for inspect_point_abnormal_log
-- ----------------------------
DROP TABLE IF EXISTS `inspect_point_abnormal_log`;
CREATE TABLE `inspect_point_abnormal_log`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '巡检点日志ID',
  `report_time` datetime(0) NULL DEFAULT NULL COMMENT '上报时间',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '巡检点描述（详细信息）',
  `picture_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '巡检照片路径',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '巡检点状态',
  `inspect_point_id` int NULL DEFAULT NULL COMMENT '外键_巡检点ID',
  `inspect_employee_id` int NULL DEFAULT NULL COMMENT '外键_巡检人员ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of inspect_point_abnormal_log
-- ----------------------------
INSERT INTO `inspect_point_abnormal_log` VALUES (1, '2022-08-27 15:00:14', '重大异常', '0', '已经报废', 1, 2);
INSERT INTO `inspect_point_abnormal_log` VALUES (2, '2022-08-17 15:01:08', '齿轮转速问题', '0', '已经报废', 1, 3);
INSERT INTO `inspect_point_abnormal_log` VALUES (3, '2022-08-20 15:01:15', '压强出错', '0', '已经报废', 2, 1);
INSERT INTO `inspect_point_abnormal_log` VALUES (4, '2022-08-25 14:46:46', '压强过小', '0', '已经报废', 3, 4);
INSERT INTO `inspect_point_abnormal_log` VALUES (5, '2022-08-10 14:47:04', '转速过大', '0', '已经报废', 4, 5);
INSERT INTO `inspect_point_abnormal_log` VALUES (6, '2022-08-27 14:47:06', '压强过高', '0', '已经报废', 5, 6);
INSERT INTO `inspect_point_abnormal_log` VALUES (7, '2022-08-08 14:47:10', '重大异常', '0', '已经报废', 7, 1);
INSERT INTO `inspect_point_abnormal_log` VALUES (8, '2022-08-14 14:47:13', '转速过小', '0', '已经报废', 8, 2);
INSERT INTO `inspect_point_abnormal_log` VALUES (9, '2022-08-26 19:06:16', '压强过大', '0', '已经报废', 3, 6);
INSERT INTO `inspect_point_abnormal_log` VALUES (10, '2022-08-05 19:06:19', '压强过大', '0', '已经报废', 3, 4);
INSERT INTO `inspect_point_abnormal_log` VALUES (11, '2022-08-24 19:07:41', '压强过大', '0', '已经报废', 4, 5);
INSERT INTO `inspect_point_abnormal_log` VALUES (12, '2022-08-28 19:06:26', '压强过大', '0', '已经报废', 5, 3);
INSERT INTO `inspect_point_abnormal_log` VALUES (13, '2022-08-07 19:06:28', '压强过大', '0', '已经报废', 8, 2);
INSERT INTO `inspect_point_abnormal_log` VALUES (14, '2022-08-12 19:06:31', '压强过大', '0', '已经报废', 7, 2);

-- ----------------------------
-- Table structure for inspect_route
-- ----------------------------
DROP TABLE IF EXISTS `inspect_route`;
CREATE TABLE `inspect_route`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '巡检路线ID',
  `route_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '巡检路线名称',
  `inspect_point_number` int NULL DEFAULT NULL COMMENT '包含的巡检点个数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of inspect_route
-- ----------------------------
INSERT INTO `inspect_route` VALUES (1, '路线1', 4);
INSERT INTO `inspect_route` VALUES (2, '路线2', 3);
INSERT INTO `inspect_route` VALUES (3, '路线3', 2);
INSERT INTO `inspect_route` VALUES (11, NULL, 0);

-- ----------------------------
-- Table structure for inspect_task
-- ----------------------------
DROP TABLE IF EXISTS `inspect_task`;
CREATE TABLE `inspect_task`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '巡检任务ID',
  `inspect_class_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '巡检班名',
  `inspect_route_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '巡检路线名',
  `finish` int NULL DEFAULT NULL COMMENT '是否完成（默认没有完成，为0）',
  `inspect_class_id` int NULL DEFAULT NULL COMMENT '外键_巡检班ID',
  `inspect_route_id` int NULL DEFAULT NULL COMMENT '外键_巡检路线ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of inspect_task
-- ----------------------------
INSERT INTO `inspect_task` VALUES (1, 'JS12', '路线1', 0, 1, 1);
INSERT INTO `inspect_task` VALUES (2, 'JS14', '路线2', 0, 2, 2);
INSERT INTO `inspect_task` VALUES (3, 'JS13', '路线3', 0, 4, 3);
INSERT INTO `inspect_task` VALUES (7, NULL, NULL, 0, 0, 11);

-- ----------------------------
-- Table structure for inspect_task_item
-- ----------------------------
DROP TABLE IF EXISTS `inspect_task_item`;
CREATE TABLE `inspect_task_item`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '巡检任务子项ID',
  `start_time` time(0) NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` time(0) NULL DEFAULT NULL COMMENT '结束时间',
  `inspect_employee_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `inspect_employee_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `inspect_class_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `inspect_route_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `inspect_task_id` int NULL DEFAULT NULL COMMENT '对应的巡检任务ID',
  `inspect_employee_id` int NULL DEFAULT NULL COMMENT '负责该任务的员工的ID',
  `finish` int NULL DEFAULT NULL COMMENT '完成状态（默认没有完成，为0）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of inspect_task_item
-- ----------------------------
INSERT INTO `inspect_task_item` VALUES (1, '04:00:00', '06:00:00', 'JS1202', '张三', 'JS14', '路线2', 2, 1, 0);
INSERT INTO `inspect_task_item` VALUES (2, '06:00:00', '08:00:00', 'JS11', '测试1', 'JS14', '路线2', 2, 6, 0);
INSERT INTO `inspect_task_item` VALUES (3, '08:00:00', '10:00:00', 'JS33', '测试3', 'JS14', '路线2', 1, 3, 0);
INSERT INTO `inspect_task_item` VALUES (4, '04:00:00', '06:00:00', 'JS1204', '李四', 'JS13', '路线3', 0, 0, 0);
INSERT INTO `inspect_task_item` VALUES (5, '06:00:00', '08:00:00', 'JS13', '王二', 'JS13', '路线3', 0, 0, 0);
INSERT INTO `inspect_task_item` VALUES (6, '05:00:00', '06:00:00', 'JS22', '测试2', 'JS12', '路线1', 0, 0, 0);
INSERT INTO `inspect_task_item` VALUES (9, '04:00:00', '05:00:00', 'JS22', '测试2', 'JS12', '路线1', 0, 0, 0);
INSERT INTO `inspect_task_item` VALUES (12, '09:00:00', '10:00:00', 'JS22', '测试2', 'JS12', '路线1', 0, 0, 0);

-- ----------------------------
-- Table structure for statistics_data
-- ----------------------------
DROP TABLE IF EXISTS `statistics_data`;
CREATE TABLE `statistics_data`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '统计数据（展示在数据大屏的数据）ID',
  `inspect_number` int NULL DEFAULT NULL COMMENT '巡检总次数',
  `inspect_point_abnormal_number` int NULL DEFAULT NULL COMMENT '巡检点异常次数',
  `hazard_source_number` int NULL DEFAULT NULL COMMENT '系统发现危险源异常次数',
  `device_number` int NULL DEFAULT NULL COMMENT '设备总数',
  `device_normal_number` int NULL DEFAULT NULL COMMENT '正常设备数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of statistics_data
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
