package com.zhongyang.java.zyfyback.service;

import java.util.List;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyback.pojo.FundRecord;

/**
 *@package com.zhongyang.java.zyfyfront.service
 *@filename FundRecordService.java
 *@date 2017年7月5日上午10:43:23
 *@author suzh
 */
public interface FundRecordService {
	
	public FundRecord addFundRecord(FundRecord fundRecord)throws Exception;
	
	public void  modifyFundRecordByParams(FundRecord fundRecord)throws Exception;
	
	public FundRecord queryFundRecordByParams(FundRecord fundRecord);
	
	public int batchAddtFundRecord(List<FundRecord>list)throws Exception;
	
	public List<FundRecord> queryFundRecordByPage(Page<FundRecord>page);
	
	public List<FundRecord> queryPersonFundRecordByPage(Page<FundRecord>page);
}
