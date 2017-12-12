package com.zhongyang.java.zyfyback.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.SysAuthoritiesDao;
import com.zhongyang.java.zyfyback.pojo.SysAuthorities;
import com.zhongyang.java.zyfyback.service.SysAuthoritiesService;
@Service
public class SysAuthoritiesServiceImpl implements SysAuthoritiesService {
	
	@Autowired
	private SysAuthoritiesDao sysAuthoritiesDao;

	@Override
	public List<SysAuthorities> queryAllSysAuthorities() {
		return sysAuthoritiesDao.selectAllSysAuthorities();
	}

}
