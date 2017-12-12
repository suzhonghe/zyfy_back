package com.zhongyang.java.bankmanager.returndata;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *@package com.zhongyang.java.bankmanager.returndata
 *@filename LoanInvestDetail.java
 *@date 2017年7月19日下午4:32:40
 *@author suzh
 */
public class LoanInvestDetail implements Serializable{
	
	private BigDecimal vol;//	O	交易份额
	
	private String trans_date;//	O	交易日期
	
	private String trans_time;//	O	交易时间
	
	private String platcust;//	O	平台客户编号
	
	private String name;//	O	客户名称
	
	private String prod_name;//	O	产品名称
	
	private String plat_name;//	O	平台名称
	
	private String in_interest;//	O	加息利率

	public BigDecimal getVol() {
		return vol;
	}

	public void setVol(BigDecimal vol) {
		this.vol = vol;
	}

	public String getTrans_date() {
		return trans_date;
	}

	public void setTrans_date(String trans_date) {
		this.trans_date = trans_date;
	}

	public String getTrans_time() {
		return trans_time;
	}

	public void setTrans_time(String trans_time) {
		this.trans_time = trans_time;
	}

	public String getPlatcust() {
		return platcust;
	}

	public void setPlatcust(String platcust) {
		this.platcust = platcust;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProd_name() {
		return prod_name;
	}

	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}

	public String getPlat_name() {
		return plat_name;
	}

	public void setPlat_name(String plat_name) {
		this.plat_name = plat_name;
	}

	public String getIn_interest() {
		return in_interest;
	}

	public void setIn_interest(String in_interest) {
		this.in_interest = in_interest;
	}

}
