package com.nikitha.android.inventoryapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static android.provider.BaseColumns._ID;
import static com.nikitha.android.inventoryapp.Data.Constants.COUNTER;
import static com.nikitha.android.inventoryapp.Data.InventoryContract.InventoryDbTableEntry.*;

public class InventoryListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    Uri uri= Uri.parse(URI);
    int counter=0;
    Intent intent;
    InventoryListCursorAdaptor inventoryListCursorAdaptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_list);



        inventoryListCursorAdaptor=new InventoryListCursorAdaptor(this,null,0);
        ListView inventoryList=(ListView) findViewById(R.id.inventoryList);
        inventoryList.setAdapter(inventoryListCursorAdaptor);
        inventoryList.setEmptyView(findViewById(R.id.empty_view));

        Loader<Cursor> cursorLoader = getSupportLoaderManager().initLoader(1, null, this);

        inventoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // Intent intent=new Intent(this,DetailsActivity.class);
                Toast.makeText(getBaseContext(), "Hello", Toast.LENGTH_SHORT).show();
                intent = new Intent(getBaseContext(), DetailsActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getBaseContext(), DetailsActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_inventorylist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.createdummydata: long newRowId =createDummyData();
                                        Toast toast ;
                                        if(newRowId!=-1) {
                                            toast = Toast.makeText(this,"Added :"+newRowId, Toast.LENGTH_SHORT);
                                            toast.show();
                                        }
                                        else{
                                            toast=Toast.makeText(this,"Error when insert data", Toast.LENGTH_SHORT);
                                            toast. show();
                                        }
                                        return true;
             case R.id.deleteall: int noOfRowsDeleted = getContentResolver().delete(uri, null, null);
                                  if(noOfRowsDeleted>0){
                                       return true;
                                   }
        }
        return super.onOptionsItemSelected(item);
    }

    private long createDummyData() {
        ContentValues values = new ContentValues();
        values.put(PRODUCT_NAME, "Inventory "+COUNTER);
        values.put(PRODUCT_PRICE, 50+COUNTER);
        //values.put(PRODUCT_IMAGE, R.drawable.saleblue);
        values.put(PRODUCT_QUANTITY, 20+COUNTER);
        values.put(PRODUCT_SUPPLIER_EMAIL, "suplieremail"+COUNTER+"@gmail.com");
        COUNTER++;
        Uri newUri = getContentResolver().insert(uri, values);
        long id = ContentUris.parseId(newUri);
        return id;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] columns={_ID,PRODUCT_NAME,PRODUCT_QUANTITY,PRODUCT_PRICE/*,PRODUCT_IMAGE*/,PRODUCT_SUPPLIER_EMAIL};
        CursorLoader petsCursorLoader = new CursorLoader(this, uri, columns,null, null, null);
        return petsCursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        inventoryListCursorAdaptor.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        inventoryListCursorAdaptor.swapCursor(null);
    }


}
