DELETE FROM concert_schedule_class;

INSERT INTO concert_schedule_class (concert_schedule_id, concert_class_id, available_seats)
VALUES
    (1, 1, 80),  -- VIP class for concert 1
    (1, 2, 450), -- General Admission class for concert 1
    (1, 3, 45),  -- VIP class for concert 2
    (1, 4, 280), -- General Admission class for concert 2
    (1, 5, 70),  -- VIP class for concert 3
    (1, 6, 350), -- General Admission class for concert 3
    (1, 7, 100), -- VIP class for concert 4
    (1, 8, 550), -- General Admission class for concert 4
    (1, 9, 85),  -- VIP class for concert 5
    (1, 10, 300); -- General Admission class for concert 5