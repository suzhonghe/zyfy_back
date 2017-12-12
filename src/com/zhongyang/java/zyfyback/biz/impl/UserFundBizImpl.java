package com.zhongyang.java.zyfyback.biz.impl;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.uitl.Message;
import com.zhongyang.java.zyfyback.biz.UserFundBiz;
import com.zhongyang.java.zyfyback.pojo.User;
import com.zhongyang.java.zyfyback.pojo.UserFund;
import com.zhongyang.java.zyfyback.returndata.UserFundDetail;
import com.zhongyang.java.zyfyback.returndata.UserFundReturn;
import com.zhongyang.java.zyfyback.service.InvestService;
import com.zhongyang.java.zyfyback.service.LoanService;
import com.zhongyang.java.zyfyback.service.UserFundService;

/**
 *@package com.zhongyang.java.zyfyback.biz.impl
 *@filename UserFundBizImpl.java
 *@date 2017年7月21日上午9:10:31
 *@author suzh
 */
@Service
public class UserFundBizImpl implements UserFundBiz {

	private static Logger logger=Logger.getLogger(UserFundBizImpl.class);
	
	@Autowired
	private UserFundService userFundService;
	
	@Autowired
	private InvestService investService;
	
	@Autowired
	private LoanService loanService;
	
	@Override
	public UserFundReturn selectUserFundDetail(User user) {
		UserFundReturn ufr=new UserFundReturn();
		try{
			if(user==null||user.getId()==null)
				throw new UException(SystemEnum.PARAMS_ERROR,"参数错误");
			
			UserFund fund=new UserFund();
			fund.setUserId(user.getId());
			UserFund queryFund = userFundService.queryUserFundByParams(fund);
			if(queryFund==null)
				throw new UException(SystemEnum.PARAMS_ERROR,"用户不存在");
			
			BigDecimal duInAmount = userFundService.queryDuInAmount(user.getId());
			BigDecimal duOutAmount = userFundService.queryDuOutAmount(user.getId());
			
			UserFundDetail fundDetail=new UserFundDetail();
			fundDetail.setAvailableAmount(queryFund.getAvailableAmount());
			fundDetail.setDueInAmount(duInAmount==null?new BigDecimal(0):duInAmount);
			fundDetail.setDueOutAmount(duOutAmount==null?new BigDecimal(0):duOutAmount);
			fundDetail.setFrozenAmount(queryFund.getFrozenAmount());
			
			fundDetail.setInvestCount(investService.queryCountByParams(user.getId()));
			fundDetail.setLoanCount(loanService.queryLoanCount(user.getId()));
			
			ufr.setUserFundDetail(fundDetail);
			ufr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"账户明细查询成功"));
			
		}catch(UException e){
			logger.info("账户明细查询失败");
			logger.info(e.fillInStackTrace());
			ufr.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return ufr;
	}
}
