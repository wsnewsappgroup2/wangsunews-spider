
-- 新闻主要信息表
CREATE TABLE IF NOT EXISTS `news_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `label` varchar(64) NOT NULL,
  `source` varchar(128) NOT NULL,
  `main_image` varchar(128),
  `title` varchar(128) COLLATE utf8_unicode_ci NOT NULL,
  `create_date` datetime NOT NULL,
  `news_date` datetime NOT NULL,
  `source_comment_num` int(11),
  `topic_word` varchar(64)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 新闻具体内容表
CREATE TABLE IF NOT EXISTS `content_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `news_id` int(11) NOT NULL,
  `content` varchar(8000) COLLATE utf8_unicode_ci NOT NULL,
  `content_type` int(1) NOT NULL,
  `index_id` int(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 用户评论表
CREATE TABLE IF NOT EXISTS `comment_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `news_id` int(11) NOT NULL,
  `author` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `comment` varchar(256) COLLATE utf8_unicode_ci NOT NULL,
  `index_id` int(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



-- 用户信息表
CREATE TABLE `user` (
  `id` INT(11) AUTO_INCREMENT NOT NULL PRIMARY KEY,
  `openid` VARCHAR(150) COLLATE UTF8_UNICODE_CI NOT NULL UNIQUE KEY,
  `token` VARCHAR(300) COLLATE UTF8_UNICODE_CI NOT NULL,
  `session_key` VARCHAR(200) COLLATE UTF8_UNICODE_CI DEFAULT NULL,
  `avartarUrl` VARCHAR(800) COLLATE UTF8_UNICODE_CI DEFAULT NULL,
  `nickName` VARCHAR(70) COLLATE UTF8_UNICODE_CI DEFAULT NULL,
  `country` VARCHAR(80) COLLATE UTF8_UNICODE_CI DEFAULT NULL,
  `province` VARCHAR(80) COLLATE UTF8_UNICODE_CI DEFAULT NULL,
  `city` VARCHAR(80) COLLATE UTF8_UNICODE_CI DEFAULT NULL,
  `gender` INT DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=UTF8_UNICODE_CI;


-- 新闻栏目ID和栏目信息映射表
CREATE TABLE IF NOT EXISTS `label_column_mapping` (
  `label_id` INT(11) AUTO_INCREMENT NOT NULL PRIMARY KEY,
  `label` VARCHAR(64) NOT NULL,
  `column_name` VARCHAR(60) COLLATE UTF8_UNICODE_CI NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=UTF8_UNICODE_CI;
INSERT INTO `label_column_mapping`(`label`,`column_name`) VALUES ('sport','体育');
INSERT INTO `label_column_mapping`(`label`,`column_name`) VALUES ('ent','娱乐');
INSERT INTO `label_column_mapping`(`label`,`column_name`) VALUES ('politics','体育');
INSERT INTO `label_column_mapping`(`label`,`column_name`) VALUES ('recommend','推荐');


-- 用户个人栏目表
CREATE TABLE IF NOT EXISTS `user_column_mapping`(
	`openid` VARCHAR(150) COLLATE UTF8_UNICODE_CI NOT NULL,
	`label_id` INT(11) AUTO_INCREMENT NOT NULL,
	CONSTRAINT FOREIGN KEY(`openid`) REFERENCES `user`(`openid`)
	ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT FOREIGN KEY(`label_id`) REFERENCES `label_column_mapping`(`label_id`)
	ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=UTF8_UNICODE_CI;


