package com.zhongyang.java.bankmanager.biz;

import javax.servlet.http.HttpServletRequest;

import com.zhongyang.java.bankmanager.entity.UntredtedApplication;
import com.zhongyang.java.bankmanager.params.PlatformManagerParams;
import com.zhongyang.java.bankmanager.params.TransAmountParams;
import com.zhongyang.java.bankmanager.returndata.PlatformManagerReturn;

/**
 *@package com.zhongyang.java.bankmanager.biz
 *@filename PlatformManagerBiz.java
 *@date 2017年7月7日上午10:54:08
 *@author suzh
 */
public interface PlatformManagerBiz {

	public PlatformManagerReturn companyCharge(HttpServletRequest request,PlatformManagerParams params);

	public PlatformManagerReturn companyWithdraw(HttpServletRequest request,PlatformManagerParams params);

	public PlatformManagerReturn platformAccountConverse(PlatformManagerParams params);

	public PlatformManagerReturn platToPersonApply(HttpServletRequest request,TransAmountParams params);

	public PlatformManagerReturn platToPersonCancle(HttpServletRequest request,UntredtedApplication application);

	public PlatformManagerReturn platToPersonList(HttpServletRequest request);
	
	public PlatformManagerReturn platToPersonAffirm(HttpServletRequest request,UntredtedApplication application);
}
