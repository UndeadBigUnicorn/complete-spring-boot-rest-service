create database if not exists `jobs`;

use `jobs`;

create table if not exists `users` (
    `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
    `username` varchar(50) NOT NULL,
    `password` varchar(100) NOT NULL,
    `email` varchar(50) NOT NULL,
    `firstname` varchar(50) NOT NULL,
    `lastname` varchar(50) NOT NULL,
    `activated` bit NOT NULL DEFAULT b'1',
    `roles` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
);