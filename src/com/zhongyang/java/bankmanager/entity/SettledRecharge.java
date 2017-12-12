package com.zhongyang.java.bankmanager.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 充值对账
 *@package com.zhongyang.java.bankmanager.entity
 *@filename Settled.java
 *@date 2017年10月17日下午3:52:35
 *@author suzh
 */
public class SettledRecharge implements Serializable {

	private String id;
	
	private String plat_no;
	
	private String settled_date;
	
	private String settled_time;
	
	private String order_no;
	
	private BigDecimal trans_amt;
	
	private String trans_type;
	
	private String channel_no;
	
	private String channel_serial;

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

	public String getTrans_type() {
		return trans_type;
	}

	public void setTrans_type(String trans_type) {
		this.trans_type = trans_type;
	}

	public String getChannel_no() {
		return channel_no;
	}

	public void setChannel_no(String channel_no) {
		this.channel_no = channel_no;
	}

	public String getChannel_serial() {
		return channel_serial;
	}

	public void setChannel_serial(String channel_serial) {
		this.channel_serial = channel_serial;
	}

	@Override
	public String toString() {
		return "SettledRecharge [id=" + id + ", plat_no=" + plat_no + ", settled_date=" + settled_date
				+ ", settled_time=" + settled_time + ", order_no=" + order_no + ", trans_amt=" + trans_amt
				+ ", trans_type=" + trans_type + ", channel_no=" + channel_no + ", channel_serial=" + channel_serial
				+ "]";
	}
	
}
