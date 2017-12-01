/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.constants;

public final class EndPoints {

    public static final String API_V1_ROOT = "api/v1";

    private EndPoints() {

    }

    public static final class PathVariable {

        public static final String USER_ID = "user-id";
        public static final String PRODUCT_CATEGORY_ID = "product-category-id";
        public static final String ADDRESS_ID ="address-id";
        public static final String PRODUCT_ID = "product-id" ;
        public static final String PRODUCT_IMAGE_ID = "product-image-id" ;
        public static final String PRODUCT_INVENTORY_ID = "product-inventory-id" ;
        public static final String OFFER_ID = "offer-id" ;
        public static final String TAX_DETAIL_ID = "tax-detail-id";
        public static final String SHOPPING_CART_ITEM_ID = "shopping-cart-item-id";
    }

    public static final class PathVariableTemplate {

        public static final String USER_ID_TEMPLATE = "{" + PathVariable.USER_ID + "}";
        public static final String PRODUCT_CATEGORY_ID_TEMPLATE = "{" + PathVariable.PRODUCT_CATEGORY_ID + "}";
        public static final String ADDRESS_ID_TEMPLATE = "{" + PathVariable.ADDRESS_ID + "}";
        public static final String PRODUCT_ID_TEMPLATE ="{"+PathVariable.PRODUCT_ID +"}";
        public static final String PRODUCT_INVENTORY_WITH_ID_TEMPLATE ="{"+PathVariable.PRODUCT_INVENTORY_ID +"}";
        public static final String PRODUCT_IMAGE_WITH_ID_TEMPLATE ="{"+PathVariable.PRODUCT_IMAGE_ID +"}";
        public static final String OFFER_WITH_ID_TEMPLATE ="{"+PathVariable.OFFER_ID +"}";
        public static final String TAX_DETAIL_WITH_ID_TEMPLATE ="{"+PathVariable.TAX_DETAIL_ID +"}";
        public static final String SHOPPING_CART_ITEM_ID_TEMPLATE ="{"+PathVariable.SHOPPING_CART_ITEM_ID +"}";
    }

    public static final class QueryParam {

        public static final String PARENT_CATEGORY_ID = "parent-category-id";
        public static final String OFFER_PRODUCT_ID = "offer-product-id";
        public static final String OFFER_CATEGORY_ID = "offer-category-id";
        public static final String ADDRESS_CODE = "address-code";
    }


    public static final class RequestParam {

        public static final String FILES = "files";
    }

    public static final class User {

        public static final String ROOT = API_V1_ROOT + "/user";
        public static final String USER_WITH_ID = ROOT + "/" + PathVariableTemplate.USER_ID_TEMPLATE;
        public static  final String USER_ADDRESS = USER_WITH_ID + "/addresses";
        public static  final String USER_ADDRESS_WITH_ID = USER_ADDRESS + " " + PathVariableTemplate.ADDRESS_ID_TEMPLATE;
        public static  final String USER_WISH_LIST = USER_WITH_ID + "/product-wish-list";
        public static final String  USER_WISH_LIST_WITH_PRODUCT_ID = USER_WISH_LIST + "/" + PathVariableTemplate.PRODUCT_ID_TEMPLATE;
        public static  final String USER_CART = USER_WITH_ID + "/cart";
        public static  final String USER_CART_ITEM_WITH_ID = USER_WITH_ID + "/" + PathVariableTemplate.SHOPPING_CART_ITEM_ID_TEMPLATE;


    }

    public static final class File {
        public static final String ROOT = API_V1_ROOT + "/files";
    }
    public static final class ProductCategory {

        public static final String ROOT = API_V1_ROOT + "/product-categories";
        public static final String PRODUCT_CATEGORY_WITH_ID = ROOT + "/" + PathVariableTemplate.PRODUCT_CATEGORY_ID_TEMPLATE;
        public static final String PRODUCT_CATEGORY_SEARCH = ROOT + "/_search";

    }

    public static final class Product {

        public static final String ROOT = API_V1_ROOT + "/products";
        public static final String PRODUCT_WITH_ID = ROOT + "/" + PathVariableTemplate.PRODUCT_ID_TEMPLATE;
        public static final String PRODUCT_SEARCH = ROOT + "/_search";

        public static final String PRODUCT_IMAGES = PRODUCT_WITH_ID + "/product-images";
        public static final String PRODUCT_IMAGE_WITH_ID = PRODUCT_IMAGES + "/" + PathVariableTemplate.PRODUCT_IMAGE_WITH_ID_TEMPLATE;

        public static final String PRODUCT_INVENTORY = PRODUCT_WITH_ID + "/product-inventory";
        public static final String PRODUCT_INVENTORY_WITH_ID = PRODUCT_INVENTORY +  PathVariableTemplate.PRODUCT_INVENTORY_WITH_ID_TEMPLATE;

    }


    public static final class Offer {
        public static final String ROOT = API_V1_ROOT + "/offers";
        public static final String OFFER_WITH_ID = ROOT + "/" + PathVariableTemplate.OFFER_WITH_ID_TEMPLATE;


    }


    public static final class TaxDetail {
        public static final String ROOT = API_V1_ROOT + "/tax-detail";
        public static final String TAX_DETAIL_WITH_ID = ROOT + "/" + PathVariableTemplate.TAX_DETAIL_WITH_ID_TEMPLATE;


    }


    public static final class AddressCode {
        public static final String ROOT = API_V1_ROOT + "/address-code";

    }
}
