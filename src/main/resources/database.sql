
DROP  table if exists user;
create table user(
   id int AUTO_INCREMENT primary key,
   openid varchar(255) default null,
   username varchar(255) unique not null,
   password varchar(255) not null,
   name varchar(255) default null,
   sex int default 0,
   identity_card varchar(255) default null,
   photo varchar(255) default null,
   house_no varchar(255) default null,
   health_status int not null default 0,
   access_times int not null default 0,
   is_locked boolean  not null default false
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP  table if exists inspectors;
create table inspectors(
    id int AUTO_INCREMENT primary key,
    openid varchar(255) default null,
    uesrname varchar(255) unique not null,
    password varchar(255) not null
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP  table if exists record_form;
create table record_form(
   id int AUTO_INCREMENT primary key,
   temperature decimal(3,1) not null,
   time datetime not null,
   user_id int not null,
   inspectors_id int not null,
   foreign key(user_id) references user(id),
   foreign key(inspectors_id) references inspectors(id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP  table if exists admin;
create table admin(
    id int AUTO_INCREMENT primary key,
    username varchar(255) unique not null,
    password varchar(255) not null
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

