package com.example.simon.smokesignals;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.simon.models.User;
import com.google.gson.Gson;

public class WelcomeActivity extends AppCompatActivity {

    TextView welcome;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        String jsonUser = getIntent().getStringExtra("User");
        user = new Gson().fromJson(jsonUser, User.class);

        welcome = findViewById(R.id.tv_welcome);
        welcome.append(user.getUserName());
    }

    public void signOut(View v)
    {
        finish();
    }
}
