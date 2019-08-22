package com.example.simon.smokesignals;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.simon.models.Chat;
import com.google.gson.Gson;

public class ChatActivity extends AppCompatActivity {

    TextView tv_subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        tv_subject = findViewById(R.id.tv_subject);
        Intent intent = getIntent();
        String jsonChat = intent.getStringExtra("Chat");
        Chat chat = new Gson().fromJson(jsonChat, Chat.class);
        tv_subject.setText(chat.getSubject());
    }
}
