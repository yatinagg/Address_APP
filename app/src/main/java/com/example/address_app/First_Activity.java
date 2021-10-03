package com.example.address_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class First_Activity extends AppCompatActivity {


    private EditText editTextName;
    private EditText editTextAddressLine1;
    private EditText editTextAddressLine2;
    private EditText editTextCity;
    private EditText editTextState;
    private EditText editTextPincode;
    private Address address;
    private String mode = "Create";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        ActionBar actionBar = getSupportActionBar();
        // providing title for the ActionBar
        actionBar.setTitle("Add Address");
        Intent intent = getIntent();
        mode = intent.getStringExtra("Mode");
        int id = intent.getIntExtra("Id", 0);
        int ind = -1;
        for (int i = 0; i < HomeActivity.lenAddress; i++) {
            if (HomeActivity.addressList.get(i).getId() == id) {
                ind = i;
            }
        }
        if (ind == -1)
            address = new Address();
        else
            address = HomeActivity.addressList.get(ind);
        
        editTextName = findViewById(R.id.editTextersonName);
        editTextAddressLine1 = findViewById(R.id.editTextAddressLine1);
        editTextAddressLine2 = findViewById(R.id.editTextAddressLine2);
        editTextCity = findViewById(R.id.editTextCity);
        editTextState = findViewById(R.id.editTextState);
        editTextPincode = findViewById(R.id.editTextPincode);

        editTextName.setText(address.getFirstname());
        editTextAddressLine1.setText(address.getAddress1());
        editTextAddressLine2.setText(address.getAddress2());
        editTextCity.setText(address.getCity());
        editTextState.setText(address.getStateName());
        editTextPincode.setText(address.getZipcode());
    }


    public void onClick(View view) throws IOException {
        String name = editTextName.getText().toString();
        String address1 = editTextAddressLine1.getText().toString();
        String address2 = editTextAddressLine2.getText().toString();
        String city = editTextCity.getText().toString();
        String state = editTextState.getText().toString();
        String zipCode = editTextPincode.getText().toString();


        if (mode != null && mode.equals("Update")) {
            System.out.println(zipCode);
            address.setFirstname(name);
            address.setAddress1(address1);
            address.setAddress2(address2);
            address.setCity(city);
            address.setStateName(state);
            address.setZipcode(zipCode);
            Retro.putData(address);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Address add = new Address(name, address1, address2, city, state, zipCode, 1400, 105, "1012121212");
            Retro.postData(add);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

    }


}

