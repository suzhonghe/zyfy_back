package com.zhongyang.java.zyfyback.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhongyang.java.dao.LoanDao;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyback.pojo.Loan;
import com.zhongyang.java.zyfyback.returndata.ManagerData;
import com.zhongyang.java.zyfyback.service.LoanService;
import com.zhongyang.java.zyfyback.vo.LoanListVo;

/**
 * 
 * @Title: LoanServiceImpl.java
 * @Package com.zhongyang.java.service.impl
 * @Description:标的业务实现类
 * @author 苏忠贺
 * @date 2015年12月4日 上午9:05:01
 * @version V1.0
 */
@Service
public class LoanServiceImpl implements LoanService {
	
	private static Logger logger=Logger.getLogger(LoanServiceImpl.class);

	@Autowired
	private LoanDao loanDao;

	@Override
	public List<Loan> queryLoanByParams(Loan loan) {
		return loanDao.selectLoanByParams(loan);

	}
	
	@Override 
	@Transactional
	public int addLoan(Loan loan)throws Exception{
		int res=0;
		try{
			res=loanDao.insertLoan(loan);
		}catch(Exception e){
			logger.info("添加标的异常"+e.fillInStackTrace());
			Thread.sleep(100);
			logger.info("添加标的第二次操作");
			try{
				res=loanDao.insertLoan(loan);
			}catch(Exception e1){
				throw new Exception();
			}
		}
		return res;
	}

	@Override
	public List<Loan> queryLoanByPage(Page<Loan> page) {
		return loanDao.selectLoanByPage(page);
	}

	@Override
	@Transactional
	public int modifyLoanByparams(Loan loan)throws Exception {
		int res=0;
		try{
			res=loanDao.updateLoanByParams(loan);
		}catch(Exception e){
			logger.info("修改标的异常"+e.fillInStackTrace());
			Thread.sleep(100);
			logger.info("修改标的第二次操作");
			try{
				res=loanDao.updateLoanByParams(loan);
			}catch(Exception e1){
				throw new Exception();
			}
		}
		return res;
	}

	@Override
	public int queryLoanCount(String loanUserId) {
		return loanDao.selectLoanCount(loanUserId);
	}

	@Override
	public List<LoanListVo> queryLoanRecordByPage(Page<LoanListVo> page) {
		return loanDao.selectLoanRecordByPage(page);
	}

	@Override
	public BigDecimal queryResult(Map<String, Object> map) {
		return loanDao.selectResult(map);
	}
	
	@Override
	public ManagerData queryLoanAmount(Date date) {
		return loanDao.selectLoanAmount(date);
	}

	@Override
	public Integer queryLoanPerson(Date date) {
		return loanDao.selectLoanPerson(date);
	}

	@Override
	public Integer queryInPerson(Date date) {
		return loanDao.selectInPerson(date);
	}

	@Override
	public Integer queryLimitDays(Date date) {
		return loanDao.selectLimitDays(date);
	}

	@Override
	public Double queryRate(Date date) {
		return loanDao.selectRate(date);
	}

	@Override
	public ManagerData queryAmountByDate(Date timeSettle, String start, String end) {
		return loanDao.selectAmountByDate(timeSettle, start, end);
	}
	
}
