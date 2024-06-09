CREATE TABLE `content_type` (
  `content_type_id` int NOT NULL,
  `content_type` varchar(50) NOT NULL,
  PRIMARY KEY (`content_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `content_type` (`content_type_id`,`content_type`) VALUES (12,'관광지');
INSERT INTO `content_type` (`content_type_id`,`content_type`) VALUES (14,'문화시설');
INSERT INTO `content_type` (`content_type_id`,`content_type`) VALUES (15,'축제/공연/행사');
INSERT INTO `content_type` (`content_type_id`,`content_type`) VALUES (25,'여행코스');
INSERT INTO `content_type` (`content_type_id`,`content_type`) VALUES (28,'레포츠');
INSERT INTO `content_type` (`content_type_id`,`content_type`) VALUES (32,'숙박');
INSERT INTO `content_type` (`content_type_id`,`content_type`) VALUES (38,'쇼핑');
INSERT INTO `content_type` (`content_type_id`,`content_type`) VALUES (39,'음식점');

CREATE TABLE `members` (
  `user_id` varchar(50) NOT NULL,
  `user_password` varchar(100) NOT NULL,
  `user_name` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `join_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


CREATE TABLE `board` (
  `board_no` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(50) NOT NULL,
  `title` varchar(100) NOT NULL,
  `content` text NOT NULL,
  `hits` int DEFAULT '0',
  `create_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`board_no`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb3;

CREATE TABLE `comment` (
  `comment_no` int NOT NULL AUTO_INCREMENT,
  `board_no` int DEFAULT NULL,
  `user_id` varchar(16) DEFAULT NULL,
  `content` varchar(1000) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`comment_no`),
  KEY `board_no` (`board_no`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`board_no`) REFERENCES `board` (`board_no`),
  CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `members` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
