package com.zhongyang.java.zyfyback.biz;

import com.zhongyang.java.zyfyback.params.LoanRepaymentParams;
import com.zhongyang.java.zyfyback.returndata.LoanRepaymentReturn;

/**
 *@package com.zhongyang.java.zyfyback.biz
 *@filename LoanRepaymentBiz.java
 *@date 2017年7月21日下午4:44:24
 *@author suzh
 */
public interface LoanRepaymentBiz {
	
	public LoanRepaymentReturn searchLoanRepaymentList(LoanRepaymentParams params);
}
