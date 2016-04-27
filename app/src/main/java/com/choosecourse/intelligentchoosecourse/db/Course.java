package com.choosecourse.intelligentchoosecourse.db;

/**
 * Created by quchwe on 2015/11/29.
 */
public class Course {
	private int cno;
	private String cn;
	private String profession;
	private int tno;
	private int classtime;
	private int daytime;
	private String place;
	private String week;
	private String credit;
	private String type;
	private String fenlei;

	public String getFenlei() {
		return fenlei;
	}

	public void setFenlei(String fenlei) {
		this.fenlei = fenlei;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String zhou) {
		this.week = zhou;
	}

	public int getCno() {
		return cno;
	}

	public void setCno(int cno) {
		this.cno = cno;
	}


	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public int getTno() {
		return tno;
	}

	public void setTno(int tno) {
		this.tno = tno;
	}

	public int getClasstime() {
		return classtime;
	}

	public void setClasstime(int classtime) {
		this.classtime = classtime;
	}

	public int getDaytime() {
		return daytime;
	}

	public void setDaytime(int daytime) {
		this.daytime = daytime;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	@Override
	public String toString() {
		return getCn()+" "+getClasstime()+"-"+(getClasstime()+2)+"节  "+getPlace();
	}
}
