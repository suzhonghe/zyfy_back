package com.zhongyang.java.bankmanager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.bankmanager.entity.SettledRecharge;
import com.zhongyang.java.bankmanager.service.SettledRechargeService;
import com.zhongyang.java.dao.SettledRechargeDao;
import com.zhongyang.java.system.page.Page;

/**
 *@package com.zhongyang.java.bankmanager.service.impl
 *@filename SettledRechargeServiceImpl.java
 *@date 2017年10月18日下午1:22:47
 *@author suzh
 */
@Service
public class SettledRechargeServiceImpl implements SettledRechargeService {

	@Autowired
	private SettledRechargeDao settledRechargeDao;
	
	@Override
	public int batchAddSettledRecharge(List<SettledRecharge> list) {
		return settledRechargeDao.batchInsertSettledRecharge(list);
	}

	
	@Override
	public int batchDeleteSettledRecharge(List<SettledRecharge> list) {
		return settledRechargeDao.batchDeleteSettledRecharge(list);
	}

	
	@Override
	public List<SettledRecharge> queryMoreByParams(SettledRecharge params) {
		return settledRechargeDao.selectMoreByParams(params);
	}

	
	@Override
	public SettledRecharge queryOneByParams(SettledRecharge params) {
		return settledRechargeDao.selectOneByParams(params);
	}


	@Override
	public List<Object> queryByPage(Page<Object> page) {
		return settledRechargeDao.selectByPage(page);
	}
}
