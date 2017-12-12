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
import com.zhongyang.java.zyfyback.biz.LoanBiz;
import com.zhongyang.java.zyfyback.params.LoanParams;
import com.zhongyang.java.zyfyback.returndata.LoanReturn;

/**
 * 
* @Title: LoanController.java 
* @Package com.zhongyang.java.controller 
* @Description:标的控制器 
* @author 苏忠贺   
* @date 2015年12月4日 上午9:58:10 
* @version V1.0
 */
@CrossOrigin
@Controller
public class LoanController extends BaseController{

	@Autowired
	private LoanBiz loanBiz;
	

	@FireAuthority(authorities=Authorities.BIDAPPLY)
	@RequestMapping(value="/back/loan/bidApply", method = RequestMethod.POST)
	public @ResponseBody LoanReturn bidApply(@RequestBody LoanParams params){
		return loanBiz.addLoan(params);
	}
	
	/**
	 * 根据标的状态查询标的列表
	 * @param params
	 * @return
	 */
	@FireAuthority(authorities=Authorities.BIDLIST)
	@RequestMapping(value="/back/loan/loanList")
	public @ResponseBody LoanReturn searchLoanList(@RequestBody LoanParams params){
		return loanBiz.searchLoanList(params);
	}
	/**
	 * 查询标的信息
	 * @param params
	 * @return
	 */
	@FireAuthority(authorities=Authorities.BIDCAT)
	@RequestMapping(value="/back/loan/searchLoanByParams", method = RequestMethod.POST)
	public @ResponseBody LoanReturn searchLoanByParams(@RequestBody LoanParams params){
		return loanBiz.queryLoanByParams(params);
	}
	/**
	 * 修改未发布标的信息
	 * @param params
	 * @return
	 */
	@FireAuthority(authorities=Authorities.BIDUPD)
	@RequestMapping(value="/back/loan/modifyUnpublishedLoan", method = RequestMethod.POST)
	public @ResponseBody LoanReturn modifyLoan(@RequestBody LoanParams params){
		return loanBiz.modifyUnpublishedLoan(params);
	}
	
	/**
	 * 借入历史
	 *@date 上午11:13:32
	 *@param params
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.LOANRECORDLIST)
	@RequestMapping(value="/back/loan/searchLoanRecord",method=RequestMethod.POST)
	public @ResponseBody LoanReturn searchLoanRecord(@RequestBody LoanParams params){
		return loanBiz.searchLoanRecord(params);
	}
}
