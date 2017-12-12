package com.zhongyang.java.zyfyback.service;

import java.util.List;

import com.zhongyang.java.zyfyback.pojo.InvestRepayment;
import com.zhongyang.java.zyfyback.pojo.LoanRepayment;

/**
 * 
 *@package com.zhongyang.java.zyfyback.service
 *@filename InvestRepaymentService.java
 *@date 2017年7月17日下午1:25:45
 *@author suzh
 */
public interface InvestRepaymentService {
	
	public int addInvestRepayment(InvestRepayment repayment)throws Exception;
	
	public int modifyInvestRepaymentByParams(InvestRepayment repayment)throws Exception;
	
	public List<InvestRepayment> queryInvestRepaymentByParams(InvestRepayment repayment);
	
	public int batchAddInvestRepayment(List<InvestRepayment>list)throws Exception;
	
	public List<InvestRepayment>queryByLoanRepayment(LoanRepayment loanRepayment);
	
	public int batchModifyInvestRepayment(List<InvestRepayment>list)throws Exception;
}
