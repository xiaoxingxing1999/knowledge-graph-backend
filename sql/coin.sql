CREATE DATABASE /*!32312 IF NOT EXISTS*/`coin` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `coin`;

DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `chart`;

CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(32) DEFAULT NULL ,
  `password` varchar(32) DEFAULT NULL ,
  `email` varchar(32) DEFAULT NULL ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `chart` (
    `id` int NOT NULL AUTO_INCREMENT,
    `userId` int NOT NULL ,
    `jsonName` varchar(255) DEFAULT NULL ,
    `jsonURL` varchar(255) DEFAULT NULL ,
    `imgName` varchar(255) DEFAULT NULL ,
    `imgURL` varchar(255) DEFAULT NULL ,
    `variable` bit DEFAULT 1,
    -- 1表示可用,0表示已删除
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

INSERT INTO `user` (`id`,`username`,`password`,`email`) VALUES
(1,'Test','123123','123@qq.com'),
(2,'motherfucker2','123321','123321@qq.com');

INSERT INTO `chart` (`id`,`userId`,`jsonName`,`jsonURL`,`imgName`,`imgURL`,`variable`) VALUES
(1,1,'blob','https://software-engineering-iii.oss-cn-hangzhou.aliyuncs.com/chartJson/2021/04/18/4a92e6b8b4d84cfbbc5acbea5c73af6eblob.json','file','https://software-engineering-iii.oss-cn-hangzhou.aliyuncs.com/chartImg/2021/04/18/4a92e6b8b4d84cfbbc5acbea5c73af6efile.png',1)