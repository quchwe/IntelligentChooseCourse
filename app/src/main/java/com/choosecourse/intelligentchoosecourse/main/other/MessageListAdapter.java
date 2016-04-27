package com.choosecourse.intelligentchoosecourse.main.other;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.choosecourse.intelligentchoosecourse.R;


/**
 * Created by quchwe on 2016/3/4 0004.
 */
public class MessageListAdapter extends ArrayAdapter<String> {
	private int resourceId;
	private String[] names;
	private String [] text;
	public MessageListAdapter(Context context, int textViewResourceId,
							  String[] objects, String[] initname) {
		super(context,textViewResourceId,objects);
		resourceId = textViewResourceId;
		this.names=initname;

	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		String Text = text[position];
		String Name=names[position];
		View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		TextView name = (TextView) view.findViewById(R.id.tv_message_title);
		TextView text = (TextView) view.findViewById(R.id.tv__message_date);
		name.setText(Name);
		text.setText(Text);
		return view;
	}
}
