package com.example.simon.smokesignals;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import com.example.simon.models.User;

public class MainActivity extends AppCompatActivity {

    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    }

    public void logIn(View v)
    {
        EditText et_userName = findViewById(R.id.username);
        EditText et_password = findViewById(R.id.username);
        String username = et_userName.getText().toString();
        String password = et_password.getText().toString();
        if(username.isEmpty())
        {

        }else if (password.isEmpty())
        {

        }else
        {
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_CALENDAR)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
            }
            Location location  = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
            User user = new User(username, password, location.getLatitude());
        }
    }
}
