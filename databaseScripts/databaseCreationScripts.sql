create database library;
use library;
create table user
(
    id         int                                   primary key auto_increment,
    name       varchar(50)                           not null,
    surname    varchar(50)                           not null,
    login      varchar(50)                           not null,
    password   varchar(32)                           not null,
    is_blocked boolean                               not null default false,
    role       enum ('reader', 'librarian', 'admin') not null
);

create table genre
(
    id   int         primary key auto_increment,
    name varchar(50) not null
);

create table publisher
(
    id   int         primary key auto_increment,
    name varchar(50) not null
);

create table book
(
    id               int          primary key auto_increment,
    title            varchar(255) not null,
    genre_id         int          not null,
    foreign key (genre_id)        references genre (id),
    publisher_id     int          not null,
    foreign key (publisher_id)    references publisher (id),
    publishment_year int          not null,
    amount           int unsigned not null,
    is_deleted       boolean      not null default false
);

create table author
(
    id   int          primary key auto_increment,
    name varchar(255) not null
);

create table book_author
(
    book_id   int           not null,
    foreign key (book_id)   references book (id),
    author_id int           not null,
    foreign key (author_id) references author (id)
);

create table book_order
(
    id          int                                                                                      primary key auto_increment,
    book_id     int                                                                                      not null,
    foreign key (book_id)                                                                                references book (id),
    user_id     int                                                                                      not null,
    foreign key (user_id)                                                                                references user (id),
    rental_type enum ('to_reading_hall', 'out_of_library')                                               not null,
    start_date  date                                                                                     not null,
    end_date    date                                                                                     not null,
    return_date date,
    state       enum ('order_placed', 'order_approved', 'order_declined', 'book_taken', 'book_returned') not null
);