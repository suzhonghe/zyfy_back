package com.zhongyang.java.bankmanager.returndata;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *@package com.zhongyang.java.bankmanager.returndata
 *@filename CustRepayList.java
 *@date 2017年7月17日下午3:39:12
 *@author suzh
 */
public class CustRepayList implements Serializable{

	private BigDecimal real_repay_amt;//实际还款金额（实际还款本金+风险互助金+体验金+加息金+利息+手续费），
	
	private BigDecimal real_repay_amount;//实际还款本金
	
	private BigDecimal experience_amt;//体验金
	
	private BigDecimal rates_amt;//加息金
	
	private BigDecimal real_repay_val;//实际还款利息
	
	private BigDecimal repay_fee;// 手续费
	
	private String cust_no;//投资人平台客户号（platcust）
	
	private Integer repay_num;// 还款期数
	
	private String repay_date;//还款日期
	
	private String real_repay_date;//实际还款日期

	public BigDecimal getReal_repay_amt() {
		return real_repay_amt;
	}

	public void setReal_repay_amt(BigDecimal real_repay_amt) {
		this.real_repay_amt = real_repay_amt;
	}

	public BigDecimal getReal_repay_amount() {
		return real_repay_amount;
	}

	public void setReal_repay_amount(BigDecimal real_repay_amount) {
		this.real_repay_amount = real_repay_amount;
	}

	public BigDecimal getExperience_amt() {
		return experience_amt;
	}

	public void setExperience_amt(BigDecimal experience_amt) {
		this.experience_amt = experience_amt;
	}

	public BigDecimal getRates_amt() {
		return rates_amt;
	}

	public void setRates_amt(BigDecimal rates_amt) {
		this.rates_amt = rates_amt;
	}

	public BigDecimal getReal_repay_val() {
		return real_repay_val;
	}

	public void setReal_repay_val(BigDecimal real_repay_val) {
		this.real_repay_val = real_repay_val;
	}

	public BigDecimal getRepay_fee() {
		return repay_fee;
	}

	public void setRepay_fee(BigDecimal repay_fee) {
		this.repay_fee = repay_fee;
	}

	public String getCust_no() {
		return cust_no;
	}

	public void setCust_no(String cust_no) {
		this.cust_no = cust_no;
	}

	public Integer getRepay_num() {
		return repay_num;
	}

	public void setRepay_num(Integer repay_num) {
		this.repay_num = repay_num;
	}

	public String getRepay_date() {
		return repay_date;
	}

	public void setRepay_date(String repay_date) {
		this.repay_date = repay_date;
	}

	public String getReal_repay_date() {
		return real_repay_date;
	}

	public void setReal_repay_date(String real_repay_date) {
		this.real_repay_date = real_repay_date;
	}

}
