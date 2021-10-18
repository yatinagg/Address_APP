package com.example.address_app.Controllers;

import static com.example.address_app.RetrofitBuilder.retrofitAPI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.core.content.res.ResourcesCompat;

import com.example.address_app.Address;
import com.example.address_app.R;
import com.example.address_app.RetrofitBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressEntryActivity extends AppCompatActivity {


    private EditText etName;
    private EditText etAddressLine1;
    private EditText etAddressLine2;
    private EditText etCity;
    private EditText etState;
    private EditText etPincode;
    public static Address address;
    private String mode = "Create";
    private int ind;
    private CheckBox checkBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //addressEntryActivity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_entry);
        ActionBar actionBar = getSupportActionBar();
        // providing title for the ActionBar
        if (actionBar != null)
            actionBar.setTitle("Add Address");
        Intent intent = getIntent();
        mode = intent.getStringExtra("Mode");
        int id = intent.getIntExtra("Id", 0);
        ind = -1;
        for (int i = 0; i < HomeActivity.lenAddress; i++) {
            if (HomeActivity.addressList.get(i).getId() == id)
                ind = i;
        }
        if (ind == -1)
            address = new Address();
        else
            address = HomeActivity.addressList.get(ind);

        // get views
        etName = findViewById(R.id.et_person_name);
        etAddressLine1 = findViewById(R.id.et_address_line1);
        etAddressLine2 = findViewById(R.id.et_address_line2);
        etCity = findViewById(R.id.et_city);
        etState = findViewById(R.id.et_state);
        etPincode = findViewById(R.id.et_pincode);
        EditText etLandmark = findViewById(R.id.et_landmark);
        checkBox = findViewById(R.id.check_box);
        TextView tvDefaultText = findViewById(R.id.tv_default_text);


        Typeface face = ResourcesCompat.getFont(this, R.font.mulish_variable_font_wght);
        // set text
        if(intent.getBooleanExtra("Default", false)){
            checkBox.setChecked(true);
        }
        etName.setText(address.getFirstname());
        etName.setTypeface(face);
        etAddressLine1.setText(address.getAddress1());
        etAddressLine1.setTypeface(face);
        etAddressLine2.setText(address.getAddress2());
        etAddressLine2.setTypeface(face);
        etCity.setText(address.getCity());
        etCity.setTypeface(face);
        etLandmark.setTypeface(face);
        etState.setText(address.getStateName());
        etState.setTypeface(face);
        etPincode.setText(address.getZipcode());
        etPincode.setTypeface(face);
        tvDefaultText.setTypeface(face);

    }

    // validate text fields
    private boolean validateTextFields(String name, String address1, String address2, String city, String zipCode) {
        boolean valid = true;
        if (name.isEmpty()) {
            etName.setError(getString(R.string.required_field));
            valid = false;
        }
        if (address1.isEmpty()) {
            etAddressLine1.setError(getString(R.string.required_field));
            valid = false;
        }
        if (address2.isEmpty()) {
            etAddressLine2.setError(getString(R.string.required_field));
            valid = false;
        }
        if (city.isEmpty()) {
            etCity.setError(getString(R.string.required_field));
            valid = false;
        }
        if (zipCode.isEmpty()) {
            etPincode.setError(getString(R.string.required_field));
            valid = false;
        } else if (zipCode.length() != 6) {
            etPincode.setError(getString(R.string.invalid));
            valid = false;
        }
        return valid;
    }

    // on click listener
    public void onClick(View view) {
        String name = etName.getText().toString();
        String address1 = etAddressLine1.getText().toString();
        String address2 = etAddressLine2.getText().toString();
        String city = etCity.getText().toString();
        String state = etState.getText().toString();
        String zipCode = etPincode.getText().toString();

        if (!validateTextFields(name, address1, address2, city, zipCode)) {
            Log.d("output", "invalid");
            return;
        }

        if (ind != -1 && checkBox.isChecked())
            HomeActivity.defaultAddress = ind;
        else if (ind != -1 && HomeActivity.defaultAddress == ind && !checkBox.isChecked())
            HomeActivity.defaultAddress = 0;

        // update mode
        if (mode != null && mode.equals("Update")) {
            System.out.println(zipCode);
            address.setFirstname(name);
            address.setAddress1(address1);
            address.setAddress2(address2);
            address.setCity(city);
            address.setStateName(state);
            address.setZipcode(zipCode);

            ProgressDialog progressDialog = new ProgressDialog(this);
            Call<Address> call = retrofitAPI.updateAddress(address.getId(), RetrofitBuilder.token, address.getFirstname(), address.getAddress1(), address.getAddress2(), address.getCity(), address.getStateName(), address.getZipcode(), address.getStateId(), address.getCountryId(), address.getPhone());

            progressDialog.setTitle("Please Wait!!");
            progressDialog.setMessage("Wait!!");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            call.enqueue(new Callback<Address>() {
                @Override
                public void onResponse(@NonNull Call<Address> call, @NonNull Response<Address> response) {
                    progressDialog.dismiss();
                    NavUtils.navigateUpFromSameTask(AddressEntryActivity.this);
                }

                @Override
                public void onFailure(@NonNull Call<Address> call, @NonNull Throwable t) {
                    progressDialog.dismiss();
                }
            });

        }
        // create mode
        else {
            Address add = new Address(name, address1, address2, city, state, zipCode, 1400, 105, "1012121212");
            ProgressDialog progressDialog = new ProgressDialog(this);
            Call<Address> call = retrofitAPI.createAddress(RetrofitBuilder.token, add.getFirstname(), add.getAddress1(), add.getAddress2(), add.getCity(), add.getStateName(), add.getZipcode(), add.getStateId(), add.getCountryId(), add.getPhone());

            progressDialog.setTitle("Please Wait!!");
            progressDialog.setMessage("Wait!!");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            call.enqueue(new Callback<Address>() {
                @Override
                public void onResponse(@NonNull Call<Address> call, @NonNull Response<Address> response) {
                    Log.d("output", response + "res");
                    HomeActivity.addressList.add(add);
                    startActivity(new Intent(AddressEntryActivity.this, HomeActivity.class));
                }

                @Override
                public void onFailure(@NonNull Call<Address> call, @NonNull Throwable t) {
                    progressDialog.dismiss();
                    startActivity(new Intent(AddressEntryActivity.this, HomeActivity.class));
                }
            });
        }

    }

}

