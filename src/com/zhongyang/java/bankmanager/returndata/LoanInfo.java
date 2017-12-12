package com.zhongyang.java.bankmanager.returndata;

import java.io.Serializable;

/**
 *@package com.zhongyang.java.bankmanager.returndata
 *@filename LoanInfo.java
 *@date 2017年7月19日下午4:46:24
 *@author suzh
 */
public class LoanInfo implements Serializable{
	
	private String ist_year;
	
	private String total_limit;
	
	private String remain_limit;
	
	private String saled_limit;
	
	private String chargeOff_auto;
	
	private String plat_no;
	
	private String prod_id;
	
	private String prod_account;
	
	private String prod_state;
	
	private String prod_name;

	public String getIst_year() {
		return ist_year;
	}

	public void setIst_year(String ist_year) {
		this.ist_year = ist_year;
	}

	public String getTotal_limit() {
		return total_limit;
	}

	public void setTotal_limit(String total_limit) {
		this.total_limit = total_limit;
	}

	public String getRemain_limit() {
		return remain_limit;
	}

	public void setRemain_limit(String remain_limit) {
		this.remain_limit = remain_limit;
	}

	public String getSaled_limit() {
		return saled_limit;
	}

	public void setSaled_limit(String saled_limit) {
		this.saled_limit = saled_limit;
	}

	public String getChargeOff_auto() {
		return chargeOff_auto;
	}

	public void setChargeOff_auto(String chargeOff_auto) {
		this.chargeOff_auto = chargeOff_auto;
	}

	public String getPlat_no() {
		return plat_no;
	}

	public void setPlat_no(String plat_no) {
		this.plat_no = plat_no;
	}

	public String getProd_id() {
		return prod_id;
	}

	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}

	public String getProd_account() {
		return prod_account;
	}

	public void setProd_account(String prod_account) {
		this.prod_account = prod_account;
	}

	public String getProd_state() {
		return prod_state;
	}

	public void setProd_state(String prod_state) {
		this.prod_state = prod_state;
	}

	public String getProd_name() {
		return prod_name;
	}

	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}
	
}
