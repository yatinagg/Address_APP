package com.example.address_app.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.example.address_app.Controllers.AddressDisplayActivity;
import com.example.address_app.Controllers.AddressEntryActivity;
import com.example.address_app.Controllers.OnButtonClickListener;
import com.example.address_app.Pojos.Address;
import com.example.address_app.R;

import java.util.List;

public class AddressListAdapter extends ArrayAdapter<Address> {

    private final OnButtonClickListener onButtonClickListener;
    public static List<Address> addressList;

    public AddressListAdapter(@NonNull Context context, List<Address> addressList, OnButtonClickListener onButtonClickListener) {
        super(context, 0, addressList);
        this.onButtonClickListener = onButtonClickListener;
        AddressListAdapter.addressList = addressList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;

        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.listview, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        Address currentNumberPosition = getItem(position);

        ImageView imageView = currentItemView.findViewById(R.id.image_view_options);

        ImageView defaultImage = currentItemView.findViewById(R.id.default_status);

        TextView textView = currentItemView.findViewById(R.id.tv_address_display);
        textView.setText(currentNumberPosition.toString());
        Typeface face = ResourcesCompat.getFont(getContext(), R.font.mulish_variable_font_wght);
        textView.setTypeface(face);

        if (currentNumberPosition.getId() == AddressDisplayActivity.defaultAddress)
            defaultImage.setVisibility(View.VISIBLE);
        if (AddressDisplayActivity.defaultAddress == -1 && position == 0) {
            defaultImage.setVisibility(View.VISIBLE);
            AddressDisplayActivity.defaultAddress = addressList.get(0).getId();
        }

        // on click listener for update and delete options
        imageView.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(getContext(), view);
            popupMenu.inflate(R.menu.menu_item_options);
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                if (menuItem.getItemId() == R.id.menu_option_update) {
                    onButtonClickListener.onButtonClick(0, position, currentNumberPosition, addressList.get(position).getId() == AddressDisplayActivity.defaultAddress);
                    addressList.set(position, AddressEntryActivity.address);
                    return true;
                } else if (menuItem.getItemId() == R.id.menu_option_delete) {
                    onButtonClickListener.onButtonClick(1, position, currentNumberPosition, addressList.get(position).getId() == AddressDisplayActivity.defaultAddress);
                    addressList.remove(position);
                    return true;
                }
                return false;
            });
            popupMenu.show();
        });


        return currentItemView;
    }


}
