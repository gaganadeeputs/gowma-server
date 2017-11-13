CREATE TYPE status AS ENUM ('ACTIVE', 'DEACTIVE');

CREATE TABLE gowma_user
 (
    id SERIAL PRIMARY KEY,
    user__login_name varchar(30) NOT NULL,
    user__password_hash character varying(120) NOT NULL,
    user__status status NOT NULL,
    user__created_date timestamp default NULL,
    user__last_modified_date timestamp default NULL,
    user__created_by integer REFERENCES gowma_user NULL,
    user__last_modified_by integer REFERENCES gowma_user NULL,
    user__is_deleted  boolean NOT NULL DEFAULT FALSE
);

CREATE TYPE gender AS ENUM ('MALE', 'FEMALE' ,'OTHER');
CREATE TABLE user_detail
(
    id SERIAL PRIMARY KEY,
    user_detail_user_id integer REFERENCES gowma_user NOT NULL,
    user_detail__email varchar(30) NOT NULL,
    user_detail__phone_no character varying(64) NOT NULL,
    user_detail_fname character varying(64) NOT NULL,
    user_detail_lname character varying(64) NOT NULL,
    user_detail_gender gender NOT NULL,
    user_detail__created_date timestamp default NULL,
    user_detail__last_modified_date timestamp default NULL,
    user_detail__created_by integer REFERENCES gowma_user NULL,
    user_detail__last_modified_by integer REFERENCES gowma_user NULL,
    user_detail__is_deleted  boolean NOT NULL DEFAULT FALSE,
    CONSTRAINT unq_email UNIQUE (user_detail__email)
);

CREATE TYPE address_type AS ENUM ('HOME', 'WORK');
CREATE TABLE user_address
(
    id SERIAL PRIMARY KEY,
    user_address_user_id integer REFERENCES gowma_user NOT NULL,
    user_address__address_type address_type,
    user_address_name character varying(64) NOT NULL,
    user_address__address1  character varying(64) NOT NULL,
    user_address__address2 character varying(64) NOT NULL,
    user_address__landmark character varying(64) NOT NULL,
    user_address__phone_no character varying(64) NOT NULL,
    user_address__is_default  boolean NOT NULL DEFAULT false,
    user_address__created_date timestamp default NULL,
    user_address__last_modified_date timestamp default NULL,
    user_address__created_by integer REFERENCES gowma_user NULL,
    user_address__last_modified_by integer REFERENCES gowma_user NULL,
    user_detail__is_deleted  boolean NOT NULL DEFAULT FALSE
);