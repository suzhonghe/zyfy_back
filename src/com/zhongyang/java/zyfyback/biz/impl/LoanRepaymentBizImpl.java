package com.zhongyang.java.zyfyback.biz.impl;

import java.security.GeneralSecurityException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.uitl.DESTextCipher;
import com.zhongyang.java.system.uitl.FormatUtils;
import com.zhongyang.java.system.uitl.Message;
import com.zhongyang.java.system.uitl.Validator;
import com.zhongyang.java.zyfyback.biz.LoanRepaymentBiz;
import com.zhongyang.java.zyfyback.params.LoanRepaymentParams;
import com.zhongyang.java.zyfyback.returndata.LoanRepayDetail;
import com.zhongyang.java.zyfyback.returndata.LoanRepaymentReturn;
import com.zhongyang.java.zyfyback.service.LoanRepaymentService;

/**
 *@package com.zhongyang.java.zyfyback.controller
 *@filename LoanRepaymentBizImpl.java
 *@date 2017年7月21日下午4:45:44
 *@author suzh
 */
@Service
public class LoanRepaymentBizImpl implements LoanRepaymentBiz {
	
	private static Logger logger=Logger.getLogger(LoanRepaymentBizImpl.class);
	
	@Autowired
	private LoanRepaymentService loanRepaymentService;
	
	@Override
	public LoanRepaymentReturn searchLoanRepaymentList(LoanRepaymentParams params) {
		LoanRepaymentReturn lr=new LoanRepaymentReturn();
		try{
			if(params==null||params.getPage()==null)
				throw new UException(SystemEnum.PARAMS_ERROR,"参数错误");
			String str=null;
			if(params.getPage().getParams().containsKey("loanUser")) 
				str = params.getPage().getParams().get("loanUser").toString();
			
			DESTextCipher cipher=DESTextCipher.getDesTextCipher();
			if(str!=null&&Validator.isMobile(str))
				try {
					params.getPage().getParams().put("mobile", cipher.encrypt(str));
				} catch (GeneralSecurityException e) {
					e.printStackTrace();
					logger.info("手机号加密异常");
					logger.info(e.fillInStackTrace());
					throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION,"手机号加密异常");
				}
			else
				params.getPage().getParams().put("userName", str);
			
			String flag=null;
			if(params.getPage().getParams().containsKey("unrepay"))
				flag=params.getPage().getParams().get("unrepay").toString();
			
			if(flag!=null &&"yes".equals(flag)&&(params.getPage().getStrStartTime()==null||"".equals(params.getPage().getStrStartTime()))){
				Date date=new Date();
		        Calendar calendar = Calendar.getInstance();
		        calendar.setTime(date);
		        calendar.add(Calendar.DAY_OF_YEAR, 1);
				params.getPage().setStrStartTime(FormatUtils.simpleFormat(calendar.getTime()));
			}
			if(flag!=null &&"no".equals(flag)&&(params.getPage().getStrEndTime()==null||"".equals(params.getPage().getStrEndTime()))){
				Date date=new Date();
		        Calendar calendar = Calendar.getInstance();
		        calendar.setTime(date);
		        calendar.add(Calendar.DAY_OF_YEAR, -1);
				params.getPage().setStrEndTime(FormatUtils.simpleFormat(calendar.getTime()));
			}
			
			List<LoanRepayDetail> res = loanRepaymentService.queryLoanReoaymentByPage(params.getPage());
			for (LoanRepayDetail loanRepayDetail : res) {
				loanRepayDetail.setStrStatus(loanRepayDetail.getStatus().getKey());
			}
			params.getPage().setResults(res);
			lr.setPage(params.getPage());
			lr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"还款计划查询成功"));
			
		}catch(UException e){
			logger.info("还款计划查询失败");
			logger.info(e.fillInStackTrace());
			lr.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return lr;
	}
}
