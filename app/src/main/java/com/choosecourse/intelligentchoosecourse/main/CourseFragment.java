package com.choosecourse.intelligentchoosecourse.main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.choosecourse.intelligentchoosecourse.R;
import com.choosecourse.intelligentchoosecourse.main.course.AllCourseActivity;
import com.choosecourse.intelligentchoosecourse.main.course.ChoosedCourseActivity;
import com.choosecourse.intelligentchoosecourse.main.course.ProfessionCourseListActivity;
import com.choosecourse.intelligentchoosecourse.main.course.RenCourseListActivity;
import com.choosecourse.intelligentchoosecourse.main.course.SheCourseListActivity;


public class CourseFragment extends ListFragment {
	private Button kexuan;
	private Button choosed;
	private Button tuijian;
	private ListView list;
	private final String [] data={"全部课程","专业课","公共选修课"};
	LayoutInflater inflater;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, data);
		setListAdapter(adapter);


	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

			Intent intent = new Intent(getActivity(), AllCourseActivity.class);
			String str = data[position];
			intent.putExtra("data",str);
			startActivity(intent);
//		} else if (position == 1) {
//			Intent intent = new Intent(getActivity(), RenCourseListActivity.class);
//			startActivity(intent);
//		} else if (position == 2) {
//			Intent intent = new Intent(getActivity(), SheCourseListActivity.class);
//			startActivity(intent);
//		} else if (position == 3) {
//			Intent intent = new Intent(getActivity(), ProfessionCourseListActivity.class);
//			startActivity(intent);
//		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.course,container,false);
		kexuan = (Button)view.findViewById(R.id.btn_kexuan);
		choosed = (Button)view.findViewById(R.id.btn_choosed);
		tuijian = (Button)view.findViewById(R.id.btn_tuijian);
		list = (ListView)view.findViewById(android.R.id.list);

		kexuan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (list.getVisibility() == View.VISIBLE) {
					list.setVisibility(View.GONE);
				} else {
					list.setVisibility(View.VISIBLE);
				}
			}
		});
		choosed.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(), ChoosedCourseActivity.class);
				startActivity(intent);
			}
		});
		tuijian.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
				alertDialog.setTitle("系统提示：");
				alertDialog.setMessage("完善个人信息将有助于更好的为你推荐课程，是否立即完善个人信息");
				alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						Intent intent = new Intent(getActivity(),InterestActivity.class);
						startActivity(intent);
					}
				});

				alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "我已完善", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						Intent intent = new Intent(getActivity(),AllCourseActivity.class);
						intent.putExtra("data","推荐课程");
						startActivity(intent);
					}
				});
				alertDialog.show();
			}
		});

		return view;
	}

}
