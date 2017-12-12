package com.zhongyang.java.zyfyback.service;

import java.util.List;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyback.pojo.Employee;
import com.zhongyang.java.zyfyback.pojo.Role;

public interface EmployeeService {

	public Employee queryEmployeeByParams(Employee emp);
	
	public void modifyEmployeeByParams(Employee emp);
	
	public int addEmployee(Employee emp);
	
	List<Employee> queryEmployeeByRole(Role role);
	
	List<Employee> queryAllEmployees(Page<Employee> page);
	
}
