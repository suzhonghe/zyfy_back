package com.zhongyang.java.zyfyback.params;

import java.io.Serializable;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyback.pojo.FundRecord;

/**
 *@package com.zhongyang.java.zyfyback.params
 *@filename FundRecordParams.java
 *@date 2017年7月21日上午9:47:49
 *@author suzh
 */
public class FundRecordParams implements Serializable{

	private Page<FundRecord>page;

	public Page<FundRecord> getPage() {
		return page;
	}

	public void setPage(Page<FundRecord> page) {
		this.page = page;
	}
	
}
