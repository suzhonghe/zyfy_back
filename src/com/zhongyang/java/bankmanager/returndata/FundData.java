package com.zhongyang.java.bankmanager.returndata;

import java.io.Serializable;
import java.util.List;

/**
 *@package com.zhongyang.java.bankmanager.returndata
 *@filename FundData.java
 *@date 2017年7月17日下午3:38:22
 *@author suzh
 */
public class FundData implements Serializable{
	
	private List<CustRepayList>custRepayList;

	public List<CustRepayList> getCustRepayList() {
		return custRepayList;
	}

	public void setCustRepayList(List<CustRepayList> custRepayList) {
		this.custRepayList = custRepayList;
	}
	

}
