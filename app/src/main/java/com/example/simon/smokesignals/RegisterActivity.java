package com.example.simon.smokesignals;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.simon.business.BestLocationFinder;
import com.example.simon.business.DataInterface;
import com.example.simon.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    EditText et_username;
    EditText et_password;
    EditText et_conf_password;
    RadioGroup rg_gender;
    EditText et_city;
    DataInterface api;
    Button btn_signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_username = findViewById(R.id.username);
        et_password = findViewById(R.id.password);
        et_conf_password = findViewById(R.id.conf_password);
        rg_gender = findViewById(R.id.rg_gender);
        et_city = findViewById(R.id.city);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(getString(R.string.api)).addConverterFactory(GsonConverterFactory.create()).build();
        api = retrofit.create(DataInterface.class);
        btn_signIn = findViewById(R.id.btn_signIn);
    }

    public void selectCity(View v)
    {
        Intent intent = new Intent(this, CitiesActivity.class);
        startActivityForResult(intent, 42);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 42 && resultCode == RESULT_OK)
        {
            String city = data.getStringExtra("City");
            if(city != null)
            {
                et_city.setText(city);
            }
        }
    }

    public void register(View v)
    {
        String userName = et_username.getText().toString();
        String password = et_password.getText().toString();
        String conf_password = et_conf_password.getText().toString();
        String city = et_city.getText().toString();

        if(userName.isEmpty() || password.isEmpty() || conf_password.isEmpty() || city.isEmpty())
        {
            Toast.makeText(RegisterActivity.this, R.string.missing_fields, Toast.LENGTH_LONG).show();
        }
        else if(!password.equals(conf_password))
        {
            Toast.makeText(RegisterActivity.this, R.string.password_mismatch, Toast.LENGTH_LONG).show();
        }
        else
        {
            int radioIndex = rg_gender.getCheckedRadioButtonId();
            char gender = radioIndex == R.id.rd_gender_M ? 'M' : radioIndex == R.id.rd_gender_F ? 'F' : 'O';
            Location lastKnownLocation = new BestLocationFinder(RegisterActivity.this).getLastKnownLocation();
            if(lastKnownLocation == null)
            {
                Toast.makeText(RegisterActivity.this, R.string.no_location, Toast.LENGTH_LONG).show();
            }
            else
            {
                btn_signIn.setVisibility(View.GONE);
                User user = new User(userName,password,gender,city,lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude());
                register(user);
            }
        }
    }

    public void register(User user)
    {
        Call<User> call = api.post(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful())
                {
                    Toast.makeText(RegisterActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                    btn_signIn.setVisibility(View.VISIBLE);
                    return;
                }

                User created = response.body();
                String json = created.toString();

                // Call the next activity
                Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                intent.putExtra("User", json);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                btn_signIn.setVisibility(View.VISIBLE);
            }
        });
    }
}
