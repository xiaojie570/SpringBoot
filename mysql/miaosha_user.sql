use miaosha;
CREATE TABLE `miaosha_user` (
  `id` int(20) NOT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `salt` varchar(10) DEFAULT NULL,
  `head` varchar(128) DEFAULT NULL,
  `register_date` datetime DEFAULT NULL,
  `last_login_date` datetime DEFAULT NULL,
  `login_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

