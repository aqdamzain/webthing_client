package com.example.webthingclient.view.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.webthingclient.UserApiClient;
import com.example.webthingclient.UserApiService;
import com.example.webthingclient.UserAuth;
import com.example.webthingclient.JwtToken;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends ViewModel {

    public LiveData<JwtToken> login(UserAuth userAuth) {
        MutableLiveData<JwtToken> responseData = new MutableLiveData<>();

        UserApiService apiService = UserApiClient.getClient().create(UserApiService.class);

        Call<JwtToken> call = apiService.login(userAuth);
        call.enqueue(new Callback<JwtToken>() {
            @Override
            public void onResponse(Call<JwtToken> call, Response<JwtToken> response) {
                responseData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<JwtToken> call, Throwable t) {

            }
        });

        return responseData;
    }

}
