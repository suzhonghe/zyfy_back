package com.zhongyang.java.zyfyback.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhongyang.java.dao.UserFundDao;
import com.zhongyang.java.zyfyback.pojo.Invest;
import com.zhongyang.java.zyfyback.pojo.UserFund;
import com.zhongyang.java.zyfyback.service.UserFundService;
@Service
public class UserFundServiceImpl implements UserFundService {
	
	private static Logger logger=Logger.getLogger(UserFundServiceImpl.class);
	
	@Autowired
	private UserFundDao userFundDao;
	
	@Override
	@Transactional
	public void modifyUserFundByParams(UserFund userFund) throws Exception{
		logger.info("修改资金账户：" + userFund.getUserId());
		try{
			userFundDao.updateUserFundByParams(userFund);
		}catch(Exception e){
			logger.info("修改用户账户资金异常"+e.fillInStackTrace());
			Thread.sleep(100);
			logger.info("修改用户账户资金第二次操作");
			try{
				userFundDao.updateUserFundByParams(userFund);
			}catch(Exception e1){
				throw new Exception();
			}
		}
			
	}

	@Override
	public UserFund queryUserFundByParams(UserFund userFund) {
		return userFundDao.selectUserFundByParams(userFund);
	}

	@Override
	public List<UserFund> queryByInvest(Invest invest) {
		return userFundDao.selectByInvest(invest);
	}

	@Override
	@Transactional
	public int batchModifyUserFunds(List<UserFund> list)throws Exception {
		int res=0;
		try{
			res=userFundDao.batchUpdateUserFunds(list);
		}catch(Exception e){
			logger.info("批量修改用户账户资金异常"+e.fillInStackTrace());
			Thread.sleep(100);
			logger.info("批量修改用户账户资金第二次操作");
			try{
				res=userFundDao.batchUpdateUserFunds(list);
			}catch(Exception e1){
				throw new Exception();
			}
		}
		return res;
	}

	@Override
	public BigDecimal queryDuInAmount(String userId) {
		return userFundDao.selectDuInAmount(userId);
	}

	@Override
	public BigDecimal queryDuOutAmount(String userId) {
		return userFundDao.selectDuOutAmount(userId);
	}

}
