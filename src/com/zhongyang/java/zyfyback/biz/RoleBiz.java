package com.zhongyang.java.zyfyback.biz;

import com.zhongyang.java.zyfyback.params.RoleParams;
import com.zhongyang.java.zyfyback.pojo.Role;
import com.zhongyang.java.zyfyback.returndata.EmployeeRoleReturn;
import com.zhongyang.java.zyfyback.returndata.RoleAuthInit;

public interface RoleBiz {
	
	public RoleAuthInit roleAuthInit();
	
	public RoleAuthInit addRole(RoleParams params);
	
	public RoleAuthInit modifyRoleAuths(RoleParams params);
	
	public RoleAuthInit getRoleAuthsDetails(Role role);
	
	public EmployeeRoleReturn getEmployeeRoles(RoleParams params);
}
