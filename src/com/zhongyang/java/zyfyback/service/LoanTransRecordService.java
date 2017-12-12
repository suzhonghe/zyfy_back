package com.zhongyang.java.zyfyback.service;

import java.util.List;

import com.zhongyang.java.zyfyback.pojo.LoanTransRecord;

/**
 *@package com.zhongyang.java.zyfyfront.service
 *@filename LoanTransRecordService.java
 *@date 2017年7月14日下午3:13:22
 *@author suzh
 */
public interface LoanTransRecordService {
	
	public int addLoanTransRecord(LoanTransRecord record)throws Exception;
	
	public int modifyLoanTransRecord(LoanTransRecord record)throws Exception;
	
	public LoanTransRecord queryByParams(LoanTransRecord record);
	
	public int batchAddRecords(List<LoanTransRecord> list)throws Exception;

}
