package com.example.webthingclient.thingclient;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DhtApiClient {
    public static DhtApiService getClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        return new Retrofit.Builder()
                .baseUrl("http://192.168.1.201:80")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(DhtApiService.class);
    }
}
