package com.example.webthingclient.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.webthingclient.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String userToken = getIntent().getStringExtra("USER_TOKEN");
        if(userToken!=null){
            binding.tvMain.setText(userToken);
        }
    }
}