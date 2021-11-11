package com.example.address_app;


import android.util.Log;
import android.widget.CheckBox;

import androidx.annotation.NonNull;

import com.example.address_app.Controllers.AddressDisplayActivity;
import com.example.address_app.Controllers.ApiCallStatusChecker;
import com.example.address_app.Pojos.Address;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {

    public static final String token = "52e04d83e87e509f07982e6ac851e2d2c67d1d0eabc4fe78";
    public static ApiCallStatusChecker apiCallStatusChecker;

    private static final retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
            .baseUrl(RetrofitApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public static final RetrofitApi retrofitAPI = retrofit.create(RetrofitApi.class);
    private static List<Address> addressList = new ArrayList<>();

    public static void getAddress(ApiCallStatusChecker apiCallStatusChecker) {
        RetrofitBuilder.apiCallStatusChecker = apiCallStatusChecker;
        Call<List<Address>> call = retrofitAPI.getAddress(RetrofitBuilder.token);
        call.enqueue(new Callback<List<Address>>() {
            @Override
            public void onResponse(@NonNull Call<List<Address>> call, @NonNull Response<List<Address>> response) {
                Log.d("output", String.valueOf(response.body()));
                if (response.isSuccessful()) {
                    addressList = response.body();
                }
                apiCallStatusChecker.apiCallComplete("Get", addressList);
            }

            @Override
            public void onFailure(@NonNull Call<List<Address>> call, @NonNull Throwable t) {
                Log.d("output", "failure");
            }
        });
    }

    public static void createData(Address add, CheckBox checkBox, ApiCallStatusChecker apiCallStatusChecker) {
        RetrofitBuilder.apiCallStatusChecker = apiCallStatusChecker;
        Call<Address> call = retrofitAPI.createAddress(RetrofitBuilder.token, add.getFirstname(), add.getAddress1(), add.getAddress2(), add.getCity(), add.getStateName(), add.getZipcode(), add.getStateId(), add.getCountryId(), add.getPhone());
        call.enqueue(new Callback<Address>() {
            @Override
            public void onResponse(@NonNull Call<Address> call, @NonNull Response<Address> response) {
                Log.d("output", response + "res");
                if (checkBox.isChecked() && response.body() != null)
                    AddressDisplayActivity.defaultAddress = response.body().getId();
                AddressDisplayActivity.getInstance().addressList.add(add);
                apiCallStatusChecker.apiCallComplete("Create", null);
            }

            @Override
            public void onFailure(@NonNull Call<Address> call, @NonNull Throwable t) {
            }
        });
    }

    public static void deleteData(Address address) {

        Call<ResponseBody> call = retrofitAPI.deleteAddress(address.getId(), token);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                Log.d("output", "Data removed from API");
                apiCallStatusChecker.apiCallComplete("Delete", null);
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.d("output", "Error found is : " + t.getMessage());
                apiCallStatusChecker.apiCallComplete("Delete", null);
            }
        });

    }

    public static void updateData(Address address, ApiCallStatusChecker apiCallStatusChecker) {
        RetrofitBuilder.apiCallStatusChecker = apiCallStatusChecker;
        Call<Address> call = retrofitAPI.updateAddress(address.getId(), RetrofitBuilder.token, address.getFirstname(), address.getAddress1(), address.getAddress2(), address.getCity(), address.getStateName(), address.getZipcode(), address.getStateId(), address.getCountryId(), address.getPhone());
        call.enqueue(new Callback<Address>() {
            @Override
            public void onResponse(@NonNull Call<Address> call, @NonNull Response<Address> response) {
                Log.d("output", "update api");
                apiCallStatusChecker.apiCallComplete("Update", null);
            }

            @Override
            public void onFailure(@NonNull Call<Address> call, @NonNull Throwable t) {
                apiCallStatusChecker.apiCallComplete("Update", null);
            }
        });
    }

}
