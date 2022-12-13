package com.example.webthingclient;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserApiService {
    @POST("/api/users/register")
    Call<JwtToken> register(@Body UserAuth userAuth);

    @POST("/api/users/login")
    Call<JwtToken> login(@Body UserAuth userAuth);

    @GET("/api/things")
    Call<JwtToken> getControl(@Header("Authorization") String bearerToken);
}
