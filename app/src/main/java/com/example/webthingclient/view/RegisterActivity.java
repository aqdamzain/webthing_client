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
import com.example.webthingclient.databinding.ActivityRegisterBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonRegister.setOnClickListener(new View.OnClickListener() {
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
                    startRegistration(new UserAuth(inputUsername, inputPassword));
                }

            }
        });
    }

    private void startRegistration( UserAuth userAuth){
        UserApiService apiService = UserApiClient.getClient().create(UserApiService.class);

        Call<JwtToken> call = apiService.register(userAuth);
        call.enqueue(new Callback<JwtToken>() {
            @Override
            public void onResponse(Call<JwtToken> call, Response<JwtToken> response) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                intent.putExtra("USER_TOKEN", response.body().getToken());
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<JwtToken> call, Throwable t) {

            }
        });
    }
}