package com.zhongyang.java.zyfyback.params;

import java.io.Serializable;

/**
 *@package com.zhongyang.java.zyfyback.params
 *@filename TimerParams.java
 *@date 2017年8月24日上午9:56:34
 *@author suzh
 */
public class TimerParams implements Serializable{
	
	private String loanId;
	
	private String publishedTime;

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getPublishedTime() {
		return publishedTime;
	}

	public void setPublishedTime(String publishedTime) {
		this.publishedTime = publishedTime;
	}
	
}
