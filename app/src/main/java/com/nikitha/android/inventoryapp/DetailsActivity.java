package com.nikitha.android.inventoryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import static com.nikitha.android.inventoryapp.Data.InventoryContract.InventoryDbTableEntry.*;

public class DetailsActivity extends AppCompatActivity {
    Uri uri=Uri.parse(URI);
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_layout);

        Intent intent = getIntent();
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(intent.hasExtra("id")) {
            Long id= intent.getLongExtra("id",0);
            setTitle(getResources().getText(R.string.editInventory));
            fragmentTransaction.replace(R.id.container1, new EditInventoryDetailsFragment(id));;
            fragmentTransaction.commit();
        }else {
            setTitle(getResources().getText(R.string.editInventory));
            fragmentTransaction.replace(R.id.container1, new NewInventoryDetailsFragment());;
            fragmentTransaction.commit();
            new NewInventoryDetailsFragment();
        }

    }
}
