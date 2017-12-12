package com.zhongyang.java.bankmanager.params;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *@package com.zhongyang.java.bankmanager.params
 *@filename PayOut.java
 *@date 2017年7月14日下午5:06:15
 *@author suzh
 */
public class PayOut implements Serializable{
	
	private String payout_plat_type;
	
	private BigDecimal payout_amt;
	
	public String getPayout_plat_type() {
		return payout_plat_type;
	}
	public void setPayout_plat_type(String payout_plat_type) {
		this.payout_plat_type = payout_plat_type;
	}
	public BigDecimal getPayout_amt() {
		return payout_amt;
	}
	public void setPayout_amt(BigDecimal payout_amt) {
		this.payout_amt = payout_amt;
	}
	
}
