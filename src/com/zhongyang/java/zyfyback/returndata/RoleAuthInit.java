package com.zhongyang.java.zyfyback.returndata;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.zhongyang.java.system.uitl.Message;
import com.zhongyang.java.zyfyback.pojo.Employee;
import com.zhongyang.java.zyfyback.pojo.Role;
import com.zhongyang.java.zyfyback.pojo.SysAuthorities;

/**
 * 
* @Title: RoleAuthInit.java 
* @Package com.zhongyang.java.zyfyback.returndata 
* @Description:角色权限初始化返回值
* @author 苏忠贺   
* @date 2017年6月7日 下午2:53:59 
* @version V1.0
 */
public class RoleAuthInit implements Serializable{
	
	private Message message;
	
	private List<Role> roles;
	
	private Map<String, List<SysAuthorities>> auth;
	
	List<Employee> emps;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Map<String, List<SysAuthorities>> getAuth() {
		return auth;
	}

	public void setAuth(Map<String, List<SysAuthorities>> auth) {
		this.auth = auth;
	}

	public List<Employee> getEmps() {
		return emps;
	}

	public void setEmps(List<Employee> emps) {
		this.emps = emps;
	}

}
