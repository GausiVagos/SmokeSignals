package com.example.simon.smokesignals;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simon.business.BestLocationFinder;
import com.example.simon.business.DataInterface;
import com.example.simon.models.User;

import okhttp3.MediaType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity{
    EditText et_userName;
    EditText et_password;
    TextView tv_welcome;
    Location location;
    static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    DataInterface api;
    Button btn_connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_userName = findViewById(R.id.username);
        et_password = findViewById(R.id.password);
        tv_welcome = findViewById(R.id.welcome);
        btn_connect = findViewById(R.id.btn_connect);

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(MainActivity.this, R.string.missing_permission, Toast.LENGTH_LONG).show();
            return;
        }
        Retrofit retrofit = new Retrofit.Builder().baseUrl(getString(R.string.api)).addConverterFactory(GsonConverterFactory.create()).build();
        api = retrofit.create(DataInterface.class);
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
            location  = new BestLocationFinder(MainActivity.this).getLastKnownLocation();
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

    private void connect(User toConnect)
    {
        btn_connect.setVisibility(View.INVISIBLE);
        Call<User> call = api.connect(toConnect);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful())
                {
                    String message = response.code()==400 ? getString(R.string.bad_identifiers) : getString(R.string.error);

                    tv_welcome.setText(message);
                    btn_connect.setVisibility(View.VISIBLE);
                    return;
                }

                User connected = response.body();
                String json = connected.toString();

                // Call the next activity
                et_userName.setText("");
                et_password.setText("");
                btn_connect.setVisibility(View.VISIBLE);
                Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                intent.putExtra("User", json);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                tv_welcome.setText(R.string.error);
            }
        });
    }

    public void signIn(View v)
    {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }
}
