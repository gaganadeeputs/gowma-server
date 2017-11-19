CREATE TYPE status AS ENUM ('ACTIVE', 'INACTIVE');
CREATE TABLE gowma_user
 (
    id SERIAL PRIMARY KEY,
    gowma_user__email VARCHAR NOT NULL,
    gowma_user__mobile_no VARCHAR NOT NULL,
    gowma_user__password_hash VARCHAR NOT NULL,
    gowma_user__status status NOT NULL,
    gowma_user__created_date timestamp default NULL,
    gowma_user__last_modified_date timestamp default NULL,
    gowma_user__created_by integer REFERENCES gowma_user NULL,
    gowma_user__last_modified_by integer REFERENCES gowma_user NULL,
    gowma_user__is_deleted  boolean NOT NULL DEFAULT FALSE,
    CONSTRAINT gowma_user_unq_email UNIQUE (gowma_user__email),
    CONSTRAINT gowma_user_unq_mobile_no UNIQUE (gowma_user__mobile_no)
);

CREATE TABLE country
(
    id SERIAL PRIMARY KEY,
    country__name VARCHAR NOT NULL,
    country__created_date timestamp default NULL,
    country__last_modified_date timestamp default NULL,
    country__created_by integer REFERENCES gowma_user NULL,
    country__last_modified_by integer REFERENCES gowma_user NULL,
    country__is_deleted  boolean NOT NULL DEFAULT FALSE
);

CREATE TABLE state
(
    id SERIAL PRIMARY KEY,
    state__country_id integer REFERENCES gowma_user NOT NULL,
    state__name VARCHAR NOT NULL,
    state__created_date timestamp default NULL,
    state__last_modified_date timestamp default NULL,
    state__created_by integer REFERENCES gowma_user NULL,
    state__last_modified_by integer REFERENCES gowma_user NULL,
    state__is_deleted  boolean NOT NULL DEFAULT FALSE
);


CREATE TABLE address_code
(
    id SERIAL PRIMARY KEY,
    address_code__code VARCHAR NOT NULL,
    address_code__place_name VARCHAR NOT NULL,
    address_code__state_id integer REFERENCES state NOT NULL,
    address_code__created_date timestamp default NULL,
    address_code__last_modified_date timestamp default NULL,
    address_code__created_by integer REFERENCES gowma_user NULL,
    address_code__last_modified_by integer REFERENCES gowma_user NULL,
    address_code__is_deleted  boolean NOT NULL DEFAULT FALSE
);



CREATE TYPE gender AS ENUM ('MALE', 'FEMALE' ,'OTHER');
CREATE TABLE user_detail
(
    id SERIAL PRIMARY KEY,
    user_detail_user_id integer REFERENCES gowma_user NOT NULL,
    user_detail_fname VARCHAR NOT NULL,
    user_detail_lname VARCHAR NOT NULL,
    user_detail_gender gender NOT NULL,
    user_detail__created_date timestamp default NULL,
    user_detail__last_modified_date timestamp default NULL,
    user_detail__created_by integer REFERENCES gowma_user NULL,
    user_detail__last_modified_by integer REFERENCES gowma_user NULL,
    user_detail__is_deleted  boolean NOT NULL DEFAULT FALSE
);


CREATE TYPE address_type AS ENUM ('HOME', 'WORK');
CREATE TABLE user_addresses
(
    id SERIAL PRIMARY KEY,
    user_address__user_id integer REFERENCES gowma_user NOT NULL,
    user_address__address_code integer REFERENCES address_code NOT NULL,
    user_address__address_type address_type,
    user_address_name VARCHAR NOT NULL,
    user_address__address1  VARCHAR NOT NULL,
    user_address__address2 VARCHAR NOT NULL,
    user_address__landmark VARCHAR NOT NULL,
    user_address__phone_no VARCHAR NOT NULL,
    user_address__is_default  boolean NOT NULL DEFAULT false,
    user_address__created_date timestamp default NULL,
    user_address__last_modified_date timestamp default NULL,
    user_address__created_by integer REFERENCES gowma_user NULL,
    user_address__last_modified_by integer REFERENCES gowma_user NULL,
    user_detail__is_deleted  boolean NOT NULL DEFAULT FALSE
);







CREATE TABLE role
(
    id SERIAL PRIMARY KEY,
    role__name  VARCHAR NOT NULL,
    role__description  VARCHAR NOT NULL,
    role__created_date timestamp default NULL,
    role__last_modified_date timestamp default NULL,
    role__created_by integer REFERENCES gowma_user NULL,
    role__last_modified_by integer REFERENCES gowma_user NULL,
    role__is_deleted  boolean NOT NULL DEFAULT FALSE
);

CREATE TABLE user_role_mapping
(
    id SERIAL PRIMARY KEY,
    user_role_mapping__user_id integer REFERENCES gowma_user NOT NULL,
    user_role_mapping__role_id integer REFERENCES role NOT NULL,
    user_role_mapping__created_date timestamp default NULL,
    user_role_mapping__last_modified_date timestamp default NULL,
    user_role_mapping__created_by integer REFERENCES gowma_user NULL,
    user_role_mapping__last_modified_by integer REFERENCES gowma_user NULL,
    user_role_mapping__is_deleted  boolean NOT NULL DEFAULT FALSE
);

CREATE TABLE ui_action
(
    id SERIAL PRIMARY KEY,
    ui_action__name  VARCHAR NOT NULL,
    ui_action__parent_id integer REFERENCES ui_action,
    ui_action__order_no integer NOT NULL,
    ui_action__display_name  VARCHAR NOT NULL,
    ui_action__component_path VARCHAR NOT NULL,
    ui_action__enabled  boolean NOT NULL DEFAULT TRUE,
    ui_action__query_param  VARCHAR NOT NULL,
    ui_action__created_date timestamp default NULL,
    ui_action__last_modified_date timestamp default NULL,
    ui_action__created_by integer REFERENCES gowma_user NULL,
    ui_action__last_modified_by integer REFERENCES gowma_user NULL,
    ui_action__is_deleted  boolean NOT NULL DEFAULT FALSE
);

CREATE TABLE role_ui_action_mapping
(
    id SERIAL PRIMARY KEY,
    role_ui_action_mapping__ui_action_id integer REFERENCES ui_action NOT NULL,
    role_ui_action_mapping__role_id integer REFERENCES role NOT NULL,
    role_ui_action_mapping__created_date timestamp default NULL,
    role_ui_action_mapping__last_modified_date timestamp default NULL,
    role_ui_action_mapping__created_by integer REFERENCES gowma_user NULL,
    role_ui_action_mapping__last_modified_by integer REFERENCES gowma_user NULL,
    role_ui_action_mapping__is_deleted  boolean NOT NULL DEFAULT FALSE
);