create database if not exists coldpulse;

use coldpulse;

create table if not exists venue
(
    id       int auto_increment
        primary key,
    name     varchar(255) not null,
    location varchar(255) null,
    capacity int          not null,
    state    varchar(255) not null
);

create table if not exists sys_user
(
    `user_id` char(19) NOT NULL,
    `nick_name` varchar(30) NOT NULL,
    `email` varchar(50)  NOT NULL,
    `phonenumber` varchar(11)  ,
    `sex` char(1) ,
    `password` varchar(100) NOT NULL,
    `status` char(1) NULL DEFAULT '0',
    PRIMARY KEY (`user_id`) USING BTREE
);

create table if not exists concert
(
    id       int auto_increment
        primary key,
    venue_id int not null,
    constraint concert_venue_id_fk
        foreign key (venue_id) references venue (id)
);

create table if not exists concert_class
(
    id                  int auto_increment
        primary key,
    class_name          varchar(64) not null,
    concert_id          int         not null,
    price_in_usd        double      not null,
    price_in_local_curr double      not null,
    capacity            int         not null,
    available_seats     int         not null,
    constraint concert_class_concert_id_fk
        foreign key (concert_id) references concert (id)
);

create table if not exists charity_event
(
    id         int auto_increment
        primary key,
    name       varchar(255) not null,
    start_time timestamp    not null,
    duration   bigint       not null,
    point      int          not null,
    concert_id int          null,
    constraint charity_event_concert_id_fk
        foreign key (concert_id) references concert (id)
);

create table if not exists concert_schedule
(
    id         int auto_increment
        primary key,
    concert_id int       not null,
    start_time timestamp not null,
    duration   bigint    not null,
    constraint concert_schedule_concert_id_fk
        foreign key (concert_id) references concert (id)
);

create table if not exists ticket
(
    id   int auto_increment
        primary key,
    concert_class_id    int         not null,
    concert_schedule_id int         not null,
    id_card_num         varchar(64) not null,
    constraint ticket_concert_class_id_fk
        foreign key (concert_class_id) references concert_class (id),
    constraint ticket_concert_schedule_id_fk
        foreign key (concert_schedule_id) references concert_schedule (id)
);

