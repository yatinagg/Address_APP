package com.example.address_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.address_app.Adapter.AddressViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {


    public static int lenAddress;
    public static List<Address> addressList = null;
    private FloatingActionButton button1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (lenAddress != 0) {
            setContentView(R.layout.activity_second);
            ArrayList<String> arrayList = new ArrayList<>();
            for (Address address : addressList) {
                arrayList.add(address.toString());
            }
            ActionBar actionBar = getSupportActionBar();
            // providing title for the ActionBar
            actionBar.setTitle("Addresses");

            TextView textView = (TextView) findViewById(R.id.label1);
            List<AddressView> addressViews = new ArrayList<>();

            for (int i = 0; i < addressList.size(); i++) {
                AddressView ad = new AddressView(R.drawable.three_dot_options, addressList.get(i), R.drawable.default_icon);
                addressViews.add(ad);
            }
            AddressViewAdapter AddressArrayAdapter = new AddressViewAdapter(this, addressViews);

            // create the instance of the ListView to set the numbersViewAdapter
            ListView addressListView = findViewById(R.id.listView);
            addressListView.setAdapter(AddressArrayAdapter);

        } else {
            setContentView(R.layout.activity_main);
            button1 = (FloatingActionButton) findViewById(R.id.floatingActionButton);
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), First_Activity.class);
                    startActivity(intent);
                }
            });
        }

        ActionBar actionBar = getSupportActionBar();
        // providing title for the ActionBar
        actionBar.setTitle("Addresses");

    }
}
