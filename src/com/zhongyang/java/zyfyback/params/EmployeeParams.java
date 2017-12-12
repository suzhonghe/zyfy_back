package com.zhongyang.java.zyfyback.params;

import java.io.Serializable;
import java.util.List;

import com.zhongyang.java.zyfyback.pojo.Employee;
import com.zhongyang.java.zyfyback.pojo.Role;

public class EmployeeParams implements Serializable{
	
	private Employee emp;
	
	private EmpLoginParams empLoginParams;
	
	private List<Role> roles;
	
	private List<String> roleIds;

	public Employee getEmp() {
		return emp;
	}

	public void setEmp(Employee emp) {
		this.emp = emp;
	}

	public EmpLoginParams getEmpLoginParams() {
		return empLoginParams;
	}

	public void setEmpLoginParams(EmpLoginParams empLoginParams) {
		this.empLoginParams = empLoginParams;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<String> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}
	
}
