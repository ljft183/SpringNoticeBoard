package com.kjm.vo;

public class UserVO {
	private String mailAddress;
	private String password;
	private String writer;
	int enabled;
	String auth;
	int exp;
	
	public UserVO() {
		super();
	}
	
	public UserVO(String writer, String password, String mailAddress, int enabled, String auth, int exp) {
		super();
		this.writer = writer;
		this.password = password;
		this.mailAddress = mailAddress;
		this.enabled = enabled;
		this.auth = auth;
		this.exp = exp;
	}

	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getmailAddress() {
		return mailAddress;
	}
	public void setmailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public String getAuth() {
		return auth;
	}

	
	public void setAuth(String auth) {
		this.auth = auth;
	}

	public int getEnabled() {
		return enabled;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}

	
	@Override
	public String toString() {
		return "BoardVO [writer=" + writer + ", password="
				+ password + ", mailAddress=" + mailAddress + ", auth=" + auth+ ", enabled=" + enabled+  "]";
	}
}