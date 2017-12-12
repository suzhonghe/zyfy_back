package com.zhongyang.java.zyfyback.returndata;

import java.io.Serializable;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.uitl.Message;

/**
 *@package com.zhongyang.java.zyfyback.returndata
 *@filename LoanRepaymentReturn.java
 *@date 2017年7月21日下午4:29:13
 *@author suzh
 */
public class LoanRepaymentReturn implements Serializable{

	private Message message;
	
	private Page<LoanRepayDetail>page;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public Page<LoanRepayDetail> getPage() {
		return page;
	}

	public void setPage(Page<LoanRepayDetail> page) {
		this.page = page;
	}
	
}
