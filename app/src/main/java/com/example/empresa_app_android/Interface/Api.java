package com.example.empresa_app_android.Interface;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @Headers({"Content-Type: application/json"})
    @POST("/api/{api_version}/users/auth/sign_in")
    Call<String> login_sign(@Path("api_version") String api, @Body String body);

    @GET("/api/{api_version}/enterprises")
    Call<String> getEnterprises(@Path("api_version") String api, @Query("name") String name);
}
