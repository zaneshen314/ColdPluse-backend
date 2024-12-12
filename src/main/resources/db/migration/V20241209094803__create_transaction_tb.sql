create table transaction
(
    id                   int auto_increment,
    user_id              int         not null,
    amount_in_usd        double      not null,
    amount_in_local_curr int         null,
    local_curr           varchar(64) not null,
    column_name          int         null,
    constraint transaction_pk
        primary key (id),
    constraint transaction_user_id_fk
        foreign key (user_id) references user (id)
);

alter table ticket
    add transaction_id int not null;

alter table ticket
    add constraint ticket_transaction_id_fk
        foreign key (transaction_id) references transaction (id);
