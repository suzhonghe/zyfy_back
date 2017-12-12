package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyback.pojo.FundRecord;

/**
 * 
 *@package com.zhongyang.java.dao
 *@filename FundRecordDao.java
 *@date 2017年7月5日上午9:58:09
 *@author suzh
 */
public interface FundRecordDao {
	
	public int insertFundRecord(FundRecord fundRecord);
	
	public int  updateFundRecordByParams(FundRecord fundRecord);
	
	public FundRecord selectFundRecordByParams(FundRecord fundRecord);
	
	public int batchInsertFundRecord(List<FundRecord>list);
	
	public List<FundRecord> selectFundRecordByPage(Page<FundRecord>page);
	
	public List<FundRecord> selectPersonFundRecordByPage(Page<FundRecord> page);
}
