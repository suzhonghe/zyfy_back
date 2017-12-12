package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyback.pojo.Invest;
import com.zhongyang.java.zyfyback.pojo.LoanRepayment;
import com.zhongyang.java.zyfyback.pojo.User;
import com.zhongyang.java.zyfyback.returndata.InvestDetail;

/**
 * 
 *@package com.zhongyang.java.dao
 *@filename InvestDao.java
 *@date 2017年7月4日下午1:37:43
 *@author suzh
 */
public interface InvestDao {
	
	public List<Invest> selectInvestsByparams(Invest invest);
	
	/**
	 * 结算成功修改标的状态为已结算
	 *@date 下午12:51:30
	 *@param invest
	 *@return
	 *@author suzh
	 */
	public int updateInvestStatusSettled(Invest invest);
	
	public int updateInvestStatusCleared(Invest invest);
	
	public int selectCountByParams(String userId);
	
	public List<InvestDetail> selectInvestByPage(Page<InvestDetail>page);
	
	public int updateInvest(Invest invest);
	
	public List<User>selectUserInvestRepayment(LoanRepayment loanRepayment);
}
