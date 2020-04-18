package com.nikitha.android.inventoryapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import static android.provider.BaseColumns._ID;
import static com.nikitha.android.inventoryapp.Data.InventoryContract.InventoryDbTableEntry.*;

public class EditInventoryDetailsFragment extends Fragment {
    private static final String LOG_TAG = EditInventoryDetailsFragment.class.getSimpleName();

    FragmentActivity activity = getActivity();
    Uri uri=Uri.parse(URI);
    Long id;
    TextView productNameValue;
    TextView quanitityAmt;
    TextView productPriceValue;
    Button minusQuanitity ;
    Button plusQuanitity;
    String quantity;
    Button delete;

    public EditInventoryDetailsFragment(Long id) {
        super();
        this.id=id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details_edit, container, false);
        super.onCreate(savedInstanceState);

        String selection = _ID + "=?";
        String[] selectionArgs = new String[]{Long.toString(id)};
        String[] columns={_ID,PRODUCT_NAME,PRODUCT_QUANTITY,PRODUCT_PRICE/*,PRODUCT_IMAGE*/,PRODUCT_SUPPLIER_EMAIL};
        Cursor queryResult = getContext().getContentResolver().query(uri, columns, selection, selectionArgs, null);

        queryResult.moveToFirst();
        if(queryResult!=null){
            productNameValue = (TextView)rootView.findViewById(R.id.productNameValue);
            quanitityAmt = (TextView)rootView.findViewById(R.id.quanitityAmt);
            productPriceValue = (TextView)rootView.findViewById(R.id.productPriceValue);
            productPriceValue = (TextView)rootView.findViewById(R.id.productPriceValue);
            minusQuanitity = (Button) rootView.findViewById(R.id.minusQuanitity);
            plusQuanitity = (Button) rootView.findViewById(R.id.plusQuanitity);
            delete = (Button) rootView.findViewById(R.id.delete);

            String productName = queryResult.getString(queryResult.getColumnIndex(PRODUCT_NAME));
            quantity = queryResult.getString(queryResult.getColumnIndex(PRODUCT_QUANTITY));
            String productPrice = queryResult.getString(queryResult.getColumnIndex(PRODUCT_PRICE));

            productNameValue.setText(productName);
            quanitityAmt.setText(quantity);
            productPriceValue.setText(productPrice);
            minusQuanitity.setTag(Long.toString(id));
            plusQuanitity.setTag(Long.toString(id));
            delete.setTag(Long.toString(id));

            minusQuanitity.setOnClickListener(new  View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = (String)v.getTag();
                    String selection = _ID + "=?";
                    String[] selectionArgs = new String[]{id};
                    String[] columns={PRODUCT_QUANTITY};
                    Cursor queryResult = getContext().getContentResolver().query(uri, columns, selection, selectionArgs, null);
                    queryResult.moveToFirst();
                    quantity=queryResult.getString(queryResult.getColumnIndex(PRODUCT_QUANTITY));
                     int quan= Integer.parseInt(quantity);
                    quan--;

                    ContentValues value=new ContentValues();
                    value.put(PRODUCT_QUANTITY, (quan)); //Integer.toString
                    getContext().getContentResolver().update(uri,value,selection,selectionArgs);
                    quanitityAmt.setText(quantity);

                }
            });

            plusQuanitity.setOnClickListener(new  View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = (String)v.getTag();
                    String selection = _ID + "=?";
                    String[] selectionArgs = new String[]{id};
                    String[] columns={PRODUCT_QUANTITY};
                    Cursor queryResult = getContext().getContentResolver().query(uri, columns, selection, selectionArgs, null);
                    queryResult.moveToFirst();
                    quantity=queryResult.getString(queryResult.getColumnIndex(PRODUCT_QUANTITY));
                    int quan= Integer.parseInt(quantity);
                    quan++;

                    ContentValues value=new ContentValues();
                    value.put(PRODUCT_QUANTITY, (quan)); //Integer.toString
                    getContext().getContentResolver().update(uri,value,selection,selectionArgs);
                    quanitityAmt.setText(quantity);

                }
            });

            delete.setOnClickListener(new  View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = (String) v.getTag();
                    String selection = _ID + "=?";
                    String[] selectionArgs = new String[]{id};
                    String[] columns={PRODUCT_QUANTITY};
                    Cursor queryResult = getContext().getContentResolver().query(uri, columns, selection, selectionArgs, null);
                    queryResult.moveToFirst();
                    quantity=queryResult.getString(queryResult.getColumnIndex(PRODUCT_QUANTITY));
                }
            });
        }
        return rootView;
    }

//    @Override
//    public void onStop() {
//        super.onStop();
//        listView1Adaptor.release();
//    }
}
