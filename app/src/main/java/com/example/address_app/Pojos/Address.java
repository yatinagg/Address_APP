package com.example.address_app.Pojos;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("address1")
    @Expose
    private String address1;
    @SerializedName("address2")
    @Expose
    private String address2;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("zipcode")
    @Expose
    private String zipcode;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("state_name")
    @Expose
    private String stateName;
    @SerializedName("state_id")
    @Expose
    private int stateId;
    @SerializedName("country_id")
    @Expose
    private int countryId;

    public Address() {
    }

    public Address(String firstname, String address1, String address2, String city, String stateName, String zipcode, int stateId, int countryId, String phone) {
        this.firstname = firstname;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.stateName = stateName;
        this.zipcode = zipcode;
        this.stateId = stateId;
        this.countryId = countryId;
        this.phone = phone;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(address1);
        result.append(address2);
        result.append(", ");
        result.append(city);
        result.append(", ");
        if (stateName != null) {
            result.append(stateName);
            result.append(", ");
        }
        result.append(zipcode);
        return result.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getPhone() {
        return phone;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public int getStateId() {
        return stateId;
    }

    public int getCountryId() {
        return countryId;
    }

}