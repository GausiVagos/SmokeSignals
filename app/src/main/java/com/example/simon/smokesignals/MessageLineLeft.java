package com.example.simon.smokesignals;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.simon.models.Message;
import com.example.simon.models.User;

public class MessageLineLeft extends RelativeLayout {

    Message message;
    TextView tv_sender;
    TextView tv_sent;
    ImageView arrow;
    TextView tv_content;

    public MessageLineLeft(Context context, Message _message)
    {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.message_line_left, this);

        message = _message;
        User user = message.getUser();

        tv_sender = findViewById(R.id.tv_sender);
        tv_sent = findViewById(R.id.tv_sent);
        arrow = findViewById(R.id.arrow);
        tv_content = findViewById(R.id.tv_content);

        tv_sender.setText(user.getUserName());
        int color = user.getGender()=='M' ? R.color.male : user.getGender()=='F' ? R.color.female : R.color.other;
        tv_sender.setBackgroundResource(color);
        tv_sent.setText(message.getSent());
        arrow.setColorFilter(color);
        tv_content.setText(message.getMessageContent());
    }
}
