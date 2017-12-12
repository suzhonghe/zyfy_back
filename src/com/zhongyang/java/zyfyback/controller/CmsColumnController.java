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
import com.zhongyang.java.zyfyback.biz.CmsColumnBiz;
import com.zhongyang.java.zyfyback.pojo.CmsColumn;
import com.zhongyang.java.zyfyback.returndata.CmsReturn;

/**
 * 
* @Title: CmsColumnController.java 
* @Package com.zhongyang.java.controller 
* @Description:栏目控制器
* @author 苏忠贺   
* @date 2016年3月3日 上午10:46:58 
* @version V1.0
 */
@CrossOrigin
@Controller
public class CmsColumnController extends BaseController{
	
	@Autowired
	private CmsColumnBiz cmsColumnBiz;
	
	@FireAuthority(authorities=Authorities.COLUMNADD)
	@RequestMapping(value="/back/cms/addCmsColumn",method=RequestMethod.POST)
	public @ResponseBody CmsReturn addCmsColumn(@RequestBody CmsColumn cmsColumn){
		return cmsColumnBiz.addCmsColumn(cmsColumn);
	}
	
	@FireAuthority(authorities=Authorities.COLUMNUPD)
	@RequestMapping(value="/back/cms/modifyCmsColumnByParams",method=RequestMethod.POST)
	public @ResponseBody CmsReturn modifyByParams(@RequestBody CmsColumn cmsColumn){
		return cmsColumnBiz.modifyByParams(cmsColumn);
	}
	
	@FireAuthority(authorities=Authorities.COLUMNDEL)
	@RequestMapping(value="/back/cms/deleteCmsColumnByParams",method=RequestMethod.POST)
	public @ResponseBody CmsReturn deleteByParams(@RequestBody CmsColumn cmsColumn){
		return cmsColumnBiz.deleteByParams(cmsColumn);
	}
	
	@FireAuthority(authorities=Authorities.COLUMNLIST)
	@RequestMapping(value="/back/cms/queryCmsColumnByParams",method=RequestMethod.POST)
	public @ResponseBody CmsReturn queryByParams(@RequestBody CmsColumn cmsColumn){
		return cmsColumnBiz.queryByParams(cmsColumn);
	}
}
