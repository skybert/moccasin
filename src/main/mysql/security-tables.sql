create table user
(
  id INTEGER AUTO_INCREMENT,
  user_name VARCHAR(40),
  password VARBINARY(1024),
  constraint pk_user primary key(id)
) ENGINE=InnoDB;

create table role
(
  id INTEGER AUTO_INCREMENT,
  name VARCHAR(40),
  constraint pk_role primary key(id)
) ENGINE=InnoDB;

create table user_role
(
  id INTEGER AUTO_INCREMENT,
  user_id INTEGER,
  role_id INTEGER,
  constraint pk_user_role primary key(id),
  constraint fk_user_role_user foreign key (user_id) references user(id),
  constraint fk_user_role_role foreign key (role_id) references role(id)
) ENGINE=InnoDB;

insert into role (role_name) values ('read');
insert into role (role_name) values ('write');

insert into user
  (user_name, password)
  values ('lisa', 'd3b07384d113edec49eaa6238ad5ff00');

insert into user
  (user_name, password)
  values ('john', 'c157a79031e1c40f85931829bc5fc552');

insert into user_role (user_id, role_id) values (1, 1);
insert into user_role (user_id, role_id) values (2, 2);
