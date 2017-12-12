package com.zhongyang.java.zyfyback.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.zhongyang.java.system.enumtype.LoanRepayMent;

public class LoanRepayment implements Serializable{
	
    private String id;

    private Integer currentPeriod;

    private BigDecimal repayAmount;

    private Date repayDate;

    private LoanRepayMent status;

    private String sourceId;

    private BigDecimal amountInterest;

    private BigDecimal amountOutStanding;

    private BigDecimal amountPrincipal;

    private Date dueDate;

    private String loanId;
    
    private boolean flag;//是否提前还款，true提前还款，false不提前还款
    
    private boolean isRepay;
    
	public boolean isRepay() {
		return isRepay;
	}

	public void setRepay(boolean isRepay) {
		this.isRepay = isRepay;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getCurrentPeriod() {
		return currentPeriod;
	}

	public void setCurrentPeriod(Integer currentPeriod) {
		this.currentPeriod = currentPeriod;
	}

	public BigDecimal getRepayAmount() {
		return repayAmount;
	}

	public void setRepayAmount(BigDecimal repayAmount) {
		this.repayAmount = repayAmount;
	}

	public Date getRepayDate() {
		return repayDate;
	}

	public void setRepayDate(Date repayDate) {
		this.repayDate = repayDate;
	}

	public LoanRepayMent getStatus() {
		return status;
	}

	public void setStatus(LoanRepayMent status) {
		this.status = status;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public BigDecimal getAmountInterest() {
		return amountInterest;
	}

	public void setAmountInterest(BigDecimal amountInterest) {
		this.amountInterest = amountInterest;
	}

	public BigDecimal getAmountOutStanding() {
		return amountOutStanding;
	}

	public void setAmountOutStanding(BigDecimal amountOutStanding) {
		this.amountOutStanding = amountOutStanding;
	}

	public BigDecimal getAmountPrincipal() {
		return amountPrincipal;
	}

	public void setAmountPrincipal(BigDecimal amountPrincipal) {
		this.amountPrincipal = amountPrincipal;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
    
}