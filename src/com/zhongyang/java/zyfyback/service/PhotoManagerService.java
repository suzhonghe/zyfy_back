package com.zhongyang.java.zyfyback.service;

import java.util.List;

import com.zhongyang.java.zyfyback.pojo.PhotoManager;

public interface PhotoManagerService {
	
	public int addPhoto(PhotoManager photoManager);
	
	public List<PhotoManager> queryByParams(PhotoManager photoManager);
	
	public void deleteByParams(PhotoManager photoManager);
	
	public int batchUpdatePhotos(List<PhotoManager> photoManagers);
	
	public int modifyByParams(PhotoManager photoManager);
}
