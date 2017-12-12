package com.zhongyang.java.bankmanager.biz.impl;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSONException;
import com.zhongyang.java.bankmanager.biz.BmAccountBackBiz;
import com.zhongyang.java.bankmanager.entity.BmAccount;
import com.zhongyang.java.bankmanager.entity.BmOrder;
import com.zhongyang.java.bankmanager.params.BmAccountParams;
import com.zhongyang.java.bankmanager.returndata.BmAccountReturn;
import com.zhongyang.java.bankmanager.service.BmAccountService;
import com.zhongyang.java.bankmanager.service.BmOrderService;
import com.zhongyang.java.bankmanager.util.RequestUtil;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.enumtype.OrderStatus;
import com.zhongyang.java.system.enumtype.OrderType;
import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.uitl.DESTextCipher;
import com.zhongyang.java.system.uitl.Message;
import com.zhongyang.java.system.uitl.SystemPro;
import com.zhongyang.java.system.uitl.Validator;
import com.zhongyang.java.zyfyback.pojo.Employee;
import com.zhongyang.java.zyfyback.pojo.User;
import com.zhongyang.java.zyfyback.service.UserService;

/**
 *@package com.zhongyang.java.bankmanager.biz.impl
 *@filename BmAccountBizImpl.java
 *@date 2017年7月20日上午10:51:12
 *@author suzh
 */
@Service
public class BmAccountBackBizImpl implements BmAccountBackBiz {
	
	static {
		Map<String, Object> sysMap = SystemPro.getProperties();
		ZYCF_ACCOUNTINFO_UPDATE = (String) sysMap.get("ZYCF_ACCOUNTINFO_UPDATE");
	}

	private static String ZYCF_ACCOUNTINFO_UPDATE;// 4.1.6客户信息变更

	private static Logger logger = Logger.getLogger(BmAccountBackBizImpl.class);

	@Autowired
	private BmOrderService bmOrderService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BmAccountService bmAccountService;
			
	@Override
	public BmAccountReturn modifyMobile(HttpServletRequest request,BmAccountParams params){
		
		BmAccountReturn bar=new BmAccountReturn();
		try{
			Employee emp=(Employee)WebUtils.getSessionAttribute(request, "zycfLoginEmp");
			
			if(emp==null)
				throw new UException(SystemEnum.USER_NOLOGIN,"没有登录");
			
			if(params==null)
				throw new UException(SystemEnum.PARAMS_ERROR,"参数接收异常");
			
			if(!Validator.isMobile(params.getOld_mobile()))
				throw new UException(SystemEnum.PARAMS_ERROR,"原手机号格式错误");
			
			if(!Validator.isMobile(params.getNew_mobile()))
				throw new UException(SystemEnum.PARAMS_ERROR,"新手机号格式错误");
			
			DESTextCipher cipher=DESTextCipher.getDesTextCipher();
			User user=new User();
			try {
				user.setMobile(cipher.encrypt(params.getOld_mobile()));
			} catch (GeneralSecurityException e) {
				e.printStackTrace();
				throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION,"数据加密异常");
			}
			User resUser = userService.queryUserByParams(user);
			
			if(resUser==null)
				throw new UException(SystemEnum.USER_NOT_EXISTS,"需变更用户不存在");
			
			
			try {
				user.setMobile(cipher.encrypt(params.getNew_mobile()));
			} catch (GeneralSecurityException e) {
				e.printStackTrace();
				throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION,"数据加密异常");
			}
			User resUser1=userService.queryUserByParams(user);
			if(resUser1!=null)
				throw new UException(SystemEnum.PARAMS_ERROR,"新手机号已存在");
			
			BmAccount account = bmAccountService.queryBmAccountByUser(resUser);
			
			User updateUser=new User();
			updateUser.setId(resUser.getId());
			try {
				updateUser.setMobile(cipher.encrypt(params.getNew_mobile()));
			} catch (GeneralSecurityException e) {
				e.printStackTrace();
				throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION,"数据加密异常");
			}
			
			if(account==null){
				userService.modifyUserByParams(updateUser);
				bar.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"变更成功，注意：用户没有开通存管账户"));
				return bar;
			}else{
				
				BmOrder order=new BmOrder();
				order.setOrderType(OrderType.ACCOUNTINFO_UPDATE);
				try {
					order=bmOrderService.bmOrderPersistence(order);
				} catch (Exception e1) {
					logger.info("订单入库异常"+e1.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED,"数据入库异常");
				}
				
				Map<String,Object>map=new HashMap<String,Object>();
				map.put("order_no", order.getOrderId());
				map.put("partner_trans_date", order.getOrderDate());
				map.put("partner_trans_time", order.getOrderTime());
				map.put("platcust", account.getPlatcust());
				map.put("cus_type", 1);
				map.put("mobile", params.getNew_mobile());
				
				Map<String, Object> sendRequest=null;
				try {
					sendRequest = RequestUtil.sendRequest(map, ZYCF_ACCOUNTINFO_UPDATE);
				} catch (JSONException | IOException e) {
					e.printStackTrace();
					logger.info(e.fillInStackTrace());
					throw new UException(SystemEnum.NET_CONNET_EXCEPTION,"网络连接异常");
				}
				String recode=sendRequest.get("recode").toString();
				String remsg=sendRequest.get("remsg").toString();
				
				order.setRecord(recode);
				order.setRemsg(remsg);
				if("10000".equals(recode)){
					order.setOrderStatus(OrderStatus.SUCCESSFUL);
					userService.modifyUserByParams(updateUser);
					BmAccount bmAccount=new BmAccount();
					bmAccount.setPreMobile(updateUser.getMobile());
					bmAccount.setUserId(updateUser.getId());
					try {
						bmAccountService.modifyBmAccountByParams(bmAccount);
					} catch (Exception e1) {
						logger.info("手机号修改异常"+e1.fillInStackTrace());
						throw new UException(SystemEnum.DATA_REFUSED,"手机号修改异常");
					}
					
					
					bar.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"客户信息变更成功"));
					
				}else{
					order.setOrderStatus(OrderStatus.FAILED);
					bar.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(),"客户信息变更失败【"+recode+"】"+remsg));
				}
				try {
					bmOrderService.bmOrderModify(order);
				} catch (Exception e1) {
					logger.info("订单修改异常"+e1.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED,"订单修改异常");
				}
				
				
			}
			
		}catch(UException e){
			logger.info("客户信息变更失败");
			logger.info(e.fillInStackTrace());
			bar.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return bar;
	}
}
