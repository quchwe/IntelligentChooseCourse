package com.choosecourse.intelligentchoosecourse.db;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.choosecourse.intelligentchoosecourse.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quchwe on 2015/12/3 0003.
 */
public class Query extends Activity {
	private TextView title;
	private ListView listView;
	private ArrayAdapter<String> adapter;
	private CourseDBUtil courseDBUtil;
	private List<String> datalist = new ArrayList<String>();

	private List<Student> studentList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.listview_query);
		listView = (ListView)findViewById(R.id.list_view);
		title = (TextView)findViewById(R.id.title);
		courseDBUtil = CourseDBUtil.getInstance(this);
		querystudent();
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,datalist);
		listView.setAdapter(adapter);


	}

	private void querystudent() {
		studentList = courseDBUtil.loadStudent();
		if(studentList.size()>0){
			datalist.clear();
			for(Student student:studentList){
				datalist.add(student.getSn());
				Log.d("Query", "查询 成功");
			}
			//title.setText("学生");
		}
		else{
			Log.d("Query", "studentList<0");
		}
	}
}
