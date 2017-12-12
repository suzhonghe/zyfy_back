package com.zhongyang.java.zyfyback.params;

import java.io.Serializable;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyback.pojo.PlatFundRecord;

/**
 *@package com.zhongyang.java.zyfyback.params
 *@filename PlatFormParams.java
 *@date 2017年7月24日上午9:01:02
 *@author suzh
 */
public class PlatFormParams implements Serializable{

	private Page<PlatFundRecord>page;

	public Page<PlatFundRecord> getPage() {
		return page;
	}

	public void setPage(Page<PlatFundRecord> page) {
		this.page = page;
	}
	
}
