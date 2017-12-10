CREATE TYPE order_status AS ENUM ('PLACED', 'PROCESSED');
CREATE TABLE gowma_order
(
    id SERIAL PRIMARY KEY,
    gowma_order__no  VARCHAR NOT NULL,
    gowma_order__date timestamp NOT NULL,
    gowma_order__status order_status NOT NULL,
    gowma_order__user_id REFERENCES gowma_user NOT NULL
    gowma_order__user_address_id REFERENCES user_addresses NOT NULL,
    gowma_order__created_date timestamp default NULL,
    gowma_order__last_modified_date timestamp default NULL,
    gowma_order__created_by integer REFERENCES gowma_user NULL,
    gowma_order__last_modified_by integer REFERENCES gowma_user NULL,
    gowma_order__is_deleted  boolean NOT NULL DEFAULT FALSE
);

CREATE TABLE order_item
(
    id SERIAL PRIMARY KEY,
    order_item__order_id integer REFERENCES gowma_order NOT NULL,
    order_item__product_id integer REFERENCES product NOT NULL,
    order_item__offer_id integer REFERENCES offer NOT NULL,
    order_item__quantity NUMERIC(10, 2) NOT NULL,
    order_item__unit_price NUMERIC(10, 2) NOT NULL,
    order_item__total_amount NUMERIC(10, 2) NOT NULL,
    order_item__created_date timestamp default NULL,
    order_item__last_modified_date timestamp default NULL,
    order_item__created_by integer REFERENCES gowma_user NULL,
    order_item__last_modified_by integer REFERENCES gowma_user NULL,
    order_item__is_deleted  boolean NOT NULL DEFAULT FALSE
);

CREATE TYPE payment_mode AS ENUM ('ONLINE', 'OFF_LINE');
CREATE TABLE payment_detail
(
    id SERIAL PRIMARY KEY,
    payment_detail__mode payment_mode NULL,
    payment_detail__description VARCHAR NULL,
    payment_detail__payment_gateway VARCHAR NULL,
    payment_detail__payment_gateway_txn_id VARCHAR NULL,
    payment_detail__created_date timestamp default NULL,
    payment_detail__last_modified_date timestamp default NULL,
    payment_detail__created_by integer REFERENCES gowma_user NULL,
    payment_detail__last_modified_by integer REFERENCES gowma_user NULL,
    payment_detail__is_deleted  boolean NOT NULL DEFAULT FALSE
);

CREATE TABLE order_transaction
(
    id SERIAL PRIMARY KEY,
    order_details__order_id integer REFERENCES gowma_order NOT NULL,
    order_transaction__payment_detail_id integer REFERENCES payment_detail NOT NULL,
    order_transaction__order_txn_id VARCHAR NOT NULL,
    order_transaction__created_date timestamp default NULL,
    order_transaction__last_modified_date timestamp default NULL,
    order_transaction__created_by integer REFERENCES gowma_user NULL,
    order_transaction__last_modified_by integer REFERENCES gowma_user NULL,
    order_transaction__is_deleted  boolean NOT NULL DEFAULT FALSE
);

CREATE TYPE shipping_status AS ENUM ('TRANSIT', 'DISPATCHED','DELIVERED');
CREATE TABLE order_shipping_detail
(
    id SERIAL PRIMARY KEY,
    order_shipping_detail__order_txn_id integer REFERENCES order_transaction NOT NULL,
    order_shipping_detail__status shipping_status ,
    order_shipping_detail_description VARCHAR NULL,
    order_shipping_detail__created_date timestamp default NULL,
    order_shipping_detail__last_modified_date timestamp default NULL,
    order_shipping_detail__created_by integer REFERENCES gowma_user NULL,
    order_shipping_detail__last_modified_by integer REFERENCES gowma_user NULL,
    order_shipping_detail__is_deleted  boolean NOT NULL DEFAULT FALSE
);