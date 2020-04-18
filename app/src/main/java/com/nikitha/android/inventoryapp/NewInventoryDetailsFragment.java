package com.nikitha.android.inventoryapp;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import static com.nikitha.android.inventoryapp.Data.InventoryContract.InventoryDbTableEntry.URI;

class NewInventoryDetailsFragment  extends Fragment {
    private static final String LOG_TAG = EditInventoryDetailsFragment.class.getSimpleName();

    FragmentActivity activity = getActivity();
    Uri uri=Uri.parse(URI);

    public NewInventoryDetailsFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details_view, container, false);
        super.onCreate(savedInstanceState);


        return rootView;
    }


    }
