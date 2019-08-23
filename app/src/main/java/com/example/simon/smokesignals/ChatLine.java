package com.example.simon.smokesignals;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.simon.models.Chat;
import com.example.simon.models.User;

public class ChatLine extends RelativeLayout {

    TextView tv_subject;
    Button btn_messages;
    TextView tv_names;

    public ChatLine(Context context, User user, Chat chat, OnClickListener listener)
    {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.chat_line, this);
        tv_subject =  findViewById(R.id.tv_subject);
        tv_subject.setText(chat.getSubject());
        tv_names = findViewById(R.id.tv_names);
        tv_names.setText(chat.userString(user,context));

        btn_messages = findViewById(R.id.btn_messages);
        btn_messages.setOnClickListener(listener);
    }
}
