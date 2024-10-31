--liquibase formatted sql

--changeset aibragimov:1
ALTER TABLE shopping_carts ADD CONSTRAINT shopping_carts_user_id_uniq UNIQUE (user_id);
--rollback ALTER TABLE shopping_carts DROP CONSTRAINT IF EXISTS shopping_carts_user_id_uniq;