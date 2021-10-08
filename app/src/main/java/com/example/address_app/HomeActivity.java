package com.example.address_app;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    public static int lenAddress;
    public static List<Address> addressList = null;
    public static int defaultAddress = 0;
    public static boolean flag = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        // providing title for the ActionBar
        if (actionBar != null)
            actionBar.setTitle(R.string.addresses);

        // blank address page
        if (lenAddress != 0) {
            Intent intent = new Intent(this, AddressDisplayActivity.class);
            startActivity(intent);

        }
        // address list page
        else {
            setContentView(R.layout.blank_address_view);
            FloatingActionButton button1 = findViewById(R.id.floatingActionButton);
            button1.setOnClickListener(view -> {
                Intent intent = new Intent(view.getContext(), AddressEntryActivity.class);
                startActivity(intent);
            });
        }

    }
}
