package com.choosecourse.intelligentchoosecourse.main.other;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.choosecourse.intelligentchoosecourse.R;
import com.choosecourse.intelligentchoosecourse.db.Diary;
import com.choosecourse.intelligentchoosecourse.main.LoginActivity;
import com.choosecourse.intelligentchoosecourse.main.course.HttpChooseUtil;
import com.choosecourse.intelligentchoosecourse.util.LoadingDialog;

import java.util.List;

/**
 * Created by quchwe on 2016/4/27 0027.
 */
public class HistoryMessageFragement extends ListFragment {
    private ListView list;
    private String[] title;
    private String[] message;
    private String [] date;
    private LoadingDialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new LoadingDialog(getActivity());
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_message,container,false);
        list = (ListView)view.findViewById(android.R.id.list);
        new queryDiaryAsync().execute("select *from riji where sno = "+ LoginActivity.ID);
        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(getActivity(),MessageInfoActivity.class);
        intent.putExtra("data",message[position]);
        startActivity(intent);
    }

    class queryDiaryAsync extends AsyncTask<String,Integer,List<Diary>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected List<Diary> doInBackground(String... params) {
            HttpChooseUtil h = new HttpChooseUtil();
            List<Diary> diarys = h.queryDiary(params[0]);
            return diarys;
        }

        @Override
        protected void onPostExecute(List<Diary> diaries) {
            super.onPostExecute(diaries);
            dialog.dismiss();
            if (diaries==null){
                Toast.makeText(getActivity(),"未查询到笔记信息",Toast.LENGTH_SHORT).show();
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
