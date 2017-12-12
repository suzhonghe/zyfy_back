package com.zhongyang.java.zyfyback.params;

import java.io.Serializable;

public class EmpLoginParams implements Serializable{
	
	private String loginName;
	
	private String passphrase;
	
	private int loginCounter;
	
	private String code;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassphrase() {
		return passphrase;
	}

	public void setPassphrase(String passphrase) {
		this.passphrase = passphrase;
	}

	public int getLoginCounter() {
		return loginCounter;
	}

	public void setLoginCounter(int loginCounter) {
		this.loginCounter = loginCounter;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
