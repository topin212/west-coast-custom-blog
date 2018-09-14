
drop database if exists `test_blog`;

create database `test_blog`;
use `test_blog`;

drop table if exists `publisher`;

create table `publisher` (
  `id` int not null auto_increment,
  `name` varchar(64) not null,
  `pass_hash` varchar(1024) not null,
  `reg_date` datetime not null,
  `role_id` int not null,
  primary key (`id`)
);

drop table if exists `user_role`;

create table `user_role` (
    `id` int not null auto_increment,
    `role_name` varchar(32) not null,
    primary key (`id`)
);

drop table if exists `post`;

create table `post` (
  `id` integer not null auto_increment,
  `publisher_id` int not null,
  `post_title` varchar(256) not null,
  `post_text` mediumtext not null,
  `post_date` datetime not null,
  `like_count` int not null,
  `is_deleted` bit not null default 0,
  primary key (`id`)
);


alter table `post` add foreign key (publisher_id) references `publisher` (`id`);
alter table `publisher` add foreign key (role_id) references `user_role` (`id`);

-----------------
-- dummy data: --
-----------------

insert into `user_role`
(`role_name`) values
('PUBLISHER');


insert into `publisher`
(`name`,`pass_hash`,`reg_date`, `role_id`) values
('Petya','dGVzdHBhc3MK','select NOW()', 1);

insert into `publisher`
(`name`,`pass_hash`,`reg_date`, `role_id`) values
('Vasya','dGVzdHBhc3MK','select NOW()', 1);

insert into `post`
(`publisher_id`,`post_title`,`post_text`,`post_date`,`like_count`,`is_deleted`) values
('1','testTitle', 'testPost','select NOW()','0','0');

insert into `post`
(`publisher_id`,`post_title`,`post_text`,`post_date`,`like_count`,`is_deleted`) values
('1','testTitle1', 'testPost1','select NOW()','0','0');

insert into `post`
(`publisher_id`,`post_title`,`post_text`,`post_date`,`like_count`,`is_deleted`) values
('2','user2TestTitle', 'user2TestPost','select NOW()','0','0');



--------------------
-- test requests: --
--------------------

-- get all authors:
select * from publisher;

-- get all posts:
select * from post;

-- get own posts:
select * from post where publisher_id = 1;

-- join:
select post.id, name, post_title, post_text, post_date, like_count, is_deleted
from post
join publisher
on (publisher_id = publisher.id);