package com.zhongyang.java.zyfyback.service;

import java.util.List;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyback.pojo.PlatFundRecord;

/**
 *@package com.zhongyang.java.service
 *@filename PlatFundRecordService.java
 *@date 2017年7月7日下午2:37:51
 *@author suzh
 */
public interface PlatFundRecordService {

	public PlatFundRecord addPlatFundRecord(PlatFundRecord record)throws Exception;
	
	public void modifyPlatFundRecord(PlatFundRecord record)throws Exception;
	
	public List<PlatFundRecord> queryByPage(Page<PlatFundRecord> page);
	
	public PlatFundRecord queyByParams(PlatFundRecord record);
	
	public int batchAddRecords(List<PlatFundRecord>list)throws Exception;
}
