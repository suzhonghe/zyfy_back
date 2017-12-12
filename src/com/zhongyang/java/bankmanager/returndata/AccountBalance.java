package com.zhongyang.java.bankmanager.returndata;

import java.io.Serializable;

/**
 *@package com.zhongyang.java.bankmanager.returndata
 *@filename AccountBalance.java
 *@date 2017年7月19日下午5:11:44
 *@author suzh
 */
public class AccountBalance implements Serializable{

	private String balance;
	
	private String frozen_amount;
	
	public String getFrozen_amount() {
		return frozen_amount;
	}

	public void setFrozen_amount(String frozen_amount) {
		this.frozen_amount = frozen_amount;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}
	
}
