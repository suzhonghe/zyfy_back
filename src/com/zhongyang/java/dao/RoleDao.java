package com.zhongyang.java.dao;

import java.util.List;
import java.util.Map;

import com.zhongyang.java.zyfyback.pojo.Role;

public interface RoleDao {
	
	List<Role> selectAllRoles();
	
	Role selectRoleByParams(Role role);
	
	List<Role> selectEmployeeRole(Map<String,String> map);	
	
	int insertRole(Role role);
	
	void updateRoleByParams(Role role);
	
	/*int deleteByPrimaryKey(String id);
    
    

    int insertSelective(Role record);
    
    Role selectByPrimaryKey(String id);
    
    Role selectByName(Role role);
    
    int updateByPrimaryKeySelective(Role record);
    
    */
}