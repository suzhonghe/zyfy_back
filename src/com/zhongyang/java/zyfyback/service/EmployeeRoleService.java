package com.zhongyang.java.zyfyback.service;

import com.zhongyang.java.zyfyback.pojo.EmployeeRole;


public interface EmployeeRoleService {

	public EmployeeRole queryEmployeeRoleByParams(EmployeeRole empRole);
	
	public int addEmployeeRole(EmployeeRole empRole);
	
	public int deleteEmpRoleByParams(EmployeeRole empRole);
}
