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
import com.zhongyang.java.zyfyback.biz.LoanRepaymentBiz;
import com.zhongyang.java.zyfyback.params.LoanRepaymentParams;
import com.zhongyang.java.zyfyback.returndata.LoanRepaymentReturn;

/**
 *@package com.zhongyang.java.zyfyback.controller
 *@filename LoanRepaymentController.java
 *@date 2017年7月21日下午4:21:51
 *@author suzh
 */
@CrossOrigin
@Controller
public class LoanRepaymentController extends BaseController{

	@Autowired
	private LoanRepaymentBiz loanRepaymentBiz;
	
	@FireAuthority(authorities=Authorities.REPAYLIST)
	@RequestMapping(value="/back/loanRepayment/searchLoanRepaymentList",method=RequestMethod.POST)
	public @ResponseBody LoanRepaymentReturn searchLoanRepaymentList(@RequestBody LoanRepaymentParams params){
		return loanRepaymentBiz.searchLoanRepaymentList(params);
	}
}
