package com.zhongyang.java.bankmanager.biz;

import javax.servlet.http.HttpServletRequest;

import com.zhongyang.java.bankmanager.params.LoanManagerParams;
import com.zhongyang.java.bankmanager.returndata.LoanManagerReturn;

/**
 *@package com.zhongyang.java.bankmanager.biz
 *@filename BmLoanManagerBiz.java
 *@date 20172017年7月3日下午1:22:58
 *@author suzh
 */
public interface BmLoanManagerBackBiz {
	
	public LoanManagerReturn publishLoan(HttpServletRequest request,LoanManagerParams params);
	
	public LoanManagerReturn settlementLoan(LoanManagerParams params);
	
	public LoanManagerReturn failedLoan(LoanManagerParams params);
	
	public LoanManagerReturn repayLoan(LoanManagerParams params);
	
	public LoanManagerReturn modifyComentSateUserId(LoanManagerParams params);
}
