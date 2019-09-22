create database seckill;

use seckill;

create table seckill(
`seckill_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'seckill id',
`name` varchar(120) NOT NULL COMMENT 'merchandise name',
`number` int NOT NULL COMMENT 'number of inventory',
`start_time` TIMESTAMP NOT NULL COMMENT 'start time',
`end_time` TIMESTAMP NOT NULL COMMENT 'end time',
`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',

PRIMARY KEY (seckill_id),
key index_start_time(start_time),
key index_end_time(end_time),
key index_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='Seckill Inventory Table';

insert into
  seckill(name, number, start_time, end_time)
values
  ('$500 for an iphone XS', 10000, '2019-1-1 00:00:00', '2019-6-30 00:00:00'),
  ('$450 for an iphone X', 20000, '2019-6-30 00:00:00', '2019-12-30 00:00:00'),
  ('$400 for an iphone 8', 30000, '2019-6-30 00:00:00', '2019-12-30 00:00:00'),
  ('$300 for an ipad', 40000, '2020-1-1 00:00:00', '2020-9-30 00:00:00');

CREATE TABLE success_killed(
`seckill_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'seckill id',
`user_phone` BIGINT NOT NULL COMMENT 'phone number of users',
`status` TINYINT NOT NULL DEFAULT -1 COMMENT 'status: -1, invalid; 0, successful seckilled; 1, paid; 2, delivered',
`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',

PRIMARY KEY (seckill_id, user_phone),
key index_create_time(create_time)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8 COMMENT='Successful Seckill Detail Table';

