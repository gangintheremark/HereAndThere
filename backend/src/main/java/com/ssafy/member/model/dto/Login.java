package com.ssafy.member.model.dto;

public class Login {
	private String userId;
	private String userPwd;

	public Login() {}

	public Login(String userId, String userPwd) {
		this.userId = userId;
		this.userPwd = userPwd;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

}
