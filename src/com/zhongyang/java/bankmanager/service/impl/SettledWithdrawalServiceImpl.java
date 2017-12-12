package com.zhongyang.java.bankmanager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.bankmanager.entity.SettledWithdrawal;
import com.zhongyang.java.bankmanager.service.SettledWithdrawalService;
import com.zhongyang.java.dao.SettledWithdrawalDao;
import com.zhongyang.java.system.page.Page;

/**
 *@package com.zhongyang.java.bankmanager.service.impl
 *@filename SettledWithdrawalServiceImpl.java
 *@date 2017年10月18日下午1:30:01
 *@author suzh
 */
@Service
public class SettledWithdrawalServiceImpl implements SettledWithdrawalService {

	@Autowired
	private SettledWithdrawalDao settledWithdrawalDao;
	
	@Override
	public int batchAddSettledWithdrawal(List<SettledWithdrawal> list) {
		return settledWithdrawalDao.batchInsertSettledWithdrawal(list);
	}

	@Override
	public int batchDeleteSettledWithdrawal(List<SettledWithdrawal> list) {
		return settledWithdrawalDao.batchDeleteSettledWithdrawal(list);
	}

	@Override
	public List<SettledWithdrawal> queryMoreByParams(SettledWithdrawal params) {
		return settledWithdrawalDao.selectMoreByParams(params);
	}

	@Override
	public SettledWithdrawal queryOneByParams(SettledWithdrawal params) {
		return settledWithdrawalDao.selectOneByParams(params);
	}

	@Override
	public List<Object> queryByPage(Page<Object> page) {
		return settledWithdrawalDao.selectByPage(page);
	}

}
