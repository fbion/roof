/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.5.19 : Database - mail
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `s_dictionary` */

CREATE TABLE `s_dictionary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` varchar(200) DEFAULT NULL,
  `val` varchar(200) DEFAULT NULL,
  `text` varchar(200) DEFAULT NULL,
  `seq` int(11) DEFAULT NULL,
  `active` char(1) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `s_dictionary` */

insert  into `s_dictionary`(`id`,`type`,`val`,`text`,`seq`,`active`,`description`) values (1,'DIC','S_DIC','数据字典',NULL,'1','');

/*Table structure for table `s_fileinfo` */

CREATE TABLE `s_fileinfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(250) NOT NULL,
  `displayName` varchar(250) DEFAULT NULL,
  `fileSize` bigint(20) DEFAULT NULL,
  `realPath` varchar(250) DEFAULT NULL,
  `webPath` varchar(250) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE_NAME` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `s_fileinfo` */

/*Table structure for table `s_menu` */

CREATE TABLE `s_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dtype` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `lvl` int(11) DEFAULT NULL,
  `leaf` char(1) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `module_id` bigint(20) DEFAULT NULL,
  `target` varchar(255) DEFAULT NULL,
  `menuType` varchar(20) NOT NULL,
  `script` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `seq` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=206 DEFAULT CHARSET=utf8;

/*Data for the table `s_menu` */

insert  into `s_menu`(`id`,`dtype`,`name`,`lvl`,`leaf`,`parent_id`,`module_id`,`target`,`menuType`,`script`,`icon`,`seq`) values (1,'Menu','菜单根节点',0,'0',NULL,NULL,'','BackGround','',NULL,NULL);
insert  into `s_menu`(`id`,`dtype`,`name`,`lvl`,`leaf`,`parent_id`,`module_id`,`target`,`menuType`,`script`,`icon`,`seq`) values (50,'Menu','系统管理',1,'0',1,NULL,'_mainFrame','BackGround','','',1000);
insert  into `s_menu`(`id`,`dtype`,`name`,`lvl`,`leaf`,`parent_id`,`module_id`,`target`,`menuType`,`script`,`icon`,`seq`) values (100,'Menu','用户管理',2,'1',50,3,'_mainFrame','BackGround','',NULL,NULL);
insert  into `s_menu`(`id`,`dtype`,`name`,`lvl`,`leaf`,`parent_id`,`module_id`,`target`,`menuType`,`script`,`icon`,`seq`) values (101,'Menu','角色管理',2,'1',50,28,'_mainFrame','BackGround','',NULL,NULL);
insert  into `s_menu`(`id`,`dtype`,`name`,`lvl`,`leaf`,`parent_id`,`module_id`,`target`,`menuType`,`script`,`icon`,`seq`) values (102,'Menu','组织架构管理',2,'1',50,16,'_mainFrame','BackGround','',NULL,NULL);
insert  into `s_menu`(`id`,`dtype`,`name`,`lvl`,`leaf`,`parent_id`,`module_id`,`target`,`menuType`,`script`,`icon`,`seq`) values (103,'Menu','资源管理',2,'1',50,35,'_mainFrame','BackGround','',NULL,NULL);
insert  into `s_menu`(`id`,`dtype`,`name`,`lvl`,`leaf`,`parent_id`,`module_id`,`target`,`menuType`,`script`,`icon`,`seq`) values (104,'Menu','菜单管理',2,'1',50,53,'_mainFrame','BackGround','',NULL,NULL);
insert  into `s_menu`(`id`,`dtype`,`name`,`lvl`,`leaf`,`parent_id`,`module_id`,`target`,`menuType`,`script`,`icon`,`seq`) values (200,'Menu','字典表管理',2,'1',50,43,'_mainFrame','BackGround','',NULL,NULL);
insert  into `s_menu`(`id`,`dtype`,`name`,`lvl`,`leaf`,`parent_id`,`module_id`,`target`,`menuType`,`script`,`icon`,`seq`) values (201,'Menu','系统登出',2,'1',50,NULL,'_mainFrame','BackGround','if (confirm(\"确定系统登出？\")) {\ntop.location.href = basePath+\"/systemAction!j_spring_security_logout\";}',NULL,9);
insert  into `s_menu`(`id`,`dtype`,`name`,`lvl`,`leaf`,`parent_id`,`module_id`,`target`,`menuType`,`script`,`icon`,`seq`) values (202,'Menu','修改密码',2,'1',50,14,'_mainFrame','BackGround','',NULL,NULL);
insert  into `s_menu`(`id`,`dtype`,`name`,`lvl`,`leaf`,`parent_id`,`module_id`,`target`,`menuType`,`script`,`icon`,`seq`) values (203,'Menu','邮件',1,'0',1,NULL,'_mainFrame','BackGround','','',1);
insert  into `s_menu`(`id`,`dtype`,`name`,`lvl`,`leaf`,`parent_id`,`module_id`,`target`,`menuType`,`script`,`icon`,`seq`) values (204,'Menu','邮件用户',2,'1',203,76,'_mainFrame','BackGround','','',NULL);
insert  into `s_menu`(`id`,`dtype`,`name`,`lvl`,`leaf`,`parent_id`,`module_id`,`target`,`menuType`,`script`,`icon`,`seq`) values (205,'Menu','邮件',2,'1',203,84,'_mainFrame','BackGround','','',NULL);

