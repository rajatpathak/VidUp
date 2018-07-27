package com.appentus.vidup.apis.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginBean {
        private String Message;
    @SerializedName("Password")
        private String Password;

        private String BusinessName;

        private String PhoneNumber;

        private String UserId;

        private String WebsiteUrl;

        private String Success;

        private String AddressOne;

        private String LastName;

        private String AddressTwo;

        private String City;

        private String Category;

        private String PostalCode;

        private String State;
    @SerializedName("Email")
        private String Email;

        private String FirstName;

//        private String AppKey;

        private String Keywords;

        public String getMessage ()
        {
            return Message;
        }

        public void setMessage (String Message)
        {
            this.Message = Message;
        }

        public String getPassword ()
        {
            return Password;
        }

        public void setPassword (String Password)
        {
            this.Password = Password;
        }

        public String getBusinessName ()
        {
            return BusinessName;
        }

        public void setBusinessName (String BusinessName)
        {
            this.BusinessName = BusinessName;
        }

        public String getPhoneNumber ()
        {
            return PhoneNumber;
        }

        public void setPhoneNumber (String PhoneNumber)
        {
            this.PhoneNumber = PhoneNumber;
        }

        public String getUserId ()
        {
            return UserId;
        }

        public void setUserId (String UserId)
        {
            this.UserId = UserId;
        }

        public String getWebsiteUrl ()
        {
            return WebsiteUrl;
        }

        public void setWebsiteUrl (String WebsiteUrl)
        {
            this.WebsiteUrl = WebsiteUrl;
        }

        public String getSuccess ()
        {
            return Success;
        }

        public void setSuccess (String Success)
        {
            this.Success = Success;
        }

        public String getAddressOne ()
        {
            return AddressOne;
        }

        public void setAddressOne (String AddressOne)
        {
            this.AddressOne = AddressOne;
        }

        public String getLastName ()
        {
            return LastName;
        }

        public void setLastName (String LastName)
        {
            this.LastName = LastName;
        }

        public String getAddressTwo ()
        {
            return AddressTwo;
        }

        public void setAddressTwo (String AddressTwo)
        {
            this.AddressTwo = AddressTwo;
        }

        public String getCity ()
        {
            return City;
        }

        public void setCity (String City)
        {
            this.City = City;
        }

        public String getCategory ()
        {
            return Category;
        }

        public void setCategory (String Category)
        {
            this.Category = Category;
        }

        public String getPostalCode ()
        {
            return PostalCode;
        }

        public void setPostalCode (String PostalCode)
        {
            this.PostalCode = PostalCode;
        }

        public String getState ()
        {
            return State;
        }

        public void setState (String State)
        {
            this.State = State;
        }

        public String getEmail ()
        {
            return Email;
        }

        public void setEmail (String Email)
        {
            this.Email = Email;
        }

        public String getFirstName ()
        {
            return FirstName;
        }

        public void setFirstName (String FirstName)
        {
            this.FirstName = FirstName;
        }

//        public String getAppKey ()
//        {
//            return AppKey;
//        }
//
//        public void setAppKey (String AppKey)
//        {
//            this.AppKey = AppKey;
//        }

        public String getKeywords ()
        {
            return Keywords;
        }

        public void setKeywords (String Keywords)
        {
            this.Keywords = Keywords;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [Message = "+Message+", Password = "+Password+", BusinessName = "+BusinessName+", PhoneNumber = "+PhoneNumber+", UserId = "+UserId+", WebsiteUrl = "+WebsiteUrl+", Success = "+Success+", AddressOne = "+AddressOne+", LastName = "+LastName+", AddressTwo = "+AddressTwo+", City = "+City+", Category = "+Category+", PostalCode = "+PostalCode+", State = "+State+", Email = "+Email+", FirstName = "+FirstName+", Keywords = "+Keywords+"]";
        }
    }
