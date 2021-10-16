package com.example.address_app.Controllers;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.address_app.Adapter.AddressViewAdapter;
import com.example.address_app.Address;
import com.example.address_app.AddressView;
import com.example.address_app.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

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
