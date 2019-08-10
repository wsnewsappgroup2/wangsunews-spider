
CREATE TABLE `news_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `label` varchar(64) NOT NULL,
  `source` varchar(128) NOT NULL,
  `main_image` varchar(128),
  `title` varchar(128) COLLATE utf8_unicode_ci NOT NULL,
  `create_date` datetime NOT NULL,
  `news_date` datetime NOT NULL,
  `source_comment_num` int(11),
  `topic_word` varchar(64),


  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `content_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `news_id` int(11) NOT NULL,
  `content` varchar(8000) COLLATE utf8_unicode_ci NOT NULL,
  `content_type` int(1) NOT NULL,
  `index_id` int(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `comment_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `news_id` int(11) NOT NULL,
  `author` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `comment` varchar(256) COLLATE utf8_unicode_ci NOT NULL,
  `index_id` int(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `user` (
  `openid` VARCHAR(200) COLLATE UTF8_UNICODE_CI NOT NULL,
  `unionid` VARCHAR(200) COLLATE UTF8_UNICODE_CI DEFAULT NULL,
  `session_key` VARCHAR(200) COLLATE UTF8_UNICODE_CI NOT NULL,
  `username` VARCHAR(100) COLLATE UTF8_UNICODE_CI NOT NULL,
  `sex` VARCHAR(10) COLLATE UTF8_UNICODE_CI NOT NULL,
  PRIMARY KEY (`openid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=UTF8_UNICODE_CI;