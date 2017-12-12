package com.zhongyang.java.zyfyback.returndata;

import java.io.Serializable;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.uitl.Message;
import com.zhongyang.java.zyfyback.pojo.Loan;

/**
 *@package com.zhongyang.java.zyfyback.returndata
 *@filename ConTractReturn.java
 *@date 2017年8月21日下午4:28:47
 *@author suzh
 */
public class ConTractReturn implements Serializable{
	
	private Page<Loan> page;
	
	private Message message;

	public Page<Loan> getPage() {
		return page;
	}

	public void setPage(Page<Loan> page) {
		this.page = page;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}
}
