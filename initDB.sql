CREATE TABLE books (
                       id TEXT PRIMARY KEY,
                       title TEXT NOT NULL,
                       author TEXT,
                       price NUMERIC(10, 2) NOT NULL,
                       quantity INT NOT NULL DEFAULT 0
);

CREATE TABLE users (
                       id TEXT PRIMARY KEY,
                       username TEXT UNIQUE NOT NULL,
                       password TEXT NOT NULL,
                       role TEXT NOT NULL
);

CREATE TABLE orders (
                        id TEXT PRIMARY KEY,
                        user_id TEXT,
                        total_price NUMERIC(10, 2),
                        status TEXT,
                        items_json TEXT,
                        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL
);

CREATE TABLE carts (
                       id TEXT PRIMARY KEY,
                       user_id TEXT,
                       book_id TEXT,
                       quantity INT NOT NULL DEFAULT 1,
                       FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                       FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE
);
INSERT INTO books (id, title, author, price, quantity) VALUES
                                                           ('b-001', 'Wiedźmin: Ostatnie Życzenie', 'Andrzej Sapkowski', 44.99, 15),
                                                           ('b-002', 'Java: Podręcznik doświadczonego programisty', 'Cay S. Horstmann', 89.90, 5),
                                                           ('b-003', 'Mistrz i Małgorzata', 'Michaił Bułhakow', 34.50, 20),
                                                           ('b-004', 'Czysty Kod', 'Robert C. Martin', 69.00, 8),
                                                           ('b-005', 'Dune', 'Frank Herbert', 49.99, 0);

INSERT INTO users (id, username, password, role) VALUES

('u-admin', 'admin', '$2a$10$R79Zdf4Z79Dbyx5E4hFmX.qG47P1ZpG77S7i9/8X5i7E1bZ.pGy2q', 'ROLE_ADMIN'),

('u-user1', 'jan_kowalski', '$2a$10$R79Zdf4Z79Dbyx5E4hFmX.qG47P1ZpG77S7i9/8X5i7E1bZ.pGy2q', 'ROLE_USER');

INSERT INTO carts (id, user_id, book_id, quantity) VALUES
                                                       ('c-001', 'u-user1', 'b-001', 2),
                                                       ('c-002', 'u-user1', 'b-004', 1);