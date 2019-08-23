package com.example.simon.smokesignals;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simon.business.DataInterface;
import com.example.simon.models.Chat;
import com.example.simon.models.Message;
import com.example.simon.models.User;
import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatActivity extends AppCompatActivity {

    TextView tv_subject;
    TextView tv_userString;
    User user;
    Chat chat;
    LinearLayout li_messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        tv_subject = findViewById(R.id.tv_subject);
        tv_userString = findViewById((R.id.tv_userString));
        li_messages = findViewById(R.id.li_messages);

        Intent intent = getIntent();
        String jsonChat = intent.getStringExtra("Chat");
        String jsonUser = intent.getStringExtra("User");
        Gson gson = new Gson();
        chat = gson.fromJson(jsonChat, Chat.class);
        user =  gson.fromJson(jsonUser, User.class);
        tv_subject.setText(chat.getSubject());
        tv_userString.setText(chat.userString(user, ChatActivity.this));

        fillList();
    }

    public void fillList()
    {
        for(Message m : chat.getMessages())
        {
            View messageView = m.getUser().getUserId() == user.getUserId() ? new MessageLignRight(this, m) : new MessageLineLeft(this, m);

            li_messages.addView(messageView);
        }
    }
}
