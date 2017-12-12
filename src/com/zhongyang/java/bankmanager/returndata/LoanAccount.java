package com.zhongyang.java.bankmanager.returndata;

import java.io.Serializable;

/**
 *@package com.zhongyang.java.bankmanager.returndata
 *@filename LoanAccount.java
 *@date 2017年7月19日下午6:13:53
 *@author suzh
 */
public class LoanAccount implements Serializable{

	private String prod_id;
	
	private String balance;

	public String getProd_id() {
		return prod_id;
	}

	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}
}
