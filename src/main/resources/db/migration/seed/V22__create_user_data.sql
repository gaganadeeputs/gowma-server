INSERT INTO public.role(id,
	role__name, role__description)
	VALUES (100000,'CUSTOMER', 'Cutsomer role description' );

    INSERT INTO public.role(id,
	role__name, role__description)
	VALUES (100001,'ADMIN', 'Admin role description' );

    INSERT INTO gowma_user (id,gowma_user__email,gowma_user__mobile_no,gowma_user__password_hash,gowma_user__status) values (100000,'gagandeepts@gmail.com','8971993015','ddddddddd','ACTIVE');


	INSERT INTO user_detail(id,user_detail_user_id, user_detail_fname, user_detail_lname, user_detail_gender)
	VALUES (100000,100000, 'GAGAN', 'DEEPU', 'MALE');

    INSERT INTO country( id,country__name)
	VALUES (100000,'INDIA');

    INSERT INTO country(id, country__name)
	VALUES (100001,'United States');



	INSERT INTO state( id,state__country_id, state__name)
	VALUES (100000,100000, 'KARNATAKA');


	INSERT INTO state( id,state__country_id, state__name)
	VALUES (100001,100000, 'KERALA');

	INSERT INTO state( id,state__country_id, state__name)
	VALUES (100002,100000, 'TAMIL_NADU');


	INSERT INTO address_code( id,address_code__code, address_code__place_name, address_code__state_id)
	VALUES (100000,'560001', 'KR-CIRCLE', 100000 );

	INSERT INTO user_role_mapping( id,user_role_mapping__user_id, user_role_mapping__role_id)
	VALUES (100000,100000, 100000);

	INSERT INTO public.user_addresses(id,user_address__user_id, user_address__address_code_id, user_address__address_type, user_address_name, user_address__address1, user_address__address2, user_address__landmark, user_address__phone_no, user_address__is_default)
	VALUES (100000,100000, 100000, 'HOME', 'ADDRESS_NAME', 'ADDRESS1', 'ADDRESS2', 'LANDMARK', '8971993015', true);


	INSERT INTO public.user_addresses(id,user_address__user_id, user_address__address_code_id, user_address__address_type, user_address_name, user_address__address1, user_address__address2, user_address__landmark, user_address__phone_no, user_address__is_default)
	VALUES (100001,100000, 100000, 'HOME', 'ADDRESS_NAME', 'ADDRESS1', 'ADDRESS2', 'LANDMARK2', '8971993018', false);