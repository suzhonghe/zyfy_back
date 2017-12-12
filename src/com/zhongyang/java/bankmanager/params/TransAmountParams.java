package com.zhongyang.java.bankmanager.params;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *@package com.zhongyang.java.bankmanager.params
 *@filename TransAmountParams.java
 *@date 2017年8月8日下午5:29:51
 *@author suzh
 */
public class TransAmountParams implements Serializable{
	
	private String telphone;
	
	private BigDecimal transAmount;
	
	private String description;
	
	private String platAccount;
	
	private String cause_type;
	
	private String charge_type;
		
	public String getCharge_type() {
		return charge_type;
	}

	public void setCharge_type(String charge_type) {
		this.charge_type = charge_type;
	}

	public String getCause_type() {
		return cause_type;
	}

	public void setCause_type(String cause_type) {
		this.cause_type = cause_type;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public BigDecimal getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(BigDecimal transAmount) {
		this.transAmount = transAmount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPlatAccount() {
		return platAccount;
	}

	public void setPlatAccount(String platAccount) {
		this.platAccount = platAccount;
	}
	
}
