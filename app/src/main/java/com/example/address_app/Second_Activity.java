package com.example.address_app;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class Second_Activity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String[] StringArray = {"Hello","World"};
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ActionBar actionBar = getSupportActionBar();
        // providing title for the ActionBar
        actionBar.setTitle("Addresses");

        listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.activity_second,R.id.label1, StringArray);
        listView.setAdapter(adapter);
        System.out.println("listview");
    }
}
