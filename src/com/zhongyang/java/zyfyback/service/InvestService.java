package com.zhongyang.java.zyfyback.service;

import java.util.List;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyback.pojo.Invest;
import com.zhongyang.java.zyfyback.pojo.LoanRepayment;
import com.zhongyang.java.zyfyback.pojo.User;
import com.zhongyang.java.zyfyback.returndata.InvestDetail;

/**
 *@package com.zhongyang.java.zyfyback.service
 *@filename InvestService.java
 *@date 2017年7月4日下午1:50:52
 *@author suzh
 */
public interface InvestService {

	public List<Invest>queryInvestsByParams(Invest invest);
	
	public int modifyInvestStatusSettled(Invest invest)throws Exception;
	
	public int modifyInvestStatusCleared(Invest invest)throws Exception;
	
	public int queryCountByParams(String userId);
	
	public List<InvestDetail> queryInvestByPage(Page<InvestDetail>page);
	
	public int modifyInvest(Invest invest)throws Exception;
	
	public List<User>queryUserInvestRepayment(LoanRepayment loanRepayment);
}
