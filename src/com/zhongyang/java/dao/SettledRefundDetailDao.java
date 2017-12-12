package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.bankmanager.entity.SettledRefundDetail;
import com.zhongyang.java.system.page.Page;

/**
 *@package com.zhongyang.java.dao
 *@filename SettledRefundDao.java
 *@date 2017年10月18日上午10:34:15
 *@author suzh
 */public interface SettledRefundDetailDao {

	int batchInsertSettledRefundDetail(List<SettledRefundDetail>list);
	
	int batchDeleteSettledRefundDetail(List<SettledRefundDetail>list);
	
	List<SettledRefundDetail>selectMoreByParams(SettledRefundDetail params);
	
	SettledRefundDetail selectOneByParams(SettledRefundDetail params);
	
	List<Object>selectByPage(Page<Object> page);
}
