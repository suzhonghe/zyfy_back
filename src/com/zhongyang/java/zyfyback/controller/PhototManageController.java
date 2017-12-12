package com.zhongyang.java.zyfyback.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.system.authority.Authorities;
import com.zhongyang.java.system.authority.FireAuthority;
import com.zhongyang.java.zyfyback.biz.PhototManageBiz;
import com.zhongyang.java.zyfyback.params.PhotoParams;
import com.zhongyang.java.zyfyback.returndata.PhotoReturn;
@CrossOrigin
@Controller
public class PhototManageController extends BaseController {
	
	@Autowired
	private PhototManageBiz photoManageBiz;
	
	/**
	 * 项目管理图片上传
	 *@date 下午6:04:25
	 *@param params
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.BANNERUPLOAD)
	@RequestMapping(value="/back/upload/uploadPhoto")
	public @ResponseBody PhotoReturn upload(@RequestBody PhotoParams params){
		return photoManageBiz.uploadPhoto(params);
	}
	/**
	 * 仅针对项目图片的删除
	 *@date 下午2:25:29
	 *@param params
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.BANNERMANAGE)
	@RequestMapping(value="/back/upload/deletePhoto",method=RequestMethod.POST)
	public @ResponseBody PhotoReturn deletePhoto(@RequestBody PhotoParams params){
		return photoManageBiz.deletePhoto(params);
	}
	
	/**
	 * 删除图片
	 *@date 下午3:05:26
	 *@param params
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.BANNERMANAGE)
	@RequestMapping(value="/back/upload/deletePhotoManager",method=RequestMethod.POST)
	public @ResponseBody PhotoReturn deletePhotoManager(@RequestBody PhotoParams params){
		return photoManageBiz.deletePhotoManager(params);
	}
	/**
	 * 获取图片列表
	 *@date 下午3:05:03
	 *@param params
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.BANNERMANAGE)
	@RequestMapping(value="/back/upload/searchPhotoManager",method=RequestMethod.POST)
	public @ResponseBody PhotoReturn queryPhotos(@RequestBody PhotoParams params){
		return photoManageBiz.queryPhotos(params);
	}
	/**
	 * 图片编辑
	 *@date 下午3:04:46
	 *@param params
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.BANNERMANAGE)
	@RequestMapping(value="/back/upload/modifyPhoto",method=RequestMethod.POST)
	public @ResponseBody PhotoReturn modifyPhoto(@RequestBody PhotoParams params){
		return photoManageBiz.modifyPhoto(params);
	}
	
	/**
	 * 轮播
	 *@date 下午1:50:11
	 *@param params
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.BANNERMANAGE)
	@RequestMapping(value="/back/upload/modifyPhotoBySerial",method=RequestMethod.POST)
	public @ResponseBody PhotoReturn modifyPhotoBySerial(@RequestBody PhotoParams params){
		return photoManageBiz.modifyPhotoBySerial(params);
	}
}
