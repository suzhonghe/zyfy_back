package com.zhongyang.java.bankmanager.returndata;

import java.io.Serializable;
import java.util.List;

import com.zhongyang.java.bankmanager.entity.UntredtedApplication;
import com.zhongyang.java.system.uitl.Message;

/**
 *@package com.zhongyang.java.bankmanager.returndata
 *@filename PlatformManagerReturn.java
 *@date 2017年7月7日上午10:53:03
 *@author suzh
 */
public class PlatformManagerReturn implements Serializable{

	private Message message;
	
	private List<UntredtedApplication>appliactions;
	
	public List<UntredtedApplication> getAppliactions() {
		return appliactions;
	}

	public void setAppliactions(List<UntredtedApplication> appliactions) {
		this.appliactions = appliactions;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}
	
}
