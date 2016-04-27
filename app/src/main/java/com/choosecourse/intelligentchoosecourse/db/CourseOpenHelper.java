package com.choosecourse.intelligentchoosecourse.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by quchwe on 2015/11/29.
 */
public class CourseOpenHelper extends SQLiteOpenHelper {
	private Context mcontext;
	private static final int VERSION = 1;
	private static final String DB_NAME="choose_course";

	public static final String CREATE_STUDENT = "create table student("
												+"sno integer primary key,"
												+"sn text,"
												+"sex text,"
												+"classname text,"
												+"academy text,"
												+"interest text)"
						;
	public  static final String CREATE_COURSE = "create table course("
												+"cno integer primary key,"
												+"cn text,"
												+"type text,"
												+"profession text,"
												+"week text,"
												+"place text,"
												+"credit text,"
												+"tno integer,"
												+"daytime integer,"
												+"classtime integer,"
			                                    +"fenlei text)";
	public static final String CREATE_ANELECTIVE = "create table choose("
												+"cno integer,"
												+"sno integer,"
												+"grade text,"
												+"cn text,"
												+"profession text,"
												+"primary key(sno,cno))";
	public static final String CREATE_TEACHER = "create table teacher("
												+"tno integer primary key,"
												+"tn text,"
												+"academy text,"
												+"sex text)";
	public static final String CREATE_LOGIN =  "create table login("
												+"userId integer primary key,"
										        +"userPwd text)";
	public static final String CREATE_RIJI =  "create table riji("
												+"sno integer,"
												+"title text,"
												+"message text,"
												+"time text)";
	public static final String CREATE_USER = "create table user(uno integer primary key)";
	public CourseOpenHelper(Context context,String name,SQLiteDatabase.CursorFactory factory,int version){
		super(context,name,factory,version);
		mcontext = context;
	}
	public CourseOpenHelper(Context context){
		super(context,DB_NAME,null,VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_STUDENT);
		db.execSQL(CREATE_COURSE);
		db.execSQL(CREATE_TEACHER);
		db.execSQL(CREATE_ANELECTIVE);
		db.execSQL(CREATE_LOGIN);
		db.execSQL(CREATE_USER);
		db.execSQL(CREATE_RIJI);
		db.execSQL("insert into student(sno,sn,sex,classname,academy)values(?,?,?,?,?)",
				new String[]{"1000","李明","男","计科","物联网"});
		db.execSQL("insert into student(sno,sn,sex,classname,academy)values(?,?,?,?,?)",
				new String[]{"1001","王靖","男","计科","物联网"});
//		db.execSQL("insert into course(cno,cn,type,profession,week,place,credit,tno,daytime,classtime)values(?,?,?,?,?,?,?,?,?,?)",
//				new String[]{"2000","C语言","jing","物联网","1-16周","1A406","2","3000","4","1"});
//		db.execSQL("insert into course(cno,cn,type,profession,week,place,credit,tno,daytime,classtime)values(?,?,?,?,?,?,?,?,?,?)",
//				new String[]{"2001","世界经济","jing","金融","1-16周","1A515","2","3000","5","1"});
//		db.execSQL("insert into course(cno,cn,type,profession,week,place,credit,tno,daytime,classtime)values(?,?,?,?,?,?,?,?,?,?)",
//				new String[]{"2002","算法分析","quan","物联网","1-16周","1A313","2","3001","1","1"});
//		db.execSQL("insert into course(cno,cn,type,profession,week,place,credit,tno,daytime,classtime)values(?,?,?,?,?,?,?,?,?,?)",
//				new String[]{"2003","电影赏析","ren","人文","1-16周","1A406","2","3000","3","1"});
//		db.execSQL("insert into course(cno,cn,type,profession,week,place,credit,tno,daytime,classtime)values(?,?,?,?,?,?,?,?,?,?)",
//				new String[]{"2004","宏观经济","jing","金融","1-16周","1A515","2","3000","2","6"});
//		db.execSQL("insert into course(cno,cn,type,profession,week,place,credit,tno,daytime,classtime)values(?,?,?,?,?,?,?,?,?,?)",
//				new String[]{"2005","算法分析","ren","物联网","1-16周","1A313","2","3001","1","3"});
//		db.execSQL("insert into course(cno,cn,type,profession,week,place,credit,tno,daytime,classtime)values(?,?,?,?,?,?,?,?,?,?)",
//				new String[]{"2006","金融学","jing","人文","1-16周","1A406","2","3000","2","3"});
//		db.execSQL("insert into course(cno,cn,type,profession,week,place,credit,tno,daytime,classtime)values(?,?,?,?,?,?,?,?,?,?)",
//				new String[]{"2007","古汉语赏析","ren","人文","1-16周","1A515","2","3000","3","8"});
//		db.execSQL("insert into course(cno,cn,type,profession,week,place,credit,tno,daytime,classtime)values(?,?,?,?,?,?,?,?,?,?)",
//				new String[]{"2008","量子物理学","she","理学院","1-16周","1A313","2","3001","1","6"});
		db.execSQL("insert into teacher(tno,tn,academy,sex)values(?,?,?,?)",
				new String[]{"3000","李明","物联网","男"});
		db.execSQL("insert into teacher(tno,tn,academy,sex)values(?,?,?,?)",
				new String[]{"3001","方伟","物联网","男"});


	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
			sqLiteDatabase.execSQL("drop table if exists student");
		onCreate(sqLiteDatabase);
	}
}
