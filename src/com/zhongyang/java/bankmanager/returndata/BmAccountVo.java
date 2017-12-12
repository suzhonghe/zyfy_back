package com.zhongyang.java.bankmanager.returndata;

/**
 *@package com.zhongyang.java.bankmanager.returndata
 *@filename BmAccountVo.java
 *@date 2017年8月9日下午4:52:40
 *@author suzh
 */
public class BmAccountVo {
	
	private String platcust;
	
	private String status;
	
	private String available_status;
	
	private String Is_card_bind;

	public String getPlatcust() {
		return platcust;
	}

	public void setPlatcust(String platcust) {
		this.platcust = platcust;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAvailable_status() {
		return available_status;
	}

	public void setAvailable_status(String available_status) {
		this.available_status = available_status;
	}

	public String getIs_card_bind() {
		return Is_card_bind;
	}

	public void setIs_card_bind(String is_card_bind) {
		Is_card_bind = is_card_bind;
	}
	
}
