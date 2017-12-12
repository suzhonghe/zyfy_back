package com.zhongyang.java.bankmanager.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.bankmanager.biz.BmLoanManagerBackBiz;
import com.zhongyang.java.bankmanager.params.LoanManagerParams;
import com.zhongyang.java.bankmanager.returndata.LoanManagerReturn;
import com.zhongyang.java.system.authority.Authorities;
import com.zhongyang.java.system.authority.FireAuthority;
import com.zhongyang.java.zyfyback.controller.BaseController;

/**
 *@package com.zhongyang.java.bankmanager.controller
 *@filename BmLoanController.java
 *@date 2017年7月6日下午1:40:09
 *@author suzh
 */
@CrossOrigin
@Controller
public class BmLoanManagerBackController extends BaseController{

	@Autowired
	private BmLoanManagerBackBiz bmLoanManagerBiz;
	
	/**
	 * 标的发布
	 *@date 上午8:44:36
	 *@param request
	 *@param params
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.BIDRELEASE)
	@RequestMapping(value="/back/loan/publishLoan",method=RequestMethod.POST)
	public @ResponseBody LoanManagerReturn publishLoan(HttpServletRequest request,@RequestBody LoanManagerParams params){
		return bmLoanManagerBiz.publishLoan(request,params);
	}
	
	/**
	 * 标的结算
	 *@date 上午10:00:35
	 *@param request
	 *@param params
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.ACCAUDITLIST)
	@RequestMapping(value="/back/loan/settleLoan",method=RequestMethod.POST)
	public synchronized @ResponseBody LoanManagerReturn settleLoan(@RequestBody LoanManagerParams params){
		return bmLoanManagerBiz.settlementLoan(params);
	}
	
	/**
	 *流标
	 *@date 上午10:02:59
	 *@param params
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.INBCANCEL)
	@RequestMapping(value="/back/loan/failedLoan",method=RequestMethod.POST)
	public synchronized @ResponseBody LoanManagerReturn failedLoan(@RequestBody LoanManagerParams params){
		return bmLoanManagerBiz.failedLoan(params);
	}
	/**
	 * 标的还款
	 *@date 上午11:14:49
	 *@param params
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.REPAYMENT)
	@RequestMapping(value="/back/loan/repayLoan",method=RequestMethod.POST)
	public synchronized @ResponseBody LoanManagerReturn repayLoan(@RequestBody LoanManagerParams params){
		return bmLoanManagerBiz.repayLoan(params);
	}
	
	@FireAuthority(authorities=Authorities.INBUPD)
	@RequestMapping(value="/back/loan/modifyComentSateUserId",method=RequestMethod.POST)
	public synchronized @ResponseBody LoanManagerReturn modifyComentSateUserId(@RequestBody LoanManagerParams params){
		return bmLoanManagerBiz.modifyComentSateUserId(params);
	}
}
