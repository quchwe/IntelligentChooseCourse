package com.choosecourse.intelligentchoosecourse.main.other;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.choosecourse.intelligentchoosecourse.R;

public class SuishoujiActivity extends Activity implements View.OnClickListener {

    private Button btn_shuishouji_back;
    private TextView tv_suishouji_textTile;
    private Button btn_suishouji_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suishouji);
        initView();



    }

    private void initView() {
        btn_shuishouji_back = (Button) findViewById(R.id.btn_shuishouji_back);
        tv_suishouji_textTile = (TextView) findViewById(R.id.tv_suishouji_textTile);
        btn_suishouji_save = (Button) findViewById(R.id.btn_suishouji_save);

        btn_shuishouji_back.setOnClickListener(this);
        btn_suishouji_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_shuishouji_back:
                    finish();
                break;
            case R.id.btn_suishouji_save:
                Toast.makeText(this,"保存成功",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
