package com.zhongyang.java.zyfyback.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.EmployeeRoleDao;
import com.zhongyang.java.zyfyback.pojo.EmployeeRole;
import com.zhongyang.java.zyfyback.service.EmployeeRoleService;
@Service
public class EmployeeRoleServiceImpl implements EmployeeRoleService {
	
	@Autowired
	private EmployeeRoleDao employeeRoleDao;

	@Override
	public EmployeeRole queryEmployeeRoleByParams(EmployeeRole empRole) {
		return employeeRoleDao.selectEmpRoleByParams(empRole);
	}

	@Override
	public int addEmployeeRole(EmployeeRole empRole) {
		return employeeRoleDao.insertEmployeeRole(empRole);
	}

	@Override
	public int deleteEmpRoleByParams(EmployeeRole empRole) {
		return employeeRoleDao.deleteEmpRoleByParams(empRole);
	}

}
