package com.example.simon.smokesignals;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ChatActivity extends AppCompatActivity {

    TextView tv_chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        tv_chat = findViewById(R.id.tv_chat);
        Intent intent = getIntent();
        String chat = intent.getStringExtra("Chat");
        tv_chat.setText(chat);
    }
}
