package com.choosecourse.intelligentchoosecourse.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by quchwe on 2016/1/3 0003.
 */
public class DateUtil {
private	int dayOfWeek;
private	int month;
private int year;
private	int dayOfMonth;
public static  String [] s = {"星期一","星期二","星期三","星期四","星期五","星期六","星期日"};

	private static Date curDate    =   new Date(System.currentTimeMillis());



	public DateUtil(Calendar c){
		setDayOfMonth(c);
		setDayOfWeek(c);
		setMonth(c);
		setYear(c);
	}
	public static Calendar setDate(){
	Calendar c = new GregorianCalendar();
	c.setTime(new Date());
	Date d = c.getTime();
		return c;
	}
	public void setDayOfWeek(Calendar c){
		int a = c.get(Calendar.DAY_OF_WEEK);
		if(a==1){
			a=7;
		}
		else{
			a-=1;
		}
		this.dayOfWeek = a;
	}
	public void setYear(Calendar c){
		 this.year = c.get(Calendar.YEAR);
	}
	public void setMonth(Calendar c){
		 this.month = c.get(Calendar.MONTH)+1;
	}
	public void setDayOfMonth (Calendar c){
		 this.dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
	}

	public int getDayOfMonth() {
		return dayOfMonth;
	}

	public int getDayOfWeek() {
		return dayOfWeek;
	}

	public int getMonth() {
		return month;
	}

	public int getYear() {
		return year;
	}
	public static String currentDateTime(){
		SimpleDateFormat formatter    =   new SimpleDateFormat("yyyy年MM月dd日");

		return  formatter.format(curDate);
	}
}
