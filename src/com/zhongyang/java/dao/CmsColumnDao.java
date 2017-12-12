package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.zyfyback.pojo.CmsColumn;

/**
 * 
 *@package com.zhongyang.java.dao
 *@filename CmsColumnDao.java
 *@date 2017年7月25日下午6:11:55
 *@author suzh
 */
public interface CmsColumnDao {
	
	int insertCmsColumn(CmsColumn cmsColumn);
	
	int updateByParams(CmsColumn cmsColumn);
	
	int deleteByParams(CmsColumn cmsColumn);
	
	List<CmsColumn> selectByParams(CmsColumn cmsColumn);
	
}
