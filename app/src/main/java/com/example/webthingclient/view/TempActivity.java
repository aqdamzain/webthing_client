package com.example.webthingclient.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.webthingclient.databinding.ActivityTempBinding;
import com.example.webthingclient.thingclient.DhtApiClient;
import com.example.webthingclient.thingclient.DhtProperty;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TempActivity extends AppCompatActivity {

    private static final String TAG = "TempActivity";
    private ActivityTempBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTempBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        readProperty();
    }

    private void readProperty() {
        Call<DhtProperty> client = DhtApiClient.getClient().getProperty();
        client.enqueue(new Callback<DhtProperty>() {
            @Override
            public void onResponse(Call<DhtProperty> call, Response<DhtProperty> response) {
                if(response.isSuccessful()){
                    if (response.body() != null) {
                        binding.tempTv.setText(String.valueOf(response.body().getTemperature()));
                        binding.humTv.setText(String.valueOf(response.body().getHumidity()));
                    }
                } else{
                    if (response.body() != null) {
                        Log.e(TAG, "onFailure: " + response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<DhtProperty> call, Throwable t) {

            }
        });
    }
}