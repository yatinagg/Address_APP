package com.example.address_app;


import android.app.ProgressDialog;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.address_app.Controllers.AddressDisplayActivity;
import com.example.address_app.Controllers.HomeActivity;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {

    public static final String token = "52e04d83e87e509f07982e6ac851e2d2c67d1d0eabc4fe78";

    private static final retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
            .baseUrl(RetrofitApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public static final RetrofitApi retrofitAPI = retrofit.create(RetrofitApi.class);

    public static void getAddress(ProgressDialog progressDialog) {

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
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Address>> call, @NonNull Throwable t) {
                // displaying an error message in toast
                Log.d("output", "failure");
                progressDialog.dismiss();
            }
        });
    }

    public static void deleteData(Address address, ProgressDialog progressDialog) {

        Call<ResponseBody> call = retrofitAPI.deleteAddress(address.getId(), token);

        progressDialogActions(progressDialog);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                Log.d("output", "Data removed from API");
                progressDialog.dismiss();
                AddressDisplayActivity.getInstance().notifyDataChange();
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.d("output", "Error found is : " + t.getMessage());
                progressDialog.dismiss();
                AddressDisplayActivity.getInstance().notifyDataChange();
            }
        });

    }

    private static void progressDialogActions(ProgressDialog progressDialog) {
        progressDialog.setTitle("Please Wait!!");
        progressDialog.setMessage("Wait!!");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }
}
