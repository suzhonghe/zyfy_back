package com.zhongyang.java.zyfyback.biz.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.uitl.Message;
import com.zhongyang.java.zyfyback.biz.InvestBiz;
import com.zhongyang.java.zyfyback.params.InvestParams;
import com.zhongyang.java.zyfyback.returndata.InvestDetail;
import com.zhongyang.java.zyfyback.returndata.InvestReturn;
import com.zhongyang.java.zyfyback.service.InvestService;

/**
 *@package com.zhongyang.java.zyfyback.biz.impl
 *@filename InvestBizImpl.java
 *@date 2017年7月21日上午10:51:13
 *@author suzh
 */
@Service
public class InvestBizImpl implements InvestBiz {

	private static Logger logger=Logger.getLogger(InvestBizImpl.class);
	
	@Autowired
	private InvestService investService;

	@Override
	public InvestReturn searchInvestRecordByParams(InvestParams params) {
		InvestReturn ir=new InvestReturn();
		try{
			if(params==null||params.getPage()==null)
				throw new UException(SystemEnum.PARAMS_ERROR,"参数错误");
			
			List<InvestDetail> investDetails = investService.queryInvestByPage(params.getPage());
			
			for (InvestDetail investDetail : investDetails) {
				investDetail.setStrStatus(investDetail.getStatus().getKey());
				investDetail.setStrMethod(investDetail.getMethod().getKey());
			}
			params.getPage().setResults(investDetails);
			ir.setPage(params.getPage());
			ir.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"投资记录查询成功"));
		}catch(UException e){
			logger.info("投资记录查询失败");
			logger.info(e.fillInStackTrace());
			ir.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		
		return ir;
	}

}
