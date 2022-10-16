package com.example.webthingclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.webthingclient.databinding.ActivityAddDeviceBinding;

public class AddDeviceActivity extends AppCompatActivity {

    ActivityAddDeviceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddDeviceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.tvAdd.setText("This is Add Activity");
    }
}