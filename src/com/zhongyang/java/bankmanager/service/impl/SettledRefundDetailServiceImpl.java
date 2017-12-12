package com.zhongyang.java.bankmanager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.bankmanager.entity.SettledRefundDetail;
import com.zhongyang.java.bankmanager.service.SettledRefundDetailService;
import com.zhongyang.java.dao.SettledRefundDetailDao;
import com.zhongyang.java.system.page.Page;

/**
 *@package com.zhongyang.java.bankmanager.service.impl
 *@filename SettledRefundDetailServiceImpl.java
 *@date 2017年10月18日下午1:25:50
 *@author suzh
 */
@Service
public class SettledRefundDetailServiceImpl implements SettledRefundDetailService {

	@Autowired
	private SettledRefundDetailDao settledRefundDetailDao;
	
	@Override
	public int batchAddSettledRefundDetail(List<SettledRefundDetail> list) {
		return settledRefundDetailDao.batchInsertSettledRefundDetail(list);
	}

	
	@Override
	public int batchDeleteSettledRefundDetail(List<SettledRefundDetail> list) {
		return settledRefundDetailDao.batchDeleteSettledRefundDetail(list);
	}

	@Override
	public List<SettledRefundDetail> queryMoreByParams(SettledRefundDetail params) {
		return settledRefundDetailDao.selectMoreByParams(params);
	}

	@Override
	public SettledRefundDetail queryOneByParams(SettledRefundDetail params) {
		return settledRefundDetailDao.selectOneByParams(params);
	}


	@Override
	public List<Object> queryByPage(Page<Object> page) {
		return settledRefundDetailDao.selectByPage(page);
	}

}
