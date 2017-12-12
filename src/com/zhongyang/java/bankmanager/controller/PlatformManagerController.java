package com.zhongyang.java.bankmanager.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.bankmanager.biz.PlatformManagerBiz;
import com.zhongyang.java.bankmanager.entity.UntredtedApplication;
import com.zhongyang.java.bankmanager.params.PlatformManagerParams;
import com.zhongyang.java.bankmanager.params.TransAmountParams;
import com.zhongyang.java.bankmanager.returndata.PlatformManagerReturn;
import com.zhongyang.java.system.authority.Authorities;
import com.zhongyang.java.system.authority.FireAuthority;
import com.zhongyang.java.zyfyback.controller.BaseController;

/**
 *@package com.zhongyang.java.bankmanager.controller
 *@filename PlatformManagerController.java
 *@date 2017年7月7日上午10:48:01
 *@author suzh
 */
@CrossOrigin
@Controller
public class PlatformManagerController extends BaseController{

	@Autowired
	private PlatformManagerBiz platformManagerBiz;
	
	/**
	 * 企业充值
	 *@date 上午8:41:32
	 *@param request
	 *@param amount
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.ENTERPRECHARGE)
	@RequestMapping(value="/back/platform/platCharge",method=RequestMethod.POST)
	public @ResponseBody PlatformManagerReturn companyCharge(HttpServletRequest request,@RequestBody PlatformManagerParams params){
		return platformManagerBiz.companyCharge(request,params);
	}
	
	/**
	 * 平台提现
	 *@date 上午9:22:57
	 *@param request
	 *@param amount
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.ENTERPREWITHDRAW)
	@RequestMapping(value="/back/platform/platWithdraw",method=RequestMethod.POST)
	public @ResponseBody PlatformManagerReturn platWithdraw(HttpServletRequest request,@RequestBody PlatformManagerParams params){
		return platformManagerBiz.companyWithdraw(request,params);
	}
	/**
	 * 平台不同子账户转账
	 *@date 上午9:58:29
	 *@param source_account
	 *@param amount
	 *@param dest_account
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.ENTERPTRANSFER)
	@RequestMapping(value="/back/platform/accountConverse")
	public @ResponseBody PlatformManagerReturn platformAccountConverse(@RequestBody PlatformManagerParams params){
		return platformManagerBiz.platformAccountConverse(params);
	}
	
	/**
	 * 平台转账给个人申请
	 *TODO
	 *@date 上午8:38:03
	 *@param request
	 *@param params
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.B2CTRANSAPPLY)
	@RequestMapping(value="/back/platform/platToPersonApply",method=RequestMethod.POST)
	public @ResponseBody PlatformManagerReturn platToPersonApply(HttpServletRequest request,@RequestBody TransAmountParams params){
		return platformManagerBiz.platToPersonApply(request,params);
	}
	
	/**
	 * 平台转账给个人申请取消
	 *TODO
	 *@date 上午8:38:12
	 *@param request
	 *@param application
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.B2CTRANSCANCEL)
	@RequestMapping(value="/back/platform/platToPersonCancle",method=RequestMethod.POST)
	public @ResponseBody PlatformManagerReturn platToPersonCancle(HttpServletRequest request,@RequestBody UntredtedApplication application){
		return platformManagerBiz.platToPersonCancle(request,application);
	}
	
	/**
	 * 平台转账给个人申请列表
	 *TODO
	 *@date 上午8:53:12
	 *@param request
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.B2CTRANSLIST)
	@RequestMapping(value="/back/platform/platToPersonList",method=RequestMethod.POST)
	public @ResponseBody PlatformManagerReturn platToPersonList(HttpServletRequest request){
		return platformManagerBiz.platToPersonList(request);
	}
	
	/**
	 * 平台转账给个人审核确认
	 *TODO
	 *@date 上午8:53:12
	 *@param request
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.B2CTRANSAGGREE)
	@RequestMapping(value="/back/platform/platToPersonAffirm",method=RequestMethod.POST)
	public @ResponseBody PlatformManagerReturn platToPersonAffirm(HttpServletRequest request,@RequestBody UntredtedApplication application){
		return platformManagerBiz.platToPersonAffirm(request,application);
	}
}
