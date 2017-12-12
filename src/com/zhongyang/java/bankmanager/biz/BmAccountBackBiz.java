package com.zhongyang.java.bankmanager.biz;

import javax.servlet.http.HttpServletRequest;

import com.zhongyang.java.bankmanager.params.BmAccountParams;
import com.zhongyang.java.bankmanager.returndata.BmAccountReturn;

/**
 *@package com.zhongyang.java.bankmanager.biz
 *@filename BmAccountBiz.java
 *@date 2017年7月20日上午10:47:48
 *@author suzh
 */
public interface BmAccountBackBiz {
	
	public BmAccountReturn modifyMobile(HttpServletRequest request,BmAccountParams params);
}
