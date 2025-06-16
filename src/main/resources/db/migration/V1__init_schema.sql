CREATE TABLE transactions (
                              id BIGINT PRIMARY KEY,
                              item_name VARCHAR(255) NOT NULL,
                              quantity NUMERIC(10, 2) NOT NULL,
                              unit VARCHAR(50) NOT NULL,
                              unit_price NUMERIC(10, 2) NOT NULL,
                              warehouse_name VARCHAR(255) NOT NULL,
                              created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE inventory (
                           id BIGINT PRIMARY KEY,
                           item_name VARCHAR(255) NOT NULL,
                           warehouse_name VARCHAR(255) NOT NULL,
                           unit_price NUMERIC(10, 2) NOT NULL,
                           quantity NUMERIC(10, 2) NOT NULL,
                           unit VARCHAR(50) NOT NULL,
                           CONSTRAINT unique_item_warehouse_price UNIQUE (item_name, warehouse_name, unit_price)
);

CREATE SEQUENCE transactions_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE inventory_id_seq START WITH 1 INCREMENT BY 1;

ALTER TABLE transactions
    ALTER COLUMN id SET DEFAULT nextval('transactions_id_seq');

ALTER TABLE inventory
    ALTER COLUMN id SET DEFAULT nextval('inventory_id_seq');

ALTER SEQUENCE transactions_id_seq OWNED BY transactions.id;
ALTER SEQUENCE inventory_id_seq OWNED BY inventory.id;
