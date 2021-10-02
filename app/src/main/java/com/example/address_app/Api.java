package com.example.address_app;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface Api {

    String BASE_URL = "https://shop-spree.herokuapp.com/api/ams/user/";

    @GET("addresses?token=52e04d83e87e509f07982e6ac851e2d2c67d1d0eabc4fe78")

    Call<List<Address>> getAddress();

    @POST("addresses?token=52e04d83e87e509f07982e6ac851e2d2c67d1d0eabc4fe78")
    Call<List<Address>> createAddress(@Body Address address);


    @PUT("addresses?token=52e04d83e87e509f07982e6ac851e2d2c67d1d0eabc4fe78")
        // on below line we are creating a method to put our data.
    Call<Address> updateData(@Body Address address);
}
