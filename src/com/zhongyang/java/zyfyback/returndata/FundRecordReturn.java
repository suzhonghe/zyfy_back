package com.zhongyang.java.zyfyback.returndata;

import java.io.Serializable;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.uitl.Message;
import com.zhongyang.java.zyfyback.pojo.FundRecord;

/**
 *@package com.zhongyang.java.zyfyback.returndata
 *@filename FundRecordReturn.java
 *@date 2017年7月21日上午9:45:28
 *@author suzh
 */
public class FundRecordReturn implements Serializable{
	
	private Message message;
	
	private Page<FundRecord>page;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public Page<FundRecord> getPage() {
		return page;
	}

	public void setPage(Page<FundRecord> page) {
		this.page = page;
	}
	
}
