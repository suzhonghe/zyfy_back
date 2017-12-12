package com.zhongyang.java.zyfyback.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.system.authority.Authorities;
import com.zhongyang.java.system.authority.FireAuthority;
import com.zhongyang.java.zyfyback.biz.UserBiz;
import com.zhongyang.java.zyfyback.params.UserParams;
import com.zhongyang.java.zyfyback.pojo.User;
import com.zhongyang.java.zyfyback.returndata.UserReturn;

/**
 *@package com.zhongyang.java.zyfyback.controller
 *@filename UserContoller.java
 *@date 20172017年6月28日上午10:24:22
 *@author suzh
 */
@CrossOrigin
@Controller
public class UserController extends BaseController{
	
	@Autowired
	private UserBiz userBiz;
	
	@FireAuthority(authorities=Authorities.USERINFODETAILE)
	@RequestMapping(value="/back/user/searchUserByParams",method=RequestMethod.POST)
	@ResponseBody
	public UserReturn searchUserByParams(@RequestBody UserParams params){
		return userBiz.searchUserByParams(params);
	}
	
	/**
	 * 用户列表
	 *@date 下午1:27:22
	 *@param params
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.USERLIST)
	@RequestMapping(value="/back/user/searchUserList",method=RequestMethod.POST)
	public @ResponseBody UserReturn searchUserList(@RequestBody UserParams params){
		return userBiz.searchUserList(params);
	}
	
	/**
	 * 用户禁启用
	 *@date 下午2:33:16
	 *@param params
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.USERINFOEDIT)
	@RequestMapping(value="/back/user/modifyUser",method=RequestMethod.POST)
	public @ResponseBody UserReturn modifyUser(@RequestBody UserParams params){
		return userBiz.modifyUser(params);
	}
	/**
	 * 单个客户推荐人变更
	 *@date 下午3:22:10
	 *@param userMobile 客户手机号
	 *@param refMobile 推荐人手机号
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.USERINFOEDIT)
	@RequestMapping(value="/back/user/modifyReferral",method=RequestMethod.POST)
	public @ResponseBody UserReturn modifyReferral(@RequestBody UserParams params){
		return userBiz.modifyReferral(params.getUserMobile(),params.getRefMobile());
	}
	
	/**
	 * 批量变更推荐人
	 *@date 下午3:43:06
	 *@param oriMobile 原推荐人手机号
	 *@param nowMobile 变更为推荐人手机号
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.USERINFOEDIT)
	@RequestMapping(value="/back/user/batchModifyReferral",method=RequestMethod.POST)
	public @ResponseBody UserReturn batchModifyReferral(@RequestBody UserParams params){
		return userBiz.batchModifyReferral(params.getOriMobile(),params.getNowMobile());
	}
	
	/**
	 * 用户详情
	 *@date 下午3:58:28
	 *@param userId
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.USERINFODETAILE)
	@RequestMapping(value="/back/user/searchUserDetail",method=RequestMethod.POST)
	public @ResponseBody UserReturn searchUserDetail(@RequestBody User user){
		return userBiz.searchUserDetail(user);
	}
	
	/**
	 * 根据注册时间查询用户
	 *@date 下午3:19:26
	 *@param req
	 *@param params
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.USERLIST)
	@RequestMapping(value = "/back/user/getUserMobiles",method=RequestMethod.POST)
	public @ResponseBody UserReturn getUserMobiles(HttpServletRequest req,@RequestBody UserParams params) {
		return userBiz.getUserMobiles(req,params);
	}
	
	/**
	 * 根据生日查询用户
	 *@date 下午3:20:36
	 *@param params
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.USERLIST)
	@RequestMapping(value = "/back/user/queryBirthDateUsers",method = RequestMethod.POST)
	public @ResponseBody UserReturn queryBirthDateUsers(@RequestBody UserParams params){
		return userBiz.queryBirthDateUsers(params);
	}
	
	/**
	 * 根据投资查询用户
	 *@date 下午3:21:06
	 *@param params
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.USERLIST)
	@RequestMapping(value = "/back/user/queryInvestUsers",method = RequestMethod.POST)
	public @ResponseBody UserReturn queryInvestUsers(@RequestBody UserParams params){
		return userBiz.queryInvestUsers(params);
	}
	
	/**
	 * 给用户发送短信
	 *@date 下午3:21:33
	 *@param params
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.GROUPMSG)
	@RequestMapping(value = "/back/user/sensMessageToUser",method = RequestMethod.POST)
	public @ResponseBody UserReturn sensMessageToUser(@RequestBody UserParams params) {
		return userBiz.sensMessageToUser(params);
	}
}
