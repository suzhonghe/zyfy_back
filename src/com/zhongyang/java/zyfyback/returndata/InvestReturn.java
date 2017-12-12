package com.zhongyang.java.zyfyback.returndata;

import java.io.Serializable;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.uitl.Message;

/**
 *@package com.zhongyang.java.zyfyback.returndata
 *@filename InvestReturn.java
 *@date 2017年7月21日上午10:36:58
 *@author suzh
 */
public class InvestReturn implements Serializable{

	private Message message;
	
	private Page<InvestDetail>page;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public Page<InvestDetail> getPage() {
		return page;
	}

	public void setPage(Page<InvestDetail> page) {
		this.page = page;
	}
	
}
