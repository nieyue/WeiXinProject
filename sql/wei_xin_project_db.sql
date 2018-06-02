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
