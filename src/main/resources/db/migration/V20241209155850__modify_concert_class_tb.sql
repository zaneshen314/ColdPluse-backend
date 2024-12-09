alter table concert
drop
column currency;

alter table concert_class
    add currency varchar(64) null;