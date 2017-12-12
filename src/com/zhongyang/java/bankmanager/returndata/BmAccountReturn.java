package com.zhongyang.java.bankmanager.returndata;

import java.io.Serializable;

import com.zhongyang.java.system.uitl.Message;

/**
 *@package com.zhongyang.java.bankmanager.returndata
 *@filename BmAccountReturn.java
 *@date 2017年7月20日上午10:47:11
 *@author suzh
 */
public class BmAccountReturn implements Serializable{

	private Message message;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}
	
}
