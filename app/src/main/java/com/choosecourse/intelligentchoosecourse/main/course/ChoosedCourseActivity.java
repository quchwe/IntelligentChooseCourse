package com.choosecourse.intelligentchoosecourse.main.course;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
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
import com.choosecourse.intelligentchoosecourse.main.LoginActivity;
import com.choosecourse.intelligentchoosecourse.util.CourseAdapter;
import com.choosecourse.intelligentchoosecourse.util.CourseList;
import com.choosecourse.intelligentchoosecourse.util.LoadingDialog;


import java.util.ArrayList;
import java.util.List;



/**
 * Created by quchwe on 2016/1/14 0014.
 */
public class ChoosedCourseActivity extends Activity {

private LoadingDialog dialog;
	private TextView title;
	private Button back;
	private CourseOpenHelper helper;
	private SQLiteDatabase db;
	private int id;
	private CourseDBUtil courseDBUtil;
	private List<CourseList> choosedCourseXianshiList = new ArrayList<CourseList>();
	private List<Course> choosedCourse = new ArrayList<Course>();
	private List<Login> user = new ArrayList<Login>();
	private Button submitButton;
	private CourseList list;
	private List<Choose> chooseList;
	private CourseAdapter adapter;
	private ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.course_list);
		helper =new CourseOpenHelper(ChoosedCourseActivity.this);
		db = helper.getWritableDatabase();
		courseDBUtil = CourseDBUtil.getInstance(this);
		choosedCourse = courseDBUtil.loadCourse();
		listView = (ListView) findViewById(R.id.list);

		dialog = new LoadingDialog(this);
		title = (TextView)findViewById(R.id.textTile);
		back = (Button)findViewById(R.id.back);

		title.setText("已选课程");

		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		chooseList = courseDBUtil.loadChoose();
		user = courseDBUtil.loadLogin();
		String sql = "select *from course where cno in (select cno from choosed where sno="+ LoginActivity.ID+")";
		new queryCourseAsync().execute(sql,HttpChooseUtil.HTTP_QUERY_COURSE);
//		if(chooseList.size()>0){
//			for(Choose c :chooseList){
//				if (choosedCourse.size() > 0) {
//					for (Course course : choosedCourse) {
//
//							if (course.getCno()==c.getCno()) {
//								CourseList item = new CourseList();
//								item.name = course.getCn();
//								item.courseother = course.getProfession() + " 周" + course.getDaytime() + " " + course.getClasstime() + "-" + (course.getClasstime() + 1) + "节";
//								choosedCourseXianshiList.add(item);
//							}
//
//					}
//
//				}
//			}
//		}
		if (user.size() > 0) {
			for (Login login:user) {
				id = login.getUserId();
			}
		}


		//构造Adapter

		//全部选中按钮的处理
		Button all_sel = (Button) findViewById(R.id.all_sel);
		Button all_unsel = (Button) findViewById(R.id.all_unsel);
		submitButton = (Button) findViewById(R.id.btn_submit);
		submitButton.setText("退选");
		/**
		 * 提交所选课程
		 */
		submitButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AlertDialog alertDialog = new AlertDialog.Builder(ChoosedCourseActivity.this).create();
				alertDialog.setTitle("系统提示：");
				alertDialog.setMessage("确定要退选已选课程吗？");
				alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						SparseBooleanArray checkedArray = listView.getCheckedItemPositions();
						List<BackCourse> backCourseList = new ArrayList<BackCourse>();
						List<CourseList> chooses = new ArrayList<CourseList>();
						for (int k = 0; k < checkedArray.size(); k++) {
							if (checkedArray.valueAt(k)) {
								//i就是选中的行号
								list = choosedCourseXianshiList.get(k);

									for (Course course : choosedCourse) {

										if (course.getCn().equals(list.name)) {
											int no = course.getCno();
											BackCourse backCourse = new BackCourse();
											backCourse.cno=no;
											backCourse.sno=LoginActivity.ID;
											backCourseList.add(backCourse);
											chooses.add(list);
											courseDBUtil.deleteChoosedCourse(no,LoginActivity.ID);
											Log.d("你好", "删除成功");
										}
								}

							}
						}

						for (CourseList c :chooses){
							choosedCourseXianshiList.remove(c);
						}
						adapter = new CourseAdapter(ChoosedCourseActivity.this, choosedCourseXianshiList);
						listView.setAdapter(adapter);
						//将退选课程的对应的课程号和用户ID传送给服务端
						List<String> sqlStringList = new ArrayList<String>();
						for (BackCourse b:backCourseList){
							String sql = "delete from choosed where cno ="+b.cno+"and sno = "+b.sno;
							sqlStringList.add(sql);
						}
						backCourseList.clear();
						new backAsyc().execute(sqlStringList);

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
				for (int i = 0; i < choosedCourseXianshiList.size(); i++) {
					listView.setItemChecked(i, true);
				}
			}
		});

		//全部取消按钮处理
		all_unsel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				for (int i = 0; i < choosedCourseXianshiList.size(); i++) {
					listView.setItemChecked(i, false);
				}
			}
		});
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
				Toast.makeText(ChoosedCourseActivity.this, "未查询到课程数据！", Toast.LENGTH_SHORT).show();
				return;
			}
			else{
				choosedCourse= list;
				for (Course course : choosedCourse) {
					{
						CourseList item = new CourseList();
						item.name = course.getCn();
						item.courseother = " " + course.getProfession() + " 周" + course.getDaytime() + " " + course.getClasstime() + "-" + (course.getClasstime() + 1) + "节";
						choosedCourseXianshiList.add(item);

					}
				}


				adapter = new CourseAdapter(ChoosedCourseActivity.this, choosedCourseXianshiList);
				listView.setAdapter(adapter);


			}
			super.onPostExecute(list);
		}
	}
	class backAsyc extends AsyncTask<List<String>,String,Boolean> {
		@Override
		protected void onPreExecute() {
			dialog.show();
			super.onPreExecute();
		}

		@Override
		protected Boolean doInBackground(List<String>... params) {


			HttpChooseUtil httpChooseUtil = new HttpChooseUtil();

			return httpChooseUtil.backCourse(params[0]);
		}

		@Override
		protected void onPostExecute(Boolean b) {
			dialog.dismiss();
			if (b) {
				Toast.makeText(ChoosedCourseActivity.this, "退课成功", Toast.LENGTH_SHORT).show();

			} else {
				Toast.makeText(ChoosedCourseActivity.this, "退课失败", Toast.LENGTH_SHORT).show();
				Log.d("main", "失败");
			}
		}
	}
	}

class BackCourse{
	int cno;
	int sno;
}
