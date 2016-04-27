package com.choosecourse.intelligentchoosecourse.db;

/**
 * Created by quchwe on 2015/11/29.
 */
public class Teacher  {
	private int tno;
	private String tn;
	private String sex;
	private String academy;

	public void setTno(int tno) {
		this.tno = tno;
	}

	public int getTno() {
		return tno;
	}

	public String getTn() {
		return tn;
	}

	public void setTn(String tn) {
		this.tn = tn;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAcademy() {
		return academy;
	}

	public void setAcademy(String academy) {
		this.academy = academy;
	}
}
