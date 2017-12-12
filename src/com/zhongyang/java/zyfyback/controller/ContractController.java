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
import com.zhongyang.java.zyfyback.biz.ContractBiz;
import com.zhongyang.java.zyfyback.params.ConTractParams;
import com.zhongyang.java.zyfyback.returndata.ConTractReturn;
@CrossOrigin
@Controller
public class ContractController extends BaseController{

	@Autowired
	private ContractBiz contractBiz;
	
	@FireAuthority(authorities=Authorities.CONTRACTGEN)
	@RequestMapping(value="/back/contract/generateContracts",method=RequestMethod.POST)
	public @ResponseBody synchronized ConTractReturn generateContracts(HttpServletRequest request, @RequestBody ConTractParams params){
		return contractBiz.generateContracts(request,params);
	}
	
	@FireAuthority(authorities=Authorities.UNCONTRACTLOANS)
	@RequestMapping(value="/back/contract/getuncontractLoans",method=RequestMethod.POST)
	public @ResponseBody ConTractReturn getuncontractLoans(@RequestBody ConTractParams params){
		return contractBiz.uncontractLoans(params);
	}
}
