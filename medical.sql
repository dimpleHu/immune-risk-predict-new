/*
 Navicat Premium Dump SQL

 Source Server         : sky-felling
 Source Server Type    : MySQL
 Source Server Version : 80041 (8.0.41)
 Source Host           : localhost:3306
 Source Schema         : medical

 Target Server Type    : MySQL
 Target Server Version : 80041 (8.0.41)
 File Encoding         : 65001

 Date: 27/10/2025 16:30:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `adminID` int NOT NULL AUTO_INCREMENT COMMENT '管理员编号',
  `adminName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '管理员姓名',
  `phoneNumber` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '管理员电话号码',
  `password` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '管理员密码',
  PRIMARY KEY (`adminID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'admin', '13800000001', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO `admin` VALUES (2, 'admin02', '13800000002', 'e10adc3949ba59abbe56e057f20f883e');

-- ----------------------------
-- Table structure for affected
-- ----------------------------
DROP TABLE IF EXISTS `affected`;
CREATE TABLE `affected`  (
  `affectedID` int NOT NULL AUTO_INCREMENT COMMENT '症状影响部位编号',
  `affectedName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '症状影响部位名称',
  PRIMARY KEY (`affectedID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of affected
-- ----------------------------

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `commentID` int NOT NULL AUTO_INCREMENT COMMENT '评论编号',
  `userID` int NOT NULL COMMENT '评论用户编号',
  `orderID` int NOT NULL COMMENT '评论订单编号',
  `projectID` int NULL DEFAULT NULL COMMENT '项目编号',
  `comments` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '评论文字内容',
  `commentImage` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评论图片链接或路径',
  `score` int NOT NULL COMMENT '评分（1-5分）',
  `commentTime` datetime NOT NULL COMMENT '评论时间',
  `likes` int NULL DEFAULT 0 COMMENT '点赞数',
  PRIMARY KEY (`commentID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (22, 16, 20, 8, '非常不错', 'http://localhost:8085/sky-feeling/files/ccd0b953-037b-4717-b83e-697b37963331.jpg', 5, '2025-06-04 22:34:18', 0);
INSERT INTO `comment` VALUES (23, 17, 24, 8, '不错', NULL, 5, '2025-06-08 12:35:07', 0);
INSERT INTO `comment` VALUES (24, 17, 54, 12, '不错', 'http://localhost:8085/sky-feeling/files/81d3b24f-6fbf-4bad-9bc5-d30ebb66718f.jpg', 5, '2025-06-08 13:09:44', 0);
INSERT INTO `comment` VALUES (26, 15, 62, 12, '不错吧', NULL, 4, '2025-07-16 21:51:22', 0);

-- ----------------------------
-- Table structure for commentreply
-- ----------------------------
DROP TABLE IF EXISTS `commentreply`;
CREATE TABLE `commentreply`  (
  `replyID` int NOT NULL AUTO_INCREMENT COMMENT '回复编号',
  `commentID` int NOT NULL COMMENT '被回复的评论编号',
  `replierType` enum('user','admin') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '回复人类型',
  `replierID` int NOT NULL COMMENT '回复人编号',
  `replyContent` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '回复内容',
  `replyTime` datetime NOT NULL COMMENT '回复时间',
  PRIMARY KEY (`replyID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of commentreply
-- ----------------------------
INSERT INTO `commentreply` VALUES (14, 17, 'admin', 1, '谢谢支持, 欢迎再次体验!', '2025-06-03 05:23:58');
INSERT INTO `commentreply` VALUES (15, 19, 'admin', 1, '谢谢支持!欢迎再次体验', '2025-06-03 08:08:39');
INSERT INTO `commentreply` VALUES (16, 18, 'admin', 1, '谢谢支持!欢迎再次体验', '2025-06-03 08:08:44');
INSERT INTO `commentreply` VALUES (19, 18, 'admin', 2, '谢谢您的支持', '2025-06-04 16:48:04');
INSERT INTO `commentreply` VALUES (20, 22, 'user', 17, '我也好想去啊', '2025-06-05 21:30:34');
INSERT INTO `commentreply` VALUES (21, 24, 'admin', 1, '谢谢你的支持', '2025-06-08 20:53:14');

-- ----------------------------
-- Table structure for disease
-- ----------------------------
DROP TABLE IF EXISTS `disease`;
CREATE TABLE `disease`  (
  `diseaseID` int NOT NULL AUTO_INCREMENT,
  `diseaseName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `diseaseType` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `probability` int NULL DEFAULT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `suggestions` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `riskfactors` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `symptoms` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `diseaseImage` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `otherImages` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`diseaseID` DESC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of disease
-- ----------------------------
INSERT INTO `disease` VALUES (16, '银屑病', NULL, 90, '慢性炎症性皮肤病，表现为边界清楚的红斑，上覆银白色鳞屑，可伴瘙痒，部分伴关节肿痛（银屑病关节炎）。', '避免搔抓、热水烫洗，忌辛辣刺激饮食，遵医嘱用外用药或光疗，保持皮肤湿润，管理压力以防复发。', '遗传：家族史是重要因素，多个基因（如 PSORS1）与发病相关，亲属中有患者时风险升高。\r\n环境因素：感染（如链球菌感染可诱发点滴状银屑病）、皮肤损伤（如外伤、晒伤，即 “同形反应”）、压力过大、吸烟、酗酒、肥胖等可能诱发或加重病情。\r\n免疫因素：T 细胞异常活化导致炎症因子释放，引发皮肤角质形成细胞过度增殖。', '皮肤症状：特征性鳞屑性红斑或斑块，表面覆盖银白色鳞屑，刮除鳞屑后可见淡红色发光半透明薄膜（薄膜现象），再刮除薄膜可见点状出血（Auspitz 征）。常见于头皮、肘部、膝部、腰骶部等部位，皮疹可呈点滴状、斑块状、脓疱型等类型。\r\n关节症状：部分患者可并发银屑病关节炎，表现为关节疼痛、肿胀、僵硬，可累及手足小关节、脊柱等，严重时导致关节畸形。\r\n其他：头皮皮损可导致束状发，指甲可出现顶针样凹陷、增厚、变色等。', 'images/disease2.jpg', 'images/disease2-1.jpg,images/disease2-2.jpg');
INSERT INTO `disease` VALUES (15, '银屑病', NULL, 70, '慢性炎症性皮肤病，表现为边界清楚的红斑，上覆银白色鳞屑，可伴瘙痒，部分伴关节肿痛（银屑病关节炎）。', '避免搔抓、热水烫洗，忌辛辣刺激饮食，遵医嘱用外用药或光疗，保持皮肤湿润，管理压力以防复发。', '遗传：家族史是重要因素，多个基因（如 PSORS1）与发病相关，亲属中有患者时风险升高。\r\n环境因素：感染（如链球菌感染可诱发点滴状银屑病）、皮肤损伤（如外伤、晒伤，即 “同形反应”）、压力过大、吸烟、酗酒、肥胖等可能诱发或加重病情。\r\n免疫因素：T 细胞异常活化导致炎症因子释放，引发皮肤角质形成细胞过度增殖。', '皮肤症状：特征性鳞屑性红斑或斑块，表面覆盖银白色鳞屑，刮除鳞屑后可见淡红色发光半透明薄膜（薄膜现象），再刮除薄膜可见点状出血（Auspitz 征）。常见于头皮、肘部、膝部、腰骶部等部位，皮疹可呈点滴状、斑块状、脓疱型等类型。\r\n关节症状：部分患者可并发银屑病关节炎，表现为关节疼痛、肿胀、僵硬，可累及手足小关节、脊柱等，严重时导致关节畸形。\r\n其他：头皮皮损可导致束状发，指甲可出现顶针样凹陷、增厚、变色等。', NULL, NULL);
INSERT INTO `disease` VALUES (14, '银屑病', NULL, 50, '慢性炎症性皮肤病，表现为边界清楚的红斑，上覆银白色鳞屑，可伴瘙痒，部分伴关节肿痛（银屑病关节炎）。', '避免搔抓、热水烫洗，忌辛辣刺激饮食，遵医嘱用外用药或光疗，保持皮肤湿润，管理压力以防复发。', '遗传：家族史是重要因素，多个基因（如 PSORS1）与发病相关，亲属中有患者时风险升高。\r\n环境因素：感染（如链球菌感染可诱发点滴状银屑病）、皮肤损伤（如外伤、晒伤，即 “同形反应”）、压力过大、吸烟、酗酒、肥胖等可能诱发或加重病情。\r\n免疫因素：T 细胞异常活化导致炎症因子释放，引发皮肤角质形成细胞过度增殖。', '皮肤症状：特征性鳞屑性红斑或斑块，表面覆盖银白色鳞屑，刮除鳞屑后可见淡红色发光半透明薄膜（薄膜现象），再刮除薄膜可见点状出血（Auspitz 征）。常见于头皮、肘部、膝部、腰骶部等部位，皮疹可呈点滴状、斑块状、脓疱型等类型。\r\n关节症状：部分患者可并发银屑病关节炎，表现为关节疼痛、肿胀、僵硬，可累及手足小关节、脊柱等，严重时导致关节畸形。\r\n其他：头皮皮损可导致束状发，指甲可出现顶针样凹陷、增厚、变色等。', NULL, NULL);
INSERT INTO `disease` VALUES (13, '银屑病', NULL, 20, '慢性炎症性皮肤病，表现为边界清楚的红斑，上覆银白色鳞屑，可伴瘙痒，部分伴关节肿痛（银屑病关节炎）。', '避免搔抓、热水烫洗，忌辛辣刺激饮食，遵医嘱用外用药或光疗，保持皮肤湿润，管理压力以防复发。', '遗传：家族史是重要因素，多个基因（如 PSORS1）与发病相关，亲属中有患者时风险升高。\r\n环境因素：感染（如链球菌感染可诱发点滴状银屑病）、皮肤损伤（如外伤、晒伤，即 “同形反应”）、压力过大、吸烟、酗酒、肥胖等可能诱发或加重病情。\r\n免疫因素：T 细胞异常活化导致炎症因子释放，引发皮肤角质形成细胞过度增殖。', '皮肤症状：特征性鳞屑性红斑或斑块，表面覆盖银白色鳞屑，刮除鳞屑后可见淡红色发光半透明薄膜（薄膜现象），再刮除薄膜可见点状出血（Auspitz 征）。常见于头皮、肘部、膝部、腰骶部等部位，皮疹可呈点滴状、斑块状、脓疱型等类型。\r\n关节症状：部分患者可并发银屑病关节炎，表现为关节疼痛、肿胀、僵硬，可累及手足小关节、脊柱等，严重时导致关节畸形。\r\n其他：头皮皮损可导致束状发，指甲可出现顶针样凹陷、增厚、变色等。', NULL, NULL);
INSERT INTO `disease` VALUES (12, '1型糖尿病', NULL, 90, '因胰岛 β 细胞破坏致胰岛素绝对缺乏，多青少年发病，表现为多饮、多尿、体重下降，易酮症酸中毒。', '终身胰岛素治疗，严格监测血糖，控制碳水摄入，规律运动（避免空腹），定期查眼底和肾功能，预防并发症。\n        胰岛素治疗推荐基础+餐时胰岛素方案（MDI）推荐注射长效胰岛素类似物（如甘精、德谷）每日1~2次。根据碳水化合物计数法计算餐时胰岛素剂量，餐前或餐后即刻注射。此方案适用大多数T1DM患者，尤其是儿童和青少年。', '遗传：HLA-DQA1、HLA-DQB1、HLA-DRB1 等基因与发病相关，家族史增加风险（但遗传模式较复杂，并非简单显性遗传）。\r\n环境因素：病毒感染（如柯萨奇病毒、风疹病毒）可能触发自身免疫反应，破坏胰岛 β 细胞；过早接触牛乳蛋白、缺乏母乳喂养等可能增加风险。\r\n自身免疫：机体产生针对胰岛 β 细胞的自身抗体（如胰岛素抗体、谷氨酸脱羧酶抗体），导致 β 细胞损伤，胰岛素分泌不足。', '典型 “三多一少” 症状：多饮（口渴明显，饮水量增加）、多食（易饥饿，进食量增多）、多尿（排尿次数和量增多）、体重减轻（尽管进食增加，体重仍快速下降）。\r\n其他：乏力、视物模糊、皮肤感染（如真菌感染）、酮症酸中毒（严重时出现恶心、呕吐、腹痛、呼吸深快、昏迷等）。\r\n特点：起病较急，多见于儿童和青少年，患者体内胰岛素绝对缺乏，需依赖外源性胰岛素治疗。', 'images/disease3.jpg', 'images/disease3-1.jpg,images/disease3-2.jpg');
INSERT INTO `disease` VALUES (11, '1型糖尿病', NULL, 70, '因胰岛 β 细胞破坏致胰岛素绝对缺乏，多青少年发病，表现为多饮、多尿、体重下降，易酮症酸中毒。', '终身胰岛素治疗，严格监测血糖，控制碳水摄入，规律运动（避免空腹），定期查眼底和肾功能，预防并发症。\n        胰岛素治疗推荐基础+餐时胰岛素方案（MDI）推荐注射长效胰岛素类似物（如甘精、德谷）每日1~2次。根据碳水化合物计数法计算餐时胰岛素剂量，餐前或餐后即刻注射。此方案适用大多数T1DM患者，尤其是儿童和青少年。', '遗传：HLA-DQA1、HLA-DQB1、HLA-DRB1 等基因与发病相关，家族史增加风险（但遗传模式较复杂，并非简单显性遗传）。\r\n环境因素：病毒感染（如柯萨奇病毒、风疹病毒）可能触发自身免疫反应，破坏胰岛 β 细胞；过早接触牛乳蛋白、缺乏母乳喂养等可能增加风险。\r\n自身免疫：机体产生针对胰岛 β 细胞的自身抗体（如胰岛素抗体、谷氨酸脱羧酶抗体），导致 β 细胞损伤，胰岛素分泌不足。', '典型 “三多一少” 症状：多饮（口渴明显，饮水量增加）、多食（易饥饿，进食量增多）、多尿（排尿次数和量增多）、体重减轻（尽管进食增加，体重仍快速下降）。\r\n其他：乏力、视物模糊、皮肤感染（如真菌感染）、酮症酸中毒（严重时出现恶心、呕吐、腹痛、呼吸深快、昏迷等）。\r\n特点：起病较急，多见于儿童和青少年，患者体内胰岛素绝对缺乏，需依赖外源性胰岛素治疗。', NULL, NULL);
INSERT INTO `disease` VALUES (10, '1型糖尿病', NULL, 50, '因胰岛 β 细胞破坏致胰岛素绝对缺乏，多青少年发病，表现为多饮、多尿、体重下降，易酮症酸中毒。', '终身胰岛素治疗，严格监测血糖，控制碳水摄入，规律运动（避免空腹），定期查眼底和肾功能，预防并发症。\n        胰岛素治疗推荐基础+餐时胰岛素方案（MDI）推荐注射长效胰岛素类似物（如甘精、德谷）每日1~2次。根据碳水化合物计数法计算餐时胰岛素剂量，餐前或餐后即刻注射。此方案适用大多数T1DM患者，尤其是儿童和青少年。', '遗传：HLA-DQA1、HLA-DQB1、HLA-DRB1 等基因与发病相关，家族史增加风险（但遗传模式较复杂，并非简单显性遗传）。\r\n环境因素：病毒感染（如柯萨奇病毒、风疹病毒）可能触发自身免疫反应，破坏胰岛 β 细胞；过早接触牛乳蛋白、缺乏母乳喂养等可能增加风险。\r\n自身免疫：机体产生针对胰岛 β 细胞的自身抗体（如胰岛素抗体、谷氨酸脱羧酶抗体），导致 β 细胞损伤，胰岛素分泌不足。', '典型 “三多一少” 症状：多饮（口渴明显，饮水量增加）、多食（易饥饿，进食量增多）、多尿（排尿次数和量增多）、体重减轻（尽管进食增加，体重仍快速下降）。\r\n其他：乏力、视物模糊、皮肤感染（如真菌感染）、酮症酸中毒（严重时出现恶心、呕吐、腹痛、呼吸深快、昏迷等）。\r\n特点：起病较急，多见于儿童和青少年，患者体内胰岛素绝对缺乏，需依赖外源性胰岛素治疗。', NULL, NULL);
INSERT INTO `disease` VALUES (9, '1型糖尿病', NULL, 20, '因胰岛 β 细胞破坏致胰岛素绝对缺乏，多青少年发病，表现为多饮、多尿、体重下降，易酮症酸中毒。', '终身胰岛素治疗，严格监测血糖，控制碳水摄入，规律运动（避免空腹），定期查眼底和肾功能，预防并发症。', '遗传：HLA-DQA1、HLA-DQB1、HLA-DRB1 等基因与发病相关，家族史增加风险（但遗传模式较复杂，并非简单显性遗传）。\r\n环境因素：病毒感染（如柯萨奇病毒、风疹病毒）可能触发自身免疫反应，破坏胰岛 β 细胞；过早接触牛乳蛋白、缺乏母乳喂养等可能增加风险。\r\n自身免疫：机体产生针对胰岛 β 细胞的自身抗体（如胰岛素抗体、谷氨酸脱羧酶抗体），导致 β 细胞损伤，胰岛素分泌不足。', '典型 “三多一少” 症状：多饮（口渴明显，饮水量增加）、多食（易饥饿，进食量增多）、多尿（排尿次数和量增多）、体重减轻（尽管进食增加，体重仍快速下降）。\r\n其他：乏力、视物模糊、皮肤感染（如真菌感染）、酮症酸中毒（严重时出现恶心、呕吐、腹痛、呼吸深快、昏迷等）。\r\n特点：起病较急，多见于儿童和青少年，患者体内胰岛素绝对缺乏，需依赖外源性胰岛素治疗。', NULL, NULL);
INSERT INTO `disease` VALUES (8, '类风湿性关节炎', NULL, 90, '自身免疫病，以对称性关节肿痛为特征，常累及手、足小关节，晚期可致关节畸形，伴晨僵（持续 1 小时以上）。', '尽早用抗风湿药，急性期制动，缓解期适度关节功能锻炼，注意保暖，避免关节负重，定期监测炎症指标。\n                 确诊RA 后先单用甲氨蝶呤，若对对甲氨蝶呤过敏，使用传统合成DMARDs，若疗效不佳，更换或联合使用其他 csDMARD；若疗效不佳，使用1种csDMARD+加用/更换1种DMARD。', '性别：女性发病率约为男性的 2-3 倍，更年期前后风险较高。\r\n遗传：HLA-DRB1 等位基因（尤其是 “共享表位”）与发病密切相关，家族史增加患病风险。\r\n环境因素：吸烟是明确的风险因素，可能诱发或加重免疫反应；感染（如牙周炎相关细菌）可能参与发病。', '关节症状：对称性多关节疼痛、肿胀、僵硬（晨僵明显，持续时间通常超过 1 小时），常见于手（掌指关节、近端指间关节）、腕、足、膝等关节，晚期可出现关节畸形（如 “天鹅颈”“纽扣花” 样改变）和功能障碍。\r\n全身症状：疲劳、低热、体重下降。\r\n其他：可能累及肺部（间质性肺炎）、心脏（心包炎）、眼睛（干燥综合征、巩膜炎）等，部分患者可出现类风湿结节（皮下硬结节，多位于关节伸侧）。', 'images/disease4.jpg', 'images/disease4-1.jpg,images/disease4-2.jpg');
INSERT INTO `disease` VALUES (7, '类风湿性关节炎', NULL, 70, '自身免疫病，以对称性关节肿痛为特征，常累及手、足小关节，晚期可致关节畸形，伴晨僵（持续 1 小时以上）。', '尽早用抗风湿药，急性期制动，缓解期适度关节功能锻炼，注意保暖，避免关节负重，定期监测炎症指标。\n                 确诊RA 后先单用甲氨蝶呤，若对对甲氨蝶呤过敏，使用传统合成DMARDs，若疗效不佳，更换或联合使用其他 csDMARD；若疗效不佳，使用1种csDMARD+加用/更换1种DMARD。', '性别：女性发病率约为男性的 2-3 倍，更年期前后风险较高。\r\n遗传：HLA-DRB1 等位基因（尤其是 “共享表位”）与发病密切相关，家族史增加患病风险。\r\n环境因素：吸烟是明确的风险因素，可能诱发或加重免疫反应；感染（如牙周炎相关细菌）可能参与发病。', '关节症状：对称性多关节疼痛、肿胀、僵硬（晨僵明显，持续时间通常超过 1 小时），常见于手（掌指关节、近端指间关节）、腕、足、膝等关节，晚期可出现关节畸形（如 “天鹅颈”“纽扣花” 样改变）和功能障碍。\r\n全身症状：疲劳、低热、体重下降。\r\n其他：可能累及肺部（间质性肺炎）、心脏（心包炎）、眼睛（干燥综合征、巩膜炎）等，部分患者可出现类风湿结节（皮下硬结节，多位于关节伸侧）。', NULL, NULL);
INSERT INTO `disease` VALUES (6, '类风湿性关节炎', NULL, 50, '自身免疫病，以对称性关节肿痛为特征，常累及手、足小关节，晚期可致关节畸形，伴晨僵（持续 1 小时以上）。', '尽早用抗风湿药，急性期制动，缓解期适度关节功能锻炼，注意保暖，避免关节负重，定期监测炎症指标。\n                 确诊RA 后先单用甲氨蝶呤，若对对甲氨蝶呤过敏，使用传统合成DMARDs，若疗效不佳，更换或联合使用其他 csDMARD；若疗效不佳，使用1种csDMARD+加用/更换1种DMARD。', '性别：女性发病率约为男性的 2-3 倍，更年期前后风险较高。\r\n遗传：HLA-DRB1 等位基因（尤其是 “共享表位”）与发病密切相关，家族史增加患病风险。\r\n环境因素：吸烟是明确的风险因素，可能诱发或加重免疫反应；感染（如牙周炎相关细菌）可能参与发病。', '关节症状：对称性多关节疼痛、肿胀、僵硬（晨僵明显，持续时间通常超过 1 小时），常见于手（掌指关节、近端指间关节）、腕、足、膝等关节，晚期可出现关节畸形（如 “天鹅颈”“纽扣花” 样改变）和功能障碍。\r\n全身症状：疲劳、低热、体重下降。\r\n其他：可能累及肺部（间质性肺炎）、心脏（心包炎）、眼睛（干燥综合征、巩膜炎）等，部分患者可出现类风湿结节（皮下硬结节，多位于关节伸侧）。', NULL, NULL);
INSERT INTO `disease` VALUES (5, '类风湿性关节炎', NULL, 20, '自身免疫病，以对称性关节肿痛为特征，常累及手、足小关节，晚期可致关节畸形，伴晨僵（持续 1 小时以上）。', '尽早用抗风湿药，急性期制动，缓解期适度关节功能锻炼，注意保暖，避免关节负重，定期监测炎症指标。\n                 确诊RA 后先单用甲氨蝶呤，若对对甲氨蝶呤过敏，使用传统合成DMARDs，若疗效不佳，更换或联合使用其他 csDMARD；若疗效不佳，使用1种csDMARD+加用/更换1种DMARD。', '性别：女性发病率约为男性的 2-3 倍，更年期前后风险较高。\r\n遗传：HLA-DRB1 等位基因（尤其是 “共享表位”）与发病密切相关，家族史增加患病风险。\r\n环境因素：吸烟是明确的风险因素，可能诱发或加重免疫反应；感染（如牙周炎相关细菌）可能参与发病。', '关节症状：对称性多关节疼痛、肿胀、僵硬（晨僵明显，持续时间通常超过 1 小时），常见于手（掌指关节、近端指间关节）、腕、足、膝等关节，晚期可出现关节畸形（如 “天鹅颈”“纽扣花” 样改变）和功能障碍。\r\n全身症状：疲劳、低热、体重下降。\r\n其他：可能累及肺部（间质性肺炎）、心脏（心包炎）、眼睛（干燥综合征、巩膜炎）等，部分患者可出现类风湿结节（皮下硬结节，多位于关节伸侧）。', NULL, NULL);
INSERT INTO `disease` VALUES (4, '系统性红斑狼疮', NULL, 90, '自身免疫性疾病，可累及皮肤、关节、肾脏等多器官，表现为面部红斑、发热、乏力等。病程易反复，需长期管理。', '严格遵医嘱用免疫抑制剂，避免日晒和过度劳累，定期查血常规、肾功能，保持情绪稳定，预防感染。\n                     SLE治疗药物包括抗疟药、糖皮质激素和免疫抑制剂等。根据SLE 病情轻重度，对于无禁忌证的SLE患者，推荐长期使用羟氯喹作为基础治疗；对于轻SLE，在使用羟氯喹或非甾体抗炎药效果不佳时，可考虑使用小剂量激素(≤10mg/d泼尼松或等效剂量的其他激素)；对于中度SLE，推荐0.5~1mg·kg-1·d-1泼尼松或其他等效剂量激素；对于重度SLE，推荐1mg·kg-1·d-1泼尼松或其他等效剂量激素；对于狼疮危象，推荐激素冲击治疗。对于激素联用羟氯喹后效果不佳的患者，推荐叠加免疫抑制剂；对于激素和免疫抑制剂治疗后，效果不佳、不耐受或复发的SLE患者，推荐再叠加生物制剂。', '性别：女性（尤其是育龄女性）发病率显著高于男性，雌激素可能参与发病。\r\n遗传：家族中有自身免疫病病史者风险升高，HLA-DR2、HLA-DR3 等基因与发病相关。\r\n环境因素：紫外线照射、感染（如 EB 病毒）、某些药物（如普鲁卡因胺、肼屈嗪）、吸烟等可能诱发或加重病情。', '全身症状：发热、疲劳、体重减轻。\r\n皮肤黏膜：面部蝶形红斑（鼻梁和双颊对称的红斑，形似蝴蝶）、盘状红斑（圆形或椭圆形鳞屑性红斑）、口腔溃疡、脱发、对光敏感（日晒后皮疹加重）。\r\n关节肌肉：关节疼痛、肿胀（多累及手、腕、膝等，对称分布，一般不遗留畸形）、肌肉酸痛。\r\n内脏受累：可能累及肾脏（蛋白尿、血尿、肾功能异常）、肺部（胸膜炎、肺炎）、心脏（心包炎）、神经系统（头痛、癫痫、认知障碍）等。', 'images/disease1.jpg', 'images/disease1-1.jpg,images/disease1-2.jpg');
INSERT INTO `disease` VALUES (3, '系统性红斑狼疮', NULL, 70, '自身免疫性疾病，可累及皮肤、关节、肾脏等多器官，表现为面部红斑、发热、乏力等。病程易反复，需长期管理。', '严格遵医嘱用免疫抑制剂，避免日晒和过度劳累，定期查血常规、肾功能，保持情绪稳定，预防感染。\n                     SLE治疗药物包括抗疟药、糖皮质激素和免疫抑制剂等。根据SLE 病情轻重度，对于无禁忌证的SLE患者，推荐长期使用羟氯喹作为基础治疗；对于轻SLE，在使用羟氯喹或非甾体抗炎药效果不佳时，可考虑使用小剂量激素(≤10mg/d泼尼松或等效剂量的其他激素)；对于中度SLE，推荐0.5~1mg·kg-1·d-1泼尼松或其他等效剂量激素；对于重度SLE，推荐1mg·kg-1·d-1泼尼松或其他等效剂量激素；对于狼疮危象，推荐激素冲击治疗。对于激素联用羟氯喹后效果不佳的患者，推荐叠加免疫抑制剂；对于激素和免疫抑制剂治疗后，效果不佳、不耐受或复发的SLE患者，推荐再叠加生物制剂。', '性别：女性（尤其是育龄女性）发病率显著高于男性，雌激素可能参与发病。\r\n遗传：家族中有自身免疫病病史者风险升高，HLA-DR2、HLA-DR3 等基因与发病相关。\r\n环境因素：紫外线照射、感染（如 EB 病毒）、某些药物（如普鲁卡因胺、肼屈嗪）、吸烟等可能诱发或加重病情。', '全身症状：发热、疲劳、体重减轻。\n皮肤黏膜：面部蝶形红斑（鼻梁和双颊对称的红斑，形似蝴蝶）、盘状红斑（圆形或椭圆形鳞屑性红斑）、口腔溃疡、脱发、对光敏感（日晒后皮疹加重）。\n关节肌肉：关节疼痛、肿胀（多累及手、腕、膝等，对称分布，一般不遗留畸形）、肌肉酸痛。\n内脏受累：可能累及肾脏（蛋白尿、血尿、肾功能异常）、肺部（胸膜炎、肺炎）、心脏（心包炎）、神经系统（头痛、癫痫、认知障碍）等。', NULL, NULL);
INSERT INTO `disease` VALUES (2, '系统性红斑狼疮', NULL, 50, '自身免疫性疾病，可累及皮肤、关节、肾脏等多器官，表现为面部红斑、发热、乏力等。病程易反复，需长期管理。', '严格遵医嘱用免疫抑制剂，避免日晒和过度劳累，定期查血常规、肾功能，保持情绪稳定，预防感染。\n                     SLE治疗药物包括抗疟药、糖皮质激素和免疫抑制剂等。根据SLE 病情轻重度，对于无禁忌证的SLE患者，推荐长期使用羟氯喹作为基础治疗；对于轻SLE，在使用羟氯喹或非甾体抗炎药效果不佳时，可考虑使用小剂量激素(≤10mg/d泼尼松或等效剂量的其他激素)；对于中度SLE，推荐0.5~1mg·kg-1·d-1泼尼松或其他等效剂量激素；对于重度SLE，推荐1mg·kg-1·d-1泼尼松或其他等效剂量激素；对于狼疮危象，推荐激素冲击治疗。对于激素联用羟氯喹后效果不佳的患者，推荐叠加免疫抑制剂；对于激素和免疫抑制剂治疗后，效果不佳、不耐受或复发的SLE患者，推荐再叠加生物制剂。', '性别：女性（尤其是育龄女性）发病率显著高于男性，雌激素可能参与发病。\r\n遗传：家族中有自身免疫病病史者风险升高，HLA-DR2、HLA-DR3 等基因与发病相关。\r\n环境因素：紫外线照射、感染（如 EB 病毒）、某些药物（如普鲁卡因胺、肼屈嗪）、吸烟等可能诱发或加重病情。', '全身症状：发热、疲劳、体重减轻。\r\n皮肤黏膜：面部蝶形红斑（鼻梁和双颊对称的红斑，形似蝴蝶）、盘状红斑（圆形或椭圆形鳞屑性红斑）、口腔溃疡、脱发、对光敏感（日晒后皮疹加重）。\r\n关节肌肉：关节疼痛、肿胀（多累及手、腕、膝等，对称分布，一般不遗留畸形）、肌肉酸痛。\r\n内脏受累：可能累及肾脏（蛋白尿、血尿、肾功能异常）、肺部（胸膜炎、肺炎）、心脏（心包炎）、神经系统（头痛、癫痫、认知障碍）等。', NULL, NULL);
INSERT INTO `disease` VALUES (1, '系统性红斑狼疮', '', 20, '自身免疫性疾病，可累及皮肤、关节、肾脏等多器官，表现为面部红斑、发热、乏力等。病程易反复，需长期管理。', '严格遵医嘱用免疫抑制剂，避免日晒和过度劳累，定期查血常规、肾功能，保持情绪稳定，预防感染。\n                     SLE治疗药物包括抗疟药、糖皮质激素和免疫抑制剂等。根据SLE 病情轻重度，对于无禁忌证的SLE患者，推荐长期使用羟氯喹作为基础治疗；对于轻SLE，在使用羟氯喹或非甾体抗炎药效果不佳时，可考虑使用小剂量激素(≤10mg/d泼尼松或等效剂量的其他激素)；对于中度SLE，推荐0.5~1mg·kg-1·d-1泼尼松或其他等效剂量激素；对于重度SLE，推荐1mg·kg-1·d-1泼尼松或其他等效剂量激素；对于狼疮危象，推荐激素冲击治疗。对于激素联用羟氯喹后效果不佳的患者，推荐叠加免疫抑制剂；对于激素和免疫抑制剂治疗后，效果不佳、不耐受或复发的SLE患者，推荐再叠加生物制剂。', '性别：女性（尤其是育龄女性）发病率显著高于男性，雌激素可能参与发病。\r\n遗传：家族中有自身免疫病病史者风险升高，HLA-DR2、HLA-DR3 等基因与发病相关。\r\n环境因素：紫外线照射、感染（如 EB 病毒）、某些药物（如普鲁卡因胺、肼屈嗪）、吸烟等可能诱发或加重病情。', '全身症状：发热、疲劳、体重减轻。\r\n皮肤黏膜：面部蝶形红斑（鼻梁和双颊对称的红斑，形似蝴蝶）、盘状红斑（圆形或椭圆形鳞屑性红斑）、口腔溃疡、脱发、对光敏感（日晒后皮疹加重）。\r\n关节肌肉：关节疼痛、肿胀（多累及手、腕、膝等，对称分布，一般不遗留畸形）、肌肉酸痛。\r\n内脏受累：可能累及肾脏（蛋白尿、血尿、肾功能异常）、肺部（胸膜炎、肺炎）、心脏（心包炎）、神经系统（头痛、癫痫、认知障碍）等。', NULL, NULL);

-- ----------------------------
-- Table structure for flightlocation
-- ----------------------------
DROP TABLE IF EXISTS `flightlocation`;
CREATE TABLE `flightlocation`  (
  `locationID` int NOT NULL AUTO_INCREMENT COMMENT '地点编号',
  `locationName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '地点名称',
  `locationAddress` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '详细地址',
  `locationDescription` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '地点描述',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '状态：1-可用，0-不可用',
  PRIMARY KEY (`locationID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of flightlocation
-- ----------------------------
INSERT INTO `flightlocation` VALUES (1, '三亚凤凰机场', '海南省三亚市吉阳区凤凰路', '三亚凤凰国际机场，位于海南省三亚市，是海南省第二大机场，可进行低空飞行体验', 1);
INSERT INTO `flightlocation` VALUES (2, '上海浦东飞行体验中心', '上海市浦东新区滨海旅游度假区', '毗邻东海，依托上海航空产业资源，提供高端飞行培训与观光体验，可俯瞰浦东繁华天际线与壮阔海景', 1);
INSERT INTO `flightlocation` VALUES (3, '杭州千岛湖飞行营地', '浙江省杭州市淳安县千岛湖旅游度假区', '坐落千岛湖湖畔，湖光山色环绕，适合低空游览湖岛、开展飞行研学，感受山水与航空融合之趣', 1);
INSERT INTO `flightlocation` VALUES (4, '北京八达岭飞行基地', '北京市延庆区八达岭镇航空产业园', '紧邻八达岭长城，空域条件良好，可进行航空运动、长城低空观光，体验历史与航空科技碰撞', 1);
INSERT INTO `flightlocation` VALUES (5, '香港大屿山飞行点', '香港特别行政区大屿山离岛区', '大屿山独特地貌与海景为背景，提供高端私人飞行、航空摄影服务，感受国际都市别样航空体验', 1);
INSERT INTO `flightlocation` VALUES (6, '台湾日月潭飞行营地', '台湾省南投县鱼池乡日月潭风景区', '位于日月潭畔，湖光山色宜人，开展轻型飞机低空游览，俯瞰日月潭独特双潭景致与周边山峦', 1);
INSERT INTO `flightlocation` VALUES (7, '澳门路氹城飞行体验中心', '澳门特别行政区路氹城旅游休闲区', '结合澳门多元旅游资源，提供轻型飞机观光，俯瞰澳门城市建筑、海岸风光，感受中西文化交融视角', 1);
INSERT INTO `flightlocation` VALUES (8, '新疆伊犁那拉提飞行基地	', '新疆维吾尔自治区伊犁哈萨克自治州新源县那拉提镇', '身处那拉提空中草原腹地，空域开阔，可低空游览草原、雪山、河谷景观，体验边疆航空特色', 1);
INSERT INTO `flightlocation` VALUES (9, '武汉东湖飞行营地', '湖北省武汉市武昌区东湖生态旅游风景区', '东湖碧波环绕，利用城市湖泊与山水资源，开展低空飞行体验、航空科普，俯瞰武汉城市与湖景交融之美', 1);
INSERT INTO `flightlocation` VALUES (10, '厦门鼓浪屿飞行体验点', '福建省厦门市思明区鼓浪屿周边海域', '围绕鼓浪屿世界文化遗产地，开展水上飞机、直升机低空观光，俯瞰鼓浪屿万国建筑与厦门岛全景', 0);

-- ----------------------------
-- Table structure for flightproject
-- ----------------------------
DROP TABLE IF EXISTS `flightproject`;
CREATE TABLE `flightproject`  (
  `projectID` int NOT NULL AUTO_INCREMENT COMMENT '飞行项目编号',
  `projectName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '飞行项目名称',
  `briefIntroduction` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '飞行项目简介',
  `flightHeight` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '飞行高度',
  `flightTime` int NOT NULL COMMENT '飞行时长（分钟）',
  `price` decimal(10, 2) NOT NULL COMMENT '飞行项目价格',
  `targetCrowd` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '适合人群',
  `projectType` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '飞行项目类型',
  `projectImage` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '飞行项目图片链接或路径',
  `volume` int NOT NULL DEFAULT 10 COMMENT '可预约容量',
  PRIMARY KEY (`projectID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of flightproject
-- ----------------------------
INSERT INTO `flightproject` VALUES (1, '直升机空中游览', '乘坐直升机从空中俯瞰城市美景，体验不一样的视角。专业飞行员驾驶，确保安全舒适的飞行体验。', '300米', 15, 999.00, '年满18周岁，身体健康的成年人', '观光旅行', 'http://localhost:8085/sky-feeling/files/a2ca8601-849b-414d-8bcc-9f6cc0ed2abc.jpg', 10);
INSERT INTO `flightproject` VALUES (2, '热气球日出之旅', '乘坐热气球迎接日出，欣赏晨光中的城市全景。专业团队保障，体验浪漫而安全的空中之旅。', '200米', 45, 599.00, '年满12周岁，无恐高症人群', '观光旅行', 'http://localhost:8085/sky-feeling/files/be102351-fc86-46bd-8dce-868314e99f95.jpg', 14);
INSERT INTO `flightproject` VALUES (3, '飞行体验课程', '专业教练一对一指导，体验自由翱翔的乐趣。含理论课程和实际飞行体验。', '100米', 30, 399.00, '年满16周岁，体重不超过100公斤的人群', '飞行体验', 'http://localhost:8085/sky-feeling/files/ea442ef3-ce7c-4ece-be76-9aebc98464ab.jpg', 20);
INSERT INTO `flightproject` VALUES (4, '直升机夜景之旅', '夜幕降临后的城市灯火辉煌，乘坐直升机欣赏璀璨夜景。', '400米', 20, 1299.00, '年满18周岁，身体健康的成年人', '观光旅行', 'http://localhost:8085/sky-feeling/files/68bad1d2-3eec-4818-9b70-f6f4f94daf2b.jpg', 16);
INSERT INTO `flightproject` VALUES (5, '亲子培训学习', '专为亲子设计的低空培训学习，让孩子感受飞行的魅力。', '100米', 30, 799.00, '6岁以上儿童及其家长', '培训学习', 'http://localhost:8085/sky-feeling/files/1414b446-0782-4cd9-9657-9d67292aae69.jpg', 14);
INSERT INTO `flightproject` VALUES (6, '高级滑翔伞课程', '进阶级滑翔伞课程，包含高级技巧训练和长距离飞行体验。', '150米', 60, 899.00, '有基础滑翔伞经验的人群', '飞行体验', 'http://localhost:8085/sky-feeling/files/ee2ac0d1-ced3-4858-a3b1-2a592b620978.jpg', 14);
INSERT INTO `flightproject` VALUES (7, '直升机观光套餐', '豪华直升机观光套餐，包含空中游览和地面特色餐饮。', '350米', 25, 1599.00, '年满18周岁，身体健康的成年人', '观光旅行', 'http://localhost:8085/sky-feeling/files/ddaed52c-8f2d-48bd-a557-ac69fbb91279.jpg', 32);
INSERT INTO `flightproject` VALUES (8, '情侣热气球之旅', '浪漫的双人热气球之旅，附赠香槟和精美礼品。', '250米', 50, 999.00, '年满18周岁的情侣', '观光旅行', 'http://localhost:8085/sky-feeling/files/6b104823-9d7f-43b4-abac-04c0bae4cca7.jpg', 30);
INSERT INTO `flightproject` VALUES (9, '团队飞行体验', '适合企业团建的团队飞行体验课程，包含基础培训和实操。', '120米', 40, 599.00, '年满16周岁的团队成员', '飞行体验', 'http://localhost:8085/sky-feeling/files/cbe1aae9-7aa6-4aca-8591-60d31bb45549.jpg', 12);
INSERT INTO `flightproject` VALUES (10, 'VIP直升机包机', '私人定制的直升机包机服务，可根据需求设计飞行路线。', '500米', 30, 2999.00, 'VIP客户', '观光旅行', 'http://localhost:8085/sky-feeling/files/00ff4d30-5f54-4bfa-beb8-486c57e11669.jpg', 50);
INSERT INTO `flightproject` VALUES (12, '直升机驾驶培训', '专业的飞行员教学。', '1000米', 30, 1000.00, '18岁以上，70岁一下，身体健康，无心脏病的成年人。', '培训学习', 'http://localhost:8085/sky-feeling/files/d053f4e3-fb0b-4fe4-abff-fc386e77c4ab.jpg', 10);
INSERT INTO `flightproject` VALUES (13, '热气球黄昏', '风景很美', '300', 30, 0.00, '成年人', '观光旅行', 'http://localhost:8085/sky-feeling/files/ed190227-de38-4d6c-ba0b-ddc454f46364.jpg', 10);

-- ----------------------------
-- Table structure for flightprojectlocation
-- ----------------------------
DROP TABLE IF EXISTS `flightprojectlocation`;
CREATE TABLE `flightprojectlocation`  (
  `projectLocationID` int NOT NULL AUTO_INCREMENT COMMENT '关联编号',
  `projectID` int NOT NULL COMMENT '飞行项目编号',
  `locationID` int NOT NULL COMMENT '地点编号',
  PRIMARY KEY (`projectLocationID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 48 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of flightprojectlocation
-- ----------------------------
INSERT INTO `flightprojectlocation` VALUES (1, 10, 9);
INSERT INTO `flightprojectlocation` VALUES (2, 10, 8);
INSERT INTO `flightprojectlocation` VALUES (3, 10, 7);
INSERT INTO `flightprojectlocation` VALUES (4, 9, 4);
INSERT INTO `flightprojectlocation` VALUES (5, 9, 3);
INSERT INTO `flightprojectlocation` VALUES (6, 9, 2);
INSERT INTO `flightprojectlocation` VALUES (7, 9, 1);
INSERT INTO `flightprojectlocation` VALUES (8, 8, 5);
INSERT INTO `flightprojectlocation` VALUES (9, 8, 4);
INSERT INTO `flightprojectlocation` VALUES (10, 8, 3);
INSERT INTO `flightprojectlocation` VALUES (11, 8, 2);
INSERT INTO `flightprojectlocation` VALUES (12, 7, 9);
INSERT INTO `flightprojectlocation` VALUES (13, 7, 6);
INSERT INTO `flightprojectlocation` VALUES (14, 7, 4);
INSERT INTO `flightprojectlocation` VALUES (15, 7, 1);
INSERT INTO `flightprojectlocation` VALUES (16, 6, 8);
INSERT INTO `flightprojectlocation` VALUES (17, 6, 7);
INSERT INTO `flightprojectlocation` VALUES (18, 6, 6);
INSERT INTO `flightprojectlocation` VALUES (19, 6, 4);
INSERT INTO `flightprojectlocation` VALUES (20, 5, 8);
INSERT INTO `flightprojectlocation` VALUES (21, 5, 5);
INSERT INTO `flightprojectlocation` VALUES (22, 5, 6);
INSERT INTO `flightprojectlocation` VALUES (23, 5, 3);
INSERT INTO `flightprojectlocation` VALUES (24, 4, 7);
INSERT INTO `flightprojectlocation` VALUES (25, 4, 4);
INSERT INTO `flightprojectlocation` VALUES (26, 4, 2);
INSERT INTO `flightprojectlocation` VALUES (27, 4, 1);
INSERT INTO `flightprojectlocation` VALUES (28, 3, 7);
INSERT INTO `flightprojectlocation` VALUES (29, 3, 6);
INSERT INTO `flightprojectlocation` VALUES (30, 3, 5);
INSERT INTO `flightprojectlocation` VALUES (31, 3, 3);
INSERT INTO `flightprojectlocation` VALUES (32, 2, 9);
INSERT INTO `flightprojectlocation` VALUES (33, 2, 7);
INSERT INTO `flightprojectlocation` VALUES (34, 2, 4);
INSERT INTO `flightprojectlocation` VALUES (35, 2, 3);
INSERT INTO `flightprojectlocation` VALUES (36, 1, 7);
INSERT INTO `flightprojectlocation` VALUES (37, 1, 4);
INSERT INTO `flightprojectlocation` VALUES (38, 1, 2);
INSERT INTO `flightprojectlocation` VALUES (39, 1, 1);
INSERT INTO `flightprojectlocation` VALUES (45, 12, 9);
INSERT INTO `flightprojectlocation` VALUES (46, 12, 1);
INSERT INTO `flightprojectlocation` VALUES (47, 12, 10);

-- ----------------------------
-- Table structure for flightreservation
-- ----------------------------
DROP TABLE IF EXISTS `flightreservation`;
CREATE TABLE `flightreservation`  (
  `reservationID` int NOT NULL AUTO_INCREMENT COMMENT '预约编号',
  `projectID` int NOT NULL COMMENT '飞行项目编号',
  `userID` int NOT NULL COMMENT '预约用户编号',
  `flightAdd` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '飞行地点',
  `flightTime` datetime NOT NULL COMMENT '飞行时间（日期+时间）',
  `volume` int NOT NULL COMMENT '可预约容量',
  `headcount` int NOT NULL COMMENT '预约人数',
  `contactName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '预约人姓名',
  `contactPhone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '预约人电话号码',
  `contactID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '预约人身份证号',
  PRIMARY KEY (`reservationID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 88 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of flightreservation
-- ----------------------------
INSERT INTO `flightreservation` VALUES (29, 8, 1, '亚龙湾飞行营地', '2025-06-05 06:00:00', 25, 2, '小龙', '15877075410', '450881199510165720');
INSERT INTO `flightreservation` VALUES (30, 7, 1, '蜈支洲岛飞行点', '2025-06-04 09:00:00', 32, 2, '小李', '15877641057', '450881199610255412');
INSERT INTO `flightreservation` VALUES (31, 9, 1, '三亚凤凰机场', '2025-06-06 06:00:00', 12, 10, '小龙', '15877076410', '450881199210184102');
INSERT INTO `flightreservation` VALUES (32, 10, 1, '大东海飞行点', '2025-06-13 06:00:00', 15, 1, '小龙', '15877015410', '450881199310185014');
INSERT INTO `flightreservation` VALUES (33, 6, 1, '海棠湾飞行基地', '2025-06-11 01:00:00', 14, 1, '小龙', '15877016410', '450881199210165047');
INSERT INTO `flightreservation` VALUES (34, 6, 1, '海棠湾飞行基地', '2025-06-04 14:00:00', 14, 2, '小李', '15877641470', '450881199510185674');
INSERT INTO `flightreservation` VALUES (35, 5, 1, '西岛飞行基地', '2025-06-05 14:00:00', 14, 2, '小李', '15877045401', '450881199510145410');
INSERT INTO `flightreservation` VALUES (36, 6, 1, '海棠湾飞行基地', '2025-06-06 09:00:00', 14, 2, '小李', '15877047150', '450881199210145047');
INSERT INTO `flightreservation` VALUES (37, 7, 1, '亚龙湾飞行营地', '2025-06-10 17:00:00', 32, 2, '小玲', '15877847410', '450881199510150574');
INSERT INTO `flightreservation` VALUES (38, 10, 1, '清水湾飞行营地', '2025-06-05 14:00:00', 15, 1, 'hu', '18000000000', '330000000000000000');
INSERT INTO `flightreservation` VALUES (39, 8, 1, '西岛飞行基地', '2025-06-04 14:00:00', 25, 1, 'hu', '18042579909', '330783200509022222');
INSERT INTO `flightreservation` VALUES (40, 10, 1, '海棠湾飞行基地', '2025-06-04 09:00:00', 15, 1, 'hu', '18042579909', '330783200509022627');
INSERT INTO `flightreservation` VALUES (41, 12, 15, '三亚凤凰机场', '2025-06-12 17:00:00', 10, 1, 'hu', '18088088808', '330724200509022222');
INSERT INTO `flightreservation` VALUES (42, 4, 1, '海棠湾飞行基地', '2025-06-05 14:00:00', 16, 1, 'hu', '18088088808', '330724200509022222');
INSERT INTO `flightreservation` VALUES (43, 8, 16, '西岛飞行基地', '2025-06-11 17:00:00', 25, 1, 'hu', '18000008888', '330783200509022222');
INSERT INTO `flightreservation` VALUES (44, 12, 17, '厦门鼓浪屿飞行体验点', '2025-06-06 14:00:00', 10, 10, 'hu', '18088888888', '330783200509022222');
INSERT INTO `flightreservation` VALUES (45, 12, 17, '厦门鼓浪屿飞行体验点', '2025-06-06 14:00:00', 10, 2, 'hu', '18088888888', '330783200509022222');
INSERT INTO `flightreservation` VALUES (46, 8, 17, '上海浦东飞行体验中心', '2025-06-06 14:00:00', 25, 2, 'hu', '18088888888', '330123456789012345');
INSERT INTO `flightreservation` VALUES (47, 8, 17, '上海浦东飞行体验中心', '2025-06-06 14:00:00', 25, 2, 'hu', '18042579909', '330783122345677890');
INSERT INTO `flightreservation` VALUES (48, 8, 17, '上海浦东飞行体验中心', '2025-06-06 14:00:00', 25, 2, 'hu', '19000000000', '330783200509022677');
INSERT INTO `flightreservation` VALUES (49, 8, 17, '上海浦东飞行体验中心', '2025-06-06 14:00:00', 25, 2, 'hu', '18042579909', '330123455678900909');
INSERT INTO `flightreservation` VALUES (50, 12, 17, '厦门鼓浪屿飞行体验点', '2025-06-07 14:00:00', 10, 10, 'hu', '18088888888', '330782200509022627');
INSERT INTO `flightreservation` VALUES (51, 12, 17, '厦门鼓浪屿飞行体验点', '2025-06-07 14:00:00', 10, 2, 'hu', '18033333333', '330783577878787879');
INSERT INTO `flightreservation` VALUES (52, 12, 17, '厦门鼓浪屿飞行体验点', '2025-06-07 14:00:00', 10, 3, 'hu', '18088888888', '330783999977774444');
INSERT INTO `flightreservation` VALUES (53, 8, 17, '上海浦东飞行体验中心', '2025-06-07 14:00:00', 25, 25, 'hu', '18088888888', '330123455566667777');
INSERT INTO `flightreservation` VALUES (54, 8, 17, '上海浦东飞行体验中心', '2025-06-07 17:00:00', 25, 2, 'hu', '18088888888', '330783200509022222');
INSERT INTO `flightreservation` VALUES (55, 4, 17, '澳门路氹城飞行体验中心', '2025-06-07 17:00:00', 16, 2, 'hu', '18088088808', '330724200509022222');
INSERT INTO `flightreservation` VALUES (56, 10, 17, '澳门路氹城飞行体验中心', '2025-06-08 17:00:00', 15, 2, 'hu', '18088888888', '330123123412341234');
INSERT INTO `flightreservation` VALUES (57, 12, 17, '武汉东湖飞行营地', '2025-06-08 14:00:00', 10, 2, 'hu', '18088888888', '330123123412341234');
INSERT INTO `flightreservation` VALUES (58, 4, 17, '上海浦东飞行体验中心', '2025-06-08 09:00:00', 16, 2, 'hu', '18088088808', '330123123412341234');
INSERT INTO `flightreservation` VALUES (59, 4, 17, '上海浦东飞行体验中心', '2025-06-08 14:00:00', 16, 2, 'hu', '18088888888', '330123123412341234');
INSERT INTO `flightreservation` VALUES (60, 4, 17, '上海浦东飞行体验中心', '2025-06-08 17:00:00', 16, 2, 'hu', '18088888888', '330123123412341234');
INSERT INTO `flightreservation` VALUES (61, 8, 17, '上海浦东飞行体验中心', '2025-06-08 17:00:00', 25, 2, 'hu', '18088888888', '330123123412341234');
INSERT INTO `flightreservation` VALUES (62, 8, 17, '上海浦东飞行体验中心', '2025-06-08 17:00:00', 25, 2, 'hu', '18088088808', '330783123412341234');
INSERT INTO `flightreservation` VALUES (63, 8, 17, '上海浦东飞行体验中心', '2025-06-08 17:00:00', 25, 2, 'hu', '18088888888', '330123123412341234');
INSERT INTO `flightreservation` VALUES (64, 10, 17, '澳门路氹城飞行体验中心', '2025-06-08 09:00:00', 15, 10, 'hu', '18088088808', '330123123412341234');
INSERT INTO `flightreservation` VALUES (65, 10, 17, '澳门路氹城飞行体验中心', '2025-06-08 09:00:00', 50, 40, 'hu', '18088888888', '330123123412341234');
INSERT INTO `flightreservation` VALUES (66, 8, 17, '上海浦东飞行体验中心', '2025-06-09 09:00:00', 30, 2, 'hu', '18088088808', '330123123412341234');
INSERT INTO `flightreservation` VALUES (67, 12, 17, '三亚凤凰机场', '2025-06-09 14:00:00', 10, 1, 'hu', '18088888888', '330123123412341234');
INSERT INTO `flightreservation` VALUES (68, 12, 17, '三亚凤凰机场', '2025-06-09 14:00:00', 10, 1, 'hu', '18088888888', '330123123412341234');
INSERT INTO `flightreservation` VALUES (69, 12, 17, '厦门鼓浪屿飞行体验点', '2025-06-09 14:00:00', 10, 1, 'hu', '18088888888', '330123123412341234');
INSERT INTO `flightreservation` VALUES (70, 4, 17, '澳门路氹城飞行体验中心', '2025-06-09 14:00:00', 16, 1, 'hu', '18042579909', '330123123412341234');
INSERT INTO `flightreservation` VALUES (71, 12, 17, '三亚凤凰机场', '2025-06-09 14:00:00', 10, 1, 'hu', '18012341234', '330123123412341234');
INSERT INTO `flightreservation` VALUES (72, 12, 17, '武汉东湖飞行营地', '2025-06-09 14:00:00', 10, 1, 'hu', '18012341234', '330123123412341234');
INSERT INTO `flightreservation` VALUES (73, 12, 17, '厦门鼓浪屿飞行体验点', '2025-06-09 14:00:00', 10, 1, 'hu', '13812341234', '330123123412341234');
INSERT INTO `flightreservation` VALUES (74, 12, 17, '武汉东湖飞行营地', '2025-06-09 14:00:00', 10, 1, 'hu', '18012341234', '330123123412341234');
INSERT INTO `flightreservation` VALUES (75, 12, 17, '武汉东湖飞行营地', '2025-06-09 09:00:00', 10, 1, 'hu', '18088888888', '330123123412341234');
INSERT INTO `flightreservation` VALUES (76, 12, 17, '武汉东湖飞行营地', '2025-06-09 14:00:00', 10, 1, 'hu', '18088888888', '330123123412341234');
INSERT INTO `flightreservation` VALUES (77, 12, 17, '武汉东湖飞行营地', '2025-06-09 14:00:00', 10, 1, 'hu', '18012341234', '330123123412341234');
INSERT INTO `flightreservation` VALUES (78, 12, 17, '厦门鼓浪屿飞行体验点', '2025-06-08 17:00:00', 10, 1, 'hu', '18088888888', '330123123412341234');
INSERT INTO `flightreservation` VALUES (79, 12, 17, '厦门鼓浪屿飞行体验点', '2025-06-09 14:00:00', 10, 1, 'hu', '18088888888', '330123123412341234');
INSERT INTO `flightreservation` VALUES (80, 12, 17, '厦门鼓浪屿飞行体验点', '2025-06-09 14:00:00', 10, 1, 'hu', '18088888888', '330123123412341234');
INSERT INTO `flightreservation` VALUES (81, 12, 18, '厦门鼓浪屿飞行体验点', '2025-06-09 14:00:00', 10, 1, 'hu', '18088088808', '330123123412341234');
INSERT INTO `flightreservation` VALUES (82, 4, 17, '三亚凤凰机场', '2025-06-09 14:00:00', 16, 1, 'hu', '18088888888', '330123123412341234');
INSERT INTO `flightreservation` VALUES (83, 6, 17, '新疆伊犁那拉提飞行基地	', '2025-06-09 14:00:00', 14, 3, 'hu', '18088888888', '330123123412341234');
INSERT INTO `flightreservation` VALUES (84, 10, 17, '武汉东湖飞行营地', '2025-06-09 09:00:00', 50, 50, 'hu', '18088888888', '330123123412341234');
INSERT INTO `flightreservation` VALUES (85, 12, 15, '武汉东湖飞行营地', '2025-07-06 14:00:00', 10, 1, 'hu', '18088088808', '330783200509022728');
INSERT INTO `flightreservation` VALUES (86, 10, 15, '武汉东湖飞行营地', '2025-07-17 14:00:00', 50, 1, 'hkx', '18099099909', '330123200509021234');
INSERT INTO `flightreservation` VALUES (87, 8, 15, '杭州千岛湖飞行营地', '2025-07-17 14:00:00', 30, 1, 'hh', '18088088808', '123456123412341234');

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `orderID` int NOT NULL AUTO_INCREMENT COMMENT '订单编号',
  `userID` int NOT NULL COMMENT '下单用户编号',
  `reservationID` int NOT NULL COMMENT '预约编号',
  `headcount` int NOT NULL COMMENT '预约总人数',
  `orderAmount` decimal(10, 2) NOT NULL COMMENT '订单金额',
  `status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单状态（已预约、待支付、已支付、已完成、已取消）',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `expiration_time` datetime NULL DEFAULT NULL COMMENT '支付过期时间',
  PRIMARY KEY (`orderID`) USING BTREE,
  INDEX `idx_userID`(`userID` ASC) USING BTREE,
  INDEX `idx_reservationID`(`reservationID` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 65 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES (6, 1, 29, 2, 1998.00, '已完成', '2025-06-03 06:32:48', '2025-06-03 06:47:48');
INSERT INTO `order` VALUES (7, 1, 30, 2, 3198.00, '已完成', '2025-06-03 06:32:48', '2025-06-03 06:47:48');
INSERT INTO `order` VALUES (8, 1, 31, 10, 5990.00, '已完成', '2025-06-08 17:00:00', '2025-06-03 06:47:48');
INSERT INTO `order` VALUES (9, 1, 32, 1, 2999.00, '已失效', '2025-06-03 06:32:48', '2025-06-03 06:47:48');
INSERT INTO `order` VALUES (10, 1, 33, 1, 899.00, '已失效', '2025-06-03 06:32:48', '2025-06-03 06:47:48');
INSERT INTO `order` VALUES (11, 1, 34, 2, 1798.00, '已失效', '2025-06-03 06:32:48', '2025-06-03 06:47:48');
INSERT INTO `order` VALUES (12, 1, 35, 2, 1598.00, '已失效', '2025-06-03 06:32:48', '2025-06-03 06:47:48');
INSERT INTO `order` VALUES (13, 1, 36, 2, 1798.00, '已失效', '2025-06-03 06:32:48', '2025-06-03 06:47:48');
INSERT INTO `order` VALUES (14, 1, 37, 2, 3198.00, '已完成', '2025-06-03 06:32:48', '2025-06-03 06:47:48');
INSERT INTO `order` VALUES (15, 1, 38, 1, 2999.00, '已完成', '2025-06-08 17:00:00', '2025-06-04 13:07:18');
INSERT INTO `order` VALUES (16, 1, 39, 1, 999.00, '已完成', '2025-06-04 13:59:50', '2025-06-04 14:14:50');
INSERT INTO `order` VALUES (17, 1, 40, 1, 2999.00, '已失效', '2025-06-04 14:16:39', '2025-06-04 14:31:39');
INSERT INTO `order` VALUES (18, 15, 41, 1, 1000.00, '已失效', '2025-06-04 14:28:08', '2025-06-04 14:43:08');
INSERT INTO `order` VALUES (19, 1, 42, 1, 1299.00, '已取消', '2025-06-04 15:54:37', '2025-06-04 16:09:37');
INSERT INTO `order` VALUES (20, 16, 43, 1, 999.00, '已完成', '2025-06-04 22:31:03', '2025-06-04 22:46:03');
INSERT INTO `order` VALUES (21, 17, 44, 10, 10000.00, '已完成', '2025-06-05 21:39:57', '2025-06-05 21:54:57');
INSERT INTO `order` VALUES (22, 17, 45, 2, 2000.00, '已失效', '2025-06-05 21:41:29', '2025-06-05 21:56:29');
INSERT INTO `order` VALUES (23, 17, 46, 2, 1998.00, '已失效', '2025-06-05 23:01:51', '2025-06-05 23:16:51');
INSERT INTO `order` VALUES (24, 17, 47, 2, 1998.00, '已完成', '2025-06-05 23:04:31', '2025-06-05 23:19:31');
INSERT INTO `order` VALUES (25, 17, 48, 2, 1998.00, '已失效', '2025-06-05 23:10:20', '2025-06-05 23:25:20');
INSERT INTO `order` VALUES (26, 17, 49, 2, 1998.00, '已失效', '2025-06-05 23:35:53', '2025-06-05 23:50:53');
INSERT INTO `order` VALUES (27, 17, 50, 10, 10000.00, '已取消', '2025-06-06 00:03:20', '2025-06-06 00:18:20');
INSERT INTO `order` VALUES (28, 17, 51, 2, 2000.00, '已取消', '2025-06-06 00:16:50', '2025-06-06 00:31:50');
INSERT INTO `order` VALUES (29, 17, 52, 3, 3000.00, '已失效', '2025-06-06 00:43:41', '2025-06-06 00:58:41');
INSERT INTO `order` VALUES (30, 17, 53, 25, 24975.00, '已失效', '2025-06-06 00:45:30', '2025-06-06 01:00:30');
INSERT INTO `order` VALUES (31, 17, 54, 2, 1998.00, '已失效', '2025-06-06 19:17:36', '2025-06-06 19:32:36');
INSERT INTO `order` VALUES (32, 17, 55, 2, 2598.00, '已失效', '2025-06-06 21:17:36', '2025-06-06 21:32:36');
INSERT INTO `order` VALUES (33, 17, 56, 2, 5998.00, '已失效', '2025-06-07 11:06:57', '2025-06-07 11:21:57');
INSERT INTO `order` VALUES (34, 17, 57, 2, 2000.00, '已失效', '2025-06-07 11:28:10', NULL);
INSERT INTO `order` VALUES (35, 17, 58, 2, 2598.00, '已失效', '2025-06-07 19:50:07', NULL);
INSERT INTO `order` VALUES (36, 17, 59, 2, 2598.00, '已失效', '2025-06-07 19:51:27', NULL);
INSERT INTO `order` VALUES (37, 17, 60, 2, 2598.00, '已失效', '2025-06-07 19:52:00', NULL);
INSERT INTO `order` VALUES (38, 17, 61, 2, 1998.00, '已失效', '2025-06-07 22:31:08', NULL);
INSERT INTO `order` VALUES (39, 17, 62, 2, 1998.00, '已失效', '2025-06-07 22:50:31', NULL);
INSERT INTO `order` VALUES (40, 17, 63, 2, 1998.00, '已完成', '2025-06-07 22:51:04', NULL);
INSERT INTO `order` VALUES (41, 17, 64, 10, 29990.00, '已失效', '2025-06-07 23:01:42', NULL);
INSERT INTO `order` VALUES (42, 17, 65, 40, 119960.00, '已失效', '2025-06-07 23:20:12', NULL);
INSERT INTO `order` VALUES (43, 17, 66, 2, 1998.00, '已失效', '2025-06-08 00:23:41', NULL);
INSERT INTO `order` VALUES (44, 17, 67, 1, 1000.00, '已完成', '2025-07-05 20:56:42', NULL);
INSERT INTO `order` VALUES (45, 17, 68, 1, 1000.00, '已失效', '2025-06-08 01:32:20', NULL);
INSERT INTO `order` VALUES (48, 17, 71, 1, 1000.00, '已失效', '2025-06-08 02:18:25', NULL);
INSERT INTO `order` VALUES (49, 17, 72, 1, 1000.00, '已失效', '2025-06-08 03:37:37', NULL);
INSERT INTO `order` VALUES (50, 17, 73, 1, 1000.00, '已失效', '2025-06-08 04:15:15', NULL);
INSERT INTO `order` VALUES (51, 17, 74, 1, 1000.00, '已失效', '2025-06-08 09:58:12', NULL);
INSERT INTO `order` VALUES (52, 17, 75, 1, 1000.00, '已失效', '2025-06-08 11:34:24', NULL);
INSERT INTO `order` VALUES (53, 17, 76, 1, 1000.00, '已失效', '2025-06-08 12:25:04', NULL);
INSERT INTO `order` VALUES (54, 17, 77, 1, 1000.00, '已完成', '2025-07-05 20:56:42', NULL);
INSERT INTO `order` VALUES (55, 17, 78, 1, 1000.00, '已完成', '2025-06-08 17:00:00', NULL);
INSERT INTO `order` VALUES (58, 18, 81, 1, 1000.00, '已完成', '2025-07-05 20:56:42', NULL);
INSERT INTO `order` VALUES (60, 17, 83, 3, 2697.00, '已完成', '2025-07-05 20:56:42', NULL);
INSERT INTO `order` VALUES (61, 17, 84, 50, 149950.00, '已失效', '2025-06-08 20:47:44', NULL);
INSERT INTO `order` VALUES (62, 15, 85, 1, 1000.00, '已完成', '2025-07-06 16:52:59', NULL);
INSERT INTO `order` VALUES (63, 15, 86, 1, 2999.00, '已完成', '2025-07-17 14:00:00', NULL);
INSERT INTO `order` VALUES (64, 15, 87, 1, 999.00, '已失效', '2025-07-16 21:52:01', NULL);

-- ----------------------------
-- Table structure for payment
-- ----------------------------
DROP TABLE IF EXISTS `payment`;
CREATE TABLE `payment`  (
  `paymentID` int NOT NULL AUTO_INCREMENT COMMENT '支付编号',
  `userID` int NOT NULL COMMENT '支付用户编号',
  `orderID` int NOT NULL COMMENT '订单编号',
  `paymentAmount` decimal(10, 2) NOT NULL COMMENT '支付金额',
  `PaymentMethod` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '支付方式',
  `PaymentTime` datetime NOT NULL COMMENT '支付时间',
  `PaymentStatus` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '支付状态（已支付、待支付）',
  PRIMARY KEY (`paymentID`) USING BTREE,
  INDEX `idx_orderID`(`orderID` ASC) USING BTREE,
  INDEX `idx_userID`(`userID` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '支付表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of payment
-- ----------------------------
INSERT INTO `payment` VALUES (5, 1, 6, 1998.00, '微信支付', '2025-06-03 05:22:27', '已支付');
INSERT INTO `payment` VALUES (6, 1, 7, 3198.00, '微信支付', '2025-06-03 05:22:34', '已支付');
INSERT INTO `payment` VALUES (7, 1, 8, 5990.00, '微信支付', '2025-06-03 05:25:39', '已支付');
INSERT INTO `payment` VALUES (8, 1, 14, 3198.00, '微信支付', '2025-06-03 07:00:04', '已支付');
INSERT INTO `payment` VALUES (9, 1, 15, 2999.00, '微信支付', '2025-06-04 12:54:48', '已支付');
INSERT INTO `payment` VALUES (10, 1, 16, 999.00, '微信支付', '2025-06-04 14:01:00', '已支付');
INSERT INTO `payment` VALUES (11, 16, 20, 999.00, '微信支付', '2025-06-04 22:32:16', '已支付');
INSERT INTO `payment` VALUES (12, 17, 21, 10000.00, '微信支付', '2025-06-05 21:40:29', '已支付');
INSERT INTO `payment` VALUES (13, 17, 24, 1998.00, '微信支付', '2025-06-05 23:09:38', '已支付');
INSERT INTO `payment` VALUES (14, 17, 40, 1998.00, '微信支付', '2025-06-07 22:51:50', '已支付');
INSERT INTO `payment` VALUES (15, 17, 44, 1000.00, '微信支付', '2025-06-08 01:11:28', '已支付');
INSERT INTO `payment` VALUES (16, 17, 46, 1000.00, '支付宝', '2025-06-08 01:59:41', '已支付');
INSERT INTO `payment` VALUES (17, 17, 54, 1000.00, '支付宝', '2025-06-08 12:38:26', '已支付');
INSERT INTO `payment` VALUES (18, 17, 55, 1000.00, '支付宝', '2025-06-08 12:41:30', '已支付');
INSERT INTO `payment` VALUES (19, 18, 58, 1000.00, '支付宝', '2025-06-08 14:18:25', '已支付');
INSERT INTO `payment` VALUES (20, 17, 60, 2697.00, '银行卡', '2025-06-08 20:35:20', '已支付');
INSERT INTO `payment` VALUES (21, 15, 62, 1000.00, '支付宝', '2025-07-05 14:44:51', '已支付');
INSERT INTO `payment` VALUES (22, 15, 63, 2999.00, '支付宝', '2025-07-16 21:31:34', '已支付');

-- ----------------------------
-- Table structure for prediction
-- ----------------------------
DROP TABLE IF EXISTS `prediction`;
CREATE TABLE `prediction`  (
  `predictionID` int NOT NULL AUTO_INCREMENT COMMENT '预测编号',
  `userID` int NOT NULL COMMENT '用户ID',
  `predictionTime` datetime NULL DEFAULT NULL COMMENT '预测时间',
  `age` int NOT NULL COMMENT '年龄',
  `sex` int NOT NULL COMMENT '性别，0男，1女',
  `symptomsID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '症状的id，用“，”分隔',
  `diseasesID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '预测可能疾病的结果ID',
  `symptoms` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '症状的中文描述',
  PRIMARY KEY (`predictionID`, `userID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 62 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of prediction
-- ----------------------------
INSERT INTO `prediction` VALUES (1, 1, '2025-07-23 18:22:18', 19, 1, '1,5', '5,3', '关节疼痛,面部红斑');
INSERT INTO `prediction` VALUES (2, 15, '2025-07-23 18:36:14', 19, 1, '1,7', '5,7', '关节疼痛,脱发');
INSERT INTO `prediction` VALUES (3, 15, '2025-07-23 19:00:48', 19, 1, '4', '6', '多关节疼痛');
INSERT INTO `prediction` VALUES (4, 15, '2025-07-23 19:02:50', 19, 1, '4', '6', '多关节疼痛');
INSERT INTO `prediction` VALUES (5, 15, '2025-07-23 19:05:18', 19, 1, '2,4,5', '7,3', '关节僵硬/肿胀,多关节疼痛,面部红斑');
INSERT INTO `prediction` VALUES (6, 15, '2025-07-23 19:05:32', 19, 1, '1,3,5,7', '6,3', '关节疼痛,晨僵,面部红斑,脱发');
INSERT INTO `prediction` VALUES (7, 15, '2025-07-30 19:39:49', 19, 1, '2,3,4,5,6,7', '7,4', '关节僵硬/肿胀,晨僵,多关节疼痛,面部红斑,口腔溃疡,脱发');
INSERT INTO `prediction` VALUES (8, 15, '2025-07-31 17:47:32', 19, 1, '1,2,3,4,7', '8,1', '关节疼痛,关节僵硬/肿胀,晨僵,多关节疼痛,脱发');
INSERT INTO `prediction` VALUES (12, 15, '2025-08-01 00:30:39', 19, 1, '1,2,4', '7', '关节疼痛,关节僵硬/肿胀,多关节疼痛');
INSERT INTO `prediction` VALUES (13, 15, '2025-08-01 00:39:58', 19, 1, '1,2,4', '7', '关节疼痛,关节僵硬/肿胀,多关节疼痛');
INSERT INTO `prediction` VALUES (14, 15, '2025-08-01 01:00:30', 19, 1, '1,2,4', '7', '关节疼痛,关节僵硬/肿胀,多关节疼痛');
INSERT INTO `prediction` VALUES (19, 15, '2025-08-01 16:49:56', 19, 1, '1,2,4', '7', '关节疼痛,关节僵硬/肿胀,多关节疼痛');
INSERT INTO `prediction` VALUES (21, 15, '2025-08-01 17:07:25', 19, 1, '4,5', '6,3', '多关节疼痛,面部红斑');
INSERT INTO `prediction` VALUES (23, 15, '2025-08-01 17:15:19', 19, 1, '1,2,4,5', '3,7', '关节疼痛,关节僵硬/肿胀,多关节疼痛,面部红斑');
INSERT INTO `prediction` VALUES (24, 15, '2025-08-01 17:29:29', 19, 1, '1', '5', '关节疼痛');
INSERT INTO `prediction` VALUES (25, 15, '2025-08-01 17:39:07', 19, 1, '1,2,4,5', '3,7', '关节疼痛,关节僵硬/肿胀,多关节疼痛,面部红斑');
INSERT INTO `prediction` VALUES (26, 15, '2025-08-01 17:57:09', 19, 1, '1,2,4', '7', '关节疼痛,关节僵硬/肿胀,多关节疼痛');
INSERT INTO `prediction` VALUES (27, 15, '2025-08-01 18:07:58', 19, 1, '1,2,4', '7', '关节疼痛,关节僵硬/肿胀,多关节疼痛');
INSERT INTO `prediction` VALUES (28, 15, '2025-08-01 18:08:32', 19, 1, '1,2,4', '7', '关节疼痛,关节僵硬/肿胀,多关节疼痛');
INSERT INTO `prediction` VALUES (29, 15, '2025-08-01 18:08:50', 19, 1, '1,2,4,5', '3,7', '关节疼痛,关节僵硬/肿胀,多关节疼痛,面部红斑');
INSERT INTO `prediction` VALUES (30, 15, '2025-08-01 20:01:32', 19, 1, '1,2,4,5', '3,7', '关节疼痛,关节僵硬/肿胀,多关节疼痛,面部红斑');
INSERT INTO `prediction` VALUES (31, 15, '2025-08-01 20:02:30', 19, 1, '1,2,4,5', '3,7', '关节疼痛,关节僵硬/肿胀,多关节疼痛,面部红斑');
INSERT INTO `prediction` VALUES (32, 15, '2025-08-01 20:13:55', 19, 1, '1,2,4,5', '3,7', '关节疼痛,关节僵硬/肿胀,多关节疼痛,面部红斑');
INSERT INTO `prediction` VALUES (33, 15, '2025-08-02 17:12:52', 19, 1, '1,2,4,5', '3,7', '关节疼痛,关节僵硬/肿胀,多关节疼痛,面部红斑');
INSERT INTO `prediction` VALUES (34, 15, '2025-08-02 17:14:28', 19, 1, '1', '5', '关节疼痛');
INSERT INTO `prediction` VALUES (35, 15, '2025-08-02 17:17:21', 56, 1, '1,2,4,10', '7', '关节疼痛,关节僵硬/肿胀,多关节疼痛,多尿');
INSERT INTO `prediction` VALUES (36, 15, '2025-08-02 17:22:50', 56, 1, '1,2,4,5', '3,7', '关节疼痛,关节僵硬/肿胀,多关节疼痛,面部红斑');
INSERT INTO `prediction` VALUES (37, 15, '2025-08-02 17:24:48', 56, 1, '1,2,4,5,16', '3,7', '关节疼痛,关节僵硬/肿胀,多关节疼痛,面部红斑,鳞屑性红斑 / 丘疹 / 斑块');
INSERT INTO `prediction` VALUES (38, 15, '2025-08-02 17:28:08', 56, 1, '1,2,5,16', '3,6', '关节疼痛,关节僵硬/肿胀,面部红斑,鳞屑性红斑 / 丘疹 / 斑块');
INSERT INTO `prediction` VALUES (39, 15, '2025-09-01 14:50:49', 19, 1, '5', '3', '面部红斑');
INSERT INTO `prediction` VALUES (40, 15, '2025-09-01 15:27:08', 19, 1, '17', '13', '点状出血');
INSERT INTO `prediction` VALUES (41, 15, '2025-09-01 15:44:20', 19, 1, '5', '3', '面部红斑');
INSERT INTO `prediction` VALUES (42, 15, '2025-09-01 15:45:55', 19, 1, '5', '3', '面部红斑');
INSERT INTO `prediction` VALUES (43, 15, '2025-09-01 16:00:25', 19, 1, '5,13', '3,9', '面部红斑,多食');
INSERT INTO `prediction` VALUES (44, 15, '2025-09-01 16:00:50', 19, 1, '4,2,16', '7,13', '多关节疼痛,关节僵硬/肿胀,束状发');
INSERT INTO `prediction` VALUES (45, 15, '2025-09-01 16:04:23', 19, 1, '1,2', '6', '关节疼痛,关节僵硬/肿胀');
INSERT INTO `prediction` VALUES (46, 15, '2025-09-01 16:34:55', 19, 1, '1,4,5', '3,6', '关节疼痛,多关节疼痛,面部红斑');
INSERT INTO `prediction` VALUES (47, 15, '2025-09-05 16:02:23', 56, 0, '4,3', '7', '多关节疼痛,晨僵');
INSERT INTO `prediction` VALUES (48, 15, '2025-09-05 16:02:37', 56, 0, '4,3,15,14', '7,16', '多关节疼痛,晨僵,瘙痒,鳞屑性红斑 / 丘疹 / 斑块');
INSERT INTO `prediction` VALUES (49, 15, '2025-10-05 16:50:35', 20, 1, '1,7', '1,5', '关节疼痛,脱发');
INSERT INTO `prediction` VALUES (50, 15, '2025-10-05 17:14:24', 56, 1, '1,2,4', '7', '关节疼痛,关节僵硬/肿胀,多关节疼痛');
INSERT INTO `prediction` VALUES (51, 15, '2025-10-05 17:17:10', 56, 1, '1,2,4', '7', '关节疼痛,关节僵硬/肿胀,多关节疼痛');
INSERT INTO `prediction` VALUES (52, 15, '2025-10-05 17:24:48', 56, 1, '1,2,4,3', '8', '关节疼痛,关节僵硬/肿胀,多关节疼痛,晨僵');
INSERT INTO `prediction` VALUES (53, 15, '2025-10-05 17:27:01', 56, 1, '1', '5', '关节疼痛');
INSERT INTO `prediction` VALUES (54, 15, '2025-10-05 17:30:33', 56, 1, '1', '5', '关节疼痛');
INSERT INTO `prediction` VALUES (55, 15, '2025-10-05 17:39:59', 59, 1, '5,3', '3,6', '面部红斑,晨僵');
INSERT INTO `prediction` VALUES (56, 15, '2025-10-05 17:43:39', 59, 1, '5', '3', '面部红斑');
INSERT INTO `prediction` VALUES (57, 15, '2025-10-05 17:45:31', 59, 1, '5', '3', '面部红斑');
INSERT INTO `prediction` VALUES (58, 15, '2025-10-05 17:46:36', 59, 1, '1,2,4', '7', '关节疼痛,关节僵硬/肿胀,多关节疼痛');
INSERT INTO `prediction` VALUES (59, 15, '2025-10-05 17:47:29', 65, 1, '1,2,4,3', '8', '关节疼痛,关节僵硬/肿胀,多关节疼痛,晨僵');
INSERT INTO `prediction` VALUES (60, 15, '2025-10-05 17:52:26', 67, 1, '1,2,4,3', '8', '关节疼痛,关节僵硬/肿胀,多关节疼痛,晨僵');
INSERT INTO `prediction` VALUES (61, 15, '2025-10-05 17:55:22', 68, 1, '1,2,4,3', '8', '关节疼痛,关节僵硬/肿胀,多关节疼痛,晨僵');

-- ----------------------------
-- Table structure for symptoms
-- ----------------------------
DROP TABLE IF EXISTS `symptoms`;
CREATE TABLE `symptoms`  (
  `symptomID` int NOT NULL AUTO_INCREMENT COMMENT '症状编号',
  `symptomName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '症状名称',
  `affectedID` int NULL DEFAULT NULL COMMENT '症状影响的部位对应的编号',
  `disease` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '症状所对应的病',
  PRIMARY KEY (`symptomID`) USING BTREE,
  INDEX `affectedID`(`affectedID` ASC) USING BTREE,
  CONSTRAINT `affectedID` FOREIGN KEY (`affectedID`) REFERENCES `affected` (`affectedID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of symptoms
-- ----------------------------
INSERT INTO `symptoms` VALUES (1, '关节疼痛', NULL, '类风湿性关节炎');
INSERT INTO `symptoms` VALUES (2, '关节僵硬/肿胀', NULL, '类风湿性关节炎');
INSERT INTO `symptoms` VALUES (3, '晨僵', NULL, '类风湿性关节炎');
INSERT INTO `symptoms` VALUES (4, '多关节疼痛', NULL, '类风湿性关节炎');
INSERT INTO `symptoms` VALUES (5, '面部红斑', NULL, '系统性红斑狼疮');
INSERT INTO `symptoms` VALUES (6, '口腔溃疡', NULL, '系统性红斑狼疮');
INSERT INTO `symptoms` VALUES (7, '脱发', NULL, '系统性红斑狼疮');
INSERT INTO `symptoms` VALUES (8, '发热', NULL, '系统性红斑狼疮');
INSERT INTO `symptoms` VALUES (9, '口干多饮、多尿', NULL, '1型糖尿病');
INSERT INTO `symptoms` VALUES (10, '消瘦', NULL, '1型糖尿病');
INSERT INTO `symptoms` VALUES (11, '乏力', NULL, '1型糖尿病');
INSERT INTO `symptoms` VALUES (12, '泡沫尿', NULL, '1型糖尿病');
INSERT INTO `symptoms` VALUES (13, '多食', NULL, '1型糖尿病');
INSERT INTO `symptoms` VALUES (14, '鳞屑性红斑 / 丘疹 / 斑块', NULL, '银屑病');
INSERT INTO `symptoms` VALUES (15, '瘙痒', NULL, '银屑病');
INSERT INTO `symptoms` VALUES (16, '束状发', NULL, '银屑病');
INSERT INTO `symptoms` VALUES (17, '点状出血', NULL, '银屑病');

-- ----------------------------
-- Table structure for token_blacklist
-- ----------------------------
DROP TABLE IF EXISTS `token_blacklist`;
CREATE TABLE `token_blacklist`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'JWT token',
  `admin_id` int NOT NULL COMMENT '管理员ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `expire_time` datetime NOT NULL COMMENT '过期时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_token`(`token` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Token黑名单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of token_blacklist
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `userID` int NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `userName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户昵称',
  `phoneNumber` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户电话号码',
  `password` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户密码',
  PRIMARY KEY (`userID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (15, 'user', '18088088808', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO `user` VALUES (16, 'userNing', '18000008888', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO `user` VALUES (17, 'uesrHu', '18088888888', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO `user` VALUES (18, 'user03', '16812341234', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO `user` VALUES (19, 'user007', '16888088808', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO `user` VALUES (20, 'huhu', '18088089909', 'e10adc3949ba59abbe56e057f20f883e');

SET FOREIGN_KEY_CHECKS = 1;
