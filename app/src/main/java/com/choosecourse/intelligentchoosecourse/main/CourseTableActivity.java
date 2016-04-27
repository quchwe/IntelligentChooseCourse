package com.choosecourse.intelligentchoosecourse.main;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.choosecourse.intelligentchoosecourse.R;
import com.choosecourse.intelligentchoosecourse.db.Course;
import com.choosecourse.intelligentchoosecourse.db.CourseDBUtil;
import com.choosecourse.intelligentchoosecourse.db.CourseOpenHelper;
import com.choosecourse.intelligentchoosecourse.db.Teacher;

import java.util.ArrayList;
import java.util.List;



public class CourseTableActivity extends Activity {
	private CourseOpenHelper helper;
	private SQLiteDatabase db;
	private CourseDBUtil courseDBUtil;
	private List<Course> courseList1;
	private List<Teacher> teacherList;
	private List<CourseTableData> courseTableDataList;
	private List<Integer> timeList1 = new ArrayList<Integer>();
	private List<Integer> timeList2 = new ArrayList<Integer>();
	private String tStr;

	private LinearLayout ll[] = new LinearLayout[7];

	private int colors[] = {
			Color.rgb(0xee, 0xff, 0xff),
			Color.rgb(0xf0, 0x96, 0x09),
			Color.rgb(0x8c, 0xbf, 0x26),
			Color.rgb(0x00, 0xab, 0xa9),
			Color.rgb(0x99, 0x6c, 0x33),
			Color.rgb(0x3b, 0x92, 0xbc),
			Color.rgb(0xd5, 0x4d, 0x34),
			Color.rgb(0xcc, 0xcc, 0xcc)
	};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		courseDBUtil = CourseDBUtil.getInstance(this);
		helper = new CourseOpenHelper(this);
		db = helper.getWritableDatabase();
		//分别表示周一到周日
		 ll[0] = (LinearLayout)findViewById(R.id.ll1);
		 ll[1] = (LinearLayout)findViewById(R.id.ll2);
		 ll[2] = (LinearLayout)findViewById(R.id.ll3);
		 ll[3] = (LinearLayout)findViewById(R.id.ll4);
		 ll[4] = (LinearLayout)findViewById(R.id.ll5);
		 ll[5] = (LinearLayout)findViewById(R.id.ll6);
		 ll[6] = (LinearLayout)findViewById(R.id.ll7);

		courseList1 = courseDBUtil.loadCourse();
		teacherList = courseDBUtil.loadTeacher();
		setCourse();

		setNoClass(ll[4],5,0);
		setNoClass(ll[5],14,0);
		setNoClass(ll[6],14,0);


	}
	/**
	 * 设置课程的方法
	 * @param ll
	 * @param title 课程名称
	 * @param place 地点
	 * @param last 时间
	 * @param time 周次
	 * @param classes 节数
	 * @param color 背景色
	 */
	void setClass(LinearLayout ll, String title, String place,
	              String last, String time, int classes, int color)
	{
		View view = LayoutInflater.from(this).inflate(R.layout.coursetableitem, null);
		view.setMinimumHeight(dip2px(this,classes * 48));
		view.setBackgroundColor(colors[color]);
		((TextView)view.findViewById(R.id.title)).setText(title);
		((TextView)view.findViewById(R.id.place)).setText(place);
		((TextView)view.findViewById(R.id.last)).setText(last);
		((TextView)view.findViewById(R.id.time)).setText(time);
		//为课程View设置点击的监听器
		view.setOnClickListener(new OnClickClassListener());
		TextView blank1 = new TextView(this);
		TextView blank2 = new TextView(this);
		blank1.setHeight(dip2px(this,classes));
		blank2.setHeight(dip2px(this,classes));
		ll.addView(blank1);
		ll.addView(view);
		ll.addView(blank2);
	}
	boolean find(int i,List<Integer> dataList){
		boolean a = false ;
		for(int k=0;k<dataList.size();k++){
			if(i==dataList.get(k)){
				a = true;
			}
		}
		return a;
	}
	/**
	 * 设置无课（空百）
	 * @param ll
	 * @param classes 无课的节数（长度）
	 * @param color
	 */
	void setNoClass(LinearLayout ll,int classes, int color)
	{
		TextView blank = new TextView(this);
		if(color == 0)
			blank.setMinHeight(dip2px(this,classes * 50));
		blank.setBackgroundColor(colors[color]);
		ll.addView(blank);
	}
	//点击课程的监听器
	class OnClickClassListener implements View.OnClickListener {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			String title;
			title = (String) ((TextView)v.findViewById(R.id.title)).getText();
			Toast.makeText(getApplicationContext(), "你点击的是:" + title,
					Toast.LENGTH_SHORT).show();
		}
	}

	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);} /** * 根据手机的分辨率从 px(像素) 的单位 转成为 dp */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);}


	/**
	 * 课程表具体信息
	 * @param day
	 * @param classtime
	 * @return
	 */
