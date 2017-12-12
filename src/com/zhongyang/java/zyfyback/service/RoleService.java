package com.zhongyang.java.zyfyback.service;

import java.util.List;
import java.util.Map;

import com.zhongyang.java.zyfyback.pojo.Role;



public interface RoleService {

	public List<Role> queryAllRoles();
	
	public Role queryRoleByParams(Role role);
	
	List<Role> queryEmployeeRole(Map<String,String> map);	
	
	public int addRole(Role role);
	
	void modifyRoleByParams(Role role);
	
	/*
	
	public Role selectByPrimarykey(String id);*/
}
