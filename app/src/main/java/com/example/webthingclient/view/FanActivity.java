package com.example.webthingclient.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.webthingclient.R;
import com.example.webthingclient.databinding.ActivityFanBinding;
import com.example.webthingclient.thingclient.ControlApiClient;
import com.example.webthingclient.thingclient.FanApiService;
import com.example.webthingclient.thingclient.ThingOnState;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FanActivity extends AppCompatActivity {
    private String controlToken;

    private static final String TAG = "FanActivity";
    private ActivityFanBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        controlToken = getIntent().getStringExtra("CONTROL_TOKEN");

        Animation spinner = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);

        binding.materialSwitch.setChecked(false);
        readFanState();

        if(binding.materialSwitch.isChecked()){
            binding.tvState.setText("ON");
            binding.ivFan.startAnimation(spinner);
        }else{
            binding.tvState.setText("OFF");
            binding.ivFan.clearAnimation();
        }


        // To check a switch
        binding.materialSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    binding.tvState.setText("ON");
                    binding.ivFan.startAnimation(spinner);
                    updateFanState(new ThingOnState(true));
                }else{
                    binding.tvState.setText("OFF");
                    binding.ivFan.clearAnimation();
                    updateFanState(new ThingOnState(false));
                }
            }
        });
    }

    private void readFanState() {

        FanApiService apiService = ControlApiClient.getClient().create(FanApiService.class);
        Call<ThingOnState> client = apiService.getState("Bearer "+controlToken);
        client.enqueue(new Callback<ThingOnState>() {
            @Override
            public void onResponse(Call<ThingOnState> call, Response<ThingOnState> response) {
                if(response.isSuccessful()){
                    if(response.body().isOn()){
                        binding.materialSwitch.setChecked(true);
                    } else{
                        binding.materialSwitch.setChecked(false);
                    }
                } else {
                    if (response.body() != null) {
                        Log.e(TAG, "onFailure: " + response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<ThingOnState> call, Throwable t) {

            }
        });

    }

    private void updateFanState(ThingOnState state){
        FanApiService apiService = ControlApiClient.getClient().create(FanApiService.class);
        Call<ThingOnState> client = apiService.updateState("Bearer "+controlToken, state);
        client.enqueue(new Callback<ThingOnState>() {
            @Override
            public void onResponse(Call<ThingOnState> call, Response<ThingOnState> response) {
                if(response.isSuccessful()){
                    Toast.makeText(binding.getRoot().getContext(), "Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(binding.getRoot().getContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ThingOnState> call, Throwable t) {

            }
        });
    }
}