package com.example.simon.smokesignals;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.simon.models.Message;
import com.example.simon.models.User;

public class MessageLignRight extends RelativeLayout {

    Message message;
    TextView tv_sender;
    ImageView arrow;
    TextView tv_content;

    public MessageLignRight(Context context, Message _message)
    {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.message_line_left, this);

        message = _message;
        User user = message.getUser();

        tv_sender = findViewById(R.id.tv_sender);
        arrow = findViewById(R.id.arrow);
        tv_content = findViewById(R.id.tv_content);

        tv_sender.setText(R.string.you);
        int color = user.getGender()=='M' ? R.color.male : user.getGender()=='F' ? R.color.female : R.color.other;
        tv_sender.setBackgroundResource(color);
        arrow.setColorFilter(color);
        tv_content.setText(message.getMessageContent());
    }
}
