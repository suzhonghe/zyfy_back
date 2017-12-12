package com.zhongyang.java.zyfyback.service;

import java.util.List;

import com.zhongyang.java.zyfyback.pojo.Organization;

/**
 * 
* @Title: OrganizationService.java 
* @Package com.zhongyang.java.service 
* @Description:机构业务接口
* @author 苏忠贺   
* @date 2016年3月9日 下午4:34:54 
* @version V1.0
 */
public interface OrganizationService {

	public int addOrganization(Organization organization);
	
	public void modifyOrganization(Organization organization);
	
	public Organization queryOrganizationByParams(Organization organization);
	
	public List<Organization> queryAllOrganizations(Organization organization);
}
