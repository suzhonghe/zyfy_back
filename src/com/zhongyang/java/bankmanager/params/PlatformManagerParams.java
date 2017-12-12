package com.zhongyang.java.bankmanager.params;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *@package com.zhongyang.java.bankmanager.params
 *@filename PlatformManagerParams.java
 *@date 2017年8月9日下午12:11:18
 *@author suzh
 */
public class PlatformManagerParams implements Serializable{

	private BigDecimal amount;
	
	private String source_account;
	
	private String dest_account;
	
	public String getSource_account() {
		return source_account;
	}

	public void setSource_account(String source_account) {
		this.source_account = source_account;
	}

	public String getDest_account() {
		return dest_account;
	}

	public void setDest_account(String dest_account) {
		this.dest_account = dest_account;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
}
