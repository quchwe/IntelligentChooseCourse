package com.choosecourse.intelligentchoosecourse.main;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import android.widget.Toast;

import com.choosecourse.intelligentchoosecourse.R;
import com.choosecourse.intelligentchoosecourse.db.Choose;
import com.choosecourse.intelligentchoosecourse.db.Course;
import com.choosecourse.intelligentchoosecourse.db.CourseDBUtil;
import com.choosecourse.intelligentchoosecourse.db.Login;
import com.choosecourse.intelligentchoosecourse.db.Student;
import com.choosecourse.intelligentchoosecourse.db.User;
import com.choosecourse.intelligentchoosecourse.main.course.HttpChooseUtil;
import com.choosecourse.intelligentchoosecourse.util.CourseDownload;
import com.choosecourse.intelligentchoosecourse.util.LoadingDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.apache.http.Header;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;



public class LoginActivity extends Activity implements OnClickListener{
	public static final int SHOW_RESPONSE = 0;
	private LoadingDialog dialog;
	private CourseDBUtil courseDBUtil;
	private boolean b ;
	private Button loginButton;
	private Button forgetPWD;
	private EditText inputAcount;
	private EditText inputPWD;
	private CheckBox rMimabox;
	public static int ID;
	private User user = new User();
	private Handler handler ;
	private List<Choose> chooseList = new ArrayList<>();
@Override

protected void onCreate(Bundle savedInstanceState) {

	super.onCreate(savedInstanceState);
	setContentView(R.layout.login);

	initData();

//	loginButton.setOnClickListener(new OnClickListener() {
//
//		@Override
//		public void onClick(View v) {
//			if (checkUser()) {
//
//				List<Login> loginList = courseDBUtil.loadLogin();
//				CourseDownload cd = new CourseDownload();
//				cd.getAllCourseList(LoginActivity.this,URL);
//				if(!loginList.contains(user)) {
//					courseDBUtil.saveLogin(user);
//				}
//
//				Log.d("你好", String.valueOf(user.getUserId()));
//				//Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
//				Intent intent = new Intent(LoginActivity.this, MainFragmentActivity.class);
//				LoginActivity.this.startActivity(intent);
//				finish();
//
//			} else if(!checkUser()){
////				Toast.makeText(LoginActivity.this, "账号或密码错误，登录失败", Toast.LENGTH_SHORT).show();
//			Log.d("main", "失败");
//			}
//		}
//	});
	loginButton.setOnClickListener(this);

}


	private void initData(){
		courseDBUtil = CourseDBUtil.getInstance(this);
		 loginButton = (Button)findViewById(R.id.loginButtonId);
		forgetPWD= (Button)findViewById(R.id.fMimaID);
		inputAcount = ((EditText)findViewById(R.id.inputClientId));
		inputPWD = ((EditText)findViewById(R.id.inputMimaId));
		dialog = new LoadingDialog(this);
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.back:
				finish();
				break;
			case R.id.fMimaID:

				break;
			case R.id.loginButtonId: {
                String number = inputAcount.getText().toString();
                String pwd = inputPWD.getText().toString();
                //String URL_Path = "http://192.168.0.101:8080/JavaWeb/choosecourse/LoginTest?LoginName=" + number + "&LoginPassword=" + pwd;
                if ((number != null) && (pwd != null)) {
                    try {
                        int n = Integer.parseInt(number);
                        int p = Integer.parseInt(pwd);
						ID = n;
                        user.setUserId(n);
                        user.setPwd(p);
						String sql = "select *from student where sno ="+n;
						List<String> sqlString = new ArrayList<>();
						sqlString.add(sql);
                        new LoginAsyc().execute(sql,HttpChooseUtil.LOGIN);
                    } catch (Exception e) {
                        Toast.makeText(this,"账号或密码输入有误",Toast.LENGTH_SHORT).show();
                    }
                }
            }
				break;
			default:break;
		}
	}

	class LoginAsyc extends AsyncTask<String,String,List<Student>>{
	@Override
	protected void onPreExecute() {
      dialog.show();
        super.onPreExecute();
	}

		@Override
		protected List<Student> doInBackground(String... params) {
//            OkHttpClient client = new OkHttpClient();
//            Request request = new Request.Builder().url(params[0]).build();
//            Response response = null;
//            try {
//                response = client.newCall(request).execute();
//                System.out.print(response.code());
//                if (response.isSuccessful()) {
//
//                    return response.body().string().equals("success");
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
			HttpChooseUtil httpChooseUtil = new HttpChooseUtil();

			return httpChooseUtil.queryStudent(params[0],params[1]);
		}

		@Override
		protected void onPostExecute(List<Student> result) {
			dialog.dismiss();
			if (result!=null) {
				Log.d("登录 ",result.get(0).getSn());
				List<Login> loginList = courseDBUtil.loadLogin();
				if(!loginList.contains(user)) {
					courseDBUtil.saveLogin(user);
					courseDBUtil.saveStudent(result.get(0));
				}

				Log.d("你好", String.valueOf(user.getUserId()));
				Intent intent = new Intent(LoginActivity.this, MainFragmentActivity.class);
				LoginActivity.this.startActivity(intent);
				finish();

			} else {
				Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
				Log.d("main", "失败");
			}
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
			for (Choose c:chooseList){
				courseDBUtil.saveChoose(c);
			}
			super.onPostExecute(list);
		}
	}

}

