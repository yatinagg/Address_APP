package com.example.address_app.Controllers;

import static com.example.address_app.RetrofitBuilder.retrofitAPI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.address_app.Adapter.AddressListAdapter;
import com.example.address_app.Address;
import com.example.address_app.R;
import com.example.address_app.RetrofitBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressDisplayActivity extends AppCompatActivity implements OnButtonClickListener {

    public List<Address> addressList = null;
    public static List<Address> addressViews = null;
    public AddressListAdapter AddressArrayAdapter = null;
    public static int defaultAddress = -1;
    public static AddressDisplayActivity addressDisplayActivity;
    private View emptyView;
    private FloatingActionButton floatingActionButtonDisplayAddAddressDisplay;
    private ListView addressListView;
    private TextView tvBlankAddress;
    private TextView tvBlankAddressDescription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        addressDisplayActivity = this;
        ActionBar actionBar = getSupportActionBar();
        setContentView(R.layout.activity_address_display);
        setupView();
        // providing title for the ActionBar
        if (actionBar != null)
            actionBar.setTitle(R.string.addresses);


        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait!!");
        progressDialog.setMessage("Wait!!");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        Call<List<Address>> call = retrofitAPI.getAddress(RetrofitBuilder.token);
        call.enqueue(new Callback<List<Address>>() {
            @Override
            public void onResponse(@NonNull Call<List<Address>> call, @NonNull Response<List<Address>> response) {
                Log.d("output", String.valueOf(response.body()));
                if (response.isSuccessful()) {
                    addressList = response.body();
                    progressDialog.dismiss();
                }
                setData();
            }

            @Override
            public void onFailure(@NonNull Call<List<Address>> call, @NonNull Throwable t) {
                // displaying an error message in toast
                Log.d("output", "failure");
                progressDialog.dismiss();
                setData();
            }
        });

    }

    void setupView() {
        emptyView = findViewById(R.id.empty_view);
        floatingActionButtonDisplayAddAddressDisplay = findViewById(R.id.floating_action_button_display_add_address_display);
        addressListView = findViewById(R.id.list_view);
        tvBlankAddress = findViewById(R.id.tv_blank_address);
        tvBlankAddressDescription = findViewById(R.id.tv_blank_address_description);
        FloatingActionButton button1 = findViewById(R.id.floating_action_button);
        floatingActionButtonDisplayAddAddressDisplay.setOnClickListener(view -> startActivity(new Intent(view.getContext(), AddressEntryActivity.class)));
        button1.setOnClickListener(view -> startActivity(new Intent(view.getContext(), AddressEntryActivity.class)));
    }

    void setData() {
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
            this.startActivity(intent);
        } else if (index == 1) {
            ProgressDialog progressDialog = new ProgressDialog(this);
            RetrofitBuilder.deleteData(address, progressDialog);
            addressList.remove(position);
            System.out.println("Done");
        }
    }
}
