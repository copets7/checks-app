CREATE TABLE IF NOT EXISTS discount_cards(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    discount DOUBLE NOT NULL
);

CREATE TABLE IF NOT EXISTS products(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(256) NOT NULL,
    price DOUBLE NOT NULL,
    discount DOUBLE NOT NULL
);

CREATE TABLE IF NOT EXIST checks(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    market_name VARCHAR(256) NOT NULL,
    cashier_name VARCHAR(256) NOT NULL,
    date DATE NOT NULL,
    time TIME NOT NULL,
    discount_card_id BIGINT NOT NULL,
    total_price DOUBLE NOT NULL,
    FOREIGN KEY(discount_cards_id) REFERENCES discount_cards (id)
);