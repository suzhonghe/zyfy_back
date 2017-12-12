package com.zhongyang.java.zyfyback.returndata;

import java.io.Serializable;
import java.util.List;

import com.zhongyang.java.system.uitl.Message;
import com.zhongyang.java.zyfyback.pojo.Employee;
import com.zhongyang.java.zyfyback.pojo.Role;

public class EmployeeRoleReturn implements Serializable{
	
	private Employee emp;
	
	private List<Role> roles;
	
	private List<Role> empRoles;
	
	private Message message;

	public List<Role> getEmpRoles() {
		return empRoles;
	}

	public void setEmpRoles(List<Role> empRoles) {
		this.empRoles = empRoles;
	}

	public Employee getEmp() {
		return emp;
	}

	public void setEmp(Employee emp) {
		this.emp = emp;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

}
