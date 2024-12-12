create table if not exists charity_event_participation
(
    user_id          int       not null,
    charity_event_id int       not null,
    check_in         binary(1) null,
    check_out        binary(1) null,
    constraint charity_event_participation_pk
    unique (user_id, charity_event_id),
    constraint charity_event_participation_charity_event_id_fk
    foreign key (charity_event_id) references charity_event (id),
    constraint charity_event_participation_user_id_fk
    foreign key (user_id) references user (id)
    );