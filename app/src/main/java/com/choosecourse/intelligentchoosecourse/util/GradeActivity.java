package com.choosecourse.intelligentchoosecourse.util;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.choosecourse.intelligentchoosecourse.R;
import com.choosecourse.intelligentchoosecourse.db.Choose;
import com.choosecourse.intelligentchoosecourse.db.Course;
import com.choosecourse.intelligentchoosecourse.db.CourseDBUtil;
import com.choosecourse.intelligentchoosecourse.db.User;
import com.choosecourse.intelligentchoosecourse.main.LoginActivity;
import com.choosecourse.intelligentchoosecourse.main.course.HttpChooseUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by quchwe on 2016/1/15 0015.
 */
public class GradeActivity extends Activity {
	private TextView title;
	private Button back;
	private CourseDBUtil courseDBUtil;
	private EditText inputCn;
	private Button queryOneCn;
	private Button queryAll;
	private LoadingDialog dialog;
	private TextView queryOneResult;
	private static final int queryone = 0;
	private static final int queryall =1;
	private List<CourseList> xianshiCourseList = new ArrayList<CourseList>();
	private List<Course> courses = new ArrayList<Course>();
	private List<User> user = new ArrayList<User>();
	private Button submitButton;
	private ListView listView;
	private GradeAdapter adapter;
	private CourseList list;
	private List<Choose> chooseList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grade_activity);

		dialog = new LoadingDialog(this);
		title = (TextView)findViewById(R.id.textTile);
		back = (Button)findViewById(R.id.back);
		queryOneCn = (Button) findViewById(R.id.btn_grade_query);
		queryOneResult = (TextView)findViewById(R.id.tv_gradeResult);
		queryAll = (Button) findViewById(R.id.btn_queryAllGrade);
		inputCn = (EditText)findViewById(R.id.et_inputGradeCourse);

		title.setText("成绩查询");

		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		courseDBUtil = CourseDBUtil.getInstance(this);
		courses = courseDBUtil.loadCourse();
		chooseList = courseDBUtil.loadChoose();

		String sql = "select *from choosed where sno ="+ LoginActivity.ID;
		new queryChoosedCourseAsync().execute(sql);


//		if(chooseList.size()>0){
//			for(Choose c :chooseList){
//				if (courses.size() > 0) {
//					for (Course course : courses) {
//						{
//							if (course.getCno()==c.getCno()) {
//								CourseList item = new CourseList();
//								item.name = course.getCn();
//								item.courseother = c.getGrade();
//								xianshiCourseList.add(item);
//							}
//						}
//					}
//
//				}
//			} listView = (ListView) findViewById(R.id.lv_gradelist);

		listView = (ListView) findViewById(R.id.lv_gradelist);

		queryOneCn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setGrade(queryone,chooseList);
			}
		});
		queryAll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setGrade(queryall,chooseList);
			}
		});

	}

	private void setGrade(int query,List<Choose> chooseList){
			if (query==queryone){
				String cn = inputCn.getText().toString();
				if (cn.length()==0){
					Toast.makeText(this,"输入为空",Toast.LENGTH_SHORT).show();
					return;
				}
				if (chooseList==null){
					Toast.makeText(this,"未查询到具体数据",Toast.LENGTH_SHORT).show();
					return;
				}
				for (Choose c :chooseList){
					if (c.getCn()==cn){
						queryOneResult.setText(c.getCn()+"/t"+c.getGrade());
					}
					else Toast.makeText(this,"未查询到具体数据",Toast.LENGTH_SHORT).show();
				}
			}else if (query==queryall){
				if (chooseList==null){
					Toast.makeText(this,"未查询到具体数据",Toast.LENGTH_SHORT).show();
					return;
				}
				for (Choose c:chooseList){
					CourseList item = new CourseList();
					item.name = c.getCn();
					item.courseother = c.getGrade();
					xianshiCourseList.add(item);
				}
				adapter = new GradeAdapter(GradeActivity.this, xianshiCourseList);

				listView.setAdapter(adapter);
			}
	}
	class queryChoosedCourseAsync extends AsyncTask<String,Integer,List<Choose>> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected List<Choose> doInBackground(String... params) {
			HttpChooseUtil util = new HttpChooseUtil();
			return util.chooseCourse(params[0]);
		}

		@Override
		protected void onPostExecute(List<Choose> list) {
			if (list==null){
				//Toast.makeText(GradeActivity.this, "未查询到具体数据", Toast.LENGTH_SHORT).show();
				return;
			}
			chooseList =  list;
			super.onPostExecute(list);
		}
	}
}
