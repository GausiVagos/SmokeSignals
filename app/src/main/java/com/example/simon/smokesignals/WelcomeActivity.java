package com.example.simon.smokesignals;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.simon.business.DataInterface;
import com.example.simon.models.Chat;
import com.example.simon.models.User;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WelcomeActivity extends AppCompatActivity {

    TextView tv_welcome;
    User user;
    LinearLayout list_chats;
    DataInterface api;
    TextView tv_no_chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        String jsonUser = getIntent().getStringExtra("User");
        user = new Gson().fromJson(jsonUser, User.class);

        tv_welcome = findViewById(R.id.tv_welcome);
        tv_welcome.append(user.getUserName());

        tv_no_chat = findViewById(R.id.tv_no_chat);

        list_chats = findViewById(R.id.list_chats);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(getString(R.string.api)).addConverterFactory(GsonConverterFactory.create()).build();
        api = retrofit.create(DataInterface.class);

        fillList();
    }

    public void signOut(View v)
    {
        finish();
    }

    public void addChat(View v)
    {
        Intent intent = new Intent(this, NewChatActivity.class);
        intent.putExtra("User", new Gson().toJson(user));
        startActivity(intent);
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        fillList();
    }

    private void fillList()
    {
        Call<Chat[]> call = api.getChatsOfUser(user.getUserId());
        call.enqueue(new Callback<Chat[]>() {
            @Override
            public void onResponse(Call<Chat[]> call, Response<Chat[]> response) {
                if(!response.isSuccessful() || response.body().length == 0)
                {
                    tv_no_chat.setVisibility(View.VISIBLE);
                }
                else
                {
                    list_chats.removeAllViews();
                    final Chat[] chats = response.body();

                    for(int i = 0; i<chats.length;i++)
                    {
                        final Chat chat = chats[i];
                        ChatLine line = new ChatLine(WelcomeActivity.this, user,chats[i], new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(WelcomeActivity.this, ChatActivity.class);
                                intent.putExtra("Chat",chat.toString());
                                intent.putExtra("User", new Gson().toJson(user));
                                startActivity(intent);
                            }
                        });
                        list_chats.addView(line);
                    }
                }
            }

            @Override
            public void onFailure(Call<Chat[]> call, Throwable t) {
                tv_no_chat.setVisibility(View.VISIBLE);
            }
        });
    }
}
