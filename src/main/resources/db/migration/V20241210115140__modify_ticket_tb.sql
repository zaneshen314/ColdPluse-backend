alter table ticket
    add concert_id int not null;
alter table ticket
    add foreign key (concert_id) references concert(id);
alter table ticket
    add column viewer_name varchar(255) not null;