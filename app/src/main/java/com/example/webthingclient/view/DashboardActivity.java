package com.example.webthingclient.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.webthingclient.JwtToken;
import com.example.webthingclient.R;
import com.example.webthingclient.ThingsList;
import com.example.webthingclient.UserApiClient;
import com.example.webthingclient.UserApiService;
import com.example.webthingclient.databinding.ActivityDashboardBinding;
import com.example.webthingclient.view.adapter.ListThingAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    ActivityDashboardBinding binding;

    private String userToken;

    private ArrayList<ThingsList> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userToken = getIntent().getStringExtra("USER_TOKEN");

        binding.rvThings.setHasFixedSize(true);

        list.addAll(getListThings());
        showRecyclerList();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu1) {
            startActivity(new Intent(DashboardActivity.this, AddDeviceActivity.class));
            return true;
        }  else {
            return true;
        }
    }

    public ArrayList<ThingsList> getListThings() {
        String[] dataName = getResources().getStringArray(R.array.data_name);
        String[] dataDescription = getResources().getStringArray(R.array.data_description);
        TypedArray dataPhoto = getResources().obtainTypedArray(R.array.data_photo);
        ArrayList<ThingsList> listThing = new ArrayList<>();
        for (int i = 0; i < dataName.length; i++) {
            ThingsList thing = new ThingsList();
            thing.setName(dataName[i]);
            thing.setDescription(dataDescription[i]);
            thing.setPhoto(dataPhoto.getResourceId(i, -1));
            listThing.add(thing);
        }
        return listThing;
    }

    private void showRecyclerList(){
        binding.rvThings.setLayoutManager(new LinearLayoutManager(this));
        ListThingAdapter listThingAdapter = new ListThingAdapter(list);
        binding.rvThings.setAdapter(listThingAdapter);

        listThingAdapter.setOnItemClickCallback(data -> showSelectedThing(data));
    }

    private void showSelectedThing(ThingsList thing) {
        if(thing.getName().equals("Weather Sensor")) {
            startActivity(new Intent(DashboardActivity.this, TempActivity.class));
        }else if(thing.getName().equals("DC Lamp")) {
            startControl(thing);
        } else if(thing.getName().equals("DC Fan")) {
            startControl(thing);
        }
    }

    private void startControl(ThingsList thing){
        UserApiService apiService = UserApiClient.getClient().create(UserApiService.class);
        Call<JwtToken> call = apiService.getControl("Bearer "+userToken);
        call.enqueue(new Callback<JwtToken>() {
            @Override
            public void onResponse(Call<JwtToken> call, Response<JwtToken> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null){

                        if(thing.getName().equals("DC Fan")) {
                            Intent intent = new Intent(DashboardActivity.this, FanActivity.class);
                            intent.putExtra("CONTROL_TOKEN", response.body().getToken());
                            startActivity(intent);
                        }else if(thing.getName().equals("DC Lamp")) {
                            Intent intent = new Intent(DashboardActivity.this, LampActivity.class);
                            intent.putExtra("CONTROL_TOKEN", response.body().getToken());
                            startActivity(intent);
                        }

                    }
                }

            }

            @Override
            public void onFailure(Call<JwtToken> call, Throwable t) {

            }
        });
    }
}