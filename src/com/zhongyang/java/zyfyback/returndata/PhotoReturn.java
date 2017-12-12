package com.zhongyang.java.zyfyback.returndata;

import java.io.Serializable;
import java.util.List;

import com.zhongyang.java.system.uitl.Message;
import com.zhongyang.java.zyfyback.pojo.PhotoManager;

/**
 *@package com.zhongyang.java.zyfyback.returndata
 *@filename PhotoReturn.java
 *@date 20172017年6月27日下午2:36:18
 *@author suzh
 */
public class PhotoReturn implements Serializable{
	
	private List<String>res;
	
	private Message message;
	
	private List<String>path;
	
	private List<PhotoManager> photoManagers;
	
	public List<PhotoManager> getPhotoManagers() {
		return photoManagers;
	}

	public void setPhotoManagers(List<PhotoManager> photoManagers) {
		this.photoManagers = photoManagers;
	}

	public List<String> getPath() {
		return path;
	}

	public void setPath(List<String> path) {
		this.path = path;
	}

	public List<String> getRes() {
		return res;
	}

	public void setRes(List<String> res) {
		this.res = res;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}
	
}
