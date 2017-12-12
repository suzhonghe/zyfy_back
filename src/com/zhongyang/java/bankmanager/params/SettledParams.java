package com.zhongyang.java.bankmanager.params;

import java.io.Serializable;

import com.zhongyang.java.bankmanager.entity.SettledRecharge;
import com.zhongyang.java.bankmanager.entity.SettledRefundDetail;
import com.zhongyang.java.bankmanager.entity.SettledWithdrawal;
import com.zhongyang.java.system.page.Page;

/**
 *@package com.zhongyang.java.bankmanager.params
 *@filename SettledParams.java
 *@date 2017年10月18日下午1:37:29
 *@author suzh
 */
public class SettledParams implements Serializable{
	
	private String settledNo;//对账编号 1充值对账 2提现对账 3退票对账
	
	private String paycheck_date;//对账日期
	
	private String begin_time;//开始时间
	
	private String end_time;//结束时间
	
	private Page<Object>page;
	
	public String getSettledNo() {
		return settledNo;
	}

	public void setSettledNo(String settledNo) {
		this.settledNo = settledNo;
	}

	public Page<Object> getPage() {
		return page;
	}

	public void setPage(Page<Object> page) {
		this.page = page;
	}

	public String getPaycheck_date() {
		return paycheck_date;
	}

	public void setPaycheck_date(String paycheck_date) {
		this.paycheck_date = paycheck_date;
	}

	public String getBegin_time() {
		return begin_time;
	}

	public void setBegin_time(String begin_time) {
		this.begin_time = begin_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	
}
