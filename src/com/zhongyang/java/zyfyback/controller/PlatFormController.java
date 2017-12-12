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
import com.zhongyang.java.zyfyback.biz.PlatFormBiz;
import com.zhongyang.java.zyfyback.params.PlatFormParams;
import com.zhongyang.java.zyfyback.returndata.PlatFormReturn;

/**
 *@package com.zhongyang.java.zyfyback.controller
 *@filename PlatFormController.java
 *@date 2017年7月24日上午8:50:29
 *@author suzh
 */
@CrossOrigin
@Controller
public class PlatFormController extends BaseController{
	
	@Autowired
	private PlatFormBiz platFormBiz;
	
	/**
	 * 平台资金记录查询
	 *@date 上午8:56:56
	 *@param params
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.PLATCAPITALRECORDS)
	@RequestMapping(value="/back/platForm/searchPlatFormRecord",method=RequestMethod.POST)
	public @ResponseBody PlatFormReturn  searchPlatFormRecord(@RequestBody PlatFormParams params){
		return platFormBiz.searchPlatFormRecord(params);
	}
}
