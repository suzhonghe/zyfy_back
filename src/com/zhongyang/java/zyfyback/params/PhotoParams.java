package com.zhongyang.java.zyfyback.params;

import java.util.List;

import com.zhongyang.java.zyfyback.pojo.PhotoManager;

/**
 *@package com.zhongyang.java.zyfyback.params
 *@filename PhotoParams.java
 *@date 2017年7月26日下午5:39:06
 *@author suzh
 */
public class PhotoParams {
	
	private PhotoManager photo;
	
	private String[] files;
	
	private String type;
	
	private String url;
	
	private List<PhotoManager>photoManagers;
	
	public List<PhotoManager> getPhotoManagers() {
		return photoManagers;
	}

	public void setPhotoManagers(List<PhotoManager> photoManagers) {
		this.photoManagers = photoManagers;
	}

	public PhotoManager getPhoto() {
		return photo;
	}

	public void setPhoto(PhotoManager photo) {
		this.photo = photo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String[] getFiles() {
		return files;
	}

	public void setFiles(String[] files) {
		this.files = files;
	}
	
}
