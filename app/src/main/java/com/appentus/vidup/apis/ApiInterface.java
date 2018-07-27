package com.appentus.vidup.apis;


import com.appentus.vidup.apis.Models.Login;
import com.appentus.vidup.apis.Models.LoginBean;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Warlock on 12/14/2017.
 */

public interface ApiInterface {



    @GET("ws/bc.svc/Getuser/{Email}/{Password}")
    Call<Login> getUser( @Path("Email") String Email,
                         @Path("Password") String Password);

    @FormUrlEncoded
    @Headers({
            "Content-type: application/json"
    })
    @POST("ws/bc.svc/uploadvideobase")
    Call<VideoFile> uploadvideo(
            @Field("FileExt") String FileExt,
            @Field("AppKey") String AppKey,
            @Field("File") String File);

    @GET("recordtime")
    Call<Login> recordingTime(
            @Query("key") String key,
            @Query("userid") String userid);






/**

    @FormUrlEncoded
    @POST("login")
    Call<LoginBean> loginApi(
            @Field("FirstName") String country_code,
            @Field("LastName") String social_id,
            @Field("Email") String mobile,
            @Field("Password") String latitude,
            @Field("SecretKey") String longitude

    );


    @FormUrlEncoded
    @POST("verify_otp")
    Call<LoginBean> verifyOTP(
            @Field("user_form_otp") String user_form_otp,
            @Field("user_id") String user_id
    );


    @FormUrlEncoded
    @POST("get_user")
    Call<LoginBean> getProfile(
            @Field("user_id") String user_id,
            @Field("user_type") String user_type
    );

    @Multipart
    @POST("update_user")
    Call<LoginBean> userUserWithPhoto(
            @Part MultipartBody.Part morephoto,
            @Part("user_id") RequestBody user_id,
            @Part("name") RequestBody name,
            @Part("email") RequestBody email,
            @Part("dob") RequestBody city,
            @Part("gender") RequestBody gender
    );

    @Multipart
    @POST("update_user")
    Call<LoginBean> userUserProfile(
            @Part("user_id") RequestBody user_id,
            @Part("name") RequestBody name,
            @Part("email") RequestBody email,
            @Part("dob") RequestBody city,
            @Part("gender") RequestBody gender
    );


    @Multipart
    @POST("place_order")
    Call<LoginBean> makeOrder(
            @Part MultipartBody.Part morephoto,
            @Part("lat") RequestBody lat,
            @Part("lang") RequestBody lang,
            @Part("user_id") RequestBody user_id,
            @Part("hotel_name") RequestBody hotel_name,
            @Part("hotel_lat") RequestBody hotel_lat,
            @Part("hotel_lang") RequestBody hotel_lang,
            @Part("hotel_address") RequestBody hotel_address,
            @Part("delivery_time") RequestBody delivery_time,
            @Part("food_details") RequestBody food_details,
            @Part("mini_cost") RequestBody mini_cost,
            @Part("delivery_loc") RequestBody delivery_loc,
            @Part("hotel_img") RequestBody hotel_img
    );

    @Multipart
    @POST("place_order")
    Call<LoginBean> makeOrder(
            @Part("lat") RequestBody lat,
            @Part("lang") RequestBody lang,
            @Part("user_id") RequestBody user_id,
            @Part("hotel_name") RequestBody hotel_name,
            @Part("hotel_lat") RequestBody hotel_lat,
            @Part("hotel_lang") RequestBody hotel_lang,
            @Part("hotel_address") RequestBody hotel_address,
            @Part("delivery_time") RequestBody delivery_time,
            @Part("food_details") RequestBody food_details,
            @Part("mini_cost") RequestBody mini_cost,
            @Part("delivery_loc") RequestBody delivery_loc,
            @Part("hotel_img") RequestBody hotel_img
    );

    @Multipart
    @POST("add_driver")
    Call<JoinDriver> joinDriver(
            @Part List<MultipartBody.Part> diver_license,
            @Part("user_id") RequestBody user_id,
            @Part("resident_id") RequestBody resident_id,
            @Part("nationality") RequestBody nationality
    );
**/

}


