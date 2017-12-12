package com.zhongyang.java.zyfyback.params;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyback.returndata.LoanRepayDetail;

/**
 *@package com.zhongyang.java.zyfyback.params
 *@filename LoanRepaymentParams.java
 *@date 2017年7月21日下午4:41:31
 *@author suzh
 */
public class LoanRepaymentParams {
	
	private Page<LoanRepayDetail>page;

	public Page<LoanRepayDetail> getPage() {
		return page;
	}

	public void setPage(Page<LoanRepayDetail> page) {
		this.page = page;
	}
	
}
