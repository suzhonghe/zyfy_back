package com.zhongyang.java.zyfyback.params;

import java.io.Serializable;

public class EmpDispatchRoleParams implements Serializable{
	
	private String empId;
	
	private String roleId;
	
	private String orgId;

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

}
