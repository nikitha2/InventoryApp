package com.nikitha.android.inventoryapp.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.nikitha.android.inventoryapp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static android.provider.BaseColumns._ID;
import static com.nikitha.android.inventoryapp.Data.InventoryContract.InventoryDbTableEntry.*;

public class InventoryProvider extends ContentProvider {
    public static final String LOG_TAG = InventoryProvider.class.getSimpleName();

    InventorydbHelper inventorydbHelper;
    SQLiteDatabase db;
    Cursor result;
    long newId;
    public static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    int match;
    String priceValue="0";

    static {
        sUriMatcher.addURI(CONTENT_AUTHORITY, TABLE_NAME, INVENTORY_CODE);
        sUriMatcher.addURI(CONTENT_AUTHORITY, TABLE_NAME + "/#", INVENTORY_WITH_ID_CODE);
        sUriMatcher.addURI(CONTENT_AUTHORITY, TABLE_NAME + "/*", INVENTORY_WITH_FILTER_CODE);


    }

    @Override
    public boolean onCreate() {
        inventorydbHelper = new InventorydbHelper(getContext());
        db = inventorydbHelper.getWritableDatabase();
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        match = sUriMatcher.match(uri);
        switch (match) {
            case INVENTORY_CODE:
                result = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);break;
            case INVENTORY_WITH_ID_CODE:
                selection = _ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                result = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);break;
            case INVENTORY_WITH_FILTER_CODE:
                selection = PRODUCT_NAME + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                result = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);break;
            default:
                throw new IllegalStateException("Unexpected value: " + uri);
        }
        result.setNotificationUri(getContext().getContentResolver(), uri);
        return result;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        match = sUriMatcher.match(uri);
        switch (match) {
            case INVENTORY_CODE:
                return CONTENT_LIST_TYPE;
            case INVENTORY_WITH_ID_CODE:
                return CONTENT_ITEM_TYPE;

            default:
                throw new IllegalStateException("Unexpected value: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Uri newUri = ContentUris.withAppendedId(uri, -1);;
        values=ValuesValidation(values);
        if(NameNotNull(values)){
            newId = db.insert(TABLE_NAME, null, values);
            newUri = ContentUris.withAppendedId(uri, newId);
        }
        if (newId == -1) {
            String e = R.string.insetNotPossible + uri.toString();
            Log.e(LOG_TAG, e);
        }
        if (newId != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return newUri;
    }

    private boolean NameNotNull(ContentValues values) {
        if(values.containsKey(PRODUCT_NAME)){
            String name=values.getAsString(PRODUCT_NAME);
            if (TextUtils.isEmpty(name)) {
                Log.e(LOG_TAG, String.valueOf(R.string.name));
                Toast toast = Toast.makeText(getContext(), R.string.name, Toast.LENGTH_SHORT);
                toast.show();
                return false;
            }
        }
        return true;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        match = sUriMatcher.match(uri);
        int noOfRowsDeleted;
        switch (match) {
            case INVENTORY_CODE:
                noOfRowsDeleted = db.delete(TABLE_NAME, selection, selectionArgs);break;
            case INVENTORY_WITH_ID_CODE:
                selection = PRODUCT_NAME + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                noOfRowsDeleted = db.delete(TABLE_NAME, selection, selectionArgs);break;
            default:
                throw new IllegalStateException("Unexpected value: " + uri);

        }
        if (noOfRowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return noOfRowsDeleted;

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String
            selection, @Nullable String[] selectionArgs) {
        match = sUriMatcher.match(uri);
        int noOfRowsUpdated;
        if(values!=null) {
            values = ValuesValidation(values);
        }
        switch (match) {
            case INVENTORY_CODE:
                noOfRowsUpdated = db.update(TABLE_NAME, values, selection, selectionArgs);break;
            case INVENTORY_WITH_ID_CODE:
                selection = PRODUCT_NAME + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                noOfRowsUpdated = db.update(TABLE_NAME, values, selection, selectionArgs);break;
            default:
                throw new IllegalStateException("Unexpected value: " + uri);
        }
        if (noOfRowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return noOfRowsUpdated;
    }

    private ContentValues ValuesValidation(@Nullable ContentValues values) {
        if(values.containsKey(PRODUCT_QUANTITY)){
            String quan=values.getAsString(PRODUCT_QUANTITY);
            if(quan.isEmpty()||quan==null){
                quan="0";
            }
           else if(Integer.parseInt(quan)<0){
                quan="0";
            }
            values.put(PRODUCT_QUANTITY, quan);
        }
        if(values.containsKey(PRODUCT_PRICE)){
            String price=values.getAsString(PRODUCT_PRICE);
            if(price.isEmpty()||price==null){
                priceValue="0";
            }
           else if(Integer.parseInt(price)<0){
                priceValue="0";
            }
           else{
               priceValue=price;
            }
            values.put(PRODUCT_PRICE, priceValue);
        }

        if(values.containsKey(PRODUCT_SUPPLIER_EMAIL)){
            String email=values.getAsString(PRODUCT_SUPPLIER_EMAIL);
            if(email.isEmpty()||email==null){
                email="inventoryEmail@gmail.com";
            }
            values.put(PRODUCT_SUPPLIER_EMAIL, email);
        }
        if(values.containsKey(PRODUCT_NAME)){
            String name=values.getAsString(PRODUCT_NAME);
            Log.e(LOG_TAG, String.valueOf(R.string.name));
            Toast toast = Toast.makeText(getContext(), R.string.name, Toast.LENGTH_SHORT);
            toast.show();
        }
        return values;
    }
}


    //TODO: values validation