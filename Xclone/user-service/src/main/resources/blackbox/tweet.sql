TRUNCATE TABLE tweet, users, tweet_image, user_image, tweet_likes, retweets,user_subscriptions, tweet_tag, tags;

INSERT INTO users
(user_id, created_by, created_dt, updated_by, updated_dt, about, confirmed, email, full_name, location, password,
 username,
 website, role)
VALUES ('96751bae-00d0-4b73-b59f-4ffa8112e04c', 'Tran Hung', '2024-04-21 13:20:37.000000', null, null, 'Twitter', true,
        'tranhung@gmail.com', 'tran manh hung', 'Kon Tum',
        '$2a$10$BKiMnZvtPEOqu.UX/3xeE./jPY6beZjT5ikMP176zbXt2bXSmwIlu', 'tranhung', null, 'USER_ROLE'),
       ('54a007cf-4ade-4a63-8380-1e4f9a14f5e0', 'Tran Manh Hung', '2024-04-21 13:20:37.000000', null, null, 'Twitter',
        true,
        'tranhung1@gmail.com', 'tran manh hung', 'Kon Tum',
        '$2a$10$BKiMnZvtPEOqu.UX/3xeE./jPY6beZjT5ikMP176zbXt2bXSmwIlu', 'tranhung', null, 'USER_ROLE')
;

INSERT INTO tweet
(tweet_id, created_by, created_dt, updated_by, updated_dt, text, user_id)
VALUES ('7645ebd0-6d94-4d72-94f3-2ef86dc2c48d', '96751bae-00d0-4b73-b59f-4ffa8112e04c', '2024-04-21 13:20:37.000000',
        null, null, 'Hello tweet', '96751bae-00d0-4b73-b59f-4ffa8112e04c'),
       ('ef1f4fef-8d73-4bbb-99e0-adf65117fa5b', '96751bae-00d0-4b73-b59f-4ffa8112e04c', '2024-04-21 13:20:37.000000',
        null, null, 'Hello tweet 2', '96751bae-00d0-4b73-b59f-4ffa8112e04c');

INSERT INTO tweet_image
(image_id, created_by, created_dt, updated_by, updated_dt, src, tweet_id)
VALUES ('e06e7ef0-133f-40d9-bd52-482a731346d9', '', '2024-04-21 13:20:37.000000', null, null,
        'https://xclonebucket.s3.ap-southeast-2.amazonaws.com/410f5301-9d79-4a37-8dd6-39c2f9ba4f0c_Screenshot%202024-06-02%20at%2000.24.56.png',
        '7645ebd0-6d94-4d72-94f3-2ef86dc2c48d'),
       ('fa9fa5ec-305e-4c7e-8f39-bf961ef0202c', '', '2024-04-21 13:20:37.000000', null, null,
        'https://xclonebucket.s3.ap-southeast-2.amazonaws.com/410f5301-9d79-4a37-8dd6-39c2f9ba4f0c_Screenshot%202024-06-02%20at%2000.24.56.png',
        '7645ebd0-6d94-4d72-94f3-2ef86dc2c48d');

INSERT INTO tweet_likes (tweet_id, user_id)
VALUES ('ef1f4fef-8d73-4bbb-99e0-adf65117fa5b', '96751bae-00d0-4b73-b59f-4ffa8112e04c'),
       ('ef1f4fef-8d73-4bbb-99e0-adf65117fa5b', '54a007cf-4ade-4a63-8380-1e4f9a14f5e0');

INSERT INTO tags (tag_id, created_by, created_dt, updated_by, updated_dt, tag_name, tweets_quantity)
VALUES ('74769fb5-d3eb-451e-9d2f-6c38b5163463', '96751bae-00d0-4b73-b59f-4ffa8112e04c', '2024-04-21 13:20:37.000000',
        null, null, 'secondTag', 1);

INSERT INTO tweet_tag (tweet_id, tags_tag_id)
VALUES ('7645ebd0-6d94-4d72-94f3-2ef86dc2c48d', '74769fb5-d3eb-451e-9d2f-6c38b5163463');

