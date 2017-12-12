package com.zhongyang.java.zyfyback.biz;

import javax.servlet.http.HttpServletRequest;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyback.params.EmployeeParams;
import com.zhongyang.java.zyfyback.pojo.Employee;
import com.zhongyang.java.zyfyback.returndata.EmployeeReturn;

public interface EmployeeBiz {

	
	public EmployeeReturn empLogin(HttpServletRequest req, EmployeeParams params);
	
	public EmployeeReturn addEmployee(HttpServletRequest request, EmployeeParams params);
	
	public EmployeeReturn empModifyInfo(HttpServletRequest request,EmployeeParams params);
	
	public EmployeeReturn getEmpList(HttpServletRequest request, Page<Employee> page);
	
	public EmployeeReturn dispatchEmpRole(EmployeeParams params);
	
	public EmployeeReturn isLogin(HttpServletRequest request);
	
}
