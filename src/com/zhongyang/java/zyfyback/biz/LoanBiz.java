package com.zhongyang.java.zyfyback.biz;

import com.zhongyang.java.zyfyback.params.LoanParams;
import com.zhongyang.java.zyfyback.returndata.LoanReturn;

/**
 * 
* @Title: LoanBiz.java 
* @Package com.zhongyang.java.biz 
* @Description:标的业务处理接口 
* @author 苏忠贺   
* @date 2015年12月4日 上午9:08:03 
* @version V1.0
 */
public interface LoanBiz {
	
	public LoanReturn addLoan(LoanParams params);
	
	public LoanReturn searchLoanList(LoanParams params);
	
	public LoanReturn queryLoanByParams(LoanParams params);
	
	public LoanReturn modifyUnpublishedLoan(LoanParams params);
	
	public LoanReturn searchLoanRecord(LoanParams params);
}
