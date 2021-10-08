package com.example.address_app;


import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retro {

    private static final String token = "52e04d83e87e509f07982e6ac851e2d2c67d1d0eabc4fe78";

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Api.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private static final Api retrofitAPI = retrofit.create(Api.class);

    public static void getAddress() {

        Call<List<Address>> call = retrofitAPI.getAddress(token);
        call.enqueue(new Callback<List<Address>>() {
            @Override
            public void onResponse(@NonNull Call<List<Address>> call, @NonNull Response<List<Address>> response) {
                Log.d("output", String.valueOf(response.body()));
                if (response.isSuccessful()) {
                    HomeActivity.addressList = response.body();
                    if (HomeActivity.addressList != null)
                        HomeActivity.lenAddress = HomeActivity.addressList.size();
                    else
                        HomeActivity.lenAddress = 0;
                    HomeActivity.flag = true;
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Address>> call, @NonNull Throwable t) {
                // displaying an error message in toast
                Log.d("output", "failure");
                HomeActivity.flag = true;
            }
        });
    }

    public static void postData(Address address) {

        Call<Address> call = retrofitAPI.createAddress(token, address.getFirstname(), address.getAddress1(), address.getAddress2(), address.getCity(), address.getStateName(), address.getZipcode(), address.getStateId(), address.getCountryId(), address.getPhone());

        call.enqueue(new Callback<Address>() {
            @Override
            public void onResponse(@NonNull Call<Address> call, @NonNull Response<Address> response) {
                Log.d("output", "response" + response.body());
                if (response.isSuccessful()) {
                    HomeActivity.addressList.add(address);
                    System.out.println(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Address> call, @NonNull Throwable t) {
                Log.d("output", "Error found is : " + t.getMessage());
            }
        });
    }

    public static void putData(Address address) {

        Call<Address> call = retrofitAPI.updateAddress(address.getId(), token, address.getFirstname(), address.getAddress1(), address.getAddress2(), address.getCity(), address.getStateName(), address.getZipcode(), address.getStateId(), address.getCountryId(), address.getPhone());
        call.enqueue(new Callback<Address>() {
            @Override
            public void onResponse(@NonNull Call<Address> call, @NonNull Response<Address> response) {
                Log.d("output", "Data updated to API");
                Log.d("output", address.getStateName());
                getAddress();
                HomeActivity.flag = true;
            }

            @Override
            public void onFailure(@NonNull Call<Address> call, @NonNull Throwable t) {
                Log.d("output", "Error found is : " + t.getMessage());
                HomeActivity.flag = true;
            }
        });

    }

    public static void deleteData(Address address) {

        Call<ResponseBody> call = retrofitAPI.deleteAddress(address.getId(), token);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                Log.d("output", "Data removed from API");
                getAddress();
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.d("output", "Error found is : " + t.getMessage());
            }
        });

    }
}
