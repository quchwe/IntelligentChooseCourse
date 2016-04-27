package com.choosecourse.intelligentchoosecourse.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quchwe on 2015/11/29.
 * 封装常用数据库操作
 */
public class CourseDBUtil {

	public static final String DB_NAME = "choose_course";

	public static final int VERSION = 1;

	private static CourseDBUtil courseDB;

	private static SQLiteDatabase db;

	/*
	*私有化构造方法
	 */
	private CourseDBUtil(Context context){
		CourseOpenHelper dbHelper = new CourseOpenHelper(context,DB_NAME,null,VERSION);
		db = dbHelper.getWritableDatabase();

	}

	/**
	 * 获取CourseDbUtil实例
	 */

	public synchronized static CourseDBUtil getInstance(Context context){
		if(courseDB==null){
			courseDB = new CourseDBUtil(context);

		}
		return courseDB;
	}

	/**
	 * 将student存入数据库
	 */

public void saveStudent(Student student) {
	List<Student> students = loadStudent();
	if (student != null){
		ContentValues values = new ContentValues();
		values.put("sno",student.getSno());
		values.put("sn",student.getSn());
		values.put("sex",student.getSex());
		values.put("classname",student.getClassname());
		values.put("academy",student.getAcademy());
		values.put("interest",student.getInterest());
		if (student.getSn()==null){
			db.execSQL("update student set interest ='"+student.getInterest()+"' where sno ="+student.getSno());
			return;
		}
		for (Student s:students){
			if (s.getSno() ==student.getSno()){
				db.execSQL("update student set sn ='"+student.getSn()+"',sex ='"+
						student.getSex()+"',classname ='"+student.getClassname()+"',academy = '"
						+student.getAcademy()+"',interest = '"+student.getInterest()+"' where sno ="+student.getSno());
				return;
			}
		}
		db.insert("student",null,values);
	}
}
//	public void saveUser(User user){
//		if(user!=null){
//			db.delete("user", null, null);
//			ContentValues values = new ContentValues();
//			values.put("uno",user.getUserId());
//			db.insert("user",null,values);
//		}
//	}
	/**
	 *读取全部课程信息
	 */
	public List<Course> loadCourse(){
		List<Course> list =new ArrayList<Course>();
		Cursor cursor = db.query("course",null,null,null,null,null,null);
		if(cursor.moveToFirst()){
			do{
				Course course = new Course();
				course.setCno(cursor.getInt(cursor.getColumnIndex("cno")));
				course.setCn(cursor.getString(cursor.getColumnIndex("cn")));
				course.setClasstime(cursor.getInt(cursor.getColumnIndex("classtime")));
				course.setWeek(cursor.getString(cursor.getColumnIndex("week")));
				course.setDaytime(cursor.getInt(cursor.getColumnIndex("daytime")));
				course.setProfession(cursor.getString(cursor.getColumnIndex("profession")));
				course.setPlace(cursor.getString(cursor.getColumnIndex("place")));
				course.setTno(cursor.getInt(cursor.getColumnIndex("tno")));
				course.setType(cursor.getString(cursor.getColumnIndex("type")));
				course.setCredit(cursor.getString(cursor.getColumnIndex("credit")));
				course.setFenlei(cursor.getString(cursor.getColumnIndex("fenlei")));
				list.add(course);

			}while (cursor.moveToNext());
		}
		if(cursor!=null){
			cursor.close();
		}
		return list;
	}

