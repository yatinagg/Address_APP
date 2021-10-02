package com.example.address_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class First_Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        ActionBar actionBar = getSupportActionBar();
        // providing title for the ActionBar
        actionBar.setTitle("Add Address");
    }


    public void onClick(View view) throws IOException {
        Toast.makeText(this, "check", Toast.LENGTH_SHORT).show();
        //postData("Yatin", "Gzb");
    }


  /*  private void postData(String name, String address1) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api retrofitAPI = retrofit.create(Api.class);
        Address address = new Address(name, address1);
        Call<Address> call = retrofitAPI.createAddress(address);

        call.enqueue(new Callback<Address>() {
            @Override
            public void onResponse(Call<Address> call, Response<Address> response) {
                Toast.makeText(getApplicationContext(), "Data added to API", Toast.LENGTH_SHORT).show();

                if(response.isSuccessful()){
                    Log.d("check", "Successful");
                }
                else{
                    Log.d("check", "UnSuccessful");
                }
                Address responseFromAPI = response.body();

                String responseString = "Response Code : " + response.code() + "\nName : " + responseFromAPI.getFirstname() + "\n" + "Address1 : " + responseFromAPI.getAddress1();

                Log.d("check", responseString);
            }

            @Override
            public void onFailure(Call<Address> call, Throwable t) {
                Log.d("check", "Error found is : " + t.getMessage());
            }
        });
    }
*/

}

