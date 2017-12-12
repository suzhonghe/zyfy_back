package com.zhongyang.java.bankmanager.params;

import java.io.Serializable;

/**
 *@package com.zhongyang.java.bankmanager.params
 *@filename BmAccountParams.java
 *@date 2017年8月11日上午9:15:29
 *@author suzh
 */
public class BmAccountParams implements Serializable{
	
	private String old_mobile;
	
	private String new_mobile;

	public String getOld_mobile() {
		return old_mobile;
	}

	public void setOld_mobile(String old_mobile) {
		this.old_mobile = old_mobile;
	}

	public String getNew_mobile() {
		return new_mobile;
	}

	public void setNew_mobile(String new_mobile) {
		this.new_mobile = new_mobile;
	}

}
