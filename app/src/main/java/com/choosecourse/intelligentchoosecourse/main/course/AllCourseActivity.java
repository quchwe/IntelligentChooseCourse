package com.choosecourse.intelligentchoosecourse.main.course;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.choosecourse.intelligentchoosecourse.R;
import com.choosecourse.intelligentchoosecourse.db.Choose;
import com.choosecourse.intelligentchoosecourse.db.Course;
import com.choosecourse.intelligentchoosecourse.db.CourseDBUtil;
import com.choosecourse.intelligentchoosecourse.db.CourseOpenHelper;
import com.choosecourse.intelligentchoosecourse.db.Login;
import com.choosecourse.intelligentchoosecourse.db.Student;
import com.choosecourse.intelligentchoosecourse.main.LoginActivity;
import com.choosecourse.intelligentchoosecourse.util.CourseAdapter;
import com.choosecourse.intelligentchoosecourse.util.CourseList;
import com.choosecourse.intelligentchoosecourse.util.LoadingDialog;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by quchwe on 2016/1/14 0014.
 */
public class AllCourseActivity extends Activity {
	private LoadingDialog dialog;
	private Button back;
	private TextView title;
	private CourseOpenHelper helper;
	private int id;
	private CourseDBUtil courseDBUtil;
	private List<CourseList> xianShiCourse = new ArrayList<CourseList>();
	private List<Course> allCourseList = new ArrayList<Course>();

	private Button submitButton;
	private CourseList xianShi;
	private List<Choose> choosingList = new ArrayList<Choose>();
	private List<Course> choosedList = new ArrayList<Course>();
	private List<CourseList> xianshilist = new ArrayList<>();
	private  ListView listView ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.course_list);
		listView = (ListView) findViewById(R.id.list);
		dialog = new LoadingDialog(this);
		helper =new CourseOpenHelper(AllCourseActivity.this);
		title = (TextView)findViewById(R.id.textTile);
		back = (Button)findViewById(R.id.back);
		courseDBUtil = CourseDBUtil.getInstance(this);

		Intent k = getIntent();
		String data = k.getStringExtra("data");
		if (data==null){
			title.setText("课程");
			return;
		}
		title.setText(data);


		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});




