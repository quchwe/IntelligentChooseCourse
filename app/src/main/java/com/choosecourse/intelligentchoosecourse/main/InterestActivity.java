package com.choosecourse.intelligentchoosecourse.main;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.choosecourse.intelligentchoosecourse.db.Student;
import com.choosecourse.intelligentchoosecourse.main.course.HttpChooseUtil;
import com.choosecourse.intelligentchoosecourse.util.CourseAdapter;
import com.choosecourse.intelligentchoosecourse.util.CourseList;
import com.choosecourse.intelligentchoosecourse.util.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

public class InterestActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_interest_back;
    private TextView tv_interest_textTile;
    private Button btn_interest_save;
    private ListView lv_interest;
    private List<CourseList> xianShiList = new ArrayList<>();
    private CourseAdapter adapter;
    private CourseDBUtil dbUtil;
    private LoadingDialog dialog;
    private String [] interests = {"文学","影视","人文历史","商业经济","数理科学","IT技术","体育运动","法学政治","两性健康"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);
        initView();


    }

    private void initView() {
        btn_interest_back = (Button) findViewById(R.id.btn_interest_back);
        tv_interest_textTile = (TextView) findViewById(R.id.tv_interest_textTile);
        btn_interest_save = (Button) findViewById(R.id.btn_interest_save);
        lv_interest = (ListView) findViewById(R.id.lv_interest);

        dialog = new LoadingDialog(this);
        btn_interest_back.setOnClickListener(this);
        btn_interest_save.setOnClickListener(this);

        dbUtil = CourseDBUtil.getInstance(this);
        for (String s:interests){
            CourseList c = new CourseList();
            c.name = s;
            xianShiList.add(c);
        }
        adapter = new CourseAdapter(this, xianShiList);
        lv_interest.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_interest_back:
                finish();
                break;
            case R.id.btn_interest_save:
                ChooseCourseMetheod();
                break;
        }
    }
    private void ChooseCourseMetheod(){
        SparseBooleanArray checkedArray = lv_interest.getCheckedItemPositions();
        List<String> interestString = new ArrayList<>();
        for (int k = 0; k < checkedArray.size(); k++) {
            if (checkedArray.valueAt(k)) {
                //k就是选中的行号
                interestString.add( xianShiList.get(k).getName());
            }
        }
        String str = null;
        for (String s:interestString){
            str+=s+",";
        }
        Student s = new Student();
        s.setSno(LoginActivity.ID);
        s.setInterest(str);
        dbUtil.saveStudent(s);
        String sqlString = "update student set interest = '"+s.getInterest()+"' where sno = "+LoginActivity.ID;
        List<String> sqlStringList = new ArrayList<>();
        sqlStringList.add(sqlString);
        new saveStudentInformation().execute(sqlStringList);

    }
    class saveStudentInformation  extends AsyncTask<List<String>,Integer,Boolean> {
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
                InterestActivity.this.finish();
                Toast.makeText(InterestActivity.this, "保存成功！", Toast.LENGTH_SHORT).show();
                return;
            }
            else{
                Toast.makeText(InterestActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
