package com.zhongyang.java.zyfyback.biz;

import com.zhongyang.java.zyfyback.pojo.CmsColumn;
import com.zhongyang.java.zyfyback.returndata.CmsReturn;

/**
 * 
* @Title: CmsColumnBiz.java 
* @Package com.zhongyang.java.biz 
* @Description:栏目biz
* @author 苏忠贺   
* @date 2016年3月2日 下午4:25:03 
* @version V1.0
 */
public interface CmsColumnBiz {
	
	public CmsReturn addCmsColumn(CmsColumn cmsColumn);
	
	public CmsReturn modifyByParams(CmsColumn cmsColumn);
	
	public CmsReturn deleteByParams(CmsColumn cmsColumn);
	
	public CmsReturn queryByParams(CmsColumn cmsColumn);
	
}
