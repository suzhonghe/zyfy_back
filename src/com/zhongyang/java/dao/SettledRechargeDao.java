package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.bankmanager.entity.SettledRecharge;
import com.zhongyang.java.system.page.Page;

/**
 *@package com.zhongyang.java.dao
 *@filename SettledRechargeDao.java
 *@date 2017年10月18日上午11:23:13
 *@author suzh
 */
public interface SettledRechargeDao {

	int batchInsertSettledRecharge(List<SettledRecharge>list);
	
	int batchDeleteSettledRecharge(List<SettledRecharge>list);
	
	List<SettledRecharge>selectMoreByParams(SettledRecharge params);
	
	SettledRecharge selectOneByParams(SettledRecharge params);
	
	List<Object>selectByPage(Page<Object> page);
}
