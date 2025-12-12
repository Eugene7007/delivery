ALTER TABLE "orders"
    ADD COLUMN "merchant_id" BIGINT;

ALTER TABLE orders
    ADD CONSTRAINT fk_orders_merchant FOREIGN KEY (merchant_id)
        REFERENCES "merchants" (id) ON DELETE RESTRICT;