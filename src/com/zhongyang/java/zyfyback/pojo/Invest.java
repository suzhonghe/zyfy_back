package com.zhongyang.java.zyfyback.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.zhongyang.java.system.enumtype.InvestStatus;
import com.zhongyang.java.system.enumtype.Method;

public class Invest implements Serializable{
	
	private String id;
	
	private String orderId;

    private BigDecimal amount;

    private String loanId;

    private Method repayMethod;
    
    private InvestStatus status;

    private Date submitTime;

    private String userId;

    private Integer days;

    private Integer months;

    private Integer years;
    
    private String userName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public Method getRepayMethod() {
		return repayMethod;
	}

	public void setRepayMethod(Method repayMethod) {
		this.repayMethod = repayMethod;
	}

	
	public InvestStatus getStatus() {
		return status;
	}

	public void setStatus(InvestStatus status) {
		this.status = status;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public Integer getMonths() {
		return months;
	}

	public void setMonths(Integer months) {
		this.months = months;
	}

	public Integer getYears() {
		return years;
	}

	public void setYears(Integer years) {
		this.years = years;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}