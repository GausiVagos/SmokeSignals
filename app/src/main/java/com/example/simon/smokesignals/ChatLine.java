package com.example.simon.smokesignals;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.simon.models.Chat;

public class ChatLine extends LinearLayout {

    TextView tv_subject;
    Button btn_messages;

    public ChatLine(Context _context, Chat _chat, OnClickListener listener)
    {
        super(_context);
        LayoutInflater inflater = LayoutInflater.from(_context);
        inflater.inflate(R.layout.chat_line, this);
        tv_subject =  findViewById(R.id.tv_subject);
        String subject = _chat.getSubject();
        tv_subject.setText(subject);

        btn_messages = findViewById(R.id.btn_messages);
        btn_messages.setOnClickListener(listener);
    }
}
