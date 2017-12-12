package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.zyfyback.pojo.LoanTransRecord;

/**
 *@package com.zhongyang.java.dao
 *@filename LoanTransRecordDao.java
 *@date 2017年7月14日下午3:01:12
 *@author suzh
 */
public interface LoanTransRecordDao {

	public int insertLoanTransRecord(LoanTransRecord record);
	
	public int updateLoanTransRecord(LoanTransRecord record);
	
	public LoanTransRecord selectByParams(LoanTransRecord record);
	
	public int batchInsertRecords(List<LoanTransRecord> list);
}
