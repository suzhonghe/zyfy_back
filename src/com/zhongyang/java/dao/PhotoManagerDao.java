package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.zyfyback.pojo.PhotoManager;

public interface PhotoManagerDao {

	public int insertPhoto(PhotoManager photoManager);

	public List<PhotoManager> selectByParams(PhotoManager photoManager);


	public void deleteByParams(PhotoManager photoManager);


	public int batchUpdatePhotos(List<PhotoManager> list);


	public int updateByParams(PhotoManager photoManager);
}
