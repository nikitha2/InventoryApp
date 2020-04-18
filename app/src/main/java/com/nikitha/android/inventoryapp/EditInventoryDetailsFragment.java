package com.nikitha.android.inventoryapp;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import static android.provider.BaseColumns._ID;
import static com.nikitha.android.inventoryapp.Data.InventoryContract.InventoryDbTableEntry.*;

public class EditInventoryDetailsFragment extends Fragment {
    private static final String LOG_TAG = EditInventoryDetailsFragment.class.getSimpleName();

    FragmentActivity activity = getActivity();
    Uri uri=Uri.parse(URI);
    Long id;String id1;
    EditText productNameValue;
    EditText quanitityAmt;
    EditText productPriceValue;
    Button minusQuanitity ;
    Button plusQuanitity,delete;
    String quantity,  productName, productPrice;

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
            productNameValue = (EditText)rootView.findViewById(R.id.productNameValue);
            quanitityAmt = (EditText)rootView.findViewById(R.id.quanitityAmt);
            productPriceValue = (EditText)rootView.findViewById(R.id.productPriceValue);
            minusQuanitity = (Button) rootView.findViewById(R.id.minusQuanitity);
            plusQuanitity = (Button) rootView.findViewById(R.id.plusQuanitity);
            delete = (Button) rootView.findViewById(R.id.delete);

            productName = queryResult.getString(queryResult.getColumnIndex(PRODUCT_NAME));
            quantity = queryResult.getString(queryResult.getColumnIndex(PRODUCT_QUANTITY));
            productPrice = queryResult.getString(queryResult.getColumnIndex(PRODUCT_PRICE));

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
                    /*Cursor queryResult = getContext().getContentResolver().query(uri, columns, selection, selectionArgs, null);
                    queryResult.moveToFirst();
*/
                    DialogInterface.OnClickListener discardButtonClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // User clicked "Discard" button, navigate to parent activity.
                            NavUtils.navigateUpFromSameTask(getActivity());
                        }
                    };
                    // Show a dialog that notifies the user they have unsaved changes
                    showUnsavedChangesDialog(discardButtonClickListener,id);

//                    quantity=queryResult.getString(queryResult.getColumnIndex(PRODUCT_QUANTITY));
                }
            });
        }
        return rootView;
    }

    /**
     * This code makes a AlertDialog using the AlertDialogBuilder. The method accepts a OnClickListener for the discard button.
     * We do this because the behavior for clicking back or up is a little bit different.
     * @param discardButtonClickListener
     */
    private void showUnsavedChangesDialog(DialogInterface.OnClickListener discardButtonClickListener,String id) {
        id1=id;
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard,  new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selection = _ID + "=?";
                String[] selectionArgs = new String[]{id1};
                getContext().getContentResolver().delete(uri,selection,selectionArgs);
                NavUtils.navigateUpFromSameTask(getActivity());
            }
        });
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Keep editing" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


//    @Override
//    public void onStop() {
//        super.onStop();
//        listView1Adaptor.release();
//    }
}
