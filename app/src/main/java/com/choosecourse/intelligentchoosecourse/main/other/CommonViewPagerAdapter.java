package com.choosecourse.intelligentchoosecourse.main.other;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment适配器 FragmentPagerAdapter : 当我们的viewpager和fragment嵌套使用时实用的
 * 
 * */
public class CommonViewPagerAdapter extends FragmentPagerAdapter {

	private Context ctx;
	private List<Fragment> fragments = new ArrayList<Fragment>();
	// FragmentManager fragment管理器 ,上下文
	public CommonViewPagerAdapter(FragmentManager fm, List<Fragment> fragments, Context ctx) {
		super(fm);
		this.fragments = fragments ;
		this.ctx = ctx;
	}

	// 返回一个fragment
	// arg0 滑动到第几页
	@Override
	public Fragment getItem(int arg0) {
		return fragments.get(arg0);
	}

	// 返回适配数量
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fragments.size();
	}
}
