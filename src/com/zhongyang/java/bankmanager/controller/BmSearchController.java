 package com.zhongyang.java.bankmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.bankmanager.biz.BmSearchBiz;
import com.zhongyang.java.bankmanager.params.BmSearchParams;
import com.zhongyang.java.bankmanager.params.OrderInfoParams;
import com.zhongyang.java.bankmanager.params.PlatcustFundDetailParams;
import com.zhongyang.java.bankmanager.params.RepayDetailParams;
import com.zhongyang.java.bankmanager.returndata.BmSearchManager;
import com.zhongyang.java.system.authority.Authorities;
import com.zhongyang.java.system.authority.FireAuthority;
import com.zhongyang.java.zyfyback.controller.BaseController;

/**
 *@package com.zhongyang.java.bankmanager.controller
 *@filename BmSearchController.java
 *@date 2017年7月19日上午10:44:30
 *@author suzh
 */
@CrossOrigin
@Controller
public class BmSearchController extends BaseController{

	@Autowired
	private BmSearchBiz bmSearchBiz;
	
	/**
	 * 资金变动明细查询
	 *@date 上午11:07:14
	 *@param params
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.MAINTAINQUERY)
	@RequestMapping(value="/back/search/seachPlatcustFundDetail",method=RequestMethod.POST)
	public @ResponseBody BmSearchManager seachPlatcustFundDetail(@RequestBody PlatcustFundDetailParams params){
		return bmSearchBiz.seachPlatcustFundDetail(params);
	}
	/**
	 * 资金余额查询
	 *@date 下午2:29:38
	 *@param mobile
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.MAINTAINQUERY)
	@RequestMapping(value="/back/search/searchAccountInfo",method=RequestMethod.POST)
	public @ResponseBody BmSearchManager searchAccountInfo(@RequestBody BmSearchParams params){
		return bmSearchBiz.searchAccountInfo(params);
	}
	
	/**
	 * 还(回)款明细查询
	 *@date 下午3:33:37
	 *@param params
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.MAINTAINQUERY)
	@RequestMapping(value="/back/search/searchRepayDetail",method=RequestMethod.POST)
	public @ResponseBody BmSearchManager searchRepayDetail(@RequestBody RepayDetailParams params){
		return bmSearchBiz.searchRepayDetail(params);
	}
	
	/**
	 * 标的投资明细查询
	 *@date 下午4:24:39
	 *@param prod_id
	 *@param pagenum
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.MAINTAINQUERY)
	@RequestMapping(value="/back/search/searchLoanInvest",method=RequestMethod.POST)
	public @ResponseBody BmSearchManager searchLoanInvest(@RequestBody BmSearchParams params){
		return bmSearchBiz.searchLoanInvest(params);
	}
	
	/**
	 * 标的信息查询
	 *@date 下午4:44:03
	 *@param prod_id
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.MAINTAINQUERY)
	@RequestMapping(value="/back/search/searchLoanInfo",method=RequestMethod.POST)
	public @ResponseBody BmSearchManager searchLoanInfo(@RequestBody BmSearchParams params){
		return bmSearchBiz.searchLoanInfo(params);
	}
	
	/**
	 * 账户余额查询
	 *@date 下午5:09:52
	 *@param account
	 *@param acct_type
	 *@param fund_type
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.MAINTAINQUERY)
	@RequestMapping(value="/back/search/searchAccountBalance",method=RequestMethod.POST)
	public @ResponseBody BmSearchManager searchAccountBalance(@RequestBody BmSearchParams params){
		return bmSearchBiz.searchAccountBalance(params);
	}
	/**
	 * 标的账户余额查询
	 *@date 下午6:09:02
	 *@param prod_id
	 *@param type
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.MAINTAINQUERY)
	@RequestMapping(value="/back/search/searchLoanAccountBalance",method=RequestMethod.POST)
	public @ResponseBody BmSearchManager searchLoanAccountBalance(@RequestBody BmSearchParams params){
		return bmSearchBiz.searchLoanAccountBalance(params);
	}
	
	/**
	 * 订单状态查询
	 *@date 下午6:25:40
	 *@param params
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.MAINTAINQUERY)
	@RequestMapping(value="/back/search/searchOrderStatus",method=RequestMethod.POST)
	public @ResponseBody BmSearchManager searchOrderStatus(@RequestBody OrderInfoParams params){
		return bmSearchBiz.searchOrderStatus(params);
	}
	/**
	 * 充值订单查询
	 *@date 下午6:48:26
	 *@param original_serial_no
	 *@param occur_balance
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.MAINTAINQUERY)
	@RequestMapping(value="/back/search/searchChargeOrder",method=RequestMethod.POST)
	public @ResponseBody BmSearchManager searchChargeOrder(@RequestBody BmSearchParams params){
		return bmSearchBiz.searchChargeOrder(params);
	}
	/**
	 * 平台客户编号查询
	 *@date 下午6:55:43
	 *@param id_code
	 *@return
	 *@author suzh
	 */
	@FireAuthority(authorities=Authorities.MAINTAINQUERY)
	@RequestMapping(value="/back/search/searchPlatCust",method=RequestMethod.POST)
	public @ResponseBody BmSearchManager searchPlatCust(@RequestBody BmSearchParams params){
		return bmSearchBiz.searchPlatCust(params);
	}
}
