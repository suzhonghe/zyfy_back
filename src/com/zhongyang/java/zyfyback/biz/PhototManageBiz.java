package com.zhongyang.java.zyfyback.biz;

import com.zhongyang.java.zyfyback.params.PhotoParams;
import com.zhongyang.java.zyfyback.returndata.PhotoReturn;

public interface PhototManageBiz {
	
	public PhotoReturn uploadPhoto(PhotoParams params);
	
	public PhotoReturn deletePhoto(PhotoParams params);
	
	public PhotoReturn deletePhotoManager(PhotoParams params);
	
	public PhotoReturn queryPhotos(PhotoParams params);
	
	public PhotoReturn modifyPhoto(PhotoParams params);
	
	public PhotoReturn modifyPhotoBySerial(PhotoParams params);
}
