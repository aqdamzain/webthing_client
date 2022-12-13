package com.example.webthingclient.thingclient;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DhtApiService {
    @GET("/things/dht22/properties")
    Call<DhtProperty> getProperty();
}
