package com.example.address_app.Controllers;

import com.example.address_app.Address;

public interface OnButtonClickListener {
    void onButtonClick(int index, int position, Address address, boolean defaultAddress);
}