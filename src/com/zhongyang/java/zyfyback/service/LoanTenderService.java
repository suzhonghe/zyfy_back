 package com.zhongyang.java.zyfyback.service;

import com.zhongyang.java.zyfyback.pojo.LoanTender;

/**
 *@package com.zhongyang.java.zyfyback.service
 *@filename LoanTenderService.java
 *@date 2017年7月17日上午11:44:53
 *@author suzh
 */
public interface LoanTenderService {
	
	int addLoanTender(LoanTender loanTender)throws Exception;
	
	int modifyLoanTenderByParams(LoanTender loanTender)throws Exception;
	
	LoanTender queryByParams(LoanTender loanTender);

}
