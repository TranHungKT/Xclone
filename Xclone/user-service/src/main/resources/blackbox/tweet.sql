TRUNCATE TABLE tweet, users;

INSERT INTO users
(user_id, created_by, created_dt, updated_by, updated_dt, about, confirmed, email, full_name, location, password, username,
 website,role)
VALUES ('96751bae-00d0-4b73-b59f-4ffa8112e04c', 'Tran Hung', '2024-04-21 13:20:37.000000', null, null, 'Twitter', true,
        'tranhung@gmail.com', 'tran manh hung', 'Kon Tum', '$2a$10$BKiMnZvtPEOqu.UX/3xeE./jPY6beZjT5ikMP176zbXt2bXSmwIlu', 'tranhung', null, 'USER_ROLE'),
       ('54a007cf-4ade-4a63-8380-1e4f9a14f5e0', 'Tran Manh Hung', '2024-04-21 13:20:37.000000', null, null, 'Twitter', true,
        'tranhung1@gmail.com', 'tran manh hung', 'Kon Tum', '$2a$10$BKiMnZvtPEOqu.UX/3xeE./jPY6beZjT5ikMP176zbXt2bXSmwIlu', 'tranhung', null, 'USER_ROLE')
;

INSERT INTO tweet
(tweet_id, created_by, created_dt, updated_by, updated_dt, text, user_id)
VALUES
    ('7645ebd0-6d94-4d72-94f3-2ef86dc2c48d', '96751bae-00d0-4b73-b59f-4ffa8112e04c', '2024-04-21 13:20:37.000000', null, null, 'Hello tweet', '96751bae-00d0-4b73-b59f-4ffa8112e04c'),
    ('ef1f4fef-8d73-4bbb-99e0-adf65117fa5b', '96751bae-00d0-4b73-b59f-4ffa8112e04c', '2024-04-21 13:20:37.000000', null, null, 'Hello tweet 2', '96751bae-00d0-4b73-b59f-4ffa8112e04c');