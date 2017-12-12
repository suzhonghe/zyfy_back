package com.zhongyang.java.bankmanager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.bankmanager.entity.ClearNotify;
import com.zhongyang.java.bankmanager.service.ClearNotifyService;
import com.zhongyang.java.dao.ClearNotifyDao;
import com.zhongyang.java.system.page.Page;

/**
 *@package com.zhongyang.java.bankmanager.service.impl
 *@filename ClearNotifyServiceImpl.java
 *@date 2017年10月18日上午10:05:22
 *@author suzh
 */
@Service
public class ClearNotifyServiceImpl implements ClearNotifyService {

	@Autowired
	private ClearNotifyDao clearNotifyDao;
	
	@Override
	public ClearNotify queryOneByParams(ClearNotify clearNotify) {
		return clearNotifyDao.selectOneByParams(clearNotify);
	}

	
	@Override
	public List<Object> queryMoreByParams(Page<Object> page) {
		return clearNotifyDao.selectMoreByParams(page);
	}

}
