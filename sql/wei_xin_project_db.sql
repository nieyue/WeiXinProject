# 数据库 
#创建数据库
DROP DATABASE IF EXISTS wei_xin_project_db;
CREATE DATABASE wei_xin_project_db;

#使用数据库 
use wei_xin_project_db;

#创建角色表
CREATE TABLE role_tb(
role_id bigint(20) NOT NULL  COMMENT '角色id',
name varchar(255) COMMENT '角色名',
duty varchar(255) COMMENT '角色职责',
update_date datetime COMMENT '更新时间',
PRIMARY KEY (role_id)
)ENGINE = InnoDB  DEFAULT CHARSET=utf8 COMMENT='角色表';

#创建权限表
CREATE TABLE permission_tb(
permission_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '权限id',
type tinyint(4) COMMENT '权限类型，默认0开放，1鉴权',
manager_name varchar(255) COMMENT '权限管理名',
name varchar(255) COMMENT '权限名',
route varchar(255) unique COMMENT '权限路由',
update_date datetime COMMENT '更新时间',
PRIMARY KEY (permission_id),
INDEX INDEX_TYPE (type) USING BTREE,
INDEX INDEX_MANAGERNAME (manager_name) USING BTREE,
INDEX INDEX_NAME (name) USING BTREE,
INDEX INDEX_ROUTE (route) USING BTREE
)ENGINE = InnoDB  DEFAULT CHARSET=utf8 COMMENT='权限表';

#创建角色权限表
CREATE TABLE role_permission_tb(
role_permission_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色权限id',
region tinyint(4) COMMENT "范围，1公共，2自身",
role_id int(11) COMMENT '角色id,外键',
update_date datetime COMMENT '更新时间',
permission_id bigint(20) COMMENT '权限id,外键',
PRIMARY KEY (role_permission_id),
UNIQUE KEY UNIQUE_ROLEID_PERMISSIONID (role_id,permission_id),
INDEX INDEX_REGION (region) USING BTREE,
INDEX INDEX_ROLEID (role_id) USING BTREE,
INDEX INDEX_PERMISSIONID (permission_id) USING BTREE
)ENGINE = InnoDB  DEFAULT CHARSET=utf8 COMMENT='角色权限表';

#创建账户表 
CREATE TABLE account_tb(
account_id bigint(20) NOT NULL  COMMENT '账户id',
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
role_id bigint(20) COMMENT '角色id外键',
PRIMARY KEY (account_id),
INDEX INDEX_AUTH (auth) USING BTREE,
INDEX INDEX_PHONE (phone) USING BTREE,
INDEX INDEX_REALNAME (realname) USING BTREE,
INDEX INDEX_CREATEDATE (create_date) USING BTREE,
INDEX INDEX_LOGINDATE (login_date) USING BTREE,
INDEX INDEX_ROLEID (role_id) USING BTREE,
INDEX INDEX_STATUS (status) USING BTREE
)ENGINE = InnoDB  DEFAULT CHARSET=utf8 COMMENT='账户表';

#创建第三方信息表
CREATE TABLE third_info_tb(
third_info_id bigint(20) NOT NULL  COMMENT '第三方信息id',
wx_openid longtext COMMENT '微信openid，多个，格式[{subscriptionId:'1004908098840150018',openid:'sdfsdfdsf2fd'}]',
wx_uuid varchar(255) COMMENT '微信uuid',
wechat varchar(255) COMMENT '微信号',
qq varchar(255) COMMENT 'qq号',
microblog varchar(255) COMMENT '微博号',
alipay varchar(255) COMMENT '支付宝号',
create_date datetime COMMENT '创建时间',
update_date datetime COMMENT '更新时间',
account_id bigint(20) COMMENT '账户id外键',
PRIMARY KEY (third_info_id),
INDEX INDEX_WXOPENID (wx_openid),
INDEX INDEX_WXUUID (wx_uuid) USING BTREE,
INDEX INDEX_ACCOUNTID (account_id) USING BTREE
)ENGINE = InnoDB  DEFAULT CHARSET=utf8 COMMENT='第三方信息表';

#公众号
CREATE TABLE subscription_tb(
subscription_id bigint(20) NOT NULL  COMMENT '公众号id',
name varchar(255) COMMENT '公众号名称',
appid varchar(255) COMMENT '公众号应用id',
appsecret varchar(255) COMMENT '公众号秘钥',
token varchar(255) COMMENT '微信接口令牌',
ghid varchar(255) COMMENT '公众号原始id',
img_address varchar(255) COMMENT '公众号二维码',
mchid varchar(255) COMMENT '公众号商户id',
mchkey varchar(255) COMMENT '公众号商户秘钥key',
create_date datetime COMMENT '创建时间',
update_date datetime COMMENT '更新时间',
account_id bigint(20) COMMENT '账户id外键',
PRIMARY KEY (subscription_id),
INDEX INDEX_ACCOUNTID (account_id) USING BTREE
)ENGINE = InnoDB  DEFAULT CHARSET=utf8 COMMENT='公众号表';

