package com.zhongyang.java.bankmanager.biz;

import com.zhongyang.java.bankmanager.entity.ClearNotify;
import com.zhongyang.java.bankmanager.entity.NotifyRefund;
import com.zhongyang.java.bankmanager.params.BmCallBackParams;
import com.zhongyang.java.bankmanager.returndata.BmCallBackReturn;

/**
 *@package com.zhongyang.java.bankmanager.biz
 *@filename BmCallBack.java
 *@date 2017年10月17日下午1:54:12
 *@author suzh
 */
public interface BmCallBackBiz {
	
	/**
	 * 退票查询
	 *@date 上午9:33:19
	 *@param params
	 *@return
	 *@author suzh
	 */
	public BmCallBackReturn searchCallBackRefund(BmCallBackParams params);
	
	/**
	 * 清算回调查询
	 *@date 上午9:33:40
	 *@param params
	 *@return
	 *@author suzh
	 */
	public BmCallBackReturn searchCallBackClear(BmCallBackParams params);

}
