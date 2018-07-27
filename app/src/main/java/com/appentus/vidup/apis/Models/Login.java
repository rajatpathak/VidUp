
package com.appentus.vidup.apis.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {

    @SerializedName("AddressOne")
    @Expose
    private String addressOne;
    @SerializedName("AddressTwo")
    @Expose
    private String addressTwo;
    @SerializedName("AppKey")
    @Expose
    private String appKey;
    @SerializedName("BusinessName")
    @Expose
    private String businessName;
    @SerializedName("Category")
    @Expose
    private String category;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("Keywords")
    @Expose
    private String keywords;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Password")
    @Expose
    private String password;
    @SerializedName("PhoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("PostalCode")
    @Expose
    private String postalCode;
    @SerializedName("State")
    @Expose
    private String state;
    @SerializedName("Success")
    @Expose
    private Boolean success;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("WebsiteUrl")
    @Expose
    private String websiteUrl;
 @SerializedName("record_time")
    @Expose
    private String record_time;

    public String getRecord_time() {
        return record_time;
    }

    public void setRecord_time(String record_time) {
        this.record_time = record_time;
    }

    public String getAddressOne() {
        return addressOne;
    }

    public void setAddressOne(String addressOne) {
        this.addressOne = addressOne;
    }

    public String getAddressTwo() {
        return addressTwo;
    }

    public void setAddressTwo(String addressTwo) {
        this.addressTwo = addressTwo;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String Password) {
        this.password= Password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }


    public Login(String addressOne, String addressTwo, String appKey, String businessName, String category, String city, String email, String firstName, String keywords, String lastName, String message, String password, String phoneNumber, String postalCode, String state, Boolean success, Integer userId, String websiteUrl, String record_time) {
        this.addressOne = addressOne;
        this.addressTwo = addressTwo;
        this.appKey = appKey;
        this.businessName = businessName;
        this.category = category;
        this.city = city;
        this.email = email;
        this.firstName = firstName;
        this.keywords = keywords;
        this.lastName = lastName;
        this.message = message;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.postalCode = postalCode;
        this.state = state;
        this.success = success;
        this.userId = userId;
        this.websiteUrl = websiteUrl;
        this.record_time= record_time;
    }
}
