package com.zhongyang.java.zyfyback.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyback.pojo.Loan;
import com.zhongyang.java.zyfyback.returndata.ManagerData;
import com.zhongyang.java.zyfyback.vo.LoanListVo;

/**
 * 
* @Title: Loan.java 
* @Package com.zhongyang.java.service 
* @Description:标的业务接口 
* @author 苏忠贺   
* @date 2015年12月3日 下午5:22:23 
* @version V1.0
 */
public interface LoanService {
	
	public List<Loan> queryLoanByParams(Loan loan);
	
	public int addLoan(Loan loan)throws Exception;
	
	public List<Loan> queryLoanByPage(Page<Loan>page);
	
	public int modifyLoanByparams(Loan loan)throws Exception;
	
	public int queryLoanCount(String loanUserId);
	
	public List<LoanListVo>queryLoanRecordByPage(Page<LoanListVo>page);
	
	public BigDecimal queryResult(Map<String,Object>map);
	
	public ManagerData queryLoanAmount(Date date);
	
	public Integer queryLoanPerson(Date date);
	
	public Integer queryInPerson(Date date);
	
	public Integer queryLimitDays(Date date);
	
	public Double queryRate(Date date);
	
	public ManagerData queryAmountByDate(Date timeSettle,String start,String end);
}
