package com.zhongyang.java.zyfyback.biz;

import com.zhongyang.java.zyfyback.params.PlatFormParams;
import com.zhongyang.java.zyfyback.returndata.PlatFormReturn;

/**
 *@package com.zhongyang.java.zyfyback.biz
 *@filename PlatFormBiz.java
 *@date 2017年7月24日上午9:01:58
 *@author suzh
 */
public interface PlatFormBiz {
	
	public PlatFormReturn  searchPlatFormRecord(PlatFormParams params);

}
