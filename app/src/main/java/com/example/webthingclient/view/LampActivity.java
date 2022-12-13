package com.example.webthingclient.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.webthingclient.R;
import com.example.webthingclient.databinding.ActivityLampBinding;
import com.example.webthingclient.thingclient.ControlApiClient;
import com.example.webthingclient.thingclient.LampApiService;
import com.example.webthingclient.thingclient.ThingOnState;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LampActivity extends AppCompatActivity {
    private String controlToken;

    private static final String TAG = "LampActivity";
    private ActivityLampBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLampBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        controlToken = getIntent().getStringExtra("CONTROL_TOKEN");

        binding.materialSwitch.setChecked(false);
        readLampState();

        if(binding.materialSwitch.isChecked()){
            binding.tvState.setText("ON");
            binding.ivBulb.setImageResource(R.drawable.lightbulb_on);
        }else{
            binding.tvState.setText("OFF");
            binding.ivBulb.setImageResource(R.drawable.lightbulb_off);
        }

        // To check a switch
        binding.materialSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    binding.tvState.setText("ON");
                    binding.ivBulb.setImageResource(R.drawable.lightbulb_on);
                    updateLampState(new ThingOnState(true));
                }else{
                    binding.tvState.setText("OFF");
                    binding.ivBulb.setImageResource(R.drawable.lightbulb_off);
                    updateLampState(new ThingOnState(false));
                }
            }
        });
    }
    private void readLampState() {
        LampApiService apiService = ControlApiClient.getClient().create(LampApiService.class);
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

    private void updateLampState(ThingOnState state){
        LampApiService apiService = ControlApiClient.getClient().create(LampApiService.class);
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