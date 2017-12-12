package com.zhongyang.java.zyfyback.service;

import java.util.List;

import com.zhongyang.java.zyfyback.pojo.CmsColumn;
/**
 * 
 *@package com.zhongyang.java.zyfyback.service
 *@filename CmsColumnService.java
 *@date 2017年7月25日下午6:11:47
 *@author suzh
 */
public interface CmsColumnService {
	
	public int addCmsColumn(CmsColumn cmsColumn);
	
	public int modifyByParams(CmsColumn cmsColumn);
	
	public int deleteByParams(CmsColumn cmsColumn);
	
	public List<CmsColumn> queryByParams(CmsColumn cmsColumn);
	
}
