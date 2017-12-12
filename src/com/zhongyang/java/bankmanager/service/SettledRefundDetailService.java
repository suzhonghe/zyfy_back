package com.zhongyang.java.bankmanager.service;

import java.util.List;

import com.zhongyang.java.bankmanager.entity.SettledRefundDetail;
import com.zhongyang.java.system.page.Page;

/**
 *@package com.zhongyang.java.dao
 *@filename SettledRefundDao.java
 *@date 2017年10月18日上午10:34:15
 *@author suzh
 */
public interface SettledRefundDetailService {

	public int batchAddSettledRefundDetail(List<SettledRefundDetail>list);
	
	public int batchDeleteSettledRefundDetail(List<SettledRefundDetail>list);
	
	public List<SettledRefundDetail>queryMoreByParams(SettledRefundDetail params);
	
	public SettledRefundDetail queryOneByParams(SettledRefundDetail params);
	
	List<Object>queryByPage(Page<Object> page);
}
