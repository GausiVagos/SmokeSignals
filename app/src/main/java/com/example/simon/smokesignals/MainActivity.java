package com.example.simon.smokesignals;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simon.business.DataInterface;
import com.example.simon.models.User;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.MediaType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity implements LocationListener{
    EditText et_userName;
    EditText et_password;
    TextView tv_welcome;
    LocationManager locationManager;
    Location location;
    static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    DataInterface api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_userName = findViewById(R.id.username);
        et_password = findViewById(R.id.password);
        tv_welcome = findViewById(R.id.welcome);
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(MainActivity.this, R.string.missing_permission, Toast.LENGTH_LONG).show();
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(getString(R.string.api)).addConverterFactory(GsonConverterFactory.create()).build();
        api = retrofit.create(DataInterface.class);
    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(MainActivity.this, "Location changed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void logIn(View v)
    {

        String username = et_userName.getText().toString();
        String password = et_password.getText().toString();

        if(username.isEmpty())
        {
            Toast.makeText(MainActivity.this, R.string.no_username, Toast.LENGTH_LONG).show();
        }else if (password.isEmpty())
        {
            Toast.makeText(MainActivity.this, R.string.no_password, Toast.LENGTH_LONG).show();
        }else if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(MainActivity.this, R.string.missing_permission, Toast.LENGTH_LONG).show();
            return;
        }
        else
        {
            location  = getLastKnownLocation();
            if(location == null)
            {
                Toast.makeText(MainActivity.this, R.string.no_location, Toast.LENGTH_LONG).show();
            }else
            {
                User user = new User(username, password, location.getLatitude(), location.getLongitude());
                connect(user);
            }
        }
    }

    private Location getLastKnownLocation() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(MainActivity.this, R.string.missing_permission, Toast.LENGTH_LONG).show();
            return null;
        }
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = locationManager.getLastKnownLocation(provider);


            if (l == null) {
                continue;
            }
            if (bestLocation == null
                    || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        if (bestLocation == null) {
            return null;
        }
        return bestLocation;
    }

    private void connect(User toConnect)
    {
        Call<User> call = api.connect(toConnect);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful())
                {
                    tv_welcome.setText("Code : "+response.code());
                    return;
                }

                User connected = response.body();
                tv_welcome.setText("Connecté en tant que "+connected.getUserName()+"("+connected.getGender()+")");
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                tv_welcome.setText(t.getMessage());
            }
        });
    }
}