/*Table structure for table `s_organization` */

CREATE TABLE `s_organization` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `lvl` int(11) DEFAULT NULL,
  `seq` int(11) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `leaf` char(1) DEFAULT NULL,
  `usable` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `s_organization` */

insert  into `s_organization`(`id`,`name`,`lvl`,`seq`,`parent_id`,`leaf`,`usable`) values (1,'组织架构',0,0,NULL,'0','1');

/*Table structure for table `s_resource` */

CREATE TABLE `s_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dtype` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `pattern` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `identify` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `seq` int(11) DEFAULT NULL,
  `lvl` int(11) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `leaf` bit(1) DEFAULT NULL,
  `returnExample` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `format` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8;

/*Data for the table `s_resource` */

insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (1,'Module','系统','/**/*','','','/',0,0,NULL,'\0','','','');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (2,'Module','用户管理','/**/user*','用户管理','user','/userAction',1,1,1,'\0',NULL,NULL,NULL);
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (3,'Privilege','用户列表','/userAction/list*','用户列表','list','/userAction/list',1,2,2,'',NULL,'','html');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (4,'Privilege','用户详情','/**/userAction/detail*','用户详情','detail','/userAction/detail',5,2,2,'',NULL,'','html');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (5,'Privilege','用户修改','/**/userAction/update*','用户修改','update','/userAction/update',3,2,2,'\0',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (6,'Privilege','用户修改页面','/userAction/update_page*','用户修改页面','page','/userAction/update_page',NULL,3,5,'',NULL,'','html');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (7,'Privilege','用户新增','/**/userAction/create*','用户新增','create','/userAction/create',2,2,2,'\0',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (8,'Privilege','用户新增页面','/**/userAction/create_page*','用户新增页面','page','/userAction/create_page',NULL,3,7,'',NULL,'','html');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (9,'Privilege','用户删除','/**/userAction/delete*','用户删除','delete','/userAction/delete',4,2,2,'',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (10,'Privilege','是否存在相同用户名','/**/userAction/sameUsername*','是否存在相同用户名','sameUsername','/userAction/sameUsername',NULL,2,2,'',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (11,'Privilege','登录页面','/**/userAction/goLogin*','用户登录页面','goLogin','/userAction/goLogin',NULL,2,2,'',NULL,'','html');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (12,'Privilege','主页面','/**/userAction/goMain*','登录后的主页面','goMain','/userAction/goMain',NULL,2,2,'',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (13,'Privilege','用户密码修改','/**/userAction/update_pwd*','用户密码修改','update_pwd','/userAction/update_pwd',NULL,2,2,'\0',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (14,'Privilege','用户密码修改页面','/**/userAction/update_pwd_page*','用户密码修改页面','page','/userAction/update_pwd_page',NULL,3,13,'',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (15,'Module','组织架构管理','/**/org*','组织架构管理','org','/org',3,1,1,'\0',NULL,NULL,NULL);
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (16,'Privilege','组织架构管理首页面','/**/orgAction/index*','组织架构管理首页面','index','/orgAction/index',1,2,15,'',NULL,'','html');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (17,'Privilege','加载组织架构树','/**/orgAction/read*','加载组织架构树','read','/orgAction/read',NULL,2,15,'',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (18,'Privilege','组织新增','/**/orgAction/create*','组织新增','create','/orgAction/create',2,2,15,'\0',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (19,'Privilege','组织新增页面','/**/orgAction/create_page*','组织新增页面','page','/orgAction/create_page',NULL,3,18,'',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (20,'Privilege','组织修改','/**/orgAction/update*','组织修改','update','/orgAction/update',3,2,15,'\0',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (21,'Privilege','组织修改页面','/**/orgAction/update_page*','组织修改页面','page','/orgAction/update_page',NULL,3,20,'',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (22,'Privilege','组织删除','/**/orgAction/delete*','组织删除','delete','/orgAction/delete',4,2,15,'',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (23,'Privilege','组织详情','/**/orgAction/detail*','组织详情','detail','/orgAction/detail',5,2,15,'',NULL,'','html');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (24,'Privilege','组织架构查询','/**/orgAction/query*','组织架构查询','query','/orgAction/query',NULL,2,15,'',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (25,'Module','角色管理','/**/role*','角色管理','role','/roleAction',2,1,1,'\0',NULL,NULL,NULL);
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (26,'Privilege','角色修改','/**/roleAction/update*','角色修改','update','/roleAction/update',3,2,25,'\0',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (27,'Privilege','角色修改页面','/**/roleAction/update_page*','角色修改页面','page','/roleAction/update_page',NULL,3,26,'',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (28,'Privilege','角色列表','/**/roleAction/list*','角色列表','list','/roleAction/list',1,2,25,'\0',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (29,'Privilege','角色选择','/**/roleAction/list_select*','角色选择','select','/roleAction/list_select',NULL,3,28,'',NULL,'','html');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (30,'Privilege','角色新增','/**/roleAction/create*','角色新增','create','/roleAction/create',2,2,25,'\0',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (31,'Privilege','角色新增页面','/**/roleAction/create_page*','角色新增页面','page','/roleAction/create_page',NULL,3,30,'',NULL,'','html');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (32,'Privilege','角色删除','/**/roleAction/delete*','角色删除','delete','/roleAction/delete',4,2,25,'',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (33,'Privilege','角色详情','/**/roleAction/detail*','角色详情','detail','/roleAction/detail',5,2,25,'',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (34,'Module','资源管理','/**/resource*','','resource','/resource',4,1,1,'\0',NULL,NULL,NULL);
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (35,'Privilege','资源管理首页面','/**/resourceAction/index*','资源管理首页面','index','/resourceAction/index',NULL,2,34,'',NULL,'','html');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (36,'Privilege','资源管理详情页面','/**/resourceAction/detail*','资源管理详情页面','detail','/resourceAction/detail',NULL,2,34,'',NULL,'','html');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (37,'Privilege','资源新建','/**/resourceAction/create*','资源新建','create','/resourceAction/create',NULL,2,34,'',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (38,'Privilege','资源删除','/**/resourceAction/delete*','资源删除','delete','/resourceAction/delete',NULL,2,34,'',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (39,'Privilege','资源更新','/**/resourceAction/updatePrivilege*','资源更新','updatePrivilege','/resourceAction/updatePrivilege',NULL,2,34,'',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (40,'Privilege','模块更新','/**/resourceAction/updateModule*','模块更新','updateModule','/resourceAction/updateModule',NULL,2,34,'',NULL,'','html');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (41,'Privilege','资源树加载','/**/resourceAction/read*','资源树加载','read','/resourceAction/read',NULL,2,34,'',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (42,'Module','字典表管理','/**/dictionary*','','dictionary','/dictionary',5,1,1,'\0',NULL,NULL,NULL);
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (43,'Privilege','字典表管理首页面','/**/dictionaryAction/index*','字典表管理首页面','index','/dictionaryAction/index',1,2,42,'',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (44,'Privilege','字典表新增','/**/dictionaryAction/create*','字典表新增','create','/dictionaryAction/create',2,2,42,'\0',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (45,'Privilege','字典表新增页面','/**/dictionaryAction/create_page*','字典表新增页面','page','/dictionaryAction/create_page',NULL,3,44,'',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (46,'Privilege','字典表修改','/**/dictionaryAction/update*','字典表修改','update','/dictionaryAction/update',3,2,42,'\0',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (47,'Privilege','字典表修改页面','/**/dictionaryAction/update_page*','字典表修改页面','page','/dictionaryAction/update_page',4,3,46,'',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (48,'Privilege','字典表删除','/**/dictionaryAction/delete*','字典表删除','delete','/dictionaryAction/delete',4,2,42,'',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (49,'Privilege','字典表详情','/**/dictionaryAction/detail*','字典表详情','detail','/dictionaryAction/detail',5,2,42,'',NULL,'','html');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (50,'Privilege','加载字典表树','/**/dictionaryAction/read*','加载字典表树','read','/dictionaryAction/read',6,2,42,'',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (51,'Privilege','字典表查找','/**/dictionaryAction/query*','字典表查找','query','/dictionaryAction/query',NULL,2,42,'',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (52,'Module','菜单管理','/**/menu*','','menu','/menu',6,1,1,'\0',NULL,NULL,NULL);
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (53,'Privilege','菜单管理首页面','/**/menuAction/index*','菜单管理首页面','index','/menuAction/index',1,2,52,'',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (54,'Privilege','菜单树加载','/**/menuAction/read*','菜单树加载','read','/menuAction/read',NULL,2,52,'',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (55,'Privilege','菜单新增','/**/menuAction/create*','菜单新增','create','/menuAction/create',2,2,52,'\0',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (56,'Privilege','菜单新增页面','/**/menuAction/create_page*','菜单新增页面','page','/menuAction/create_page',NULL,3,55,'',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (57,'Privilege','菜单修改','/**/menuAction/update*','菜单修改','update','/menuAction/update',3,2,52,'\0',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (58,'Privilege','菜单修改页面','/**/menuAction/update_page*','菜单修改页面','page','/menuAction/update_page',NULL,3,57,'',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (59,'Privilege','菜单删除','/**/menuAction/delete*','菜单删除','delete','/menuAction/delete',4,2,52,'',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (60,'Privilege','菜单详情','/**/menuAction/detail*','菜单详情','detail','/menuAction/detail',5,2,52,'',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (61,'Privilege','菜单移动','/**/menuAction/moveTo*','菜单移动','moveTo','/menuAction/moveTo',NULL,2,52,'',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (62,'Module','缓存管理','/**/cache*','','cache','/cache',7,1,1,'\0',NULL,NULL,NULL);
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (63,'Privilege','缓存列表','/**/cacheAction/list*','缓存列表','list','/cacheAction/list',NULL,2,62,'',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (64,'Privilege','缓存清除','/**/cacheAction/delete*','缓存清除','delete','/cacheAction/delete',NULL,2,62,'',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (65,'Module','系统公共资源','/**/system*','系统公共资源','system','/systemAction',7,1,1,'\0',NULL,NULL,NULL);
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (66,'Privilege','系统登出','/**/systemAction/j_spring_security_logout*','系统登出','j_spring_security_logout','/systemAction/j_spring_security_logout',NULL,2,65,'',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (67,'Module','邮件','/**/mail*/*',NULL,'mail','/mailAction',100,1,1,'\0',NULL,NULL,NULL);
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (75,'Module','邮件用户','/**/mail/mailuser*/*',NULL,'mailuser','/mail/mailuserAction',1,2,67,'\0',NULL,NULL,NULL);
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (76,'Privilege','邮件用户列表','/**/mail/mailuserAction/list*','邮件用户列表','list','/mail/mailuserAction/list',1,2,75,'',NULL,'','html');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (77,'Privilege','邮件用户详情','/**/mail/mailuserAction/detail*','','detail','/mail/mailuserAction/detail',5,2,75,'',NULL,'','html');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (78,'Privilege','邮件用户修改','/**/mail/mailuserAction/update*','','update','/mail/mailuserAction/update',3,2,75,'\0',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (79,'Privilege','邮件用户修改页面','/**/mail/mailuserAction/update_page*','','page','/mail/mailuserAction/update_page',NULL,3,78,'',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (80,'Privilege','邮件用户新增','/**/mail/mailuserAction/create*','','create','/mail/mailuserAction/create',2,2,75,'\0',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (81,'Privilege','邮件用户新增页面','/**/mail/mailuserAction/create_page*','','page','/mail/mailuserAction/create_page',NULL,3,80,'',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (82,'Privilege','邮件用户删除','/**/mail/mailuserAction/delete*','','delete','/mail/mailuserAction/delete',4,2,75,'',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (83,'Module','邮件','/**/mail/mail*/*',NULL,'mail','/mail/mailAction',NULL,2,67,'\0',NULL,NULL,NULL);
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (84,'Privilege','邮件列表','/**/mail/mailAction/list*','邮件列表','list','/mail/mailAction/list',1,2,83,'',NULL,'','html');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (85,'Privilege','邮件详情','/**/mail/mailAction/detail*','','detail','/mail/mailAction/detail',5,2,83,'',NULL,'','html');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (86,'Privilege','邮件修改','/**/mail/mailAction/update*','','update','/mail/mailAction/update',3,2,83,'\0',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (87,'Privilege','邮件修改页面','/**/mail/mailAction/update_page*','','page','/mail/mailAction/update_page',NULL,3,86,'',NULL,'','html ');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (88,'Privilege','邮件新增','/**/mail/mailAction/create*','','create','/mail/mailAction/create',2,2,83,'\0',NULL,'','json');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (89,'Privilege','邮件新增页面','/**/mail/mailAction/create_page*','','page','/mail/mailAction/create_page',NULL,3,88,'',NULL,'','html ');
insert  into `s_resource`(`id`,`dtype`,`name`,`pattern`,`description`,`identify`,`path`,`seq`,`lvl`,`parent_id`,`leaf`,`returnExample`,`remark`,`format`) values (90,'Privilege','邮件删除','/**/mail/mailAction/delete*','','delete','/mail/mailAction/delete',4,2,83,'',NULL,'','json');

/*Table structure for table `s_role` */

CREATE TABLE `s_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dtype` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1451 DEFAULT CHARSET=utf8;

/*Data for the table `s_role` */

insert  into `s_role`(`id`,`dtype`,`name`,`create_date`,`description`) values (150,'Role','登录用户','2015-07-20 11:11:38','登录用户');
insert  into `s_role`(`id`,`dtype`,`name`,`create_date`,`description`) values (1450,'Role','系统管理员','2015-07-20 11:11:38','系统管理员');

/*Table structure for table `s_role_resource_rel` */

CREATE TABLE `s_role_resource_rel` (
  `resource_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `s_role_resource_rel` */

insert  into `s_role_resource_rel`(`resource_id`,`role_id`) values (1,1450);

/*Table structure for table `s_user` */

CREATE TABLE `s_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `accountNonExpired` char(1) DEFAULT NULL,
  `accountNonLocked` char(1) DEFAULT NULL,
  `credentialsNonExpired` char(1) DEFAULT NULL,
  `enabled` char(1) DEFAULT NULL,
  `dtype` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `login_count` bigint(20) DEFAULT NULL,
  `org_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=154 DEFAULT CHARSET=utf8;

/*Data for the table `s_user` */

insert  into `s_user`(`id`,`username`,`password`,`accountNonExpired`,`accountNonLocked`,`credentialsNonExpired`,`enabled`,`dtype`,`name`,`create_date`,`update_time`,`login_count`,`org_id`) values (153,'admin','DF10EF8509DC176D733D59549E7DBFAF','1','1','1','1','User','超级管理员','2015-07-20 11:10:56',NULL,NULL,NULL);

/*Table structure for table `s_user_role_rel` */

CREATE TABLE `s_user_role_rel` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `s_user_role_rel` */

insert  into `s_user_role_rel`(`user_id`,`role_id`) values (153,1450);

/*Table structure for table `t_fail_log` */

CREATE TABLE `t_fail_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `mail` varchar(255) NOT NULL COMMENT '接收邮箱',
  `mail_id` bigint(20) NOT NULL COMMENT '邮件ID',
  `msg` varchar(2550) DEFAULT NULL COMMENT '发送结果',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `t_fail_log` */

insert  into `t_fail_log`(`id`,`mail`,`mail_id`,`msg`,`create_time`) values (1,'m1@ss.com',1,'Mail server connection failed; nested exception is javax.mail.MessagingException: Could not connect to SMTP host: smtp.exmail.qq.com, port: 465;\n  nested exception is:\n	java.net.ConnectException: connection to smtp.exmail.qq.com timed out. Failed messages: javax.mail.MessagingException: Could not connect to SMTP host: smtp.exmail.qq.com, port: 465;\n  nested exception is:\n	java.net.ConnectException: connection to smtp.exmail.qq.com timed out',NULL);
insert  into `t_fail_log`(`id`,`mail`,`mail_id`,`msg`,`create_time`) values (2,'50949742@qq.com',1,'Mail server connection failed; nested exception is javax.mail.MessagingException: Exception reading response;\n  nested exception is:\n	java.net.SocketTimeoutException: Read timed out. Failed messages: javax.mail.MessagingException: Exception reading response;\n  nested exception is:\n	java.net.SocketTimeoutException: Read timed out',NULL);
insert  into `t_fail_log`(`id`,`mail`,`mail_id`,`msg`,`create_time`) values (3,'madfrogxin@163.com',1,'Mail server connection failed; nested exception is javax.mail.MessagingException: Exception reading response;\n  nested exception is:\n	java.net.SocketTimeoutException: Read timed out. Failed messages: javax.mail.MessagingException: Exception reading response;\n  nested exception is:\n	java.net.SocketTimeoutException: Read timed out',NULL);
insert  into `t_fail_log`(`id`,`mail`,`mail_id`,`msg`,`create_time`) values (4,'m1@ss.com',1,'Mail server connection failed; nested exception is javax.mail.MessagingException: Exception reading response;\n  nested exception is:\n	java.net.SocketTimeoutException: Read timed out. Failed messages: javax.mail.MessagingException: Exception reading response;\n  nested exception is:\n	java.net.SocketTimeoutException: Read timed out',NULL);
insert  into `t_fail_log`(`id`,`mail`,`mail_id`,`msg`,`create_time`) values (5,'50949742@qq.com',1,'Mail server connection failed; nested exception is javax.mail.MessagingException: Could not connect to SMTP host: smtp.exmail.qq.com, port: 465;\n  nested exception is:\n	java.net.ConnectException: connection to smtp.exmail.qq.com timed out. Failed messages: javax.mail.MessagingException: Could not connect to SMTP host: smtp.exmail.qq.com, port: 465;\n  nested exception is:\n	java.net.ConnectException: connection to smtp.exmail.qq.com timed out',NULL);
insert  into `t_fail_log`(`id`,`mail`,`mail_id`,`msg`,`create_time`) values (6,'madfrogxin@163.com',1,'Mail server connection failed; nested exception is javax.mail.MessagingException: Exception reading response;\n  nested exception is:\n	java.net.SocketTimeoutException: Read timed out. Failed messages: javax.mail.MessagingException: Exception reading response;\n  nested exception is:\n	java.net.SocketTimeoutException: Read timed out',NULL);

/*Table structure for table `t_mail` */

CREATE TABLE `t_mail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `content` mediumtext COMMENT '内容',
  `send_count` int(11) DEFAULT NULL COMMENT '发送次数',
  `stat` int(6) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `t_mail` */

insert  into `t_mail`(`id`,`name`,`content`,`send_count`,`stat`) values (1,'测试邮件','<p><a href=\"http://${subscribe}\" target=\"_blank\" title=\"订阅\">订阅</a></p><p><a href=\"http://${cancel}\" target=\"_self\" title=\"取消订阅\">取消订阅</a><br/></p>',35,2);

/*Table structure for table `t_mail_user` */

CREATE TABLE `t_mail_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `mail` varchar(255) DEFAULT NULL,
  `mail_md5` varchar(255) DEFAULT NULL,
  `fail_count` smallint(6) DEFAULT '0',
  `enabled` char(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `t_mail_user` */

insert  into `t_mail_user`(`id`,`username`,`mail`,`mail_md5`,`fail_count`,`enabled`) values (1,'liu','madfrogxin@163.com','eef2d69c2f45d87be7f2101b55258c26',2,'0');
insert  into `t_mail_user`(`id`,`username`,`mail`,`mail_md5`,`fail_count`,`enabled`) values (2,'50949742@qq.com','50949742@qq.com','750474d1f80e3407c9197525ebd14603',2,'1');
insert  into `t_mail_user`(`id`,`username`,`mail`,`mail_md5`,`fail_count`,`enabled`) values (3,'m1','m1@ss.com','99b47944dcf7131aa8c1e588d71b8781',1,'1');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
