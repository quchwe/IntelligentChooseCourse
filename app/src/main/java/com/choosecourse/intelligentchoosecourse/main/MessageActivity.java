package com.choosecourse.intelligentchoosecourse.main;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.choosecourse.intelligentchoosecourse.R;
import com.choosecourse.intelligentchoosecourse.db.Diary;
import com.choosecourse.intelligentchoosecourse.db.Message;
import com.choosecourse.intelligentchoosecourse.main.course.HttpChooseUtil;
import com.choosecourse.intelligentchoosecourse.main.other.MessageListAdapter;
import com.choosecourse.intelligentchoosecourse.util.LoadingDialog;

import java.util.List;

public class MessageActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView lv_messageList;
    private Button back;
    private TextView textTile;
    private LoadingDialog dialog;
    private String []title;
    private String []message;
    private String [] date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initView();

        textTile.setText("通知");
    }

    private void initView() {
        lv_messageList = (ListView) findViewById(R.id.lv_messageList);
        back = (Button) findViewById(R.id.back);
        textTile = (TextView) findViewById(R.id.textTile);
        dialog = new LoadingDialog(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
    class queryDiaryAsync extends AsyncTask<String,Integer,List<Message>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected List<Message> doInBackground(String... params) {
            HttpChooseUtil h = new HttpChooseUtil();
            List<Message> diarys = h.queryInform(params[0]);
            return diarys;
        }

        @Override
        protected void onPostExecute(List<Message> diaries) {
            super.onPostExecute(diaries);
            dialog.dismiss();
            if (diaries==null){
                Toast.makeText(MessageActivity.this,"未查询到通知信息",Toast.LENGTH_SHORT).show();
                return;
            }
            title = new String[diaries.size()];
            message = new String[diaries.size()];
            date = new String[diaries.size()];
            for (int i=0;i<diaries.size();i++){
                Diary d = diaries.get(i);
                if (d.getTitle()==null)
                    title[i] ="未命名";
                message[i] = d.getMessage();
                date[i] = d.getTime();
            }
            MessageListAdapter adapter = new MessageListAdapter(getActivity(),R.layout.list_message,title,date);
            list.setAdapter(adapter);
        }
    }
}
