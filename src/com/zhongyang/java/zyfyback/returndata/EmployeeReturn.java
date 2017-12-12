package com.zhongyang.java.zyfyback.returndata;

import java.io.Serializable;
import java.util.List;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.uitl.Message;
import com.zhongyang.java.zyfyback.pojo.Employee;
import com.zhongyang.java.zyfyback.pojo.Role;

public class EmployeeReturn implements Serializable{
	
	private Message message;
	
	private Page<Employee> page;
	
	private List<Role> roles;
	
	private Employee emp;
	
	public Employee getEmp() {
		return emp;
	}

	public void setEmp(Employee emp) {
		this.emp = emp;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public Page<Employee> getPage() {
		return page;
	}

	public void setPage(Page<Employee> page) {
		this.page = page;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}
