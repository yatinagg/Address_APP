package com.example.address_app;

public class AddressView {

    private final Address address;
    private final int defaultId;

    public AddressView(Address address, int defaultId) {
        this.address = address;
        this.defaultId = defaultId;
    }

    public Address getAddress() {
        return address;
    }

    public int getDefaultId() {
        return defaultId;
    }
}
