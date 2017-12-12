package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.bankmanager.entity.ClearNotify;
import com.zhongyang.java.system.page.Page;

/**
 *@package com.zhongyang.java.dao
 *@filename ClearNotifyDao.java
 *@date 2017年10月18日上午9:28:01
 *@author suzh
 */
public interface ClearNotifyDao {
	
	ClearNotify selectOneByParams(ClearNotify clearNotify);
	
	List<Object>selectMoreByParams(Page<Object> page);
}