#客服消息
CREATE TABLE kf_message_tb(
kf_message_id bigint(20) NOT NULL  COMMENT '客服消息id',
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
create_date datetime COMMENT '创建时间',
update_date datetime COMMENT '更新时间',
subscription_id bigint(20) COMMENT '公众号id外键',
PRIMARY KEY (kf_message_id),
INDEX INDEX_MSGTYPE (msgtype) USING BTREE,
INDEX INDEX_SUBSCRIPTIONID (subscription_id) USING BTREE
)ENGINE = InnoDB  DEFAULT CHARSET=utf8 COMMENT='客服消息表';

#客服消息文章
CREATE TABLE kf_article_tb(
kf_article_id bigint(20) NOT NULL  COMMENT '客服消息文章id',
title varchar(255) COMMENT '图文消息的标题',
description varchar(255) COMMENT '图文消息的描述',
url varchar(255) COMMENT '图文消息被点击后跳转的链接',
picurl varchar(255) COMMENT '图文消息的图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80',
create_date datetime COMMENT '创建时间',
update_date datetime COMMENT '更新时间',
kf_message_id bigint(20) COMMENT '客服消息id外键',
PRIMARY KEY (kf_article_id),
INDEX INDEX_KFMESSAGEID (kf_message_id) USING BTREE
)ENGINE = InnoDB  DEFAULT CHARSET=utf8 COMMENT='客服消息文章表';

#模板消息
CREATE TABLE template_message_tb(
template_message_id bigint(20) NOT NULL  COMMENT '模板消息id',
teamplate_id varchar(255) COMMENT '模板id',
title varchar(255) COMMENT '模板标题（必填），与公众号一致',
content longtext COMMENT '模板内容（可选），与公众号一致，只做展示',
url varchar(255) COMMENT '模板跳转链接',
appid varchar(255) COMMENT '小程序的appid，有则跳小程序',
pagepath varchar(255) COMMENT '小程序的页面路径，跟app.json对齐，支持参数，比如pages/index/index?foo=bar',
create_date datetime COMMENT '创建时间',
update_date datetime COMMENT '更新时间',
subscription_id bigint(20) COMMENT '公众号id外键',
PRIMARY KEY (template_message_id),
INDEX INDEX_SUBSCRIPTIONID (subscription_id) USING BTREE
)ENGINE = InnoDB  DEFAULT CHARSET=utf8 COMMENT='模板消息表';

#模板数据
CREATE TABLE template_data_tb(
template_data_id bigint(20) NOT NULL  COMMENT '模板数据id',
name varchar(255) COMMENT '模板数据名称，对应{{name.DATA}}中的name',
value varchar(255) COMMENT '模板数据值，对应{{name.DATA}}的转换值',
color varchar(255) COMMENT '模板数据值得颜色',
create_date datetime COMMENT '创建时间',
update_date datetime COMMENT '更新时间',
template_message_id bigint(20) COMMENT '模板消息id外键',
PRIMARY KEY (template_data_id),
INDEX INDEX_TEMPLATEMESSAGEID (template_message_id) USING BTREE
)ENGINE = InnoDB  DEFAULT CHARSET=utf8 COMMENT='模板数据表';

#创建签到表 
CREATE TABLE sign_tb(
sign_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '签到id',
day_number int(11) DEFAULT 0 COMMENT '连续天数',
integral decimal(11,2)  DEFAULT 0 COMMENT '总积分',
create_date datetime COMMENT '创建时间',
update_date datetime COMMENT '修改时间',
openid varchar(255) COMMENT '微信openid',
subscription_id bigint(20) COMMENT '公众号id外键',
account_id bigint(20) COMMENT '任务人id外键',
PRIMARY KEY (sign_id),
INDEX INDEX_OPENID (openid) USING BTREE,
INDEX INDEX_SUBSCRIPTIONID (subscription_id) USING BTREE,
INDEX INDEX_ACCOUNTID (account_id) USING BTREE
)ENGINE = InnoDB DEFAULT CHARSET=utf8 COMMENT='签到表';

#创建签到记录表 
CREATE TABLE sign_record_tb(
sign_record_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '签到记录id',
integral decimal(11,2) DEFAULT 0 COMMENT '奖励积分',
sign_date datetime COMMENT '签到时间',
openid varchar(255) COMMENT '微信openid',
subscription_id bigint(20) COMMENT '公众号id外键',
account_id bigint(20) COMMENT '任务人id外键',
PRIMARY KEY (sign_record_id),
INDEX INDEX_OPENID (openid) USING BTREE,
INDEX INDEX_SUBSCRIPTIONID (subscription_id) USING BTREE,
INDEX INDEX_ACCOUNTID (account_id) USING BTREE
)ENGINE = InnoDB  DEFAULT CHARSET=utf8 COMMENT='签到记录表';

