package com.example.address_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {


    public static int lenAddress;
    public static List<Address> addressList;
    private FloatingActionButton button1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println(lenAddress+"hello");
        if(lenAddress != 0){
            setContentView(R.layout.activity_second);
            ArrayList<String> arrayList = new ArrayList<>();
            for(Address address : addressList){
                arrayList.add(address.toString());
            }
            ActionBar actionBar = getSupportActionBar();
            // providing title for the ActionBar
            actionBar.setTitle("Addresses");

            ListView listView = (ListView) findViewById(R.id.listView);
            TextView textView = (TextView) findViewById(R.id.label1);
            ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.activity_listview,R.id.label1, arrayList);
            listView.setAdapter(adapter);
            System.out.println("listview");
        }
        else {
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
        //if (android.os.Build.VERSION.SDK_INT > 9) { StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy); }

        ActionBar actionBar = getSupportActionBar();
        // providing title for the ActionBar
        actionBar.setTitle("Addresses");



    }
}
