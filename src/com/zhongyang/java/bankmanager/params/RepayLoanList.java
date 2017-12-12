package com.zhongyang.java.bankmanager.params;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *@package com.zhongyang.java.bankmanager.params
 *@filename RepayLoanList.java
 *@date 2017年7月14日下午5:14:13
 *@author suzh
 */
public class RepayLoanList implements Serializable{
	
	private BigDecimal repay_amt;//还款金额
	
	private BigDecimal repay_fee;//手续费
	
	private String repay_num;// 还款期数
	
	private String repay_date;//还款日期

	public BigDecimal getRepay_amt() {
		return repay_amt;
	}

	public void setRepay_amt(BigDecimal repay_amt) {
		this.repay_amt = repay_amt;
	}

	public BigDecimal getRepay_fee() {
		return repay_fee;
	}

	public void setRepay_fee(BigDecimal repay_fee) {
		this.repay_fee = repay_fee;
	}

	public String getRepay_num() {
		return repay_num;
	}

	public void setRepay_num(String repay_num) {
		this.repay_num = repay_num;
	}

	public String getRepay_date() {
		return repay_date;
	}

	public void setRepay_date(String repay_date) {
		this.repay_date = repay_date;
	}
	

}
