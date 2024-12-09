alter table charity_event
    add suggested_participation_size int null after concert_id;
alter table charity_event
    add current_enrolled int null after concert_id;