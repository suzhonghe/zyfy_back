package com.zhongyang.java.zyfyback.biz.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.alibaba.druid.util.Base64;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.uitl.GetUUID;
import com.zhongyang.java.system.uitl.Message;
import com.zhongyang.java.system.uitl.SystemPro;
import com.zhongyang.java.zyfyback.biz.PhototManageBiz;
import com.zhongyang.java.zyfyback.params.PhotoParams;
import com.zhongyang.java.zyfyback.pojo.PhotoManager;
import com.zhongyang.java.zyfyback.returndata.PhotoReturn;
import com.zhongyang.java.zyfyback.service.PhotoManagerService;

@Service
public class PhototManageBizImpl implements PhototManageBiz {

	static {
		Map<String, Object> sysMap = SystemPro.getProperties();
		pc_banner = (String) sysMap.get("ZYCFMANAGE_PC_BANNER");
		app_banner = (String) sysMap.get("ZYCFMANAGE_APP_BANNER");
		advertisement = (String) sysMap.get("ZYCFMANAGE_ADVERTISEMENT");
		news = (String) sysMap.get("ZYCFMANAGE_NEWS");
		article = (String) sysMap.get("ZYCFMANAGE_ARTICLE");
		itemPhoto = (String) sysMap.get("ZYCFMANAGE_PHOTO_FILE");
		ZYCF_IP = (String) sysMap.get("ZYCF_IP");
	}

	private static Logger logger = Logger.getLogger(PhototManageBizImpl.class);

	private static String pc_banner;

	private static String app_banner;

	private static String advertisement;

	private static String news;

	private static String article;

	private static String itemPhoto;

	private static String ZYCF_IP;

	@Autowired
	private PhotoManagerService photoManagerService;

