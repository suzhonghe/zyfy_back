package com.zhongyang.java.bankmanager.service;

import java.util.List;

import com.zhongyang.java.bankmanager.entity.ClearNotify;
import com.zhongyang.java.system.page.Page;

/**
 *@package com.zhongyang.java.dao
 *@filename ClearNotifyDao.java
 *@date 2017年10月18日上午9:28:01
 *@author suzh
 */
public interface ClearNotifyService {
	
	ClearNotify queryOneByParams(ClearNotify clearNotify);
	
	List<Object>queryMoreByParams(Page<Object>page);
}
