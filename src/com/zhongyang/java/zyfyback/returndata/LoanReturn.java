package com.zhongyang.java.zyfyback.returndata;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.uitl.Message;
import com.zhongyang.java.zyfyback.pojo.Loan;
import com.zhongyang.java.zyfyback.vo.LoanDetail;
import com.zhongyang.java.zyfyback.vo.LoanListVo;

public class LoanReturn implements Serializable{
	
	private Message message;
	
	private List<Loan> loans;
	
	private Page<LoanListVo>page;
	
	private BigDecimal totalAmount;
	
	private LoanDetail loanDetail;
	
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public List<Loan> getLoans() {
		return loans;
	}

	public void setLoans(List<Loan> loans) {
		this.loans = loans;
	}

	public Page<LoanListVo> getPage() {
		return page;
	}

	public void setPage(Page<LoanListVo> page) {
		this.page = page;
	}

	public LoanDetail getLoanDetail() {
		return loanDetail;
	}

	public void setLoanDetail(LoanDetail loanDetail) {
		this.loanDetail = loanDetail;
	}

}