	@Override
	public PhotoReturn uploadPhoto(PhotoParams params) {
		PhotoReturn pr = new PhotoReturn();
		logger.info("开始上传图片");
		List<String> paths = new ArrayList<String>();
		String[] files = params.getFiles();
		FileOutputStream out = null;
		BufferedOutputStream buffer = null;
		try {
			int count=0;
			for (String str : files) {
				count++;
				String newFileName = System.currentTimeMillis()+count + "." + this.getImge(str);// 图片格式
				str = str.substring(str.lastIndexOf(",") + 1);
				byte[] base64ToByteArray = Base64.base64ToByteArray(str);

				switch (params.getType()) {
				case "pc_banner":
					out = new FileOutputStream(pc_banner + newFileName);
					break;
				case "app_banner":
					out = new FileOutputStream(app_banner + newFileName);
					break;
				case "advertisement":
					out = new FileOutputStream(advertisement + newFileName);
					break;
				case "news":
					out = new FileOutputStream(news + newFileName);
					break;
				case "article":
					out = new FileOutputStream(article + newFileName);
					break;
				case "upload":
					out = new FileOutputStream(itemPhoto + newFileName);
					;
					break;
				default:
					;
					break;
				}
				buffer = new BufferedOutputStream(out);
				buffer.write(base64ToByteArray);
				buffer.flush();
				buffer.close();
				out.flush();
				out.close();
				paths.add(ZYCF_IP.substring(0, ZYCF_IP.lastIndexOf("/")) + "/" + params.getType() + "/" + newFileName);
				
				//非项目需求图片直接入库
				if(!"upload".equals(params.getType())){
					
					PhotoManager photoManager = new PhotoManager();
					photoManager.setId(GetUUID.getUniqueKey());
					// 图片存储路径
	
					photoManager.setPathAddress("/" + params.getType() + "/" + newFileName);
					Date date = new Date();
					Timestamp timeStamp = new Timestamp(date.getTime());
					photoManager.setTime(timeStamp);
					photoManager.setPhotoName(newFileName);
	
					photoManager.setType(params.getType());
					photoManager.setWhetherShow(false);
					photoManager.setSerialNumber(Integer.MAX_VALUE);
					photoManagerService.addPhoto(photoManager);
	
				}
			}

			pr.setPath(paths);
			pr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "图片上传成功"));
		} catch (IOException e) {
			e.printStackTrace();
			pr.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(), "图片上传失败"));
		}
		return pr;
	}

	String getImge(String str) {
		String re = null;
		if (str.contains("jpg"))
			re = "jpg";
		if (str.contains("jpeg"))
			re = "jpeg";
		if (str.contains("png"))
			re = "png";
		if (str.contains("gif"))
			re = "gif";
		return re;
	}

	@Override
	public PhotoReturn deletePhoto(@RequestBody PhotoParams params) {
		String path;
		PhotoReturn pr = new PhotoReturn();
		try {
			if (params == null || params.getType() == null || params.getUrl() == null)
				throw new UException(SystemEnum.PARAMS_ERROR, "参数错误");

			switch (params.getType()) {
			case "pc_banner":
				path = pc_banner;
				break;
			case "app_banner":
				path = app_banner;
				break;
			case "advertisement":
				path = advertisement;
				break;
			case "news":
				path = news;
				break;
			case "article":
				path = article;
				break;
			case "upload":
				path = itemPhoto;
				break;
			default:
				path = null;
				break;
			}

			String filePath = path + params.getUrl().substring(params.getUrl().lastIndexOf("/"));
			File file = new File(filePath);
			if (file.exists()) {
				file.delete();
				pr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "删除成功"));
			} else
				throw new UException(SystemEnum.OPRARION_FAILED, "图片不存在");

		} catch (UException e) {
			logger.info(e.fillInStackTrace());
			pr.setMessage(new Message(e.getCode().value(), e.getMessage()));
		}

		return pr;
	}

	@Override
	public PhotoReturn deletePhotoManager(PhotoParams params) {
		PhotoReturn pr = new PhotoReturn();
		try {
			String path = null;
			String url = null;
			if (params.getUrl().contains("pc_banner")) {
				path = pc_banner;
				url = "/pc_banner";
			}
			if (params.getUrl().contains("app_banner")) {
				path = app_banner;
				url = "/app_banner";
			}
			if (params.getUrl().contains("news")) {
				path = news;
				url = "/news";
			}
			if (params.getUrl().contains("advertisement")) {
				path = advertisement;
				url = "/advertisement";
			}
			if (params.getUrl().contains("article")) {
				path = article;
				url = "/article";
			}
			String filePath = path + params.getUrl().substring(params.getUrl().lastIndexOf("/")+1);
			File file = new File(filePath);
			if (file.exists()) {
				file.delete();
			}
			PhotoManager photo = new PhotoManager();
			photo.setPathAddress(url + params.getUrl().substring(params.getUrl().lastIndexOf("/")));
			photoManagerService.deleteByParams(photo);

			pr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "删除成功"));

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("删除图片出现异常");
			logger.info(e, e.fillInStackTrace());
			pr.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(), "删除失败"));
		}
		return pr;
	}

	@Override
	public PhotoReturn queryPhotos(PhotoParams params) {
		PhotoReturn pr = new PhotoReturn();
		PhotoManager photoManager = new PhotoManager();
		photoManager.setType(params.getType());
		List<PhotoManager> res = photoManagerService.queryByParams(photoManager);
		for (PhotoManager photo : res) {
			photo.setPathAddress(ZYCF_IP.substring(0, ZYCF_IP.lastIndexOf("/")) + photo.getPathAddress());
		}

		pr.setPhotoManagers(res);
		pr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "查询成功"));

		return pr;

	}
	
	@Override
	public PhotoReturn modifyPhoto(PhotoParams params) {
		PhotoReturn pr = new PhotoReturn();
		photoManagerService.modifyByParams(params.getPhoto());
		pr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "图片修改成功"));

		return pr;
	}

	@Override
	public PhotoReturn modifyPhotoBySerial(PhotoParams params) {
		PhotoReturn pr = new PhotoReturn();

		for (PhotoManager photo : params.getPhotoManagers()) {
			if (photo == null)
				continue;
			photo.setWhetherShow(true);
			String pathAddress = photo.getPathAddress().substring(photo.getPathAddress().lastIndexOf("/"));
			photo.setPathAddress("/" + params.getType() + pathAddress);
		}
		PhotoManager photoManager = new PhotoManager();
		photoManager.setWhetherShow(true);
		photoManager.setType(params.getType());
		List<PhotoManager> queryBannerPhotos = photoManagerService.queryByParams(photoManager);

		if (queryBannerPhotos.size() != 0) {
			for (PhotoManager photo : queryBannerPhotos) {
				photo.setWhetherShow(false);
				photo.setSerialNumber(Integer.MAX_VALUE);

			}
		}
		params.getPhotoManagers().addAll(queryBannerPhotos);
		photoManagerService.batchUpdatePhotos(params.getPhotoManagers());
		pr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "轮播修改成功"));

		return pr;
	}

}
