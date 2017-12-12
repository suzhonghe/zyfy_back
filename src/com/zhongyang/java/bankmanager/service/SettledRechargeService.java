package com.zhongyang.java.bankmanager.service;

import java.util.List;

import com.zhongyang.java.bankmanager.entity.SettledRecharge;
import com.zhongyang.java.system.page.Page;

/**
 *@package com.zhongyang.java.dao
 *@filename SettledRechargeDao.java
 *@date 2017年10月18日上午11:23:13
 *@author suzh
 */
public interface SettledRechargeService {

	public int batchAddSettledRecharge(List<SettledRecharge>list);
	
	public int batchDeleteSettledRecharge(List<SettledRecharge>list);
	
	public List<SettledRecharge>queryMoreByParams(SettledRecharge params);
	
	public SettledRecharge queryOneByParams(SettledRecharge params);
	
	List<Object>queryByPage(Page<Object> page);
}
