package com.zhongyang.java.bankmanager.biz.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.zhongyang.java.bankmanager.biz.SettledAccountBiz;
import com.zhongyang.java.bankmanager.entity.SettledRecharge;
import com.zhongyang.java.bankmanager.entity.SettledRefundDetail;
import com.zhongyang.java.bankmanager.entity.SettledWithdrawal;
import com.zhongyang.java.bankmanager.params.SettledParams;
import com.zhongyang.java.bankmanager.returndata.SettledReturn;
import com.zhongyang.java.bankmanager.service.SettledRechargeService;
import com.zhongyang.java.bankmanager.service.SettledRefundDetailService;
import com.zhongyang.java.bankmanager.service.SettledWithdrawalService;
import com.zhongyang.java.bankmanager.util.RequestUtil;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.uitl.ApplicationBean;
import com.zhongyang.java.system.uitl.FormatUtils;
import com.zhongyang.java.system.uitl.GetUUID;
import com.zhongyang.java.system.uitl.Message;
import com.zhongyang.java.system.uitl.SystemPro;

/**
 * @package com.zhongyang.java.bankmanager.biz.impl
 * @filename SettledAccountBizImpl.java
 * @date 2017年10月18日下午1:39:35
 * @author suzh
 */
@Service
public class SettledAccountBizImpl implements SettledAccountBiz {

	private static Logger logger = Logger.getLogger(SettledAccountBizImpl.class);

	static {
		Map<String, Object> sysMap = SystemPro.getProperties();
		ZYCF_SETTLED_RECHARGE = (String) sysMap.get("ZYCF_SETTLED_RECHARGE");
		ZYCF_SETTLED_REFUND_DETAIL = (String) sysMap.get("ZYCF_SETTLED_REFUND_DETAIL");
		ZYCF_SETTLED_WITHDRAWAL = (String) sysMap.get("ZYCF_SETTLED_WITHDRAWAL");
	}

	private static String ZYCF_SETTLED_RECHARGE;

	private static String ZYCF_SETTLED_REFUND_DETAIL;

	private static String ZYCF_SETTLED_WITHDRAWAL;

	@Autowired
	private SettledRechargeService settledRechargeService;

	@Autowired
	private SettledRefundDetailService settledRefundDetailService;

	@Autowired
	private SettledWithdrawalService settledWithdrawalService;

	@Override
	public SettledReturn settledRecharge(SettledParams params) {
		SettledReturn sr = new SettledReturn();
		try {
			if (params == null || params.getPaycheck_date() == null || "".equals(params.getPaycheck_date()))
				throw new UException(SystemEnum.PARAMS_ERROR, "对账时间不能为空");
			Date date = new Date();
			ApplicationBean application = new ApplicationBean();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("order_no", application.orderId());
			map.put("partner_trans_date", FormatUtils.simpleFormat(date));
			map.put("partner_trans_time", FormatUtils.timeFormat(date));
			map.put("precheck_flag", "0");
			map.put("paycheck_date", params.getPaycheck_date().replace("-", ""));
			String sendRequest = null;
			try {
				sendRequest = RequestUtil.sendReqResponse(map, ZYCF_SETTLED_RECHARGE);

			} catch (JSONException | IOException e) {
				e.printStackTrace();
				logger.info(e.fillInStackTrace());
				throw new UException(SystemEnum.NET_CONNET_EXCEPTION, "网络连接异常");
			}
			
			if(sendRequest.contains("recode")){
				JSONObject parseObject = JSON.parseObject(sendRequest);
				Map<String,Object>res=(Map<String,Object>)parseObject;
				sr.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(), res.get("recode").toString()+res.get("remsg").toString()));
				return sr;
			}
			
			List<SettledRecharge> recharges = new ArrayList<SettledRecharge>();
			
			String[] strs = sendRequest.split("\r\n");

