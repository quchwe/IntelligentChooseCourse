package com.choosecourse.intelligentchoosecourse.db;

/**
 * Created by quchwe on 2016/1/4 0004.
 */
public class Login {
	private int userId;
	private String UserPwd;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserPwd() {
		return UserPwd;
	}

	public void setUserPwd(String userPwd) {
		UserPwd = userPwd;
	}
}
