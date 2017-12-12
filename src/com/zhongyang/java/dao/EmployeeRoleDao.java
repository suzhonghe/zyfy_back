package com.zhongyang.java.dao;

import com.zhongyang.java.zyfyback.pojo.EmployeeRole;

public interface EmployeeRoleDao {
	
	EmployeeRole selectEmpRoleByParams(EmployeeRole empRole);
	
	int insertEmployeeRole(EmployeeRole empRole);
	
	int deleteEmpRoleByParams(EmployeeRole empRole);
}