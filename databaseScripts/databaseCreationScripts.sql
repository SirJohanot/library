create database library;
use library;
create table user(
id int primary key auto_increment,
name varchar(50) not null,
surname varchar(50) not null,
login varchar(50) not null,
password varchar(32) not null,
role enum('reader', 'librarian', 'admin') not null
);

create table genre(
id int primary key auto_increment,
name varchar(50) not null
);

create table publisher(
id int primary key auto_increment,
name varchar(50) not null
);

create table book(
id int primary key auto_increment,
title varchar(255) not null,
genre_id int not null,
publisher_id int not null,
publishment_year YEAR not null,
amount int unsigned not null,
foreign key (genre_id) references genre(id),
foreign key (publisher_id) references publisher(id)
);

create table author(
id int primary key auto_increment,
name varchar(50) not null,
surname varchar(50) not null
);

create table book_author(
book_id int not null,
author_id int not null,
foreign key (book_id) references book(id),
foreign key (author_id) references author(id)
);

create table book_order(
id int primary key auto_increment,
book_id int not null,
user_id int not null,
rental_type enum('to_reading_hall', 'out_of_library') not null,
start_date date not null,
end_date date not null,
return_date date,
state enum('order_placed', 'order_approved', 'book_taken', 'book_returned') not null,
foreign key (book_id) references book(id),
foreign key (user_id) references user(id)
);