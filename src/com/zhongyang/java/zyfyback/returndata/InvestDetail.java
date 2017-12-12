package com.zhongyang.java.zyfyback.returndata;

import java.io.Serializable;
import java.math.BigDecimal;

import com.zhongyang.java.system.enumtype.InvestStatus;
import com.zhongyang.java.system.enumtype.LoanRepayMent;
import com.zhongyang.java.system.enumtype.LoanStatus;
import com.zhongyang.java.system.enumtype.Method;

/**
 *@package com.zhongyang.java.zyfyback.returndata
 *@filename InvestDetail.java
 *@date 2017年7月21日上午10:38:02
 *@author suzh
 */
public class InvestDetail implements Serializable{
	
	private String investId;
	
	private BigDecimal amount;
	
	private String orderId;
	
	private LoanStatus status;
	
	private String strStatus;
	
	private String strSubmitTime;
	
	private String title;
	
	private Method method;
	
	private String strMethod;
	
	private int months;
	
	private Double rate;//借款费率
	
	private Double addRate; 
	
	private String loanId;

	public String getInvestId() {
		return investId;
	}

	public void setInvestId(String investId) {
		this.investId = investId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	
	public String getStrStatus() {
		return strStatus;
	}

	public void setStrStatus(String strStatus) {
		this.strStatus = strStatus;
	}

	public String getStrSubmitTime() {
		return strSubmitTime;
	}

	public void setStrSubmitTime(String strSubmitTime) {
		this.strSubmitTime = strSubmitTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LoanStatus getStatus() {
		return status;
	}

	public void setStatus(LoanStatus status) {
		this.status = status;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public String getStrMethod() {
		return strMethod;
	}

	public void setStrMethod(String strMethod) {
		this.strMethod = strMethod;
	}

	public int getMonths() {
		return months;
	}

	public void setMonths(int months) {
		this.months = months;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Double getAddRate() {
		return addRate;
	}

	public void setAddRate(Double addRate) {
		this.addRate = addRate;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	
}