	public void saveCourse(Course course) {
		List<Course> courseList =loadCourse();
		if (course!= null){

			ContentValues values = new ContentValues();
			values.put("cno",course.getCno());
			values.put("cn",course.getCn());
			values.put("place",course.getPlace());
			values.put("classtime",course.getClasstime());
			values.put("daytime",course.getDaytime());
			values.put("profession",course.getProfession());
			values.put("tno", course.getTno());
			values.put("credit",course.getCredit());
			values.put("type",course.getType());
			values.put("week", course.getWeek());
			values.put("fenlei",course.getFenlei());
			for (Course c:courseList){
				if (c.getCno()==course.getCno()){
					db.execSQL("update course set cn ='"+course.getCn()+"',place ='"+
							course.getPlace()+"',classtime ="+course.getClasstime()+",daytime ="
					+course.getDaytime()+", profession ='"+course.getProfession()+"',tno="
					+course.getTno()+",credit = '"+course.getCredit()+"',type = '"
					+course.getType()+"', week = "+course.getWeek()+"',fenlei = '"+course.getFenlei()+"'where cno ="+course.getCno());
				}
			}
			db.insert("course", null, values);

			Log.d("保存成功", "成功");
		}
	}
	public void saveLogin(User user) {
		if (user != null){
			List<Login> logins = loadLogin();
			db.execSQL("delete from login");
			ContentValues values = new ContentValues();
			values.put("userId",user.getUserId());
			values.put("userPwd",user.getPwd());

			for (Login l:logins){
				if (l.getUserId()==user.getUserId()){
					db.execSQL("update login set userPwd = "+user.getPwd()+" where userId =  "+user.getPwd());
					return;
				}
			}
			db.insert("login",null,values);

		}
	}
	public void saveTeacher(Teacher teacher) {
		if (teacher != null){
			ContentValues values = new ContentValues();
			values.put("tno",teacher.getTno());
			values.put("tn",teacher.getTn());
			values.put("sex",teacher.getSex());
			values.put("academy",teacher.getAcademy());
			db.insert("teacher", null, values);
		}
	}
	public List<Teacher> loadTeacher(){
		List<Teacher> list =new ArrayList<Teacher>();
		Cursor cursor = db.query("teacher",null,null,null,null,null,null);
		if(cursor.moveToFirst()){
			do{
				Teacher teacher = new Teacher();
				teacher.setTno(cursor.getInt(cursor.getColumnIndex("tno")));
				teacher.setTn(cursor.getString(cursor.getColumnIndex("tn")));
				teacher.setSex(cursor.getString(cursor.getColumnIndex("sex")));
				teacher.setAcademy(cursor.getString(cursor.getColumnIndex("academy")));
				list.add(teacher);

			}while (cursor.moveToNext());
		}
		if(cursor!=null){
			cursor.close();
		}
		return list;
	}
	public List<Login> loadLogin(){
		List<Login> list =new ArrayList<Login>();
		Cursor cursor = db.query("login",null,null,null,null,null,null);
		if(cursor.moveToFirst()){
			do{
				Login login = new Login();
				login.setUserId(cursor.getInt(cursor.getColumnIndex("userId")));
				login.setUserPwd(cursor.getString(cursor.getColumnIndex("userPwd")));
				list.add(login);

			}while (cursor.moveToNext());
		}
		if(cursor!=null){
			cursor.close();
		}
		return list;
	}
	public void saveChoose(Choose anelective) {
		List<Choose> chooseList = loadChoose();

		if (anelective != null){
			ContentValues values = new ContentValues();
			values.put("cno",anelective.getCno());
			values.put("sno",anelective.getSno());
			values.put("grade",anelective.getGrade());
			values.put("profession",anelective.getProfession());
			values.put("cn",anelective.getCn());

			for (Choose c:chooseList){
				if (c.getCno()==anelective.getCno()&&c.getSno()==anelective.getSno()){
					db.execSQL("update choose set profession = '"+anelective.getProfession()+
							"', cn = '"+anelective.getCn()+"', grade = '"+anelective.getGrade()+"' where cno ="+
							anelective.getCno()+" and sno ="+anelective.getSno());
					return;

				}
			}
			db.insert("choose", null, values);
			Log.d("选课", anelective.getGrade());
		}
	}
	public void deleteChoosedCourse(int cno,int sno){
		db.execSQL("delete from choose where cno ="+cno+" and sno ="+sno);
		Log.d("退课", "成功");
	}
	public List<User> loadUser(){
		List<User> list =new ArrayList<User>();
		Cursor cursor = db.query("user",null,null,null,null,null,null);
		if(cursor.moveToFirst()){
			do{
				User user = new User();
				user.setUserId(cursor.getInt(cursor.getColumnIndex("uno")));
				list.add(user);

			}while (cursor.moveToNext());
		}
		if(cursor!=null){
			cursor.close();
		}
		return list;
	}


	public List<Student> loadStudent(){
		List<Student> list =new ArrayList<Student>();
		Cursor cursor = db.query("student",null,null,null,null,null,null);
		if(cursor.moveToFirst()){
			do{
				Student student = new Student();
				student.setSno(cursor.getInt(cursor.getColumnIndex("sno")));
				student.setSn(cursor.getString(cursor.getColumnIndex("sn")));
				student.setClassname(cursor.getString(cursor.getColumnIndex("classname")));
				student.setSex(cursor.getString(cursor.getColumnIndex("sex")));
				student.setAcademy(cursor.getString(cursor.getColumnIndex("academy")));
				student.setInterest(cursor.getString(cursor.getColumnIndex("interest")));
				list.add(student);

			}while (cursor.moveToNext());
		}
		if(cursor!=null){
			cursor.close();
		}
		return list;
	}
	public List<Choose> loadChoose(){
		List<Choose> list =new ArrayList<Choose>();
		Cursor cursor = db.query("choose",null,null,null,null,null,null);
		if(cursor.moveToFirst()){
			do{
				Choose choose = new Choose();
				choose.setSno(cursor.getInt(cursor.getColumnIndex("sno")));
				choose.setCno(cursor.getInt(cursor.getColumnIndex("cno")));
				choose.setGrade(cursor.getString(cursor.getColumnIndex("grade")));

				list.add(choose);

			}while (cursor.moveToNext());
		}
		if(cursor!=null){
			cursor.close();
		}
		return list;
	}
//	public List<User> loadUser(){
//		List<User> list =new ArrayList<User>();
//		Cursor cursor = db.query("user",null,null,null,null,null,null);
//		if(cursor.moveToFirst()){
//			do{
//				User user = new User();
//				user.setUserId(cursor.getInt(cursor.getColumnIndex("userId")));
//
//				list.add(user);
//
//			}while (cursor.moveToNext());
//		}
//		if(cursor!=null){
//			cursor.close();
//		}
//		return list;
//	}
}
