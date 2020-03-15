create database if not exists `jobs`;

use `jobs`;

create table if not exists `users` (
    `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
    `username` varchar(50) NOT NULL,
    `password` varchar(100) NOT NULL,
    `enabled` bit NOT NULL DEFAULT b'1',
    `roles` varchar(50) NOT NULL,
    PRIMARY KEY (`id`)
);