package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.bankmanager.entity.NotifyRefund;
import com.zhongyang.java.system.page.Page;

/**
 *@package com.zhongyang.java.dao
 *@filename NotifyRefundDao.java
 *@date 2017年10月18日上午9:49:16
 *@author suzh
 */
public interface NotifyRefundDao {
	
	NotifyRefund selectOneByParams(NotifyRefund notifyRefund);
	
	List<Object>selectMoreByParams(Page<Object> page);
}
