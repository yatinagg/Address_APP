package com.example.address_app.Controllers;

import com.example.address_app.Pojos.Address;

import java.util.List;

public interface ApiCallStatusChecker {
    void apiCallComplete(String mode, List<Address> addressList);
}
