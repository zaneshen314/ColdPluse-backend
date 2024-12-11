CREATE TABLE ticket_release
(
    concert_schedule_id INT NOT NULL,
    start_time VARCHAR(64) NOT NULL,
    end_time VARCHAR(64) NOT NULL,
    frequency INT NOT NULL,
    hour INT NOT NULL,
    next_presell_time VARCHAR(64) NOT NULL,
    CONSTRAINT ticket_release_concert_schedule_id_fk
        FOREIGN KEY (concert_schedule_id) REFERENCES concert_schedule (id)
);