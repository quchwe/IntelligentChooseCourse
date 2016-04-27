package com.choosecourse.intelligentchoosecourse.main;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.choosecourse.intelligentchoosecourse.R;

public class MainFragmentActivity extends FragmentActivity  {
	/**
	 *
	 */

	RadioButton rb_classoff;
	RadioButton rb_course;
	RadioButton rb_courseTable;
	RadioButton rb_myInfo;
	
	RadioGroup radioGroup;
	private TextView title;
	private Button back;

    /** 
     *
     */  
    Fragment oneFragment;
    Fragment twoFragment;  
    Fragment threeFragment;  
    Fragment fourFragment;  
    
    FragmentTransaction transaction;
    
   
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.fragmentmain);
		rb_classoff = (RadioButton) findViewById(R.id.classoff);
		rb_course = (RadioButton) findViewById(R.id.course);
		rb_courseTable = (RadioButton) findViewById(R.id.courseTable);
		rb_myInfo = (RadioButton) findViewById(R.id.myInfo);
		title = (TextView)findViewById(R.id.textTile);
		back = (Button)findViewById(R.id.back);
		back.setVisibility(View.GONE);
		radioGroup = (RadioGroup) findViewById(R.id.radiogroup);

		  ((RadioButton)radioGroup.findViewById(R.id.classoff)).setChecked(true);
		  transaction = getSupportFragmentManager().beginTransaction();
		  Fragment fragment = new OneFragment();
		  transaction.replace(R.id.content, fragment);
		transaction.commit();
		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.classoff:
					title.setText("下课聊");
					 transaction = getSupportFragmentManager().beginTransaction();
					 Fragment onefragment = new OneFragment();
					 transaction.replace(R.id.content, onefragment);
					transaction.commit();
					break;
				case R.id.course:
					title.setText("课程");
					transaction = getSupportFragmentManager().beginTransaction();
					Fragment twofragment = new CourseFragment();
					transaction.replace(R.id.content, twofragment);
					transaction.commit();
					break;
				case R.id.courseTable:
					title.setText("课程表");
					transaction = getSupportFragmentManager().beginTransaction();
					Fragment threefragment = new CourseTableFragment();
					transaction.replace(R.id.content, threefragment);
					transaction.commit();
					break;
				case R.id.myInfo:
					title.setText("我的信息");
					transaction = getSupportFragmentManager().beginTransaction();
					Fragment fourfragment = new MyinfoFragment();
					transaction.replace(R.id.content, fourfragment);
					transaction.commit();
					break;

				default:
					break;
				}
			}
		});
		
		
		
	}

	private long exitTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 连续按两次返回键退出程序
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(MainFragmentActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				Intent startMain = new Intent(Intent.ACTION_MAIN);
				startMain.addCategory(Intent.CATEGORY_HOME);
				startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(startMain);
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	
	
}
