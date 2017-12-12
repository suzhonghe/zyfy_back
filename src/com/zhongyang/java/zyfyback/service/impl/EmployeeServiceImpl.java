package com.zhongyang.java.zyfyback.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.EmployeeDao;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyback.pojo.Employee;
import com.zhongyang.java.zyfyback.pojo.Role;
import com.zhongyang.java.zyfyback.service.EmployeeService;
@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeDao employeeDao;

	@Override
	public Employee queryEmployeeByParams(Employee emp) {
		return employeeDao.selectEmployeeByParams(emp);
	}

	@Override
	public void modifyEmployeeByParams(Employee emp) {
		employeeDao.updateEmployeeByParams(emp);
	}

	@Override
	public int addEmployee(Employee emp) {
		return employeeDao.insertEmployee(emp);
	}

	@Override
	public List<Employee> queryEmployeeByRole(Role role) {
		return employeeDao.selectEmployeeByRole(role);
	}

	@Override
	public List<Employee> queryAllEmployees(Page<Employee> page) {
		return employeeDao.selectAllEmployees(page);
	}
}
