package com.zhongyang.java.zyfyback.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhongyang.java.dao.LoanRepaymentDao;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyback.pojo.LoanRepayment;
import com.zhongyang.java.zyfyback.returndata.LoanRepayDetail;
import com.zhongyang.java.zyfyback.service.LoanRepaymentService;

/**
 *@package com.zhongyang.java.zyfyback.service.impl
 *@filename LoanRepaymentServiceImpl.java
 *@date 2017年7月4日下午2:05:34
 *@author suzh
 */
@Service
public class LoanRepaymentServiceImpl implements LoanRepaymentService {
	
	private static Logger logger=Logger.getLogger(LoanRepaymentServiceImpl.class);

	@Autowired
	private LoanRepaymentDao loanRepaymentDao;
	
	@Override
	public List<LoanRepayment> queryLoanRepaymentsByParams(LoanRepayment params) {
		return loanRepaymentDao.selectLoanRepaymentsByParams(params);
	}

	@Override
	@Transactional
	public int batchAddLoanRepayment(List<LoanRepayment> list)throws Exception {
		int res=0;
		try{
			res=loanRepaymentDao.batchInsertLoanRepayment(list);
		}catch(Exception e){
			logger.info("批量添加还款计划异常"+e.fillInStackTrace());
			Thread.sleep(100);
			logger.info("批量添加还款计划第二次操作");
			try{
				res=loanRepaymentDao.batchInsertLoanRepayment(list);
			}catch(Exception e1){
				throw new Exception();
			}
		}
		return res;
	}

	@Override
	@Transactional
	public int modifyLoanRepayment(LoanRepayment loanRepayment)throws Exception {
		int res=0;
		try{
			res=loanRepaymentDao.updateLoanRepayment(loanRepayment);
		}catch(Exception e){
			logger.info("修改还款计划异常"+e.fillInStackTrace());
			Thread.sleep(100);
			logger.info("修改还款计划第二次操作");
			try{
				res=loanRepaymentDao.updateLoanRepayment(loanRepayment);
			}catch(Exception e1){
				throw new Exception();
			}
		}
		return res;
	}

	@Override
	public List<LoanRepayDetail> queryLoanReoaymentByPage(Page<LoanRepayDetail> page) {
		return loanRepaymentDao.selectLoanReoaymentByPage(page);
	}

	@Override
	@Transactional
	public int modifyStatusByTime(String status)throws Exception {
		int res=0;
		try{
			res=loanRepaymentDao.updateStatusByTime(status);
		}catch(Exception e){
			logger.info("修改还款计划异常"+e.fillInStackTrace());
			Thread.sleep(100);
			logger.info("修改还款计划第二次操作");
			try{
				res=loanRepaymentDao.updateStatusByTime(status);
			}catch(Exception e1){
				throw new Exception();
			}
		}
		return res;
	}

}
