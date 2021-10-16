package com.example.address_app.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
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

import com.example.address_app.AddressView;
import com.example.address_app.Controllers.AddressEntryActivity;
import com.example.address_app.Controllers.HomeActivity;
import com.example.address_app.Controllers.OnButtonClickListener;
import com.example.address_app.R;

import java.util.List;

public class AddressViewAdapter extends ArrayAdapter<AddressView> {

    private final OnButtonClickListener onButtonClickListener;
    public static List<AddressView> addressViews;

    public AddressViewAdapter(@NonNull Context context, List<AddressView> addressViews, OnButtonClickListener onButtonClickListener) {
        super(context, 0, addressViews);
        this.onButtonClickListener = onButtonClickListener;
        AddressViewAdapter.addressViews = addressViews;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;

        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.listview, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        AddressView currentNumberPosition = getItem(position);

        ImageView imageView = currentItemView.findViewById(R.id.image_view_options);

        ImageView defaultImage = currentItemView.findViewById(R.id.default_status);
        defaultImage.setImageResource(currentNumberPosition.getDefaultId());

        TextView textView = currentItemView.findViewById(R.id.tv_address_display);
        textView.setText(currentNumberPosition.getAddress().toString());
        Typeface face = ResourcesCompat.getFont(getContext(), R.font.mulish_variable_font_wght);
        textView.setTypeface(face);

        if (position == HomeActivity.defaultAddress)
            defaultImage.setVisibility(View.VISIBLE);

        // on click listener for update and delete options
        imageView.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(getContext(), view);
            popupMenu.inflate(R.menu.menu_item_options);
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                if (menuItem.getItemId() == R.id.menu_option_update) {
                    Log.d("output", "update");
                    onButtonClickListener.onButtonClick(0, position, currentNumberPosition.getAddress());
                    addressViews.set(position, new AddressView(AddressEntryActivity.address, R.drawable.default_icon));
                    return true;
                } else if (menuItem.getItemId() == R.id.menu_option_delete) {
                    addressViews.remove(position);
                    onButtonClickListener.onButtonClick(1, position, currentNumberPosition.getAddress());
                    return true;
                }
                return false;
            });
            popupMenu.show();
        });


        return currentItemView;
    }


}
