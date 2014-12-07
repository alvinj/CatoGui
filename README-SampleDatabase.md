Sample Database Table
=====================

I use the following code to create a MySQL database table named
`users` to help test Cato:

````
create table users (
    id int unsigned auto_increment not null,
    email_address varchar(64) not null,
    password varchar(16) not null,
    first_name varchar(32),
    last_name varchar(32),
    date_created timestamp default now(),
    primary key (id),
    constraint unique index idx_users_unique (email_address)
) ENGINE = InnoDB;

insert into users (email_address, password, first_name, last_name)
       values('al@al.com', 'alspass', 'alvin', 'alexander');

insert into users (email_address, password, first_name, last_name)
       values('kim@kim.com', 'kimspass', 'kim', 'alexander');
````
