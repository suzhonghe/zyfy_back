package com.zhongyang.java.bankmanager.biz.impl;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.bankmanager.biz.BmCallBackBiz;
import com.zhongyang.java.bankmanager.params.BmCallBackParams;
import com.zhongyang.java.bankmanager.returndata.BmCallBackReturn;
import com.zhongyang.java.bankmanager.service.ClearNotifyService;
import com.zhongyang.java.bankmanager.service.NotifyRefundService;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.uitl.Message;

/**
 *@package com.zhongyang.java.bankmanager.biz.impl
 *@filename BmCallBackBizImpl.java
 *@date 2017年10月26日上午9:34:37
 *@author suzh
 */
@Service
public class BmCallBackBizImpl implements BmCallBackBiz {
	
	private static Logger logger=Logger.getLogger(BmCallBackBizImpl.class);

	@Autowired
	private ClearNotifyService clearNotifyService;
	
	@Autowired
	private NotifyRefundService notifyRefundService;

	@Override
	public BmCallBackReturn searchCallBackRefund(BmCallBackParams params) {
		
		BmCallBackReturn cr=new BmCallBackReturn();
		try{
			if(params==null)
				throw new UException(SystemEnum.PARAMS_ERROR,"参数错误");
			List<Object> res = notifyRefundService.queryMoreByParams(params.getPage());
			params.getPage().setResults(res);
			cr.setPage(params.getPage());
			cr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"查询成功"));
			
			
		}catch(UException e){
			logger.info(e.fillInStackTrace());
			cr.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return cr;
	}

	@Override
	public BmCallBackReturn searchCallBackClear(BmCallBackParams params) {
		BmCallBackReturn cr=new BmCallBackReturn();
		try{
			if(params==null)
				throw new UException(SystemEnum.PARAMS_ERROR,"参数错误");
			List<Object> res = clearNotifyService.queryMoreByParams(params.getPage());
			params.getPage().setResults(res);
			cr.setPage(params.getPage());
			cr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"查询成功"));
			
			
		}catch(UException e){
			logger.info(e.fillInStackTrace());
			cr.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return cr;
	}

}
