package com.example.address_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.address_app.AddressEntryActivity;
import com.example.address_app.AddressView;
import com.example.address_app.HomeActivity;
import com.example.address_app.R;
import com.example.address_app.Retro;

import java.util.List;

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

        ImageView imageView = currentItemView.findViewById(R.id.imageViewOptions);

        ImageView defaultImage = currentItemView.findViewById(R.id.default_status);
        defaultImage.setImageResource(currentNumberPosition.getDefaultId());

        TextView textView = currentItemView.findViewById(R.id.label1);
        textView.setText(currentNumberPosition.getAddress().toString());

        if (position == HomeActivity.defaultAddress)
            defaultImage.setVisibility(View.VISIBLE);

        // on click listener for update and delete options
        imageView.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(getContext(), view);
            popupMenu.inflate(R.menu.menu_item_options);
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                if (menuItem.getItemId() == R.id.menu_option_update) {
                    Intent intent = new Intent(getContext(), AddressEntryActivity.class);
                    intent.putExtra("Mode", "Update");
                    intent.putExtra("Id", currentNumberPosition.getAddress().getId());
                    getContext().startActivity(intent);
                    return true;
                } else if (menuItem.getItemId() == R.id.menu_option_delete) {
                    Handler handler = new Handler();
                    ProgressBar progressBar = new ProgressBar(getContext());
                    progressBar.setVisibility(View.VISIBLE);
                    new Thread(() -> {
                        float currProgress = 0;
                        HomeActivity.flag = false;
                        progressBar.setProgress(0);
                        Retro.deleteData(currentNumberPosition.getAddress());
                        Retro.getAddress();
                        while (!HomeActivity.flag) {
                            currProgress += 0.1;
                            progressBar.setProgress((int) currProgress);
                        }
                        handler.post(() -> {
                            progressBar.setProgress(100);
                            System.out.println("Done");
                            getContext().startActivity(new Intent(getContext(), HomeActivity.class));
                        });
                    }).start();
                    return true;
                }
                return false;
            });
            popupMenu.show();
        });

        return currentItemView;
    }

}
