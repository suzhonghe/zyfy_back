package com.zhongyang.java.bankmanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.CompanyDao;
import com.zhongyang.java.bankmanager.entity.Company;
import com.zhongyang.java.bankmanager.service.CompanyService;

/**
 *@package com.zhongyang.java.zyfyfront.service.impl
 *@filename CompanyServiceImpl.java
 *@date 2017年8月16日下午4:19:21
 *@author suzh
 */
@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyDao companyDao;
	
	@Override
	public Company queryCompanyByParams(Company company) {
		return companyDao.selectCompanyByParams(company);
	}

}
