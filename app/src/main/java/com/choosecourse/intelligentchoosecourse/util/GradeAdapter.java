package com.choosecourse.intelligentchoosecourse.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.choosecourse.intelligentchoosecourse.R;

import java.util.List;

/**
 * Created by quchwe on 2016/1/15 0015.
 */
public class GradeAdapter extends BaseAdapter {
	private List<CourseList> mList;
	private Context mContext;

	private LayoutInflater layoutInflater;
	public GradeAdapter(Context context, List<CourseList> objects){

		mContext = context;
		mList = objects;
		layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {

			holder=new ViewHolder();

			convertView = layoutInflater.inflate(R.layout.grade_list_item, null);
			holder.courseName = (TextView) convertView.findViewById(R.id.tv_gradename);
			holder.courseother = (TextView) convertView.findViewById(R.id.tv_gradeitem);
			convertView.setTag(holder);

		}else {

			holder = (ViewHolder)convertView.getTag();
		}

		holder.courseName.setText((String) mList.get(position).name);
		holder.courseother.setText((String)mList.get(position).courseother);
		return convertView;
	}
	class ViewHolder{
		TextView courseName;
		TextView courseother;
	}
}


