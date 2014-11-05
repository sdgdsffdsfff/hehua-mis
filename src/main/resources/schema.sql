create table `aclusers` (
  id int(11) not null primary key auto_increment,
  username varchar(100) not null,
  password varchar(100) not null,
  nickname varchar(100) not null,
  email varchar(100) not null,
  enabled boolean not null,
  created_time datetime not null,
  salt varchar(100) not null,
  role varchar(100) not null,
  unique key `uidx_username` (username),
  unique key `uidx_nickname` (nickname),
  unique key `uidx_email` (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;