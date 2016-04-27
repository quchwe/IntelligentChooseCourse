package com.choosecourse.intelligentchoosecourse.main.other;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.choosecourse.intelligentchoosecourse.R;

public class MessageInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private Button back;
    private TextView textTile;
    private TextView tv_messageInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_info);

        initView();

        Intent k = getIntent();
        String s = k.getStringExtra("data");
        if (s==null){
            tv_messageInfo.setText(" ");
        }
        else tv_messageInfo.setText(s);

    }

    private void initView() {
        back = (Button) findViewById(R.id.back);
        textTile = (TextView) findViewById(R.id.textTile);
        tv_messageInfo = (TextView) findViewById(R.id.tv_messageInfo);
        textTile.setText("笔记");
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                    finish();
                break;
        }
    }
}
