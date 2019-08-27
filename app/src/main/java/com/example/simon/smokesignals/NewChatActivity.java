package com.example.simon.smokesignals;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simon.business.DataInterface;
import com.example.simon.models.Chat;
import com.example.simon.models.User;
import com.google.gson.Gson;

import java.util.HashSet;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewChatActivity extends AppCompatActivity {

    EditText et_subject;
    LinearLayout li_users;
    Button btn_addChat;
    TextView tv_no_user;
    User connected;
    DataInterface api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chat);

        Intent intent = getIntent();
        String userJson = intent.getStringExtra("User");
        connected = new Gson().fromJson(userJson, User.class);

        et_subject = findViewById(R.id.et_subject);
        li_users = findViewById(R.id.li_users);
        btn_addChat = findViewById(R.id.btn_addChat);
        tv_no_user = findViewById(R.id.tv_no_user);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(getString(R.string.api)).addConverterFactory(GsonConverterFactory.create()).build();
        api = retrofit.create(DataInterface.class);

        fillList();
    }

    public void fillList()
    {
        Call<Set<User>> call = api.getUsers();
        call.enqueue(new Callback<Set<User>>() {
            @Override
            public void onResponse(Call<Set<User>> call, Response<Set<User>> response) {
                if(!response.isSuccessful() || response.body()==null|| response.body().isEmpty())
                {
                    tv_no_user.setVisibility(View.VISIBLE);
                    return;
                }

                for(User u : response.body())
                {
                    if(u.getUserId() != connected.getUserId())
                    {
                        UserLine ul = new UserLine(NewChatActivity.this, u);
                        li_users.addView(ul);
                    }
                }
            }

            @Override
            public void onFailure(Call<Set<User>> call, Throwable t) {
                tv_no_user.setVisibility(View.VISIBLE);
            }
        });
    }

    public void cancel(View v){finish();}

    public void addChat(View v)
    {
        String subject = et_subject.getText().toString();
        if(subject.isEmpty())
        {
            Toast.makeText(this, R.string.no_subject, Toast.LENGTH_LONG).show();
            return;
        }

        btn_addChat.setVisibility(View.INVISIBLE);
        Set<User> users = new HashSet<>();
        users.add(connected);

        for(int i=0; i<li_users.getChildCount(); i++)
        {
            UserLine ul = (UserLine) li_users.getChildAt(i);
            if(ul!=null && ul.getSelection())
            {
                users.add(ul.getUser());
            }
        }

        Chat newChat = new Chat(subject, users);

        Call<Chat> call = api.post(newChat);
        call.enqueue(new Callback<Chat>() {
            @Override
            public void onResponse(Call<Chat> call, Response<Chat> response) {
                if(!response.isSuccessful())
                {
                    Toast.makeText(NewChatActivity.this, response.code()+"", Toast.LENGTH_LONG).show();
                    btn_addChat.setVisibility(View.VISIBLE);
                }
                else
                {
                    Chat newChat = response.body();
                    Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                    Gson gson = new Gson();
                    intent.putExtra("User", gson.toJson(connected));
                    intent.putExtra("Chat", gson.toJson(newChat));
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Chat> call, Throwable t) {
                Toast.makeText(NewChatActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                btn_addChat.setVisibility(View.VISIBLE);
            }
        });
    }
}
