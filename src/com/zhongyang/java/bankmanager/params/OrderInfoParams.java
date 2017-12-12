package com.zhongyang.java.bankmanager.params;

import java.io.Serializable;

/**
 *@package com.zhongyang.java.bankmanager.params
 *@filename OrderInfoParams.java
 *@date 2017年7月19日下午6:24:32
 *@author suzh
 */
public class OrderInfoParams implements Serializable{
	
	private String order_no;
	
	private String partner_trans_date;
	
	private String partner_trans_time;
	
	private String query_order_no;

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getPartner_trans_date() {
		return partner_trans_date;
	}

	public void setPartner_trans_date(String partner_trans_date) {
		this.partner_trans_date = partner_trans_date;
	}

	public String getPartner_trans_time() {
		return partner_trans_time;
	}

	public void setPartner_trans_time(String partner_trans_time) {
		this.partner_trans_time = partner_trans_time;
	}

	public String getQuery_order_no() {
		return query_order_no;
	}

	public void setQuery_order_no(String query_order_no) {
		this.query_order_no = query_order_no;
	}
	
}
