package com.zhongyang.java.bankmanager.biz.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONException;
import com.zhongyang.java.bankmanager.biz.BmSearchBiz;
import com.zhongyang.java.bankmanager.entity.BmAccount;
import com.zhongyang.java.bankmanager.entity.BmOrder;
import com.zhongyang.java.bankmanager.entity.Company;
import com.zhongyang.java.bankmanager.params.BmSearchParams;
import com.zhongyang.java.bankmanager.params.OrderInfoParams;
import com.zhongyang.java.bankmanager.params.PlatcustFundDetailParams;
import com.zhongyang.java.bankmanager.params.RepayDetailParams;
import com.zhongyang.java.bankmanager.returndata.AccountBalance;
import com.zhongyang.java.bankmanager.returndata.BmAccountInfo;
import com.zhongyang.java.bankmanager.returndata.BmAccountVo;
import com.zhongyang.java.bankmanager.returndata.BmSearchManager;
import com.zhongyang.java.bankmanager.returndata.FundTransDetail;
import com.zhongyang.java.bankmanager.returndata.LoanAccount;
import com.zhongyang.java.bankmanager.returndata.LoanInfo;
import com.zhongyang.java.bankmanager.returndata.LoanInvestDetail;
import com.zhongyang.java.bankmanager.returndata.OrderInfo;
import com.zhongyang.java.bankmanager.returndata.RepayDetail;
import com.zhongyang.java.bankmanager.service.BmAccountService;
import com.zhongyang.java.bankmanager.service.BmOrderService;
import com.zhongyang.java.bankmanager.service.CompanyService;
import com.zhongyang.java.bankmanager.util.RequestUtil;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.enumtype.OrderStatus;
import com.zhongyang.java.system.enumtype.OrderType;
import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.uitl.DESTextCipher;
import com.zhongyang.java.system.uitl.Message;
import com.zhongyang.java.system.uitl.SystemPro;
import com.zhongyang.java.system.uitl.Validator;
import com.zhongyang.java.zyfyback.pojo.User;
import com.zhongyang.java.zyfyback.service.UserService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *@package com.zhongyang.java.bankmanager.biz.impl
 *@filename BmSearchBizImpl.java
 *@date 2017年7月19日上午11:12:14
 *@author suzh
 */
@Service
public class BmSearchBizImpl implements BmSearchBiz{
	
	static {
		Map<String, Object> sysMap = SystemPro.getProperties();
		ZYCF_FUND_LIST = (String) sysMap.get("ZYCF_FUND_LIST");
		ZYCF_ACCOUNT_INFO = (String) sysMap.get("ZYCF_ACCOUNT_INFO");
		ZYCF_REPAY_DETAIL = (String) sysMap.get("ZYCF_REPAY_DETAIL");
		ZYCF_LOAN_INVEST = (String) sysMap.get("ZYCF_LOAN_INVEST");
		ZYCF_LOAN_INFO = (String) sysMap.get("ZYCF_LOAN_INFO");
		ZYCF_ACCOUNT_BALANCE = (String) sysMap.get("ZYCF_ACCOUNT_BALANCE");
		ZYCF_LOANACCOUNT_BALANCE = (String) sysMap.get("ZYCF_LOANACCOUNT_BALANCE");
		ZYCF_ORDER_STATUS = (String) sysMap.get("ZYCF_ORDER_STATUS");
		ZYCF_CHARGE_ORDER_STATUS = (String) sysMap.get("ZYCF_CHARGE_ORDER_STATUS");
		ZYCF_PLATCUST_SEARCH = (String) sysMap.get("ZYCF_PLATCUST_SEARCH");
	}

	private static Logger logger=Logger.getLogger(BmSearchBizImpl.class);

	private static String ZYCF_FUND_LIST;// 4.6.1资金变动明细查询
	
	private static String ZYCF_ACCOUNT_INFO;//4.6.2资金余额查询
	
	private static String ZYCF_REPAY_DETAIL;//4.6.3还款明细查询

