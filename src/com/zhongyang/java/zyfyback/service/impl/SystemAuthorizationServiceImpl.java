package com.zhongyang.java.zyfyback.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.SysAuthorizationMapper;
import com.zhongyang.java.zyfyback.pojo.SysAuthorization;
import com.zhongyang.java.zyfyback.service.SystemAuthorizationService;

@Service
public class SystemAuthorizationServiceImpl  implements SystemAuthorizationService{

	@Autowired
	SysAuthorizationMapper sysAuthorizationMapper;
	
	 public List<SysAuthorization> selectAll(){
		
		return sysAuthorizationMapper.selectAll();
	}
}
