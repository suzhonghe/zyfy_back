package com.zhongyang.java.zyfyback.params;

import java.io.Serializable;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyback.returndata.InvestDetail;

/**
 *@package com.zhongyang.java.zyfyback.params
 *@filename InvestParams.java
 *@date 2017年7月21日上午10:47:16
 *@author suzh
 */
public class InvestParams implements Serializable{

	private Page<InvestDetail>page;

	public Page<InvestDetail> getPage() {
		return page;
	}

	public void setPage(Page<InvestDetail> page) {
		this.page = page;
	}
	
}
