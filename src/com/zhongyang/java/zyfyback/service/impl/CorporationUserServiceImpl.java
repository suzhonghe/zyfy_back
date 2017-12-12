package com.zhongyang.java.zyfyback.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.CorporationUserDao;
import com.zhongyang.java.zyfyback.pojo.CorporationUser;
import com.zhongyang.java.zyfyback.service.CorporationUserService;
@Service
public class CorporationUserServiceImpl implements CorporationUserService {
	
	@Autowired
	private CorporationUserDao corporationUserDao;
	
	@Override
	public List<CorporationUser> queryAllCorporationUser(CorporationUser corporationUser){
		return corporationUserDao.selectAllCorporationUser(corporationUser);
	}

}
