--liquibase formatted sql

--changeset aibragimov:1
ALTER TABLE shopping_carts ADD CONSTRAINT shopping_carts_user_id_uniq UNIQUE (user_id);
--rollback ALTER TABLE shopping_carts DROP CONSTRAINT IF EXISTS shopping_carts_user_id_uniq;

--changeset aibragimov:2
ALTER TABLE users ADD COLUMN image_path VARCHAR(512);
--rollback ALTER TABLE users DROP COLUMN image_path;