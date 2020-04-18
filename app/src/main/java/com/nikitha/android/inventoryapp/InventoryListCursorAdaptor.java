package com.nikitha.android.inventoryapp;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import static android.provider.BaseColumns._ID;
import static com.nikitha.android.inventoryapp.Data.InventoryContract.InventoryDbTableEntry.*;

public class InventoryListCursorAdaptor extends CursorAdapter {
    String quantityValue;Button saleBtn;
    TextView quantity;
    Context contextNew;
    Activity contextActivity;
    Uri uri=Uri.parse(URI);

    public InventoryListCursorAdaptor(Context context, Cursor c, int flags) {
        super(context, c, 0);
        this.contextNew= context;
        this.contextActivity= (Activity) context;
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item_layout, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //cursor.moveToFirst();
        TextView productName = (TextView)view.findViewById(R.id.productName);
        productName.setText(cursor.getString(cursor.getColumnIndex(PRODUCT_NAME)));

        quantity = (TextView)view.findViewById(R.id.quantity);
        quantityValue = cursor.getString(cursor.getColumnIndex(PRODUCT_QUANTITY));
        quantity.setText(quantityValue);

        TextView price = (TextView)view.findViewById(R.id.price);
        String priceValue=cursor.getString(cursor.getColumnIndex(PRODUCT_PRICE));
        price.setText(cursor.getString(cursor.getColumnIndex(PRODUCT_PRICE)));

        String p=cursor.getString(cursor.getColumnIndex(_ID));
        //view.setTag(cursor.getString(cursor.getColumnIndex(_ID)));

        saleBtn = (Button)view.findViewById(R.id.sale);
        saleBtn.setTag(p);
        saleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = (String)v.getTag();
                ContentValues value=new ContentValues();
                String selection = _ID + "=?";
                String[] selectionArgs = new String[]{id};

                String[] columns={PRODUCT_QUANTITY};
                Cursor queryResult = contextNew.getContentResolver().query(uri, columns, selection, selectionArgs, null);
                if(queryResult!=null) {
                    queryResult.moveToFirst();
                    String q = queryResult.getString(queryResult.getColumnIndex(PRODUCT_QUANTITY));
                    int quantityValue = Integer.parseInt(q) ;
                    quantityValue--;

                    quantity.setText(String.valueOf(quantityValue));
                    value.put(PRODUCT_QUANTITY, quantityValue);
                    contextNew.getContentResolver().update(uri,value,selection,selectionArgs);
                }

            }
        });

//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String id = (String)v.getTag();
//                Toast.makeText(contextNew, "Hello", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(contextNew, DetailsActivity.class);
//                intent.putExtra("id",id);
//                contextNew.startActivity(intent);
//            }
//        });

    }
}
