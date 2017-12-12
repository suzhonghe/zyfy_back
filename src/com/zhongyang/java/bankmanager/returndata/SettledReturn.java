package com.zhongyang.java.bankmanager.returndata;

import java.io.Serializable;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.uitl.Message;

/**
 *@package com.zhongyang.java.bankmanager.returndata
 *@filename SettledReturn.java
 *@date 2017年10月18日下午1:36:43
 *@author suzh
 */
public class SettledReturn implements Serializable{

	private Message message;
	
	private Page<Object>page;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public Page<Object> getPage() {
		return page;
	}

	public void setPage(Page<Object> page) {
		this.page = page;
	}
	
}
