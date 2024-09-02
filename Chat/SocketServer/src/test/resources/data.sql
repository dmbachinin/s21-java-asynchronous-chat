-- Вставка тестовых пользователей
INSERT INTO users (id, name, email, password_hash)
VALUES
    (1, 'Alice Smith', 'alice@example.com', 'hashed_password_1'),
    (2, 'Bob Johnson', 'bob@example.com', 'hashed_password_2'),
    (3, 'Charlie Davis', 'charlie@example.com', 'hashed_password_3');

-- Вставка тестовых комнат
INSERT INTO chat_rooms (id, creator_id, name, description)
VALUES
    (1, 1, 'General Chat', 'A room for general discussions'),
    (2, 2, 'Tech Talk', 'A room for tech enthusiasts'),
    (3, 3, 'Random', 'A room for random conversations');

-- Вставка связей пользователей с комнатами
INSERT INTO user_chat_rooms (id, user_id, room_id)
VALUES
    (1, 1, 2), -- Alice in General Chat
    (2, 2, 1), -- Bob in General Chat
    (3, 3, 1), -- Charlie in General Chat
    (4, 1, 1), -- Alice in Tech Talk
    (5, 2, 2), -- Bob in Tech Talk
    (6, 3, 3); -- Charlie in Random

-- Вставка тестовых сообщений
INSERT INTO messages (id, room_id, user_id, content)
VALUES
    (1, 1, 1, 'Hello everyone!'), -- Alice in General Chat
    (2, 1, 2, 'Hi Alice!'), -- Bob in General Chat
    (3, 1, 3, 'Hey folks!'), -- Charlie in General Chat
    (4, 2, 1, 'Anyone here into Java?'), -- Alice in Tech Talk
    (5, 2, 2, 'I am! Let s talk about Spring.'), -- Bob in Tech Talk
    (6, 3, 3, 'What s up?'); -- Charlie in Random


