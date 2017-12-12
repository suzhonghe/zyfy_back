package com.zhongyang.java.zyfyback.returndata;

import java.util.List;

import com.zhongyang.java.system.uitl.Message;

/**
 *@package com.zhongyang.java.zyfyback.returndata
 *@filename ReturnManagerData.java
 *@date 2017年9月11日上午10:04:02
 *@author suzh
 */
public class ReturnManagerData {
	
	private ManagerData managerData;
	
	private List<ManagerData>data;//截止到2018年3月未来N个月当月资金流量
	
	private Message message;
	
	public List<ManagerData> getData() {
		return data;
	}

	public void setData(List<ManagerData> data) {
		this.data = data;
	}

	public ManagerData getManagerData() {
		return managerData;
	}

	public void setManagerData(ManagerData managerData) {
		this.managerData = managerData;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}
	
}
