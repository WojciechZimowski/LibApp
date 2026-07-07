CREATE TABLE books(
    id TEXT PRIMARY KEY,
    title TEXT,
    author TEXT,
    price NUMERIC,
    quantity INT


);
CREATE TABLE orders(
    id TEXT PRIMARY KEY,
    user_id TEXT,
    total_price NUMERIC,
    status TEXT,
    items_json TEXT
);
CREATE TABLE users(
    id TEXT PRIMARY KEY,
    username TEXT UNIQUE,
    password TEXT,
    role TEXT
);
CREATE TABLE carts(
    id TEXT PRIMARY KEY,
    user_id TEXT,
    book_id TEXT,
    quantity INT,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE
);
INSERT INTO users (id, username, password, role) VALUES
                                                     ('u-admin-123', 'admin', '$2a$10$vK3d0q6vL6jVpGZ.i3E9OOBtP.v4d9h9N0fH.XkWVzWwX3A5y2XmG', 'ADMIN'),
                                                     ('u-user-456', 'user', '$2a$10$E2lS.7m2P0E7Yv8lUvO2..v5/eS4E7H8v6/Yw8pX9zWwX3A5y2XmG', 'USER');


INSERT INTO books (id, title, author, price) VALUES
                                                 ('b-001', 'Wiedzmin: Ostatnie Zyczenie', 'Andrzej Sapkowski', 49.99),
                                                 ('b-002', 'Java: Podrecznik doswiadczonego programisty', 'Cay S. Horstmann', 89.90);


INSERT INTO carts (id, user_id, book_id, quantity) VALUES
    ('c-001', 'u-user-456', 'b-001', 2);


INSERT INTO orders (id, user_id, total_price, status, item_json) VALUES
    ('o-001', 'u-user-456', 89.90, 'PENDING', 'Java: Podrecznik doswiadczonego programisty x1');