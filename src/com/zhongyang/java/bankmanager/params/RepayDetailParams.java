package com.zhongyang.java.bankmanager.params;

import java.io.Serializable;

/**
 *@package com.zhongyang.java.bankmanager.params
 *@filename RepayDetailParams.java
 *@date 2017年7月19日下午3:28:18
 *@author suzh
 */
public class RepayDetailParams implements Serializable{
	
	private String mobile;
	
	private String type;
	
	private String prod_id;
	
	private String start_date;
	
	private String end_date;
	
	private int pagesize=10;  
	
	private int pagenum=1;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProd_id() {
		return prod_id;
	}

	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getPagenum() {
		return pagenum;
	}

	public void setPagenum(int pagenum) {
		this.pagenum = pagenum;
	}
	
}
