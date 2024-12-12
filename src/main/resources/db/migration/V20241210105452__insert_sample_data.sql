INSERT INTO concert (venue_id, name)
VALUES (1, 'Rock Concert'),
       (2, 'Jazz Night'),
       (3, 'Classical Music Evening'),
       (4, 'Pop Music Festival'),
       (1, 'Indie Band Showcase');

INSERT INTO concert_class (class_name, concert_id, price_in_usd, price_in_local_curr, capacity, available_seats,
                           currency)
VALUES ('VIP', 1, 150.00, 150.00, 100, 80, 'USD'),
       ('General Admission', 1, 50.00, 50.00, 500, 450, 'USD'),
       ('VIP', 2, 200.00, 200.00, 50, 45, 'USD'),
       ('General Admission', 2, 75.00, 75.00, 300, 280, 'USD'),
       ('VIP', 3, 180.00, 180.00, 80, 70, 'USD'),
       ('General Admission', 3, 60.00, 60.00, 400, 350, 'USD'),
       ('VIP', 4, 250.00, 250.00, 120, 100, 'USD'),
       ('General Admission', 4, 100.00, 100.00, 600, 550, 'USD'),
       ('VIP', 5, 130.00, 130.00, 90, 85, 'USD'),
       ('General Admission', 5, 40.00, 40.00, 350, 300, 'USD');

INSERT INTO concert_schedule (concert_id, start_time, duration)
VALUES (1, '2024-12-01 19:00:00', 7200),
       (2, '2024-12-05 20:00:00', 10800),
       (3, '2024-12-10 18:30:00', 9000),
       (4, '2024-12-15 21:00:00', 14400),
       (5, '2024-12-20 17:00:00', 7200);