package com.zhongyang.java.zyfyback.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhongyang.java.dao.InvestRepaymentDao;
import com.zhongyang.java.zyfyback.pojo.InvestRepayment;
import com.zhongyang.java.zyfyback.pojo.LoanRepayment;
import com.zhongyang.java.zyfyback.service.InvestRepaymentService;

/**
 *@package com.zhongyang.java.zyfyback.service.impl
 *@filename InvestRepaymentServiceImpl.java
 *@date 2017年7月17日下午1:26:22
 *@author suzh
 */
@Service
public class InvestRepaymentServiceImpl implements InvestRepaymentService {
	
	private static Logger logger=Logger.getLogger(InvestRepaymentServiceImpl.class);
	
	@Autowired
	private InvestRepaymentDao investRepaymentDao;

	@Override
	@Transactional
	public int addInvestRepayment(InvestRepayment repayment)throws Exception {
		int res=0;
		try{
			res=investRepaymentDao.insertInvestRepayment(repayment);
		}catch(Exception e){
			logger.info("添加回款计划异常"+e.fillInStackTrace());
			Thread.sleep(100);
			logger.info("添加回款计划第二次操作");
			try{
				res=investRepaymentDao.insertInvestRepayment(repayment);
			}catch(Exception e1){
				throw new Exception();
			}
		}
		return res;
	}

	@Override
	@Transactional
	public int modifyInvestRepaymentByParams(InvestRepayment repayment)throws Exception {
		int res=0;
		try{
			res=investRepaymentDao.updateInvestRepaymentByParams(repayment);
		}catch(Exception e){
			logger.info("修改回款计划异常"+e.fillInStackTrace());
			Thread.sleep(100);
			logger.info("修改回款计划第二次操作");
			try{
				res=investRepaymentDao.updateInvestRepaymentByParams(repayment);
			}catch(Exception e1){
				throw new Exception();
			}
		}
		return res;
	}

	@Override
	public List<InvestRepayment> queryInvestRepaymentByParams(InvestRepayment repayment) {
		return investRepaymentDao.selectInvestRepaymentByParams(repayment);
	}

	@Override
	public int batchAddInvestRepayment(List<InvestRepayment> list)throws Exception {
		int res=0;
		try{
			res=investRepaymentDao.batchInsertInvestRepayment(list);
		}catch(Exception e){
			logger.info("批量添加回款计划异常"+e.fillInStackTrace());
			Thread.sleep(100);
			logger.info("批量添加回款计划第二次操作");
			try{
				res=investRepaymentDao.batchInsertInvestRepayment(list);
			}catch(Exception e1){
				throw new Exception();
			}
		}
		return res;
	}

	@Override
	public List<InvestRepayment> queryByLoanRepayment(LoanRepayment loanRepayment) {
		return investRepaymentDao.selectByLoanRepayment(loanRepayment);
	}

	@Override
	@Transactional
	public int batchModifyInvestRepayment(List<InvestRepayment> list)throws Exception {
		int res=0;
		try{
			res=investRepaymentDao.batchUpdateInvestRepayment(list);
		}catch(Exception e){
			logger.info("批量修改回款计划异常"+e.fillInStackTrace());
			Thread.sleep(100);
			logger.info("批量修改回款计划第二次操作");
			try{
				res=investRepaymentDao.batchUpdateInvestRepayment(list);
			}catch(Exception e1){
				throw new Exception();
			}
		}
		return res;
	}

}
