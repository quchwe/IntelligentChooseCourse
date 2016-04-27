package com.choosecourse.intelligentchoosecourse.util;

import android.content.Context;
import android.util.Log;

import com.choosecourse.intelligentchoosecourse.db.Choose;
import com.choosecourse.intelligentchoosecourse.db.Course;
import com.choosecourse.intelligentchoosecourse.db.CourseDBUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;





/**
 * Created by quchwe on 2016/2/19 0019.
 */
public class CourseDownload {
	private static String URL = "http://192.168.43.8:8080/JavaWeb/choosecourse/HaveChoosedCourse";
	private CourseDBUtil courseDBUtil ;
	private List<Course> courseList;
	private List<Choose> chooseList;
	private Gson gson = new Gson();;

	public  void getAllCourseList(final Context context, String url){
		List<Cno> cnoList =new ArrayList<Cno>();
		courseDBUtil = CourseDBUtil.getInstance(context);
		courseList = courseDBUtil.loadCourse();

		if(courseList.size()>0){
			for(Course course : courseList){
				Cno cno = new Cno();
				cno.cno=course.getCno();
				cnoList.add(cno);
			}

		}else {
			Cno cno = new Cno();
			cno.cno = -1;
			cnoList.add(cno);
		}
		String cno=gson.toJson(cnoList);

		AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("Cno", cno);
		asyncHttpClient.post(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				if (statusCode == 200) {
					String c = new String(responseBody);

					List<Course> courseList1 = gson.fromJson(c,
							new TypeToken<List<Course>>() {
							}.getType());
					if (courseList1==null){
						return;
					}
					for (Course course : courseList1) {
						Log.d("课程号:", course.getPlace());
					}

					for(Course course:courseList1){
						Log.d("上课地点：", course.getPlace());
						courseDBUtil.saveCourse(course);

					}
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				Log.d("连接", "连接失败");
			}
		});
	}
public void getChoosedCourseList(Context context,String url){
	List<Cno> cnoList =new ArrayList<Cno>();
	courseDBUtil = CourseDBUtil.getInstance(context);
	chooseList = courseDBUtil.loadChoose();

	if(chooseList.size()>0){
		for(Choose choose : chooseList){
			Cno cno = new Cno();
			cno.cno=choose.getCno();
			cnoList.add(cno);
		}

	}else {
		Cno cno = new Cno();
		cno.cno = -1;
		cnoList.add(cno);
	}
	String cno=gson.toJson(cnoList);

	AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
	RequestParams params = new RequestParams();
	params.put("ChoosedCno", cno);
	asyncHttpClient.post(url, params, new AsyncHttpResponseHandler() {
		@Override
		public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
			if (statusCode == 200) {
				String c = new String(responseBody);

				List<Choose> chooseList1 = gson.fromJson(c,
						new TypeToken<List<Choose>>() {
						}.getType());

				for (Choose choose : chooseList1) {
					Log.d("上课地点：", String.valueOf(choose.getCno()));
					courseDBUtil.saveChoose(choose);

				}
			}
		}

		@Override
		public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
			Log.d("连接", "连接失败");
		}
	});
	}
	public void submitSelectCourse(Context context,String courses){
		AsyncHttpClient asy = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("SelectCourse",courses);
		asy.post(URL, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				if (statusCode==200){
					Log.d("连网选课", "选课成功");
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				Log.d("连网选课", "选课失败");
			}
		});

	}
}


