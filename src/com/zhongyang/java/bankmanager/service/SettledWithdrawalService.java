package com.zhongyang.java.bankmanager.service;

import java.util.List;

import com.zhongyang.java.bankmanager.entity.SettledWithdrawal;
import com.zhongyang.java.system.page.Page;

/**
 *@package com.zhongyang.java.dao
 *@filename SettledRechargeDao.java
 *@date 2017年10月18日上午11:23:13
 *@author suzh
 */
public interface SettledWithdrawalService {

	public int batchAddSettledWithdrawal(List<SettledWithdrawal>list);
	
	public int batchDeleteSettledWithdrawal(List<SettledWithdrawal>list);
	
	public List<SettledWithdrawal>queryMoreByParams(SettledWithdrawal params);
	
	public SettledWithdrawal queryOneByParams(SettledWithdrawal params);
	
	List<Object>queryByPage(Page<Object> page);
}
