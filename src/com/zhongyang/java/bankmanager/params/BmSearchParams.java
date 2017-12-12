package com.zhongyang.java.bankmanager.params;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *@package com.zhongyang.java.bankmanager.params
 *@filename BmSearchParams.java
 *@date 2017年8月9日下午2:34:05
 *@author suzh
 */
public class BmSearchParams implements Serializable{

	private String account;

	private String acct_type;
	private String fund_type;
	
	private String prod_id;
	private String type;
	
	private int pagenum=1;
	private int pagesize=10;
	
	private String original_serial_no;
	private BigDecimal occur_balance;
	
	private String mobile;
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOriginal_serial_no() {
		return original_serial_no;
	}

	public void setOriginal_serial_no(String original_serial_no) {
		this.original_serial_no = original_serial_no;
	}

	public BigDecimal getOccur_balance() {
		return occur_balance;
	}

	public void setOccur_balance(BigDecimal occur_balance) {
		this.occur_balance = occur_balance;
	}

	public int getPagenum() {
		return pagenum;
	}

	public void setPagenum(int pagenum) {
		this.pagenum = pagenum;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public String getProd_id() {
		return prod_id;
	}

	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAcct_type() {
		return acct_type;
	}

	public void setAcct_type(String acct_type) {
		this.acct_type = acct_type;
	}

	public String getFund_type() {
		return fund_type;
	}

	public void setFund_type(String fund_type) {
		this.fund_type = fund_type;
	}
}
