package com.zhongyang.java.bankmanager.returndata;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *@package com.zhongyang.java.bankmanager.returndata
 *@filename FundTransDetail.java
 *@date 2017年7月19日上午11:08:57
 *@author suzh
 */
public class FundTransDetail implements Serializable{
	
	private String plat_no;
	
	private String platcust;
	
	private String trans_time;
	
	private String trans_date;
	
	private BigDecimal amt;
	
	private String amt_type;

	public String getPlat_no() {
		return plat_no;
	}

	public void setPlat_no(String plat_no) {
		this.plat_no = plat_no;
	}

	public String getPlatcust() {
		return platcust;
	}

	public void setPlatcust(String platcust) {
		this.platcust = platcust;
	}

	public String getTrans_time() {
		return trans_time;
	}

	public void setTrans_time(String trans_time) {
		this.trans_time = trans_time;
	}

	public String getTrans_date() {
		return trans_date;
	}

	public void setTrans_date(String trans_date) {
		this.trans_date = trans_date;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public String getAmt_type() {
		return amt_type;
	}

	public void setAmt_type(String amt_type) {
		this.amt_type = amt_type;
	}
	
}
