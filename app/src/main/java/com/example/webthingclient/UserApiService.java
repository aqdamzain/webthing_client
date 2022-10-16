package com.example.webthingclient;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApiService {
    @POST("/api/users/register")
    Call<UserToken> register(@Body UserAuth userAuth);

    @POST("/api/users/login")
    Call<UserToken> login(@Body UserAuth userAuth);
}
