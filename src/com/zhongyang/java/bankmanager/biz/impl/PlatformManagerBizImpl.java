package com.zhongyang.java.bankmanager.biz.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSONException;
import com.zhongyang.java.bankmanager.biz.PlatformManagerBiz;
import com.zhongyang.java.bankmanager.entity.BmAccount;
import com.zhongyang.java.bankmanager.entity.BmOrder;
import com.zhongyang.java.bankmanager.entity.UntredtedApplication;
import com.zhongyang.java.bankmanager.params.PlatformManagerParams;
import com.zhongyang.java.bankmanager.params.TransAmountParams;
import com.zhongyang.java.bankmanager.returndata.PlatformManagerReturn;
import com.zhongyang.java.bankmanager.service.BmAccountService;
import com.zhongyang.java.bankmanager.service.BmOrderService;
import com.zhongyang.java.bankmanager.service.UntredtedApplicationService;
import com.zhongyang.java.bankmanager.util.RequestUtil;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.enumtype.FundRecordOperation;
import com.zhongyang.java.system.enumtype.FundRecordStatus;
import com.zhongyang.java.system.enumtype.FundRecordType;
import com.zhongyang.java.system.enumtype.OrderStatus;
import com.zhongyang.java.system.enumtype.OrderType;
import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.uitl.DESTextCipher;
import com.zhongyang.java.system.uitl.FormatUtils;
import com.zhongyang.java.system.uitl.GetUUID;
import com.zhongyang.java.system.uitl.Message;
import com.zhongyang.java.system.uitl.SystemPro;
import com.zhongyang.java.system.uitl.Validator;
import com.zhongyang.java.zyfyback.pojo.Employee;
import com.zhongyang.java.zyfyback.pojo.FundRecord;
import com.zhongyang.java.zyfyback.pojo.PlatFundRecord;
import com.zhongyang.java.zyfyback.pojo.User;
import com.zhongyang.java.zyfyback.pojo.UserFund;
import com.zhongyang.java.zyfyback.service.FundRecordService;
import com.zhongyang.java.zyfyback.service.PlatFundRecordService;
import com.zhongyang.java.zyfyback.service.UserFundService;
import com.zhongyang.java.zyfyback.service.UserService;

/**
 * @package com.zhongyang.java.bankmanager.biz.impl
 * @filename PlatformManagerBizImpl.java
 * @date 2017年7月7日上午10:58:53
 * @author suzh
 */
@Service
public class PlatformManagerBizImpl implements PlatformManagerBiz {

	static {
		Map<String, Object> sysMap = SystemPro.getProperties();
		ZYCF_PLATFORM_CHARGE = (String) sysMap.get("ZYCF_PLATFORM_CHARGE");
		ZYCF_PLAT_MOBILE = (String) sysMap.get("ZYCF_PLAT_MOBILE");
		ZYCF_PLATFORM_WITHDRAW = (String) sysMap.get("ZYCF_PLATFORM_WITHDRAW");
		ZYCF_PLATFORM_TRANS = (String) sysMap.get("ZYCF_PLATFORM_TRANS");
		ZYCF_PLAT_TO_PERSON = (String) sysMap.get("ZYCF_PLAT_TO_PERSON");
	}

	private static Logger logger = Logger.getLogger(PlatformManagerBiz.class);

	private static String ZYCF_PLATFORM_CHARGE;

	private static String ZYCF_PLAT_MOBILE;

	private static String ZYCF_PLATFORM_WITHDRAW;

	private static String ZYCF_PLATFORM_TRANS;

	private static String ZYCF_PLAT_TO_PERSON;

	@Autowired
	private PlatFundRecordService platFundRecordService;

	@Autowired
	private BmOrderService bmOrderService;

	@Autowired
	private UserService userService;

	@Autowired
	private UntredtedApplicationService untredtedApplicationService;

	@Autowired
	private BmAccountService bmAccountService;

	@Autowired
	private FundRecordService fundRecordService;

	@Autowired
	private UserFundService userFundService;

