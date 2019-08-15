package com.example.simon.smokesignals;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import java.lang.String;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void logIn(View v)
    {
        EditText et_userName = findViewById(R.id.username);
        EditText et_password = findViewById(R.id.username);
        String username = et_userName.getText().toString();
        String password = et_password.getText().toString();
    }
}
