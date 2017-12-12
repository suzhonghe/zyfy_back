package com.zhongyang.java.bankmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.bankmanager.biz.SettledAccountBiz;
import com.zhongyang.java.bankmanager.params.SettledParams;
import com.zhongyang.java.bankmanager.returndata.SettledReturn;
import com.zhongyang.java.system.authority.Authorities;
import com.zhongyang.java.system.authority.FireAuthority;
import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.uitl.Message;

/**
 *@package com.zhongyang.java.bankmanager.controller
 *@filename SttledAccountController.java
 *@date 2017年10月18日下午1:41:56
 *@author suzh
 */
@Controller
public class SttledAccountController {

	@Autowired
	private SettledAccountBiz settledAccountBiz;
	
	/**
	 * 充值对账下载
	 *@date 上午9:00:52
	 *@param params
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.DOWNLOADCHECKFILE)
	@RequestMapping(value="/back/settled/settledAccount",method=RequestMethod.POST)
	public @ResponseBody SettledReturn settledRecharge(@RequestBody SettledParams params){
		if(params==null||params.getSettledNo()==null||"".equals(params.getSettledNo())){
			SettledReturn sr=new SettledReturn();
			sr.setMessage(new Message(SystemEnum.PARAMS_ERROR.value(),"参数错误"));
			return sr;
		}
		else if("1".equals(params.getSettledNo()))
			return settledAccountBiz.settledRecharge(params);
		else if("2".equals(params.getSettledNo()))
			return settledAccountBiz.settledWithdrawal(params);
		else if("3".equals(params.getSettledNo()))
			return settledAccountBiz.settledRefundDetail(params);
		else{
			return null;
		}
	}
	
	
	/**
	 * 充值对账查询
	 *@date 上午9:01:59
	 *@param params
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.CHECKFILEMANAGER)
	@RequestMapping(value="/back/search/searchRecharge",method=RequestMethod.POST)
	public @ResponseBody SettledReturn searchRecharge(@RequestBody SettledParams params){
		return settledAccountBiz.searchRecharge(params);
	}
	
	/**
	 * 提现对账查询
	 *@date 上午9:03:29
	 *@param params
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.CHECKFILEMANAGER)
	@RequestMapping(value="/back/search/searchWithdrawal",method=RequestMethod.POST)
	public @ResponseBody SettledReturn searchWithdrawal(@RequestBody SettledParams params){
		return settledAccountBiz.searchWithdrawal(params);
	}
	
	/**
	 * 退票对账查询
	 *@date 上午9:03:42
	 *@param params
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.CHECKFILEMANAGER)
	@RequestMapping(value="/back/search/searchRefundDetail",method=RequestMethod.POST)
	public @ResponseBody SettledReturn searchRefundDetail(@RequestBody SettledParams params){
		return settledAccountBiz.searchRefundDetail(params);
	}
}
