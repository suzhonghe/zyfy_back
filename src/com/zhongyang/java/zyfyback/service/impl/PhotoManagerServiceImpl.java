package com.zhongyang.java.zyfyback.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.PhotoManagerDao;
import com.zhongyang.java.zyfyback.pojo.PhotoManager;
import com.zhongyang.java.zyfyback.service.PhotoManagerService;
@Service
public class PhotoManagerServiceImpl implements PhotoManagerService {
	
	@Autowired
	private PhotoManagerDao photoManagerDao;
	
	
	@Override
	public int addPhoto(PhotoManager photoManager){
		return photoManagerDao.insertPhoto(photoManager);
	}

	@Override
	public List<PhotoManager> queryByParams(PhotoManager photoManager){
		return photoManagerDao.selectByParams(photoManager);
	}

	@Override
	public void deleteByParams(PhotoManager photoManager){
		photoManagerDao.deleteByParams(photoManager);
	}

	@Override
	public int batchUpdatePhotos(List<PhotoManager> photoManagers){
		return photoManagerDao.batchUpdatePhotos(photoManagers);
	}

	@Override
	public int modifyByParams(PhotoManager photoManager){
		return photoManagerDao.updateByParams(photoManager);
	}
}
