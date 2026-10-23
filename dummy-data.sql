-- Run AFTER starting the app once (so Hibernate has created the tables)
USE travel_db;

-- Two demo users. Password for both is: 1234  (stored as a BCrypt hash)
INSERT INTO users (username, password) VALUES
('stuti',  '$2a$10$jyoaXa2fFgcv2GYb8NfX3ula9G6JXNxWcom110mJQ5Woqq16B2QlO'),
('sumedh', '$2a$10$jyoaXa2fFgcv2GYb8NfX3ula9G6JXNxWcom110mJQ5Woqq16B2QlO');

-- Destinations for stuti
INSERT INTO destination (name, city, country, description, category, visited, user_id) VALUES
('Eiffel Tower',      'Paris',     'France',      'Watch the sunset from the top.',          'HISTORICAL', false, (SELECT id FROM users WHERE username='stuti')),
('Maldives Getaway',  'Male',      'Maldives',    'Overwater villa and snorkelling.',        'BEACH',      false, (SELECT id FROM users WHERE username='stuti')),
('Leh Ladakh',        'Leh',       'India',       'Road trip through high mountain passes.', 'MOUNTAIN',   true,  (SELECT id FROM users WHERE username='stuti')),
('Gateway of India',  'Mumbai',    'India',       'Street food and the Marine Drive walk.',  'CITY',       true,  (SELECT id FROM users WHERE username='stuti')),
('Santorini',         'Oia',       'Greece',      'White houses and blue domes.',            'BEACH',      false, (SELECT id FROM users WHERE username='stuti')),
('Colosseum',         'Rome',      'Italy',       'Ancient amphitheatre tour.',              'HISTORICAL', false, (SELECT id FROM users WHERE username='stuti'));

-- Destinations for sumedh
INSERT INTO destination (name, city, country, description, category, visited, user_id) VALUES
('Mount Fuji',        'Fujiyoshida','Japan',      'Sunrise hike in summer.',                 'MOUNTAIN',   false, (SELECT id FROM users WHERE username='sumedh')),
('Times Square',      'New York',  'USA',         'Broadway show and city lights.',          'CITY',       false, (SELECT id FROM users WHERE username='sumedh')),
('Goa Beaches',       'Panaji',    'India',       'Beach shacks and water sports.',          'BEACH',      true,  (SELECT id FROM users WHERE username='sumedh')),
('Taj Mahal',         'Agra',      'India',       'Visit at sunrise.',                       'HISTORICAL', true,  (SELECT id FROM users WHERE username='sumedh')),
('Swiss Alps',        'Interlaken','Switzerland', 'Scenic train rides and skiing.',          'MOUNTAIN',   false, (SELECT id FROM users WHERE username='sumedh'));

-- ===== Already loaded the data earlier? Run just this block to add ratings =====
-- (Hibernate adds the new rating / image_url columns automatically when the app restarts)
UPDATE destination SET rating = 5 WHERE name IN ('Leh Ladakh', 'Taj Mahal');
UPDATE destination SET rating = 4 WHERE name IN ('Gateway of India', 'Goa Beaches');
-- Optional: your own picture link for any destination
-- UPDATE destination SET image_url = 'https://example.com/photo.jpg' WHERE name = 'Santorini';
