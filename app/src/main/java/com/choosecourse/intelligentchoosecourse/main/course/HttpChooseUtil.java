package com.choosecourse.intelligentchoosecourse.main.course;

import android.content.Context;
import android.util.Log;

import com.choosecourse.intelligentchoosecourse.db.Choose;
import com.choosecourse.intelligentchoosecourse.db.Course;
import com.choosecourse.intelligentchoosecourse.db.Diary;
import com.choosecourse.intelligentchoosecourse.db.Message;
import com.choosecourse.intelligentchoosecourse.db.Student;
import com.choosecourse.intelligentchoosecourse.main.*;
import com.choosecourse.intelligentchoosecourse.util.LoadingDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.apache.http.Header;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by quchwe on 2016/4/20 0020.
 */
public class HttpChooseUtil {
    private LoadingDialog dialog;
    List <Choose> chooses =null;
    public static final String HTTP_QUERY_COURSE="http://192.168.0.102:8080/JavaWeb/choosecourse/QueryCourse";
    private static final String HTTP_BACK_COURSE = "http://192.168.0.102:8080/JavaWeb/choosecourse/ExcuteSqlServer";
    public static final String LOGIN =  "http://192.168.0.102:8080/JavaWeb/choosecourse/LoginTest";
    public static final String CHOOSE_COURSE =  "http://192.168.0.102:8080/JavaWeb/choosecourse/HaveChoosedCourse";
    public static final String TUIJIAN_COURSE =  "http://192.168.0.102:8080/JavaWeb/choosecourse/TuiJianCourseServer";
    public static final String QUERY_DIARY =  "http://192.168.0.102:8080/JavaWeb/choosecourse/DiaryServer";
    Gson gson = new Gson();
    List<Course> courseList = null;
    OkHttpClient o = new OkHttpClient();


