package com.nikitha.android.inventoryapp.Data;

import static android.provider.BaseColumns._ID;
import static com.nikitha.android.inventoryapp.Data.InventoryContract.*;
import static com.nikitha.android.inventoryapp.Data.InventoryContract.InventoryDbTableEntry.*;

public class Constants {
    public static int DATABASE_VERSION=1;
    public static int COUNTER=1;

    public final static String CREATE_SQL_DB=  "CREATE TABLE " + TABLE_NAME + " ("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PRODUCT_NAME + " TEXT NOT NULL, "
            + PRODUCT_QUANTITY + " INTEGER NOT NULL, "
            + PRODUCT_PRICE + " INTEGER NOT NULL, "
           /* + PRODUCT_IMAGE + " BLOB NOT NULL,"*/
            + PRODUCT_SUPPLIER_EMAIL + " TEXT NOT NULL );";

    public final static String DELETE_SQL_DB= "DROP TABLE [IF EXISTS] "+ TABLE_NAME;
}