	@Override
	public PlatformManagerReturn companyCharge(HttpServletRequest request, PlatformManagerParams params) {
		PlatformManagerReturn pmr = new PlatformManagerReturn();
		try {
			Employee emp = (Employee) WebUtils.getSessionAttribute(request, "zycfLoginEmp");
			if (emp == null)
				throw new UException(SystemEnum.USER_NOLOGIN, "您没有登录");

			if (params == null || params.getAmount().doubleValue() == 0)
				throw new UException(SystemEnum.PARAMS_ERROR, "充值金额不能为空");

			User user = new User();
			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			try {
				user.setMobile(cipher.encrypt(ZYCF_PLAT_MOBILE));
			} catch (GeneralSecurityException e) {
				throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION, "数据加密异常");
			}
			user = userService.queryUserByParams(user);

			logger.info("====================添加平台充值订单=====================");
			BmOrder order = new BmOrder();
			order.setOrderType(OrderType.DEPOSIT);
			order.setUserId(user.getId());
			order.setRemark("企业充值");
			order.setUserId(user == null ? null : user.getId());
			try {
				bmOrderService.bmOrderPersistence(order);
			} catch (Exception e1) {
				logger.info("订单入库异常"+e1.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED,"订单入库异常");
			}
			logger.info("====================平台充值订单添加完成==================");

			logger.info("====================添加平台充值资金记录==================");
			PlatFundRecord record = new PlatFundRecord();
			record.setAccount("1");
			record.setAmount(params.getAmount());
			record.setDescription("平台充值");
			record.setType(FundRecordType.DEPOSIT);
			record.setStatus(FundRecordStatus.PROCESSING);
			record.setOrderId(order.getOrderId());
			record.setOperation(FundRecordOperation.IN);
			record.setUserId(user == null ? null : user.getId());
			record.setRemark("操作员工:" + emp.getEmpName());
			try {
				platFundRecordService.addPlatFundRecord(record);
			} catch (Exception e1) {
				logger.info("添加平台资金记录异常"+e1.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED,"添加平台资金记录异常");
			}
			
			logger.info("====================平台充值资金记录添加完成==================");

			logger.info("====================构建存管系统平台充值需要的参数==============");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("order_no", order.getOrderId());
			map.put("partner_trans_date", order.getOrderDate());
			map.put("partner_trans_time", order.getOrderTime());
			map.put("amount", params.getAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN));
			logger.info("====================存管系统平台充值需要的参数构建完成==============");

