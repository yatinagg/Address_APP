package com.example.address_app;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.util.ScopeUtil;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retro {
    protected static void getAddress() {

        System.out.println("hello");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api retrofitAPI = retrofit.create(Api.class);
        Call<List<Address>> call = retrofitAPI.getAddress();
        call.enqueue(new Callback<List<Address>>() {
            @Override
            public void onResponse(Call<List<Address>> call, Response<List<Address>> response) {
                Log.d("output", String.valueOf(response.body()));
                if (response.isSuccessful()) {
                    HomeActivity.addressList = response.body();
                    HomeActivity.lenAddress = HomeActivity.addressList.size();
                    System.out.println(HomeActivity.lenAddress);
                    System.out.println(HomeActivity.addressList.size());
                    int ind=0;
                    for(Address address : HomeActivity.addressList) {
                        Log.d("output", ind+address.getFirstname()+address.getAddress1());
                        ind++;
                    }
                }
                else{
                    Log.d("output", "no");
                    Log.d("output", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Address>> call, Throwable t) {
                // displaying an error message in toast
                Log.d("output", "failure");
            }
        });
    }

    protected static void postData() {

        // on below line we are creating a retrofit
        // builder and passing our base url
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // below line is to create an instance for our retrofit api class.
        Api retrofitAPI = retrofit.create(Api.class);

        // passing data from our text fields to our modal class.
        Address modal = new Address("Rahul","Ghaziabad","GZB",91, 55, "201010","9988776655");

        // calling a method to create a post and passing our modal class.
        Call<List<Address>> call = retrofitAPI.createAddress(modal);

        // on below line we are executing our method.
        call.enqueue(new Callback<List<Address>>() {
            @Override
            public void onResponse(Call<List<Address>> call, Response<List<Address>> response) {
                Log.d("output", "response"+ response.body());
                if (response.isSuccessful()) {
                    HomeActivity.addressList = response.body();
                    HomeActivity.lenAddress = HomeActivity.addressList.size();
                    System.out.println(HomeActivity.lenAddress);
                    System.out.println(HomeActivity.addressList.size());
                    int ind=0;
                    for(Address address : HomeActivity.addressList) {
                        Log.d("output", ind+address.getFirstname()+address.getAddress1());
                        ind++;
                    }
                }
                else{
                    Log.d("output", "no");
                    Log.d("output", String.valueOf(response.code()));
                    Log.d("output", String.valueOf(response.errorBody()));
                    System.out.println(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Address>> call, Throwable t) {
                Log.d("output","Error found is : "+t.getMessage());
            }
        });
    }

    protected static void callPUTDataMethod() {

        // on below line we are creating a retrofit
        // builder and passing our base url
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // below the line is to create an instance for our retrofit api class.
        Api retrofitAPI = retrofit.create(Api.class);

        // passing data from our text fields to our modal class.
        Address modal = new Address("Rahul","Ghaziabad","GZB",91, 55, "201010","9988776655");

        // calling a method to create an update and passing our modal class.
        Call<Address> call = retrofitAPI.updateData(modal);

        // on below line we are executing our method.
        call.enqueue(new Callback<Address>() {
            @Override
            public void onResponse(Call<Address> call, Response<Address> response) {
                // this method is called when we get response from our api.
                Log.d("output","Data updated to API");

                // we are getting a response from our body and
                // passing it to our modal class.
                Address responseFromAPI = response.body();

                // on below line we are getting our data from modal class
                // and adding it to our string.
                System.out.println(responseFromAPI+" why");
                //String responseString = "Response Code : " + response.code() + "\nName : " + responseFromAPI.getFirstname() + "\n" + "Address : " + responseFromAPI.getAddress1();
            }

            @Override
            public void onFailure(Call<Address> call, Throwable t) {

                // setting text to our text view when
                // we get error response from API.
                Log.d("output","Error found is : " + t.getMessage());
            }
        });
    }
}
