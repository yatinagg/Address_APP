package com.example.address_app.Controllers;

import static com.example.address_app.RetrofitBuilder.retrofitAPI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.address_app.Adapter.AddressViewAdapter;
import com.example.address_app.Address;
import com.example.address_app.AddressView;
import com.example.address_app.R;
import com.example.address_app.RetrofitBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    public static int lenAddress;
    public static List<Address> addressList = null;
    public static List<AddressView> addressViews = null;
    public static AddressViewAdapter AddressArrayAdapter = null;
    public static int defaultAddress = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();

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
                    HomeActivity.addressList = response.body();
                    if (HomeActivity.addressList != null)
                        HomeActivity.lenAddress = HomeActivity.addressList.size();
                    else
                        HomeActivity.lenAddress = 0;
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

    void setData(){
        // blank address page
        if (lenAddress != 0) {
            Intent intent = new Intent(this, AddressDisplayActivity.class);
            startActivity(intent);

        }
        // address list page
        else {
            setContentView(R.layout.activity_blank_address);
            TextView tvBlankAddress = findViewById(R.id.tv_blank_address);
            TextView tvBlankAddressDescription = findViewById(R.id.tv_blank_address_description);
            Typeface face = ResourcesCompat.getFont(this, R.font.mulish_variable_font_wght);
            tvBlankAddress.setTypeface(face);
            tvBlankAddressDescription.setTypeface(face);
            FloatingActionButton button1 = findViewById(R.id.floating_action_button);
            button1.setOnClickListener(view -> {
                Intent intent = new Intent(view.getContext(), AddressEntryActivity.class);
                startActivity(intent);
            });
        }
    }
}