#创建奖品表 
CREATE TABLE prize_tb(
prize_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '奖品id',
day_number int(11) DEFAULT 0 COMMENT '连续天数',
name varchar(255)  COMMENT '奖品名称',
number int(11) DEFAULT 0 COMMENT '奖品数量',
img_address varchar(255)  COMMENT '奖品图标',
content longtext  COMMENT '奖品内容',
create_date datetime COMMENT '创建时间',
update_date datetime COMMENT '修改时间',
subscription_id bigint(20) COMMENT '公众号id外键',
account_id bigint(20) COMMENT '创建奖品账户id外键',
PRIMARY KEY (prize_id),
INDEX INDEX_DAYNUMBER (day_number) USING BTREE,
INDEX INDEX_SUBSCRIPTIONID (subscription_id) USING BTREE,
INDEX INDEX_ACCOUNTID (account_id) USING BTREE
)ENGINE = InnoDB  DEFAULT CHARSET=utf8 COMMENT='奖品表';

#创建签到奖品表 
CREATE TABLE sign_prize_tb(
sign_prize_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '签到奖品id',
day_number int(11) DEFAULT 0 COMMENT '连续天数',
name varchar(255)  COMMENT '奖品名称',
number int(11) DEFAULT 0 COMMENT '奖品数量',
img_address varchar(255)  COMMENT '奖品图标',
content longtext  COMMENT '奖品内容',
prize_date datetime COMMENT '领奖时间',
status  tinyint(4) COMMENT '状态，1待申请，2申请领奖，3领取成功，4拒绝发送',
openid varchar(255) COMMENT '微信openid',
subscription_id bigint(20) COMMENT '公众号id外键',
prize_id bigint(20) COMMENT '奖品id外键',
account_id bigint(20) COMMENT '领奖人id外键',
PRIMARY KEY (sign_prize_id),
INDEX INDEX_DAYNUMBER (day_number) USING BTREE,
INDEX INDEX_STATUS (status) USING BTREE,
INDEX INDEX_OPENID (openid) USING BTREE,
INDEX INDEX_SUBSCRIPTIONID (subscription_id) USING BTREE,
INDEX INDEX_PRIZEID (prize_id) USING BTREE,
INDEX INDEX_ACCOUNTID (account_id) USING BTREE
)ENGINE = InnoDB  DEFAULT CHARSET=utf8 COMMENT='签到奖品表';

#创建收货信息表 
CREATE TABLE receipt_info_tb(
receipt_info_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '收货信息id',
name varchar(255) COMMENT '姓名',
phone varchar(255) COMMENT '手机号',
province varchar(255) COMMENT '省',
city varchar(255) COMMENT '市',
area varchar(255) COMMENT '区',
address varchar(255) COMMENT '收货地址',
is_default tinyint(4) DEFAULT 0 COMMENT '默认为0不是，1是',
create_date datetime   COMMENT '创建时间',
update_date datetime   COMMENT '更新时间',
openid varchar(255) COMMENT '微信openid,外键',
account_id bigint(20) COMMENT '账户id,外键',
PRIMARY KEY (receipt_info_id),
INDEX INDEX_OPENID (openid) USING BTREE,
INDEX INDEX_ACCOUNTID (account_id) USING BTREE,
INDEX INDEX_ISDEFAULT (is_default) USING BTREE,
INDEX INDEX_CREATEDATE (create_date) USING BTREE,
INDEX INDEX_UPDATEDATE (update_date) USING BTREE
)ENGINE = InnoDB  DEFAULT CHARSET=utf8 COMMENT='收货地址表 ';

#设置初始角色
INSERT IGNORE INTO role_tb (role_id,name,duty,update_date) 
VALUES (1000,"超级管理员","超级管理员",now());
INSERT IGNORE INTO role_tb (role_id,name,duty,update_date) 
VALUES (1001,"普通管理员","普通管理员",now());
INSERT IGNORE INTO role_tb (role_id,name,duty,update_date) 
VALUES (1002,"商户","商户",now());
INSERT IGNORE INTO role_tb (role_id,name,duty,update_date) 
VALUES (1003,"推广户","推广户",now());
INSERT IGNORE INTO role_tb (role_id,name,duty,update_date) 
VALUES (1004,"用户","用户",now());
 
#设置初始管理员密码MD5加密123456
INSERT IGNORE INTO account_tb (account_id,nickname,phone,email,password,create_date,login_date,role_id) 
VALUES (1000,"聂跃","15111336587","278076304@qq.com","11874bb6149dd45428da628c9766b252",now(),now(),1000); 

INSERT IGNORE INTO third_info_tb (third_info_id,create_date,update_date,account_id) 
VALUES (1000,now(),now(),1000); 
