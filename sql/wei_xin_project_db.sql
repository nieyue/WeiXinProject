# 数据库 
#创建数据库
DROP DATABASE IF EXISTS wei_xin_project_db;
CREATE DATABASE wei_xin_project_db;

#使用数据库 
use wei_xin_project_db;

#创建角色表
CREATE TABLE role_tb(
role_id int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
name varchar(255) COMMENT '角色名',
duty varchar(255) COMMENT '角色职责',
update_date datetime COMMENT '更新时间',
PRIMARY KEY (role_id)
)ENGINE = InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='角色表';

#创建账户表 
CREATE TABLE account_tb(
account_id int(11) NOT NULL AUTO_INCREMENT COMMENT '账户id',
phone varchar(255) COMMENT '注册手机号',
password varchar(255) COMMENT '密码',
nickname varchar(255) COMMENT '昵称',
icon varchar(255) COMMENT '图像',
sex tinyint(4) DEFAULT 0 COMMENT '性别,默认为0未知，为1男性，为2女性',
country varchar(255) COMMENT '国家,默认中国',
realname varchar(255) COMMENT '真实姓名',
email varchar(255) COMMENT 'email',
safety_grade tinyint(4) COMMENT '安全等级，1低，2中，3高',
auth tinyint(4) COMMENT '认证，0没认证，1审核中，2已认证',
identity_cards varchar(255) COMMENT '身份证',
identity_cards_hold_img varchar(255) COMMENT '手持身份证上半身照',
identity_cards_front_img varchar(255) COMMENT '身份证正面',
identity_cards_back_img varchar(255) COMMENT '身份证反面',
create_date datetime COMMENT '创建时间',
login_date datetime COMMENT '登陆时间',
status tinyint DEFAULT 0 COMMENT '状态，默认0正常，1锁定，2，异常',
role_id int(11) COMMENT '角色id外键',
PRIMARY KEY (account_id),
INDEX INDEX_AUTH (auth) USING BTREE,
INDEX INDEX_PHONE (phone) USING BTREE,
INDEX INDEX_REALNAME (realname) USING BTREE,
INDEX INDEX_CREATEDATE (create_date) USING BTREE,
INDEX INDEX_LOGINDATE (login_date) USING BTREE,
INDEX INDEX_ROLEID (role_id) USING BTREE,
INDEX INDEX_STATUS (status) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='账户表';

#公众号
CREATE TABLE subscription_tb(
subscription_id int(11) NOT NULL AUTO_INCREMENT COMMENT '公众号id',
name varchar(255) COMMENT '公众号名称',
appid varchar(255) COMMENT '公众号应用id',
appsecret varchar(255) COMMENT '公众号秘钥',
ghid varchar(255) COMMENT '公众号原始id',
img_address varchar(255) COMMENT '公众号二维码',
mchid varchar(255) COMMENT '公众号商户id',
mchkey varchar(255) COMMENT '公众号商户秘钥key',
update_date datetime COMMENT '更新时间',
account_id int(11) COMMENT '账户id外键',
PRIMARY KEY (subscription_id)
)ENGINE = InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='公众号表';

#客服消息
CREATE TABLE kf_message_tb(
kf_message_id int(11) NOT NULL AUTO_INCREMENT COMMENT '客服消息id',
msgtype varchar(255) COMMENT '消息类型，text文本消息，image图片消息，voice语音消息，video视频消息，music音乐消息，news图文消息（外链），mpnews图文消息（微信链接），wxcard发送卡券，miniprogrampage小程序',
content longtext COMMENT '文本消息内容',
media_id varchar(255) COMMENT '发送的图片/语音/视频/图文消息（点击跳转到图文消息页）的媒体ID',
thumb_media_id varchar(255) COMMENT '缩略图/小程序卡片图片的媒体ID，小程序卡片图片建议大小为520*416',
title varchar(255) COMMENT '视频消息/音乐消息/小程序卡片的标题',
description varchar(255) COMMENT '视频消息/音乐消息的描述',
musicurl varchar(255) COMMENT '音乐链接',
hqmusicurl varchar(255) COMMENT '高品质音乐链接，wifi环境优先使用该链接播放音乐',
card_id varchar(255) COMMENT '卡卷id',
appid varchar(255) COMMENT '小程序的appid，要求小程序的appid需要与公众号有关联关系',
pagepath varchar(255) COMMENT '小程序的页面路径，跟app.json对齐，支持参数，比如pages/index/index?foo=bar',
update_date datetime COMMENT '更新时间',
subscription_id int(11) COMMENT '公众号id外键',
PRIMARY KEY (kf_message_id)
)ENGINE = InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='客服消息表';

#客服消息文章
CREATE TABLE kf_article_tb(
kf_article_id int(11) NOT NULL AUTO_INCREMENT COMMENT '客服消息文章id',
title varchar(255) COMMENT '图文消息的标题',
description varchar(255) COMMENT '图文消息的描述',
url varchar(255) COMMENT '图文消息被点击后跳转的链接',
picurl varchar(255) COMMENT '图文消息的图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80',
update_date datetime COMMENT '更新时间',
kf_message_id int(11) COMMENT '客服消息id外键',
PRIMARY KEY (kf_article_id)
)ENGINE = InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='客服消息文章表';

#设置初始角色
INSERT IGNORE INTO role_tb (name,duty,update_date) 
VALUES ("超级管理员","超级管理员",now());
INSERT IGNORE INTO role_tb (name,duty,update_date) 
VALUES ("普通管理员","普通管理员",now());
INSERT IGNORE INTO role_tb (name,duty,update_date) 
VALUES ("商户","商户",now());
INSERT IGNORE INTO role_tb (name,duty,update_date) 
VALUES ("推广户","推广户",now());
INSERT IGNORE INTO role_tb (name,duty,update_date) 
VALUES ("用户","用户",now());
 
#设置初始管理员密码MD5加密123456
INSERT IGNORE INTO account_tb (nickname,phone,email,password,create_date,login_date,role_id) 
VALUES ("聂跃","15111336587","278076304@qq.com","11874bb6149dd45428da628c9766b252",now(),now(),1000); 