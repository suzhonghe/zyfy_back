package com.zhongyang.java.bankmanager.service;

import java.util.List;

import com.zhongyang.java.bankmanager.entity.BmOrder;

/**
 * 
 *@package com.zhongyang.java.bankmanager.service
 *@filename BmAccountOrderService.java
 *@date 20172017年6月26日下午5:03:29
 *@author suzh
 */
public interface BmOrderService {

	public BmOrder bmOrderPersistence(BmOrder order)throws Exception;
	
	public void bmOrderModify(BmOrder order)throws Exception;
	
	public BmOrder queryBmAccountOrderByParams(BmOrder bmOrder);
	
	public List<BmOrder>queryOrdersByStatus();
}
