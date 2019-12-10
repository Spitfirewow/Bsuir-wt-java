create database if not exists coffee;
use coffee;

create table if not exists extras (
    id 				int primary key auto_increment not null,
    name			varchar(255) not null,
    price           double not null
);

create table if not exists coffees (
    id 				int primary key auto_increment not null,
    name			varchar(255) not null,
    weight_s        int not null,
    weight_m        int not null,
    weight_l        int not null
);

create table if not exists toppings (
    id				int primary key auto_increment not null,
    name            varchar(255) not null
);

create table if not exists coffee_toppings (
    id 				int primary key auto_increment not null,
    coffee_id		int not null,
    topping_id	    int not null,
    foreign key (coffee_id) references coffees(id),
    foreign key (topping_id) references toppings(id)
);

create table if not exists users (
    id              int primary key auto_increment not null,
    login           varchar(64) unique not null,
    password_hash   varchar(64) unique not null ,
    password_salt	varchar(16) not null,
    admin           boolean
);

create table if not exists orders (
    id              int primary key auto_increment not null,
    date_time       datetime not null,
    ready           boolean,
    user_id         int not null,
    foreign key (user_id) references users(id)
);

create table if not exists ordered_coffees (
    id              int primary key auto_increment not null,
    size            int not null,
    count           int not null,
    coffee_id        int not null,
    order_id        int not null,
    foreign key (coffee_id) references coffees(id),
    foreign key (order_id) references orders(id)
);

create table if not exists ordered_extras (
    id              int primary key auto_increment not null,
    count           int not null,
    extra_id        int not null,
    order_id        int not null,
    foreign key (extra_id) references extras(id),
    foreign key (order_id) references orders(id)
);

