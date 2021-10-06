package com.example.address_app;

import com.example.address_app.Adapter.AddressViewAdapter;

public class AddressView {

    private Address address;
    private int optionId;
    private int defaultId;

    public AddressView(int optionId, Address address, int defaultId) {

        this.address = address;
        this.optionId = optionId;
        this.defaultId = defaultId;
    }


    public Address getAddress() {
        return address;
    }

    public int getDefaultId() {
        return defaultId;
    }
}
