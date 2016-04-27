package com.choosecourse.intelligentchoosecourse.main.other;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.TextView;


import com.choosecourse.intelligentchoosecourse.R;

import java.util.List;

/**
 * Created by quchwe on 2016/3/3 0003.
 */
public class ImageDataListAdapter extends ArrayAdapter<ImageDataListText> {
	private int resourceId;
	public ImageDataListAdapter(Context context, int textViewResourceId,
	                  List<ImageDataListText>objects) {
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageDataListText imageDataListText = getItem(position); // 获取当前项的Fruit实例
		View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		TextView name = (TextView) view.findViewById(R.id.tv_message_title);
		TextView date = (TextView) view.findViewById(R.id.tv__message_date);

		name.setText(imageDataListText.getName());
		date.setText(imageDataListText.getDate());
		return view;
	}
}
