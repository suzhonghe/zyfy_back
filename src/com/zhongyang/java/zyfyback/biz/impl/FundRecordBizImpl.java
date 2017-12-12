package com.zhongyang.java.zyfyback.biz.impl;

import java.security.GeneralSecurityException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.uitl.DESTextCipher;
import com.zhongyang.java.system.uitl.FormatUtils;
import com.zhongyang.java.system.uitl.Message;
import com.zhongyang.java.zyfyback.biz.FundRecordBiz;
import com.zhongyang.java.zyfyback.params.FundRecordParams;
import com.zhongyang.java.zyfyback.pojo.FundRecord;
import com.zhongyang.java.zyfyback.returndata.FundRecordReturn;
import com.zhongyang.java.zyfyback.service.FundRecordService;

/**
 *@package com.zhongyang.java.zyfyback.biz.impl
 *@filename FundRecordBizImpl.java
 *@date 2017年7月21日上午9:49:52
 *@author suzh
 */
@Service
public class FundRecordBizImpl implements FundRecordBiz {
	
	private static Logger logger=Logger.getLogger(FundRecordBizImpl.class);

	@Autowired
	private FundRecordService fundRecordService;

	@Override
	public FundRecordReturn searchFundRecordByPage(FundRecordParams params) {
		FundRecordReturn fr=new FundRecordReturn();
		try{
			if(params==null||params.getPage()==null)
				throw new UException(SystemEnum.PARAMS_ERROR,"参数错误");
			if(params.getPage().getStrStartTime()!=null&&!"".equals(params.getPage().getStrStartTime()))
				params.getPage().setStartTime(FormatUtils.millisFormat(params.getPage().getStrStartTime()+" 00:00:00"));
			
			if(params.getPage().getStrEndTime() !=null&&!"".equals(params.getPage().getStrEndTime()))
				params.getPage().setEndTime(FormatUtils.millisFormat(params.getPage().getStrEndTime()+" 23:59:59"));
			
			List<FundRecord> fundRecords = fundRecordService.queryFundRecordByPage(params.getPage());
			for (FundRecord fundRecord : fundRecords) {
				fundRecord.setStrStatus(fundRecord.getStatus().getKey());
				fundRecord.setStrType(fundRecord.getType().getKey());
			}
			params.getPage().setResults(fundRecords);
			fr.setPage(params.getPage());
			fr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"分页查询资金记录成功"));
			
		}catch(UException e){
			logger.info("分页查询资金记录失败");
			logger.info(e.fillInStackTrace());
			fr.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return fr;
	}

	@Override
	public FundRecordReturn searchPersonFundRecordByPage(FundRecordParams params) {
		FundRecordReturn fr=new FundRecordReturn();
		try{
			if(params==null||params.getPage()==null)
				throw new UException(SystemEnum.PARAMS_ERROR,"参数错误");
			if(params.getPage().getStrStartTime()!=null&&!"".equals(params.getPage().getStrStartTime()))
				params.getPage().setStartTime(FormatUtils.millisFormat(params.getPage().getStrStartTime()+" 00:00:00"));
			
			if(params.getPage().getStrEndTime() !=null&&!"".equals(params.getPage().getStrEndTime()))
				params.getPage().setEndTime(FormatUtils.millisFormat(params.getPage().getStrEndTime()+" 23:59:59"));
			DESTextCipher cipher=DESTextCipher.getDesTextCipher();
			if(params.getPage().getParams().containsKey("mobile")&&!"".equals(params.getPage().getParams().get("mobile").toString())){
				String mobile=params.getPage().getParams().get("mobile").toString();
				try {
					mobile=cipher.encrypt(mobile);
				} catch (GeneralSecurityException e) {
					e.printStackTrace();
					throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION,"数据加密异常");
				}
				params.getPage().getParams().put("mobile", mobile);
			}
			
			
			
			List<FundRecord> fundRecords = fundRecordService.queryPersonFundRecordByPage(params.getPage());
			for (FundRecord fundRecord : fundRecords) {
				fundRecord.setStrStatus(fundRecord.getStatus().getKey());
				fundRecord.setStrType(fundRecord.getType().getKey());
				if(fundRecord.getMobile()!=null){
					try {
						fundRecord.setMobile(cipher.decrypt(fundRecord.getMobile()));
					} catch (GeneralSecurityException e) {
						e.printStackTrace();
						throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION,"数据解密异常");
					}
				}
			}
			params.getPage().setResults(fundRecords);
			fr.setPage(params.getPage());
			fr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"分页查询资金记录成功"));
			
		}catch(UException e){
			logger.info("分页查询个人资金记录失败");
			logger.info(e.fillInStackTrace());
			fr.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return fr;
	}
	
	
	
}
