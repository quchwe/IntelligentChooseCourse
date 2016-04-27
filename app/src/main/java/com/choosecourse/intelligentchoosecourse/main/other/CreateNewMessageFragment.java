package com.choosecourse.intelligentchoosecourse.main.other;


import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.choosecourse.intelligentchoosecourse.R;
import com.choosecourse.intelligentchoosecourse.main.LoginActivity;
import com.choosecourse.intelligentchoosecourse.main.MessageActivity;
import com.choosecourse.intelligentchoosecourse.main.course.HttpChooseUtil;
import com.choosecourse.intelligentchoosecourse.util.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quchwe on 2016/4/27 0027.
 */
public class CreateNewMessageFragment extends Fragment {
    private Button btn_suishouji_save;
    private EditText titleEdit;
    private EditText messageEdit;
    private String title;
    private String message;
    private LoadingDialog dialog;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         title=null;
       message = null;

        if ((titleEdit.getText().toString()).length()>0){
            title = titleEdit.getText().toString();
        }
        if (messageEdit.getText().toString().length()>0){
            message = messageEdit.getText().toString();
        }
        btn_suishouji_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (title ==null){
                    Toast.makeText(getActivity(),"请输入标题",Toast.LENGTH_SHORT).show();
                    return;
                }
                String sql = "insert into riji (sno,title,message,time) values("+ LoginActivity.ID+",'"+title+"','"
                        +message+"','"+String.valueOf(System.currentTimeMillis())+"')";
                List<String> sqlString = new ArrayList<String>();

                new chooseCourseAsync().execute(sqlString);
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_suishouji,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        btn_suishouji_save = (Button) view.findViewById(R.id.btn_suishouji_save);
        titleEdit = (EditText)view.findViewById(R.id.et_suiShou_title);
        messageEdit = (EditText)view.findViewById(R.id.et_suishou_message);
        dialog = new LoadingDialog(getActivity());
    }
    class chooseCourseAsync extends AsyncTask<List<String>,Integer,Boolean> {
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
                Toast.makeText(getActivity(), "保存成功！", Toast.LENGTH_SHORT).show();
                titleEdit.setText("");
                messageEdit.setText("");
                return;
            }
            else{
                Toast.makeText(getActivity(), "保存失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
