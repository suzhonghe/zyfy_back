package com.zhongyang.java.task;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhongyang.java.system.enumtype.LoanRepayMent;
import com.zhongyang.java.zyfyback.service.LoanRepaymentService;

@Component("loanRepayTask")
public class loanRepayTask {
	private static Logger logger = Logger.getLogger(loanRepayTask.class);
	
	@Autowired
	private LoanRepaymentService loanRepaymentService;
	
	public void loanRepaymentTask(){
		try{
			String status = LoanRepayMent.OVERDUE.toString();
			loanRepaymentService.modifyStatusByTime(status);
			logger.info("还款逾期修改状态成功！");
		}catch(Exception e){
			logger.info("还款逾期状态修改失败"+e.getMessage());
			e.printStackTrace();
		}

	}
}
