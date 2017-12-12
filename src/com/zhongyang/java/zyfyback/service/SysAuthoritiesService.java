package com.zhongyang.java.zyfyback.service;

import java.util.List;

import com.zhongyang.java.zyfyback.pojo.SysAuthorities;

public interface SysAuthoritiesService {
	/**
	 * 
	* @Title: queryAllSysAuthorities 
	* @Description:查找所有的权限
	* @return List<SysAuthorities>    返回类型 
	* @throws
	 */
	List<SysAuthorities> queryAllSysAuthorities();

    /*int deleteByPrimaryKey(String privilige);

    int insert(SysAuthorities record);
    int insertSelective(SysAuthorities record);

    SysAuthorities selectByPrimaryKey(String privilige);

    int updateByPrimaryKeySelective(SysAuthorities record);

    int updateByPrimaryKey(SysAuthorities record);*/
}