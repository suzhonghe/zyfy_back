package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.zyfyback.pojo.SysAuthorities;

public interface SysAuthoritiesDao {
	/**
	 * 
	* @Title: selectAllSysAuthorities 
	* @Description:查找所有的权限
	* @return List<SysAuthorities>    返回类型 
	* @throws
	 */
	List<SysAuthorities> selectAllSysAuthorities();

    /*int deleteByPrimaryKey(String privilige);

    int insert(SysAuthorities record);
    int insertSelective(SysAuthorities record);

    SysAuthorities selectByPrimaryKey(String privilige);

    int updateByPrimaryKeySelective(SysAuthorities record);

    int updateByPrimaryKey(SysAuthorities record);*/
}