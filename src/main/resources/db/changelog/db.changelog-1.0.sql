--liquibase formatted sql

--changeset aibragimov:1
CREATE TYPE ROLE
AS ENUM ('ADMIN', 'USER', 'EDITOR', 'ANONYMOUS');
--rollback DROP TYPE  IF EXISTS ROLE;

--changeset aibragimov:2
CREATE TYPE CATEGORY
AS ENUM ('ELECTRONICS', 'COMPUTERS', 'SMART_HOME');
--rollback DROP TYPE IF EXISTS CATEGORY;

--changeset aibragimov:3
CREATE TABLE users
(
    id        BIGSERIAL,
    firstname VARCHAR(64),
    lastname  VARCHAR(64),
    username  VARCHAR(32)  NOT NULL UNIQUE,
    password  VARCHAR(256) NOT NULL,
    email     VARCHAR(128) NOT NULL UNIQUE,
    role      ROLE         NOT NULL,

    PRIMARY KEY (id)
);
--rollback DROP TABLE IF EXISTS users;

--changeset aibragimov:4
CREATE TABLE products
(
    id          BIGSERIAL,
    title       VARCHAR(256)   NOT NULL,
    description TEXT           NOT NULL,
    price       DECIMAL(10, 2) NOT NULL,
    weight      DECIMAL(4, 2)  NOT NULL,
    category    CATEGORY       NOT NULL,

    PRIMARY KEY (id)
);
--rollback DROP TABLE IF EXISTS products;

--changeset aibragimov:5
CREATE TABLE orders
(
    id         BIGSERIAL,
    user_id    BIGINT         NOT NULL,
    created_at TIMESTAMP DEFAULT now(),
    fee        DECIMAL(10, 2) NOT NULL,
    paid       BOOLEAN   DEFAULT FALSE,

    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);
--rollback DROP TABLE IF EXISTS orders;

--changeset aibragimov:6
CREATE TABLE shopping_carts
(
    id       BIGSERIAL,
    user_id  BIGINT   NOT NULL,
    quantity SMALLINT NOT NULL,
    amount   DECIMAL(10, 2),

    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);
--rollback DROP TABLE IF EXISTS shopping_carts;

--changeset aibragimov:7
CREATE TABLE order_items
(
    id         BIGSERIAL,
    order_id   BIGINT NOT NULL,
    product_id BIGINT NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (order_id) REFERENCES orders (id),
    FOREIGN KEY (product_id) REFERENCES products (id)
);
--rollback DROP TABLE IF EXISTS order_items;

--changeset aibragimov:8
--tagDatabase:1.0
CREATE TABLE cart_items
(
    id         BIGSERIAL,
    cart_id    BIGINT NOT NULL,
    product_id BIGINT NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (cart_id) REFERENCES shopping_carts (id),
    FOREIGN KEY (product_id) REFERENCES products (id)
);
--rollback DROP TABLE IF EXISTS cart_items;