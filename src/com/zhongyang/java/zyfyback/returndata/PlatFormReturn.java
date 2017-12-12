package com.zhongyang.java.zyfyback.returndata;

import java.io.Serializable;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.uitl.Message;
import com.zhongyang.java.zyfyback.pojo.PlatFundRecord;

/**
 *@package com.zhongyang.java.zyfyback.returndata
 *@filename PlatFormReturn.java
 *@date 2017年7月24日上午8:57:34
 *@author suzh
 */
public class PlatFormReturn implements Serializable{
	
	private Message message;
	
	private Page<PlatFundRecord>page;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public Page<PlatFundRecord> getPage() {
		return page;
	}

	public void setPage(Page<PlatFundRecord> page) {
		this.page = page;
	}

}
