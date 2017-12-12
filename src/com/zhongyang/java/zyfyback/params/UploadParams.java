package com.zhongyang.java.zyfyback.params;

import java.io.Serializable;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyback.pojo.Loan;

/**
 *@package com.zhongyang.java.zyfyback.params
 *@filename UploadParams.java
 *@date 2017年8月24日上午10:44:04
 *@author suzh
 */
public class UploadParams implements Serializable{
	
	private Page<Loan>page;

	public Page<Loan> getPage() {
		return page;
	}

	public void setPage(Page<Loan> page) {
		this.page = page;
	}
	
}
