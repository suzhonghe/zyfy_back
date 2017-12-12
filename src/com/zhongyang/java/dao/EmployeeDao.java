package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyback.pojo.Employee;
import com.zhongyang.java.zyfyback.pojo.Role;

public interface EmployeeDao {
	
	Employee selectEmployeeByParams(Employee emp);
	
	void updateEmployeeByParams(Employee emp);
	
	int insertEmployee(Employee emp);
	
	List<Employee> selectEmployeeByRole(Role role);
	
	List<Employee> selectAllEmployees(Page<Employee> page);
}