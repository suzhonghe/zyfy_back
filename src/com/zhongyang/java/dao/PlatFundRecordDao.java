package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyback.pojo.PlatFundRecord;

/**
 *@package com.zhongyang.java.dao
 *@filename PlatFundRecordDao.java
 *@date 2017年7月7日下午1:07:14
 *@author suzh
 */
public interface PlatFundRecordDao {
	
	public int insertPlatFundRecord(PlatFundRecord record);
	
	public int updatePlatFundRecord(PlatFundRecord record);
	
	public List<PlatFundRecord> selectByPage(Page<PlatFundRecord> page);
	
	public PlatFundRecord selectByParams(PlatFundRecord record);
	
	public int batchInsertRecords(List<PlatFundRecord>list);
}
