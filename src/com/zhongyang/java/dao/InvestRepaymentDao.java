package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.zyfyback.pojo.InvestRepayment;
import com.zhongyang.java.zyfyback.pojo.LoanRepayment;

/**
 *@package com.zhongyang.java.dao
 *@filename InvestRepaymentDao.java
 *@date 2017年7月7日下午4:57:05
 *@author suzh
 */
public interface InvestRepaymentDao {
	
	public int insertInvestRepayment(InvestRepayment repayment);
	
	public int updateInvestRepaymentByParams(InvestRepayment repayment);
	
	public List<InvestRepayment> selectInvestRepaymentByParams(InvestRepayment repayment);
	
	public int batchInsertInvestRepayment(List<InvestRepayment>list);
	
	public List<InvestRepayment>selectByLoanRepayment(LoanRepayment loanRepayment);

	public int batchUpdateInvestRepayment(List<InvestRepayment>list);
	
}
