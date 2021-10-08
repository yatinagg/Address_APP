package com.example.address_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.address_app.Adapter.AddressViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AddressDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_display);
        ActionBar actionBar = getSupportActionBar();

        // providing title for the ActionBar
        if (actionBar != null)
            actionBar.setTitle(R.string.addresses);

        List<AddressView> addressViews = new ArrayList<>();

        for (int i = 0; i < HomeActivity.addressList.size(); i++) {
            AddressView addressView = new AddressView(HomeActivity.addressList.get(i), R.drawable.default_icon);
            addressViews.add(addressView);
        }
        AddressViewAdapter AddressArrayAdapter = new AddressViewAdapter(this, addressViews);

        ListView addressListView = findViewById(R.id.listView);
        addressListView.setAdapter(AddressArrayAdapter);
        // providing title for the ActionBar
        if (actionBar != null)
            actionBar.setTitle(R.string.addresses);
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButtonDisplay);
        floatingActionButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), AddressEntryActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        startActivity(a);
    }

}
