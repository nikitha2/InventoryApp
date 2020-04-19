package com.nikitha.android.inventoryapp.Data;

import android.content.ContentResolver;

import java.net.URI;

public class InventoryContract {

    public class InventoryDbTableEntry {
        public final static String DATABASE_NAME = "inventorydb";
        public final static String TABLE_NAME = "inventorytable";

        public final static String PRODUCT_NAME = "Name";
        public final static String PRODUCT_QUANTITY = "Quantity";
        public final static String PRODUCT_PRICE = "Price";
        public final static String PRODUCT_IMAGE = "Image";
        public final static String PRODUCT_SUPPLIER_EMAIL = "Supplier_emailid";

        public final static String CONTENT_AUTHORITY="com.nikitha.android.inventoryapp.Data";

        public final static int INVENTORY_CODE = 200;
        public final static int INVENTORY_WITH_ID_CODE = 201;
        public final static int INVENTORY_WITH_FILTER_CODE = 202;


        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of pets.
         */
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single pet.
         */
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;

        //content URI format- content://authority/path/id
        public final static String URI="content://"+CONTENT_AUTHORITY+"/"+TABLE_NAME;
//        public static final String URI="content://com.example.android.pets.Data/"+TABLE_NAME;
//        public final static String CONTENT_AUTHORITY="com.nikitha.android.inventoryapp.Data";


    }


}
