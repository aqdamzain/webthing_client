package com.example.webthingclient;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends ViewModel {

    public LiveData<UserToken> login(UserAuth userAuth) {
        MutableLiveData<UserToken> responseData = new MutableLiveData<>();

        UserApiService apiService = UserApiClient.getClient().create(UserApiService.class);

        Call<UserToken> call = apiService.login(userAuth);
        call.enqueue(new Callback<UserToken>() {
            @Override
            public void onResponse(Call<UserToken> call, Response<UserToken> response) {
                responseData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<UserToken> call, Throwable t) {

            }
        });

        return responseData;
    }

}
