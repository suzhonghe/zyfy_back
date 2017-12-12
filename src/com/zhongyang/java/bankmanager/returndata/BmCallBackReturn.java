package com.zhongyang.java.bankmanager.returndata;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.uitl.Message;

/**
 *@package com.zhongyang.java.bankmanager.returndata
 *@filename BmCallBackReturn.java
 *@date 2017年10月26日上午9:32:31
 *@author suzh
 */
public class BmCallBackReturn {
	
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
