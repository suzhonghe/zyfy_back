package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyback.pojo.ContractPojo;
import com.zhongyang.java.zyfyback.pojo.InvestRepayment;
import com.zhongyang.java.zyfyback.pojo.Loan;

public interface ContractDao {

	public List<ContractPojo> getContractInfo(String loanId);
	
	public List<InvestRepayment> getInvestRepaymets(InvestRepayment investRepayment);
	
	public List<Loan> uncontractLoans(Page<Loan> page);
}
