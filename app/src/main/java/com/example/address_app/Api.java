package com.example.address_app;

//import static com.example.address_app.Retro.POST_URL;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    String BASE_URL = "https://shop-spree.herokuapp.com/api/ams/user/";

    @GET("addresses")
    Call<List<Address>> getAddress(@Query("token") String token);

    @POST("addresses")
    Call<Address> createAddress(@Query("token") String token,
                                @Query("address[firstname]") String firstname,
                                @Query("address[address1]") String address1,
                                @Query("address[address2]") String address2,
                                @Query("address[city]") String city,
                                @Query("address[state_name]") String state_name,
                                @Query("address[zipcode]") String pinCode,
                                @Query("address[state_id]") int state_id,
                                @Query("address[country_id]") int country_id,
                                @Query("address[phone]") String phone);


    @FormUrlEncoded
    @PUT("addresses/{id}")
    Call<Address> updateAddress(@Path("id") int id,
                                @Query("token") String token,
                                @Field("address[firstname]") String firstname,
                                @Field("address[address1]") String address1,
                                @Field("address[address1]") String address2,
                                @Field("address[city]") String city,
                                @Field("address[state_name]") String state_name,
                                @Field("address[zipcode]") String pinCode,
                                @Field("address[state_id]") int state_id,
                                @Field("address[country_id]") int country_id,
                                @Field("address[phone]") String phone);

    @DELETE("addresses/{id}")
    Call<ResponseBody> deleteAddress(@Path("id") int id,
                                     @Query("token") String token);
}
