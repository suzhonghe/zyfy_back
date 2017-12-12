package com.zhongyang.java.zyfyback.params;

import java.io.Serializable;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyback.pojo.Loan;
import com.zhongyang.java.zyfyback.pojo.Project;
import com.zhongyang.java.zyfyback.pojo.User;
import com.zhongyang.java.zyfyback.vo.LoanListVo;

public class LoanParams implements Serializable{
	
	private User user;
	
	private Project project;
	
	private Loan loan;
	
	private Page<Loan>page;
	
	private Page<LoanListVo>loanPage;

	public Page<LoanListVo> getLoanPage() {
		return loanPage;
	}

	public void setLoanPage(Page<LoanListVo> loanPage) {
		this.loanPage = loanPage;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}

	public Page<Loan> getPage() {
		return page;
	}

	public void setPage(Page<Loan> page) {
		this.page = page;
	}

}
