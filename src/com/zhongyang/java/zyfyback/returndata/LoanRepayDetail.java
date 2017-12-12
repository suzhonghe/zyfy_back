package com.zhongyang.java.zyfyback.returndata;

import java.math.BigDecimal;

import com.zhongyang.java.system.enumtype.LoanRepayMent;

/**
 *@package com.zhongyang.java.zyfyback.returndata
 *@filename LoanRepayDetail.java
 *@date 2017年7月21日下午4:36:15
 *@author suzh
 */
public class LoanRepayDetail {
	
	private String id;//还款计划ID

	private String dueDate;
	
	private String title;
	
	private String loanId;
	
	private Integer currentPeriod;
	
	private String userName;//借款人
	
	private BigDecimal amountInterest;//待还利息
	
	private BigDecimal amountPrincipal;//待还本金
	
	private BigDecimal account_aviamount;//标的账户余额
	
	private LoanRepayMent status;//还款状态
	
	private String strStatus;
	
	private Boolean isRepay;//借款人是否还款
	
	private String strRepay;//借款人是否还款
	
	private String repayDate;//还款时间
	
	private BigDecimal amount;
			
	public String getStrRepay() {
		return strRepay;
	}

	public void setStrRepay(String strRepay) {
		this.strRepay = strRepay;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getRepayDate() {
		return repayDate;
	}

	public void setRepayDate(String repayDate) {
		this.repayDate = repayDate;
	}

	public String getStrStatus() {
		return strStatus;
	}

	public void setStrStatus(String strStatus) {
		this.strStatus = strStatus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getCurrentPeriod() {
		return currentPeriod;
	}

	public void setCurrentPeriod(Integer currentPeriod) {
		this.currentPeriod = currentPeriod;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public BigDecimal getAmountInterest() {
		return amountInterest;
	}

	public void setAmountInterest(BigDecimal amountInterest) {
		this.amountInterest = amountInterest;
	}

	public BigDecimal getAmountPrincipal() {
		return amountPrincipal;
	}

	public void setAmountPrincipal(BigDecimal amountPrincipal) {
		this.amountPrincipal = amountPrincipal;
	}

	public BigDecimal getAccount_aviamount() {
		return account_aviamount;
	}

	public void setAccount_aviamount(BigDecimal account_aviamount) {
		this.account_aviamount = account_aviamount;
	}

	public LoanRepayMent getStatus() {
		return status;
	}

	public void setStatus(LoanRepayMent status) {
		this.status = status;
	}

	public Boolean getIsRepay() {
		return isRepay;
	}

	public void setIsRepay(Boolean isRepay) {
		this.isRepay = isRepay;
	}
	
}
