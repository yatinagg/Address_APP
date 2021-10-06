package com.example.address_app;


import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.util.ScopeUtil;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retro {

    private static String token = "52e04d83e87e509f07982e6ac851e2d2c67d1d0eabc4fe78";

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Api.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private static Api retrofitAPI = retrofit.create(Api.class);

    protected static void getAddress() {

        Call<List<Address>> call = retrofitAPI.getAddress(token);
        call.enqueue(new Callback<List<Address>>() {
            @Override
            public void onResponse(Call<List<Address>> call, Response<List<Address>> response) {
                Log.d("output", String.valueOf(response.body()));
                if (response.isSuccessful()) {
                    HomeActivity.addressList = response.body();
                    HomeActivity.lenAddress = HomeActivity.addressList.size();
                }
            }

            @Override
            public void onFailure(Call<List<Address>> call, Throwable t) {
                // displaying an error message in toast
                Log.d("output", "failure");
            }
        });
    }

    public static void postData(Address address) {

        Call<Address> call = retrofitAPI.createAddress(token, address.getFirstname(), address.getAddress1(), address.getAddress2(), address.getCity(), address.getStateName(), address.getZipcode(), address.getStateId(), address.getCountryId(), address.getPhone());

        call.enqueue(new Callback<Address>() {
            @Override
            public void onResponse(Call<Address> call, Response<Address> response) {
                Log.d("output", "response" + response.body());
                if (response.isSuccessful()) {
                    HomeActivity.addressList.add(address);
                    System.out.println(response.body());
                }
            }

            @Override
            public void onFailure(Call<Address> call, Throwable t) {
                Log.d("output", "Error found is : " + t.getMessage());
            }
        });
    }

    public static void putData(Address address) {

        Call<Address> call = retrofitAPI.updateAddress(address.getId(), token, address.getFirstname(), address.getAddress1(), address.getAddress2(), address.getCity(), address.getStateName(), address.getZipcode(), address.getStateId(), address.getCountryId(), address.getPhone());

        call.enqueue(new Callback<Address>() {
            @Override
            public void onResponse(Call<Address> call, Response<Address> response) {
                Log.d("output", "Data updated to API");
                Address responseFromAPI = response.body();
                getAddress();
            }

            @Override
            public void onFailure(Call<Address> call, Throwable t) {
                Log.d("output", "Error found is : " + t.getMessage());
            }
        });
    }

    public static void deleteData(Address address) {

        Call<ResponseBody> call = retrofitAPI.deleteAddress(address.getId(), token);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("output", "Data removed from API");
                getAddress();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("output", "Error found is : " + t.getMessage());
            }
        });
    }
}
