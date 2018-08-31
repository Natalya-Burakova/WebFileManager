create table new_user
(
  id   varchar(255)  primary key,
  login    varchar(255) not null,
  mail     varchar(255) not null,
  password varchar(255) not null
);


create table new_file
(
  id       varchar(255) primary key ,
  count_down     integer      not null,
  data      timestamp    not null,
  file      BLOB,
  info      varchar(255) not null,
  name_file varchar(255) not null,
  status    varchar(555) not null,
  type      varchar(255) not null,
  user_id   varchar(255) references new_user(id)
);
