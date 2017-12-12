package com.zhongyang.java.zyfyback.biz.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.enumtype.LoanStatus;
import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.uitl.Message;
import com.zhongyang.java.task.TimerPublishedLoan;
import com.zhongyang.java.zyfyback.biz.TimerBiz;
import com.zhongyang.java.zyfyback.params.TimerParams;
import com.zhongyang.java.zyfyback.pojo.Loan;
import com.zhongyang.java.zyfyback.service.LoanService;
@Scope("prototype")
@Service
public class TimerBizImpl implements TimerBiz{
	
	private static Logger logger = Logger.getLogger(TimerBizImpl.class);
	
	@Autowired
	private TimerPublishedLoan timerPublishedLoan;
	
	@Autowired
	private LoanService loanService;
	
	@Override
	@Transactional
	public Message timerBidPublished(TimerParams params) {
		Message message=new Message();
		try {
			Timer timer=new Timer();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = dateFormat.parse(params.getPublishedTime());
			timerPublishedLoan.setLoanId(params.getLoanId());
			timer.schedule(timerPublishedLoan,date);
			Loan loan=new Loan();
			loan.setId(params.getLoanId());
			loan.setStatus(LoanStatus.SCHEDULED);
			loan.setTimeOpen(date);
			Date timeScheduled=new Date();
			loan.setTimeScheduled(timeScheduled);
			loanService.modifyLoanByparams(loan);
			message.setCode(SystemEnum.OPRARION_SUCCESS.value());
			message.setMessage("定时发布成功");
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "定时发布失败");
		}
	}

}
