package com.nikitha.android.inventoryapp.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import static com.nikitha.android.inventoryapp.Data.Constants.*;
import static com.nikitha.android.inventoryapp.Data.InventoryContract.InventoryDbTableEntry.*;

public class InventorydbHelper extends SQLiteOpenHelper {
    public InventorydbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL(CREATE_SQL_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DATABASE_VERSION =newVersion;
        db.execSQL(DELETE_SQL_DB);
        onCreate(db);
    }
}
