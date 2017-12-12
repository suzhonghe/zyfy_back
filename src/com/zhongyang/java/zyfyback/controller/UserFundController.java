package com.zhongyang.java.zyfyback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.zyfyback.biz.UserFundBiz;
import com.zhongyang.java.zyfyback.pojo.User;
import com.zhongyang.java.zyfyback.returndata.UserFundReturn;

/**
 *@package com.zhongyang.java.zyfyback.controller
 *@filename UserFundController.java
 *@date 2017年7月21日上午8:54:53
 *@author suzh
 */
@CrossOrigin
@Controller
public class UserFundController extends BaseController{

	@Autowired
	private UserFundBiz userFundBiz;
	
	/**
	 * 资金明细
	 *@date 上午8:59:17
	 *@param userId
	 *@return
	 *@author suzh
	 */
	@RequestMapping(value="/back/user/searchUserFundDetail",method=RequestMethod.POST)
	public @ResponseBody UserFundReturn selectUserFundDetail(@RequestBody User user){
		return userFundBiz.selectUserFundDetail(user);
	}
}
