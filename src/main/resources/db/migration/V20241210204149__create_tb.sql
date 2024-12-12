create table ticket_release
(
    concert_schedule_id int         not null,
    start_time          varchar(64) not null,
    end_time            varchar(64) not null,
    frequency           int         not null,
    constraint ticket_release_concert_schedule_id_fk
        foreign key (concert_schedule_id) references concert_schedule (id)
);

create table concert_schedule_class
(
    concert_schedule_id int not null,
    concert_class_id    int not null,
    available_seats     int not null,
    constraint concert_schedule_class_concert_class_id_fk
        foreign key (concert_class_id) references concert_class (id),
    constraint concert_schedule_class_concert_schedule_id_fk
        foreign key (concert_schedule_id) references concert_schedule (id)
);