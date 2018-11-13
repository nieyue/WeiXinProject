/*
Navicat MySQL Data Transfer

Source Server         : centos_10m-2
Source Server Version : 50636
Source Host           : 118.190.133.146:3306
Source Database       : wei_xin_project_db

Target Server Type    : MYSQL
Target Server Version : 50636
File Encoding         : 65001

Date: 2018-11-13 16:28:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `account_tb`
-- ----------------------------
DROP TABLE IF EXISTS `account_tb`;
CREATE TABLE `account_tb` (
  `account_id` bigint(20) NOT NULL COMMENT '账户id',
  `phone` varchar(255) DEFAULT NULL COMMENT '注册手机号',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
  `icon` varchar(255) DEFAULT NULL COMMENT '图像',
  `sex` tinyint(4) DEFAULT '0' COMMENT '性别,默认为0未知，为1男性，为2女性',
  `country` varchar(255) DEFAULT NULL COMMENT '国家,默认中国',
  `realname` varchar(255) DEFAULT NULL COMMENT '真实姓名',
  `email` varchar(255) DEFAULT NULL COMMENT 'email',
  `safety_grade` tinyint(4) DEFAULT NULL COMMENT '安全等级，1低，2中，3高',
  `auth` tinyint(4) DEFAULT NULL COMMENT '认证，0没认证，1审核中，2已认证',
  `identity_cards` varchar(255) DEFAULT NULL COMMENT '身份证',
  `identity_cards_hold_img` varchar(255) DEFAULT NULL COMMENT '手持身份证上半身照',
  `identity_cards_front_img` varchar(255) DEFAULT NULL COMMENT '身份证正面',
  `identity_cards_back_img` varchar(255) DEFAULT NULL COMMENT '身份证反面',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `login_date` datetime DEFAULT NULL COMMENT '登陆时间',
  `status` tinyint(4) DEFAULT '0' COMMENT '状态，默认0正常，1锁定，2，异常',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色id外键',
  PRIMARY KEY (`account_id`),
  KEY `INDEX_AUTH` (`auth`) USING BTREE,
  KEY `INDEX_PHONE` (`phone`) USING BTREE,
  KEY `INDEX_REALNAME` (`realname`) USING BTREE,
  KEY `INDEX_CREATEDATE` (`create_date`) USING BTREE,
  KEY `INDEX_LOGINDATE` (`login_date`) USING BTREE,
  KEY `INDEX_ROLEID` (`role_id`) USING BTREE,
  KEY `INDEX_STATUS` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账户表';

-- ----------------------------
-- Records of account_tb
-- ----------------------------
INSERT INTO `account_tb` VALUES ('1000', '15111336587', '11874bb6149dd45428da628c9766b252', '聂跃', null, '0', null, null, '278076304@qq.com', null, null, null, null, null, null, '2018-06-06 22:33:32', '2018-11-13 16:15:40', '0', '1000');
INSERT INTO `account_tb` VALUES ('1005644711717662722', '15111336588', '11874bb6149dd45428da628c9766b252', 'dd', null, '0', '中国', null, null, null, '0', null, null, null, null, '2018-11-13 14:22:06', '2018-06-18 20:53:05', '0', '1001');
INSERT INTO `account_tb` VALUES ('1007072886735667201', '18684549007', '11874bb6149dd45428da628c9766b252', '颜宇', null, '2', null, null, null, '1', '2', null, null, null, null, '2018-11-13 14:22:08', '2018-11-13 14:22:13', '0', '1001');
INSERT INTO `account_tb` VALUES ('1007073104239689730', '13755031427', '11874bb6149dd45428da628c9766b252', '卢颖', null, '2', null, null, null, '1', '2', null, null, null, null, '2018-11-13 14:22:11', '2018-11-13 16:16:48', '0', '1001');

-- ----------------------------
-- Table structure for `kf_article_tb`
-- ----------------------------
DROP TABLE IF EXISTS `kf_article_tb`;
CREATE TABLE `kf_article_tb` (
  `kf_article_id` bigint(20) NOT NULL COMMENT '客服消息文章id',
  `title` varchar(255) DEFAULT NULL COMMENT '图文消息的标题',
  `description` varchar(255) DEFAULT NULL COMMENT '图文消息的描述',
  `url` varchar(255) DEFAULT NULL COMMENT '图文消息被点击后跳转的链接',
  `picurl` varchar(255) DEFAULT NULL COMMENT '图文消息的图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `kf_message_id` bigint(20) DEFAULT NULL COMMENT '客服消息id外键',
  PRIMARY KEY (`kf_article_id`),
  KEY `INDEX_KFMESSAGEID` (`kf_message_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客服消息文章表';

-- ----------------------------
-- Records of kf_article_tb
-- ----------------------------
INSERT INTO `kf_article_tb` VALUES ('1005826597500907521', '《太古神王》再曝预告片暗藏玄机 盛一伦眼神凌厉', '网易娱乐6月11日报道近日，由著名导演胡储玺、陈伟祥执导，盛一伦、王子文、向佐、汤晶媚、陈孟奇等主演的东方传奇巨制《太古神王》曝光全新预告片“背后藏刀”，随着主要人物的逐一亮相，进一步揭秘剧中扑朔迷离的矛盾关系，引燃观众对正片的期待。', 'https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_9348486676047684888%22%7D&n_type=0&p_from=1', 'https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2027487592,2581064668&fm=173&app=25&f=JPEG?w=218&h=146&s=80FA7C851F130AC0468C64B203008082', '2018-06-10 22:58:43', '2018-06-11 16:42:15', '1005818121710104577');
INSERT INTO `kf_article_tb` VALUES ('1005826857300291586', '是疏忽？还是命数？一次坠床，孩子走了……', '每个宝宝生来便是父母的天使，从降临这个世界的那一刻起，他们便被爱的海洋包围。成长是美好的，而危机同样无处不在。对宝宝而言，从他第四个月会翻身开始，天堂到地狱，生与死之间，真的很近。', 'https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_17543833354903818020%22%7D&n_type=0&p_from=1', 'https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3411771317,1276375638&fm=173&app=25&f=JPEG?w=218&h=146&s=758BB5571C60CA1F1305F9E10300F03D', '2018-06-10 22:59:45', '2018-06-11 16:40:26', '1005818121710104577');
INSERT INTO `kf_article_tb` VALUES ('1007205878091104258', '每一次离别都要用力，再见或许已是来生', '等到了一定的岁数，你自然会明白：原来不是所有的离别，还能再次重逢。', 'https://mp.weixin.qq.com/s/5_IICZurKXnVenzxePYEhA', 'http://ccsd.boya1.cn/uploaderPath/img/20180614/1528971562294.jpg', '2018-06-14 18:19:29', '2018-06-14 18:20:11', '1007075785616293889');
INSERT INTO `kf_article_tb` VALUES ('1009965828219559937', '5分钟短片戳中泪点：爸爸，你永远是我心中的超人！', '一位退休后的爸爸鼓起勇气，向一家时尚购物公司递交了求职信。面试官是两名年轻靓丽的女性，她们对于这位爸爸面试的岗位充满了疑问，一再确认：您面试的这个工作，是实习生么？', 'https://mp.weixin.qq.com/s/ROY0JvJ9A8IB81XNHM-vsw', 'https://mmbiz.qpic.cn/mmbiz_gif/9XeHBSMzUXoj4hibrc1yUzZ9cnTfthr7OACRB5mRaPGAcwjFUDJDUVzWgAlJCOIPibHubsIaFia4AQjciaJpFibrAWg/640?wx_fmt=gif&tp=webp&wxfrom=5&wx_lazy=1', '2018-06-22 09:06:32', '2018-06-22 09:06:32', '1009965555468165121');
INSERT INTO `kf_article_tb` VALUES ('1010148525185392642', '和空姐在荒岛求生，我每天都求放过…… ', '我揉了揉眼睛，定眼一瞧，那男人正在肆意的撕扯一个空姐的制服，空姐绝望的挣扎着……', 'https://mp.weixin.qq.com/s/saUTd9SgvGhKzSKXtyoXdw', 'http://ccsd.boya1.cn/uploaderPath/img/20180622/1529673098696.jpg', '2018-06-22 21:12:31', '2018-06-22 21:12:31', '1010147142856040449');
INSERT INTO `kf_article_tb` VALUES ('1010159371420655617', '一觉醒来，身边多了个美女……', '身边的她随着呼吸的节奏丰满的胸脯一起一伏，我血脉喷张，脑子里浮现出来的全是把她压在身下疯狂侵犯的画面……', 'https://mp.weixin.qq.com/s/8_fOWShGrI1G_sEcvATJIA', 'http://ccsd.boya1.cn/uploaderPath/img/20180622/1529675702872.jpg', '2018-06-22 21:55:37', '2018-06-22 21:55:37', '1010158330373431298');
INSERT INTO `kf_article_tb` VALUES ('1010526985472925697', '村上唯一的寡妇，她的生活天天是这样的……', '进屋后，张青山刚坐下，乔寡妇就招呼他道：“你吃了吗？要不我下面给你吃吧。”  张青山赶紧站起来……', 'https://mp.weixin.qq.com/s/r7MCLYWJ-HFZD9xKVMOQLA', 'http://ccsd.boya1.cn/uploaderPath/img/20180623/1529763380600.jpg', '2018-06-23 22:16:23', '2018-06-23 22:16:23', '1010524440016936962');
INSERT INTO `kf_article_tb` VALUES ('1011138544603717633', '如何撕碎女生的矜持？让她乖乖听你的！', '“嗯，小`姨好久没有给我洗过澡了，就是想要小`姨帮我洗……”', 'https://mp.weixin.qq.com/s/o6ilA5RE6S7RY_kAum99dg', 'http://ccsd.boya1.cn/uploaderPath/img/20180625/1529909173566.jpg', '2018-06-25 14:46:30', '2018-06-25 14:51:26', '1011136953779707906');
INSERT INTO `kf_article_tb` VALUES ('1011144096339681282', '7分钟催泪短片：即将变成丧尸的爸爸，这样拯救自己的宝宝……', '父亲对我们的爱大多时候是沉默无言的，但是我们作为儿女，不应该隐藏对他们的爱。', 'https://mp.weixin.qq.com/s/OeLz9VGS1Gp9JTawRz9CHw', 'http://ccsd.boya1.cn/uploaderPath/img/20180625/1529910509972.jpg', '2018-06-25 15:08:33', '2018-06-25 15:09:08', '1011142560691740674');
INSERT INTO `kf_article_tb` VALUES ('1011169242597253122', '靠自己挣钱，是一个女人最大的体面', '一个女人怎样才算活得体面？是嫁入豪门，相夫教子；还是成为女强人，宠自己一辈子!', 'https://mp.weixin.qq.com/s/NrUyh3v93Ac5CBmuovsBhA', 'http://ccsd.boya1.cn/uploaderPath/img/20180625/1529916493920.jpg', '2018-06-25 16:48:29', '2018-06-25 16:48:29', '1011169140885381122');
INSERT INTO `kf_article_tb` VALUES ('1011586936119189505', '真正被偏爱的那个人，不会在乎外在多难看', '都说“被偏爱的都有恃无恐”，但如果一个人在自己爱人面前，连自己最真实的样子都无法展现，那生活该有多累？', 'https://mp.weixin.qq.com/s/riuVutH1d9ajYeJ992E2wQ', 'http://ccsd.boya1.cn/uploaderPath/img/20180628/153018610435.jpg', '2018-06-26 20:28:15', '2018-06-28 19:48:20', '1011586797950427137');
INSERT INTO `kf_article_tb` VALUES ('1011591751108878338', '他对她爱入骨髓，却甘心把她拱手让给兄弟', '“等季洛伊把孩子生下来，你就跟沈潇结婚，孩子是你的，男人也是你的……', 'https://mp.weixin.qq.com/s/_W4yxWnS1GZDtvo6LehCvA', 'http://ccsd.boya1.cn/uploaderPath/img/20180626/1530017240125.jpg', '2018-06-26 20:47:23', '2018-06-26 20:47:23', '1011591550906359810');
INSERT INTO `kf_article_tb` VALUES ('1011949924760117249', '女子失身后，如何在新婚夜瞒天过海？', '刚被迫做掉孩子，叶予念就得到了一个不可思议的噩耗', 'https://mp.weixin.qq.com/s/hUbm7TxpFnEmOix3BFtQsw', 'http://ccsd.boya1.cn/uploaderPath/img/20180627/1530102632492.jpg', '2018-06-27 20:30:38', '2018-06-27 20:30:38', '1011949434882187265');
INSERT INTO `kf_article_tb` VALUES ('1012313196332015617', '女子街头突然昏迷，神秘大叔掏出一枚硬币，神奇的一幕发生了', '一个肥胖的男人左拥右抱着几名花枝招展的女郎，肥胖男人脸上有着一道深深的刀疤，给人一种阴险狠辣的感觉。', 'https://mp.weixin.qq.com/s/NZtTLbMhKsvCpId1NuL1qw', 'http://ccsd.boya1.cn/uploaderPath/img/20180628/1530189246155.jpg', '2018-06-28 20:34:09', '2018-06-28 20:34:09', '1012312759126155265');
INSERT INTO `kf_article_tb` VALUES ('1013046757435596801', '6张图道破人性，只有少数人才能看懂!', null, 'https://mp.weixin.qq.com/s/Fn4ZzNTUkKEOgfHUKz05Jw', 'http://ccsd.boya1.cn/uploaderPath/img/20180630/1530364140551.jpg', '2018-06-30 21:09:03', '2018-06-30 21:09:03', '1013045792615653377');
INSERT INTO `kf_article_tb` VALUES ('1013047027624271873', '合租室友月收入三万多，但我不嫉妒她……', null, 'https://mp.weixin.qq.com/s/mUdSH2sfov7dUecqXBCiJg', 'http://ccsd.boya1.cn/uploaderPath/img/20180630/153036420437.jpg', '2018-06-30 21:10:08', '2018-06-30 21:10:08', '1013045792615653377');
INSERT INTO `kf_article_tb` VALUES ('1013047240581668866', '女业主出轨，丈夫默默原谅了她，她居然扬言要离婚！', null, 'https://mp.weixin.qq.com/s/cKmjPuF1ylTt39zHH6asuA', 'http://ccsd.boya1.cn/uploaderPath/img/20180630/1530364242902.jpg', '2018-06-30 21:10:58', '2018-06-30 21:10:58', '1013045792615653377');
INSERT INTO `kf_article_tb` VALUES ('1013047703255343106', '儿子去世3年后，妈妈再次听到他的心跳，瞬间泪崩！', null, 'https://mp.weixin.qq.com/s/lCSh3fFiF2Fqv15R5gE7rA', 'http://ccsd.boya1.cn/uploaderPath/img/20180630/1530364352366.jpg', '2018-06-30 21:12:49', '2018-06-30 21:12:49', '1013045792615653377');
INSERT INTO `kf_article_tb` VALUES ('1013053853195526145', '被美女开豪车撞伤，为补偿我，她提出这个要求……', null, 'https://mp.weixin.qq.com/s/f4UR2z0v-rvhzd1KnOB9FA', 'http://ccsd.boya1.cn/uploaderPath/img/20180630/1530365830737.jpg', '2018-06-30 21:37:15', '2018-06-30 21:37:15', '1013053319940104194');
INSERT INTO `kf_article_tb` VALUES ('1013053994958807042', '妻子半夜发了条朋友圈，出差丈夫看到后连夜赶回家', null, 'https://mp.weixin.qq.com/s/h0IzX4CY_yPCyDjo0mvyPA', 'http://ccsd.boya1.cn/uploaderPath/img/20180630/1530365866396.jpg', '2018-06-30 21:37:49', '2018-06-30 21:43:56', '1013053319940104194');
INSERT INTO `kf_article_tb` VALUES ('1013054298265706497', '离婚吧，这日子没法过了！', null, 'https://mp.weixin.qq.com/s/0n9ePLBysTL83vt1qmTOzg', 'http://ccsd.boya1.cn/uploaderPath/img/20180630/1530365936990.jpg', '2018-06-30 21:39:01', '2018-06-30 21:39:01', '1013053319940104194');
INSERT INTO `kf_article_tb` VALUES ('1013054524238028802', '《钓鱼》结局出来了，无数人惊掉下巴', null, 'https://mp.weixin.qq.com/s/H-QHd2dWJlUfjmGchbGrgg', 'http://ccsd.boya1.cn/uploaderPath/img/20180630/153036599044.jpg', '2018-06-30 21:39:55', '2018-06-30 21:44:59', '1013053319940104194');
INSERT INTO `kf_article_tb` VALUES ('1013719035190140930', '即使喝了孟婆汤，这故事的情节你依然忘不掉', '这是一片粉红的世界：粉红色的墙、粉红色的衣柜、粉红色的席梦思、粉红色的床单床罩、粉红色的暧昧灯光……', 'https://mp.weixin.qq.com/s/6ncf6ATN76KtzW7EqNxZZA', 'http://ccsd.boya1.cn/uploaderPath/img/20180702/1530524422786.jpg', '2018-07-02 17:40:27', '2018-07-02 17:40:27', '1013718916998848513');
INSERT INTO `kf_article_tb` VALUES ('1013720107954692097', '老公当众说嫌弃老婆的话，另一个男人却这么说……', '苏狂先是一阵愣神，然后便是恍然，他眼中跳动着激动的光芒，突然张开手臂，紧紧的将少女抱在怀里，仿佛想要将她揉进身体一般。', 'https://mp.weixin.qq.com/s/bIZEOUaqUustUSQ8c4wl3w', 'http://ccsd.boya1.cn/uploaderPath/img/20180702/153052559673.jpg', '2018-07-02 17:44:42', '2018-07-02 18:00:00', '1013717758309785602');
INSERT INTO `kf_article_tb` VALUES ('1013734907350835202', '朋友圈最火爆的一组暖心照片，总有一张让你泪目', null, 'https://mp.weixin.qq.com/s/L2Xwh8w_ugSKTa_T72f-yw', 'http://ccsd.boya1.cn/uploaderPath/img/20180702/1530528204427.jpg', '2018-07-02 18:43:31', '2018-07-02 18:43:31', '1013734590521499650');
INSERT INTO `kf_article_tb` VALUES ('1013735083301888001', '老公要妻子多干活，婆婆却大骂：老婆娶来是干什么的？', null, 'https://mp.weixin.qq.com/s/RdW1l2WCsklhFG9977rvbA', 'http://ccsd.boya1.cn/uploaderPath/img/20180702/1530528250381.jpg', '2018-07-02 18:44:13', '2018-07-02 18:44:13', '1013734590521499650');
INSERT INTO `kf_article_tb` VALUES ('1013735191670120450', '孙俪用4年养了一匹白眼狼，差点身败名裂，别把我对你的好当成理所当然', null, 'https://mp.weixin.qq.com/s/zGR5paAGWlsE2Rncrt8I8g', 'http://ccsd.boya1.cn/uploaderPath/img/20180702/1530528276576.jpg', '2018-07-02 18:44:39', '2018-07-02 18:44:39', '1013734590521499650');
INSERT INTO `kf_article_tb` VALUES ('1014158695255203841', '为什么大多出轨的男人不会离婚娶小三，真相令人震惊', '前面扭着一位纤腰的美女，叶风紧紧地跟随在后面……', 'https://mp.weixin.qq.com/s/SUmt9Uo0UIHfp2i9SdkpSw', 'http://ccsd.boya1.cn/uploaderPath/img/20180703/1530629242531.jpg', '2018-07-03 22:47:30', '2018-07-03 22:47:30', '1014158404606713857');
INSERT INTO `kf_article_tb` VALUES ('1014489567462629378', '对于这样的女人，男人从来都没有抵抗力......', '“别那么多废话了，你们六个一起上吧”男人似乎有些不耐烦了，直接说道。', 'https://mp.weixin.qq.com/s/Hxb-Mkjcs4s7ekKXKoGnAw', 'http://ccsd.boya1.cn/uploaderPath/img/20180704/1530708132498.jpg', '2018-07-04 20:42:16', '2018-07-04 20:42:16', '1014489307340283906');
INSERT INTO `kf_article_tb` VALUES ('1014490106673963009', '一个老实人的世态炎凉', '七年后再回故土，苏狂已找不到熟悉的家门。曾经那脏乱的棚户区已消失，一栋栋小高层拔地而起...', 'https://mp.weixin.qq.com/s/LGV91AUL0eGcxMp2GTtVdQ', 'http://ccsd.boya1.cn/uploaderPath/img/20180704/1530708253665.jpg', '2018-07-04 20:44:24', '2018-07-04 20:44:24', '1014489637285208065');
INSERT INTO `kf_article_tb` VALUES ('1015159897273344002', '女人每天这样做，保证那里很舒服！', null, 'https://mp.weixin.qq.com/s/5Ht-t0pvA9s3XuI4rE3UQA', 'http://ccsd.boya1.cn/uploaderPath/img/20180706/1530867952658.jpg', '2018-07-06 17:05:55', '2018-07-06 17:05:55', '1015158685719273473');
INSERT INTO `kf_article_tb` VALUES ('1015160191231139841', '娱乐圈居然乱成这样！光鲜的背后满是心酸.......', null, 'https://mp.weixin.qq.com/s/4Cp_pj6iPeoqDkBtMMde6Q', 'http://ccsd.boya1.cn/uploaderPath/img/20180706/1530868023382.jpg', '2018-07-06 17:07:05', '2018-07-06 17:07:05', '1015158685719273473');
INSERT INTO `kf_article_tb` VALUES ('1015160913096024065', '因为一次意外，从此她身边多了个霸道的恶魔，每天都...', null, 'https://mp.weixin.qq.com/s/FvgRC3IBwOmaEFMv9FmNWw', 'http://ccsd.boya1.cn/uploaderPath/img/20180706/1530868196482.jpg', '2018-07-06 17:09:57', '2018-07-06 17:09:57', '1015158685719273473');
INSERT INTO `kf_article_tb` VALUES ('1015161204851810306', '酒店的床单为什么是白色的，真相让人脸红...', null, 'https://mp.weixin.qq.com/s/OGZ3eK8BxivOJY8ZXWhsAg', 'http://ccsd.boya1.cn/uploaderPath/img/20180706/1530868265709.jpg', '2018-07-06 17:11:07', '2018-07-06 17:11:07', '1015158685719273473');
INSERT INTO `kf_article_tb` VALUES ('1015161277937557506', '那晚，她不小心进了不该进的房间', null, 'https://mp.weixin.qq.com/s/pMCl_CoBIc8_Ns3i_lBhMQ', 'http://ccsd.boya1.cn/uploaderPath/img/20180706/1530868283142.jpg', '2018-07-06 17:11:24', '2018-07-06 17:11:24', '1015158685719273473');
INSERT INTO `kf_article_tb` VALUES ('1015382617571401729', '别以为是色情，其实句句都是人生智慧', null, 'https://mp.weixin.qq.com/s/cdpQCbFxqFu8W3FOaaZzzA', 'http://ccsd.boya1.cn/uploaderPath/img/20180707/1530921052631.jpg', '2018-07-07 07:50:56', '2018-07-07 07:50:56', '1014447346541600770');
INSERT INTO `kf_article_tb` VALUES ('1015382730976993282', '《我不是药神》口碑爆棚：我不想死，我想活着……', null, 'https://mp.weixin.qq.com/s/vTivEJZGBdVxTLodwVRq5g', 'http://ccsd.boya1.cn/uploaderPath/img/20180707/1530921076590.jpg', '2018-07-07 07:51:23', '2018-07-07 07:57:04', '1014447346541600770');
INSERT INTO `kf_article_tb` VALUES ('1015384052883202049', '傻子走了，骗子傻了……（看懂的人了不起）', null, 'https://mp.weixin.qq.com/s/6L5Z36PmHAwxjbLi0VDimw', 'http://ccsd.boya1.cn/uploaderPath/img/20180707/1530921393610.jpg', '2018-07-07 07:56:38', '2018-07-07 07:56:38', '1014447346541600770');
INSERT INTO `kf_article_tb` VALUES ('1015957915144757249', '顶着这张丑陋的脸，他竟然也下的去手...', '“你洗好了吗？”连家阿姨的声音突然从外面传来，让深陷在欲望中...', 'https://mp.weixin.qq.com/s/dcgXrDGeuC80xWz-4PAaag', 'http://ccsd.boya1.cn/uploaderPath/img/20180708/1531058215411.jpg', '2018-07-08 21:56:57', '2018-07-08 21:56:57', '1015957730272419841');
INSERT INTO `kf_article_tb` VALUES ('1015960395584839682', '只是协议结婚，结果新婚当晚他却对我为所欲为！', '妻子出轨了！陆伟瘫软坐在椅子上，想到那不堪入目的画面，就像有一把锋利的刀子扎在心脏一般...', 'https://mp.weixin.qq.com/s/OF86b2baIk10SOn2u8hqvw', 'http://ccsd.boya1.cn/uploaderPath/img/20180708/153105880174.jpg', '2018-07-08 22:06:49', '2018-07-08 22:07:02', '1015960154286530561');
INSERT INTO `kf_article_tb` VALUES ('1016198562527256577', '出狱后，她竟用这种方式迎接他……', null, 'https://mp.weixin.qq.com/s/6Bc3DBJZBJxAtXH1dLXhaw', 'http://ccsd.boya1.cn/uploaderPath/img/20180709/1531115590275.jpg', '2018-07-09 13:53:12', '2018-07-09 13:53:12', '1016198020015005697');
INSERT INTO `kf_article_tb` VALUES ('1016198641795407873', '妻子出轨，丈夫默默原谅了她，她居然扬言要离婚！', null, 'https://mp.weixin.qq.com/s/eOz5pnszUVZy3BQPGDqmYg', 'http://ccsd.boya1.cn/uploaderPath/img/20180709/1531115605383.jpg', '2018-07-09 13:53:31', '2018-07-09 13:53:31', '1016198020015005697');
INSERT INTO `kf_article_tb` VALUES ('1016198849077911553', '小伙带女友在房东家偷住一夜：他家床太软了', null, 'https://mp.weixin.qq.com/s/glFErnRUjNNWjMvj20uCzA', 'http://ccsd.boya1.cn/uploaderPath/img/20180709/1531115645997.jpg', '2018-07-09 13:54:20', '2018-07-09 13:54:20', '1016198020015005697');
INSERT INTO `kf_article_tb` VALUES ('1016573288848236546', '体检最后一项羞羞 让人难以启齿', null, 'https://mp.weixin.qq.com/s/m0bezoqCutYkwKEsnJixAg', 'http://ccsd.boya1.cn/uploaderPath/img/20180710/1531204932466.jpg', '2018-07-10 14:42:14', '2018-07-10 14:42:14', '1016573186997952513');
INSERT INTO `kf_article_tb` VALUES ('1016573813455003649', '女人为什么会叫，这才是你不知道的原因！', null, 'https://mp.weixin.qq.com/s/MypiqNWUni1B8W7a1RWTOQ', 'http://ccsd.boya1.cn/uploaderPath/img/20180710/1531205083356.jpg', '2018-07-10 14:44:19', '2018-07-10 14:46:13', '1016573186997952513');
INSERT INTO `kf_article_tb` VALUES ('1016574409503350785', '美女乘坐电梯视频被曝光 家人看了都脸红了', null, 'https://mp.weixin.qq.com/s/S_EjSBfBwAfo6FaGQvGIeg', 'http://ccsd.boya1.cn/uploaderPath/img/20180710/1531205199783.jpg', '2018-07-10 14:46:41', '2018-07-10 14:46:41', '1016573186997952513');
INSERT INTO `kf_article_tb` VALUES ('1016934158178590721', '一个想和你过一辈子的男人，是这样的！', null, 'https://mp.weixin.qq.com/s/Hz6arLEkymUiaH12zOt3UQ', 'http://ccsd.boya1.cn/uploaderPath/img/20180711/1531290970521.jpg', '2018-07-11 14:36:12', '2018-07-11 14:36:12', '1016932086402129921');
INSERT INTO `kf_article_tb` VALUES ('1016934256228835329', '女神说新买的口红味道不错，让我尝尝……', null, 'https://mp.weixin.qq.com/s/yG1JxM09IvC5n2mDb4ZTHQ', 'http://ccsd.boya1.cn/uploaderPath/img/20180711/1531291142622.jpg', '2018-07-11 14:36:35', '2018-07-11 14:39:05', '1016932086402129921');
INSERT INTO `kf_article_tb` VALUES ('1016935076806664193', '“装傻”的女人，最令男人着迷了！', null, 'https://mp.weixin.qq.com/s/h11V4Q6wdfwZPNAwXxKJUA', 'http://ccsd.boya1.cn/uploaderPath/img/20180711/1531291188321.jpg', '2018-07-11 14:39:51', '2018-07-11 14:39:51', '1016932086402129921');
INSERT INTO `kf_article_tb` VALUES ('1017336338836496386', '全家逼我向婆婆道歉，我要求亲戚全部到场，现在他们肠子都悔青了', null, 'https://mp.weixin.qq.com/s/T_2mgug46jc1A_L3Mrep5w', 'http://ccsd.boya1.cn/uploaderPath/img/20180712/1531386947195.jpg', '2018-07-12 17:14:19', '2018-07-12 17:15:49', '1016558504555782146');
INSERT INTO `kf_article_tb` VALUES ('1017336984784478210', '女人是不是好色之徒，就看这3点', null, 'https://mp.weixin.qq.com/s/Mw5Or_TQfptB6rfEqq2bDg', 'http://ccsd.boya1.cn/uploaderPath/img/20180712/1531387011107.jpg', '2018-07-12 17:16:53', '2018-07-12 17:16:53', '1016558504555782146');
INSERT INTO `kf_article_tb` VALUES ('1017337129475383298', '女子和丈夫通话后忘挂断, 接下来的声音让丈夫泪崩！', null, 'https://mp.weixin.qq.com/s/sj1QhS0rioVfK2GiADjVLw', 'http://ccsd.boya1.cn/uploaderPath/img/20180712/1531387044326.jpg', '2018-07-12 17:17:28', '2018-07-12 17:17:28', '1016558504555782146');
INSERT INTO `kf_article_tb` VALUES ('1017660719391449090', '怀孕后遭遇强行，丈夫却对我不闻不问', null, 'https://mp.weixin.qq.com/s/nkmqtrQIx83xeGpxISJRBw', 'http://ccsd.boya1.cn/uploaderPath/img/20180713/1531464398951.jpg', '2018-07-13 14:43:17', '2018-07-13 14:46:41', '1017655025535688706');
INSERT INTO `kf_article_tb` VALUES ('1017662302678294529', '跟她的那一晚，感觉身体被掏空....', null, 'https://mp.weixin.qq.com/s/MQCIONYkkpwh2ECmGD87Pw', 'http://ccsd.boya1.cn/uploaderPath/img/20180713/1531464573782.jpg', '2018-07-13 14:49:35', '2018-07-13 14:49:35', '1017655025535688706');
INSERT INTO `kf_article_tb` VALUES ('1017662466063212545', '女友和教练这姿势让男友气得要分手', null, 'https://mp.weixin.qq.com/s/a37FWmAom9BYGl-Ryq_uag', 'http://ccsd.boya1.cn/uploaderPath/img/20180713/153146461376.jpg', '2018-07-13 14:50:14', '2018-07-13 14:50:14', '1017655025535688706');
INSERT INTO `kf_article_tb` VALUES ('1018750329152532482', '妻子去世后，丈夫对孩子说了这样的话，看哭了', null, 'https://mp.weixin.qq.com/s/OTBV5mf_ZZOAoG3bp4u-uQ', 'http://ccsd.boya1.cn/uploaderPath/img/20180716/153172399520.jpg', '2018-07-16 14:53:01', '2018-07-16 14:53:18', '1018749773033959426');
INSERT INTO `kf_article_tb` VALUES ('1018750481665814529', '女人最易让男人朝思暮想的特点，你有吗？', null, 'https://mp.weixin.qq.com/s/CCvns6n7Q8hAZDsIoBMSdQ', 'http://ccsd.boya1.cn/uploaderPath/img/20180716/1531724064220.jpg', '2018-07-16 14:53:37', '2018-07-16 14:54:29', '1018749773033959426');
INSERT INTO `kf_article_tb` VALUES ('1018750769126633473', '约了个美女，可她太主动了…', null, 'https://mp.weixin.qq.com/s/Ic0Uy58lvTMSy278oNqoDA', 'http://ccsd.boya1.cn/uploaderPath/img/20180716/1531724083629.jpg', '2018-07-16 14:54:45', '2018-07-16 14:54:45', '1018749773033959426');
INSERT INTO `kf_article_tb` VALUES ('1019038213420019713', '古代女子洞房前后的区别，惊呆了！', null, 'https://mp.weixin.qq.com/s/-efHRqFGDmZO2riMs7EBsg', 'http://ccsd.boya1.cn/uploaderPath/img/20180717/1531792615728.jpg', '2018-07-17 09:56:58', '2018-07-17 09:56:58', '1019037772699332610');
INSERT INTO `kf_article_tb` VALUES ('1019038345251188737', '新婚老婆太能折腾，没几天就搞得我想离婚了！', null, 'https://mp.weixin.qq.com/s/XO1DM_sdbAA3W7ndM_heiA', 'http://ccsd.boya1.cn/uploaderPath/img/20180717/1531792647965.jpg', '2018-07-17 09:57:29', '2018-07-17 09:57:29', '1019037772699332610');
INSERT INTO `kf_article_tb` VALUES ('1019038425903460354', '女人对男人的\"狠\"，是怎么在床上表现的？', null, 'https://mp.weixin.qq.com/s/t5iGYgC5pBTERVsV8ez5Zg', 'http://ccsd.boya1.cn/uploaderPath/img/20180717/1531792665847.jpg', '2018-07-17 09:57:48', '2018-07-17 09:57:48', '1019037772699332610');
INSERT INTO `kf_article_tb` VALUES ('1019837479377891330', '女人有这样的心计，男人才会把你放在心上！', null, 'https://mp.weixin.qq.com/s/7NMIFe7vuPsDxJPI2GRK2g', 'http://ccsd.boya1.cn/uploaderPath/img/20180719/1531983138204.jpg', '2018-07-19 14:52:57', '2018-07-19 14:52:57', '1019836153948467202');
INSERT INTO `kf_article_tb` VALUES ('1019837583983833090', '分手前一夜，男友求我再陪他一个晚上我该答应吗？', null, 'https://mp.weixin.qq.com/s/mMLsgQsRniWBoI59eEpv3w', 'http://ccsd.boya1.cn/uploaderPath/img/20180719/1531983237770.jpg', '2018-07-19 14:53:22', '2018-07-19 14:53:58', '1019836153948467202');
INSERT INTO `kf_article_tb` VALUES ('1019837838083158018', '给好朋友当伴娘，突然被好几个男人摁住……', null, 'https://mp.weixin.qq.com/s/s4el5vN7lCZHMSa66HQQnw', 'http://ccsd.boya1.cn/uploaderPath/img/20180719/1531983256138.jpg', '2018-07-19 14:54:23', '2018-07-19 14:54:23', '1019836153948467202');
INSERT INTO `kf_article_tb` VALUES ('1021232198657961986', '床上的那些害羞事儿，你该学学她怎么调情....', null, 'https://mp.weixin.qq.com/s/9t-wVjm0LxAoSEUCuhKZOg', 'http://ccsd.boya1.cn/uploaderPath/img/20180723/1532315742444.jpg', '2018-07-23 11:15:04', '2018-07-23 11:15:43', '1021232092877615106');
INSERT INTO `kf_article_tb` VALUES ('1021232492607369217', '你的大腿内侧颜色越深，说明你老公越......', null, 'https://mp.weixin.qq.com/s/jbN_kxVpISTWblhIGSxTvw', 'http://ccsd.boya1.cn/uploaderPath/img/20180723/1532315772168.jpg', '2018-07-23 11:16:14', '2018-07-23 11:16:14', '1021232092877615106');
INSERT INTO `kf_article_tb` VALUES ('1021233048897908738', '那晚趁我喝醉，姐夫不让我回家将我反锁在房间', null, 'https://mp.weixin.qq.com/s/oP8VBLle9wym1vQidhptoQ', 'http://ccsd.boya1.cn/uploaderPath/img/20180723/1532315798511.jpg', '2018-07-23 11:18:27', '2018-07-23 11:18:27', '1021232092877615106');
INSERT INTO `kf_article_tb` VALUES ('1021313573490716674', '女生不出声，对男票影响多大？', null, 'https://mp.weixin.qq.com/s/Mrl02iRi-HVgHPlB1MXdBw', 'http://ccsd.boya1.cn/uploaderPath/img/20180723/1532335125838.jpg', '2018-07-23 16:38:26', '2018-07-23 16:38:57', '1021236047217094657');
INSERT INTO `kf_article_tb` VALUES ('1021314266414903298', '女子13年没穿内衣，13年后的结果惊呆所有人！', null, 'https://mp.weixin.qq.com/s/mPRU23xHTbkWB9iwVEwafg', 'http://ccsd.boya1.cn/uploaderPath/img/20180723/1532335269473.jpg', '2018-07-23 16:41:11', '2018-07-23 16:41:11', '1021236047217094657');
INSERT INTO `kf_article_tb` VALUES ('1021314545277399041', '情侣退房后，服务员打开房间脸都红了', null, 'https://mp.weixin.qq.com/s/SE1Ey_rW6xWw2w811t_sTg', 'http://ccsd.boya1.cn/uploaderPath/img/20180723/1532335336476.jpg', '2018-07-23 16:42:17', '2018-07-23 16:42:17', '1021236047217094657');
INSERT INTO `kf_article_tb` VALUES ('1021760763946266626', '女人什么姿势，让男人看了浴火焚身？', null, 'https://mp.weixin.qq.com/s/1TtN67We6e97hMYD-hnJPQ', 'http://ccsd.boya1.cn/uploaderPath/img/20180724/1532441721894.jpg', '2018-07-24 22:15:24', '2018-07-24 22:15:24', '1021760367790059522');
INSERT INTO `kf_article_tb` VALUES ('1021760867562352642', '她的第一次，交给了一件硅胶器具', null, 'https://mp.weixin.qq.com/s/7I6DI6B50A1ZCQou-lL_qQ', 'http://ccsd.boya1.cn/uploaderPath/img/20180724/1532441744740.jpg', '2018-07-24 22:15:49', '2018-07-24 22:15:49', '1021760367790059522');
INSERT INTO `kf_article_tb` VALUES ('1021761754179497985', '80后小伙儿当催乳师月入两万，声称在体力上有优势……', null, 'https://mp.weixin.qq.com/s/43t7Qz83RARqkpAC0XRMHQ', 'http://ccsd.boya1.cn/uploaderPath/img/20180724/1532441955811.jpg', '2018-07-24 22:19:20', '2018-07-24 22:19:20', '1021760367790059522');
INSERT INTO `kf_article_tb` VALUES ('1022111839337771009', '滴滴司机群聊大尺度曝光，强奸犯都去开顺风车了？！', null, 'https://mp.weixin.qq.com/s/ahbFux2FOVedkL5PWPdP6g', 'http://ccsd.boya1.cn/uploaderPath/img/20180827/1535338550951.jpg', '2018-07-25 21:30:27', '2018-08-27 11:03:04', '1022111636392177666');
INSERT INTO `kf_article_tb` VALUES ('1022491452270374913', '女人犯错后，让男人瞬间心软的4句话，只有情商高的才会说', null, 'https://mp.weixin.qq.com/s/o8vuiz8GAVzrLVZVy0DY3g', 'http://ccsd.boya1.cn/uploaderPath/img/20180726/1532615931522.jpg', '2018-07-26 22:38:54', '2018-07-26 22:38:54', '1015174716529516546');
INSERT INTO `kf_article_tb` VALUES ('1022491572600762370', '老公让我帮他和客户谈生意，却不想是一场噩梦…', null, 'https://mp.weixin.qq.com/s/VRL9zIUj7qEu8z9Jl2l1Bw', 'http://ccsd.boya1.cn/uploaderPath/img/20180726/1532615960179.jpg', '2018-07-26 22:39:23', '2018-07-26 22:39:23', '1015174716529516546');
INSERT INTO `kf_article_tb` VALUES ('1022491667022934017', '为了生存，一个女人需要付出多少代价？', null, 'https://mp.weixin.qq.com/s/IljBroG0ZMI_71iy6TpqGg', 'http://ccsd.boya1.cn/uploaderPath/img/20180726/1532615976833.jpg', '2018-07-26 22:39:45', '2018-07-26 22:39:45', '1015174716529516546');
INSERT INTO `kf_article_tb` VALUES ('1023404223598096386', '女人最易让男人朝思暮想的特点，你有吗？', null, 'https://mp.weixin.qq.com/s/ymieDbn7_o2TKNyHjx1J5Q', 'http://ccsd.boya1.cn/uploaderPath/img/20180729/1532833534703.jpg', '2018-07-29 11:05:55', '2018-07-29 11:05:55', '1022750554808578049');
INSERT INTO `kf_article_tb` VALUES ('1023404337817382914', '隔壁女孩邀我去她家换灯泡…', null, 'https://mp.weixin.qq.com/s/YiKcThmWsvLFem2dE7m6UA', 'http://ccsd.boya1.cn/uploaderPath/img/20180729/1532833565327.jpg', '2018-07-29 11:06:23', '2018-07-29 11:06:23', '1022750554808578049');
INSERT INTO `kf_article_tb` VALUES ('1023404462891528194', '他是不是想和你睡，看这个十个细节……', null, 'https://mp.weixin.qq.com/s/1FrBwJuHY2NrwDsm44-SjA', 'http://ccsd.boya1.cn/uploaderPath/img/20180729/1532833589341.jpg', '2018-07-29 11:06:53', '2018-07-29 11:06:53', '1022750554808578049');
INSERT INTO `kf_article_tb` VALUES ('1023760667363176450', '如果婚姻走到尽头，看看这组6岁孩子的漫画吧', null, 'https://mp.weixin.qq.com/s/tWHQMNzVtvvK7TpMn56NcA', 'http://ccsd.boya1.cn/uploaderPath/img/20180730/153291853528.jpg', '2018-07-30 10:42:18', '2018-07-30 10:42:18', '1015979933839859714');
INSERT INTO `kf_article_tb` VALUES ('1023760810758041602', '人贩子都该死！六旬老人在儿子失踪处摆摊30年：只想再见儿一面……', null, 'https://mp.weixin.qq.com/s/IBKA__VJHSH05JR4qSuPuw', 'http://ccsd.boya1.cn/uploaderPath/img/20180730/1532918570682.jpg', '2018-07-30 10:42:52', '2018-07-30 10:42:52', '1015979933839859714');
INSERT INTO `kf_article_tb` VALUES ('1023760976445632514', '二胎后让老公结扎避孕，医生拒绝做手术：不符合人道主义！', null, 'https://mp.weixin.qq.com/s/iw13hscpEZdKs6ItnKaV6g', 'http://ccsd.boya1.cn/uploaderPath/img/20180730/1532918604846.jpg', '2018-07-30 10:43:32', '2018-07-30 10:43:32', '1015979933839859714');
INSERT INTO `kf_article_tb` VALUES ('1023776271876943873', '女子深夜嚎啕大哭挽留男友：你卑微的样子，真让人心疼', null, 'https://mp.weixin.qq.com/s/P1hpMKcDxW6T89t4JPyW6g', 'http://ccsd.boya1.cn/uploaderPath/img/20180730/1532922257462.jpg', '2018-07-30 11:44:19', '2018-07-30 15:30:01', '1023775972911149058');
INSERT INTO `kf_article_tb` VALUES ('1023776418891493377', '我去穷游那几年，为了省钱做了后悔的事情', null, 'https://mp.weixin.qq.com/s/ObZPoqjDVhBd68d0Kmy0Kw', 'http://ccsd.boya1.cn/uploaderPath/img/20180730/1532935871953.jpg', '2018-07-30 11:44:54', '2018-07-30 15:31:13', '1023775972911149058');
INSERT INTO `kf_article_tb` VALUES ('1023776521341562882', '浴室里的一个小细节，让她发现老公出轨了', null, 'https://mp.weixin.qq.com/s/EQa0Fwv3XfTs3Nyrz6ZF2Q', 'http://ccsd.boya1.cn/uploaderPath/img/20180730/1532922317489.jpg', '2018-07-30 11:45:18', '2018-07-30 15:30:31', '1023775972911149058');
INSERT INTO `kf_article_tb` VALUES ('1023834699068534785', '原配与小三的聊天记录，这才是高手！', null, 'https://mp.weixin.qq.com/s/nwHoLjyR6UEaD21yjqR4vw', 'http://ccsd.boya1.cn/uploaderPath/img/20180730/1532936177271.jpg', '2018-07-30 15:36:29', '2018-07-30 15:36:29', '1023834366862880770');
INSERT INTO `kf_article_tb` VALUES ('1023834836675260417', '晚归的男人，最怕被碰这三个地方，你知道吗？', null, 'https://mp.weixin.qq.com/s/ZRvGnQIHUeOxZKQSadmIHg', 'http://ccsd.boya1.cn/uploaderPath/img/20180730/1532936214438.jpg', '2018-07-30 15:37:02', '2018-07-30 15:37:02', '1023834366862880770');
INSERT INTO `kf_article_tb` VALUES ('1023834933085532161', '你会不会因为尺寸问题和对方分手？', null, 'https://mp.weixin.qq.com/s/CAIwfNrZ5IgkF1YbN42EiQ', 'http://ccsd.boya1.cn/uploaderPath/img/20180730/1532936236676.jpg', '2018-07-30 15:37:25', '2018-07-30 15:37:25', '1023834366862880770');
INSERT INTO `kf_article_tb` VALUES ('1024185875974778882', '回农村参加了一场婚礼，新娘新郎双双去世……', null, 'https://mp.weixin.qq.com/s/4u5xIrt_ofy1y_rmYsTOrw', 'http://ccsd.boya1.cn/uploaderPath/img/20180731/1533019898194.jpg', '2018-07-31 14:51:56', '2018-07-31 14:51:56', '1024185526769610753');
INSERT INTO `kf_article_tb` VALUES ('1024186003112521729', '这4个生肖的女人命最好，看看有没有你？', null, 'https://mp.weixin.qq.com/s/Ax2JALLG_MZpWl7w2O4SzA', 'http://ccsd.boya1.cn/uploaderPath/img/20180731/1533019945379.jpg', '2018-07-31 14:52:26', '2018-07-31 14:52:26', '1024185526769610753');
INSERT INTO `kf_article_tb` VALUES ('1024186092744798209', '合租房的故事：隔壁传来的声音让我内心燥热', null, 'https://mp.weixin.qq.com/s/HtqRdEHRi9WgYzP5sDpLXA', 'http://ccsd.boya1.cn/uploaderPath/img/20180731/1533019965204.jpg', '2018-07-31 14:52:48', '2018-07-31 14:52:48', '1024185526769610753');
INSERT INTO `kf_article_tb` VALUES ('1024190101643784194', '子上的圆洞到底干嘛用的？真相令人爆笑', null, 'https://mp.weixin.qq.com/s/mtm8DO2_xQth0RsMV_L9VQ', 'http://ccsd.boya1.cn/uploaderPath/img/20180731/1533020922567.png', '2018-07-31 15:08:43', '2018-07-31 15:08:43', '1024189952041349121');
INSERT INTO `kf_article_tb` VALUES ('1024190301569478657', '25岁姑娘乳房长出9个肿瘤！这个坏习惯，女生们千万要避免', null, 'https://mp.weixin.qq.com/s/jyVXHomtFfEBAGyJUcfHFQ', 'http://ccsd.boya1.cn/uploaderPath/img/20180731/1533020961285.jpg', '2018-07-31 15:09:31', '2018-07-31 15:09:31', '1024189952041349121');
INSERT INTO `kf_article_tb` VALUES ('1024190429361532929', '泳池中令人窒息一幕：男子淡定游过，水面竟飘起...周围人立马蹿上岸！', null, 'https://mp.weixin.qq.com/s/rtBr_hjpgyaulpUPXwkDSA', 'http://ccsd.boya1.cn/uploaderPath/img/20180731/1533020997416.jpg', '2018-07-31 15:10:02', '2018-07-31 15:10:02', '1024189952041349121');
INSERT INTO `kf_article_tb` VALUES ('1024195610522349570', '路人街拍, 姑娘小心衣服露出来!', null, 'https://mp.weixin.qq.com/s/5K2F9WDCviSkOER-mjVXsw', 'http://ccsd.boya1.cn/uploaderPath/img/20180731/1533022234624.jpg', '2018-07-31 15:30:37', '2018-07-31 15:30:37', '1024195433317199874');
INSERT INTO `kf_article_tb` VALUES ('1024195678767869954', '女子电梯内被索吻 事后男子替她擦嘴时又遭强吻', null, 'https://mp.weixin.qq.com/s/MAtFPvx9hHl5pmwlUXTmUw', 'http://ccsd.boya1.cn/uploaderPath/img/20180731/1533022252256.jpg', '2018-07-31 15:30:53', '2018-07-31 15:30:53', '1024195433317199874');
INSERT INTO `kf_article_tb` VALUES ('1024196125733875714', 'https://mp.weixin.qq.com/s/MAtFPvx9hHl5pmwlUXTmUw', null, 'https://mp.weixin.qq.com/s/98KvNceryN8ZmGe33fyLmQ', 'http://ccsd.boya1.cn/uploaderPath/img/20180731/1533022333634.jpg', '2018-07-31 15:32:40', '2018-07-31 15:32:40', '1024195433317199874');
INSERT INTO `kf_article_tb` VALUES ('1024536226951262210', '你和你的另一半相克吗？准到吓人！！', null, 'https://mp.weixin.qq.com/s/rhMUBuBRseAUO4iSAloALQ', 'http://ccsd.boya1.cn/uploaderPath/img/20180801/1533103443439.jpg', '2018-08-01 14:04:06', '2018-08-01 14:04:06', '1024534807783337985');
INSERT INTO `kf_article_tb` VALUES ('1024536506862333954', '男人想睡的女人是这种！博主看完眼瞎了..', null, 'https://mp.weixin.qq.com/s/WEqbXU45P85w3SEVTDIcZQ', 'http://ccsd.boya1.cn/uploaderPath/img/20180801/1533103511663.jpg', '2018-08-01 14:05:13', '2018-08-01 14:05:13', '1024534807783337985');
INSERT INTO `kf_article_tb` VALUES ('1024536710952972289', '小姐姐透明薄纱三角杯文胸，夏日也会不慎春光外泄？', null, 'https://mp.weixin.qq.com/s/tVMbk5eOqrfrYj9CGnMaZQ', 'http://ccsd.boya1.cn/uploaderPath/img/20180801/1533103560697.jpg', '2018-08-01 14:06:02', '2018-08-01 14:06:02', '1024534807783337985');
INSERT INTO `kf_article_tb` VALUES ('1027038731966803970', '什么样的女生一看就没谈过恋爱？', null, 'https://mp.weixin.qq.com/s/rNnd09l7VALTci9b713Uzg', 'http://ccsd.boya1.cn/uploaderPath/img/20180808/1533700089600.jpg', '2018-08-08 11:48:10', '2018-08-08 11:48:10', '1027038652186947586');
INSERT INTO `kf_article_tb` VALUES ('1027038798551379970', '男女之间的爱，最怕的是什么？', null, 'https://mp.weixin.qq.com/s/hmDWcUFZyQBwRp02XOag2w', 'http://ccsd.boya1.cn/uploaderPath/img/20180808/1533700104232.jpg', '2018-08-08 11:48:26', '2018-08-08 11:48:26', '1027038652186947586');
INSERT INTO `kf_article_tb` VALUES ('1027038998347051010', '完事后男人做的第一件事，看他爱不爱你', null, 'https://mp.weixin.qq.com/s/tV2jMteG9tzT1t3CrZ5N-w', 'http://ccsd.boya1.cn/uploaderPath/img/20180808/1533700118479.jpg', '2018-08-08 11:49:13', '2018-08-08 11:49:13', '1027038652186947586');
INSERT INTO `kf_article_tb` VALUES ('1028945570597892098', '再不主动，你的人生就来不及了', null, 'https://mp.weixin.qq.com/s/cC2aMdMbkdPUcfmyGrZo5g', 'http://ccsd.boya1.cn/uploaderPath/img/20180813/153415471430.jpg', '2018-08-13 18:05:16', '2018-08-13 18:05:16', '1028945374849724417');
INSERT INTO `kf_article_tb` VALUES ('1028945654513332225', '20岁产妇分娩时，产房外家属来了25人，孩子出生后医生才明白', null, 'https://mp.weixin.qq.com/s/ydCWlOH9Up_qJCDAFjEO6A', 'http://ccsd.boya1.cn/uploaderPath/img/20180813/1534154732594.jpg', '2018-08-13 18:05:36', '2018-08-13 18:05:36', '1028945374849724417');
INSERT INTO `kf_article_tb` VALUES ('1028945870717120514', '男子与网恋女友语音, 她不小心打开了摄像头, 画面让男子惊恐不已', null, 'https://mp.weixin.qq.com/s/7SjPlHopjLaWM8gTQIWFsQ', 'http://ccsd.boya1.cn/uploaderPath/img/20180813/153415477963.jpg', '2018-08-13 18:06:27', '2018-08-13 18:06:27', '1028945374849724417');
INSERT INTO `kf_article_tb` VALUES ('1029269594049802242', '清醒一点！这才是婚姻中最大的骗局', null, 'https://mp.weixin.qq.com/s/Arc9pgKdJaCrJEf9uZ-LJw', 'http://ccsd.boya1.cn/uploaderPath/img/20180814/1534231957270.jpeg', '2018-08-14 15:32:49', '2018-08-14 15:32:49', '1029268922994716673');
INSERT INTO `kf_article_tb` VALUES ('1029270802009354241', '没人料到他竟将她宠到那样的地步，人前风光无限', null, 'https://mp.weixin.qq.com/s/HUghKMbd0kES37uF1HqpoA', 'http://ccsd.boya1.cn/uploaderPath/img/20180814/1534232254430.jpg', '2018-08-14 15:37:37', '2018-08-14 15:37:37', '1029268922994716673');
INSERT INTO `kf_article_tb` VALUES ('1029270924587888642', '看一个女人的身材，就大概知道她的修养和气质', null, 'https://mp.weixin.qq.com/s/qK5_RJR20LMPXOexg2eZxg', 'http://ccsd.boya1.cn/uploaderPath/img/20180814/1534232449625.jpg', '2018-08-14 15:38:06', '2018-08-14 15:40:52', '1029268922994716673');
INSERT INTO `kf_article_tb` VALUES ('1029662169944485890', '你拼命追我的样子，真的很廉价。', null, 'https://mp.weixin.qq.com/s/0LBpqZ8o8k3mdykAm1C_NQ', 'http://ccsd.boya1.cn/uploaderPath/img/20180815/1534325561861.jpg', '2018-08-15 17:32:46', '2018-08-15 17:32:46', '1029661980760403969');
INSERT INTO `kf_article_tb` VALUES ('1029662259329298433', '为什么99%的男人永远无法拒绝前任？', null, 'https://mp.weixin.qq.com/s/B18_YXLEzxVlvkvJVnAAcA', 'http://ccsd.boya1.cn/uploaderPath/img/20180815/1534325581755.jpg', '2018-08-15 17:33:07', '2018-08-15 17:33:07', '1029661980760403969');
INSERT INTO `kf_article_tb` VALUES ('1029662333660753922', '女生这几处越小，越不安分。', null, 'https://mp.weixin.qq.com/s/eJccxtIKvAd9AIoDLnYTPw', 'http://ccsd.boya1.cn/uploaderPath/img/20180815/1534325619811.jpg', '2018-08-15 17:33:25', '2018-08-15 17:33:40', '1029661980760403969');
INSERT INTO `kf_article_tb` VALUES ('1030027041794355201', '对男人，女人最要保守的4个秘密！', null, 'https://mp.weixin.qq.com/s/zIwRl4EhAt0GlH4bkkrFVA', 'http://ccsd.boya1.cn/uploaderPath/img/20180816/1534412970112.jpg', '2018-08-16 17:42:38', '2018-08-16 17:49:39', '1030020141530935298');
INSERT INTO `kf_article_tb` VALUES ('1030028988265984001', '男女之间的爱，最怕的是什么？', null, 'https://mp.weixin.qq.com/s/y7F2uEJ9BRW2Ci1wfGb2tw', 'http://ccsd.boya1.cn/uploaderPath/img/20180816/1534413012419.jpg', '2018-08-16 17:50:22', '2018-08-16 17:50:22', '1030020141530935298');
INSERT INTO `kf_article_tb` VALUES ('1030029213441388545', '我养你，才是这世界上最毒的情话', null, 'https://mp.weixin.qq.com/s/mgmSnwe7OrB5eo0qIVXf_Q', 'http://ccsd.boya1.cn/uploaderPath/img/20180816/1534413073126.jpg', '2018-08-16 17:51:16', '2018-08-16 17:51:16', '1030020141530935298');
INSERT INTO `kf_article_tb` VALUES ('1031451495766355970', '嘴唇厚度决定你的婚姻质量，准的可怕！', null, 'https://mp.weixin.qq.com/s/dttyYvbNC1YCX3l4RVISgg', 'http://ccsd.boya1.cn/uploaderPath/img/20180820/1534752171269.jpg', '2018-08-20 16:02:55', '2018-08-20 16:02:55', '1031451113442963457');
INSERT INTO `kf_article_tb` VALUES ('1031451579836985345', '女人用这个动作，男人会上瘾一辈子', null, 'https://mp.weixin.qq.com/s/PInWIlAtiGx3F_YaeKeOAQ', 'http://ccsd.boya1.cn/uploaderPath/img/20180820/1534752193269.jpg', '2018-08-20 16:03:15', '2018-08-20 16:03:15', '1031451113442963457');
INSERT INTO `kf_article_tb` VALUES ('1031451706932785153', '左右你心情的人，一定是你最爱的人', null, 'https://mp.weixin.qq.com/s/tizXyqvNfm2E1-V74TuOIA', 'http://ccsd.boya1.cn/uploaderPath/img/20180820/1534752209867.jpg', '2018-08-20 16:03:45', '2018-08-20 16:03:45', '1031451113442963457');
INSERT INTO `kf_article_tb` VALUES ('1031547433948213249', '为什么在感情里，受伤的总是女人？', null, 'https://mp.weixin.qq.com/s/o_Y6ZHxfPAPtm7X6qBgKeQ', 'http://ccsd.boya1.cn/uploaderPath/img/20180820/1534775043592.jpg', '2018-08-20 22:24:08', '2018-08-20 22:24:08', '1030099322998550529');
INSERT INTO `kf_article_tb` VALUES ('1031547561786404866', '马伊琍“背后的男人”：因为有他的支撑，我才能走得更远', null, 'https://mp.weixin.qq.com/s/73K0L1sWqTaohRonUbJSwg', 'http://ccsd.boya1.cn/uploaderPath/img/20180820/1534775076104.jpg', '2018-08-20 22:24:39', '2018-08-20 22:24:39', '1030099322998550529');
INSERT INTO `kf_article_tb` VALUES ('1031547665125666818', '职场不需要“菜鸟”：你要么出众，要么出局', null, 'https://mp.weixin.qq.com/s/91TfEMljBKVVJ0jUVGxzyA', 'http://ccsd.boya1.cn/uploaderPath/img/20180820/1534775101750.jpg', '2018-08-20 22:25:03', '2018-08-20 22:25:03', '1030099322998550529');
INSERT INTO `kf_article_tb` VALUES ('1031547840107835393', '不要假装自己很努力，结局不会陪你演戏！', null, 'https://mp.weixin.qq.com/s/ymzwsVW8NxqYZiQVf6myoA', 'http://ccsd.boya1.cn/uploaderPath/img/20180820/1534775142694.jpg', '2018-08-20 22:25:45', '2018-08-20 22:25:45', '1030099322998550529');
INSERT INTO `kf_article_tb` VALUES ('1031808821606150145', '别纠缠，别回头，别念旧。', null, 'https://mp.weixin.qq.com/s/_F5U7qezaYfo7G1PSCU2KQ', 'http://ccsd.boya1.cn/uploaderPath/img/20180821/1534837359185.jpg', '2018-08-21 15:42:48', '2018-08-21 15:42:48', '1031796160575238145');
INSERT INTO `kf_article_tb` VALUES ('1031809071100129282', '为何宫女出宫后无人敢娶，原因竟是', null, 'https://mp.weixin.qq.com/s/7wPdLBnrTxQG2MsNn7JyXg', 'http://ccsd.boya1.cn/uploaderPath/img/20180821/1534837425739.jpg', '2018-08-21 15:43:47', '2018-08-21 15:43:47', '1031796160575238145');
INSERT INTO `kf_article_tb` VALUES ('1031809141207920641', '如果有一天我忘了爱你，别放弃我', null, 'https://mp.weixin.qq.com/s/cYpZ_WOcCbUtD1CiRCOcLg', 'http://ccsd.boya1.cn/uploaderPath/img/20180821/1534837435385.jpg', '2018-08-21 15:44:04', '2018-08-21 15:44:04', '1031796160575238145');
INSERT INTO `kf_article_tb` VALUES ('1032176339491418114', '原配与小三的聊天记录，这才是高手！', null, 'https://mp.weixin.qq.com/s/B7XOAsQiJTu5eWJoKEyPFg', 'http://ccsd.boya1.cn/uploaderPath/img/20180822/1534924980413.jpg', '2018-08-22 16:03:11', '2018-08-22 16:03:11', '1032176047618191362');
INSERT INTO `kf_article_tb` VALUES ('1032176547524702210', '两个人合不合适，就看这4点', null, 'https://mp.weixin.qq.com/s/4YDtH4-RyeEutWerbkH_2Q', 'http://ccsd.boya1.cn/uploaderPath/img/20180822/153492503717.jpg', '2018-08-22 16:04:00', '2018-08-22 16:04:00', '1032176047618191362');
INSERT INTO `kf_article_tb` VALUES ('1032176666743599106', '意外被渣男缠上，为摆脱他我选择了这种方法！', null, 'https://mp.weixin.qq.com/s/LOwFwSXFUYg1iogLHqH7Uw', 'http://ccsd.boya1.cn/uploaderPath/img/20180822/1534925057302.jpg', '2018-08-22 16:04:29', '2018-08-22 16:04:29', '1032176047618191362');
INSERT INTO `kf_article_tb` VALUES ('1032580484274778114', '27岁年轻人感冒后死亡，8个脏器5个衰竭，只因做了这件事', null, 'https://mp.weixin.qq.com/s/HeQF3cvYaGtDF5Fs2spq2A', 'http://ccsd.boya1.cn/uploaderPath/img/20180823/1535021342432.jpg', '2018-08-23 18:49:07', '2018-08-23 18:49:07', '1032580119408078850');
INSERT INTO `kf_article_tb` VALUES ('1032580648775380993', '高铁\"霸座男\"的真实身份确认！他出来道歉了，占座理由竟然是…', null, 'https://mp.weixin.qq.com/s/kWmovXp264uFWBUq1J-ZNQ', 'http://ccsd.boya1.cn/uploaderPath/img/20180823/1535021383637.jpg', '2018-08-23 18:49:46', '2018-08-23 18:49:46', '1032580119408078850');
INSERT INTO `kf_article_tb` VALUES ('1032580784687607809', '“近六成年轻人没存款”，别在该挣钱的年纪，选择当佛系青年', null, 'https://mp.weixin.qq.com/s/ULYpXZQU1TszY7SsIdPSAw', 'http://ccsd.boya1.cn/uploaderPath/img/20180823/153502141561.jpg', '2018-08-23 18:50:18', '2018-08-23 18:50:18', '1032580119408078850');
INSERT INTO `kf_article_tb` VALUES ('1033998110943277058', '200斤女孩被男友劈腿 她在朋友圈消失2年后惊艳归来', null, 'https://mp.weixin.qq.com/s/56H4CN4VE1Trhra3fIHICA', 'http://ccsd.boya1.cn/uploaderPath/img/20180827/1535359334388.jpg', '2018-08-27 16:42:15', '2018-08-27 16:42:15', '1033997802531909634');
INSERT INTO `kf_article_tb` VALUES ('1033998311745581058', '女生有哪些高段位的撩人技巧？', null, 'https://mp.weixin.qq.com/s/wLQaS3G0NowO4qOQRqWtaA', 'http://ccsd.boya1.cn/uploaderPath/img/20180827/1535359374174.jpg', '2018-08-27 16:43:03', '2018-08-27 16:43:03', '1033997802531909634');
INSERT INTO `kf_article_tb` VALUES ('1033998668378861570', '哥，这事不怨我啊!是嫂子先提出来的啊', null, 'https://mp.weixin.qq.com/s/dlp3e-_2cqZcWuA0uBJCCA', 'http://ccsd.boya1.cn/uploaderPath/img/20180827/1535359466293.jpg', '2018-08-27 16:44:28', '2018-08-27 16:44:28', '1033997802531909634');
INSERT INTO `kf_article_tb` VALUES ('1034359762175848450', '姑娘天天做美甲 结果手指变黑了...', null, 'https://mp.weixin.qq.com/s/2R_iyom15uRH0gITCjf0mA', 'http://ccsd.boya1.cn/uploaderPath/img/20180828/1535445556295.jpg', '2018-08-28 16:39:19', '2018-08-28 16:39:19', '1034359377172295682');
INSERT INTO `kf_article_tb` VALUES ('1034359840932294657', '夫妻结婚4年“不孕” 医生一查：女方仍是…', null, 'https://mp.weixin.qq.com/s/HgzpfSekT_oq1G-g3YDtUg', 'http://ccsd.boya1.cn/uploaderPath/img/20180828/153544556882.jpg', '2018-08-28 16:39:38', '2018-08-28 16:39:38', '1034359377172295682');
INSERT INTO `kf_article_tb` VALUES ('1034360128154038273', '出生豪门，却与穷小子闪婚，处处为丈夫着想，如今成这样！', null, 'https://mp.weixin.qq.com/s/ojRQlzoiV2BeBKelT2L92g', 'http://ccsd.boya1.cn/uploaderPath/img/20180828/1535445645857.jpg', '2018-08-28 16:40:47', '2018-08-28 16:40:47', '1034359377172295682');
INSERT INTO `kf_article_tb` VALUES ('1035097507923947522', '他心底的7种温柔，他其实真的很爱你！', null, 'https://mp.weixin.qq.com/s/YhpvAhN_lEB3aL-2xIOQgQ', 'http://ccsd.boya1.cn/uploaderPath/img/20180830/1535621441517.jpg', '2018-08-30 17:30:52', '2018-08-30 17:30:52', '1035097017576255490');
INSERT INTO `kf_article_tb` VALUES ('1035097580065976322', '“我下个月结婚，再陪我最后一次”', null, 'https://mp.weixin.qq.com/s/o_dTAkr0E9zIxk49pQXuvw', 'http://ccsd.boya1.cn/uploaderPath/img/20180830/1535621464694.jpg', '2018-08-30 17:31:09', '2018-08-30 17:31:09', '1035097017576255490');
INSERT INTO `kf_article_tb` VALUES ('1035097650211516418', '那个结婚\"不要房不要车不要彩礼\"的姑娘，后来怎么样了？', null, 'https://mp.weixin.qq.com/s/0GlIdd2SWOQjtyyx6mB5Bw', 'http://ccsd.boya1.cn/uploaderPath/img/20180830/1535621481640.jpg', '2018-08-30 17:31:26', '2018-08-30 17:31:26', '1035097017576255490');
INSERT INTO `kf_article_tb` VALUES ('1036445514783191042', '爱你用了好多年，删除却只用了一秒', null, 'https://mp.weixin.qq.com/s/EFlx-F2I2Oaij0cU-KQ9Jw', 'http://ccsd.boya1.cn/uploaderPath/img/20180903/1535942560344.jpg', '2018-09-03 10:47:22', '2018-09-03 10:47:22', '1036443784481796098');
INSERT INTO `kf_article_tb` VALUES ('1036446870147362817', '有毒的女子，才可爱', null, 'https://mp.weixin.qq.com/s/DNS0RqFzyQ_F4OLzcNJBRQ', 'http://ccsd.boya1.cn/uploaderPath/img/20180903/153594316155.jpg', '2018-09-03 10:52:45', '2018-09-03 10:52:45', '1036443784481796098');
INSERT INTO `kf_article_tb` VALUES ('1036447205511327745', '\"你那么好看，别当小三了\"', null, 'https://mp.weixin.qq.com/s/dfaZ2dCbWPGsX76HiY_Qjg', 'http://ccsd.boya1.cn/uploaderPath/img/20180903/1535943204477.jpg', '2018-09-03 10:54:05', '2018-09-03 10:54:05', '1036443784481796098');
INSERT INTO `kf_article_tb` VALUES ('1036532796580098049', '朋友结婚我随了400元份子钱，一个月后她把我拉黑了', null, 'https://mp.weixin.qq.com/s/ArfWBdwTE4o_QhQaS0f12w', 'http://ccsd.boya1.cn/uploaderPath/img/20180903/1535963648446.jpg', '2018-09-03 16:34:11', '2018-09-03 16:34:11', '1036532491150880769');
INSERT INTO `kf_article_tb` VALUES ('1036532985143422978', '“8年被迫流产6次”，女人是人，不是生育的机器', null, 'https://mp.weixin.qq.com/s/2h3kNWOkXHLz8OC8NWggPQ', 'http://ccsd.boya1.cn/uploaderPath/img/20180903/1535963689489.jpg', '2018-09-03 16:34:56', '2018-09-03 16:34:56', '1036532491150880769');
INSERT INTO `kf_article_tb` VALUES ('1036533153725083649', '35岁女医生被逼自杀：比起眼盲，更可怕的是心盲', null, 'https://mp.weixin.qq.com/s/8kIM-HGjtEL4BcqHoksNnQ', 'http://ccsd.boya1.cn/uploaderPath/img/20180903/1535963732519.jpg', '2018-09-03 16:35:36', '2018-09-03 16:35:36', '1036532491150880769');
INSERT INTO `kf_article_tb` VALUES ('1036533351490711554', '这几天最感动朋友圈的10张图，看到第几张你泪目了？', null, 'https://mp.weixin.qq.com/s/flL5CusZnEIfvNMpm0kUww', 'http://ccsd.boya1.cn/uploaderPath/img/20180903/1535963772708.jpg', '2018-09-03 16:36:24', '2018-09-03 16:36:24', '1036532491150880769');
INSERT INTO `kf_article_tb` VALUES ('1036533672652763138', '上海杀妻藏尸案：嫁错了垃圾男，是会要命的', null, 'https://mp.weixin.qq.com/s/sTC6zeqYm_KikqckIt_olw', 'http://ccsd.boya1.cn/uploaderPath/img/20180903/1535963857877.jpg', '2018-09-03 16:37:40', '2018-09-03 16:37:40', '1036532491150880769');
INSERT INTO `kf_article_tb` VALUES ('1037183594754404354', '婚姻有两种，一种叫搭伙，一种叫余生！', null, 'https://mp.weixin.qq.com/s/w-E21coVdmummlLLQvHmeA', 'http://ccsd.boya1.cn/uploaderPath/img/20180905/1536118811828.jpg', '2018-09-05 11:40:14', '2018-09-05 11:40:14', '1037179865577746434');
INSERT INTO `kf_article_tb` VALUES ('1037183720998760450', '古代“通房丫头”不能说的秘密，到底是怎么个“通房”法?', null, 'https://mp.weixin.qq.com/s/8EdMs0BaFY2hg2TYPs3T0Q', 'http://ccsd.boya1.cn/uploaderPath/img/20180905/153611884234.jpg', '2018-09-05 11:40:44', '2018-09-05 11:40:44', '1037179865577746434');
INSERT INTO `kf_article_tb` VALUES ('1037184536254017537', '七年之痒杀死了多少婚姻', null, 'https://mp.weixin.qq.com/s/62vYrOEWX7HT3-Ju7XbDTg', 'http://ccsd.boya1.cn/uploaderPath/img/20180905/153611895725.jpg', '2018-09-05 11:43:58', '2018-09-05 11:43:58', '1037179865577746434');
INSERT INTO `kf_article_tb` VALUES ('1039057078086463489', '他爱不爱你，其实你比谁都清楚', null, 'https://mp.weixin.qq.com/s/FuSkHzZTJ7E9f-F8Emj7eg', 'http://ccsd.boya1.cn/uploaderPath/img/20180910/1536565474964.jpg', '2018-09-10 15:44:47', '2018-09-10 15:44:47', '1039056939036897282');
INSERT INTO `kf_article_tb` VALUES ('1039057546762186754', '你后悔娶现在的老婆吗？我们采访了500个已婚男士...', null, 'https://mp.weixin.qq.com/s/t4Sm-zKxygqwhJ-2U73jVg', 'http://ccsd.boya1.cn/uploaderPath/img/20180910/1536565523803.jpg', '2018-09-10 15:46:39', '2018-09-10 15:46:39', '1039056939036897282');
INSERT INTO `kf_article_tb` VALUES ('1039057736873209858', '没法再近一点，那就和他再深一点', null, 'https://mp.weixin.qq.com/s/lHSPRz_5AsZzlmGR1196aQ', 'http://ccsd.boya1.cn/uploaderPath/img/20180910/1536565642686.jpg', '2018-09-10 15:47:24', '2018-09-10 15:47:24', '1039056939036897282');
INSERT INTO `kf_article_tb` VALUES ('1041954382414016513', '“我嫁给了暖男，最后却离婚了”', null, 'https://mp.weixin.qq.com/s/r62ItvdLqtlnl14qbnh8mw', 'http://ccsd.boya1.cn/uploaderPath/img/20180918/153725634931.jpg', '2018-09-18 15:37:38', '2018-09-18 15:40:03', '1041567526363000833');
INSERT INTO `kf_article_tb` VALUES ('1041954652179066881', '好的婚姻，就是一次又一次地爱上对方', null, 'https://mp.weixin.qq.com/s/57HYhq6oj-xIAjCfLiLpmA', 'http://ccsd.boya1.cn/uploaderPath/img/20180918/153725632081.jpg', '2018-09-18 15:38:42', '2018-09-18 15:38:42', '1041567526363000833');
INSERT INTO `kf_article_tb` VALUES ('1041955093449207810', '古代结局凄凉的妃子很多，最惨的肯定是她了！', null, 'https://mp.weixin.qq.com/s/mPuwTAmQboMAoRWzuO0t_A', 'http://ccsd.boya1.cn/uploaderPath/img/20180918/1537256426770.jpg', '2018-09-18 15:40:28', '2018-09-18 15:40:28', '1041567526363000833');
INSERT INTO `kf_article_tb` VALUES ('1042693692016750594', '最让女生忘不掉的男生是这种…', null, 'https://mp.weixin.qq.com/s/pjMQOgctbp3mCS49w8deaQ', 'http://ccsd.boya1.cn/uploaderPath/img/20180920/1537432519112.jpg', '2018-09-20 16:35:23', '2018-09-20 16:35:23', '1042693515809845249');
INSERT INTO `kf_article_tb` VALUES ('1042693780336209921', '不管世界对你多糟糕，你也要活得清澈而美好', null, 'https://mp.weixin.qq.com/s/6IZJ-hkifh388g14-_QGnQ', 'http://ccsd.boya1.cn/uploaderPath/img/20180920/1537432542514.jpg', '2018-09-20 16:35:44', '2018-09-20 16:35:44', '1042693515809845249');
INSERT INTO `kf_article_tb` VALUES ('1042693915577348098', '爱不是用嘴说，要做！', null, 'https://mp.weixin.qq.com/s/SUTGf7lflW_1KFsMsyHACw', 'http://ccsd.boya1.cn/uploaderPath/img/20180920/1537432572175.jpg', '2018-09-20 16:36:16', '2018-09-20 16:36:16', '1042693515809845249');
INSERT INTO `kf_article_tb` VALUES ('1044506714746056706', '手上的这条线,居然代表你的婚姻?一定要看', null, 'https://mp.weixin.qq.com/s/Ed_jWCUZ5go7cn9BoTE6mw', 'http://ccsd.boya1.cn/uploaderPath/img/20180925/1537864774692.jpg', '2018-09-25 16:39:41', '2018-09-25 16:41:58', '1044504616767778817');
INSERT INTO `kf_article_tb` VALUES ('1044506803774353410', '男人的不解风情可以到什么程度？', null, 'https://mp.weixin.qq.com/s/ezOFu7L00wbm2WFO16nPHw', 'http://ccsd.boya1.cn/uploaderPath/img/20180925/1537864795388.jpg', '2018-09-25 16:40:03', '2018-09-25 16:41:49', '1044504616767778817');
INSERT INTO `kf_article_tb` VALUES ('1044507223779373057', '“爱你的男人，会主动给你的”', null, 'https://mp.weixin.qq.com/s/RU0yY1dfgORWuLW35AFNYA', 'http://ccsd.boya1.cn/uploaderPath/img/20180925/1537864899641.jpg', '2018-09-25 16:41:43', '2018-09-25 16:42:02', '1044504616767778817');
INSERT INTO `kf_article_tb` VALUES ('1045219113891459074', '有一种伤害，叫冷淡！', null, 'https://mp.weixin.qq.com/s/8bYEfCyS_A5EREragpgGGQ', 'http://ccsd.boya1.cn/uploaderPath/img/20180927/1538034534648.jpg', '2018-09-27 15:50:31', '2018-09-27 15:50:31', '1045218106650324994');
INSERT INTO `kf_article_tb` VALUES ('1045219344641093634', '除了转账和娶你，所有的喜欢都是假的。', null, 'https://mp.weixin.qq.com/s/my8FnN0iACEXzOg6_NvMSQ', 'http://ccsd.boya1.cn/uploaderPath/img/20180927/1538034684293.jpg', '2018-09-27 15:51:26', '2018-09-27 15:51:26', '1045218106650324994');
INSERT INTO `kf_article_tb` VALUES ('1045219598853664770', '他心里最难忘的前任，都是这样的姑娘', null, 'https://mp.weixin.qq.com/s/PeA7ty9RLjRRE3bIyn9PQQ', 'http://ccsd.boya1.cn/uploaderPath/img/20180927/1538034719650.jpg', '2018-09-27 15:52:26', '2018-09-27 15:52:26', '1045218106650324994');
INSERT INTO `kf_article_tb` VALUES ('1052474588236288002', '终于知道有些男的为什么宁愿一直单身了！！', null, 'https://mp.weixin.qq.com/s/anX-QzCnZR_rIUa61swK7Q', 'http://ccsd.boya1.cn/uploaderPath/img/20181017/15397644857.jpg', '2018-10-17 16:21:11', '2018-10-17 16:21:27', '1052474497974865922');
INSERT INTO `kf_article_tb` VALUES ('1052474733933826049', '好的婚姻，就是一次又一次地爱上对方', null, 'https://mp.weixin.qq.com/s/0OEj3xjFGs-0g7RMZJecvw', 'http://ccsd.boya1.cn/uploaderPath/img/20181017/1539764504999.jpg', '2018-10-17 16:21:45', '2018-10-17 16:21:45', '1052474497974865922');
INSERT INTO `kf_article_tb` VALUES ('1052474808869261314', '“等你让我满意了，我就帮你弄...”', null, 'https://mp.weixin.qq.com/s/4t1vA4gDvSN3_MubV85E_Q', 'http://ccsd.boya1.cn/uploaderPath/img/20181017/1539764518251.jpg', '2018-10-17 16:22:03', '2018-10-17 16:22:03', '1052474497974865922');

-- ----------------------------
-- Table structure for `kf_message_tb`
-- ----------------------------
DROP TABLE IF EXISTS `kf_message_tb`;
CREATE TABLE `kf_message_tb` (
  `kf_message_id` bigint(20) NOT NULL COMMENT '客服消息id',
  `msgtype` varchar(255) DEFAULT NULL COMMENT '消息类型，text文本消息，image图片消息，voice语音消息，video视频消息，music音乐消息，news图文消息（外链），mpnews图文消息（微信链接），wxcard发送卡券，miniprogrampage小程序',
  `content` longtext COMMENT '文本消息内容',
  `media_id` varchar(255) DEFAULT NULL COMMENT '发送的图片/语音/视频/图文消息（点击跳转到图文消息页）的媒体ID',
  `thumb_media_id` varchar(255) DEFAULT NULL COMMENT '缩略图/小程序卡片图片的媒体ID，小程序卡片图片建议大小为520*416',
  `title` varchar(255) DEFAULT NULL COMMENT '视频消息/音乐消息/小程序卡片的标题',
  `description` varchar(255) DEFAULT NULL COMMENT '视频消息/音乐消息的描述',
  `musicurl` varchar(255) DEFAULT NULL COMMENT '音乐链接',
  `hqmusicurl` varchar(255) DEFAULT NULL COMMENT '高品质音乐链接，wifi环境优先使用该链接播放音乐',
  `card_id` varchar(255) DEFAULT NULL COMMENT '卡卷id',
  `appid` varchar(255) DEFAULT NULL COMMENT '小程序的appid，要求小程序的appid需要与公众号有关联关系',
  `pagepath` varchar(255) DEFAULT NULL COMMENT '小程序的页面路径，跟app.json对齐，支持参数，比如pages/index/index?foo=bar',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `subscription_id` bigint(20) DEFAULT NULL COMMENT '公众号id外键',
  PRIMARY KEY (`kf_message_id`),
  KEY `INDEX_MSGTYPE` (`msgtype`) USING BTREE,
  KEY `INDEX_SUBSCRIPTIONID` (`subscription_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客服消息表';

-- ----------------------------
-- Records of kf_message_tb
-- ----------------------------
INSERT INTO `kf_message_tb` VALUES ('1005818121710104577', 'news', null, null, null, null, null, null, null, null, null, null, '2018-06-10 22:25:02', '2018-06-10 22:25:02', '1004731546939158530');
INSERT INTO `kf_message_tb` VALUES ('1005821604970524673', 'text', '测试<a href=\"http://www.baidu.com\">百度</a>\n测试2<a href=\"http://www.baidu.com\">百度2</a>', null, null, null, null, null, null, null, null, null, '2018-06-10 22:38:53', '2018-06-11 00:29:07', '1004731546939158530');
INSERT INTO `kf_message_tb` VALUES ('1007591279536619522', 'text', '你好！/n<a href=\"http://www.baidu.com\">百度</a>', null, null, null, null, null, null, null, null, null, '2018-06-15 19:50:56', '2018-06-15 19:50:56', '1007591003165540353');
INSERT INTO `kf_message_tb` VALUES ('1008892308312014850', 'text', '小说上线，邀您围观<a href=\"http://c484.keyuxiaoshuo.com/#/Reading?novel_id=213&link_id=9836&chapter_id=1\">热搜小说</a>', null, null, null, null, null, null, null, null, null, '2018-06-19 10:00:45', '2018-06-19 10:00:45', '1008890688689913858');
INSERT INTO `kf_message_tb` VALUES ('1009965555468165121', 'news', null, null, null, null, null, null, null, null, null, null, '2018-06-22 09:05:27', '2018-06-22 09:05:27', '1007075074857926657');
INSERT INTO `kf_message_tb` VALUES ('1011136953779707906', 'news', null, null, null, null, null, null, null, null, null, null, '2018-06-25 14:40:10', '2018-06-25 14:40:10', '1008890688689913858');
INSERT INTO `kf_message_tb` VALUES ('1013718916998848513', 'news', null, null, null, null, null, null, null, null, null, null, '2018-07-02 17:39:58', '2018-07-02 17:39:58', '1008890688689913858');
INSERT INTO `kf_message_tb` VALUES ('1014489597594509313', 'news', null, null, null, null, null, null, null, null, null, null, '2018-07-04 20:42:23', '2018-07-04 20:42:23', '1008890688689913858');
INSERT INTO `kf_message_tb` VALUES ('1014489637285208065', 'news', null, null, null, null, null, null, null, null, null, null, '2018-07-04 20:42:32', '2018-07-04 20:42:32', '1008890688689913858');
INSERT INTO `kf_message_tb` VALUES ('1015960154286530561', 'news', null, null, null, null, null, null, null, null, null, null, '2018-07-08 22:05:51', '2018-07-08 22:05:51', '1008890688689913858');
INSERT INTO `kf_message_tb` VALUES ('1016558504555782146', 'news', null, null, null, null, null, null, null, null, null, null, '2018-07-10 13:43:29', '2018-07-10 13:43:29', '1008890688689913858');
INSERT INTO `kf_message_tb` VALUES ('1018749773033959426', 'news', null, null, null, null, null, null, null, null, null, null, '2018-07-16 14:50:48', '2018-07-16 14:50:48', '1008890688689913858');
INSERT INTO `kf_message_tb` VALUES ('1019037772699332610', 'news', null, null, null, null, null, null, null, null, null, null, '2018-07-17 09:55:12', '2018-07-17 09:55:12', '1008890688689913858');
INSERT INTO `kf_message_tb` VALUES ('1021236047217094657', 'news', null, null, null, null, null, null, null, null, null, null, '2018-07-23 11:30:22', '2018-07-23 11:30:22', '1008890688689913858');
INSERT INTO `kf_message_tb` VALUES ('1021760367790059522', 'news', null, null, null, null, null, null, null, null, null, null, '2018-07-24 22:13:50', '2018-07-24 22:13:50', '1008952171394220033');
INSERT INTO `kf_message_tb` VALUES ('1022111636392177666', 'news', null, null, null, null, null, null, null, null, null, null, '2018-07-25 21:29:39', '2018-07-25 21:29:39', '1019770736240422913');
INSERT INTO `kf_message_tb` VALUES ('1022750554808578049', 'news', null, null, null, null, null, null, null, null, null, null, '2018-07-27 15:48:29', '2018-07-27 15:48:29', '1008890688689913858');
INSERT INTO `kf_message_tb` VALUES ('1023834366862880770', 'news', null, null, null, null, null, null, null, null, null, null, '2018-07-30 15:35:10', '2018-07-30 15:35:10', '1008890688689913858');
INSERT INTO `kf_message_tb` VALUES ('1024185526769610753', 'news', null, null, null, null, null, null, null, null, null, null, '2018-07-31 14:50:33', '2018-07-31 14:50:33', '1014716513685544961');
INSERT INTO `kf_message_tb` VALUES ('1024195433317199874', 'news', null, null, null, null, null, null, null, null, null, null, '2018-07-31 15:29:55', '2018-07-31 15:29:55', '1008890688689913858');
INSERT INTO `kf_message_tb` VALUES ('1024534807783337985', 'news', null, null, null, null, null, null, null, null, null, null, '2018-08-01 13:58:28', '2018-08-01 13:58:28', '1008890688689913858');
INSERT INTO `kf_message_tb` VALUES ('1027038652186947586', 'news', null, null, null, null, null, null, null, null, null, null, '2018-08-08 11:47:51', '2018-08-08 11:47:51', '1008952171394220033');
INSERT INTO `kf_message_tb` VALUES ('1028945374849724417', 'news', null, null, null, null, null, null, null, null, null, null, '2018-08-13 18:04:29', '2018-08-13 18:04:29', '1008890688689913858');
INSERT INTO `kf_message_tb` VALUES ('1029268922994716673', 'news', null, null, null, null, null, null, null, null, null, null, '2018-08-14 15:30:09', '2018-08-14 15:30:09', '1008890688689913858');
INSERT INTO `kf_message_tb` VALUES ('1029661980760403969', 'news', null, null, null, null, null, null, null, null, null, null, '2018-08-15 17:32:01', '2018-08-15 17:32:01', '1008890688689913858');
INSERT INTO `kf_message_tb` VALUES ('1030020141530935298', 'news', null, null, null, null, null, null, null, null, null, null, '2018-08-16 17:15:13', '2018-08-16 17:15:13', '1008890688689913858');
INSERT INTO `kf_message_tb` VALUES ('1031451089954861057', 'news', null, null, null, null, null, null, null, null, null, null, '2018-08-20 16:01:18', '2018-08-20 16:01:18', '1008890688689913858');
INSERT INTO `kf_message_tb` VALUES ('1031451113442963457', 'news', null, null, null, null, null, null, null, null, null, null, '2018-08-20 16:01:24', '2018-08-20 16:01:24', '1008890688689913858');
INSERT INTO `kf_message_tb` VALUES ('1031796160575238145', 'news', null, null, null, null, null, null, null, null, null, null, '2018-08-21 14:52:29', '2018-08-21 14:52:29', '1008890688689913858');
INSERT INTO `kf_message_tb` VALUES ('1032176047618191362', 'news', null, null, null, null, null, null, null, null, null, null, '2018-08-22 16:02:01', '2018-08-22 16:02:01', '1008890688689913858');
INSERT INTO `kf_message_tb` VALUES ('1033997802531909634', 'news', null, null, null, null, null, null, null, null, null, null, '2018-08-27 16:41:02', '2018-08-27 16:41:02', '1008890688689913858');
INSERT INTO `kf_message_tb` VALUES ('1034359377172295682', 'news', null, null, null, null, null, null, null, null, null, null, '2018-08-28 16:37:48', '2018-08-28 16:37:48', '1008890688689913858');
INSERT INTO `kf_message_tb` VALUES ('1035097017576255490', 'news', null, null, null, null, null, null, null, null, null, null, '2018-08-30 17:28:55', '2018-08-30 17:28:55', '1008890688689913858');
INSERT INTO `kf_message_tb` VALUES ('1036443784481796098', 'news', null, null, null, null, null, null, null, null, null, null, '2018-09-03 10:40:29', '2018-09-03 10:40:29', '1008890688689913858');
INSERT INTO `kf_message_tb` VALUES ('1036532491150880769', 'news', null, null, null, null, null, null, null, null, null, null, '2018-09-03 16:32:58', '2018-09-03 16:32:58', '1009011640656457730');
INSERT INTO `kf_message_tb` VALUES ('1037179865577746434', 'news', null, null, null, null, null, null, null, null, null, null, '2018-09-05 11:25:24', '2018-09-05 11:25:24', '1008890688689913858');
INSERT INTO `kf_message_tb` VALUES ('1038654353800622082', 'news', null, null, null, null, null, null, null, null, null, null, '2018-09-09 13:04:30', '2018-09-09 13:04:30', '1007591003165540353');
INSERT INTO `kf_message_tb` VALUES ('1039056939036897282', 'news', null, null, null, null, null, null, null, null, null, null, '2018-09-10 15:44:14', '2018-09-10 15:44:14', '1008890688689913858');
INSERT INTO `kf_message_tb` VALUES ('1041567526363000833', 'news', null, null, null, null, null, null, null, null, null, null, '2018-09-17 14:00:24', '2018-09-17 14:00:24', '1008890688689913858');
INSERT INTO `kf_message_tb` VALUES ('1041954263530663938', 'news', null, null, null, null, null, null, null, null, null, null, '2018-09-18 15:37:10', '2018-09-18 15:37:10', '1008890688689913858');
INSERT INTO `kf_message_tb` VALUES ('1042693515809845249', 'news', null, null, null, null, null, null, null, null, null, null, '2018-09-20 16:34:41', '2018-09-20 16:34:41', '1008890688689913858');
INSERT INTO `kf_message_tb` VALUES ('1044504616767778817', 'news', null, null, null, null, null, null, null, null, null, null, '2018-09-25 16:31:21', '2018-09-25 16:31:21', '1008890688689913858');
INSERT INTO `kf_message_tb` VALUES ('1045218106650324994', 'news', null, null, null, null, null, null, null, null, null, null, '2018-09-27 15:46:30', '2018-09-27 15:46:30', '1008890688689913858');
INSERT INTO `kf_message_tb` VALUES ('1049472444658806785', 'news', null, null, null, null, null, null, null, null, null, null, '2018-10-09 09:31:44', '2018-10-09 09:31:44', '1008890688689913858');
INSERT INTO `kf_message_tb` VALUES ('1049474052960481282', 'news', null, null, null, null, null, null, null, null, null, null, '2018-10-09 09:38:07', '2018-10-09 09:38:07', '1008890688689913858');
INSERT INTO `kf_message_tb` VALUES ('1052474497974865922', 'news', null, null, null, null, null, null, null, null, null, null, '2018-10-17 16:20:49', '2018-10-17 16:20:49', '1008890688689913858');

-- ----------------------------
-- Table structure for `permission_tb`
-- ----------------------------
DROP TABLE IF EXISTS `permission_tb`;
CREATE TABLE `permission_tb` (
  `permission_id` bigint(20) NOT NULL COMMENT '权限id',
  `type` tinyint(4) DEFAULT NULL COMMENT '权限类型，默认0开放，1鉴权',
  `manager_name` varchar(255) DEFAULT NULL COMMENT '权限管理名',
  `name` varchar(255) DEFAULT NULL COMMENT '权限名',
  `route` varchar(255) DEFAULT NULL COMMENT '权限路由',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`permission_id`),
  UNIQUE KEY `route` (`route`) USING BTREE,
  KEY `INDEX_TYPE` (`type`) USING BTREE,
  KEY `INDEX_MANAGERNAME` (`manager_name`) USING BTREE,
  KEY `INDEX_NAME` (`name`) USING BTREE,
  KEY `INDEX_ROUTE` (`route`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Records of permission_tb
-- ----------------------------
INSERT INTO `permission_tb` VALUES ('1006512472683061250', '1', '模板数据管理', '模板数据增加', '/templateData/add', '2018-11-09 16:19:25');
INSERT INTO `permission_tb` VALUES ('1006512472796307458', '0', '模板数据管理', '模板数据数量', '/templateData/count', '2018-11-09 16:19:29');
INSERT INTO `permission_tb` VALUES ('1006512472821473282', '1', '模板数据管理', '模板数据删除', '/templateData/delete', '2018-11-09 16:19:33');
INSERT INTO `permission_tb` VALUES ('1006512472880193538', '0', '模板数据管理', '模板数据列表', '/templateData/list', '2018-11-09 16:19:38');
INSERT INTO `permission_tb` VALUES ('1006512472909553666', '0', '模板数据管理', '模板数据单个加载', '/templateData/load', '2018-11-09 16:19:50');
INSERT INTO `permission_tb` VALUES ('1006512472938913794', '1', '模板数据管理', '模板数据修改', '/templateData/update', '2018-11-09 16:19:45');
INSERT INTO `permission_tb` VALUES ('1006512472989245441', '1', '账户管理', '账户增加', '/account/add', '2018-06-12 20:24:08');
INSERT INTO `permission_tb` VALUES ('1006512473031188482', '1', '账户管理', '账户实名认证', '/account/auth', '2018-06-12 20:24:08');
INSERT INTO `permission_tb` VALUES ('1006512473123463169', '0', '账户管理', '账户数量', '/account/count', '2018-06-12 20:24:08');
INSERT INTO `permission_tb` VALUES ('1006512473144434690', '1', '账户管理', '账户删除', '/account/delete', '2018-06-12 20:24:08');
INSERT INTO `permission_tb` VALUES ('1006512473186377729', '0', '账户管理', '是否登录', '/account/islogin', '2018-06-12 20:24:08');
INSERT INTO `permission_tb` VALUES ('1006512473219932161', '0', '账户管理', '账户列表', '/account/list', '2018-06-12 20:24:08');
INSERT INTO `permission_tb` VALUES ('1006512473253486593', '0', '账户管理', '账户单个加载', '/account/load', '2018-06-12 20:24:08');
INSERT INTO `permission_tb` VALUES ('1006512473308012546', '0', '账户管理', '管理员登录', '/account/login', '2018-06-12 20:24:08');
INSERT INTO `permission_tb` VALUES ('1006512473349955585', '0', '账户管理', '登出', '/account/loginout', '2018-06-12 20:24:08');
INSERT INTO `permission_tb` VALUES ('1006512473391898625', '1', '账户管理', '手机号账户是否存在', '/account/phoneIsExist', '2018-06-12 20:24:08');
INSERT INTO `permission_tb` VALUES ('1006512473438035970', '1', '账户管理', '账户修改', '/account/update', '2018-06-12 20:24:08');
INSERT INTO `permission_tb` VALUES ('1006512473475784706', '1', '账户管理', '账户修改用户信息', '/account/updateInfo', '2018-06-12 20:24:08');
INSERT INTO `permission_tb` VALUES ('1006512473509339137', '1', '账户管理', '账户修改密码', '/account/updatePassword', '2018-06-12 20:24:08');
INSERT INTO `permission_tb` VALUES ('1006512473530310658', '0', '账户管理', 'web用户登录', '/account/weblogin', '2018-06-12 20:24:08');
INSERT INTO `permission_tb` VALUES ('1006512473597419522', '1', '客服消息文章管理', '客服消息文章增加', '/kfArticle/add', '2018-06-12 20:24:08');
INSERT INTO `permission_tb` VALUES ('1006512473643556865', '0', '客服消息文章管理', '客服消息文章数量', '/kfArticle/count', '2018-06-12 20:24:08');
INSERT INTO `permission_tb` VALUES ('1006512473672916993', '1', '客服消息文章管理', '客服消息文章删除', '/kfArticle/delete', '2018-06-12 20:24:08');
INSERT INTO `permission_tb` VALUES ('1006512473698082818', '0', '客服消息文章管理', '客服消息文章列表', '/kfArticle/list', '2018-06-12 20:24:08');
INSERT INTO `permission_tb` VALUES ('1006512473723248641', '0', '客服消息文章管理', '客服消息文章单个加载', '/kfArticle/load', '2018-06-12 20:24:08');
INSERT INTO `permission_tb` VALUES ('1006512473773580290', '1', '客服消息文章管理', '客服消息文章修改', '/kfArticle/update', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512473794551810', '1', '客服消息管理', '客服消息增加', '/kfMessage/add', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512473832300546', '0', '客服消息管理', '客服消息数量', '/kfMessage/count', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512473874243586', '1', '客服消息管理', '客服消息删除', '/kfMessage/delete', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512473916186626', '0', '客服消息管理', '客服消息列表', '/kfMessage/list', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512473949741057', '0', '客服消息管理', '客服消息单个加载', '/kfMessage/load', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512474016849921', '1', '客服消息管理', '客服消息群发', '/kfMessage/sendKfMessage', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512474079764482', '1', '客服消息管理', '客服消息修改', '/kfMessage/update', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512474109124610', '1', '权限管理', '权限增加', '/permission/add', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512474151067649', '0', '权限管理', '权限数量', '/permission/count', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512474151067650', '1', '权限管理', '权限删除', '/permission/delete', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512474197204994', '1', '权限管理', '初始化权限', '/permission/init', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512474226565121', '0', '权限管理', '权限列表', '/permission/list', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512474251730946', '0', '权限管理', '权限单个加载', '/permission/load', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512474293673986', '1', '权限管理', '权限修改', '/permission/update', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512474327228418', '1', '奖品管理', '奖品增加', '/prize/add', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512474352394241', '0', '奖品管理', '奖品数量', '/prize/count', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512474398531586', '1', '奖品管理', '奖品删除', '/prize/delete', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512474432086018', '0', '奖品管理', '奖品列表', '/prize/list', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512474461446145', '0', '奖品管理', '奖品单个加载', '/prize/load', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512474486611970', '1', '奖品管理', '奖品修改', '/prize/update', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512474528555010', '1', '收货信息管理', '收货信息增加', '/receiptInfo/add', '2018-06-13 09:20:06');
INSERT INTO `permission_tb` VALUES ('1006512474553720834', '0', '收货信息管理', '收货信息数量', '/receiptInfo/count', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512474578886658', '1', '收货信息管理', '收货信息删除', '/receiptInfo/delete', '2018-06-13 09:22:49');
INSERT INTO `permission_tb` VALUES ('1006512474616635393', '0', '收货信息管理', '收货信息', '/receiptInfo/list', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512474654384130', '0', '收货信息管理', '收货信息单个加载', '/receiptInfo/load', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512474687938561', '1', '收货信息管理', '设置默认收货信息', '/receiptInfo/setIsDefault', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512474704715777', '0', '收货信息管理', '收货信息修改', '/receiptInfo/update', '2018-06-13 09:07:42');
INSERT INTO `permission_tb` VALUES ('1006512474717298690', '1', '角色管理', '角色增加', '/role/add', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512474759241729', '0', '角色管理', '角色数量', '/role/count', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512474796990465', '1', '角色管理', '角色删除', '/role/delete', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512474826350594', '0', '角色管理', '角色列表', '/role/list', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512474872487938', '0', '角色管理', '角色单个加载', '/role/load', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512474906042369', '1', '角色管理', '角色修改', '/role/update', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512474935402497', '1', '角色权限管理', '角色权限增加', '/rolePermission/add', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512474960568321', '0', '角色权限管理', '角色权限数量', '/rolePermission/count', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512474985734146', '1', '角色权限管理', '角色权限删除', '/rolePermission/delete', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512475010899969', '0', '角色权限管理', '角色权限列表', '/rolePermission/list', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512475040260097', '0', '角色权限管理', '角色权限单个加载', '/rolePermission/load', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512475065425922', '1', '角色权限管理', '角色权限修改', '/rolePermission/update', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512475090591745', '1', '签到管理', '签到增加', '/sign/add', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512475119951874', '0', '签到管理', '签到数量', '/sign/count', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512475174477825', '1', '签到管理', '签到删除', '/sign/delete', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512475199643649', '0', '签到管理', '签到列表', '/sign/list', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512475229003777', '0', '签到管理', '签到单个加载', '/sign/load', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512475283529729', '0', '签到管理', '签到单个加载', '/sign/sign', '2018-06-13 09:02:33');
INSERT INTO `permission_tb` VALUES ('1006512475317084161', '1', '签到管理', '签到修改', '/sign/update', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512475346444290', '1', '签到奖品管理', '签到奖品增加', '/signPrize/add', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512475371610114', '0', '签到奖品管理', '签到奖品申请领取', '/signPrize/apply', '2018-06-13 09:03:51');
INSERT INTO `permission_tb` VALUES ('1006512475392581633', '0', '签到奖品管理', '签到奖品数量', '/signPrize/count', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512475409358849', '1', '签到奖品管理', '签到奖品删除', '/signPrize/delete', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512475434524674', '0', '签到奖品管理', '签到奖品列表', '/signPrize/list', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512475468079105', '0', '签到奖品管理', '签到奖品单个加载', '/signPrize/load', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512475493244929', '1', '签到奖品管理', '签到奖品修改', '/signPrize/update', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512475526799361', '1', '签到记录管理', '签到记录增加', '/signRecord/add', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512475551965185', '0', '签到记录管理', '签到记录数量', '/signRecord/count', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512475581325314', '1', '签到记录管理', '签到记录删除', '/signRecord/delete', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512475602296833', '0', '签到记录管理', '签到记录列表', '/signRecord/list', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512475619074049', '0', '签到记录管理', '签到记录单个加载', '/signRecord/load', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512475644239874', '1', '签到记录管理', '签到记录修改', '/signRecord/update', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512475669405698', '1', '公众号管理', '公众号增加', '/subscription/add', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512475694571521', '0', '公众号管理', '公众号数量', '/subscription/count', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512475719737346', '1', '公众号管理', '公众号删除', '/subscription/delete', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512475740708865', '0', '公众号管理', '公众号列表', '/subscription/list', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512475761680386', '0', '公众号管理', '公众号单个加载', '/subscription/load', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512475786846209', '1', '公众号管理', '公众号修改', '/subscription/update', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512475807817730', '1', '模板消息管理', '模板消息增加', '/templateMessage/add', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512475841372161', '0', '模板消息管理', '模板消息数量', '/templateMessage/count', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512475870732290', '1', '模板消息管理', '模板消息删除', '/templateMessage/delete', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512475891703809', '0', '模板消息管理', '模板消息列表', '/templateMessage/list', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512475921063938', '0', '模板消息管理', '模板消息单个加载', '/templateMessage/load', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512475942035457', '1', '模板消息管理', '模板消息修改', '/templateMessage/update', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512475963006978', '1', '第三方信息管理', '第三方信息增加', '/thirdInfo/add', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512475979784194', '0', '第三方信息管理', '第三方信息数量', '/thirdInfo/count', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512476004950018', '1', '第三方信息管理', '第三方信息删除', '/thirdInfo/delete', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512476030115842', '0', '第三方信息管理', '第三方信息列表', '/thirdInfo/list', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512476055281666', '0', '第三方信息管理', '第三方信息单个加载', '/thirdInfo/load', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512476084641794', '1', '第三方信息管理', '第三方信息修改', '/thirdInfo/update', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512476114001921', '0', '工具接口管理', 'addFile', '/tool/file/add', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512476134973441', '0', '工具接口管理', 'getSession', '/tool/getSession', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512476160139266', '0', '工具接口管理', '验证码', '/tool/getVerificationCode', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006512476176916482', '0', '工具接口管理', 'addAdvertiseImg', '/tool/img/add', '2018-06-12 20:24:09');
INSERT INTO `permission_tb` VALUES ('1006706997846245378', '0', '收货信息管理', 'openid收货信息增加', '/receiptInfo/openidAdd', '2018-06-13 09:18:47');
INSERT INTO `permission_tb` VALUES ('1007227378571661314', '1', '微信公众号接口管理', '微信菜单创建', '/weixin/menu/create', '2018-06-14 19:44:55');
INSERT INTO `permission_tb` VALUES ('1007227378714267649', '1', '微信公众号接口管理', '删除个性化菜单接口', '/weixin/menu/delete', '2018-06-14 19:44:55');
INSERT INTO `permission_tb` VALUES ('1007227378814930945', '1', '微信公众号接口管理', '自定义菜单查询接口', '/weixin/menu/get', '2018-06-14 19:44:55');
INSERT INTO `permission_tb` VALUES ('1007227378915594241', '1', '微信公众号接口管理', '获取自定义菜单配置接口', '/weixin/menu/getSelfMenuInfo', '2018-06-14 19:44:55');
INSERT INTO `permission_tb` VALUES ('1007227378991091713', '1', '微信公众号接口管理', '测试个性化菜单匹配结果', '/weixin/menu/menuTryMatch', '2018-06-14 19:44:55');
INSERT INTO `permission_tb` VALUES ('1007227379041423361', '0', '微信公众号接口管理', '微信服务门户，接受xml', '/weixin/portal/**', '2018-06-14 20:28:47');
INSERT INTO `permission_tb` VALUES ('1007589552649388033', '1', '模板消息管理', '模板消息群发', '/templateMessage/sendTemplateMessage', '2018-06-15 19:44:04');
INSERT INTO `permission_tb` VALUES ('1007589552997515266', '0', '微信公众号接口管理', '微信菜单创建', '/weixin/menu/testcreate', '2018-06-19 10:06:26');
INSERT INTO `permission_tb` VALUES ('1017656273225105410', '1', '工作计划管理', '工作计划增加', '/scheduleJob/add', '2018-07-13 14:25:37');
INSERT INTO `permission_tb` VALUES ('1017656273304797185', '0', '工作计划管理', '工作计划数量', '/scheduleJob/count', '2018-07-13 14:25:37');
INSERT INTO `permission_tb` VALUES ('1017656273321574401', '1', '工作计划管理', '工作计划删除', '/scheduleJob/delete', '2018-07-13 14:25:37');
INSERT INTO `permission_tb` VALUES ('1017656273321574402', '0', '工作计划管理', '工作计划列表', '/scheduleJob/list', '2018-07-13 14:25:37');
INSERT INTO `permission_tb` VALUES ('1017656273376100354', '0', '工作计划管理', '工作计划列表（quartz内存中）', '/scheduleJob/listquartz', '2018-07-13 14:25:37');
INSERT INTO `permission_tb` VALUES ('1017656273376100355', '0', '工作计划管理', '工作计划单个加载', '/scheduleJob/load', '2018-07-13 14:25:37');
INSERT INTO `permission_tb` VALUES ('1017656273376100356', '1', '工作计划管理', '工作计划列表', '/scheduleJob/turn', '2018-07-13 14:25:37');
INSERT INTO `permission_tb` VALUES ('1017656273376100357', '1', '工作计划管理', '工作计划修改', '/scheduleJob/update', '2018-07-13 14:25:37');
INSERT INTO `permission_tb` VALUES ('1061904263081562114', '0', '工具接口管理', 'test', '/tool/test', '2018-11-12 16:51:20');
INSERT INTO `permission_tb` VALUES ('1062219928753582082', '0', '模板消息管理', '模板个人消息发送', '/templateMessage/sendSingleTemplateMessage', '2018-11-13 13:52:24');
INSERT INTO `permission_tb` VALUES ('1062219928996851714', '0', '工具接口管理', '生成二维码', '/tool/getQrCode', '2018-11-13 13:45:41');
INSERT INTO `permission_tb` VALUES ('1062219929080737793', '0', '微信公众号接口管理', '微信js授权访问', '/weixin/authorize', '2018-11-13 13:45:41');
INSERT INTO `permission_tb` VALUES ('1062219929303035905', '0', '微信公众号接口管理', '微信jssdk 接口', '/weixin/js/connection', '2018-11-13 13:45:41');
INSERT INTO `permission_tb` VALUES ('1062219929466613761', '0', '微信公众号接口管理', '微信登录获取openid', '/weixin/openid', '2018-11-13 13:45:41');

-- ----------------------------
-- Table structure for `prize_tb`
-- ----------------------------
DROP TABLE IF EXISTS `prize_tb`;
CREATE TABLE `prize_tb` (
  `prize_id` bigint(20) NOT NULL COMMENT '奖品id',
  `day_number` int(11) DEFAULT '0' COMMENT '连续天数',
  `name` varchar(255) DEFAULT NULL COMMENT '奖品名称',
  `number` int(11) DEFAULT '0' COMMENT '奖品数量',
  `img_address` varchar(255) DEFAULT NULL COMMENT '奖品图标',
  `content` longtext COMMENT '奖品内容',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `subscription_id` bigint(20) DEFAULT NULL COMMENT '公众号id',
  `account_id` bigint(20) DEFAULT NULL COMMENT '创建奖品账户id',
  PRIMARY KEY (`prize_id`),
  KEY `INDEX_DAYNUMBER` (`day_number`) USING BTREE,
  KEY `INDEX_SUBSCRIPTIONID` (`subscription_id`) USING BTREE,
  KEY `INDEX_ACCOUNTID` (`account_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='奖品表';

-- ----------------------------
-- Records of prize_tb
-- ----------------------------
INSERT INTO `prize_tb` VALUES ('1005288439059197954', '3', '11', '1', 'http://ccsd.boya1.cn/uploaderPath/img/20180618/1529326361160.png', '<p>sdf444gfgdfgfdgdfggdfgdgdfgdfdfgdfgdfg</p>', '2018-06-09 11:20:16', '2018-10-31 13:37:55', null, '1000');

-- ----------------------------
-- Table structure for `receipt_info_tb`
-- ----------------------------
DROP TABLE IF EXISTS `receipt_info_tb`;
CREATE TABLE `receipt_info_tb` (
  `receipt_info_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '收货信息id',
  `name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `phone` varchar(255) DEFAULT NULL COMMENT '手机号',
  `province` varchar(255) DEFAULT NULL COMMENT '省',
  `city` varchar(255) DEFAULT NULL COMMENT '市',
  `area` varchar(255) DEFAULT NULL COMMENT '区',
  `address` varchar(255) DEFAULT NULL COMMENT '收货地址',
  `is_default` tinyint(4) DEFAULT '0' COMMENT '默认为0不是，1是',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `openid` varchar(255) DEFAULT NULL COMMENT '微信openid,外键',
  `account_id` bigint(20) DEFAULT NULL COMMENT '账户id,外键',
  PRIMARY KEY (`receipt_info_id`),
  KEY `INDEX_OPENID` (`openid`) USING BTREE,
  KEY `INDEX_ACCOUNTID` (`account_id`) USING BTREE,
  KEY `INDEX_ISDEFAULT` (`is_default`) USING BTREE,
  KEY `INDEX_CREATEDATE` (`create_date`) USING BTREE,
  KEY `INDEX_UPDATEDATE` (`update_date`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1009614924551249923 DEFAULT CHARSET=utf8 COMMENT='收货地址表 ';

-- ----------------------------
-- Records of receipt_info_tb
-- ----------------------------
INSERT INTO `receipt_info_tb` VALUES ('1009614924551249922', '聂跃', '15111336587', '湖南省', '长沙市', '岳麓区', '麓谷企业广场e8栋3楼', '0', '2018-06-21 09:52:10', '2018-06-21 09:52:10', 'ojmGi0hq5rZDY4PU_qCb11i4_wxI', null);

-- ----------------------------
-- Table structure for `role_permission_tb`
-- ----------------------------
DROP TABLE IF EXISTS `role_permission_tb`;
CREATE TABLE `role_permission_tb` (
  `role_permission_id` bigint(20) NOT NULL COMMENT '角色权限id',
  `region` tinyint(4) DEFAULT NULL COMMENT '范围，1公共，2自身',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id,外键',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `permission_id` bigint(20) DEFAULT NULL COMMENT '权限id,外键',
  PRIMARY KEY (`role_permission_id`),
  UNIQUE KEY `UNIQUE_ROLEID_PERMISSIONID` (`role_id`,`permission_id`) USING BTREE,
  KEY `INDEX_REGION` (`region`) USING BTREE,
  KEY `INDEX_ROLEID` (`role_id`) USING BTREE,
  KEY `INDEX_PERMISSIONID` (`permission_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限表';

-- ----------------------------
-- Records of role_permission_tb
-- ----------------------------
INSERT INTO `role_permission_tb` VALUES ('1062225658722299905', '1', '1001', '2018-11-13 14:08:27', '1017656273376100357');
INSERT INTO `role_permission_tb` VALUES ('1062225659011706881', '1', '1001', '2018-11-13 14:08:27', '1017656273376100356');
INSERT INTO `role_permission_tb` VALUES ('1062225659443720194', '1', '1001', '2018-11-13 14:08:27', '1017656273321574401');
INSERT INTO `role_permission_tb` VALUES ('1062225659854761985', '1', '1001', '2018-11-13 14:08:27', '1017656273225105410');
INSERT INTO `role_permission_tb` VALUES ('1062225660303552513', '1', '1001', '2018-11-13 14:08:27', '1007589552649388033');
INSERT INTO `role_permission_tb` VALUES ('1062225660697817090', '1', '1001', '2018-11-13 14:08:27', '1007227378991091713');
INSERT INTO `role_permission_tb` VALUES ('1062225661125636098', '1', '1001', '2018-11-13 14:08:27', '1007227378915594241');
INSERT INTO `role_permission_tb` VALUES ('1062225661545066498', '1', '1001', '2018-11-13 14:08:28', '1007227378814930945');
INSERT INTO `role_permission_tb` VALUES ('1062225662023217153', '1', '1001', '2018-11-13 14:08:28', '1007227378714267649');
INSERT INTO `role_permission_tb` VALUES ('1062225662392315905', '1', '1001', '2018-11-13 14:08:28', '1007227378571661314');
INSERT INTO `role_permission_tb` VALUES ('1062225662870466561', '1', '1001', '2018-11-13 14:08:28', '1006512476084641794');
INSERT INTO `role_permission_tb` VALUES ('1062225663176650753', '1', '1001', '2018-11-13 14:08:28', '1006512476004950018');
INSERT INTO `role_permission_tb` VALUES ('1062225663625441282', '1', '1001', '2018-11-13 14:08:28', '1006512475963006978');
INSERT INTO `role_permission_tb` VALUES ('1062225664036483074', '1', '1001', '2018-11-13 14:08:28', '1006512475942035457');
INSERT INTO `role_permission_tb` VALUES ('1062225664447524866', '1', '1001', '2018-11-13 14:08:28', '1006512475870732290');
INSERT INTO `role_permission_tb` VALUES ('1062225664887926786', '1', '1001', '2018-11-13 14:08:28', '1006512475807817730');
INSERT INTO `role_permission_tb` VALUES ('1062225665311551490', '1', '1001', '2018-11-13 14:08:28', '1006512475786846209');
INSERT INTO `role_permission_tb` VALUES ('1062225665710010370', '1', '1001', '2018-11-13 14:08:29', '1006512475719737346');
INSERT INTO `role_permission_tb` VALUES ('1062225666150412290', '1', '1001', '2018-11-13 14:08:29', '1006512475669405698');
INSERT INTO `role_permission_tb` VALUES ('1062225666553065473', '1', '1001', '2018-11-13 14:08:29', '1006512475644239874');
INSERT INTO `role_permission_tb` VALUES ('1062225667027021825', '1', '1001', '2018-11-13 14:08:29', '1006512475581325314');
INSERT INTO `role_permission_tb` VALUES ('1062225667391926274', '1', '1001', '2018-11-13 14:08:29', '1006512475526799361');
INSERT INTO `role_permission_tb` VALUES ('1062225667811356674', '1', '1001', '2018-11-13 14:08:29', '1006512475493244929');
INSERT INTO `role_permission_tb` VALUES ('1062225668226592769', '1', '1001', '2018-11-13 14:08:29', '1006512475409358849');
INSERT INTO `role_permission_tb` VALUES ('1062225668666994690', '1', '1001', '2018-11-13 14:08:29', '1006512475346444290');
INSERT INTO `role_permission_tb` VALUES ('1062225669069647873', '1', '1001', '2018-11-13 14:08:29', '1006512475317084161');
INSERT INTO `role_permission_tb` VALUES ('1062225669480689665', '1', '1001', '2018-11-13 14:08:29', '1006512475174477825');
INSERT INTO `role_permission_tb` VALUES ('1062225669925285889', '1', '1001', '2018-11-13 14:08:30', '1006512475090591745');
INSERT INTO `role_permission_tb` VALUES ('1062225670319550465', '1', '1001', '2018-11-13 14:08:30', '1006512475065425922');
INSERT INTO `role_permission_tb` VALUES ('1062225670734786561', '1', '1001', '2018-11-13 14:08:30', '1006512474985734146');
INSERT INTO `role_permission_tb` VALUES ('1062225671179382785', '1', '1001', '2018-11-13 14:08:30', '1006512474935402497');
INSERT INTO `role_permission_tb` VALUES ('1062225671582035970', '1', '1001', '2018-11-13 14:08:30', '1006512474906042369');
INSERT INTO `role_permission_tb` VALUES ('1062225672001466369', '1', '1001', '2018-11-13 14:08:30', '1006512474796990465');
INSERT INTO `role_permission_tb` VALUES ('1062225672420896770', '1', '1001', '2018-11-13 14:08:30', '1006512474717298690');
INSERT INTO `role_permission_tb` VALUES ('1062225672848715777', '1', '1001', '2018-11-13 14:08:30', '1006512474687938561');
INSERT INTO `role_permission_tb` VALUES ('1062225673263951873', '1', '1001', '2018-11-13 14:08:30', '1006512474578886658');
INSERT INTO `role_permission_tb` VALUES ('1062225673674993666', '1', '1001', '2018-11-13 14:08:30', '1006512474528555010');
INSERT INTO `role_permission_tb` VALUES ('1062225674111201281', '1', '1001', '2018-11-13 14:08:31', '1006512474486611970');
INSERT INTO `role_permission_tb` VALUES ('1062225674555797505', '1', '1001', '2018-11-13 14:08:31', '1006512474398531586');
INSERT INTO `role_permission_tb` VALUES ('1062225674941673474', '1', '1001', '2018-11-13 14:08:31', '1006512474327228418');
INSERT INTO `role_permission_tb` VALUES ('1062225675356909570', '1', '1001', '2018-11-13 14:08:31', '1006512474293673986');
INSERT INTO `role_permission_tb` VALUES ('1062225675788922881', '1', '1001', '2018-11-13 14:08:31', '1006512474197204994');
INSERT INTO `role_permission_tb` VALUES ('1062225676204158978', '1', '1001', '2018-11-13 14:08:31', '1006512474151067650');
INSERT INTO `role_permission_tb` VALUES ('1062225676619395074', '1', '1001', '2018-11-13 14:08:31', '1006512474109124610');
INSERT INTO `role_permission_tb` VALUES ('1062225677093351426', '1', '1001', '2018-11-13 14:08:31', '1006512474079764482');
INSERT INTO `role_permission_tb` VALUES ('1062225677454061570', '1', '1001', '2018-11-13 14:08:31', '1006512474016849921');
INSERT INTO `role_permission_tb` VALUES ('1062225677865103362', '1', '1001', '2018-11-13 14:08:31', '1006512473874243586');
INSERT INTO `role_permission_tb` VALUES ('1062225678309699585', '1', '1001', '2018-11-13 14:08:32', '1006512473794551810');
INSERT INTO `role_permission_tb` VALUES ('1062225678724935682', '1', '1001', '2018-11-13 14:08:32', '1006512473773580290');
INSERT INTO `role_permission_tb` VALUES ('1062225679131783169', '1', '1001', '2018-11-13 14:08:32', '1006512473672916993');
INSERT INTO `role_permission_tb` VALUES ('1062225679572185089', '1', '1001', '2018-11-13 14:08:32', '1006512473597419522');
INSERT INTO `role_permission_tb` VALUES ('1062225679991615489', '1', '1001', '2018-11-13 14:08:32', '1006512473509339137');
INSERT INTO `role_permission_tb` VALUES ('1062225680406851586', '1', '1001', '2018-11-13 14:08:32', '1006512473475784706');
INSERT INTO `role_permission_tb` VALUES ('1062225680817893378', '1', '1001', '2018-11-13 14:08:32', '1006512473438035970');
INSERT INTO `role_permission_tb` VALUES ('1062225681249906690', '1', '1001', '2018-11-13 14:08:32', '1006512473391898625');
INSERT INTO `role_permission_tb` VALUES ('1062225681753223170', '1', '1001', '2018-11-13 14:08:32', '1006512473144434690');
INSERT INTO `role_permission_tb` VALUES ('1062225682105544706', '1', '1001', '2018-11-13 14:08:32', '1006512473031188482');
INSERT INTO `role_permission_tb` VALUES ('1062225682512392194', '1', '1001', '2018-11-13 14:08:33', '1006512472989245441');
INSERT INTO `role_permission_tb` VALUES ('1062225682931822594', '1', '1001', '2018-11-13 14:08:33', '1006512472938913794');
INSERT INTO `role_permission_tb` VALUES ('1062225683351252993', '1', '1001', '2018-11-13 14:08:33', '1006512472821473282');
INSERT INTO `role_permission_tb` VALUES ('1062225683766489089', '1', '1001', '2018-11-13 14:08:33', '1006512472683061250');

-- ----------------------------
-- Table structure for `role_tb`
-- ----------------------------
DROP TABLE IF EXISTS `role_tb`;
CREATE TABLE `role_tb` (
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `name` varchar(255) DEFAULT NULL COMMENT '角色名',
  `duty` varchar(255) DEFAULT NULL COMMENT '角色职责',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of role_tb
-- ----------------------------
INSERT INTO `role_tb` VALUES ('1000', '超级管理员', '超级管理员', '2018-06-06 22:33:31');
INSERT INTO `role_tb` VALUES ('1001', '普通管理员', '普通管理员', '2018-06-06 22:33:32');
INSERT INTO `role_tb` VALUES ('1002', '商户', '商户', '2018-06-06 22:33:32');
INSERT INTO `role_tb` VALUES ('1003', '推广户', '推广户', '2018-06-06 22:33:32');
INSERT INTO `role_tb` VALUES ('1004', '用户', '用户', '2018-06-06 22:33:32');

-- ----------------------------
-- Table structure for `schedule_job_tb`
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job_tb`;
CREATE TABLE `schedule_job_tb` (
  `schedule_job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '计划任务id',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `job_name` varchar(255) DEFAULT NULL COMMENT '任务名称',
  `job_group` varchar(255) DEFAULT NULL COMMENT '任务分组',
  `job_status` varchar(255) DEFAULT NULL COMMENT '任务状态,PAUSED暂停，NORMAL正常',
  `cron_expression` varchar(255) DEFAULT NULL COMMENT 'cron表达式',
  `description` varchar(255) DEFAULT NULL COMMENT '描述 ',
  `job_id` bigint(20) DEFAULT NULL COMMENT '任务Id ',
  `type` tinyint(4) DEFAULT NULL COMMENT '任务类型1.文章推送2.书籍推荐',
  `method_name` varchar(255) DEFAULT NULL COMMENT '任务调用的方法名',
  PRIMARY KEY (`schedule_job_id`),
  KEY `INDEX_JOBID` (`job_id`) USING BTREE,
  KEY `INDEX_TYPE` (`type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1045 DEFAULT CHARSET=utf8 COMMENT='计划任务表';

-- ----------------------------
-- Records of schedule_job_tb
-- ----------------------------
INSERT INTO `schedule_job_tb` VALUES ('1000', '2018-07-13 18:20:50', '2018-07-15 23:19:56', 'com.nieyue.schedule.QuartzJob', '1523c8af-5724-46d6-a84b-c5b5df0a2497', 'NORMAL', '0  1 * * * ?', 'dfgdfg', '1005818121710104577', '1', 'execute');
INSERT INTO `schedule_job_tb` VALUES ('1001', '2018-07-16 10:00:48', '2018-07-16 10:02:15', 'com.nieyue.schedule.QuartzJob', '45ddc9d0-2fef-4f9c-a0d2-c3456f054238', 'PAUSED', '0 2 * * * ?', 'lpplkl', '1005821604970524673', '1', 'execute');
INSERT INTO `schedule_job_tb` VALUES ('1005', '2018-07-17 18:53:10', '2018-07-23 18:36:01', 'com.nieyue.schedule.QuartzJob', 'b0637a52-8491-4358-b5ef-250c9d38adf2', 'NORMAL', '16 23 22 23 07 ? 2018', '', '1015174716529516546', '1', 'execute');
INSERT INTO `schedule_job_tb` VALUES ('1006', '2018-07-23 17:38:33', '2018-07-23 17:39:04', 'com.nieyue.schedule.QuartzJob', '59c007f2-33bf-4efb-aa79-f90119aee8d4', 'NORMAL', '00 21 21 23 07 ? 2018', '7.23客服消息', '1021232092877615106', '1', 'execute');
INSERT INTO `schedule_job_tb` VALUES ('1007', '2018-07-23 17:40:06', '2018-07-23 17:40:11', 'com.nieyue.schedule.QuartzJob', 'e1ebc3ac-5199-42cd-9665-d127475b351a', 'NORMAL', '00 22 22 23 07 ? 2018', '7.23客服消息\n', '1021236047217094657', '1', 'execute');
INSERT INTO `schedule_job_tb` VALUES ('1009', '2018-07-24 22:20:09', '2018-07-24 22:20:09', 'com.nieyue.schedule.QuartzJob', '83d5cb70-a94c-4b0f-8270-5e2c6587a2f6', 'NORMAL', '03 03 23 24 07 ? 2018', null, '1021760367790059522', '1', 'execute');
INSERT INTO `schedule_job_tb` VALUES ('1012', '2018-07-26 23:03:55', '2018-07-26 23:03:55', 'com.nieyue.schedule.QuartzJob', 'fca39430-aee7-4218-9860-904413ee15f8', 'NORMAL', '09 33 7 27 07 ? 2018', null, '1022111636392177666', '1', 'execute');
INSERT INTO `schedule_job_tb` VALUES ('1013', '2018-07-29 11:10:43', '2018-07-29 11:10:43', 'com.nieyue.schedule.QuartzJob', 'c1fafdd5-b438-49ba-b8d7-b6b318301eb3', 'NORMAL', '00 21 21 29 07 ? 2018', '7.29客服推送', '1022750554808578049', '1', 'execute');
INSERT INTO `schedule_job_tb` VALUES ('1015', '2018-07-30 10:44:32', '2018-07-30 10:44:32', 'com.nieyue.schedule.QuartzJob', '70f75421-b68e-4200-b28d-a5eba6e2297f', 'NORMAL', '18 18 18 30 07 ? 2018', null, '1015979933839859714', '1', 'execute');
INSERT INTO `schedule_job_tb` VALUES ('1016', '2018-07-30 11:49:19', '2018-07-30 11:49:19', 'com.nieyue.schedule.QuartzJob', '3ddd532b-a8fc-4c65-bfed-2ff0c6d585bc', 'NORMAL', '18 33 22 30 07 ? 2018', null, '1023775972911149058', '1', 'execute');
INSERT INTO `schedule_job_tb` VALUES ('1017', '2018-07-30 15:37:46', '2018-07-30 15:37:46', 'com.nieyue.schedule.QuartzJob', '6d9a5cf0-eb50-471c-947d-37ede953b252', 'NORMAL', '00 21 21 30 07 ? 2018', '7.30推送', '1023834366862880770', '1', 'execute');
INSERT INTO `schedule_job_tb` VALUES ('1018', '2018-07-31 14:53:42', '2018-07-31 14:53:42', 'com.nieyue.schedule.QuartzJob', 'cea24067-3f2e-4983-9c5b-d892a818fae9', 'NORMAL', '08 08 22 31 07 ? 2018', null, '1024185526769610753', '1', 'execute');
INSERT INTO `schedule_job_tb` VALUES ('1019', '2018-07-31 15:10:53', '2018-07-31 15:10:53', 'com.nieyue.schedule.QuartzJob', '90e3f2ba-6463-4fb6-9752-18263b7e3b21', 'NORMAL', '18 18 18 31 07 ? 2018', null, '1024189952041349121', '1', 'execute');
INSERT INTO `schedule_job_tb` VALUES ('1020', '2018-07-31 15:32:59', '2018-07-31 15:32:59', 'com.nieyue.schedule.QuartzJob', '12ecaea0-c5f7-43c5-889e-90c2d93abfab', 'NORMAL', '00 21 21 31 07 ? 2018', '7.31推送', '1024195433317199874', '1', 'execute');
INSERT INTO `schedule_job_tb` VALUES ('1021', '2018-08-01 14:06:27', '2018-08-01 14:06:27', 'com.nieyue.schedule.QuartzJob', '160a8ad0-a860-496c-a990-7a4de43e912b', 'NORMAL', '00 21 21 1 08 ? 2018', '8.1客服消息', '1024534807783337985', '1', 'execute');
INSERT INTO `schedule_job_tb` VALUES ('1022', '2018-08-08 11:55:22', '2018-08-08 11:55:22', 'com.nieyue.schedule.QuartzJob', 'd7f64d05-5fb6-4838-8946-92ee502b856f', 'NORMAL', '00 00 22 8 08 ? 2018', '8.8客服推送', '1027038652186947586', '1', 'execute');
INSERT INTO `schedule_job_tb` VALUES ('1023', '2018-08-13 18:06:51', '2018-08-13 18:06:51', 'com.nieyue.schedule.QuartzJob', 'cf5e7b37-215b-4e7c-9a4c-df832dc9be9a', 'NORMAL', '00 10 21 13 08 ? 2018', '8.13', '1028945374849724417', '1', 'execute');
INSERT INTO `schedule_job_tb` VALUES ('1024', '2018-08-14 15:41:25', '2018-08-14 15:41:25', 'com.nieyue.schedule.QuartzJob', 'e26e9338-d72e-436b-8b16-806bd2ff1b57', 'NORMAL', '00 20 21 14 08 ? 2018', '8.14', '1029268922994716673', '1', 'execute');
INSERT INTO `schedule_job_tb` VALUES ('1025', '2018-08-15 18:04:52', '2018-08-15 18:04:52', 'com.nieyue.schedule.QuartzJob', 'b8de0c6d-7ca8-4817-9b31-7365fb6bc9fe', 'NORMAL', '00 20 21 15 08 ? 2018', '8.15', '1029661980760403969', '1', 'execute');
INSERT INTO `schedule_job_tb` VALUES ('1026', '2018-08-16 17:52:34', '2018-08-16 17:52:34', 'com.nieyue.schedule.QuartzJob', '7ff32447-6a68-4cab-b815-23a1c77c73af', 'NORMAL', '00 20 21 16 08 ? 2018', '8.16', '1030020141530935298', '1', 'execute');
INSERT INTO `schedule_job_tb` VALUES ('1027', '2018-08-20 16:04:05', '2018-08-20 16:04:05', 'com.nieyue.schedule.QuartzJob', '341e1dbb-3aa3-415d-aa8c-5dcf1d2b012d', 'NORMAL', '00 15 22 20 08 ? 2018', null, '1031451113442963457', '1', 'execute');
INSERT INTO `schedule_job_tb` VALUES ('1028', '2018-08-21 15:44:19', '2018-08-21 15:44:19', 'com.nieyue.schedule.QuartzJob', 'c0c5d907-b71b-46d1-b325-cdeeecfa89c3', 'NORMAL', '00 20 22 21 08 ? 2018', null, '1031796160575238145', '1', 'execute');
INSERT INTO `schedule_job_tb` VALUES ('1029', '2018-08-22 16:04:50', '2018-08-22 16:04:50', 'com.nieyue.schedule.QuartzJob', 'e48f1388-8490-4600-9df7-2e16753777ca', 'NORMAL', '00 15 22 22 08 ? 2018', '8.22', '1032176047618191362', '1', 'execute');
INSERT INTO `schedule_job_tb` VALUES ('1030', '2018-08-27 16:45:02', '2018-08-27 16:45:02', 'com.nieyue.schedule.QuartzJob', '4b6f412b-69cb-427a-bd4c-0d6a52ea0844', 'NORMAL', '00 15 22 27 08 ? 2018', '8.27', '1033997802531909634', '1', 'execute');
INSERT INTO `schedule_job_tb` VALUES ('1031', '2018-08-28 16:41:03', '2018-08-28 16:41:03', 'com.nieyue.schedule.QuartzJob', '1778dc56-bb6c-4427-9528-200f1b722f07', 'NORMAL', '00 07 22 28 08 ? 2018', '', '1034359377172295682', '1', 'execute');
INSERT INTO `schedule_job_tb` VALUES ('1032', '2018-08-30 17:31:43', '2018-08-30 17:31:43', 'com.nieyue.schedule.QuartzJob', '2aea7ca2-d621-4b1b-b8d7-4c15c3c861fe', 'NORMAL', '00 22 21 30 08 ? 2018', '8.30\n', '1035097017576255490', '1', 'execute');
INSERT INTO `schedule_job_tb` VALUES ('1033', '2018-09-03 10:54:19', '2018-09-03 10:54:19', 'com.nieyue.schedule.QuartzJob', '88284258-0127-4024-b526-001b6f5130fb', 'NORMAL', '00 20 22 3 09 ? 2018', null, '1036443784481796098', '1', 'execute');
INSERT INTO `schedule_job_tb` VALUES ('1034', '2018-09-05 11:45:01', '2018-09-05 11:45:46', 'com.nieyue.schedule.QuartzJob', 'd9bf24c7-6737-4f7d-aa4a-9c8e39b7c07b', 'NORMAL', '00 18 22 5 09 ? 2018', '', '1037179865577746434', '1', 'execute');
INSERT INTO `schedule_job_tb` VALUES ('1036', '2018-09-12 15:26:50', '2018-09-12 15:26:50', 'com.nieyue.schedule.QuartzJob', '7d4f9f2b-2f83-474e-8343-9c6d95c3837f', 'NORMAL', '00 07 22 12 09 ? 2018', null, '1039056939036897282', '1', 'execute');
INSERT INTO `schedule_job_tb` VALUES ('1038', '2018-09-18 15:40:56', '2018-09-18 15:40:56', 'com.nieyue.schedule.QuartzJob', '8afb676b-82fb-4d46-9ddf-0991ca24e7ae', 'NORMAL', '00 24 22 18 09 ? 2018', null, '1041567526363000833', '1', 'execute');
INSERT INTO `schedule_job_tb` VALUES ('1039', '2018-09-20 16:36:32', '2018-09-20 16:36:32', 'com.nieyue.schedule.QuartzJob', 'e747c626-547c-4994-8b9d-2d4d42054d85', 'NORMAL', '00 17 22 20 09 ? 2018', null, '1042693515809845249', '1', 'execute');
INSERT INTO `schedule_job_tb` VALUES ('1040', '2018-09-25 16:42:15', '2018-09-25 16:42:15', 'com.nieyue.schedule.QuartzJob', '953a044a-0de5-4c14-82b3-b543f17c17e6', 'NORMAL', '00 06 22 25 09 ? 2018', null, '1044504616767778817', '1', 'execute');
INSERT INTO `schedule_job_tb` VALUES ('1041', '2018-09-27 15:52:39', '2018-09-27 15:52:39', 'com.nieyue.schedule.QuartzJob', 'b89a9e84-0494-4195-8d7c-f41f4e2659f2', 'NORMAL', '00 25 22 27 09 ? 2018', null, '1045218106650324994', '1', 'execute');
INSERT INTO `schedule_job_tb` VALUES ('1042', '2018-10-09 10:10:37', '2018-10-09 10:10:37', 'com.nieyue.schedule.QuartzJob', '1d25ad1f-f92c-449a-b424-56e45da6d924', 'NORMAL', '00 03 22 9 10 ? 2018', null, '1049474052960481282', '1', 'execute');
INSERT INTO `schedule_job_tb` VALUES ('1043', '2018-10-09 14:00:25', '2018-10-09 14:00:25', 'com.nieyue.schedule.QuartzJob', '331cde8f-7b5e-48f4-81dd-7d90515224c2', 'NORMAL', '00 04 22 10 10 ? 2018', null, '1049474052960481282', '1', 'execute');
INSERT INTO `schedule_job_tb` VALUES ('1044', '2018-10-17 16:22:18', '2018-10-17 16:22:18', 'com.nieyue.schedule.QuartzJob', 'd68cac5c-19a9-4003-9626-0805f03acf32', 'NORMAL', '00 06 22 17 10 ? 2018', null, '1052474497974865922', '1', 'execute');

-- ----------------------------
-- Table structure for `sign_prize_tb`
-- ----------------------------
DROP TABLE IF EXISTS `sign_prize_tb`;
CREATE TABLE `sign_prize_tb` (
  `sign_prize_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '签到奖品id',
  `day_number` int(11) DEFAULT '0' COMMENT '连续天数',
  `name` varchar(255) DEFAULT NULL COMMENT '奖品名称',
  `number` int(11) DEFAULT '0' COMMENT '奖品数量',
  `img_address` varchar(255) DEFAULT NULL COMMENT '奖品图标',
  `content` longtext COMMENT '奖品内容',
  `prize_date` datetime DEFAULT NULL COMMENT '领奖时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态，1待申请，2申请领奖，3领取成功，4拒绝发送',
  `openid` varchar(255) DEFAULT NULL COMMENT '微信openid',
  `subscription_id` bigint(20) DEFAULT NULL COMMENT '公众号id外键',
  `prize_id` bigint(20) DEFAULT NULL COMMENT '奖品id外键',
  `account_id` bigint(20) DEFAULT NULL COMMENT '领奖人id外键',
  PRIMARY KEY (`sign_prize_id`),
  KEY `INDEX_DAYNUMBER` (`day_number`) USING BTREE,
  KEY `INDEX_STATUS` (`status`) USING BTREE,
  KEY `INDEX_OPENID` (`openid`) USING BTREE,
  KEY `INDEX_SUBSCRIPTIONID` (`subscription_id`) USING BTREE,
  KEY `INDEX_PRIZEID` (`prize_id`) USING BTREE,
  KEY `INDEX_ACCOUNTID` (`account_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1009614299780308995 DEFAULT CHARSET=utf8 COMMENT='签到奖品表';

-- ----------------------------
-- Records of sign_prize_tb
-- ----------------------------
INSERT INTO `sign_prize_tb` VALUES ('1009614299780308994', '3', '10', '1', 'http://ccsd.boya1.cn/uploaderPath/img/20180618/1529326361160.png', '<p>sdf444</p>', '2018-06-21 09:49:41', '2', 'ojmGi0hq5rZDY4PU_qCb11i4_wxI', '1008890688689913858', '1005288439059197954', null);

-- ----------------------------
-- Table structure for `sign_record_tb`
-- ----------------------------
DROP TABLE IF EXISTS `sign_record_tb`;
CREATE TABLE `sign_record_tb` (
  `sign_record_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '签到记录id',
  `integral` decimal(11,2) DEFAULT '0.00' COMMENT '奖励积分',
  `sign_date` datetime DEFAULT NULL COMMENT '签到时间',
  `openid` varchar(255) DEFAULT NULL COMMENT '微信openid',
  `subscription_id` bigint(20) DEFAULT NULL COMMENT '公众号id外键',
  `account_id` bigint(20) DEFAULT NULL COMMENT '任务人id外键',
  PRIMARY KEY (`sign_record_id`),
  KEY `INDEX_OPENID` (`openid`) USING BTREE,
  KEY `INDEX_SUBSCRIPTIONID` (`subscription_id`) USING BTREE,
  KEY `INDEX_ACCOUNTID` (`account_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1009614299704811522 DEFAULT CHARSET=utf8 COMMENT='签到记录表';

-- ----------------------------
-- Records of sign_record_tb
-- ----------------------------
INSERT INTO `sign_record_tb` VALUES ('1009010328191627266', '1.00', '2018-06-19 17:49:43', 'o5DlN1QB0o1kT_zxI5KTsQfqoYDk', '1008952171394220033', null);
INSERT INTO `sign_record_tb` VALUES ('1009010568000958466', '1.00', '2018-06-19 17:50:41', 'o5DlN1aEvcL4z8iq1WCWClBILyog', '1008952171394220033', null);
INSERT INTO `sign_record_tb` VALUES ('1009010600792027138', '1.00', '2018-06-19 17:50:48', 'o5DlN1Q8wTwPfdHCJsY0mtBIZTss', '1008952171394220033', null);
INSERT INTO `sign_record_tb` VALUES ('1009011319167250433', '1.00', '2018-06-19 17:53:40', 'ojmGi0hq5rZDY4PU_qCb11i4_wxI', '1008890688689913858', null);
INSERT INTO `sign_record_tb` VALUES ('1009011443276705794', '1.00', '2018-06-19 17:54:09', 'ojmGi0rNSw6FEP2bdMNucMASX5CE', '1008890688689913858', null);
INSERT INTO `sign_record_tb` VALUES ('1009268148916936705', '1.00', '2018-06-20 10:54:13', 'ojmGi0hq5rZDY4PU_qCb11i4_wxI', '1008890688689913858', null);
INSERT INTO `sign_record_tb` VALUES ('1009614299704811521', '1.00', '2018-06-21 09:49:41', 'ojmGi0hq5rZDY4PU_qCb11i4_wxI', '1008890688689913858', null);

-- ----------------------------
-- Table structure for `sign_tb`
-- ----------------------------
DROP TABLE IF EXISTS `sign_tb`;
CREATE TABLE `sign_tb` (
  `sign_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '签到id',
  `day_number` int(11) DEFAULT '0' COMMENT '连续天数',
  `integral` decimal(11,2) DEFAULT '0.00' COMMENT '总积分',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `openid` varchar(255) DEFAULT NULL COMMENT '微信openid',
  `subscription_id` bigint(20) DEFAULT NULL COMMENT '公众号id外键',
  `account_id` bigint(20) DEFAULT NULL COMMENT '任务人id外键',
  PRIMARY KEY (`sign_id`),
  KEY `INDEX_OPENID` (`openid`) USING BTREE,
  KEY `INDEX_SUBSCRIPTIONID` (`subscription_id`) USING BTREE,
  KEY `INDEX_ACCOUNTID` (`account_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1009011443243151363 DEFAULT CHARSET=utf8 COMMENT='签到表';

-- ----------------------------
-- Records of sign_tb
-- ----------------------------
INSERT INTO `sign_tb` VALUES ('1009010328174850050', '1', '1.00', '2018-06-19 17:49:43', '2018-06-19 17:49:43', 'o5DlN1QB0o1kT_zxI5KTsQfqoYDk', '1008952171394220033', null);
INSERT INTO `sign_tb` VALUES ('1009010567992569857', '1', '1.00', '2018-06-19 17:50:41', '2018-06-19 17:50:41', 'o5DlN1aEvcL4z8iq1WCWClBILyog', '1008952171394220033', null);
INSERT INTO `sign_tb` VALUES ('1009010600792027137', '1', '1.00', '2018-06-19 17:50:48', '2018-06-19 17:50:48', 'o5DlN1Q8wTwPfdHCJsY0mtBIZTss', '1008952171394220033', null);
INSERT INTO `sign_tb` VALUES ('1009011319158861826', '3', '3.00', '2018-06-21 09:49:41', '2018-06-21 09:49:41', 'ojmGi0hq5rZDY4PU_qCb11i4_wxI', '1008890688689913858', null);
INSERT INTO `sign_tb` VALUES ('1009011443243151362', '1', '1.00', '2018-06-19 17:54:09', '2018-06-19 17:54:09', 'ojmGi0rNSw6FEP2bdMNucMASX5CE', '1008890688689913858', null);

-- ----------------------------
-- Table structure for `subscription_tb`
-- ----------------------------
DROP TABLE IF EXISTS `subscription_tb`;
CREATE TABLE `subscription_tb` (
  `subscription_id` bigint(20) NOT NULL COMMENT '公众号id',
  `name` varchar(255) DEFAULT NULL COMMENT '公众号名称',
  `appid` varchar(255) DEFAULT NULL COMMENT '公众号应用id',
  `appsecret` varchar(255) DEFAULT NULL COMMENT '公众号秘钥',
  `token` varchar(255) DEFAULT NULL COMMENT '微信接口令牌',
  `ghid` varchar(255) DEFAULT NULL COMMENT '公众号原始id',
  `img_address` varchar(255) DEFAULT NULL COMMENT '公众号二维码',
  `mchid` varchar(255) DEFAULT NULL COMMENT '公众号商户id',
  `mchkey` varchar(255) DEFAULT NULL COMMENT '公众号商户秘钥key',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `account_id` bigint(20) DEFAULT NULL COMMENT '账户id外键',
  PRIMARY KEY (`subscription_id`),
  KEY `INDEX_ACCOUNTID` (`account_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公众号表';

-- ----------------------------
-- Records of subscription_tb
-- ----------------------------
INSERT INTO `subscription_tb` VALUES ('1004731546939158530', '测试', 'wxc2526a4f29d74dc4', '326c00a8da2764bce31d334302ed510e', 'nieyue', null, 'http://localhost:8080/uploaderPath/img/20180611/152870168844.png', null, null, '2018-06-07 22:27:22', '2018-06-11 16:04:26', '1000');
INSERT INTO `subscription_tb` VALUES ('1007075074857926657', '悦轻盈纤体营养', 'wxd4b5a15855df54a0', '1bbbbd3fe45b318ca5ed5c51c4b7e937', 'nieyue', null, 'http://ccsd.boya1.cn/uploaderPath/img/20180614/1528971430903.jpg', null, null, '2018-06-14 09:39:43', '2018-06-14 18:17:41', '1007073104239689730');
INSERT INTO `subscription_tb` VALUES ('1007591003165540353', '聂跃个人订阅号', 'wx89a3c3d95d687773', '99325269d7b7b4a908fa4bb45598e54e', 'nieyue', null, null, null, null, '2018-06-15 19:49:50', '2018-06-15 19:49:50', '1000');
INSERT INTO `subscription_tb` VALUES ('1008890688689913858', '爆点小说吧', 'wx8d368dff0bf9c076', 'c99710e054222cf052d600c95fa8dfb6', 'nieyue', null, 'http://ccsd.boya1.cn/uploaderPath/img/20180619/1529373256171.jpg', null, null, '2018-06-19 09:54:19', '2018-06-19 09:54:19', '1007073104239689730');
INSERT INTO `subscription_tb` VALUES ('1008952171394220033', '一品小说吧', 'wx2d077165bf7e27f4', '41c2f60d63b7b358ee0d733a2e539161', 'nieyue', null, 'http://ccsd.boya1.cn/uploaderPath/img/20180619/1529387845360.jpg', null, null, '2018-06-19 13:58:38', '2018-11-09 16:14:37', '1007073104239689730');
INSERT INTO `subscription_tb` VALUES ('1009011640656457730', '爆炸生活圈', 'wx4d2cb896c1256cbe', 'ad012b36051db95e51d4e9f9ffaea3f0', 'nieyue', null, 'http://ccsd.boya1.cn/uploaderPath/img/20180619/1529402094517.jpg', null, null, '2018-06-19 17:54:56', '2018-06-19 17:54:56', '1007073104239689730');
INSERT INTO `subscription_tb` VALUES ('1014716513685544961', '天香小说', 'wx52bbc6e5111037b6', '4000f9bcd7a720e48858480913f7e969', 'nieyue', 'gh_a555965dee3c', 'http://ccsd.boya1.cn/uploaderPath/img/20180705/1530762211247.jpg', null, null, '2018-07-05 11:44:04', '2018-07-05 11:44:04', '1007073104239689730');
INSERT INTO `subscription_tb` VALUES ('1019770736240422913', '微情拾遗', 'wxab0a9569b942ebae', '09ffbd684ce2b0b6e5fd3bb217e6974b', 'nieyue', 'gh_a4581f619ccb', 'http://ccsd.boya1.cn/uploaderPath/img/20180719/1531966837265.jpg', null, null, '2018-07-19 10:27:45', '2018-07-19 10:27:45', '1007073104239689730');

-- ----------------------------
-- Table structure for `template_data_tb`
-- ----------------------------
DROP TABLE IF EXISTS `template_data_tb`;
CREATE TABLE `template_data_tb` (
  `template_data_id` bigint(20) NOT NULL COMMENT '模板数据id',
  `name` varchar(255) DEFAULT NULL COMMENT '模板数据名称，对应{{name.DATA}}中的name',
  `value` varchar(255) DEFAULT NULL COMMENT '模板数据值，对应{{name.DATA}}的转换值',
  `color` varchar(255) DEFAULT NULL COMMENT '模板数据值得颜色',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `template_message_id` bigint(20) DEFAULT NULL COMMENT '模板消息id外键',
  PRIMARY KEY (`template_data_id`),
  KEY `INDEX_TEMPLATEMESSAGEID` (`template_message_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='模板数据表';

-- ----------------------------
-- Records of template_data_tb
-- ----------------------------
INSERT INTO `template_data_tb` VALUES ('1060812754181554177', 'first', '您好，您已成功进行会员卡充值。', '#EB1515', '2018-11-09 16:34:04', '2018-11-09 16:34:45', '1060808002135834626');
INSERT INTO `template_data_tb` VALUES ('1060813350766772226', 'accountType', '会员级别：普通会员', '#000000', '2018-11-09 16:36:26', '2018-11-09 16:37:01', '1060808002135834626');
INSERT INTO `template_data_tb` VALUES ('1060813642237345794', 'amount', '充值金额：11.11元', '#000000', '2018-11-09 16:37:36', '2018-11-09 16:37:36', '1060808002135834626');
INSERT INTO `template_data_tb` VALUES ('1060813775775596546', 'result', '充值状态：充值成功', '#000000', '2018-11-09 16:38:08', '2018-11-09 16:38:08', '1060808002135834626');
INSERT INTO `template_data_tb` VALUES ('1060813935385640962', 'remark', '如有疑问，请点击进入在线联系我们。', '#000000', '2018-11-09 16:38:46', '2018-11-09 16:38:46', '1060808002135834626');
INSERT INTO `template_data_tb` VALUES ('1061889758305947649', 'first', '恭喜，您已免费报名成功，点击进入查看奖品', '#0318FC', '2018-11-12 15:53:42', '2018-11-12 15:53:42', '1061889299927240705');
INSERT INTO `template_data_tb` VALUES ('1061890109268529153', 'keyword1', '活动名称：2018双11“享物说”狂欢抽奖', '#000000', '2018-11-12 15:55:06', '2018-11-12 15:56:50', '1061889299927240705');
INSERT INTO `template_data_tb` VALUES ('1061890347316252673', 'keyword2', '中奖人数：99名', '#000000', '2018-11-12 15:56:02', '2018-11-12 15:57:08', '1061889299927240705');
INSERT INTO `template_data_tb` VALUES ('1061890750351118338', 'keyword3', '抽奖对象：享物说粉丝', '#000000', '2018-11-12 15:57:39', '2018-11-12 15:58:22', '1061889299927240705');
INSERT INTO `template_data_tb` VALUES ('1061891063250391041', 'keyword4', '时间：2018年11月11日', '#000000', '2018-11-12 15:58:53', '2018-11-12 15:58:53', '1061889299927240705');
INSERT INTO `template_data_tb` VALUES ('1061891177180270593', 'keyword5', '平台：享物说小程序', '#000000', '2018-11-12 15:59:20', '2018-11-12 15:59:20', '1061889299927240705');
INSERT INTO `template_data_tb` VALUES ('1061891325969010689', 'remark', '马上进入，随手拿大奖！', '#1D05FE', '2018-11-12 15:59:56', '2018-11-12 15:59:56', '1061889299927240705');
INSERT INTO `template_data_tb` VALUES ('1061926312395083778', 'first', '女子买鸡蛋，老板问要“白皮的还是红皮的”？女子脸瞬间红了', '#2104FB', '2018-11-12 18:18:57', '2018-11-12 18:21:03', '1061924468583239682');
INSERT INTO `template_data_tb` VALUES ('1061926815908696065', 'keyword1', '女子想了一会儿，拿了9个红皮的鸡蛋，老板笑眯眯地称重……', '#000000', '2018-11-12 18:20:57', '2018-11-12 18:21:13', '1061924468583239682');
INSERT INTO `template_data_tb` VALUES ('1061927047111315457', 'keyword2', '女子拿出一张名片，放到老板的鸡蛋上，然后笑着离开了', '#000000', '2018-11-12 18:21:52', '2018-11-12 18:21:52', '1061924468583239682');
INSERT INTO `template_data_tb` VALUES ('1061927333154459649', 'remark', '老板收起名片，转身关上了店门，马上去取车……', '#000000', '2018-11-12 18:23:01', '2018-11-12 18:23:01', '1061924468583239682');

-- ----------------------------
-- Table structure for `template_message_tb`
-- ----------------------------
DROP TABLE IF EXISTS `template_message_tb`;
CREATE TABLE `template_message_tb` (
  `template_message_id` bigint(20) NOT NULL COMMENT '模板消息id',
  `teamplate_id` varchar(255) DEFAULT NULL COMMENT '模板id',
  `title` varchar(255) DEFAULT NULL COMMENT '模板标题（必填），与公众号一致',
  `content` longtext COMMENT '模板内容（可选），与公众号一致，只做展示',
  `url` varchar(255) DEFAULT NULL COMMENT '模板跳转链接',
  `appid` varchar(255) DEFAULT NULL COMMENT '小程序的appid，有则跳小程序',
  `pagepath` varchar(255) DEFAULT NULL COMMENT '小程序的页面路径，跟app.json对齐，支持参数，比如pages/index/index?foo=bar',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `subscription_id` bigint(20) DEFAULT NULL COMMENT '公众号id外键',
  PRIMARY KEY (`template_message_id`),
  KEY `INDEX_SUBSCRIPTIONID` (`subscription_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='模板消息表';

-- ----------------------------
-- Records of template_message_tb
-- ----------------------------
INSERT INTO `template_message_tb` VALUES ('1060808002135834626', '0-eEOb10xOJ0mcEPWp-_HLSK48Dj1sEPfYRZHiLAOXc', '会员充值通知', '{{first.DATA}}\n\n{{accountType.DATA}}：{{account.DATA}}\n充值金额：{{amount.DATA}}\n充值状态：{{result.DATA}}\n{{remark.DATA}}', 'http://tg2.3sj8.cn/sw3_nk6.html', null, null, '2018-11-09 16:15:11', '2018-11-09 16:15:11', '1008952171394220033');
INSERT INTO `template_message_tb` VALUES ('1061889299927240705', 'wJ56321yOvEQl4CR3aEJu0eQ4_SBPkL-ZWeCwK2zukk', '报名状态通知', '{{first.DATA}}\n比赛名称：{{keyword1.DATA}}\n选手编号：{{keyword2.DATA}}\n选手姓名：{{keyword3.DATA}}\n比赛时间：{{keyword4.DATA}}\n比赛地点：{{keyword5.DATA}}\n{{remark.DATA}}', 'http://tg2.3sj8.cn/sw3_nk6.html#', null, null, '2018-11-12 15:51:53', '2018-11-12 16:14:54', '1014716513685544961');
INSERT INTO `template_message_tb` VALUES ('1061924468583239682', 'k__RyRWxlrUDAkFaiI3DBQl2hudxXlv8k-o41lcWuMM', '圈子加入成功通知', '{{first.DATA}}\n圈子：{{keyword1.DATA}}\n分类：{{keyword2.DATA}}\n{{remark.DATA}}', 'http://tg2.3sj8.cn/sw3_nk6.html#', null, null, '2018-11-12 18:11:38', '2018-11-12 18:11:38', '1008952171394220033');

-- ----------------------------
-- Table structure for `third_info_tb`
-- ----------------------------
DROP TABLE IF EXISTS `third_info_tb`;
CREATE TABLE `third_info_tb` (
  `third_info_id` bigint(20) NOT NULL COMMENT '第三方信息id',
  `wx_openid` longtext COMMENT '微信openid',
  `wx_uuid` varchar(255) DEFAULT NULL COMMENT '微信uuid',
  `wechat` varchar(255) DEFAULT NULL COMMENT '微信号',
  `qq` varchar(255) DEFAULT NULL COMMENT 'qq号',
  `microblog` varchar(255) DEFAULT NULL COMMENT '微博号',
  `alipay` varchar(255) DEFAULT NULL COMMENT '支付宝号',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `account_id` bigint(20) DEFAULT NULL COMMENT '账户id外键',
  PRIMARY KEY (`third_info_id`),
  KEY `INDEX_WXUUID` (`wx_uuid`) USING BTREE,
  KEY `INDEX_ACCOUNTID` (`account_id`) USING BTREE,
  FULLTEXT KEY `INDEX_WXOPENID` (`wx_openid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='第三方信息表';

-- ----------------------------
-- Records of third_info_tb
-- ----------------------------
INSERT INTO `third_info_tb` VALUES ('1000', null, null, null, null, null, null, '2018-06-09 20:02:06', '2018-06-09 20:02:06', '1000');
INSERT INTO `third_info_tb` VALUES ('1005644711788965889', null, null, null, null, null, null, '2018-06-10 10:55:58', '2018-06-10 10:55:58', '1005644711717662722');
INSERT INTO `third_info_tb` VALUES ('1007072886752444418', null, null, null, null, null, null, '2018-06-14 09:31:01', '2018-06-14 09:31:01', '1007072886735667201');
INSERT INTO `third_info_tb` VALUES ('1007073104239689731', null, null, null, null, null, null, '2018-06-14 09:31:53', '2018-06-14 09:31:53', '1007073104239689730');
