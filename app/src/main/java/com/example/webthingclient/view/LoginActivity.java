package com.example.webthingclient.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.example.webthingclient.UserApiClient;
import com.example.webthingclient.UserApiService;
import com.example.webthingclient.UserAuth;
import com.example.webthingclient.JwtToken;
import com.example.webthingclient.databinding.ActivityLoginBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
            }
        });

        binding.buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });


        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputUsername = binding.tfUsername.getEditText().getText().toString();
                String inputPassword= binding.tfPassword.getEditText().getText().toString();
                boolean isEmptyFields = false;

                if(TextUtils.isEmpty(inputUsername)) {
                    isEmptyFields = true;
                    binding.tfUsername.setError("Invalid");
                }
                if(TextUtils.isEmpty(inputPassword)){
                    isEmptyFields = true;
                    binding.tfUsername.setError("Invalid");
                }

                if(!isEmptyFields){
                    startLogin(new UserAuth(inputUsername, inputPassword));
                }

            }
        });
    }

    private void startLogin(UserAuth userAuth) {
        UserApiService apiService = UserApiClient.getClient().create(UserApiService.class);

        Call<JwtToken> call = apiService.login(userAuth);
        call.enqueue(new Callback<JwtToken>() {
            @Override
            public void onResponse(Call<JwtToken> call, Response<JwtToken> response) {
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                intent.putExtra("USER_TOKEN", response.body().getToken());
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<JwtToken> call, Throwable t) {

            }
        });
    }
}