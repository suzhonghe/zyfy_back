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
import com.zhongyang.java.zyfyback.biz.InvestBiz;
import com.zhongyang.java.zyfyback.params.InvestParams;
import com.zhongyang.java.zyfyback.returndata.InvestReturn;

/**
 *@package com.zhongyang.java.zyfyback.controller
 *@filename InvestLoanController.java
 *@date 2017年7月21日上午10:34:35
 *@author suzh
 */
@CrossOrigin
@Controller
public class InvestLoanController extends BaseController{

	@Autowired
	private InvestBiz investBiz;
	/**
	 * 投资记录查询
	 *@date 上午10:50:07
	 *@param params
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.INVESTRECORDLIST)
	@RequestMapping(value="/back/invest/searchInvestRecordByParams",method=RequestMethod.POST)
	public @ResponseBody InvestReturn searchInvestRecordByParams(@RequestBody InvestParams params){
		return investBiz.searchInvestRecordByParams(params);
	}
	
}
