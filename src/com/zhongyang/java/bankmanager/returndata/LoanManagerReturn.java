package com.zhongyang.java.bankmanager.returndata;

import java.io.Serializable;

import com.zhongyang.java.system.uitl.Message;

/**
 *@package com.zhongyang.java.bankmanager.returndata
 *@filename LoanManagerReturn.java
 *@date 20172017年7月3日下午1:24:38
 *@author suzh
 */
public class LoanManagerReturn implements Serializable{
	
	private Message message;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}
	
}
