package com.choosecourse.intelligentchoosecourse.main.course;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.choosecourse.intelligentchoosecourse.util.CourseAdapter;
import com.choosecourse.intelligentchoosecourse.util.CourseList;

import java.util.ArrayList;
import java.util.List;

import com.choosecourse.intelligentchoosecourse.R;
import com.choosecourse.intelligentchoosecourse.db.Choose;
import com.choosecourse.intelligentchoosecourse.db.Course;
import com.choosecourse.intelligentchoosecourse.db.CourseDBUtil;
import com.choosecourse.intelligentchoosecourse.db.User;
import com.choosecourse.intelligentchoosecourse.util.CourseAdapter;
import com.choosecourse.intelligentchoosecourse.util.CourseList;
import com.loopj.android.http.AsyncHttpClient;


/**
 * Created by quchwe on 2016/1/12 0012.
 */
public class SheCourseListActivity extends Activity {
	private int id;
	private TextView title;
	private Button back;
	private CourseDBUtil courseDBUtil;
	private List<CourseList> courseList = new ArrayList<CourseList>();
	private List<Course> courses = new ArrayList<Course>();
	private List<User> user = new ArrayList<User>();
	private Button submitButton;
	private CourseList list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.course_list);
		back = (Button)findViewById(R.id.back);
		title = (TextView)findViewById(R.id.textTile);


		title.setText("社科类课程");
		courseDBUtil = CourseDBUtil.getInstance(this);
		courses = courseDBUtil.loadCourse();
		user = courseDBUtil.loadUser();
		if(user.size()>0){
			for(User user0:user){
				id = user0.getUserId();
			}
		}
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		//构造数据
		if (courses.size() > 0) {
			for (Course course : courses) {
				{
					if (course.getType().equals("she")) {
						CourseList item = new CourseList();
						item.name = course.getCn();
						item.courseother = " "+course.getProfession() + " 周" + course.getDaytime() + " " + course.getClasstime() + "-" + (course.getClasstime() + 1) + "节";
						courseList.add(item);
					}
				}
			}

		}

		//构造Adapter
		CourseAdapter adapter = new CourseAdapter(SheCourseListActivity.this, courseList);
		final ListView listView = (ListView) findViewById(R.id.list);
		listView.setAdapter(adapter);

		//全部选中按钮的处理
		Button all_sel = (Button) findViewById(R.id.all_sel);
		Button all_unsel = (Button) findViewById(R.id.all_unsel);
		submitButton = (Button)findViewById(R.id.btn_submit);
		/**
		 * 提交所选课程
		 */
		submitButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AlertDialog alertDialog = new AlertDialog.Builder(SheCourseListActivity.this).create();
				alertDialog.setTitle("系统提示：");
				alertDialog.setMessage("确定要提交所选课程吗？");
				alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						SparseBooleanArray checkedArray = listView.getCheckedItemPositions();
						for (int k = 0; k < checkedArray.size(); k++) {
							if (checkedArray.valueAt(k)) {
								//i就是选中的行号
								list = courseList.get(k);
								if (courses.size() > 0) {
									for (Course course : courses) {

										if (course.getCn().equals(list.name)) {
											Choose choose = new Choose();
											choose.setCno(course.getCno());
											choose.setSno(id);
											choose.setGrade(" ");
											courseDBUtil.saveChoose(choose);
											Log.d("你好", "插入成功");
											Toast.makeText(SheCourseListActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
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
				for (int i = 0; i < courseList.size(); i++) {
					listView.setItemChecked(i, true);
				}
			}
		});

		//全部取消按钮处理
		all_unsel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				for (int i = 0; i < courseList.size(); i++) {
					listView.setItemChecked(i, false);
				}
			}
		});

	}
}