//		if (students.size() > 0) {
//			id= LoginActivity.ID;
//		}
		String sql = null;
		if (data.contains("全部课程")){
			 sql = "select *from course where (course.profession = '所有专业'or course.type = '基础课' or course.profession =" +
					" (select student.classname from student where student.sno =  "+LoginActivity.ID+"))and cno not in (select cno from choosed where sno ="+LoginActivity.ID+")";
		}else if(data.contains("专业课")){
			sql = "select *from course where  course.profession =" +
					" (select student.classname from student where student.sno =  "+ LoginActivity.ID+")and cno not in (select cno from choosed where sno ="+LoginActivity.ID+")";
		}else if (data.contains("公共选修课")){
			sql = "select *from course where  course.profession ='公选课' )and cno not in (select cno from choosed where sno ="+LoginActivity.ID+")";
		} else if (data.contains("推荐课程")){
			sql = "select *from student where sno = "+LoginActivity.ID;
		}
		if (data.contains("推荐课程")){
			new queryTuiJianAsync().execute(sql);
		}else{
			new queryCourseAsync().execute(sql,HttpChooseUtil.HTTP_QUERY_COURSE);
		}
		//构造数据,显示在界面上

		//构造Adapter




		//全部选中按钮的处理
		Button all_sel = (Button) findViewById(R.id.all_sel);
		Button all_unsel = (Button) findViewById(R.id.all_unsel);
		submitButton = (Button) findViewById(R.id.btn_submit);

		/**
		 * 提交所选课程
		 */
		submitButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AlertDialog alertDialog = new AlertDialog.Builder(AllCourseActivity.this).create();
				alertDialog.setTitle("系统提示：");
				alertDialog.setMessage("确定要提交所选课程吗？");
				alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						ChooseCourseMetheod();
					}
				});

				alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "再想想", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						finish();
					}
				});
				alertDialog.show();
			}
		});
		all_sel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				for (int i = 0; i < xianShiCourse.size(); i++) {
					listView.setItemChecked(i, true);
				}
			}
		});

		//全部取消按钮处理
		all_unsel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				for (int i = 0; i < xianShiCourse.size(); i++) {
					listView.setItemChecked(i, false);
				}
			}
		});
	}


	private void ChooseCourseMetheod(){
		SparseBooleanArray checkedArray = listView.getCheckedItemPositions();
		for (int k = 0; k < checkedArray.size(); k++) {
			if (checkedArray.valueAt(k)) {
				//k就是选中的行号
				xianShi = xianShiCourse.get(k);
				xianshilist.add(xianShi);
			}
		}
		for (CourseList xianshi:xianshilist)
			for (Course course : allCourseList) {
				if (course.getCn().equals(xianshi.name)) {
					Log.d("全部课程，所选课程名：",course.getCn()+" "+course.getProfession());
					Choose choose = new Choose();
					choose.setCno(course.getCno());
					choose.setSno(LoginActivity.ID);
					choose.setGrade("100");
					choose.setCn(course.getCn());
					choose.setProfession(course.getProfession());
					if (choosedList != null) {
						for (Course c: choosedList)
							if (c.getCno()==choose.getCno()){
								Log.d("allcourse","重复选课");
								Toast.makeText(AllCourseActivity.this, "你已经选过"+choose.getCn(), Toast.LENGTH_SHORT).show();
								return;
							}
					}

					choosingList.add(choose);
					courseDBUtil.saveChoose(choose);
				}

			}

		xianshilist.clear();
		//Toast.makeText(AllCourseActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
		if(choosingList !=null) {

			List<String> sqlStringList = new ArrayList<String>();
			for (Choose c: choosingList){
				String sql = "insert into choosed(cno,sno,cn,profession,grade) values("+c.getCno() +
						","+c.getSno()+",'"+c.getCn()+"','"+c.getProfession()+"','"+c.getGrade()+"')";
				System.out.println(sql);
				sqlStringList.add(sql);
			}
//							new chooseCourseAsync().execute(sqlStringList);
//							new chooseCourseTest().execute(choosingList);
			new chooseCourseAsync().execute(sqlStringList);
			choosingList.clear();
		}
		checkedArray.clear();
	}

	class queryCourseAsync extends AsyncTask<String,Integer,List<Course>>{
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog.show();
	}

	@Override
	protected List<Course> doInBackground(String... params) {
			HttpChooseUtil util = new HttpChooseUtil();
			return util.queryCourse(params[0],params[1]);
	}

	@Override
	protected void onPostExecute(List<Course> list) {
		dialog.dismiss();
		if (list==null){
			Toast.makeText(AllCourseActivity.this, "未查询到课程数据！", Toast.LENGTH_SHORT).show();
			return;
		}
		else{
			if (list.size()==0){
				Toast.makeText(AllCourseActivity.this, "未查询到课程数据！", Toast.LENGTH_SHORT).show();
				return;
			}
			allCourseList = list;
				for (Course course : allCourseList) {
					{
						CourseList item = new CourseList();
						item.name = course.getCn();
						item.courseother = " " + course.getProfession() + " 周" + course.getDaytime() + " " + course.getClasstime() + "-" + (course.getClasstime() + 1) + "节";
						xianShiCourse.add(item);

					}
				}



			CourseAdapter adapter = new CourseAdapter(AllCourseActivity.this, xianShiCourse);
			listView.setAdapter(adapter);
			String sql = "select *from course where cno in (select cno from choosed where sno="+ LoginActivity.ID+")";
			new queryChoosedCourseAsync().execute(sql,HttpChooseUtil.HTTP_QUERY_COURSE);

		}
		super.onPostExecute(list);
	}
}
	class chooseCourseAsync extends AsyncTask<List<String>,Integer,Boolean>{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.show();
		}

		@Override
		protected Boolean doInBackground(List<String>... params) {
			HttpChooseUtil util = new HttpChooseUtil();
			return util.backCourse(params[0]);
		}

		@Override
		protected void onPostExecute(Boolean b) {
			dialog.dismiss();
			if (b){
				Toast.makeText(AllCourseActivity.this, "选课成功！", Toast.LENGTH_SHORT).show();
				return;
			}
			else{
				Toast.makeText(AllCourseActivity.this, "选课失败", Toast.LENGTH_SHORT).show();
				}
		}
	}
	class queryChoosedCourseAsync extends AsyncTask<String,Integer,List<Course>>{
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
				choosedList = null;
				return;
			}
			choosedList =  list;
			super.onPostExecute(list);
		}
	}
	class chooseCourseTest extends AsyncTask<List<Choose>,Integer,Boolean>{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.show();
		}

		@Override
		protected Boolean doInBackground(List<Choose>... params) {
			HttpChooseUtil util = new HttpChooseUtil();
			return util.choosedCourseTest(params[0]);
		}

		@Override
		protected void onPostExecute(Boolean b) {
			dialog.dismiss();
			if (b){
				Toast.makeText(AllCourseActivity.this, "选课成功！", Toast.LENGTH_SHORT).show();
				return;
			}
			else{
				Toast.makeText(AllCourseActivity.this, "选课失败", Toast.LENGTH_SHORT).show();
			}
		}
	}

	class queryTuiJianAsync extends AsyncTask<String,Integer,List<Course>>{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.show();
		}

		@Override
		protected List<Course> doInBackground(String... params) {
			HttpChooseUtil util = new HttpChooseUtil();
			return util.tuijianCourseList(params[0]);
		}

		@Override
		protected void onPostExecute(List<Course> list) {
			dialog.dismiss();
			if (list==null){
				Toast.makeText(AllCourseActivity.this, "未查询到课程数据！", Toast.LENGTH_SHORT).show();
				return;
			}
			else{
				if (list.size()==0){
					Toast.makeText(AllCourseActivity.this, "未查询到课程数据！", Toast.LENGTH_SHORT).show();
					return;
				}
				allCourseList = list;
				for (Course course : allCourseList) {
					{
						CourseList item = new CourseList();
						item.name = course.getCn();
						item.courseother = " " + course.getProfession() + " 周" + course.getDaytime() + " " + course.getClasstime() + "-" + (course.getClasstime() + 1) + "节";
						xianShiCourse.add(item);

					}
				}



				CourseAdapter adapter = new CourseAdapter(AllCourseActivity.this, xianShiCourse);
				listView.setAdapter(adapter);
				String sql = "select *from course where cno in (select cno from choosed where sno="+ LoginActivity.ID+")";
				new queryChoosedCourseAsync().execute(sql,HttpChooseUtil.HTTP_QUERY_COURSE);

			}
			super.onPostExecute(list);
		}
	}

	@Override
	protected void onResume() {
		xianshilist.clear();
		allCourseList.clear();
		choosedList.clear();
		choosingList.clear();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		xianshilist.clear();
		allCourseList.clear();
		choosedList.clear();
		choosingList.clear();
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		xianshilist.clear();
		allCourseList.clear();
		choosedList.clear();
		choosingList.clear();
		super.onPause();
	}
}
