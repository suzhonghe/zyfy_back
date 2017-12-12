package com.zhongyang.java.zyfyback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.system.authority.Authorities;
import com.zhongyang.java.system.authority.FireAuthority;
import com.zhongyang.java.zyfyback.biz.OrganizationBiz;
import com.zhongyang.java.zyfyback.pojo.Organization;
import com.zhongyang.java.zyfyback.returndata.ReturnOrganization;
@CrossOrigin
@Controller
public class OrganizationController extends BaseController{
	
	@Autowired
	private OrganizationBiz organizationBiz;
	
	@FireAuthority(authorities=Authorities.ORGADD)
	@RequestMapping(value="/back/org/addOrganization",method=RequestMethod.POST)
	public @ResponseBody ReturnOrganization addOrganization(@RequestBody Organization organization){
		return organizationBiz.addOrganization(organization);
	}
	
	@FireAuthority(authorities=Authorities.ORGMODIFY)
	@RequestMapping(value="/back/org/modifyOrganization",method=RequestMethod.POST)
	public @ResponseBody ReturnOrganization modifyOrganization(@RequestBody Organization organization){
		return organizationBiz.modifyOrganization(organization);
	}
	
	@RequestMapping(value="/back/org/queryOrganizationByParams",method=RequestMethod.POST)
	public @ResponseBody ReturnOrganization queryOrganizationByParams(@RequestBody Organization organization){
		return organizationBiz.queryOrganizationByParams(organization);
	}
	
	@FireAuthority(authorities=Authorities.ORGMANAGE)
	@RequestMapping(value="/back/org/queryAllOrganizations",method=RequestMethod.POST)
	public @ResponseBody ReturnOrganization queryAllOrganizations(){
		return organizationBiz.queryAllOrganizations();
	}
}
