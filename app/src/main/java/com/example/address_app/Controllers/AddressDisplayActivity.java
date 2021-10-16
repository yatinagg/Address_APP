package com.example.address_app.Controllers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.address_app.Adapter.AddressViewAdapter;
import com.example.address_app.Address;
import com.example.address_app.AddressView;
import com.example.address_app.R;
import com.example.address_app.RetrofitBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AddressDisplayActivity extends AppCompatActivity implements OnButtonClickListener {

    public static AddressDisplayActivity addressDisplayActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addressDisplayActivity = this;
        setContentView(R.layout.activity_address_display);
        ActionBar actionBar = getSupportActionBar();

        // providing title for the ActionBar
        if (actionBar != null)
            actionBar.setTitle(R.string.addresses);

        setAddressListView();
        // providing title for the ActionBar
        if (actionBar != null)
            actionBar.setTitle(R.string.addresses);
        FloatingActionButton floatingActionButton = findViewById(R.id.floating_action_button_display_add_address_display);
        floatingActionButton.setOnClickListener(view -> startActivity(new Intent(view.getContext(), AddressEntryActivity.class)));
    }

    public static AddressDisplayActivity getInstance() {
        return addressDisplayActivity;
    }

    public void setAddressListView() {
        HomeActivity.addressViews = new ArrayList<>();

        for (int i = 0; i < HomeActivity.addressList.size(); i++) {
            AddressView addressView = new AddressView(HomeActivity.addressList.get(i), R.drawable.default_icon);
            HomeActivity.addressViews.add(addressView);
        }
        HomeActivity.AddressArrayAdapter = new AddressViewAdapter(this, HomeActivity.addressViews, this);

        ListView addressListView = findViewById(R.id.list_view);
        addressListView.setAdapter(HomeActivity.AddressArrayAdapter);
    }

    public void notifyDataChange() {
        if (HomeActivity.AddressArrayAdapter == null) {
            HomeActivity.AddressArrayAdapter = new AddressViewAdapter(this, HomeActivity.addressViews, this);
            ListView addressListView = findViewById(R.id.list_view);
            addressListView.setAdapter(HomeActivity.AddressArrayAdapter);
        } else {
            // lets find all the duplicates and do all the updating
            // finally notify the adapter/listview
            HomeActivity.AddressArrayAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        startActivity(a);
    }


    @Override
    public void onButtonClick(int index, int position, Address address) {
        if (index == 0) {
            Intent intent = new Intent(this, AddressEntryActivity.class);
            intent.putExtra("Mode", "Update");
            intent.putExtra("Id", address.getId());
            intent.putExtra("Position", position);
            this.startActivity(intent);
        } else if (index == 1) {
            ProgressDialog progressDialog = new ProgressDialog(this);
            RetrofitBuilder.deleteData(address, progressDialog);
            System.out.println("Done");
        }
    }
}
