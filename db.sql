CREATE DATABASE `shard0`CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `shard0`;


CREATE TABLE `t_order0` (
                            `order_id` INT(11) NOT NULL,
                            `user_id` INT(11) NOT NULL,
                            PRIMARY KEY (`order_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;
CREATE TABLE `t_order1` (
                            `order_id` INT(11) NOT NULL,
                            `user_id` INT(11) NOT NULL,
                            PRIMARY KEY (`order_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;


CREATE DATABASE `shard1`CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `shard1`;

CREATE TABLE `t_order0` (
                            `order_id` INT(11) NOT NULL,
                            `user_id` INT(11) NOT NULL,
                            PRIMARY KEY (`order_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;
CREATE TABLE `t_order1` (
                            `order_id` INT(11) NOT NULL,
                            `user_id` INT(11) NOT NULL,
                            PRIMARY KEY (`order_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;