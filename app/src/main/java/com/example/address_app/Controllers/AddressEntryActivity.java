package com.example.address_app.Controllers;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NavUtils;
import androidx.core.content.res.ResourcesCompat;

import com.example.address_app.Pojos.Address;
import com.example.address_app.R;
import com.example.address_app.RetrofitBuilder;

import java.util.List;

public class AddressEntryActivity extends AppCompatActivity implements ApiCallStatusChecker {


    private EditText etName;
    private EditText etAddressLine1;
    private EditText etAddressLine2;
    private EditText etCity;
    private EditText etState;
    private EditText etPincode;
    private EditText etLandmark;
    private TextView tvDefaultText;
    private ConstraintLayout progressEntry;
    public static Address address;
    private String mode = "Create";
    private int position;
    private int id;
    private CheckBox checkBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_entry);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        // providing title for the ActionBar
        if (actionBar != null)
            actionBar.setTitle("Add Address");

        setupView();
        setData();
    }

    private void setupView() {
        // get views
        etName = findViewById(R.id.et_person_name);
        etAddressLine1 = findViewById(R.id.et_address_line1);
        etAddressLine2 = findViewById(R.id.et_address_line2);
        etCity = findViewById(R.id.et_city);
        etState = findViewById(R.id.et_state);
        etPincode = findViewById(R.id.et_pincode);
        checkBox = findViewById(R.id.check_box);
        etLandmark = findViewById(R.id.et_landmark);
        tvDefaultText = findViewById(R.id.tv_default_text);
        progressEntry = findViewById(R.id.progress_entry);
    }

    private void setData() {
        Intent intent = getIntent();
        mode = intent.getStringExtra("Mode");
        position = intent.getIntExtra("Position", -1);
        id = intent.getIntExtra("Id", -1);

        if (position == -1)
            address = new Address();
        else
            address = AddressDisplayActivity.getInstance().addressList.get(position);

        if (intent.getBooleanExtra("Default", false)) {
            checkBox.setChecked(true);
        }
        Typeface face = ResourcesCompat.getFont(this, R.font.mulish_variable_font_wght);
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
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

        if (id != -1 && checkBox.isChecked())
            AddressDisplayActivity.defaultAddress = id;
        else if (id != -1 && AddressDisplayActivity.defaultAddress == id && !checkBox.isChecked())
            AddressDisplayActivity.defaultAddress = -1;

        // update mode
        if (mode != null && mode.equals("Update")) {
            System.out.println(zipCode);
            address = AddressDisplayActivity.getInstance().addressList.get(position);
            address.setFirstname(name);
            address.setAddress1(address1);
            address.setAddress2(address2);
            address.setCity(city);
            address.setStateName(state);
            address.setZipcode(zipCode);

            progressEntry.setVisibility(View.VISIBLE);
            RetrofitBuilder.updateData(address, this);
        }
        // create mode
        else {
            Address add = new Address(name, address1, address2, city, state, zipCode, 1400, 105, "1012121212");
            progressEntry.setVisibility(View.VISIBLE);
            RetrofitBuilder.createData(add, checkBox, this);
        }

    }

    @Override
    public void apiCallComplete(String mode, List<Address> addressList) {
        if (mode.equals("Update") || mode.equals("Create")) {
            NavUtils.navigateUpFromSameTask(AddressEntryActivity.this);
        }
    }
}

