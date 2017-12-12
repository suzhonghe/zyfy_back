package com.zhongyang.java.bankmanager.returndata;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *@package com.zhongyang.java.bankmanager.returndata
 *@filename RepayDetail.java
 *@date 2017年7月19日下午3:53:49
 *@author suzh
 */
public class RepayDetail implements Serializable{
	
	private String prod_name;
	
	private String repay_num;
	
	private String repay_date;
	
	private String real_repay_date;
	
	private BigDecimal repay_amt;
	
	private BigDecimal real_repay_amt;
	
	private String status;
	
	private String plat_no;

	public String getProd_name() {
		return prod_name;
	}

	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
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

	public String getReal_repay_date() {
		return real_repay_date;
	}

	public void setReal_repay_date(String real_repay_date) {
		this.real_repay_date = real_repay_date;
	}

	public BigDecimal getRepay_amt() {
		return repay_amt;
	}

	public void setRepay_amt(BigDecimal repay_amt) {
		this.repay_amt = repay_amt;
	}

	public BigDecimal getReal_repay_amt() {
		return real_repay_amt;
	}

	public void setReal_repay_amt(BigDecimal real_repay_amt) {
		this.real_repay_amt = real_repay_amt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPlat_no() {
		return plat_no;
	}

	public void setPlat_no(String plat_no) {
		this.plat_no = plat_no;
	}
	
}
