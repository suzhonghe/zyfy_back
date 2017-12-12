package com.zhongyang.java.bankmanager.biz;

import com.zhongyang.java.bankmanager.params.SettledParams;
import com.zhongyang.java.bankmanager.returndata.SettledReturn;

/**
 *@package com.zhongyang.java.bankmanager.biz
 *@filename SettledAccountBiz.java
 *@date 2017年10月18日下午1:32:36
 *@author suzh
 */
public interface SettledAccountBiz {
	
	public SettledReturn settledRecharge(SettledParams params);
	
	public SettledReturn settledRefundDetail(SettledParams params);
	
	public SettledReturn settledWithdrawal(SettledParams params);
	
	public SettledReturn searchRecharge(SettledParams params);
	
	public SettledReturn searchWithdrawal(SettledParams params);
	
	public SettledReturn searchRefundDetail(SettledParams params);
}
