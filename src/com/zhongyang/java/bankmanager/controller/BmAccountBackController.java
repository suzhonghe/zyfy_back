package com.zhongyang.java.bankmanager.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.bankmanager.biz.BmAccountBackBiz;
import com.zhongyang.java.bankmanager.params.BmAccountParams;
import com.zhongyang.java.bankmanager.returndata.BmAccountReturn;
import com.zhongyang.java.system.authority.Authorities;
import com.zhongyang.java.system.authority.FireAuthority;
import com.zhongyang.java.zyfyback.controller.BaseController;

/**
 *@package com.zhongyang.java.bankmanager.controller
 *@filename BmAccountController.java
 *@date 2017年7月20日上午10:43:25
 *@author suzh
 */
@CrossOrigin
@Controller
public class BmAccountBackController extends BaseController{

	@Autowired
	private BmAccountBackBiz bmAccountBiz;
	/**
	 * 用户手机号变更
	 *@date 上午10:50:44
	 *@param request
	 *@param old_mobile
	 *@param new_mobile
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.USERINFOEDIT)
	@RequestMapping(value="/back/bmAccount/modifyMobile",method=RequestMethod.POST)
	public @ResponseBody BmAccountReturn modifyMobile(HttpServletRequest request,@RequestBody BmAccountParams params){
		return bmAccountBiz.modifyMobile(request,params);
	}
}
