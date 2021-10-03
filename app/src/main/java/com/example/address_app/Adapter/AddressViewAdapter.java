package com.example.address_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.address_app.Address;
import com.example.address_app.AddressView;
import com.example.address_app.First_Activity;
import com.example.address_app.HomeActivity;
import com.example.address_app.MainActivity;
import com.example.address_app.R;
import com.example.address_app.Retro;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;
import java.util.jar.JarOutputStream;

public class AddressViewAdapter extends ArrayAdapter<AddressView> {
    public AddressViewAdapter(@NonNull Context context, List<AddressView> addressList) {
        super(context, 0, addressList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;

        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_listview, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        AddressView currentNumberPosition = getItem(position);

        ImageView defaultImage = currentItemView.findViewById(R.id.default_status);
        defaultImage.setImageResource(currentNumberPosition.getDefaultId());

        TextView textView = currentItemView.findViewById(R.id.label1);
        textView.setText(currentNumberPosition.getAddress().toString());


        if(position == 0)
            defaultImage.setVisibility(View.VISIBLE);


        Spinner spinnerView = currentItemView.findViewById(R.id.action_bar_spinner);
        spinnerView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {
                    Intent intent = new Intent(getContext(), First_Activity.class);
                    intent.putExtra("Mode", "Update");
                    intent.putExtra("Id", currentNumberPosition.getAddress().getId());
                    getContext().startActivity(intent);
                } else if (i == 2) {
                    Retro.deleteData(currentNumberPosition.getAddress());
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    getContext().startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        return currentItemView;
    }

}
