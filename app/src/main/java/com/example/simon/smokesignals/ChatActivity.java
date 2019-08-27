package com.example.simon.smokesignals;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simon.business.DataInterface;
import com.example.simon.models.Chat;
import com.example.simon.models.Message;
import com.example.simon.models.User;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatActivity extends AppCompatActivity {

    TextView tv_subject;
    TextView tv_userString;
    User user;
    Chat chat;
    LinearLayout li_messages;
    EditText et_msg;
    Button btn_msg;
    Button btn_actualize;
    NestedScrollView scr_msg;

    DataInterface api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        tv_subject = findViewById(R.id.tv_subject);
        tv_userString = findViewById((R.id.tv_userString));
        li_messages = findViewById(R.id.li_messages);
        et_msg = findViewById(R.id.et_msg);
        btn_msg = findViewById((R.id.btn_send));
        btn_actualize=findViewById((R.id.btn_actualize));
        scr_msg = findViewById(R.id.scr_msg);

        Intent intent = getIntent();
        String jsonChat = intent.getStringExtra("Chat");
        String jsonUser = intent.getStringExtra("User");
        Gson gson = new Gson();
        chat = gson.fromJson(jsonChat, Chat.class);
        user =  gson.fromJson(jsonUser, User.class);
        tv_subject.setText(chat.getSubject());
        tv_userString.setText(chat.userString(user, ChatActivity.this));

        Retrofit retrofit = new Retrofit.Builder().baseUrl(getString(R.string.api)).addConverterFactory(GsonConverterFactory.create()).build();
        api = retrofit.create(DataInterface.class);

        fillList(chat.getMessages());
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        actualize();
    }

    public void fillList(Set<Message> messages)
    {
        if(messages == null)
            return;

        li_messages.removeAllViews();
        for(Message m : messages)
        {
            View messageView = m.getUser().getUserId() == user.getUserId() ? new MessageLineRight(this, m) : new MessageLineLeft(this, m);
            li_messages.addView(messageView);
        }
        scr_msg.postDelayed(new Runnable() { @Override public void run() { scr_msg.fullScroll(View.FOCUS_DOWN); } }, 1000);
    }

    public void addMessage(View v)
    {
        String msgContent = et_msg.getText().toString();

        if(!msgContent.isEmpty())
        {
            btn_msg.setVisibility(View.INVISIBLE);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy '@' HH:mm");
            Date date = new Date();
            Message msg = new Message(msgContent, dateFormat.format(date), user);

            Call<Set<Message>> call = api.addMessage(chat.getChatId(), msg);
            call.enqueue(new Callback<Set<Message>>() {
                @Override
                public void onResponse(Call<Set<Message>> call, Response<Set<Message>> response) {
                    if(!response.isSuccessful())
                    {
                        Toast.makeText(ChatActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        fillList(response.body());
                        et_msg.setText("");
                    }
                    btn_msg.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFailure(Call<Set<Message>> call, Throwable t) {
                    Toast.makeText(ChatActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                    btn_msg.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    public void launchActualize(View v)
    {
        actualize();
    }

    private void actualize()
    {
        btn_actualize.setVisibility(View.INVISIBLE);
        Call<Set<Message>> call = api.getChatMessages(chat.getChatId());
        call.enqueue(new Callback<Set<Message>>() {
            @Override
            public void onResponse(Call<Set<Message>> call, Response<Set<Message>> response) {
                if(!response.isSuccessful())
                {
                    Toast.makeText(ChatActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                }
                else
                {
                    fillList(response.body());
                }
                btn_actualize.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<Set<Message>> call, Throwable t) {
                Toast.makeText(ChatActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                btn_actualize.setVisibility(View.VISIBLE);
            }
        });
    }
}