	private static String ZYCF_LOAN_INVEST;//4.6.4标的投资明细查询
	
	private static String ZYCF_LOAN_INFO;//4.6.5标的信息查询
	
	private static String ZYCF_ACCOUNT_BALANCE;//4.6.6账户余额明细查询
	
	private static String ZYCF_LOANACCOUNT_BALANCE;//4.6.7标的账户余额查询
	
	private static String ZYCF_ORDER_STATUS;//4.6.8订单状态查询
	
	private static String ZYCF_CHARGE_ORDER_STATUS;//4.6.9充值订单状态查询
	
	private static String ZYCF_PLATCUST_SEARCH;//4.6.10平台客户编号查询
	
	@Autowired
	private BmAccountService bmAccountService;
	
	@Autowired
	private BmOrderService bmOrderService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CompanyService companyService;
	
	@Override
	public BmSearchManager seachPlatcustFundDetail(PlatcustFundDetailParams params) {
		BmSearchManager bsm=new BmSearchManager();
		try{
			
			if(params==null||params.getPlatcust()==null||"".equals(params.getPlatcust()))
				throw new UException(SystemEnum.PARAMS_ERROR,"参数异常，手机号或平台客户号为必填项");
			
			Map<String,Object>map=new HashMap<String,Object>();
			
			if(Validator.isMobile(params.getPlatcust())){
				User user=new User();
				DESTextCipher cipher=DESTextCipher.getDesTextCipher();
				try {
					user.setMobile(cipher.encrypt(params.getPlatcust()));
				} catch (GeneralSecurityException e) {
					e.printStackTrace();
					throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION,"数据加密异常");
				}
				
				BmAccount account = bmAccountService.queryBmAccountByUser(user);

				map.put("platcust", account.getPlatcust());
			}else if("01".equals(params.getPlatcust())){
				map.put("platcust", "01");
			}
			
			if(params.getOrder_no()!=null&&!"".equals(params.getOrder_no()))
				map.put("order_no", params.getOrder_no());
			if(params.getStart_date()!=null&&!"".equals(params.getStart_date()))
				map.put("start_date", params.getStart_date());
			if(params.getEnd_date()!=null&&!"".equals(params.getEnd_date()))
				map.put("end_date", params.getEnd_date());
			if(params.getTrans_name()!=null&&!"".equals(params.getTrans_name()))
				map.put("trans_name", params.getTrans_name());
			if(params.getAcct_type()!=null&&!"".equals(params.getAcct_type()))
				map.put("acct_type", params.getAcct_type());
				
			map.put("pagenum", params.getPagenum());
			map.put("pagesize", params.getPagesize());
			
			
			Map<String, Object> sendRequest=null;
			try {
				sendRequest = RequestUtil.sendRequest(map, ZYCF_FUND_LIST);
			} catch (JSONException | IOException e) {
				e.printStackTrace();
				logger.info(e.fillInStackTrace());
				throw new UException(SystemEnum.NET_CONNET_EXCEPTION,"网络连接异常");
			}
			logger.info(sendRequest);
			
			String recode=(String)sendRequest.get("recode");
			String remsg=(String)sendRequest.get("remsg");
			
			if("10000".equals(recode)){
				
				JSONObject jb=JSONObject.fromObject(sendRequest.get("data"));
				JSONArray ja = jb.getJSONArray("fundList");
				
				List<FundTransDetail> fundTransDetails = new ArrayList<FundTransDetail>();

		        // 循环添加FundTransDetail对象（可能有多个）
		        for (int i = 0; i < ja.size(); i++) {
		        	FundTransDetail transDetail = new FundTransDetail();
		        	transDetail.setPlat_no(ja.getJSONObject(i).getString("plat_no"));
		        	transDetail.setPlatcust(ja.getJSONObject(i).getString("platcust"));
		        	transDetail.setTrans_date(ja.getJSONObject(i).getString("trans_date"));
		        	transDetail.setTrans_time(ja.getJSONObject(i).getString("trans_time"));
		        	transDetail.setAmt(new BigDecimal(ja.getJSONObject(i).getString("amt")));
		        	transDetail.setAmt_type(ja.getJSONObject(i).getString("amt_type"));
		            fundTransDetails.add(transDetail);
		        }
		        
				bsm.setList(fundTransDetails);
				bsm.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"查询成功"));
			}else
				bsm.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(),remsg));
			
		}catch(UException e){
			logger.info("资金变动明细查询出现异常");
			logger.info(e.fillInStackTrace());
			bsm.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return bsm;
	}

	
	@Override
	public BmSearchManager searchAccountInfo(BmSearchParams params) {
		BmSearchManager bsm=new BmSearchManager();
		Map<String,Object>map=new HashMap<String,Object>();
		try{
			if(params==null||params.getAccount()==null)
				throw new UException(SystemEnum.PARAMS_ERROR,"账户编号不能为空");
				
			logger.info("用户资金余额查询:"+params);
			if(Validator.isMobile(params.getAccount())){
				DESTextCipher cipher=DESTextCipher.getDesTextCipher();
				User user=new User();
				try {
					user.setMobile(cipher.encrypt(params.getAccount()));
				} catch (GeneralSecurityException e) {
					e.printStackTrace();
					throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION,"数据加密异常");
				}
				BmAccount account = bmAccountService.queryBmAccountByUser(user);
				if(account==null||account.getPlatcust()==null)
					throw new UException(SystemEnum.PARAMS_ERROR,"用户未开通存管账户");
				
				map.put("account", account.getPlatcust());
			}else
				map.put("account", params.getAccount());
		
			Map<String, Object> sendRequest=null;
			try {
				sendRequest = RequestUtil.sendRequest(map, ZYCF_ACCOUNT_INFO);
			} catch (JSONException | IOException e) {
				e.printStackTrace();
				logger.info(e.fillInStackTrace());
				throw new UException(SystemEnum.NET_CONNET_EXCEPTION,"网络连接异常");
			}
			logger.info("存管系统返回信息："+sendRequest.toString());
			
			String recode=(String)sendRequest.get("recode");
			String remsg=(String)sendRequest.get("remsg");
			
			if("10000".equals(recode)){
				@SuppressWarnings("unchecked")
				Map<String,Object> res=(Map<String,Object>)sendRequest.get("data");
				BmAccountInfo accountInfo=new BmAccountInfo();
				accountInfo.setBalance(new BigDecimal(res.get("balance").toString()));
				accountInfo.setFrozen_amount(new BigDecimal(res.get("frozen_amount").toString()));
				accountInfo.setAccount(params.getAccount());
				accountInfo.setPlatCust((String)map.get("account"));
				
				
				bsm.setAccountInfo(accountInfo);
				bsm.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"查询成功"));
			}else
				bsm.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(),remsg));
		
		}catch(UException e){
			logger.info("资金余额查询失败");
			logger.info(e.fillInStackTrace());
			bsm.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return bsm;
	}


	
	@Override
	public BmSearchManager searchRepayDetail(RepayDetailParams params) {
		
		BmSearchManager bsm=new BmSearchManager();
		Map<String,Object>map=new HashMap<String,Object>();
		try{
			if(params==null)
				throw new UException(SystemEnum.PARAMS_ERROR,"擦书接收异常");
				
			logger.info("还款明细查询:"+params);
			if(Validator.isMobile(params.getMobile())){
				DESTextCipher cipher=DESTextCipher.getDesTextCipher();
				User user=new User();
				try {
					user.setMobile(cipher.encrypt(params.getMobile()));
				} catch (GeneralSecurityException e) {
					e.printStackTrace();
					throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION,"数据加密异常");
				}
				BmAccount account = bmAccountService.queryBmAccountByUser(user);
				if(account.getPlatcust()==null)
					throw new UException(SystemEnum.PARAMS_ERROR,"用户未开通存管账户");
				
				map.put("platcust", account.getPlatcust());
			}else
				throw new UException(SystemEnum.PARAMS_ERROR,"用户手机号不正确");
			
				map.put("type", params.getType());
				
				if(params.getProd_id()!=null&&!"".equals(params.getProd_id()))
					map.put("prod_id", params.getProd_id());
				
				if(params.getStart_date()!=null&&!"".equals(params.getStart_date()))
					map.put("start_date", params.getStart_date());
				
				if(params.getEnd_date()!=null&&!"".equals(params.getEnd_date()))
					map.put("end_date", params.getEnd_date());
				
				map.put("pagenum", params.getPagenum());
				map.put("pagesize", params.getPagesize());
		
			Map<String, Object> sendRequest=null;
			try {
				sendRequest = RequestUtil.sendRequest(map, ZYCF_REPAY_DETAIL);
			} catch (JSONException | IOException e) {
				e.printStackTrace();
				logger.info(e.fillInStackTrace());
				throw new UException(SystemEnum.NET_CONNET_EXCEPTION,"网络连接异常");
			}
			logger.info("存管系统返回信息："+sendRequest.toString());
			
			String recode=(String)sendRequest.get("recode");
			String remsg=(String)sendRequest.get("remsg");
			
			if("10000".equals(recode)){
				@SuppressWarnings("unchecked")
				List<Map<String,Object>>res=(List<Map<String,Object>>)sendRequest.get("data");
				List<RepayDetail>repayDetails=new ArrayList<RepayDetail>();
				for (Map<String, Object> mp : res) {
					RepayDetail detail=new RepayDetail();
					detail.setPlat_no(mp.get("plat_no").toString());
					detail.setProd_name(mp.get("prod_name").toString());
					detail.setReal_repay_amt(new BigDecimal(mp.get("real_repay_amt").toString()));
					detail.setReal_repay_date(mp.get("real_repay_date").toString());
					detail.setRepay_amt(mp.get("repay_amt")==null?null:new BigDecimal(mp.get("repay_amt").toString()));
					detail.setRepay_date(mp.get("repay_date").toString());
					detail.setRepay_num(mp.get("repay_num").toString());
					detail.setStatus(this.getOrderStatus(mp.get("status").toString()));
					repayDetails.add(detail);
				}
				
				bsm.setRepayDetails(repayDetails);
				bsm.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"查询成功"));
			}else
				bsm.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(),remsg));
		
		}catch(UException e){
			logger.info("还(回)款明细查询失败");
			logger.info(e.fillInStackTrace());
			bsm.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return bsm;
	}


	
	@Override
	public BmSearchManager searchLoanInvest(BmSearchParams params) {
		BmSearchManager bsm=new BmSearchManager();
		Map<String,Object>map=new HashMap<String,Object>();
		try{
			if(params==null||params.getProd_id()==null)
				throw new UException(SystemEnum.PARAMS_ERROR,"参数接收异常");
			
			logger.info("标的投资明细查询:"+params.getProd_id());
			
			map.put("prod_id",params.getProd_id());
			map.put("pagenum", params.getPagenum());
			map.put("pagesize", params.getPagesize());
		
			Map<String, Object> sendRequest=null;
			try {
				sendRequest = RequestUtil.sendRequest(map, ZYCF_LOAN_INVEST);
			} catch (JSONException | IOException e) {
				e.printStackTrace();
				logger.info(e.fillInStackTrace());
				throw new UException(SystemEnum.NET_CONNET_EXCEPTION,"网络连接异常");
			}
			logger.info("存管系统返回信息："+sendRequest.toString());
			
			String recode=(String)sendRequest.get("recode");
			String remsg=(String)sendRequest.get("remsg");
			
			if("10000".equals(recode)){
				@SuppressWarnings("unchecked")
				List<Map<String,Object>>res=(List<Map<String,Object>>)sendRequest.get("data");
				List<LoanInvestDetail>loanInvestDetails=new ArrayList<LoanInvestDetail>();
				for (Map<String, Object> mp : res) {
					LoanInvestDetail detail=new LoanInvestDetail();
					detail.setIn_interest(mp.get("in_interest").toString());
					detail.setName(mp.get("name").toString());
					detail.setPlat_name(mp.get("plat_name").toString());
					detail.setPlatcust(mp.get("platcust").toString());
					detail.setProd_name(mp.get("prod_name").toString());
					detail.setTrans_date(mp.get("trans_date").toString());
					detail.setTrans_time(mp.get("trans_time").toString());
					detail.setVol(new BigDecimal(mp.get("vol").toString()));

					loanInvestDetails.add(detail);
				}
				
				bsm.setLoanInvestDetails(loanInvestDetails);
				bsm.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"查询成功"));
			}else
				bsm.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(),remsg));
		
		}catch(UException e){
			logger.info("标的投资明细查询失败");
			logger.info(e.fillInStackTrace());
			bsm.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return bsm;
	}


	
	@Override
	public BmSearchManager searchLoanInfo(BmSearchParams params) {
		BmSearchManager bsm=new BmSearchManager();
		Map<String,Object>map=new HashMap<String,Object>();
		try{
			if(params==null||params.getProd_id()==null)
				throw new UException(SystemEnum.PARAMS_ERROR,"参数接收异常");
				
			logger.info("标的信息查询:"+params.getProd_id());
			
			map.put("prod_id",params.getProd_id());
		
			Map<String, Object> sendRequest=null;
			try {
				sendRequest = RequestUtil.sendRequest(map, ZYCF_LOAN_INFO);
			} catch (JSONException | IOException e) {
				e.printStackTrace();
				logger.info(e.fillInStackTrace());
				throw new UException(SystemEnum.NET_CONNET_EXCEPTION,"网络连接异常");
			}
			logger.info("存管系统返回信息："+sendRequest.toString());
			
			String recode=(String)sendRequest.get("recode");
			String remsg=(String)sendRequest.get("remsg");
			
			if("10000".equals(recode)){
				@SuppressWarnings("unchecked")
				Map<String,Object>res=(Map<String,Object>)sendRequest.get("data");
				
				LoanInfo loanInfo=new LoanInfo();
				loanInfo.setChargeOff_auto(res.get("chargeOff_auto").toString());
				loanInfo.setIst_year(res.get("ist_year").toString());
				loanInfo.setPlat_no(res.get("plat_no").toString());
				loanInfo.setProd_account(res.get("prod_account").toString());
				loanInfo.setProd_id(res.get("prod_id").toString());
				loanInfo.setProd_name(res.get("prod_name").toString());
				loanInfo.setProd_state(res.get("prod_state").toString());
				loanInfo.setRemain_limit(res.get("remain_limit").toString());
				loanInfo.setSaled_limit(res.get("saled_limit").toString());
				loanInfo.setTotal_limit(res.get("total_limit").toString());
				
				bsm.setLoanInfo(loanInfo);
				bsm.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"查询成功"));
			}else
				bsm.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(),remsg));
		
		}catch(UException e){
			logger.info("标的信息查询失败");
			logger.info(e.fillInStackTrace());
			bsm.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return bsm;
	}


	
	@Override
	public BmSearchManager searchAccountBalance(BmSearchParams params) {
		BmSearchManager bsm=new BmSearchManager();
		Map<String,Object>map=new HashMap<String,Object>();
		try{
			if(params==null||params.getAccount()==null)
				throw new UException(SystemEnum.PARAMS_ERROR,"参数接收异常");
				
			logger.info("账户余额查询:"+params.getAccount());
			if(Validator.isMobile(params.getAccount())){
				DESTextCipher cipher=DESTextCipher.getDesTextCipher();
				User user=new User();
				try {
					user.setMobile(cipher.encrypt(params.getAccount()));
				} catch (GeneralSecurityException e) {
					e.printStackTrace();
					throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION,"数据加密异常");
				}
				BmAccount acc = bmAccountService.queryBmAccountByUser(user);
				if(acc==null)
					throw new UException(SystemEnum.PARAMS_ERROR,"用户不存在");
				
				if(acc.getPlatcust()==null)
					throw new UException(SystemEnum.PARAMS_ERROR,"用户未开通存管账户");
				
				map.put("account", acc.getPlatcust());
			}else
				map.put("account", params.getAccount());
			
			if(params.getAcct_type()!=null&&!"".equals(params.getAcct_type()))
				map.put("acct_type",params.getAcct_type());
			
			if(params.getFund_type()!=null&&!"".equals(params.getFund_type()))
				map.put("fund_type",params.getFund_type());
			
			
			Map<String, Object> sendRequest=null;
			try {
				sendRequest = RequestUtil.sendRequest(map, ZYCF_ACCOUNT_BALANCE);
			} catch (JSONException | IOException e) {
				e.printStackTrace();
				logger.info(e.fillInStackTrace());
				throw new UException(SystemEnum.NET_CONNET_EXCEPTION,"网络连接异常");
			}
			logger.info("存管系统返回信息："+sendRequest.toString());
			
			String recode=(String)sendRequest.get("recode");
			String remsg=(String)sendRequest.get("remsg");
			
			if("10000".equals(recode)){
				@SuppressWarnings("unchecked")
				Map<String,Object>res=(Map<String,Object>)sendRequest.get("data");
				AccountBalance accountBalance=new AccountBalance();
				accountBalance.setBalance(res.get("balance").toString());
				accountBalance.setFrozen_amount(res.get("frozen_amount").toString());
				
				bsm.setAccountBalance(accountBalance);
				bsm.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"查询成功"));
			}else
				bsm.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(),remsg));
		
		}catch(UException e){
			logger.info("账户余额查询失败");
			logger.info(e.fillInStackTrace());
			bsm.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return bsm;
	}


	
	@Override
	public BmSearchManager searchLoanAccountBalance(BmSearchParams params) {
		
		BmSearchManager bsm=new BmSearchManager();
		Map<String,Object>map=new HashMap<String,Object>();
		try{
			if(params==null||params.getProd_id()==null)
				throw new UException(SystemEnum.PARAMS_ERROR,"参数接收异常");
			
			logger.info("标的账户余额查询:"+params.getProd_id());
			map.put("prod_id", params.getProd_id());
			
			if(params.getType()!=null&&!"".equals(params.getType()))
				map.put("type",params.getType());
			
			
			Map<String, Object> sendRequest=null;
			try {
				sendRequest = RequestUtil.sendRequest(map, ZYCF_LOANACCOUNT_BALANCE);
			} catch (IOException e) {
				e.printStackTrace();
				logger.info(e.fillInStackTrace());
				throw new UException(SystemEnum.NET_CONNET_EXCEPTION,"网络异常");
			}
			logger.info("存管系统返回信息："+sendRequest.toString());
			
			String recode=(String)sendRequest.get("recode");
			String remsg=(String)sendRequest.get("remsg");
			
			if("10000".equals(recode)){
				String balance=sendRequest.get("data").toString();
				LoanAccount loanAccount=new LoanAccount();
				loanAccount.setProd_id(params.getProd_id());
				loanAccount.setBalance(balance);
				
				bsm.setLoanAccount(loanAccount);
				bsm.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"查询成功"));
			}else
				bsm.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(),remsg));
		
		}catch(UException e){
			logger.info("标的账户余额查询失败");
			logger.info(e.fillInStackTrace());
			bsm.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return bsm;
	}


	
	@Override
	public BmSearchManager searchOrderStatus(OrderInfoParams params) {
		BmSearchManager bsm=new BmSearchManager();
		Map<String,Object>map=new HashMap<String,Object>();
		try{
			if(params==null||params.getQuery_order_no()==null)
				throw new UException(SystemEnum.PARAMS_ERROR,"参数接收异常");
			
			logger.info("订单状态查询："+params.getQuery_order_no());
			
			BmOrder order=new BmOrder();
			order.setOrderType(OrderType.ORDER_SEARCH);
			try {
				order=bmOrderService.bmOrderPersistence(order);
			} catch (Exception e1) {
				logger.info("订单入库异常"+e1.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED,"订单入库异常");
			}
			
			map.put("order_no",order.getOrderId());
			map.put("partner_trans_date",order.getOrderDate());
			map.put("partner_trans_time", order.getOrderTime());
			
			if(params.getQuery_order_no()!=null&&!"".equals(params.getQuery_order_no()))
				map.put("query_order_no", params.getQuery_order_no());
			
			Map<String, Object> sendRequest=null;
			try {
				sendRequest = RequestUtil.sendRequest(map, ZYCF_ORDER_STATUS);
			}catch (JSONException | IOException e) {
				e.printStackTrace();
				logger.info(e.fillInStackTrace());
				throw new UException(SystemEnum.NET_CONNET_EXCEPTION,"网络连接异常");
			}
			logger.info("存管系统返回信息："+sendRequest.toString());
			
			String recode=(String)sendRequest.get("recode");
			String remsg=(String)sendRequest.get("remsg");
			
			order.setRecord(recode);
			order.setRemsg(remsg);
			if("10000".equals(recode)){
				order.setOrderStatus(OrderStatus.SUCCESSFUL);
				
				@SuppressWarnings("unchecked")
				Map<String,Object>res=(Map<String,Object>)sendRequest.get("data");
				
				OrderInfo orderInfo=new OrderInfo();
				orderInfo.setPlat_no(res.get("plat_no").toString());
				orderInfo.setQuery_order_no(res.get("query_order_no").toString());
				orderInfo.setStatus(this.getOrderStatus(res.get("status").toString()));
				
				bsm.setOrderInfo(orderInfo);
				bsm.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"查询成功"));
			}else{
				order.setOrderStatus(OrderStatus.FAILED);
				bsm.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(),remsg));
			}
			try {
				bmOrderService.bmOrderModify(order);
			} catch (Exception e1) {
				logger.info("修改订单状态异常"+e1.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED,"修改订单状态异常");
			}
				
		
		}catch(UException e){
			logger.info("订单状态查询失败");
			logger.info(e.fillInStackTrace());
			bsm.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return bsm;
	}
	
	String getOrderStatus(String status){
		String orderStatus=null;
		switch (status) {
			case "0":orderStatus="处理中";break;
			case "1":orderStatus="交易成功";break;
			case "2":orderStatus="交易失败";break;
			case "11":orderStatus="请求成功";break;
			case "12":orderStatus="请求失败";break;
			case "21":orderStatus="确认成功";break;
			case "22":orderStatus="确认失败";break;
		}
		return orderStatus;
	}



	@Override
	public BmSearchManager searchChargeOrder(BmSearchParams params) {
		BmSearchManager bsm=new BmSearchManager();
		Map<String,Object>map=new HashMap<String,Object>();
		try{
			if(params==null||params.getOriginal_serial_no()==null)
				throw new UException(SystemEnum.PARAMS_ERROR,"参数接收异常");
			
			logger.info("充值订单状态查询:"+params.getOriginal_serial_no());
			map.put("original_serial_no", params.getOriginal_serial_no());
			map.put("occur_balance",params.getOccur_balance().setScale(2, BigDecimal.ROUND_HALF_EVEN));
					
			Map<String, Object> sendRequest=null;
			try {
				sendRequest = RequestUtil.sendRequest(map, ZYCF_CHARGE_ORDER_STATUS);
			} catch (JSONException | IOException e) {
				e.printStackTrace();
				logger.info(e.fillInStackTrace());
				throw new UException(SystemEnum.NET_CONNET_EXCEPTION,"网络连接异常");
			}
			logger.info("存管系统返回信息："+sendRequest.toString());
			
			String recode=(String)sendRequest.get("recode");
			String remsg=(String)sendRequest.get("remsg");
			
			if("10000".equals(recode)){
				@SuppressWarnings("unchecked")
				Map<String,Object>res=(Map<String,Object>)sendRequest.get("data");
				
				OrderInfo orderInfo=new OrderInfo();
				orderInfo.setPlat_no(res.get("plat_no").toString());
				orderInfo.setQuery_order_no(res.get("query_order_no").toString());
				orderInfo.setStatus(this.getOrderStatus(res.get("status").toString()));
				
				bsm.setOrderInfo(orderInfo);
				bsm.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"查询成功"));
			}else
				bsm.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(),remsg));
		
		}catch(UException e){
			logger.info("充值订单状态查询失败");
			logger.info(e.fillInStackTrace());
			bsm.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return bsm;
	}


	
	@Override
	public BmSearchManager searchPlatCust(BmSearchParams params) {
		BmSearchManager bsm=new BmSearchManager();
		Map<String,Object>map=new HashMap<String,Object>();
		try{
			if(params==null||params.getMobile()==null)
				throw new UException(SystemEnum.PARAMS_ERROR,"参数接收错误");
				
			logger.info("客户编号查询,手机号:"+params.getMobile());
			if(!Validator.isMobile(params.getMobile()))
				throw new UException(SystemEnum.PARAMS_ERROR,"手机号格式错误");
			
			User user=new User();
			DESTextCipher cipher=DESTextCipher.getDesTextCipher();
			try {
				user.setMobile(cipher.encrypt(params.getMobile()));
				user=userService.queryUserByParams(user);
				if(user==null)
					throw new UException(SystemEnum.PARAMS_ERROR,"用户不存在");
				
				String idCode=user.getIdCode();
				if(idCode==null)
					throw new UException(SystemEnum.UN_AUTHENTICATION,"用户未实名认证");
				
				idCode=cipher.decrypt(idCode);
				if(user.getCustType()==1){
					map.put("id_code", idCode);
					map.put("id_type",1);
					map.put("cus_type",1);
				}else{
					map.put("cus_type", 2);
					Company company=new Company();
					company.setUserId(user.getId());
					company=companyService.queryCompanyByParams(company);
					map.put("org_name", company.getOrg_name());
					if(company.getBank_license()!=null)
						map.put("bank_license", cipher.decrypt(company.getBank_license()));
					if(company.getBusiness_license()!=null)
						map.put("business_license", cipher.decrypt(company.getBusiness_license()));
				}
			} catch (GeneralSecurityException e) {
				e.printStackTrace();
				throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION,"数据加(解)密异常");
			}
					
			Map<String, Object> sendRequest=null;
			try {
				sendRequest = RequestUtil.sendRequest(map, ZYCF_PLATCUST_SEARCH);
			} catch (JSONException | IOException e) {
				e.printStackTrace();
				logger.info(e.fillInStackTrace());
				throw new UException(SystemEnum.NET_CONNET_EXCEPTION,"网络连接异常");
			}
			logger.info("存管系统返回信息："+sendRequest.toString());
			
			String recode=(String)sendRequest.get("recode");
			String remsg=(String)sendRequest.get("remsg");
			
			if("10000".equals(recode)){
				@SuppressWarnings("unchecked")
				Map<String,Object>res=(Map<String,Object>)sendRequest.get("data");
				
				BmAccountVo account=new BmAccountVo();
				account.setPlatcust(res.get("platcust").toString());
				switch (res.get("status").toString()) {
					case "0":account.setStatus("注销");break;
					case "1":account.setStatus("正常");break;
					case "2":account.setStatus("注册");break;
				}
				account.setAvailable_status(res.get("available_status").toString().equals("1")?"正常":"其他");
				account.setIs_card_bind(res.get("is_card_bind").toString().equals("1")?"已绑":"未绑");
				
				bsm.setAccount(account);
				bsm.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"查询成功"));
			}else
				bsm.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(),remsg));
		
		}catch(UException e){
			logger.info("客户编号查询成功");
			logger.info(e.fillInStackTrace());
			bsm.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return bsm;
	}
	
}
