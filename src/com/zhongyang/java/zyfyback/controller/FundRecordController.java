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
import com.zhongyang.java.zyfyback.biz.FundRecordBiz;
import com.zhongyang.java.zyfyback.params.FundRecordParams;
import com.zhongyang.java.zyfyback.returndata.FundRecordReturn;

/**
 *@package com.zhongyang.java.zyfyback.controller
 *@filename FundRecordController.java
 *@date 2017年7月21日上午9:42:56
 *@author suzh
 */
@CrossOrigin
@Controller
public class FundRecordController extends BaseController{

	@Autowired
	private FundRecordBiz fundRecordBiz;
	
	/**
	 * 分页条件查询资金记录
	 *@date 上午10:19:33
	 *@param params
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.FUNDRECORDLIST)
	@RequestMapping(value="/back/fundRecord/searchFundRecordByPage",method=RequestMethod.POST)
	public @ResponseBody FundRecordReturn searchFundRecordByPage(@RequestBody FundRecordParams params){
		return fundRecordBiz.searchFundRecordByPage(params);
	}
	
	/**
	 * 个人资金记录查询
	 *@date 上午10:14:26
	 *@param params
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.FUNDRECORDLIST)
	@RequestMapping(value="/back/fundRecord/searchPersonFundRecordByPage",method=RequestMethod.POST)
	public @ResponseBody FundRecordReturn searchPersonFundRecordByPage(@RequestBody FundRecordParams params){
		return fundRecordBiz.searchPersonFundRecordByPage(params);
	}
}
