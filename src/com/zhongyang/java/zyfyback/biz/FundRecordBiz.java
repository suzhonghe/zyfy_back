package com.zhongyang.java.zyfyback.biz;

import com.zhongyang.java.zyfyback.params.FundRecordParams;
import com.zhongyang.java.zyfyback.returndata.FundRecordReturn;

/**
 *@package com.zhongyang.java.zyfyback.biz
 *@filename FundRecordBiz.java
 *@date 2017年7月21日上午9:48:18
 *@author suzh
 */
public interface FundRecordBiz {
	
	public FundRecordReturn searchFundRecordByPage(FundRecordParams params);
	
	public FundRecordReturn searchPersonFundRecordByPage(FundRecordParams params);
}
