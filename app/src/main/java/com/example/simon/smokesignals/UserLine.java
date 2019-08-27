package com.example.simon.smokesignals;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simon.models.User;

public class UserLine extends LinearLayout implements View.OnClickListener {

    TextView tv_userName;
    Button btn_add;
    Context context;
    private boolean isSelected = false;
    private User user;

    public UserLine(Context context, User user)
    {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.user_line, this);
        this.user = user;
        this.context = context;

        tv_userName = findViewById(R.id.tv_userName);
        btn_add = findViewById(R.id.btn_add);

        tv_userName.setText(user.getUserName());
        btn_add.setOnClickListener(this);
    }

    public boolean getSelection()
    {
        return isSelected;
    }

    public User getUser()
    {
        return user;
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId() == R.id.btn_add)
        {
            if(!isSelected)
            {
                isSelected = true;
                btn_add.setText(R.string.added);
                btn_add.setBackgroundColor(getResources().getColor(R.color.female));
            }
            else
            {
                isSelected = false;
                btn_add.setText(R.string.add);
                btn_add.setBackgroundColor(getResources().getColor(R.color.other));
            }
        }
    }
}