private CourseTableData loadCoursedata(int day,int classtime) {
	CourseTableData courseTableData =new CourseTableData();
	Cursor cursor = db.rawQuery("select course.cn,course.place,course.week,teacher.tn " +
								"from course,teacher " +
								" where course.tno = teacher.tno and "+
								" course.cno in (select choose.cno  from choose,login " +
								" where choose.sno = login.userId) and "+
								" course.classtime = "+classtime+" and course.daytime ="+day,null);
	if(cursor.moveToFirst()) {
		do {
			Log.d("123", "你好");
			CourseTableData course = new CourseTableData();
			course.setCn(cursor.getString(cursor.getColumnIndex("cn")));
			Log.d("123", course.getCn());
			course.setWeek(cursor.getString(cursor.getColumnIndex("week")));
			course.setPlace(cursor.getString(cursor.getColumnIndex("place")));
			course.setTn(cursor.getString(cursor.getColumnIndex("tn")));
			Log.d("123", course.getTn());
			courseTableData = course;

		}while (cursor.moveToNext());
	}
	if(cursor!=null){
		Log.d("1234", "你好");
		cursor.close();
	}

	return courseTableData;
}


private void setCourse() {

	//周一
	if (loadCoursedata(1, 1).getCn() == null) {
		setNoClass(ll[0], 2, 0);
	} else {
		CourseTableData course1 = loadCoursedata(1, 1);
		setClass(ll[0], course1.getCn(), course1.getPlace(), course1.getTn(), course1.getWeek(), 2, 1);
	}
	if (loadCoursedata(1, 3).getCn() == null) {
		setNoClass(ll[0], 2, 0);
	} else {
		CourseTableData course1 = loadCoursedata(1, 3);
		setClass(ll[0], course1.getCn(), course1.getPlace(), course1.getTn(), course1.getWeek(), 2, 2);
	}
	if (loadCoursedata(1, 6).getCn() == null) {
		setNoClass(ll[0], 2, 0);
	} else {
		CourseTableData course1 = loadCoursedata(1, 6);
		setClass(ll[0], course1.getCn(), course1.getPlace(), course1.getTn(), course1.getWeek(), 2, 3);
	}
	if (loadCoursedata(1, 8).getCn() == null) {
		setNoClass(ll[0], 2, 0);
	} else {
		CourseTableData course1 = loadCoursedata(1, 8);
		setClass(ll[0], course1.getCn(), course1.getPlace(), course1.getTn(), course1.getWeek(), 2, 4);
	}
//周二
	if (loadCoursedata(2, 1).getCn() == null) {
		setNoClass(ll[1], 2, 0);
	} else {
		CourseTableData course1 = loadCoursedata(2, 1);
		setClass(ll[1], course1.getCn(), course1.getPlace(), course1.getTn(), course1.getWeek(), 2, 4);
	}
	if (loadCoursedata(2, 3).getCn() == null) {
		setNoClass(ll[1], 2, 0);
	} else {
		CourseTableData course1 = loadCoursedata(2, 3);
		setClass(ll[1], course1.getCn(), course1.getPlace(), course1.getTn(), course1.getWeek(), 2, 3);
	}
	if (loadCoursedata(2, 6).getCn() == null) {
		setNoClass(ll[1], 2, 0);
	} else {
		CourseTableData course1 = loadCoursedata(2, 6);
		setClass(ll[1], course1.getCn(), course1.getPlace(), course1.getTn(), course1.getWeek(), 2, 2);
	}
	if (loadCoursedata(2, 8).getCn() == null) {
		setNoClass(ll[1], 2, 0);
	} else {
		CourseTableData course1 = loadCoursedata(2, 8);
		setClass(ll[1], course1.getCn(), course1.getPlace(), course1.getTn(), course1.getWeek(), 2, 1);
	}
	//周三
	if (loadCoursedata(3, 1).getCn() == null) {
		setNoClass(ll[2], 2, 0);
	} else {
		CourseTableData course1 = loadCoursedata(3, 1);
		setClass(ll[2], course1.getCn(), course1.getPlace(), course1.getTn(), course1.getWeek(), 2, 1);
	}
	if (loadCoursedata(3, 3).getCn() == null) {
		setNoClass(ll[2], 2, 0);
	} else {
		CourseTableData course1 = loadCoursedata(3, 3);
		setClass(ll[2], course1.getCn(), course1.getPlace(), course1.getTn(), course1.getWeek(), 2, 2);
	}
	if (loadCoursedata(3, 6).getCn() == null) {
		setNoClass(ll[2], 2, 0);
	} else {
		CourseTableData course1 = loadCoursedata(3, 6);
		setClass(ll[2], course1.getCn(), course1.getPlace(), course1.getTn(), course1.getWeek(), 2, 3);
	}
	if (loadCoursedata(3, 8).getCn() == null) {
		setNoClass(ll[2], 2, 0);
	} else {
		CourseTableData course1 = loadCoursedata(3, 8);
		setClass(ll[2], course1.getCn(), course1.getPlace(), course1.getTn(), course1.getWeek(), 2, 4);
	}
//周四
	if (loadCoursedata(4, 1).getCn() == null) {
		setNoClass(ll[3], 2, 0);
	} else {
		CourseTableData course1 = loadCoursedata(4, 1);
		setClass(ll[3], course1.getCn(), course1.getPlace(), course1.getTn(), course1.getWeek(), 2, 4);
	}
	if (loadCoursedata(4, 3).getCn() == null) {
		setNoClass(ll[3], 2, 0);
	} else {
		CourseTableData course1 = loadCoursedata(4, 3);
		setClass(ll[3], course1.getCn(), course1.getPlace(), course1.getTn(), course1.getWeek(), 2, 3);
	}
	if (loadCoursedata(4, 6).getCn() == null) {
		setNoClass(ll[3], 2, 0);
	} else {
		CourseTableData course1 = loadCoursedata(4, 6);
		setClass(ll[3], course1.getCn(), course1.getPlace(), course1.getTn(), course1.getWeek(), 2, 2);
	}
	if (loadCoursedata(4, 8).getCn() == null) {
		setNoClass(ll[3], 2, 0);
	} else {
		CourseTableData course1 = loadCoursedata(4, 8);
		setClass(ll[3], course1.getCn(), course1.getPlace(), course1.getTn(), course1.getWeek(), 2, 1);
	}
	//周五
	if (loadCoursedata(5, 1).getCn() == null) {
		setNoClass(ll[4], 2, 0);
	} else {
		CourseTableData course1 = loadCoursedata(5, 1);
		setClass(ll[4], course1.getCn(), course1.getPlace(), course1.getTn(), course1.getWeek(), 2, 1);
	}
	if (loadCoursedata(5, 3).getCn() == null) {
		setNoClass(ll[4], 2, 0);
	} else {
		CourseTableData course1 = loadCoursedata(5, 3);
		setClass(ll[4], course1.getCn(), course1.getPlace(), course1.getTn(), course1.getWeek(), 2, 3);
	}
	if (loadCoursedata(5, 6).getCn() == null) {
		setNoClass(ll[4], 2, 0);
	} else {
		CourseTableData course1 = loadCoursedata(5, 6);
		setClass(ll[4], course1.getCn(), course1.getPlace(), course1.getTn(), course1.getWeek(), 2, 2);
	}
	if (loadCoursedata(5, 8).getCn() == null) {
		setNoClass(ll[4], 2, 0);
	} else {
		CourseTableData course1 = loadCoursedata(5, 8);
		setClass(ll[4], course1.getCn(), course1.getPlace(), course1.getTn(), course1.getWeek(), 2, 1);
	}
}
}
