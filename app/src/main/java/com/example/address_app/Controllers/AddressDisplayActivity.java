package com.example.address_app.Controllers;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import com.example.address_app.Adapter.AddressListAdapter;
import com.example.address_app.Pojos.Address;
import com.example.address_app.R;
import com.example.address_app.RetrofitBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AddressDisplayActivity extends AppCompatActivity implements OnButtonClickListener, ApiCallStatusChecker {

    public List<Address> addressList = null;
    public static List<Address> addressViews = null;
    public AddressListAdapter AddressArrayAdapter = null;
    public static int defaultAddress = -1;
    public static AddressDisplayActivity addressDisplayActivity;
    private View emptyView;
    private FloatingActionButton floatingActionButtonDisplayAddAddressDisplay;
    private FloatingActionButton floatingActionButton;
    private ListView addressListView;
    private TextView tvBlankAddress;
    private TextView tvBlankAddressDescription;
    private ConstraintLayout progressDisplay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        addressDisplayActivity = this;
        ActionBar actionBar = getSupportActionBar();
        setContentView(R.layout.activity_address_display);
        setupView();
        setupListeners();
        // providing title for the ActionBar
        if (actionBar != null)
            actionBar.setTitle(R.string.addresses);


        //ProgressDialog progressDialog = new ProgressDialog(this);
        progressDisplay.setVisibility(View.VISIBLE);
        RetrofitBuilder.getAddress(this);

    }

    void setupView() {
        emptyView = findViewById(R.id.empty_view);
        progressDisplay = findViewById(R.id.progress_display);
        floatingActionButtonDisplayAddAddressDisplay = findViewById(R.id.floating_action_button_display_add_address_display);
        addressListView = findViewById(R.id.list_view);
        tvBlankAddress = findViewById(R.id.tv_blank_address);
        tvBlankAddressDescription = findViewById(R.id.tv_blank_address_description);
        floatingActionButton = findViewById(R.id.floating_action_button);
    }

    private void setupListeners() {
        floatingActionButtonDisplayAddAddressDisplay.setOnClickListener(view -> startActivity(new Intent(view.getContext(), AddressEntryActivity.class)));
        floatingActionButton.setOnClickListener(view -> startActivity(new Intent(view.getContext(), AddressEntryActivity.class)));
    }

    void setData() {


        progressDisplay.setVisibility(View.GONE);
        // blank address page
        if (addressList != null && addressList.size() != 0) {
            emptyView.setVisibility(View.GONE);
            addressListView.setVisibility(View.VISIBLE);
            setAddressListView();
        }
        // address list page
        else {
            emptyView.setVisibility(View.VISIBLE);
            addressListView.setVisibility(View.GONE);
            floatingActionButtonDisplayAddAddressDisplay.setVisibility(View.GONE);

            Typeface face = ResourcesCompat.getFont(this, R.font.mulish_variable_font_wght);
            tvBlankAddress.setTypeface(face);
            tvBlankAddressDescription.setTypeface(face);
        }
    }

    public static AddressDisplayActivity getInstance() {
        return addressDisplayActivity;
    }

    public void setAddressListView() {
        floatingActionButtonDisplayAddAddressDisplay.setVisibility(View.VISIBLE);
        AddressDisplayActivity.addressViews = new ArrayList<>();
        AddressDisplayActivity.addressViews.addAll(addressList);
        AddressArrayAdapter = new AddressListAdapter(this, AddressDisplayActivity.addressViews, this);
        addressListView.setAdapter(AddressArrayAdapter);
    }

    public void notifyDataChange() {
        if (AddressArrayAdapter == null) {
            AddressArrayAdapter = new AddressListAdapter(this, AddressDisplayActivity.addressViews, this);
            ListView addressListView = findViewById(R.id.list_view);
            addressListView.setAdapter(AddressArrayAdapter);
        } else {
            // lets find all the duplicates and do all the updating
            // finally notify the adapter/listview
            AddressArrayAdapter.notifyDataSetChanged();
            if (addressList.size() == 0) {
                setData();
            }
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
    public void onButtonClick(int index, int position, Address address, boolean defaultAddress) {
        if (index == 0) {
            Intent intent = new Intent(this, AddressEntryActivity.class);
            intent.putExtra("Mode", "Update");
            intent.putExtra("Position", position);
            intent.putExtra("Id", address.getId());
            intent.putExtra("Default", defaultAddress);
            this.startActivity(intent);
        } else if (index == 1) {
            if (defaultAddress) {
                AddressDisplayActivity.defaultAddress = -1;
            }
            progressDisplay.setVisibility(View.VISIBLE);
            RetrofitBuilder.deleteData(address);
            addressList.remove(position);
            System.out.println("Done");
        }
    }

    @Override
    public void apiCallComplete(String mode, List<Address> addressList) {
        if (mode.equals("Get")) {
            this.addressList = addressList;
            setData();
        } else if (mode.equals("Delete")) {
            notifyDataChange();
            progressDisplay.setVisibility(View.GONE);
        }
    }
}
