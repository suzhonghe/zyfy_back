package com.zhongyang.java.bankmanager.biz;

import com.zhongyang.java.bankmanager.params.BmSearchParams;
import com.zhongyang.java.bankmanager.params.OrderInfoParams;
import com.zhongyang.java.bankmanager.params.PlatcustFundDetailParams;
import com.zhongyang.java.bankmanager.params.RepayDetailParams;
import com.zhongyang.java.bankmanager.returndata.BmSearchManager;

/**
 *@package com.zhongyang.java.bankmanager.biz
 *@filename BmSearchBiz.java
 *@date 2017年7月19日上午10:45:42
 *@author suzh
 */
public interface BmSearchBiz {
	
	public BmSearchManager seachPlatcustFundDetail(PlatcustFundDetailParams params);
	
	public BmSearchManager searchAccountInfo(BmSearchParams params);
	
	public BmSearchManager searchRepayDetail(RepayDetailParams params);
	
	public BmSearchManager searchLoanInvest(BmSearchParams params);
	
	public BmSearchManager searchLoanInfo(BmSearchParams params);
	
	public BmSearchManager searchAccountBalance(BmSearchParams params);

	public BmSearchManager searchLoanAccountBalance(BmSearchParams params);
	
	public BmSearchManager searchOrderStatus(OrderInfoParams params);
	
	public BmSearchManager searchChargeOrder(BmSearchParams params);

	public BmSearchManager searchPlatCust(BmSearchParams params);
}
