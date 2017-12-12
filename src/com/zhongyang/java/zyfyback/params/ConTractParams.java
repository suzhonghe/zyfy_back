package com.zhongyang.java.zyfyback.params;

import java.io.Serializable;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyback.pojo.Loan;

/**
 *@package com.zhongyang.java.zyfyback.params
 *@filename ConTractParams.java
 *@date 2017年8月21日下午4:27:42
 *@author suzh
 */
public class ConTractParams implements Serializable{
	
	private Page<Loan> page;
	
	private String loanId;

	public Page<Loan> getPage() {
		return page;
	}

	public void setPage(Page<Loan> page) {
		this.page = page;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
}
