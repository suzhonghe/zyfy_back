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
import com.zhongyang.java.zyfyback.biz.RoleBiz;
import com.zhongyang.java.zyfyback.params.RoleParams;
import com.zhongyang.java.zyfyback.pojo.Role;
import com.zhongyang.java.zyfyback.returndata.EmployeeRoleReturn;
import com.zhongyang.java.zyfyback.returndata.RoleAuthInit;
@CrossOrigin
@Controller
public class RoleController extends BaseController{
	
	@Autowired
	private RoleBiz roleBiz;
	
	/**
	 * 
	* @Title: authRole 
	* @Description:角色权限初始化以及对应角色下员工列表
	* @return Map    返回类型 
	* @throws
	 */

	@FireAuthority(authorities=Authorities.ROLEAUTHINIT)
	@RequestMapping(value="/back/emp/authRoleInit",method=RequestMethod.POST)
	public @ResponseBody RoleAuthInit authRole(){
		return roleBiz.roleAuthInit();
		
	}

	/**
	 * 
	* @Title: addRole 
	* @Description:添加角色 
	* @return Message    返回类型 
	* @throws
	 */
	@FireAuthority(authorities=Authorities.ROLEADD)
	@RequestMapping(value="/back/emp/addRole",method=RequestMethod.POST)
	public @ResponseBody RoleAuthInit addRole(@RequestBody RoleParams params){
		return roleBiz.addRole(params);
	}
	
	/**
	 * 
	* @Title: modifyRoleAuths 
	* @Description:修改角色权限
	* @return Message    返回类型 
	* @throws
	 */
	@FireAuthority(authorities=Authorities.ROLEAUTHUPD)
	@RequestMapping(value="/back/emp/modifyRoleAuths",method=RequestMethod.POST)
	public @ResponseBody RoleAuthInit modifyRoleAuths(@RequestBody RoleParams params){
		return roleBiz.modifyRoleAuths(params);
	}
	/**
	 * 
	* @Title: getRoleAuthsDetails 
	* @Description:获取角色权限详情
	* @return RoleAuthInit    返回类型 
	* @throws
	 */
	@FireAuthority(authorities=Authorities.ROLEAUTH)
	@RequestMapping(value="/back/emp/getRoleAuthsDetails",method=RequestMethod.POST)
	public @ResponseBody RoleAuthInit getRoleAuthsDetails(@RequestBody Role role){
		return roleBiz.getRoleAuthsDetails(role);
	}
	/**
	 * 
	* @Title: getUserRoles 
	* @Description:获取员工角色
	* @return Map    返回类型 
	* @throws
	 */
	@RequestMapping(value="/back/emp/getEmployeeRoles")
	public @ResponseBody EmployeeRoleReturn getEmployeeRoles(@RequestBody RoleParams params){
		return roleBiz.getEmployeeRoles(params);
	}
}
