package com.zhongyang.java.bankmanager.returndata;

import java.io.Serializable;

/**
 *@package com.zhongyang.java.bankmanager.returndata
 *@filename OrderInfo.java
 *@date 2017年7月19日下午6:35:09
 *@author suzh
 */
public class OrderInfo implements Serializable{
	
	private String plat_no;
	
	private String query_order_no;
	
	private String status;
	
	private String trans_time;

	public String getPlat_no() {
		return plat_no;
	}

	public void setPlat_no(String plat_no) {
		this.plat_no = plat_no;
	}

	public String getQuery_order_no() {
		return query_order_no;
	}

	public void setQuery_order_no(String query_order_no) {
		this.query_order_no = query_order_no;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTrans_time() {
		return trans_time;
	}

	public void setTrans_time(String trans_time) {
		this.trans_time = trans_time;
	}
	
}
