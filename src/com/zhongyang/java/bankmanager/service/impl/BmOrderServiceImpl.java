package com.zhongyang.java.bankmanager.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhongyang.java.bankmanager.entity.BmOrder;
import com.zhongyang.java.bankmanager.service.BmOrderService;
import com.zhongyang.java.dao.BmOrderDao;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.enumtype.OrderStatus;
import com.zhongyang.java.system.uitl.ApplicationBean;
import com.zhongyang.java.system.uitl.FormatUtils;
import com.zhongyang.java.system.uitl.GetUUID;

/**
 *@package com.zhongyang.java.bankmanager.service.impl
 *@filename BmAccountOrderServiceImpl.java
 *@date 20172017年6月26日下午5:04:00
 *@author suzh
 */
@Service
public class BmOrderServiceImpl implements BmOrderService {

	private static Logger logger=Logger.getLogger(BmOrderServiceImpl.class);

	@Autowired
	private BmOrderDao bmOrderDao;
	
	@Transactional
	public BmOrder bmOrderPersistence(BmOrder order)throws Exception{
		Date date = new Date();
		ApplicationBean application = new ApplicationBean();
		order.setId(GetUUID.getUniqueKey());
		order.setOrderId(application.orderId());
		order.setOrderDate(FormatUtils.simpleFormat(date));
		order.setOrderTime(FormatUtils.timeFormat(date));
		order.setOrderStatus(OrderStatus.PROCESSING);
		logger.info("订单详情：" + order.toString());
		logger.info("订单入库,订单号：" + order.getOrderId());
		try{
			bmOrderDao.insertBmOrder(order);
		}catch(Exception e){
			logger.info("订单入库异常"+e.fillInStackTrace());
			Thread.sleep(100);
			logger.info("订单入库第二次操作");
			try{
				bmOrderDao.insertBmOrder(order);
			}catch(Exception e1){
				throw new Exception();
			}
		}
		return order;
	}
	/**
	 * 订单信息修改
	 *@date 2017年6月29日下午4:54:54
	 *@param order
	 *@throws UException
	 *@author suzh
	 */
	@Transactional
	public void bmOrderModify(BmOrder order)throws Exception{
				
		logger.info("修改订单状态：" + order.getOrderId() + order.getOrderStatus());
		try{
			bmOrderDao.updateBmOrderByParams(order);
		}catch(Exception e){
			logger.info("修改订单状态异常"+e.fillInStackTrace());
			Thread.sleep(100);
			logger.info("修改订单状态第二次操作");
			try{
				bmOrderDao.updateBmOrderByParams(order);
			}catch(Exception e1){
				throw new Exception();
			}
		}
	}

	
	@Override
	public BmOrder queryBmAccountOrderByParams(BmOrder bmOrder) {
		return bmOrderDao.selectBmOrderByParams(bmOrder);
	}
	@Override
	public List<BmOrder> queryOrdersByStatus() {
		return bmOrderDao.selectOrdersByStatus();
	}

}
