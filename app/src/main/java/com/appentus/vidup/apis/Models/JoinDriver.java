package com.appentus.vidup.apis.Models;

import java.util.List;

public class JoinDriver{
    private String message;

    private List<Result> result;

    private String status;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public List<Result> getResult ()
    {
        return result;
    }

    public void setResult (List<Result> result)
    {
        this.result = result;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public class Result
    {
        private String status;

        private String user_type;

        private String device_type;

        private String image;

        private String device_id;

        private String social_id;

        private String id;

        private String nationality;

        private String email;

        private String device_token;

        private String dob;

        private String name;

        private String resident_id;

        private String gender;

        private String created_at;

        private String user_id;

        private String driver_id;

        private String longitude;

        private String latitude;

        private String otp;

        private String account_balance;

        private String mobile;

        public String getStatus ()
        {
            return status;
        }

        public void setStatus (String status)
        {
            this.status = status;
        }

        public String getUser_type ()
        {
            return user_type;
        }

        public void setUser_type (String user_type)
        {
            this.user_type = user_type;
        }

        public String getDevice_type ()
        {
            return device_type;
        }

        public void setDevice_type (String device_type)
        {
            this.device_type = device_type;
        }

        public String getImage ()
        {
            return image;
        }

        public void setImage (String image)
        {
            this.image = image;
        }

        public String getDevice_id ()
        {
            return device_id;
        }

        public void setDevice_id (String device_id)
        {
            this.device_id = device_id;
        }

        public String getSocial_id ()
        {
            return social_id;
        }

        public void setSocial_id (String social_id)
        {
            this.social_id = social_id;
        }

        public String getId ()
        {
            return id;
        }

        public void setId (String id)
        {
            this.id = id;
        }

        public String getNationality ()
        {
            return nationality;
        }

        public void setNationality (String nationality)
        {
            this.nationality = nationality;
        }

        public String getEmail ()
        {
            return email;
        }

        public void setEmail (String email)
        {
            this.email = email;
        }

        public String getDevice_token ()
        {
            return device_token;
        }

        public void setDevice_token (String device_token)
        {
            this.device_token = device_token;
        }

        public String getDob ()
        {
            return dob;
        }

        public void setDob (String dob)
        {
            this.dob = dob;
        }

        public String getName ()
        {
            return name;
        }

        public void setName (String name)
        {
            this.name = name;
        }

        public String getResident_id ()
        {
            return resident_id;
        }

        public void setResident_id (String resident_id)
        {
            this.resident_id = resident_id;
        }

        public String getGender ()
        {
            return gender;
        }

        public void setGender (String gender)
        {
            this.gender = gender;
        }

        public String getCreated_at ()
        {
            return created_at;
        }

        public void setCreated_at (String created_at)
        {
            this.created_at = created_at;
        }

        public String getUser_id ()
        {
            return user_id;
        }

        public void setUser_id (String user_id)
        {
            this.user_id = user_id;
        }

        public String getDriver_id ()
        {
            return driver_id;
        }

        public void setDriver_id (String driver_id)
        {
            this.driver_id = driver_id;
        }

        public String getLongitude ()
        {
            return longitude;
        }

        public void setLongitude (String longitude)
        {
            this.longitude = longitude;
        }

        public String getLatitude ()
        {
            return latitude;
        }

        public void setLatitude (String latitude)
        {
            this.latitude = latitude;
        }

        public String getOtp ()
        {
            return otp;
        }

        public void setOtp (String otp)
        {
            this.otp = otp;
        }

        public String getAccount_balance ()
        {
            return account_balance;
        }

        public void setAccount_balance (String account_balance)
        {
            this.account_balance = account_balance;
        }

        public String getMobile ()
        {
            return mobile;
        }

        public void setMobile (String mobile)
        {
            this.mobile = mobile;
        }
    }
}