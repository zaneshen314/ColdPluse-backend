alter table concert_schedule_class
    add id int first;

alter table concert_schedule_class
    add constraint concert_schedule_class_pk
        primary key (id);

alter table concert_schedule_class
    modify id int auto_increment;