package com.nikitha.android.inventoryapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import static com.nikitha.android.inventoryapp.Data.InventoryContract.InventoryDbTableEntry.PRODUCT_NAME;
import static com.nikitha.android.inventoryapp.Data.InventoryContract.InventoryDbTableEntry.PRODUCT_PRICE;
import static com.nikitha.android.inventoryapp.Data.InventoryContract.InventoryDbTableEntry.PRODUCT_QUANTITY;
import static com.nikitha.android.inventoryapp.Data.InventoryContract.InventoryDbTableEntry.PRODUCT_SUPPLIER_EMAIL;
import static com.nikitha.android.inventoryapp.Data.InventoryContract.InventoryDbTableEntry.URI;

public class NewInventoryDetailsFragment  extends Fragment {
    private static final String LOG_TAG = EditInventoryDetailsFragment.class.getSimpleName();
    FragmentActivity activity = getActivity();
    Uri uri = Uri.parse(URI);
    Long id;
    String id1;
    EditText productNameValue;
    EditText quanitityAmt;
    EditText productPriceValue,productEmailValue;
    Button minusQuanitity;
    Button plusQuanitity, delete;
    String quantity, productName, productPrice;

    public NewInventoryDetailsFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details_view, container, false);
        super.onCreate(savedInstanceState);

        productNameValue = (EditText) rootView.findViewById(R.id.productNameValue);
        quanitityAmt = (EditText) rootView.findViewById(R.id.quanitityAmt);
        productPriceValue = (EditText) rootView.findViewById(R.id.productPriceValue);
        productEmailValue = (EditText) rootView.findViewById(R.id.productEmailValue);

        setHasOptionsMenu(true);
        return rootView;
    }

  /*  @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.save_menu, menu);
    }*/

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.save_menu, menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                String productNameValue1 = productNameValue.getText().toString();
                String quanitityAmt1 = quanitityAmt.getText().toString();
                String productPriceValue1 = productPriceValue.getText().toString();
                String email=productEmailValue.getText().toString();
                ContentValues values = new ContentValues();
                values.put(PRODUCT_QUANTITY, (quanitityAmt1)); //Integer.toString
                values.put(PRODUCT_NAME, (productNameValue1));
                values.put(PRODUCT_PRICE, (productPriceValue1));
                values.put(PRODUCT_SUPPLIER_EMAIL, email);

                Uri newUri = getContext().getContentResolver().insert(uri, values);
                long newId = ContentUris.parseId(newUri);
                if (newId != -1) {
                    Log.e(LOG_TAG, String.valueOf(R.string.recordSaved));
                    Toast toast = Toast.makeText(getContext(), R.string.recordSaved, Toast.LENGTH_SHORT);
                    toast.show();
                    return true;
                }
        }
        return super.onOptionsItemSelected(item);
    }
}
