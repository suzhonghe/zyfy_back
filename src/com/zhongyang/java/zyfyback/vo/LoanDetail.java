package com.zhongyang.java.zyfyback.vo;

import java.util.List;

import com.zhongyang.java.zyfyback.pojo.Invest;
import com.zhongyang.java.zyfyback.pojo.Loan;
import com.zhongyang.java.zyfyback.pojo.LoanRepayment;
import com.zhongyang.java.zyfyback.pojo.Product;

/**
 * 
* @Title: LoanDetail.java 
* @Package com.zhongyang.java.vo 
* @Description:标的详情 
* @author 苏忠贺   
* @date 2016年1月7日 下午4:05:06 
* @version V1.0
 */
public class LoanDetail {

	private Loan loan;
	
	private Product product;
	
	private ProjectDetail projectDtail;
	
	private List<LoanRepayment>loanRepayments;
	
	private List<Invest>invests;
	
	public ProjectDetail getProjectDtail() {
		return projectDtail;
	}

	public void setProjectDtail(ProjectDetail projectDtail) {
		this.projectDtail = projectDtail;
	}

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<LoanRepayment> getLoanRepayments() {
		return loanRepayments;
	}

	public void setLoanRepayments(List<LoanRepayment> loanRepayments) {
		this.loanRepayments = loanRepayments;
	}

	public List<Invest> getInvests() {
		return invests;
	}

	public void setInvests(List<Invest> invests) {
		this.invests = invests;
	}
	
}
