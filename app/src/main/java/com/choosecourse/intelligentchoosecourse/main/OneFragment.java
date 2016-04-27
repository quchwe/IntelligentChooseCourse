package com.choosecourse.intelligentchoosecourse.main;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.choosecourse.intelligentchoosecourse.R;
import com.choosecourse.intelligentchoosecourse.db.Course;
import com.choosecourse.intelligentchoosecourse.db.CourseDBUtil;
import com.choosecourse.intelligentchoosecourse.db.CourseOpenHelper;
import com.choosecourse.intelligentchoosecourse.db.Login;
import com.choosecourse.intelligentchoosecourse.main.course.HttpChooseUtil;
import com.choosecourse.intelligentchoosecourse.main.other.SuishoujiActivity;
import com.choosecourse.intelligentchoosecourse.util.DateUtil;
import com.choosecourse.intelligentchoosecourse.util.GradeActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OneFragment extends ListFragment {
	private CourseOpenHelper helper;
	private SQLiteDatabase db;
	private int id;
	private TextView todayCourse;
	private TextView gradeCheck;
	private CourseDBUtil courseDBUtil;
	private List<String> data = new ArrayList<String>();
	private List<Course> courseList = new ArrayList<>();
	private DateUtil date;
	private Button gradeButton;
	private Button suiShouJi;
	private ListView list;
	//法定假日
	private String[]str = {

			"0101", "0102",	"0103",
			"0501",	"0502",	"0503",
			"1001",	"1002",	"1003"
	};
	//日期
	String mYear;
	String mMonth;
	String mDay;
	String dateStr;
	Map<Integer,String> map = new HashMap<>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {




		View view = inflater.inflate(R.layout.calss_talk, null, false);
		for (int i=0;i<DateUtil.s.length;i++){
			map.put(i+1,DateUtil.s[i]);
		}
		initView(view);
		setOnClickListener();


		date = new DateUtil(DateUtil.setDate());




			return view;


	}


	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		courseDBUtil = CourseDBUtil.getInstance(getActivity());
		helper = new CourseOpenHelper(getActivity());
		db=helper.getWritableDatabase();
		List<Login> user = new ArrayList<>();
		user = courseDBUtil.loadLogin();
		if (user.size() > 0) {
			for (Login login : user) {
				id = login.getUserId();
			}
		}
		String sql = "select *from course where cno in (select cno from choosed where sno = "+LoginActivity.ID+")";

		new queryCourseAsync().execute(sql,HttpChooseUtil.HTTP_QUERY_COURSE);





	}

	private void setOnClickListener() {
		gradeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				Intent intent = new Intent(getActivity(), GradeActivity.class);
				startActivity(intent);
			}
		});
		suiShouJi.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), SuishoujiActivity.class);
				startActivity(intent);
			}
		});

	}

	public boolean find(String s,String[]str){
	boolean b = false;
	for(int i=0;i<str.length;i++){
		if(s.equals(str[i])){
			b=true;
		}
	}
	return b;
}
	private void setTodayCourse(){
		//mDay = (date.getDayOfMonth()<10)?("0"+String.valueOf((date.getDayOfMonth()))):
					//String.valueOf(date.getDayOfMonth());
		if(date.getDayOfMonth()<10){
			mDay = "0"+ String.valueOf(date.getDayOfMonth());
		}
		else{
			mDay = String.valueOf(date.getDayOfMonth());
		}
		if(date.getMonth()<10){
			mMonth = "0"+ String.valueOf(date.getMonth());
		}
		else{
			mMonth = String.valueOf(date.getMonth());
		}
		dateStr = mMonth+mDay;
		//Toast.makeText(getActivity(), dateStr, Toast.LENGTH_SHORT).show();
		if(!find(dateStr,str)){
			//Toast.makeText(getActivity(), String.valueOf(date.getDayOfWeek()), Toast.LENGTH_SHORT).show();
			if(!(date.getDayOfWeek()==6||date.getDayOfWeek()==7)) {

				for (Course course : courseList) {
					Log.d("今天非休假日", "呵呵");
					if (date.getDayOfWeek() == course.getDaytime()) {
						data.add(course.toString());
					}
				}
			}
		}

	}
	private void initView(View v){
		suiShouJi = (Button)v.findViewById(R.id.btn_suishouji);
		todayCourse = (TextView)v.findViewById(R.id.tv_todayCourse);
		gradeButton = (Button)v.findViewById(R.id.btn_grade);
		list = (ListView)v.findViewById(android.R.id.list);
	}
	class queryCourseAsync extends AsyncTask<String,Integer,List<Course>> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected List<Course> doInBackground(String... params) {
			HttpChooseUtil util = new HttpChooseUtil();
			return util.queryCourse(params[0],params[1]);
		}

		@Override
		protected void onPostExecute(List<Course> list) {
			if (list==null){
				Toast.makeText(getActivity(), "未查询到课程数据！", Toast.LENGTH_SHORT).show();
				return;
			}
			else{
				courseList = list;

				setTodayCourse();
				if (data.size()==0){
					data.add("今天没有课！");
				}
				String s = mMonth+"月"+mDay+"日"+" "+map.get(date.getDayOfWeek());
				todayCourse.setText(s);

				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						getActivity(), android.R.layout.simple_list_item_1, data);
				setListAdapter(adapter);

			}
			super.onPostExecute(list);
		}
	}
	// 退出程序

}



