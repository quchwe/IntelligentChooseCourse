package com.choosecourse.intelligentchoosecourse.main.other;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import com.choosecourse.intelligentchoosecourse.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quchwe on 2016/3/9 0009.
 */
public class MessageViewPager extends FragmentActivity implements View.OnClickListener{
	private CommonViewPagerAdapter myAdapter;
	private Button back;
	private ViewPager mViewPager;
	private List<Fragment> fragments = new ArrayList<Fragment>();
	private Button button_mingxi,button_xinxi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dtweixiudanmingxi_xx);


		TextView title=(TextView)findViewById(R.id.textTile);
		title.setText("电梯基本信息");
		fragments.add(new CreateNewMessageFragment());
		fragments.add(new HistoryMessageFragement());
		mViewPager = (ViewPager) findViewById(R.id.viewpager_dtwxdmx);
		myAdapter = new CommonViewPagerAdapter(
				this.getSupportFragmentManager(), fragments,this);
		mViewPager.setAdapter(myAdapter);
		mViewPager.setOffscreenPageLimit(2);

		button_mingxi=(Button)findViewById(R.id.dtwxdmx_mingxxi);
		button_mingxi.setOnClickListener(this);
		button_mingxi.setText("新笔记");
		button_xinxi=(Button)findViewById(R.id.dtwxdmx_xinxi);
		back = (Button)findViewById(R.id.back);
		back.setOnClickListener(this);
		button_xinxi.setText("历史笔记");
		button_xinxi.setOnClickListener(this);

		mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				if (position==0){
					button_mingxi.setBackgroundResource(R.drawable.button_yuanjiao_white);
					button_xinxi.setBackgroundResource(R.drawable.roundbutton_blue);
				}
				else {
					button_xinxi.setBackgroundResource(R.drawable.button_yuanjiao_white);
					button_mingxi.setBackgroundResource(R.drawable.roundbutton_blue);
				}

			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
			case R.id.dtwxdmx_mingxxi:
				mViewPager.setCurrentItem(0);
				button_mingxi.setBackgroundResource(R.drawable.button_yuanjiao_white);
				button_xinxi.setBackgroundResource(R.drawable.roundbutton_blue);
				break;
			case R.id.dtwxdmx_xinxi:
				mViewPager.setCurrentItem(1);
				button_xinxi.setBackgroundResource(R.drawable.button_yuanjiao_white);
				button_mingxi.setBackgroundResource(R.drawable.roundbutton_blue);
				break;
			case R.id.back:
				finish();
			default:
				break;
		}
	}
}
