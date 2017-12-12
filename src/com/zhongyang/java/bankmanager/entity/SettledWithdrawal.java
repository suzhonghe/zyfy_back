package com.zhongyang.java.bankmanager.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 提现对账
 *@package com.zhongyang.java.bankmanager.entity
 *@filename SettledWithdrawal.java
 *@date 2017年10月17日下午4:03:04
 *@author suzh
 */
public class SettledWithdrawal implements Serializable{

	private String id;
	
	private String plat_no;
	
	private String settled_date;
	
	private String settled_time;
	
	private String order_no;
	
	private BigDecimal trans_amt;
	
	private String result;

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

	public String getSettled_date() {
		return settled_date;
	}

	public void setSettled_date(String settled_date) {
		this.settled_date = settled_date;
	}

	public String getSettled_time() {
		return settled_time;
	}

	public void setSettled_time(String settled_time) {
		this.settled_time = settled_time;
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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "SettledWithdrawal [id=" + id + ", plat_no=" + plat_no + ", order_no=" + order_no + ", trans_amt="
				+ trans_amt + ", result=" + result + "]";
	}
	
}
