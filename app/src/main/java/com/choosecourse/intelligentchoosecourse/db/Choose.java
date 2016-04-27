package com.choosecourse.intelligentchoosecourse.db;

/**
 * Created by quchwe on 2015/11/29.
 */
public class Choose {
	private int cno;
	private int sno;
	private String grade;
	private String profession;
	private String  cn;

	public java.lang.String getProfession() {
		return profession;
	}

	public void setProfession(java.lang.String profession) {
		this.profession = profession;
	}

	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public int getCno() {
		return cno;
	}

	public void setCno(int cno) {
		this.cno = cno;
	}

	public int getSno() {
		return sno;
	}

	public void setSno(int sno) {
		this.sno = sno;
	}
}

