INSERT INTO charity_event (name, start_time, duration, point, concert_id, current_enrolled,
                           suggested_participation_size, location, description)
VALUES ('Charity Run', '2024-12-01 08:00:00', 7200, 10, null, 50, 100, 'Central Park',
        'A charity run event to raise funds for local schools.'),
       ('Food Drive', '2024-12-05 09:00:00', 14400, 20, null, 30, 50, 'Community Center',
        'A food drive to collect non-perishable items for the food bank.'),
       ('Blood Donation Camp', '2024-12-10 10:00:00', 10800, 15, null, 40, 80, 'City Hospital',
        'A blood donation camp to support the local blood bank.'),
       ('Charity Concert', '2024-12-15 18:00:00', 18000, 25, null, 200, 300, 'Downtown Arena',
        'A charity concert featuring local bands to raise funds for healthcare.'),
       ('Tree Plantation Drive', '2024-12-20 07:00:00', 3600, 5, null, 100, 150, 'City Park',
        'A tree plantation drive to promote environmental awareness.');

INSERT INTO venue (name, location, capacity, state)
VALUES ('Central Park', 'New York, NY', 5000, 'NY'),
       ('Community Center', 'Los Angeles, CA', 2000, 'CA'),
       ('City Hospital', 'Chicago, IL', 1000, 'IL'),
       ('Downtown Arena', 'Houston, TX', 10000, 'TX'),
       ('City Park', 'Phoenix, AZ', 3000, 'AZ');

INSERT INTO user (name, password, email, status, cumulated_point)
VALUES ('Alice Johnson', 'password123', 'alice.johnson@example.com', 0, 100),
       ('Bob Smith', 'password456', 'bob.smith@example.com', 0, 50),
       ('Charlie Brown', 'password789', 'charlie.brown@example.com', 0, 200),
       ('Diana Prince', 'password101', 'diana.prince@example.com', 0, 150),
       ('Eve Adams', 'password202', 'eve.adams@example.com', 0, 75);