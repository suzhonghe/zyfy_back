package com.zhongyang.java.bankmanager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.bankmanager.entity.NotifyRefund;
import com.zhongyang.java.bankmanager.service.NotifyRefundService;
import com.zhongyang.java.dao.NotifyRefundDao;
import com.zhongyang.java.system.page.Page;

/**
 *@package com.zhongyang.java.bankmanager.service.impl
 *@filename NotifyRefundServiceImpl.java
 *@date 2017年10月18日上午10:06:39
 *@author suzh
 */
@Service
public class NotifyRefundServiceImpl implements NotifyRefundService {

	@Autowired
	private NotifyRefundDao notifyRefundDao;
		
	@Override
	public NotifyRefund queryOneByParams(NotifyRefund notifyRefund) {
		return notifyRefundDao.selectOneByParams(notifyRefund);
	}

	
	@Override
	public List<Object> queryMoreByParams(Page<Object> page) {
		return notifyRefundDao.selectMoreByParams(page);
	}

}
