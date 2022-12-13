package com.example.webthingclient.thingclient;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;

public interface FanApiService {
    @GET("/api/control-1/fan-1/properties/on")
    Call<ThingOnState> getState(@Header("Authorization") String bearerToken);

    @PUT("/api/control-1/fan-1/properties/on")
    Call<ThingOnState> updateState(@Header("Authorization") String bearerToken, @Body ThingOnState state);
}
