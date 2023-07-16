INSERT INTO users (username, firstname, lastname, password, roles)
VALUES ('CopetS', 'Andrey', 'Yarosh', '$2a$12$H6x1r85bPd4sbHVLlcAFf.3lz8RTI.uHP30XCLJ3Q9VrgXta9ZG6y','1');

INSERT INTO  roles (name)
VALUES ('ADMIN');
INSERT INTO roles (name)
VALUES ('CASHIER');

INSERT INTO products (description, price, discount)
values ('Milk', 1.9, 0);

INSERT INTO products (description, price, discount)
values ('Bread', 1.4, 0);

INSERT INTO products (description, price, discount)
values ('Pasta', 2.9, 0.1);

INSERT INTO products (description, price, discount)
values ('Potato', 3.9, 0.5);

INSERT INTO products (description, price, discount)
values ('Cheese', 5.8, 0);

INSERT INTO discount_cards (discount)
values (10);

INSERT INTO discount_cards (discount)
values (15);

INSERT INTO discount_cards (discount)
values (20);

INSERT INTO discount_cards (discount)
values (25);

INSERT INTO discount_cards (discount)
values (30);
