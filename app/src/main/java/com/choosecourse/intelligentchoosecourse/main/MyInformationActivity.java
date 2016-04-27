package com.choosecourse.intelligentchoosecourse.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.choosecourse.intelligentchoosecourse.R;

/**
 * Created by quchwe on 2016/3/12 0012.
 */
public class MyInformationActivity extends Activity implements View.OnClickListener{
	private TextView title;
	private Button back;
	private EditText realName;
	private EditText birthday;
	private EditText ruxueDate;
	private EditText school;
	private Button interestButton;
	private Button saveInformation;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my);
		initView();


	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.back:
				finish();
				break;
			case R.id.btn_interest:
				Intent intent = new Intent(this,InterestActivity.class);
				startActivity(intent);
				break;
			case R.id.btn_myinfo_save:
				Toast.makeText(MyInformationActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
				finish();
				break;
			default:break;
		}
	}

	private void initView(){
		interestButton = (Button)findViewById(R.id.btn_interest);
		title = (TextView)findViewById(R.id.textTile);
		back = (Button)findViewById(R.id.btn_myinfo_back);
		realName = (EditText)findViewById(R.id.et_name);
		birthday = (EditText)findViewById(R.id.et_birthday);
		ruxueDate = (EditText)findViewById(R.id.et_ruxuedate);
		school = (EditText)findViewById(R.id.et_school);
		saveInformation = (Button)findViewById(R.id.btn_myinfo_save);

		back.setOnClickListener(this);
		interestButton.setOnClickListener(this);
		interestButton.setOnClickListener(this);
	}
}

