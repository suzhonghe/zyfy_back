package com.zhongyang.java.bankmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.bankmanager.biz.BmCallBackBiz;
import com.zhongyang.java.bankmanager.params.BmCallBackParams;
import com.zhongyang.java.bankmanager.returndata.BmCallBackReturn;
import com.zhongyang.java.system.authority.Authorities;
import com.zhongyang.java.system.authority.FireAuthority;
import com.zhongyang.java.zyfyback.controller.BaseController;

/**
 *@package com.zhongyang.java.bankmanager.controller
 *@filename BmCallBackController.java
 *@date 2017年10月17日下午1:53:29
 *@author suzh
 */
@Controller
public class BmBackCallBackController extends BaseController{
	
	@Autowired
	private BmCallBackBiz bmCallBackBiz;
	
	@FireAuthority(authorities=Authorities.MAINTAINQUERY)
	@RequestMapping(value="/back/callBack/searchCallBackRefund",method=RequestMethod.POST)
	public @ResponseBody BmCallBackReturn searchCallBackRefund(@RequestBody BmCallBackParams params){
		return bmCallBackBiz.searchCallBackRefund(params);
	}
	
	@FireAuthority(authorities=Authorities.MAINTAINQUERY)
	@RequestMapping(value="/back/callBack/searchCallBackClear",method=RequestMethod.POST)
	public @ResponseBody BmCallBackReturn searchCallBackClear(@RequestBody BmCallBackParams params){
		return bmCallBackBiz.searchCallBackClear(params);
	}
}
