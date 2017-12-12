package com.zhongyang.java.zyfyback.params;

import java.io.Serializable;

import com.zhongyang.java.zyfyback.pojo.Role;

public class RoleParams implements Serializable{
	
	private String empId;

	private Role role;
	
	private String priviliges;
	
	private boolean flag;
	
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String getPriviliges() {
		return priviliges;
	}
	public void setPriviliges(String priviliges) {
		this.priviliges = priviliges;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
}
