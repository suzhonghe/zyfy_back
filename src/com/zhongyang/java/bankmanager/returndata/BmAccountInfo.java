package com.zhongyang.java.bankmanager.returndata;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *@package com.zhongyang.java.bankmanager.returndata
 *@filename BmAccountInfo.java
 *@date 2017年7月19日下午2:39:36
 *@author suzh
 */
public class BmAccountInfo implements Serializable{
	
	private String account;
	
	private String platCust;

	private BigDecimal frozen_amount;
	
	private BigDecimal balance;
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPlatCust() {
		return platCust;
	}

	public void setPlatCust(String platCust) {
		this.platCust = platCust;
	}

	public BigDecimal getFrozen_amount() {
		return frozen_amount;
	}

	public void setFrozen_amount(BigDecimal frozen_amount) {
		this.frozen_amount = frozen_amount;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
}
