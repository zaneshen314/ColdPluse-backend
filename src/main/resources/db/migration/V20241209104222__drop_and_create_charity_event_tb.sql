drop table if exists charity_event_participation;

create table charity_event_participation
(
    user_id          int       not null,
    charity_event_id int       not null,
    enrolled         binary(1) not null,
    finished         binary(1) not null,
    closed           binary(1) not null,
    constraint charity_event_participation_pk
        unique (user_id, charity_event_id),
    constraint charity_event_participation_charity_event_id_fk
        foreign key (charity_event_id) references charity_event (id),
    constraint charity_event_participation_user_id_fk
        foreign key (user_id) references user (id)
);