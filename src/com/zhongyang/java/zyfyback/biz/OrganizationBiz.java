package com.zhongyang.java.zyfyback.biz;

import com.zhongyang.java.zyfyback.pojo.Organization;
import com.zhongyang.java.zyfyback.returndata.ReturnOrganization;

/**
 * 
* @Title: OrganizationBiz.java 
* @Package com.zhongyang.java.biz 
* @Description:机构biz
* @author 苏忠贺   
* @date 2016年3月9日 下午4:39:21 
* @version V1.0
 */
public interface OrganizationBiz {

	public ReturnOrganization addOrganization(Organization organization);
	
	public ReturnOrganization modifyOrganization(Organization organization);
	
	public ReturnOrganization queryOrganizationByParams(Organization Organization);
	
	public ReturnOrganization queryAllOrganizations();
}
