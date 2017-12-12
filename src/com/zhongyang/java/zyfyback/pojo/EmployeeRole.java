package com.zhongyang.java.zyfyback.pojo;

import java.io.Serializable;

public class EmployeeRole implements Serializable {
	
    private String roleId;

    private String empId;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}
    
}