			logger.info("====================平台充值请求发往存管系统==============");
			logger.info("请求详情:" + map.toString());
			Map<String, Object> sendRequest=null;
			try {
				sendRequest = RequestUtil.sendRequest(map, ZYCF_PLATFORM_CHARGE);
			} catch (JSONException | IOException e) {
				e.printStackTrace();
				logger.info(e.fillInStackTrace());
				throw new UException(SystemEnum.NET_CONNET_EXCEPTION,"网络连接异常");
			}
			logger.info("存管系统响应平台充值请求：" + sendRequest.toString());
			//"recode":"10000","remsg":"请求充值成功"
			String code = (String) sendRequest.get("code");
			String recode = (String) sendRequest.get("recode");
			String remsg = (String) sendRequest.get("remsg");
			order.setRecord(recode);
			order.setRemsg(remsg);
			if ("10000".equals(recode)&&"1".equals(code)) {
				logger.info("平台充值入账成功");
				order.setOrderStatus(OrderStatus.SUCCESSFUL);
				order.setRemark("平台充值成功");

				record.setStatus(FundRecordStatus.SUCCESSFUL);
				record.setRemark("平台充值成功");

				pmr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "充值成功"));

			} else {
				logger.info("平台充值入账失败");
				order.setOrderStatus(OrderStatus.FAILED);
				order.setRemark("平台充值失败:recode["+recode+"]code["+code+"]remsg["+remsg);

				record.setStatus(FundRecordStatus.FAILED);
				record.setRemark("平台充值失败:recode["+recode+"]code["+code+"]remsg["+remsg);

				pmr.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(), "平台充值失败:recode["+recode+"]code["+code+"]remsg["+remsg));
			}
			try {
				bmOrderService.bmOrderModify(order);
			} catch (Exception e1) {
				logger.info("修改订单状态异常"+e1.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED,"修改订单状态异常");
			}
			
			try {
				platFundRecordService.modifyPlatFundRecord(record);
			} catch (Exception e1) {
				logger.info("修改平台资金记录异常"+e1.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED,"修改平台资金记录异常");
			}
			

		} catch (UException e) {
			logger.info("平台充值出现异常");
			logger.info(e.fillInStackTrace());
			pmr.setMessage(new Message(e.getCode().value(), e.getMessage()));
		}
		return pmr;
	}

	@Override
	public PlatformManagerReturn companyWithdraw(HttpServletRequest request, PlatformManagerParams params) {

		PlatformManagerReturn pmr = new PlatformManagerReturn();
		try {
			Employee emp = (Employee) WebUtils.getSessionAttribute(request, "zycfLoginEmp");
			if (emp == null)
				throw new UException(SystemEnum.USER_NOLOGIN, "您没有登录");

			if (params == null || params.getAmount().doubleValue() == 0)
				throw new UException(SystemEnum.PARAMS_ERROR, "提现金额不能为空");

			// 创建提现订单
			User user = new User();
			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			try {
				user.setMobile(cipher.encrypt(ZYCF_PLAT_MOBILE));
			} catch (GeneralSecurityException e) {
				throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION, "数据加密异常");
			}
			user = userService.queryUserByParams(user);

			logger.info("====================添加平台提现订单=====================");
			BmOrder order = new BmOrder();
			order.setOrderType(OrderType.WITHDRAW);
			order.setUserId(user.getId());
			order.setRemark("平台提现");
			order.setUserId(user == null ? null : user.getId());
			try {
				bmOrderService.bmOrderPersistence(order);
			} catch (Exception e1) {
				logger.info("订单入库异常"+e1.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED,"订单入库异常");
			}
			
			logger.info("====================平台提现订单添加完成==================");

			// 添加平台资金记录
			logger.info("====================添加平台提现资金记录==================");
			PlatFundRecord record = new PlatFundRecord();
			record.setAccount("1");//提现是从平台自有子账户提现至自有资金账户
			record.setAmount(params.getAmount());
			record.setDescription("企业提现");
			record.setType(FundRecordType.WITHDRAW);
			record.setOrderId(order.getOrderId());
			record.setStatus(FundRecordStatus.PROCESSING);
			record.setOperation(FundRecordOperation.OUT);
			record.setUserId(user == null ? null : user.getId());
			record.setRemark("操作员工:" + emp.getEmpName());
			try {
				platFundRecordService.addPlatFundRecord(record);
			} catch (Exception e1) {
				logger.info("添加平台资金记录异常"+e1.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED,"添加平台资金记录异常");
			}
			
			logger.info("====================平台提现资金记录添加完成==================");

			logger.info("====================构建存管系统平台提现需要的参数==============");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("order_no", order.getOrderId());
			map.put("partner_trans_date", order.getOrderDate());
			map.put("partner_trans_time", order.getOrderTime());
			map.put("amount", params.getAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN));
			logger.info("====================存管系统平台提现需要的参数构建完成==============");

			logger.info("====================平台提现请求发往存管系统==============");
			logger.info("请求详情:" + map.toString());
			Map<String, Object> sendRequest=null;
			try {
				sendRequest = RequestUtil.sendRequest(map, ZYCF_PLATFORM_WITHDRAW);
			} catch (JSONException | IOException e) {
				e.printStackTrace();
				logger.info(e.fillInStackTrace());
				throw new UException(SystemEnum.NET_CONNET_EXCEPTION,"网络连接异常");
			}
			logger.info("存管系统响应平台提现请求：" + sendRequest.toString());

			// 根据返回结果修改订单状态和平台资金记录状态
			//"recode":"10000","remsg":"提现成功！"
			String code = (String) sendRequest.get("code");
			String recode = (String) sendRequest.get("recode");
			String remsg = (String) sendRequest.get("remsg");
			order.setRecord(recode);
			order.setRemsg(remsg);
			
			if ("10000".equals(recode)&&"1".equals(code)) {

				logger.info("平台提现入账成功");
				order.setOrderStatus(OrderStatus.SUCCESSFUL);
				order.setRemark("平台提现成功");

				record.setStatus(FundRecordStatus.SUCCESSFUL);
				record.setRemark("平台提现成功");

				pmr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "平台提现成功"));

			} else {
				logger.info("平台提现入账失败");
				order.setOrderStatus(OrderStatus.FAILED);
				order.setRemark("平台提现失败:recode["+recode+"]code["+code+"]remsg["+remsg);

				record.setStatus(FundRecordStatus.FAILED);
				record.setRemark("平台提现失败:recode["+recode+"]code["+code+"]remsg["+remsg);

				pmr.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(), "平台提现失败:recode["+recode+"]code["+code+"]remsg["+remsg));
			}
			try {
				bmOrderService.bmOrderModify(order);
			} catch (Exception e1) {
				logger.info("修改订单状态异常"+e1.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED,"修改订单状态异常");
			}
			try {
				platFundRecordService.modifyPlatFundRecord(record);
			} catch (Exception e1) {
				logger.info("修改平台资金记录异常"+e1.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED,"修改平台资金记录异常");
			}
			

		} catch (UException e) {
			logger.info("平台提现出现异常");
			logger.info(e.fillInStackTrace());
			pmr.setMessage(new Message(e.getCode().value(), e.getMessage()));
		}
		return pmr;
	}

	@Override
	public PlatformManagerReturn platformAccountConverse(PlatformManagerParams params) {
		PlatformManagerReturn pmr = new PlatformManagerReturn();
		try {

			if (params == null || params.getSource_account() == null | params.getDest_account() == null
					|| params.getAmount() == null || params.getAmount().doubleValue() == 0)
				throw new UException(SystemEnum.PARAMS_ERROR, "参数错误");

			BmOrder order = new BmOrder();
			order.setOrderType(OrderType.PLAT_TRANS);
			try {
				order = bmOrderService.bmOrderPersistence(order);
			} catch (Exception e1) {
				logger.info("订单入库异常"+e1.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED,"订单入库异常");
			}
			

			User user = new User();
			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			try {
				user.setMobile(cipher.encrypt(ZYCF_PLAT_MOBILE));
			} catch (GeneralSecurityException e) {
				throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION, "数据加密异常");
			}
			user = userService.queryUserByParams(user);

			List<PlatFundRecord> list = new ArrayList<>();
			
			Date date = new Date();
			PlatFundRecord record1 = new PlatFundRecord();
			record1.setId(GetUUID.getUniqueKey());
			record1.setAccount(params.getSource_account());
			record1.setAmount(params.getAmount());
			record1.setDescription(getPlatAccountType(params.getSource_account()) + "-->"
					+ getPlatAccountType(params.getDest_account()));
			record1.setType(FundRecordType.PLAT_TRANS);
			record1.setOrderId(order.getOrderId());
			record1.setStatus(FundRecordStatus.PROCESSING);
			record1.setOperation(FundRecordOperation.OUT);
			record1.setUserId(user == null ? null : user.getId());
			record1.setTimeRecorded(date);
			list.add(record1);

			PlatFundRecord record2 = new PlatFundRecord();
			record2.setId(GetUUID.getUniqueKey());
			record2.setAmount(params.getAmount());
			record2.setAccount(params.getDest_account());
			record2.setDescription(getPlatAccountType(params.getDest_account()) + "进账");
			record2.setType(FundRecordType.PLAT_TRANS);
			record2.setOrderId(order.getOrderId());
			record2.setStatus(FundRecordStatus.PROCESSING);
			record2.setOperation(FundRecordOperation.IN);
			record2.setUserId(user == null ? null : user.getId());
			record2.setTimeRecorded(date);
			list.add(record2);
			
			try {
				platFundRecordService.batchAddRecords(list);
			} catch (Exception e1) {
				logger.info("批量添加平台资金记录异常"+e1.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED,"批量添加平台资金记录异常");
			}
			

			logger.info("====================构建存管系统需要的参数==============");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("order_no", order.getOrderId());
			map.put("partner_trans_date", order.getOrderDate());
			map.put("partner_trans_time", order.getOrderTime());
			map.put("amt", params.getAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN));
			map.put("source_account", params.getSource_account());
			map.put("dest_account", params.getDest_account());

			logger.info("====================平台不同子账户之间转账请求发往存管系统==============");
			logger.info("请求详情:" + map.toString());
			Map<String, Object> sendRequest;
			try {
				sendRequest = RequestUtil.sendRequest(map, ZYCF_PLATFORM_TRANS);
			} catch (JSONException | IOException e) {
				e.printStackTrace();
				logger.info(e.fillInStackTrace());
				throw new UException(SystemEnum.NET_CONNET_EXCEPTION,"网络连接异常");
			}
			logger.info("存管系统响应不同子账户之间转账请求：" + sendRequest.toString());

			String recode = (String) sendRequest.get("recode");
			String remsg = (String) sendRequest.get("remsg");

			order.setRecord(recode);
			order.setRemsg(remsg);

			if ("10000".equals(recode)) {
				order.setOrderStatus(OrderStatus.SUCCESSFUL);

				record1.setStatus(FundRecordStatus.SUCCESSFUL);
				record2.setStatus(FundRecordStatus.SUCCESSFUL);
				pmr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "转账成功"));

			} else {
				order.setOrderStatus(OrderStatus.FAILED);

				record1.setStatus(FundRecordStatus.FAILED);
				record2.setStatus(FundRecordStatus.FAILED);
				pmr.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(), "转账失败"));

			}
			try {
				bmOrderService.bmOrderModify(order);
			} catch (Exception e1) {
				logger.info("订单修改异常"+e1.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED,"订单修改异常");
			}
			
			try {
				platFundRecordService.modifyPlatFundRecord(record1);
			} catch (Exception e1) {
				logger.info("修改平台资金记录异常"+e1.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED,"修改平台资金记录异常");
			}
			
			try {
				platFundRecordService.modifyPlatFundRecord(record2);
			} catch (Exception e1) {
				logger.info("修改平台资金记录异常"+e1.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED,"修改平台资金记录异常");
			}
			
			

		} catch (UException e) {
			logger.info("平台子账户之间转账异常");
			logger.info(e.fillInStackTrace());
			pmr.setMessage(new Message(e.getCode().value(), e.getMessage()));
		}

		return pmr;
	}

	String getPlatAccountType(String account) {
		String accountName = null;
		switch (account) {
		case "1":
			accountName = "平台自有子账户";
			break;
		case "3":
			accountName = "平台手续费子账户";
			break;
		case "4":
			accountName = "平台体验金子账户";
			break;
		case "5":
			accountName = "平台抵佣金子账户";
			break;
		case "6":
			accountName = "平台加息金子账户";
			break;
		case "7":
			accountName = "平台保证金子账户";
			break;
		}
		return accountName;
	}

	@Override
	public PlatformManagerReturn platToPersonApply(HttpServletRequest request, TransAmountParams params) {
		PlatformManagerReturn pr = new PlatformManagerReturn();
		try {

			Employee emp = (Employee) request.getSession().getAttribute("zycfLoginEmp");
			if (emp == null)
				throw new UException(SystemEnum.USER_NOLOGIN, "您没有登录");

			if (params == null)
				throw new UException(SystemEnum.PARAMS_ERROR, "参数接收异常");

			UntredtedApplication untredtedApplication = new UntredtedApplication();
			if (params.getTelphone() == null)
				throw new UException(SystemEnum.PARAMS_ERROR, "参数接收异常,转入账户手机号错误");

			if (!Validator.isMobile(params.getTelphone()))
				throw new UException(SystemEnum.PARAMS_ERROR, "参数接收异常,转入账户手机号格式错误");

			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			User user = new User();
			try {
				user.setMobile(cipher.encrypt(params.getTelphone()));
			} catch (GeneralSecurityException e) {
				e.printStackTrace();
				throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION, "手机号加密异常");
			}
			user = userService.queryUserByParams(user);
			if (user == null)
				throw new UException(SystemEnum.PARAMS_ERROR, "参数接收异常,转入账户不存在");

			untredtedApplication.setId(GetUUID.getUniqueKey());
			untredtedApplication.setTradeAmount(params.getTransAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN));
			untredtedApplication.setDescription(params.getDescription());
			untredtedApplication.setAccount(params.getPlatAccount());
			untredtedApplication.setUserName(user.getUserName());
			untredtedApplication.setTelephone(user.getMobile());
			untredtedApplication.setCreateTime(new Date());
			untredtedApplication.setStatus(FundRecordStatus.AUDITING);
			untredtedApplication.setOperationEmplyee(emp.getEmpName());// 当前登录用户------------------------
			untredtedApplication.setUserId(user.getId());
			untredtedApplication.setCause_type(params.getCause_type());
			untredtedApplication.setCharge_type(params.getCharge_type());
			int res=0;
			try {
				res = untredtedApplicationService.addUntredtedApplication(untredtedApplication);
			} catch (Exception e1) {
				logger.info("添加转账申请异常"+e1.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED,"添加转账申请异常");
			}
			
			if (res > 0)
				pr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "申请成功"));
			else
				pr.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(), "申请失败"));

		} catch (UException e) {
			logger.info("创建转账申请失败:" + e);
			e.printStackTrace();
			pr.setMessage(new Message(e.getCode().value(), e.getMessage()));
		}
		return pr;
	}

	@Override
	public PlatformManagerReturn platToPersonCancle(HttpServletRequest request, UntredtedApplication application) {
		PlatformManagerReturn pr = new PlatformManagerReturn();
		try {
			Employee emp = (Employee) request.getSession().getAttribute("zycfLoginEmp");
			if (emp == null)
				throw new UException(SystemEnum.USER_NOLOGIN, "您没有登录");

			if (application == null || application.getId() == null)
				throw new UException(SystemEnum.PARAMS_ERROR, "参数接收异常");

			application.setStatus(FundRecordStatus.CANCELED);
			int res=0;
			try {
				res = untredtedApplicationService.modifyUntredtedApplicationByParams(application);
			} catch (Exception e1) {
				logger.info("修改转账申请状态异常"+e1.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED,"修改转账申请状态异常");
			}
			 
			if (res > 0)
				pr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "取消成功"));
			else
				pr.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(), "取消失败"));

		} catch (UException e) {
			e.printStackTrace();
			pr.setMessage(new Message(e.getCode().value(), e.getMessage()));
		}
		return pr;
	}

	@Override
	public PlatformManagerReturn platToPersonList(HttpServletRequest request) {
		PlatformManagerReturn pr = new PlatformManagerReturn();
		try {
			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			UntredtedApplication application = new UntredtedApplication();
			application.setFlag(false);
			application.setStatus(FundRecordStatus.AUDITING);
			List<UntredtedApplication> res = untredtedApplicationService.queryUntredtedApplicationByParams(application);
			for (UntredtedApplication untredtedApplication : res) {
				untredtedApplication.setAccount(this.getPlatAccount(untredtedApplication.getAccount()));
				untredtedApplication.setCause_type(this.getCause_Type(untredtedApplication.getCause_type()));
				untredtedApplication.setCharge_type(this.getTransAccount(untredtedApplication.getCharge_type()));
				untredtedApplication.setStrCreateTime(FormatUtils.millisDateFormat(untredtedApplication.getCreateTime()));
				try {
					untredtedApplication.setTelephone(cipher.decrypt(untredtedApplication.getTelephone()));
				} catch (GeneralSecurityException e) {
					e.printStackTrace();
					throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION, "手机号解密异常");
				}
			}
			pr.setAppliactions(res);
			pr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "查询成功"));
		} catch (UException e) {
			e.printStackTrace();
			pr.setMessage(new Message(e.getCode().value(), e.getMessage()));
		}
		return pr;
	}
	String getPlatAccount(String account){
		String res=null;
		switch (account) {
			case "1":res="平台自有账户";break;
			case "3":res="手续费账户";break;
			case "4":res="体验金";break;
			case "5":res="低用金";break;
			case "6":res="加息活动金";break;
			case "7":res="保证金";break;
			case "9":res="奖励金";break;
			case "10":res="现金垫付";break;
		}
		return res;
	}
	String getCause_Type(String cause_Type){
		String res=null;
		switch (cause_Type) {
			case "1":res="平台赠送";break;
			case "2":res="平台补贴";break;
			case "3":res="活动派发";break;
			case "4":res="抵扣券未使用派发";break;
			case "5":res="迁移存管转账";break;
			case "6":res="加息补偿";break;
		}
		return res;
	}
	String getTransAccount(String charge_Type){
		String res=null;
		switch (charge_Type) {
			case "1":res="个人现金投资账户";break;
			case "2":res="个人现金融资账户";break;
		}
		return res;
	}
	@Override
	public PlatformManagerReturn platToPersonAffirm(HttpServletRequest request, UntredtedApplication application) {
		PlatformManagerReturn pr = new PlatformManagerReturn();
		try {
			Employee emp = (Employee) request.getSession().getAttribute("zycfLoginEmp");
			if (emp == null)
				throw new UException(SystemEnum.USER_NOLOGIN, "您没有登录");

			if (application == null || application.getId() == null)
				throw new UException(SystemEnum.PARAMS_ERROR, "参数接收异常");

			List<UntredtedApplication> res = untredtedApplicationService.queryUntredtedApplicationByParams(application);
			UntredtedApplication untredtedApplication = null;
			if (res != null && res.size() == 1)
				untredtedApplication = res.get(0);
			else
				throw new UException(SystemEnum.PARAMS_ERROR, "申请记录不存在");

			BmAccount account = new BmAccount();
			account.setUserId(untredtedApplication.getUserId());
			account = bmAccountService.queryBmAccountByParams(account);
			if (account.getPlatcust() == null)
				throw new UException(SystemEnum.PARAMS_ERROR, "转入账户没有实名认证");

			BmOrder order = new BmOrder();
			order.setOrderType(OrderType.PLAT_TO_PERSON);
			order.setUserId(untredtedApplication.getUserId());
			order.setRemark("平台转账给个人");
			try {
				order = bmOrderService.bmOrderPersistence(order);
			} catch (Exception e1) {
				logger.info("订单入库异常"+e1.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED,"订单入库异常");
			}
			

			PlatFundRecord record = new PlatFundRecord();
			record.setAmount(untredtedApplication.getTradeAmount());
			record.setAccount(untredtedApplication.getAccount());
			record.setDescription("平台转账给个人");
			record.setType(FundRecordType.TRANSFEROUT);
			record.setStatus(FundRecordStatus.PROCESSING);
			record.setOrderId(order.getOrderId());
			record.setOperation(FundRecordOperation.OUT);
			record.setUserId(untredtedApplication.getUserId());
			record.setRemark("操作员工:" + emp.getEmpName());
			try {
				record = platFundRecordService.addPlatFundRecord(record);
			} catch (Exception e1) {
				logger.info("添加平台资金记录异常"+e1.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED,"添加平台资金记录异常");
			}
			

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("order_no", order.getOrderId());
			map.put("partner_trans_date", order.getOrderDate());
			map.put("partner_trans_time", order.getOrderTime());
			map.put("acct_type", untredtedApplication.getCause_type());
			map.put("amount", untredtedApplication.getTradeAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN));
			map.put("cause_type", untredtedApplication.getCause_type());
			map.put("charge_type", untredtedApplication.getCharge_type());
			map.put("platcust", account.getPlatcust());

			Map<String, Object> sendRequest=null;
			try {
				sendRequest = RequestUtil.sendRequest(map, ZYCF_PLAT_TO_PERSON);
			} catch (JSONException | IOException e) {
				e.printStackTrace();
				logger.info(e.fillInStackTrace());
				throw new UException(SystemEnum.NET_CONNET_EXCEPTION,"网络连接异常");
			}
			System.out.println(sendRequest);

			String recode = (String) sendRequest.get("recode");
			String remsg = (String) sendRequest.get("remsg");

			PlatFundRecord platfundRecord = new PlatFundRecord();
			platfundRecord.setId(record.getId());

			BmOrder updateOrder = new BmOrder();
			updateOrder.setOrderId(order.getOrderId());
			updateOrder.setRecord(recode);
			updateOrder.setRemsg(remsg);
			
			UntredtedApplication uapp=new UntredtedApplication();
			uapp.setId(untredtedApplication.getId());
			uapp.setOrderId(order.getOrderId());
			uapp.setFlag(true);

			if ("10000".equals(recode)) {
				platfundRecord.setStatus(FundRecordStatus.SUCCESSFUL);
				updateOrder.setOrderStatus(OrderStatus.SUCCESSFUL);
				
				uapp.setStatus(FundRecordStatus.SUCCESSFUL);

				UserFund userFund = new UserFund();
				userFund.setUserId(untredtedApplication.getUserId());
				userFund = userFundService.queryUserFundByParams(userFund);

				FundRecord fundRecord = new FundRecord();
				fundRecord.setAmount(untredtedApplication.getTradeAmount());
				fundRecord.setAviAmount(userFund.getAvailableAmount().add(fundRecord.getAmount()));
				fundRecord.setDescription("平台转账");
				fundRecord.setOperation(FundRecordOperation.IN);
				fundRecord.setOrderId(order.getOrderId());
				fundRecord.setStatus(FundRecordStatus.SUCCESSFUL);
				fundRecord.setType(FundRecordType.TRANSFERIN);
				fundRecord.setUserId(untredtedApplication.getUserId());
				try {
					fundRecordService.addFundRecord(fundRecord);
				} catch (Exception e1) {
					logger.info("添加资金记录异常"+e1.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED,"添加资金记录异常");
				}
				

				UserFund fund = new UserFund();
				fund.setUserId(userFund.getUserId());
				fund.setAvailableAmount(untredtedApplication.getTradeAmount());
				try {
					userFundService.modifyUserFundByParams(fund);
				} catch (Exception e1) {
					logger.info("修改用户账户资金异常"+e1.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED,"修改用户账户资金异常");
				}
				

			} else {
				updateOrder.setOrderStatus(OrderStatus.FAILED);
				platfundRecord.setStatus(FundRecordStatus.FAILED);
				uapp.setStatus(FundRecordStatus.FAILED);
			}
			try {
				bmOrderService.bmOrderModify(updateOrder);
			} catch (Exception e1) {
				logger.info("修改订单状态异常"+e1.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED,"修改订单状态异常");
			}
			
			try {
				platFundRecordService.modifyPlatFundRecord(platfundRecord);
			} catch (Exception e1) {
				logger.info("修改平台资金记录状态异常"+e1.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED,"修改平台资金记录状态异常");
			}
			
			try {
				untredtedApplicationService.modifyUntredtedApplicationByParams(uapp);
			} catch (Exception e1) {
				logger.info("修改转账申请状态异常"+e1.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED,"修改转账申请状态异常");
			}
			
			pr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "转账成功"));
		} catch (UException e) {
			e.printStackTrace();
			pr.setMessage(new Message(e.getCode().value(), e.getMessage()));
		}

		return pr;
	}

}
