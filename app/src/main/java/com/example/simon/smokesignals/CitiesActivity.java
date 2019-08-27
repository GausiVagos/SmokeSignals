package com.example.simon.smokesignals;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simon.business.DataInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CitiesActivity extends AppCompatActivity {

    DataInterface api;
    LinearLayout cities_layout;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);
        cities_layout = findViewById(R.id.cities_layout);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(getString(R.string.api)).addConverterFactory(GsonConverterFactory.create()).build();
        api = retrofit.create(DataInterface.class);
        tv= findViewById(R.id.tv_no_city);
        getCities();
    }

    protected void getCities()
    {
        Call<String[]> call = api.getCities();
        call.enqueue(new Callback<String[]>() {
            @Override
            public void onResponse(Call<String[]> call, Response<String[]> response) {
                if(!response.isSuccessful() || response.body()==null || response.body().length == 0)
                {
                    tv.setVisibility(View.VISIBLE);
                }
                else
                {
                    String[] cities = response.body();
                    for(int i = 0; i<cities.length;i++)
                    {
                        Button btnCity = new Button(CitiesActivity.this);
                        btnCity.setText(cities[i]);
                        btnCity.setTag(cities[i]);
                        btnCity.setWidth(cities_layout.getWidth());
                        btnCity.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent cityChosen = new Intent();
                                String city = (String)v.getTag();
                                cityChosen.putExtra("City", city);
                                setResult(RESULT_OK, cityChosen);
                                finish();
                            }
                        });
                        cities_layout.addView(btnCity);
                    }
                }

            }

            @Override
            public void onFailure(Call<String[]> call, Throwable t) {
                tv.setVisibility(View.VISIBLE);
            }
        });
    }

    public void cancel(View v)
    {
        setResult(RESULT_CANCELED);
        finish();
    }
}
