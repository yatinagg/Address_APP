package com.example.address_app;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private Object lastname;
    @SerializedName("address1")
    @Expose
    private String address1;
    @SerializedName("address2")
    @Expose
    private Object address2;
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
    private Object stateName;
    @SerializedName("alternative_phone")
    @Expose
    private Object alternativePhone;
    @SerializedName("company")
    @Expose
    private Object company;
    @SerializedName("state_id")
    @Expose
    private Integer stateId;
    @SerializedName("country_id")
    @Expose
    private Integer countryId;

    public Address(String firstname, String address1, String city, int countryId, int stateId, String zipcode, String phone) {
        this.firstname = firstname;
        this.address1 = address1;
        this.city = city;
        this.zipcode = zipcode;
        this.phone = phone;
        this.stateId = stateId;
        this.countryId = countryId;
    }

    @Override
    public String toString() {
        return "Address{" +
                "firstname='" + firstname + '\'' +
                ", address1='" + address1 + '\'' +
                ", city='" + city + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", phone='" + phone + '\'' +
                ", stateId=" + stateId +
                ", countryId=" + countryId +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Object getLastname() {
        return lastname;
    }

    public void setLastname(Object lastname) {
        this.lastname = lastname;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public Object getAddress2() {
        return address2;
    }

    public void setAddress2(Object address2) {
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

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Object getStateName() {
        return stateName;
    }

    public void setStateName(Object stateName) {
        this.stateName = stateName;
    }

    public Object getAlternativePhone() {
        return alternativePhone;
    }

    public void setAlternativePhone(Object alternativePhone) {
        this.alternativePhone = alternativePhone;
    }

    public Object getCompany() {
        return company;
    }

    public void setCompany(Object company) {
        this.company = company;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

}