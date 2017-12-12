package com.zhongyang.java.zyfyback.service;

import java.util.List;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyback.pojo.LoanRepayment;
import com.zhongyang.java.zyfyback.returndata.LoanRepayDetail;

/**
 * 
 *@package com.zhongyang.java.zyfyback.service
 *@filename LoanRepaymentService.java
 *@date 2017年7月4日下午2:05:15
 *@author suzh
 */
public interface LoanRepaymentService {

	public List<LoanRepayment>queryLoanRepaymentsByParams(LoanRepayment params);
	
	public int batchAddLoanRepayment(List<LoanRepayment>list)throws Exception;
	
	public int modifyLoanRepayment(LoanRepayment loanRepayment)throws Exception;
	
	public List<LoanRepayDetail>queryLoanReoaymentByPage(Page<LoanRepayDetail>page);
	
	/**
	 * 定时查询前一天数据修改状态
	 */
	public int modifyStatusByTime(String status)throws Exception;
}
