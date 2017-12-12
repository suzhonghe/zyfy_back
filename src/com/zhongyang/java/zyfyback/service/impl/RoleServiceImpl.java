package com.zhongyang.java.zyfyback.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.RoleDao;
import com.zhongyang.java.zyfyback.pojo.Role;
import com.zhongyang.java.zyfyback.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;
	
	@Override
	public List<Role> queryAllRoles() {
		return roleDao.selectAllRoles();
	}

	@Override
	public Role queryRoleByParams(Role role) {
		return roleDao.selectRoleByParams(role);
	}

	@Override
	public List<Role> queryEmployeeRole(Map<String,String> map) {
		return roleDao.selectEmployeeRole(map);
	}
	
	public int addRole(Role role) {
		return roleDao.insertRole(role);
	}

	@Override
	public void modifyRoleByParams(Role role) {
		roleDao.updateRoleByParams(role);
	}
}