    public  List<Course> queryCourse(String sql,String Url){
//        dialog = new LoadingDialog(context);
//
//        AsyncHttpClient client = new AsyncHttpClient();
//        RequestParams params = new RequestParams();
//        params.put("SQL",sql);
//        dialog.show();
//        client.post(HTTP_QUERY_COURSE, params, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int i, Header[] headers, byte[] bytes) {
//
//                if (i == 200) {
//                    String c = new String(bytes);
//                    courseList = gson.fromJson(c,
//                            new TypeToken<List<Course>>() {
//                            }.getType());
//                    dialog.dismiss();
//                }
//            }
//            @Override
//            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
//                Log.d("HttpChooseUtil","选课失败");
//            }
//        });
//        return courseList;


        RequestBody body = new FormEncodingBuilder().add("SQL",sql).build();
        Request request = new Request.Builder().url(Url).post(body).build();
        try {
            Response response = o.newCall(request).execute();
            if (response.isSuccessful()){
                List<Course> dataList = gson.fromJson(response.body().string(),new TypeToken<List<Course>>()
                {}.getType());
                //Log.d("查询课程",dataList.get(0).getCn());
                return  dataList;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean backCourse(List<String> sql){

        RequestBody body = new FormEncodingBuilder().add("ExcuteSQL",gson.toJson(sql)).build();
        System.out.println(gson.toJson(sql));
        Request request = new Request.Builder().url(HTTP_BACK_COURSE).post(body).build();
        try {
            Response response = o.newCall(request).execute();
            Log.d("statusCode",String.valueOf(response.code()));
            if (response.isSuccessful()){
                String s = response.body().string();
                Log.d("response",s);

                    if (s.equals("success")) {
                        return true;
                    }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public  List<Student> queryStudent(String sql, String Url){
//        dialog = new LoadingDialog(context);
//
//        AsyncHttpClient client = new AsyncHttpClient();
//        RequestParams params = new RequestParams();
//        params.put("SQL",sql);
//        dialog.show();
//        client.post(HTTP_QUERY_COURSE, params, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int i, Header[] headers, byte[] bytes) {
//
//                if (i == 200) {
//                    String c = new String(bytes);
//                    courseList = gson.fromJson(c,
//                            new TypeToken<List<Course>>() {
//                            }.getType());
//                    dialog.dismiss();
//                }
//            }
//            @Override
//            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
//                Log.d("HttpChooseUtil","选课失败");
//            }
//        });
//        return courseList;

        RequestBody body = new FormEncodingBuilder().add("SQL",sql).build();
        Request request = new Request.Builder().url(Url).post(body).build();
        try {
            Response response = o.newCall(request).execute();
            if (response.isSuccessful()){
                List<Student> dataList = gson.fromJson(response.body().string(),new TypeToken<List<Student>>()
                {}.getType());
                return  dataList;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public  List<CourseTableData> queryCourseTable(String sql, String Url){
//        dialog = new LoadingDialog(context);
//
//        AsyncHttpClient client = new AsyncHttpClient();
//        RequestParams params = new RequestParams();
//        params.put("SQL",sql);
//        dialog.show();
//        client.post(HTTP_QUERY_COURSE, params, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int i, Header[] headers, byte[] bytes) {
//
//                if (i == 200) {
//                    String c = new String(bytes);
//                    courseList = gson.fromJson(c,
//                            new TypeToken<List<Course>>() {
//                            }.getType());
//                    dialog.dismiss();
//                }
//            }
//            @Override
//            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
//                Log.d("HttpChooseUtil","选课失败");
//            }
//        });
//        return courseList;


        RequestBody body = new FormEncodingBuilder().add("table",sql).build();
        Request request = new Request.Builder().url(Url).post(body).build();
        try {
            Response response = o.newCall(request).execute();
            if (response.isSuccessful()){
                List<CourseTableData> dataList = gson.fromJson(response.body().string(),new TypeToken<List<CourseTableData>>()
                {}.getType());
                //Log.d("查询课程",dataList.get(0).getCn());
                return  dataList;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void chooseCourse(List<Choose> chooseList) {

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("ChooseCourse", gson.toJson(chooseList));
        asyncHttpClient.post(CHOOSE_COURSE, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.d("成功", "成功");
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }
    public List<Choose> chooseCourse(String sql) {

//        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
//        RequestParams params = new RequestParams();
//        params.put("ChooseList", sql);
//
//        asyncHttpClient.post(CHOOSE_COURSE, params, new AsyncHttpResponseHandler() {
//
//            @Override
//            public void onSuccess(int i, Header[] headers, byte[] bytes) {
//              chooses = gson.fromJson(new String(bytes), new TypeToken<List<Choose>>() {
//                }.getType());
//
//            }
//
//            @Override
//            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
//
//            }
//        });
//        return chooses;
        RequestBody body = new FormEncodingBuilder().add("ChoosedList",sql).build();
        Request request = new Request.Builder().url(CHOOSE_COURSE).post(body).build();
        try {
            Response response = o.newCall(request).execute();
            if (response.isSuccessful()){
                List<Choose> dataList = gson.fromJson(response.body().string(),new TypeToken<List<Choose>>()
                {}.getType());
                //Log.d("查询课程",dataList.get(0).getCn());
                return  dataList;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean choosedCourseTest(List<Choose> chooseList){
        RequestBody body = new FormEncodingBuilder().add("ChooseCourse",gson.toJson(chooseList)).build();
        System.out.println(gson.toJson(chooseList));
        Request request = new Request.Builder().url(CHOOSE_COURSE).post(body).build();
        try {
            Response response = o.newCall(request).execute();
            if (response.isSuccessful()){
                String res = response.body().string();
                Log.d("response",res);

                if (res.equals("success")) {
                    return true;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public List<Course> tuijianCourseList(String sqlString){
        RequestBody body = new FormEncodingBuilder().add("TUIJIAN",sqlString).build();
        Request request = new Request.Builder().url(TUIJIAN_COURSE).post(body).build();


        try {
            Response response = o.newCall(request).execute();
            if (response.isSuccessful()){
                List<Course> dataList = gson.fromJson(response.body().string(),new TypeToken<List<Course>>()
                {}.getType());
                //Log.d("查询课程",dataList.get(0).getCn());
                return  dataList;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Diary> queryDiary(String sqlString){
        RequestBody body = new FormEncodingBuilder().add("Diary",sqlString).build();
        Request request = new Request.Builder().url(QUERY_DIARY).post(body).build();


        try {
            Response response = o.newCall(request).execute();
            if (response.isSuccessful()){
                List<Diary> dataList = gson.fromJson(response.body().string(),new TypeToken<List<Diary>>()
                {}.getType());
                //Log.d("查询课程",dataList.get(0).getCn());
                return  dataList;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Message> queryInform(String sqlString){
        RequestBody body = new FormEncodingBuilder().add("Inform",sqlString).build();
        Request request = new Request.Builder().url(QUERY_DIARY).post(body).build();


        try {
            Response response = o.newCall(request).execute();
            if (response.isSuccessful()){
                List<Message> dataList = gson.fromJson(response.body().string(),new TypeToken<List<Message>>()
                {}.getType());
                //Log.d("查询课程",dataList.get(0).getCn());
                return  dataList;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

