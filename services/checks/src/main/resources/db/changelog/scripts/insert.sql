INSERT INTO users (username, firstname, lastname, password, roles)
VALUES ('CopetS', 'Andrey', 'Yarosh', '$2a$12$H6x1r85bPd4sbHVLlcAFf.3lz8RTI.uHP30XCLJ3Q9VrgXta9ZG6y','1');

INSERT INTO  roles (name)
VALUES ('ADMIN');
INSERT INTO roles (name)
VALUES ('CASHIER');

INSERT INTO products (description, price, discount)
VALUES ('Milk', 1.9, 0);

INSERT INTO products (description, price, discount)
VALUES ('Bread', 1.4, 0);

INSERT INTO products (description, price, discount)
VALUES ('Pasta', 2.9, 0.1);

INSERT INTO products (description, price, discount)
VALUES ('Potato', 3.9, 0.5);

INSERT INTO products (description, price, discount)
VALUES ('Cheese', 5.8, 0);

INSERT INTO discount_cards (discount)
VALUES (10);

INSERT INTO discount_cards (discount)
VALUES (15);

INSERT INTO discount_cards (discount)
VALUES (20);

INSERT INTO discount_cards (discount)
VALUES (25);

INSERT INTO discount_cards (discount)
VALUES (30);
