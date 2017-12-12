package com.zhongyang.java.bankmanager.params;

import java.io.Serializable;

/**
 *@package com.zhongyang.java.bankmanager.params
 *@filename PlatcustFundDetailParams.java
 *@date 2017年7月19日上午10:48:08
 *@author suzh
 */
public class PlatcustFundDetailParams implements Serializable{
	
	private String order_no;//选填
	
	private String platcust ;
	
	private String start_date;//查询起始时间(YYYY-MM-DD HH:mm:ss)选填
	
	private String end_date;//查询结束时间(YYYY-MM-DD HH:mm:ss)
	
	private String trans_name;//交易名称(提现、标的出账、债权转让、标的成立、标的废弃、标的出账、标的还款、借款人还款、充值、投融资转换、转账)选填
	
	private int pagesize=10; 
	
	private int pagenum=1;
	
	private String acct_type;
	
	public String getAcct_type() {
		return acct_type;
	}

	public void setAcct_type(String acct_type) {
		this.acct_type = acct_type;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getPlatcust() {
		return platcust;
	}

	public void setPlatcust(String platcust) {
		this.platcust = platcust;
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

	public String getTrans_name() {
		return trans_name;
	}

	public void setTrans_name(String trans_name) {
		this.trans_name = trans_name;
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
