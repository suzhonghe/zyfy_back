package com.zhongyang.java.task;

import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.zhongyang.java.bankmanager.biz.BmLoanManagerBackBiz;
import com.zhongyang.java.bankmanager.params.LoanManagerParams;
import com.zhongyang.java.zyfyback.pojo.Loan;
@Scope("prototype")
@Service
public class TimerPublishedLoan extends TimerTask{
	
	private String loanId;
	
	@Autowired
	private BmLoanManagerBackBiz bmLoanManagerBackBiz;
	
	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	@Override
	public void run() {
		LoanManagerParams params=new LoanManagerParams();
		Loan loan=new Loan();
		loan.setId(loanId);
		params.setLoan(loan);
		params.setTask("task");//定时发标
		bmLoanManagerBackBiz.publishLoan(null, params);
	}

}
