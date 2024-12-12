alter table ticket
    add user_id int not null;

alter table ticket
    add state varchar(24) not null;

alter table ticket
    add constraint ticket_user_id_fk
        foreign key (user_id) references user (id);