			for (int i = 0; i < strs.length; i++) {
				if (i == 0 || i == strs.length - 1)
					continue;

				String[] split = strs[i].split("\\|");
				SettledRecharge recharge = new SettledRecharge();
				recharge.setId(GetUUID.getUniqueKey());
				recharge.setPlat_no(split[0]);
				recharge.setSettled_date(split[1].substring(0,4)+"-"+split[1].substring(4,6)+"-"+split[1].substring(6,8));
				recharge.setSettled_time(split[2].substring(0,2)+":"+split[2].substring(2,4)+":"+split[2].substring(4,6));
				recharge.setOrder_no(split[3]);
				recharge.setTrans_amt(new BigDecimal(split[4]).setScale(2, BigDecimal.ROUND_HALF_EVEN));
				recharge.setTrans_type(split[5]);
				recharge.setChannel_no(split[6]);
				recharge.setChannel_serial(split[7]);
				recharges.add(recharge);
			}

			if (recharges.size() != 0) {
				settledRechargeService.batchDeleteSettledRecharge(recharges);
				settledRechargeService.batchAddSettledRecharge(recharges);
			}

			sr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "充值对账下载成功"));
		} catch (UException e) {
			sr.setMessage(new Message(e.getCode().value(), e.getMessage()));
		}
		return sr;
	}
	@Override
	public SettledReturn settledRefundDetail(SettledParams params) {
		SettledReturn sr = new SettledReturn();
		try {
			if (params == null || params.getPaycheck_date() == null || "".equals(params.getPaycheck_date()))
				throw new UException(SystemEnum.PARAMS_ERROR, "对账时间不能为空");
			Date date = new Date();
			ApplicationBean application = new ApplicationBean();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("order_no", application.orderId());
			map.put("partner_trans_date", FormatUtils.simpleFormat(date));
			map.put("partner_trans_time", FormatUtils.timeFormat(date));
			map.put("paycheck_date", params.getPaycheck_date().replace("-", ""));
			String sendRequest = null;
			try {
				sendRequest = RequestUtil.sendReqResponse(map, ZYCF_SETTLED_REFUND_DETAIL);
			} catch (JSONException | IOException e) {
				e.printStackTrace();
				logger.info(e.fillInStackTrace());
				throw new UException(SystemEnum.NET_CONNET_EXCEPTION, "网络连接异常");
			}
			
			if(sendRequest.contains("recode")){
				JSONObject parseObject = JSON.parseObject(sendRequest);
				Map<String,Object>res=(Map<String,Object>)parseObject;
				sr.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(), res.get("recode").toString()+res.get("remsg").toString()));
				return sr;
			}
			
			List<SettledRefundDetail> details = new ArrayList<SettledRefundDetail>();

			String[] strs = sendRequest.split("\r\n");

			for (int i = 0; i < strs.length; i++) {
				if (i == 0 || i == strs.length - 1)
					continue;

				String[] split = strs[i].split("\\|");
				SettledRefundDetail refund = new SettledRefundDetail();
				refund.setId(GetUUID.getUniqueKey());
				refund.setPlat_no(split[0]);
				refund.setRefund_date(split[1]);
				refund.setRefund_time(split[2]);
				refund.setOrder_no(split[3]);
				refund.setTrans_amt(new BigDecimal(split[4]).setScale(2, BigDecimal.ROUND_HALF_EVEN));
				details.add(refund);
			}

			if (details.size() != 0) {
				settledRefundDetailService.batchDeleteSettledRefundDetail(details);
				settledRefundDetailService.batchAddSettledRefundDetail(details);
			}

			sr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "退票对账明细下载成功"));
		} catch (UException e) {
			sr.setMessage(new Message(e.getCode().value(), e.getMessage()));
		}
		return sr;
	}

	@Override
	public SettledReturn settledWithdrawal(SettledParams params) {
		SettledReturn sr = new SettledReturn();
		try {
			if (params == null || params.getPaycheck_date() == null || "".equals(params.getPaycheck_date()))
				throw new UException(SystemEnum.PARAMS_ERROR, "对账时间不能为空");
			Date date = new Date();
			ApplicationBean application = new ApplicationBean();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("order_no", application.orderId());
			map.put("partner_trans_date", FormatUtils.simpleFormat(date));
			map.put("partner_trans_time", FormatUtils.timeFormat(date));
			map.put("paycheck_date", params.getPaycheck_date().replace("-", ""));
			map.put("precheck_flag", "0");
			String sendRequest = null;
			try {
				sendRequest = RequestUtil.sendReqResponse(map, ZYCF_SETTLED_WITHDRAWAL);
			} catch (JSONException | IOException e) {
				e.printStackTrace();
				logger.info(e.fillInStackTrace());
				throw new UException(SystemEnum.NET_CONNET_EXCEPTION, "网络连接异常");
			}
			
			if(sendRequest.contains("recode")){
				JSONObject parseObject = JSON.parseObject(sendRequest);
				Map<String,Object>res=(Map<String,Object>)parseObject;
				sr.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(), res.get("recode").toString()+res.get("remsg").toString()));
				return sr;
			}
			
			List<SettledWithdrawal> withdrawals = new ArrayList<SettledWithdrawal>();
			String[] strs = sendRequest.split("\r\n");

			for (int i = 0; i < strs.length; i++) {
				if (i == 0 || i == strs.length - 1)
					continue;

				String[] split = strs[i].split("\\|");
				SettledWithdrawal withdrawal = new SettledWithdrawal();
				withdrawal.setId(GetUUID.getUniqueKey());
				withdrawal.setPlat_no(split[0]);
				withdrawal.setSettled_date(split[1].substring(0,4)+"-"+split[1].substring(4,6)+"-"+split[1].substring(6,8));
				withdrawal.setSettled_time(split[2]);
				withdrawal.setOrder_no(split[3]);
				withdrawal.setTrans_amt(new BigDecimal(split[4]).setScale(2, BigDecimal.ROUND_HALF_EVEN));
				withdrawal.setResult(split[5]);
				withdrawals.add(withdrawal);
			}

			if (withdrawals.size() != 0) {
				settledWithdrawalService.batchDeleteSettledWithdrawal(withdrawals);
				settledWithdrawalService.batchAddSettledWithdrawal(withdrawals);
			}

			sr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "提现对账下载成功"));
		} catch (UException e) {
			sr.setMessage(new Message(e.getCode().value(), e.getMessage()));
		}
		return sr;
	}

	@Override
	public SettledReturn searchRecharge(SettledParams params) {
		SettledReturn sr=new SettledReturn();
		try{
			if(params==null||params.getPage()==null)
				throw new UException(SystemEnum.PARAMS_ERROR, "参数错误");
			
			List<Object> res = settledRechargeService.queryByPage(params.getPage());
			params.getPage().setResults(res);
			sr.setPage(params.getPage());
			sr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"查询成功"));
		
		}catch(UException e){
			sr.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
			
		return sr;
	}

	@Override
	public SettledReturn searchWithdrawal(SettledParams params) {
		SettledReturn sr=new SettledReturn();
		try{
			if(params==null||params.getPage()==null)
				throw new UException(SystemEnum.PARAMS_ERROR, "参数错误");
			
			List<Object> res = settledWithdrawalService.queryByPage(params.getPage());
			params.getPage().setResults(res);
			sr.setPage(params.getPage());
			sr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"查询成功"));
		
		}catch(UException e){
			sr.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
			
		return sr;
	}

	@Override
	public SettledReturn searchRefundDetail(SettledParams params) {
		SettledReturn sr=new SettledReturn();
		try{
			if(params==null||params.getPage()==null)
				throw new UException(SystemEnum.PARAMS_ERROR, "参数错误");
			
			List<Object> res = settledRefundDetailService.queryByPage(params.getPage());
			params.getPage().setResults(res);
			sr.setPage(params.getPage());
			sr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"查询成功"));
		
		}catch(UException e){
			sr.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
			
		return sr;
	}

}
