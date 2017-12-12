package com.zhongyang.java.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyback.pojo.Loan;
import com.zhongyang.java.zyfyback.returndata.ManagerData;
import com.zhongyang.java.zyfyback.vo.LoanListVo;

public interface LoanDao {
	
	public List<Loan> selectLoanByParams(Loan loan);
	
	public int insertLoan(Loan loan);

	public List<Loan> selectLoanByPage(Page<Loan> page);

	public int updateLoanByParams(Loan loan);
	
	public int selectLoanCount(String loanUserId);
	
	public List<LoanListVo>selectLoanRecordByPage(Page<LoanListVo>page);
	
	public BigDecimal selectResult(Map<String,Object>map);
	
	public ManagerData selectLoanAmount(Date date);
	
	public Integer selectLoanPerson(Date date);
	
	public Integer selectInPerson(Date date);
	
	public Integer selectLimitDays(Date date);
	
	public Double selectRate(Date date);
	
	public ManagerData selectAmountByDate(Date timeSettle,String start,String end);
}
