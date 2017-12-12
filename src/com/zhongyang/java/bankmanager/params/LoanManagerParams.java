package com.zhongyang.java.bankmanager.params;

import java.io.Serializable;

import com.zhongyang.java.zyfyback.pojo.Loan;
import com.zhongyang.java.zyfyback.pojo.LoanRepayment;

/**
 *@package com.zhongyang.java.bankmanager.params
 *@filename LoanManagerParams.java
 *@date 20172017年7月3日下午1:25:17
 *@author suzh
 */
public class LoanManagerParams implements Serializable{

	private Loan loan;
	
	private LoanRepayment loanRepayment;
	
	private String task;

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}

	public LoanRepayment getLoanRepayment() {
		return loanRepayment;
	}

	public void setLoanRepayment(LoanRepayment loanRepayment) {
		this.loanRepayment = loanRepayment;
	}
	
}
