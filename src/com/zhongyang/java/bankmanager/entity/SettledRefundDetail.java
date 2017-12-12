package com.zhongyang.java.bankmanager.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 退票明细对账
 *@package com.zhongyang.java.bankmanager.entity
 *@filename SettledRefundDetail.java
 *@date 2017年10月17日下午3:59:20
 *@author suzh
 */
public class SettledRefundDetail implements Serializable{

	private String id;
	
	private String plat_no;
	
	private String refund_date;
	
	private String refund_time;
	
	private String order_no;
	
	private BigDecimal trans_amt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlat_no() {
		return plat_no;
	}

	public void setPlat_no(String plat_no) {
		this.plat_no = plat_no;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public BigDecimal getTrans_amt() {
		return trans_amt;
	}

	public void setTrans_amt(BigDecimal trans_amt) {
		this.trans_amt = trans_amt;
	}

	public String getRefund_date() {
		return refund_date;
	}

	public void setRefund_date(String refund_date) {
		this.refund_date = refund_date;
	}

	public String getRefund_time() {
		return refund_time;
	}

	public void setRefund_time(String refund_time) {
		this.refund_time = refund_time;
	}


}
