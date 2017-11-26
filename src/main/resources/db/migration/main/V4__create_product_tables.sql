CREATE TABLE product_category
 (
    id SERIAL PRIMARY KEY,
    product_category__parent_id integer REFERENCES product_category,
    product_category__name VARCHAR NOT NULL,
    product_category__description VARCHAR NOT NULL,
    product_category__image_url VARCHAR NOT NULL,
    product_category__order_no integer NOT NULL,
    product_category__enabled boolean NOT NULL DEFAULT FALSE,
    product_category__created_date timestamp default NULL,
    product_category__last_modified_date timestamp default NULL,
    product_category__created_by integer REFERENCES gowma_user NULL,
    product_category__last_modified_by integer REFERENCES gowma_user NULL,
    product_category__is_deleted  boolean NOT NULL DEFAULT FALSE
);

CREATE TABLE unit_of_measure
(
    id SERIAL PRIMARY KEY,
    unit_of_measure__name VARCHAR NOT NULL,
    unit_of_measure__created_date timestamp default NULL,
    unit_of_measure__last_modified_date timestamp default NULL,
    unit_of_measure__created_by integer REFERENCES gowma_user NULL,
    unit_of_measure__last_modified_by integer REFERENCES gowma_user NULL,
    unit_of_measure__is_deleted  boolean NOT NULL DEFAULT FALSE
);

CREATE TABLE product
(
    id SERIAL PRIMARY KEY,
    product__category_id integer REFERENCES product_category NOT NULL,
    product__unit_of_measure_id integer REFERENCES unit_of_measure NOT NULL,
    product__name VARCHAR NOT NULL,
    product__price NUMERIC(10, 2) NOT NULL,
    product__caption VARCHAR NOT NULL,
    product__description VARCHAR NOT NULL,
    product__view_count integer NOT NULL DEFAULT 0,
    product__is_active boolean NOT NULL DEFAULT TRUE,
    user_detail__created_date timestamp default NULL,
    user_detail__last_modified_date timestamp default NULL,
    user_detail__created_by integer REFERENCES gowma_user NULL,
    user_detail__last_modified_by integer REFERENCES gowma_user NULL,
    user_detail__is_deleted  boolean NOT NULL DEFAULT FALSE
);

CREATE TABLE product_images
(
    id SERIAL PRIMARY KEY,
    product_images__product_id integer REFERENCES product NOT NULL,
    product_images__image_url VARCHAR NOT NULL,
    product_images_order_no integer NOT NULL,
    product_images__created_date timestamp default NULL,
    product_images__last_modified_date timestamp default NULL,
    product_images__created_by integer REFERENCES gowma_user NULL,
    product_images__last_modified_by integer REFERENCES gowma_user NULL,
    product_images__is_deleted  boolean NOT NULL DEFAULT FALSE
);

CREATE TYPE inventory_status AS ENUM ('AVAILABLE', 'OUT_OF_STOCK','ARRAIVING_SOON','PRE-ORDER','TWO-THREE-DAYS');
CREATE TABLE product_inventory
(
    id SERIAL PRIMARY KEY,
    inventory__product_id integer REFERENCES product NOT NULL,
    inventory__available_count integer NOT NULL DEFAULT 0,
    inventory__sold_count integer NOT NULL DEFAULT 0,
    inventory__inventory_status inventory_status NOT NULL,
    inventory__created_date timestamp default NULL,
    inventory__last_modified_date timestamp default NULL,
    inventory__created_by integer REFERENCES gowma_user NULL,
    inventory__last_modified_by integer REFERENCES gowma_user NULL,
    inventory__is_deleted  boolean NOT NULL DEFAULT FALSE
);

CREATE TABLE tax_type
(
    id SERIAL PRIMARY KEY,
    tax_type__name VARCHAR NOT NULL,
    tax_type__percent  NUMERIC(10, 2)  NULL,
    tax_type__created_date timestamp default NULL,
    tax_type__last_modified_date timestamp default NULL,
    tax_type__created_by integer REFERENCES gowma_user NULL,
    tax_type__last_modified_by integer REFERENCES gowma_user NULL,
    tax_type__is_deleted  boolean NOT NULL DEFAULT FALSE
);

CREATE TABLE tax_details
(
    id SERIAL PRIMARY KEY,
    product_tax_detail__product_id integer REFERENCES product NOT NULL,
    product_tax_detail__category_id integer REFERENCES product_category NOT NULL,
    product_tax_details__tax_type_id integer REFERENCES tax_type NOT NULL,
    product_tax_details__tax_percentage  NUMERIC(10, 2)  NULL,
    product_tax_details__created_date timestamp default NULL,
    product_tax_details__last_modified_date timestamp default NULL,
    product_tax_details__created_by integer REFERENCES gowma_user NULL,
    product_tax_details__last_modified_by integer REFERENCES gowma_user NULL,
    product_tax_details__is_deleted  boolean NOT NULL DEFAULT FALSE
);

CREATE TABLE offer
(
    id SERIAL PRIMARY KEY,
    offer__name VARCHAR NOT NULL,
    offer__percenatge VARCHAR NOT NULL,
    offer__valid_from timestamp default NULL,
    offer__valid_to timestamp default NULL,
    offer__created_date timestamp default NULL,
    offer__last_modified_date timestamp default NULL,
    offer__created_by integer REFERENCES gowma_user NULL,
    offer__last_modified_by integer REFERENCES gowma_user NULL,
    offer__is_deleted  boolean NOT NULL DEFAULT FALSE
);

CREATE TABLE offer_mapping
(
    id SERIAL PRIMARY KEY,
    offer_mapping__product_id integer REFERENCES product NOT NULL,
    offer_mapping__offer_id integer REFERENCES offer NOT NULL,
    offer_mapping__category_id integer REFERENCES product_category NOT NULL,
    offer_mapping__created_date timestamp default NULL,
    offer_mapping__last_modified_date timestamp default NULL,
    offer_mapping__created_by integer REFERENCES gowma_user NULL,
    offer_mapping__last_modified_by integer REFERENCES gowma_user NULL,
    offer_mapping__is_deleted  boolean NOT NULL DEFAULT FALSE
);

CREATE TABLE user_product_whilist
(
    id SERIAL PRIMARY KEY,
    user_product_whilist__user_id integer REFERENCES gowma_user NOT NULL,
    user_product_whilist__product_id integer REFERENCES product NOT NULL,
    user_product_whilist__created_date timestamp default NULL,
    user_product_whilist__last_modified_date timestamp default NULL,
    user_product_whilist__created_by integer REFERENCES gowma_user NULL,
    user_product_whilist__last_modified_by integer REFERENCES gowma_user NULL,
    user_product_whilist__is_deleted  boolean NOT NULL DEFAULT FALSE
);

CREATE TABLE user_cart_list
(
    id SERIAL PRIMARY KEY,
    user_cart_list__user_id integer REFERENCES gowma_user NOT NULL,
    user_cart_list__product_id integer REFERENCES product NOT NULL,
    user_cart_list__quantity NUMERIC(10, 2) NOT NULL,
    user_cart_list__last_modified_date timestamp default NULL,
    user_cart_list__created_by integer REFERENCES gowma_user NULL,
    user_cart_list__last_modified_by integer REFERENCES gowma_user NULL,
    user_cart_list__is_deleted  boolean NOT NULL DEFAULT FALSE
);