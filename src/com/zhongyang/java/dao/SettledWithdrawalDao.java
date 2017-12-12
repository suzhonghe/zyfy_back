package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.bankmanager.entity.SettledWithdrawal;
import com.zhongyang.java.system.page.Page;

/**
 *@package com.zhongyang.java.dao
 *@filename SettledRechargeDao.java
 *@date 2017年10月18日上午11:23:13
 *@author suzh
 */
public interface SettledWithdrawalDao {

	int batchInsertSettledWithdrawal(List<SettledWithdrawal>list);
	
	int batchDeleteSettledWithdrawal(List<SettledWithdrawal>list);
	
	List<SettledWithdrawal>selectMoreByParams(SettledWithdrawal params);
	
	SettledWithdrawal selectOneByParams(SettledWithdrawal params);
	
	List<Object>selectByPage(Page<Object> page);
}
