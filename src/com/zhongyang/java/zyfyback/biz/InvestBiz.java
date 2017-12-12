package com.zhongyang.java.zyfyback.biz;

import com.zhongyang.java.zyfyback.params.InvestParams;
import com.zhongyang.java.zyfyback.returndata.InvestReturn;

/**
 *@package com.zhongyang.java.zyfyback.biz
 *@filename InvestBiz.java
 *@date 2017年7月21日上午10:49:12
 *@author suzh
 */
public interface InvestBiz {

	public InvestReturn searchInvestRecordByParams(InvestParams params);
}
