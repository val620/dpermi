-- --------------------------------------------------------
-- 主机:                           192.168.1.49
-- 服务器版本:                        5.7.19-log - MySQL Community Server (GPL)
-- 服务器操作系统:                      Linux
-- HeidiSQL 版本:                  9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出 dpermi 的数据库结构
CREATE DATABASE IF NOT EXISTS `dpermi` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `dpermi`;


-- 导出  表 dpermi.brand 结构
CREATE TABLE IF NOT EXISTS `brand` (
  `brand_id` varchar(36) NOT NULL COMMENT '主键',
  `brand_name` varchar(30) NOT NULL COMMENT '名称',
  `logo` varchar(200) DEFAULT NULL COMMENT '图标',
  PRIMARY KEY (`brand_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='品牌表';

-- 正在导出表  dpermi.brand 的数据：~4 rows (大约)
/*!40000 ALTER TABLE `brand` DISABLE KEYS */;
INSERT INTO `brand` (`brand_id`, `brand_name`, `logo`) VALUES
	('b1', '无印良品', NULL),
	('b2', 'Niki', NULL),
	('b3', 'Apple', NULL),
	('b4', '小米', NULL),
	('b5', '华为', NULL),
	('b6', '一加', NULL);
/*!40000 ALTER TABLE `brand` ENABLE KEYS */;


-- 导出  表 dpermi.data_object 结构
CREATE TABLE IF NOT EXISTS `data_object` (
  `data_object_id` varchar(50) NOT NULL COMMENT '主键',
  `data_type` int(11) DEFAULT '0' COMMENT '数据类型',
  `field_name` varchar(100) DEFAULT NULL COMMENT '字段名称',
  `class_name` varchar(100) NOT NULL COMMENT '实体名称',
  PRIMARY KEY (`data_object_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据权限关联对象';

-- 正在导出表  dpermi.data_object 的数据：~4 rows (大约)
/*!40000 ALTER TABLE `data_object` DISABLE KEYS */;
INSERT INTO `data_object` (`data_object_id`, `data_type`, `field_name`, `class_name`) VALUES
	('1', 0, 'menuId', 'com.dpermi.domain.Menu'),
	('20', 20, 'brandId', 'com.dpermi.domain.Product'),
	('21', 20, 'productName', 'com.dpermi.domain.Product'),
	('22', 20, 'productImage', 'com.dpermi.domain.Product'),
	('3', 1, 'brandId', 'com.dpermi.domain.Product');
/*!40000 ALTER TABLE `data_object` ENABLE KEYS */;


-- 导出  表 dpermi.menu 结构
CREATE TABLE IF NOT EXISTS `menu` (
  `menu_id` varchar(36) NOT NULL COMMENT '主键',
  `parent_id` varchar(36) DEFAULT NULL COMMENT '父id',
  `menu_name` varchar(20) NOT NULL COMMENT '菜单名称',
  `menu_code` varchar(30) NOT NULL COMMENT '菜单代码，需唯一',
  `menu_url` varchar(200) DEFAULT NULL COMMENT '菜单链接',
  `menu_order` int(11) NOT NULL COMMENT '排序号',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- 正在导出表  dpermi.menu 的数据：~10 rows (大约)
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` (`menu_id`, `parent_id`, `menu_name`, `menu_code`, `menu_url`, `menu_order`) VALUES
	('c461a1c15bbf4b61a7839d96509cd956', 'm1', '菜单1.3', 'menu8', '8', 3),
	('f9113e43ef58471291819d61ebc1ec30', 'm4', '菜单1.2.3', 'menu7', 'seven.html', 3),
	('m1', NULL, '菜单1', 'menu1', '', 1),
	('m2', '', '角色', 'role', 'html/role/index.html', 3),
	('m3', 'm1', '菜单1.1', 'menu3', 'html/third.html', 1),
	('m4', 'm1', '菜单1.2', 'menu4', '', 2),
	('m5', 'm4', '菜单1.2.1', 'menu5', 'five.html', 1),
	('m6', 'm4', '菜单1.2.2', 'menu6', 'six.html', 2),
	('m7', '', '产品', 'product', 'html/product/index.html', 4),
	('m8', NULL, '用户', 'user', 'html/user/index.html', 2),
	('m9', '', '菜单', 'menu', 'html/menu/index.html', 150);
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;


-- 导出  表 dpermi.method_object 结构
CREATE TABLE IF NOT EXISTS `method_object` (
  `method_object_id` varchar(50) NOT NULL COMMENT '主键',
  `method_id` varchar(255) NOT NULL COMMENT '方法ID',
  `method_name` varchar(255) NOT NULL COMMENT '方法名称',
  `url` varchar(255) DEFAULT NULL COMMENT '链接',
  `parent_id` varchar(50) DEFAULT NULL COMMENT '父id',
  `order_no` int(11) NOT NULL COMMENT '排序号',
  PRIMARY KEY (`method_object_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='方法对象';

-- 正在导出表  dpermi.method_object 的数据：~23 rows (大约)
/*!40000 ALTER TABLE `method_object` DISABLE KEYS */;
INSERT INTO `method_object` (`method_object_id`, `method_id`, `method_name`, `url`, `parent_id`, `order_no`) VALUES
	('007675fc0f2344a5ba6f0f373a0e8757', 'com.dpermi.controller.productcontroller.getproducts', '搜索', '/product/getproducts', 'product', 4),
	('01fc913873d64c898f5ab9bf8bb70eb1', 'com.dpermi.controller.productcontroller.addproduct', '添加', '/product/addproduct', 'product', 2),
	('0abbdffba2ab47b786a6d2b61f2beab8', 'com.dpermi.controller.usercontroller.adduserrole', '保存角色-新增', '/user/adduserrole', 'user', 2),
	('10888eec4eb64a1f83e20bc36e97b625', 'com.dpermi.controller.authorizecontroller.getmenupermission', '菜单权限', '/auth/menupermission', 'role', 1),
	('1408f14680c1418fa1a9eeac6d14af03', 'com.dpermi.controller.productcontroller.getbrands', '品牌下拉框', '/product/getbrands', 'product', 3),
	('337cd252b53b4494bd32ff3a30f215d5', 'com.dpermi.controller.menucontroller.getmenutree', '加载菜单', '/menu/getmenutree', 'menu', 5),
	('3c5ab9aa72eb41bfaa9c453b5110b79d', 'com.dpermi.controller.rolecontroller.adduserrole', '保存用户-新增', '/role/adduserrole', 'role', 8),
	('42da54bbd4cc44f992830076298ba7a3', 'com.dpermi.controller.rolecontroller.saverolepermission', '保存菜单权限', '/role/saverolepermission', 'role', 2),
	('4d44cd182c8a43b1a03e9d5787cd114b', 'com.dpermi.controller.authorizecontroller.getdatapermission', '数据权限', '/auth/datapermission', 'role', 2),
	('51bc83fe6b654724a9aa0ac6ddf1e163', 'com.dpermi.controller.menucontroller.addmenu', '添加', '/menu/addmenu', 'menu', 2),
	('6b384f8a00a94ff38e63349698b2bccd', 'com.dpermi.controller.usercontroller.updateuser', '编辑', '/user/updateuser', 'user', 5),
	('6f29ea6f2875462da2a6a236210692c1', 'com.dpermi.controller.menucontroller.getall', '所有菜单', '/menu/getall', 'menu', 1),
	('727ae80995454eb0a0500103d0dc48cc', 'com.dpermi.controller.usercontroller.adduser', '添加', '/user/adduser', 'user', 1),
	('7a233eb249834b00912805772ccfc700', 'com.dpermi.controller.menucontroller.getmenus', '搜索', '/menu/getmenus', 'menu', 4),
	('8f9303626dfd43238de195ca732ca0d2', 'com.dpermi.controller.usercontroller.deleteuserrole', '保存角色-删除', '/user/deleteuserrole', 'user', 1),
	('9136cca80ef24c0296e33bcd6b63524f', 'com.dpermi.controller.usercontroller.getusers', '搜索', '/user/getusers', 'user', 2),
	('a89e191446a3404aa96fa816e5eecf91', 'com.dpermi.controller.rolecontroller.deleterolepermission', '保存数据权限-删除', '/role/deleterolepermission', 'role', 2),
	('a9ae4e133c4e4df29b9d2e6c71e0b734', 'com.dpermi.controller.rolecontroller.addrole', '添加', '/role/addrole', 'role', 4),
	('b3f7543db0f84163b1baeb13a813dbc4', 'com.dpermi.controller.rolecontroller.deleteuserrole', '保存用户-删除', '/role/deleteuserrole', 'role', 6),
	('c9710efa655a444cb2e062cf14f90d98', 'com.dpermi.controller.productcontroller.updateproduct', '修改', '/product/updateproduct', 'product', 1),
	('d88b0fee636144348ccd08ce621e80da', 'com.dpermi.controller.rolecontroller.updaterole', '修改', '/role/updaterole', 'role', 3),
	('d9f92dd0c60844d98361688b9f93a91e', 'com.dpermi.controller.rolecontroller.getroles', '搜索', '/role/getroles', 'role', 1),
	('e910a2d3cffa45aeb01671c88936f2f1', 'com.dpermi.controller.rolecontroller.addrolepermission', '保存数据权限-添加', '/role/addrolepermission', 'role', 3),
	('fb659dd8ea5f40cca283c37e4ee2bee7', 'com.dpermi.controller.menucontroller.updatemenu', '更新', '/menu/updatemenu', 'menu', 3);
/*!40000 ALTER TABLE `method_object` ENABLE KEYS */;


-- 导出  表 dpermi.product 结构
CREATE TABLE IF NOT EXISTS `product` (
  `product_id` varchar(36) NOT NULL COMMENT '主键',
  `product_name` varchar(30) NOT NULL COMMENT '名称',
  `product_image` varchar(100) DEFAULT NULL COMMENT '产品图片',
  `brand_id` varchar(36) NOT NULL COMMENT '品牌ID',
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品表';

-- 正在导出表  dpermi.product 的数据：~7 rows (大约)
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` (`product_id`, `product_name`, `product_image`, `brand_id`) VALUES
	('cf0587cc2c5d48f88762191b6bf259b8', '连衣裙', '', 'b2'),
	('d45dad4bcb544fbdb0b1e733a07808e3', '一加手机', '', 'b6'),
	('p1', 'T恤', NULL, 'b1'),
	('p2', '长裤', '', 'b1'),
	('p3', 'iphone x', 'a.jpg', 'b3'),
	('p4', '小米手机', NULL, 'b4'),
	('p5', '华为手机', NULL, 'b5');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;


-- 导出  表 dpermi.region 结构
CREATE TABLE IF NOT EXISTS `region` (
  `region_id` varchar(10) NOT NULL COMMENT '主键',
  `region_name` varchar(16) NOT NULL COMMENT '名称',
  PRIMARY KEY (`region_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='地区表';

-- 正在导出表  dpermi.region 的数据：~34 rows (大约)
/*!40000 ALTER TABLE `region` DISABLE KEYS */;
INSERT INTO `region` (`region_id`, `region_name`) VALUES
	('11', '北京市'),
	('12', '天津市'),
	('13', '河北省'),
	('14', '山西省'),
	('15', '内蒙古自治区'),
	('21', '辽宁省'),
	('22', '吉林省'),
	('23', '黑龙江省'),
	('31', '上海市'),
	('32', '江苏省'),
	('33', '浙江省'),
	('34', '安徽省'),
	('35', '福建省'),
	('36', '江西省'),
	('37', '山东省'),
	('41', '河南省'),
	('42', '湖北省'),
	('43', '湖南省'),
	('44', '广东省'),
	('45', '广西壮族自治区'),
	('46', '海南省'),
	('50', '重庆市'),
	('51', '四川省'),
	('52', '贵州省'),
	('53', '云南省'),
	('54', '西藏自治区'),
	('61', '陕西省'),
	('62', '甘肃省'),
	('63', '青海省'),
	('64', '宁夏回族自治区'),
	('65', '新疆维吾尔自治区'),
	('71', '台湾省'),
	('81', '香港特别行政区'),
	('82', '澳门特别行政区');
/*!40000 ALTER TABLE `region` ENABLE KEYS */;


-- 导出  表 dpermi.role 结构
CREATE TABLE IF NOT EXISTS `role` (
  `role_id` varchar(50) NOT NULL COMMENT '主键',
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `begin_time` datetime DEFAULT NULL COMMENT '生效时间',
  `end_time` datetime DEFAULT NULL COMMENT '失效时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色';

-- 正在导出表  dpermi.role 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` (`role_id`, `role_name`, `description`, `begin_time`, `end_time`) VALUES
	('1', '角色01', '描述1', '2018-07-08 13:45:18', '2018-10-08 13:45:25'),
	('2', '角色02', '描述2', '2018-08-08 13:45:18', '2018-11-08 13:45:18'),
	('6ffe874c195e48c4987609d9fdefbeb3', '管理员', '', NULL, NULL);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;


-- 导出  表 dpermi.role_permission 结构
CREATE TABLE IF NOT EXISTS `role_permission` (
  `role_permission_id` varchar(50) NOT NULL COMMENT '主键',
  `role_id` varchar(50) NOT NULL COMMENT '角色',
  `object_id` varchar(50) NOT NULL COMMENT '数据权限对象',
  `data_type` int(11) NOT NULL DEFAULT '0' COMMENT '数据类型',
  PRIMARY KEY (`role_permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限';

-- 正在导出表  dpermi.role_permission 的数据：~25 rows (大约)
/*!40000 ALTER TABLE `role_permission` DISABLE KEYS */;
INSERT INTO `role_permission` (`role_permission_id`, `role_id`, `object_id`, `data_type`) VALUES
	('00b5f9129388412c9b455b32ff3fde30', '1', 'c461a1c15bbf4b61a7839d96509cd956', 0),
	('0e48bd2da45d46c7ae39286fdc693c35', '6ffe874c195e48c4987609d9fdefbeb3', 'd9f92dd0c60844d98361688b9f93a91e', 0),
	('1926e69dedf44840adcd4922a71b7d0c', '2', '4286115d312f4e4a9e43ab3d71eb41d4', 0),
	('2', '1', 'b1', 1),
	('226a95b525a3480d82cb89d7972f113c', '1', 'm6', 0),
	('22934721b0344e7fbc97aec6297fe7ea', '6ffe874c195e48c4987609d9fdefbeb3', '337cd252b53b4494bd32ff3a30f215d5', 0),
	('24c7e735d8c146e09e5d55b0ca4871d8', '6ffe874c195e48c4987609d9fdefbeb3', 'b1', 1),
	('3', '1', 'b2', 1),
	('3637707e4cc240e2926b61b1d7511fab', '1', 'b3', 1),
	('3b0c516d31c7452ea37aca5cba00da09', '6ffe874c195e48c4987609d9fdefbeb3', 'b6', 1),
	('5fd7e056ead2483ebfabe86dc1824197', '2', 'f9db115bb6484b4d8ce49fee4bb7c50d', 0),
	('62b0bd37d5754d7e8d1bafae33179151', '1', 'b3', 1),
	('6e7238772b6746beae082dbc55d9977b', '6ffe874c195e48c4987609d9fdefbeb3', '007675fc0f2344a5ba6f0f373a0e8757', 0),
	('71114dc8e4c54079a4ad85ccd9da55fb', '1', 'b1', 1),
	('8173ca6562464f4a8bfd35cc3b9c6174', '1', 'm3', 0),
	('82b75196bc1d413a8c85ab3fdfbf6669', '1', 'b2', 1),
	('8c0d4523088e4c91be613ecb5e9d1d97', '6ffe874c195e48c4987609d9fdefbeb3', 'f9113e43ef58471291819d61ebc1ec30', 0),
	('8edc7dbf92f244b7ad3c24a4048abbd2', '1', 'm1', 0),
	('9840e2398fb34f1da5e642c859dc98d1', '1', 'm4', 0),
	('a8032d2b4c8c461482c7000fc04977fd', '1', 'm5', 0),
	('bf79db2006794655831ccd6285fcb008', '1', 'f9113e43ef58471291819d61ebc1ec30', 0),
	('d026a2b3916245eea5286dc9b734c942', '6ffe874c195e48c4987609d9fdefbeb3', '10888eec4eb64a1f83e20bc36e97b625', 0),
	('d03ce4d64fa04fd3a6a8fc684b11889c', '6ffe874c195e48c4987609d9fdefbeb3', '1408f14680c1418fa1a9eeac6d14af03', 0),
	('da3d49284f1b490880d00b66852a060d', '1', 'on', 1),
	('fff7d9f2efdf46339e221133fea949ae', '6ffe874c195e48c4987609d9fdefbeb3', 'c9710efa655a444cb2e062cf14f90d98', 0);
/*!40000 ALTER TABLE `role_permission` ENABLE KEYS */;


-- 导出  表 dpermi.user 结构
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` varchar(36) NOT NULL COMMENT '主键',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `cellphone` varchar(20) NOT NULL COMMENT '手机号',
  `password` varchar(500) NOT NULL COMMENT '密码',
  `gender` tinyint(4) DEFAULT NULL COMMENT '性别',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `user_state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '用户状态（未验证：0，验证中：1，已验证：2，验证不通过：3，黑名单：4）',
  `qq` varchar(12) DEFAULT NULL COMMENT 'QQ号',
  `id_card` varchar(18) DEFAULT NULL COMMENT '身份证',
  `icon` varchar(200) DEFAULT NULL COMMENT '头像',
  `introduction` varchar(200) DEFAULT NULL COMMENT '个人介绍',
  `address` varchar(36) DEFAULT NULL COMMENT '地址',
  `is_delete` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除，0未删除、1已删除',
  `is_admin` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0不是管理员，1是管理员',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- 正在导出表  dpermi.user 的数据：~5 rows (大约)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`user_id`, `name`, `email`, `cellphone`, `password`, `gender`, `birthday`, `user_state`, `qq`, `id_card`, `icon`, `introduction`, `address`, `is_delete`, `is_admin`) VALUES
	('3545ed93835d413fbecb684e4b3ee399', '老陈', '', '18800001111', '9f2d3b9ecb2a26c209d2a62b92bbacddc10b5cdac9a6ddd4cd31bbbc48ba444a9f41705c0dd68e693c2d94f2ea01de8a', 1, NULL, 0, '', NULL, '07.jpg', '', '', b'0', 0),
	('4ae9ed9779c5401f914603fe3cf200cb', '三毛', NULL, '17700000001', '9f2d3b9ecb2a26c209d2a62b92bbacddc10b5cdac9a6ddd4cd31bbbc48ba444a9f41705c0dd68e693c2d94f2ea01de8a', 0, NULL, 1, NULL, 'sdfagfsadf', '05.jpg', NULL, NULL, b'0', 0),
	('6cfe2d5d75944e3fa72c3e7331b09b97', '小飞', '', '13812345678', '9f2d3b9ecb2a26c209d2a62b92bbacddc10b5cdac9a6ddd4cd31bbbc48ba444a9f41705c0dd68e693c2d94f2ea01de8a', 0, NULL, 0, '', NULL, '08.jpg', '', '', b'0', 0),
	('82d0ee79f90f4af9a62cc76fcd3dae32', 'val620', 'val620@126.com', '17700000002', '9f2d3b9ecb2a26c209d2a62b92bbacddc10b5cdac9a6ddd4cd31bbbc48ba444a9f41705c0dd68e693c2d94f2ea01de8a', 1, NULL, 0, '1313213', NULL, '06.jpg', NULL, '广州', b'0', 0),
	('d8736cfb59534666b157fc0ce19393ec', '无名', '', '17700000003', 'c323febce83ee2e7d982fec41ca179870beb89cc061218b091d4bd5c21e7bb509f41705c0dd68e693c2d94f2ea01de8a', 1, NULL, 0, NULL, NULL, '02.jpg', NULL, '', b'0', 1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;


-- 导出  表 dpermi.user_permission 结构
CREATE TABLE IF NOT EXISTS `user_permission` (
  `user_permission_id` varchar(50) NOT NULL COMMENT '主键',
  `user_id` varchar(50) NOT NULL COMMENT '用户',
  `object_id` varchar(50) NOT NULL COMMENT '数据权限对象',
  `data_type` int(11) DEFAULT '0',
  PRIMARY KEY (`user_permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户数据权限';

-- 正在导出表  dpermi.user_permission 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `user_permission` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_permission` ENABLE KEYS */;


-- 导出  表 dpermi.user_role 结构
CREATE TABLE IF NOT EXISTS `user_role` (
  `user_role_id` varchar(50) NOT NULL COMMENT '主键',
  `user_id` varchar(50) NOT NULL COMMENT '用户',
  `role_id` varchar(50) NOT NULL COMMENT '角色',
  PRIMARY KEY (`user_role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色';

-- 正在导出表  dpermi.user_role 的数据：~4 rows (大约)
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` (`user_role_id`, `user_id`, `role_id`) VALUES
	('1', '4ae9ed9779c5401f914603fe3cf200cb', '1'),
	('65f016f53a1d42799fd8f36209839947', '82d0ee79f90f4af9a62cc76fcd3dae32', '6ffe874c195e48c4987609d9fdefbeb3'),
	('e8bb5ed8081c4662b38ff6f2af3e16b9', '4ae9ed9779c5401f914603fe3cf200cb', '6ffe874c195e48c4987609d9fdefbeb3'),
	('f3376958a86945d6802180a81d5530fe', '6cfe2d5d75944e3fa72c3e7331b09b97', '2');
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
