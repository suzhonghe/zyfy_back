package com.zhongyang.java.zyfyback.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.CmsColumnDao;
import com.zhongyang.java.zyfyback.pojo.CmsColumn;
import com.zhongyang.java.zyfyback.service.CmsColumnService;
@Service
public class CmsColumnServiceImpl implements CmsColumnService {
	
	@Autowired
	private CmsColumnDao cmsColumnDao;

	@Override
	public int addCmsColumn(CmsColumn cmsColumn){
		return cmsColumnDao.insertCmsColumn(cmsColumn);
	}

	@Override
	public int modifyByParams(CmsColumn cmsColumn){
		return cmsColumnDao.updateByParams(cmsColumn);
	}

	@Override
	public int deleteByParams(CmsColumn cmsColumn){
		return cmsColumnDao.deleteByParams(cmsColumn);
	}

	@Override
	public List<CmsColumn> queryByParams(CmsColumn cmsColumn){
		return cmsColumnDao.selectByParams(cmsColumn);
	}

}
