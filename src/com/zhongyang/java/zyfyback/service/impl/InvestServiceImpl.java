package com.zhongyang.java.zyfyback.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhongyang.java.dao.InvestDao;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyback.pojo.Invest;
import com.zhongyang.java.zyfyback.pojo.LoanRepayment;
import com.zhongyang.java.zyfyback.pojo.User;
import com.zhongyang.java.zyfyback.returndata.InvestDetail;
import com.zhongyang.java.zyfyback.service.InvestService;

/**
 *@package com.zhongyang.java.zyfyback.service.impl
 *@filename InvestServiceImpl.java
 *@date 2017年7月4日下午1:51:57
 *@author suzh
 */
@Service
public class InvestServiceImpl implements InvestService {
	
	private static Logger logger=Logger.getLogger(InvestServiceImpl.class);

	@Autowired
	private InvestDao investDao;
	
	@Override
	public List<Invest> queryInvestsByParams(Invest invest) {
		return investDao.selectInvestsByparams(invest);
	}

	@Override
	@Transactional
	public int modifyInvestStatusSettled(Invest invest)throws Exception {
		int res=0;
		try{
			res=investDao.updateInvestStatusSettled(invest);
		}catch(Exception e){
			logger.info("修改投资记录异常"+e.fillInStackTrace());
			Thread.sleep(100);
			logger.info("修改投资记录第二次操作");
			try{
				res=investDao.updateInvestStatusSettled(invest);
			}catch(Exception e1){
				throw new Exception();
			}
		}
		return res;
	}

	@Override
	@Transactional
	public int modifyInvestStatusCleared(Invest invest)throws Exception {
		int res=0;
		try{
			res=investDao.updateInvestStatusCleared(invest);
		}catch(Exception e){
			logger.info("修改投资记录异常"+e.fillInStackTrace());
			Thread.sleep(100);
			logger.info("修改投资记录第二次操作");
			try{
				res=investDao.updateInvestStatusCleared(invest);
			}catch(Exception e1){
				throw new Exception();
			}
		}
		return res;
	}

	@Override
	public int queryCountByParams(String userId) {
		return investDao.selectCountByParams(userId);
	}

	@Override
	public List<InvestDetail> queryInvestByPage(Page<InvestDetail> page) {
		return investDao.selectInvestByPage(page);
	}

	@Override
	@Transactional
	public int modifyInvest(Invest invest)throws Exception {
		int res=0;
		try{
			res=investDao.updateInvest(invest);
		}catch(Exception e){
			logger.info("修改投资记录异常"+e.fillInStackTrace());
			Thread.sleep(100);
			logger.info("修改投资记录第二次操作");
			try{
				res=investDao.updateInvest(invest);
			}catch(Exception e1){
				throw new Exception();
			}
		}
		return res;
	}

	@Override
	public List<User> queryUserInvestRepayment(LoanRepayment loanRepayment) {
		return investDao.selectUserInvestRepayment(loanRepayment);
	}

}
