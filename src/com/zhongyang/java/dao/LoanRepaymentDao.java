package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyback.pojo.LoanRepayment;
import com.zhongyang.java.zyfyback.returndata.LoanRepayDetail;

/**
 *@package com.zhongyang.java.dao
 *@filename LoanRepaymentDao.java
 *@date 2017年7月4日下午1:53:28
 *@author suzh
 */
public interface LoanRepaymentDao {

	List<LoanRepayment>selectLoanRepaymentsByParams(LoanRepayment params);
	
	int batchInsertLoanRepayment(List<LoanRepayment>list);
	
	int updateLoanRepayment(LoanRepayment loanRepayment);
	
	List<LoanRepayDetail>selectLoanReoaymentByPage(Page<LoanRepayDetail>page);
	
	/**
	 * 定时查询前一天数据修改状态
	 */
	int updateStatusByTime(String status);
}
