package com.ceva.base.common.bean;

public class UserBean {

	private String userid;
	private String password;
	private String encryptPassword;
	private String newPassword;
	private String confirmNewPassword;
	
	private String  hiddenPassword;
	private String  windowsize;
	private String token;
	
	

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getWindowsize() {
		return windowsize;
	}

	public void setWindowsize(String windowsize) {
		this.windowsize = windowsize;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getEncryptPassword() {
		return encryptPassword;
	}

	public void setEncryptPassword(String encryptPassword) {
		this.encryptPassword = encryptPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}

	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}

	public String getHiddenPassword() {
		return hiddenPassword;
	}

	public void setHiddenPassword(String hiddenPassword) {
		this.hiddenPassword = hiddenPassword;
	}

}
