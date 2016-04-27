package com.choosecourse.intelligentchoosecourse.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.choosecourse.intelligentchoosecourse.R;



public class MyinfoFragment extends Fragment{

private Button nameButton;
	private Button messageButton;
	private Button helpButton;
	private Button aboutButton;
	private Button exitButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.myinfo, container,false);
		 nameButton = (Button)view.findViewById(R.id.btn_acount);
		messageButton = (Button)view.findViewById(R.id.btn_message);
		 helpButton = (Button)view.findViewById(R.id.btn_help);
		 aboutButton = (Button)view.findViewById(R.id.btn_aboutapp);
		 exitButton = (Button)view.findViewById(R.id.btn_exit);
		setOnclickListener();

		return view;
	}

	private void setOnclickListener() {
		nameButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(),MyInformationActivity.class);
				startActivity(intent);
			}
		});
		messageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

			}
		});
		exitButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				System.exit(0);
			}
		});
	}


}
