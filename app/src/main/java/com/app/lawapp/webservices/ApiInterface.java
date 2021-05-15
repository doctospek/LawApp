package com.app.lawapp.webservices;/* Created By Ashwini Saraf on 1/13/2021*/


import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("lawyerLogin.php")
    Call<JsonObject> lawyerLogin(@Query("mobile") String mobile, @Query("password") String password);

    @GET("userLogin.php")
    Call<JsonObject> userLogin(@Query("mobile") String mobile, @Query("password") String password);

    @GET("lawyerReg.php")
    Call<JsonObject> lawyerReg(@Query("name") String name,
                               @Query("email") String email,
                               @Query("mobile") String mobile,
                               @Query("lawyer_type") String lawyer_type,
                               @Query("password") String password);

    @GET("userReg.php")
    Call<JsonObject> userReg(@Query("name") String name,
                             @Query("email") String email,
                             @Query("mobile") String mobile,
                             @Query("password") String password);

    @GET("getLawyer.php")
    Call<JsonObject> getLawyer(@Query("type") String type);

    @GET("updateUserFirebaseToken.php")
    Call<JsonObject> updateFirebaseToken(@Query("user_id") String user_id,
                                         @Query("token") String token);

    @GET("updateLawyerFirebaseToken.php")
    Call<JsonObject> updateLawyerFirebaseToken(@Query("user_id") String user_id,
                                               @Query("token") String token);

    @GET("addNotification.php")
    Call<JsonObject> addNotification(@Query("lawyer_id") String lawyer_id,
                                     @Query("user_id") String user_id);

    @GET("updateNotification.php")
    Call<JsonObject> updateNotification(@Query("id") String id,
                                        @Query("user_id") String user_id,
                                        @Query("is_accepted") String is_accepted);

    @GET("getNotifications.php")
    Call<JsonObject> getNotifications(@Query("id") String id);

    @GET("insertChat.php")
    Call<JsonObject> insertChat(@Query("msg") String msg,
                                @Query("from_id") String from_id,
                                @Query("to_id") String to_id,
                                @Query("from") String from
    );

    @GET("getChat.php")
    Call<JsonObject> getChat(@Query("from_id") String from_id, @Query("to_id") String to_id);
}
