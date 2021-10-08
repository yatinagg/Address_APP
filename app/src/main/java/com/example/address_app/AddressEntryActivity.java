package com.example.address_app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class AddressEntryActivity extends AppCompatActivity {


    private EditText editTextName;
    private EditText editTextAddressLine1;
    private EditText editTextAddressLine2;
    private EditText editTextCity;
    private EditText editTextState;
    private EditText editTextPincode;
    private Address address;
    private Handler handler;
    private String mode = "Create";
    private int ind;
    private CheckBox checkBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_entry);
        ActionBar actionBar = getSupportActionBar();
        // providing title for the ActionBar
        if (actionBar != null)
            actionBar.setTitle("Add Address");
        Intent intent = getIntent();
        handler = new Handler();
        mode = intent.getStringExtra("Mode");
        int id = intent.getIntExtra("Id", 0);
        ind = -1;
        for (int i = 0; i < HomeActivity.lenAddress; i++) {
            if (HomeActivity.addressList.get(i).getId() == id) {
                ind = i;
            }
        }
        if (ind == -1)
            address = new Address();
        else
            address = HomeActivity.addressList.get(ind);

        // get views
        editTextName = findViewById(R.id.editTextPersonName);
        editTextAddressLine1 = findViewById(R.id.editTextAddressLine1);
        editTextAddressLine2 = findViewById(R.id.editTextAddressLine2);
        editTextCity = findViewById(R.id.editTextCity);
        editTextState = findViewById(R.id.editTextState);
        editTextPincode = findViewById(R.id.editTextPincode);
        checkBox = findViewById(R.id.checkBox);

        // set text
        editTextName.setText(address.getFirstname());
        editTextAddressLine1.setText(address.getAddress1());
        editTextAddressLine2.setText(address.getAddress2());
        editTextCity.setText(address.getCity());
        editTextState.setText(address.getStateName());
        editTextPincode.setText(address.getZipcode());
    }

    // validate text fields
    private boolean validateTextFields(String name, String address1, String address2, String city, String zipCode) {
        boolean valid = true;
        if (name.isEmpty()) {
            editTextName.setError(getString(R.string.required_field));
            valid = false;
        }
        if (address1.isEmpty()) {
            editTextAddressLine1.setError(getString(R.string.required_field));
            valid = false;
        }
        if (address2.isEmpty()) {
            editTextAddressLine2.setError(getString(R.string.required_field));
            valid = false;
        }
        if (city.isEmpty()) {
            editTextCity.setError(getString(R.string.required_field));
            valid = false;
        }
        if (zipCode.isEmpty()) {
            editTextPincode.setError(getString(R.string.required_field));
            valid = false;
        } else if (zipCode.length() != 6) {
            editTextPincode.setError(getString(R.string.invalid));
            valid = false;
        }
        return valid;
    }


    // on click listener
    public void onClick(View view) {
        String name = editTextName.getText().toString();
        String address1 = editTextAddressLine1.getText().toString();
        String address2 = editTextAddressLine2.getText().toString();
        String city = editTextCity.getText().toString();
        String state = editTextState.getText().toString();
        String zipCode = editTextPincode.getText().toString();

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

            new Thread(() -> {
                int currProgress = 0;
                HomeActivity.flag = false;
                Retro.putData(address);
                Retro.getAddress();
                while (!HomeActivity.flag) {
                    currProgress += 0.1;
                }
                System.out.println(currProgress);
                handler.post(() -> {
                    System.out.println("Done");
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                });
            }).start();

        }
        // create mode
        else {
            Address add = new Address(name, address1, address2, city, state, zipCode, 1400, 105, "1012121212");
            new Thread(() -> {
                HomeActivity.flag = false;
                int currProgress = 0;
                Retro.postData(add);
                Retro.getAddress();
                while (!HomeActivity.flag) {
                    currProgress += 0.1;
                }
                System.out.println(currProgress);
                handler.post(() -> {
                    System.out.println("Done");
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                });
            }).start();
        }

    }

}

