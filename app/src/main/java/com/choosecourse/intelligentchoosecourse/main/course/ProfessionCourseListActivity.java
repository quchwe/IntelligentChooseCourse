package com.choosecourse.intelligentchoosecourse.main.course;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.choosecourse.intelligentchoosecourse.R;
import com.choosecourse.intelligentchoosecourse.db.Choose;
import com.choosecourse.intelligentchoosecourse.db.Course;
import com.choosecourse.intelligentchoosecourse.db.CourseDBUtil;
import com.choosecourse.intelligentchoosecourse.db.User;
import com.choosecourse.intelligentchoosecourse.main.LoginActivity;
import com.choosecourse.intelligentchoosecourse.util.CourseAdapter;
import com.choosecourse.intelligentchoosecourse.util.CourseList;
import com.choosecourse.intelligentchoosecourse.util.LoadingDialog;


import java.util.ArrayList;
import java.util.List;



public class ProfessionCourseListActivity extends Activity {
	private TextView title;
	private Button back;
	private int id;
	private LoadingDialog dialog;
	private CourseDBUtil courseDBUtil;
	private List<CourseList> xianShiCourse = new ArrayList<CourseList>();
	private List<Course> allCourseList = new ArrayList<Course>();
	private List<User> user = new ArrayList<User>();
	private Button submitButton;
	private CourseList list;
	private  ListView listView;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.course_list);
		courseDBUtil = CourseDBUtil.getInstance(this);
		dialog = new LoadingDialog(this);
		user = courseDBUtil.loadUser();
		title = (TextView)findViewById(R.id.textTile);
		back = (Button)findViewById(R.id.back);
		listView = (ListView) findViewById(R.id.list);
		title.setText("专业课程");
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		if(user.size()>0){
			for(User user0:user){
				 id = user0.getUserId();
			}
		}
		String sql = "select *from course where  course.profession =" +
				" (select student.classname from student where student.sno =  "+ LoginActivity.ID+")";

		new queryCourseAsync().execute(sql,HttpChooseUtil.HTTP_QUERY_COURSE);

		//全部选中按钮的处理
		Button all_sel = (Button) findViewById(R.id.all_sel);
		Button all_unsel = (Button) findViewById(R.id.all_unsel);
		submitButton = (Button)findViewById(R.id.btn_submit);
		/* *
		提交所选课程
		 */
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

			}
		});
		submitButton.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View view) {
								AlertDialog alertDialog = new AlertDialog.Builder(ProfessionCourseListActivity.this).create();
								alertDialog.setTitle("系统提示：");
								alertDialog.setMessage("确定要提交所选课程吗？");
								alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialogInterface, int i) {


										SparseBooleanArray checkedArray = listView.getCheckedItemPositions();
										for (int k = 0; k < checkedArray.size(); k++) {
											if (checkedArray.valueAt(k)) {
												//i就是选中的行号
												list = xianShiCourse.get(k);
												if (allCourseList.size() > 0) {
													for (Course course : allCourseList) {



														if (course.getCn().equals(list.name)) {
															Choose choose = new Choose();
															choose.setCno(course.getCno());
															choose.setSno(id);
															choose.setGrade(" ");
															courseDBUtil.saveChoose(choose);
															Log.d("你好", "插入成功");
															Toast.makeText(ProfessionCourseListActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
														}

													}

												}

											}
										}
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
	class queryCourseAsync extends AsyncTask<String,Integer,List<Course>> {
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
		protected void onPostExecute(List list) {
			dialog.dismiss();
			if (list==null){
				Toast.makeText(ProfessionCourseListActivity.this, "未查询到课程数据！", Toast.LENGTH_SHORT).show();
				return;
			}
			else{
				allCourseList = list;
				for (Course course : allCourseList) {
					{
						CourseList item = new CourseList();
						item.name = course.getCn();
						item.courseother = " " + course.getProfession() + " 周" + course.getDaytime() + " " + course.getClasstime() + "-" + (course.getClasstime() + 1) + "节";
						xianShiCourse.add(item);

					}
				}



				CourseAdapter adapter = new CourseAdapter(ProfessionCourseListActivity.this, xianShiCourse);
				listView.setAdapter(adapter);

			}
			super.onPostExecute(list);
		}
	}



}
