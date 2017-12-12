package com.zhongyang.java.bankmanager.biz.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.zhongyang.java.bankmanager.biz.BmLoanManagerBackBiz;
import com.zhongyang.java.bankmanager.biz.BmSearchBiz;
import com.zhongyang.java.bankmanager.entity.BmAccount;
import com.zhongyang.java.bankmanager.entity.BmOrder;
import com.zhongyang.java.bankmanager.entity.Cust;
import com.zhongyang.java.bankmanager.params.BmSearchParams;
import com.zhongyang.java.bankmanager.params.Compensation;
import com.zhongyang.java.bankmanager.params.LoanManagerParams;
import com.zhongyang.java.bankmanager.params.PayOut;
import com.zhongyang.java.bankmanager.params.RepayLoanList;
import com.zhongyang.java.bankmanager.returndata.BmSearchManager;
import com.zhongyang.java.bankmanager.returndata.CustRepayList;
import com.zhongyang.java.bankmanager.returndata.FundData;
import com.zhongyang.java.bankmanager.returndata.LoanManagerReturn;
import com.zhongyang.java.bankmanager.service.BmAccountService;
import com.zhongyang.java.bankmanager.service.BmOrderService;
import com.zhongyang.java.bankmanager.util.RequestUtil;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.enumtype.FundRecordOperation;
import com.zhongyang.java.system.enumtype.FundRecordStatus;
import com.zhongyang.java.system.enumtype.FundRecordType;
import com.zhongyang.java.system.enumtype.InvestStatus;
import com.zhongyang.java.system.enumtype.LoanRepayMent;
import com.zhongyang.java.system.enumtype.LoanStatus;
import com.zhongyang.java.system.enumtype.Method;
import com.zhongyang.java.system.enumtype.OrderStatus;
import com.zhongyang.java.system.enumtype.OrderType;
import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.uitl.ArithmeticInterest;
import com.zhongyang.java.system.uitl.DESTextCipher;
import com.zhongyang.java.system.uitl.FormatUtils;
import com.zhongyang.java.system.uitl.GetUUID;
import com.zhongyang.java.system.uitl.Message;
import com.zhongyang.java.system.uitl.RepaymentCalendar;
import com.zhongyang.java.system.uitl.ShortMessage;
import com.zhongyang.java.system.uitl.SystemPro;
import com.zhongyang.java.system.uitl.UtilBiz;
import com.zhongyang.java.zyfyback.pojo.Employee;
import com.zhongyang.java.zyfyback.pojo.FundRecord;
import com.zhongyang.java.zyfyback.pojo.Invest;
import com.zhongyang.java.zyfyback.pojo.InvestRepayment;
import com.zhongyang.java.zyfyback.pojo.Loan;
import com.zhongyang.java.zyfyback.pojo.LoanRepayment;
import com.zhongyang.java.zyfyback.pojo.LoanTender;
import com.zhongyang.java.zyfyback.pojo.LoanTransRecord;
import com.zhongyang.java.zyfyback.pojo.PlatFundRecord;
import com.zhongyang.java.zyfyback.pojo.Project;
import com.zhongyang.java.zyfyback.pojo.User;
import com.zhongyang.java.zyfyback.pojo.UserFund;
import com.zhongyang.java.zyfyback.service.FundRecordService;
import com.zhongyang.java.zyfyback.service.InvestRepaymentService;
import com.zhongyang.java.zyfyback.service.InvestService;
import com.zhongyang.java.zyfyback.service.LoanRepaymentService;
import com.zhongyang.java.zyfyback.service.LoanService;
import com.zhongyang.java.zyfyback.service.LoanTenderService;
import com.zhongyang.java.zyfyback.service.LoanTransRecordService;
import com.zhongyang.java.zyfyback.service.PlatFundRecordService;
import com.zhongyang.java.zyfyback.service.ProjectService;
import com.zhongyang.java.zyfyback.service.UserFundService;
import com.zhongyang.java.zyfyback.service.UserService;

/**
 * @package com.zhongyang.java.bankmanager.biz.impl
 * @filename BmLoanAccountBizImpl.java
 * @date 2017年7月3日下午2:03:40
 * @author suzh
 */
@Service
public class BmLoanManagerBackBizImpl extends UtilBiz implements BmLoanManagerBackBiz {

	static {
		Map<String, Object> sysMap = SystemPro.getProperties();
		ZYCF_LOAN_PUBLISH = (String) sysMap.get("ZYCF_LOAN_PUBLISH");
		ZYCF_LOAN_ISTABLISH = (String) sysMap.get("ZYCF_LOAN_ISTABLISH");
		ZYCF_LOAN_REPAY = (String) sysMap.get("ZYCF_LOAN_REPAY");
		ZYCF_COMPENSATE_REPAY = (String) sysMap.get("ZYCF_COMPENSATE_REPAY");
	}

	private static String ZYCF_LOAN_PUBLISH;// 4.3.1标的发布

	private static String ZYCF_LOAN_ISTABLISH;// 4.3.2标的成废

	private static String ZYCF_LOAN_REPAY;// 4.3.5标的还款

	private static String ZYCF_COMPENSATE_REPAY;

	private static Logger logger = Logger.getLogger(BmLoanManagerBackBizImpl.class);

	@Autowired
	private LoanService loanService;

	@Autowired
	private BmOrderService bmOrderService;

	@Autowired
	private BmAccountService bmAccountService;

	@Autowired
	private UserService userService;

	@Autowired
	private InvestService investService;

	@Autowired
	private UserFundService userFundService;

	@Autowired
	private FundRecordService fundRecordService;

	@Autowired
	private LoanTransRecordService loanTransRecordService;

	@Autowired
	private PlatFundRecordService platFundRecordService;

	@Autowired
	private LoanTenderService loanTenderService;

	@Autowired
	private LoanRepaymentService loanRepaymentService;

	@Autowired
	private InvestRepaymentService investRepaymentService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private BmSearchBiz bmSearchBiz;

	@Override
	public LoanManagerReturn publishLoan(HttpServletRequest request, LoanManagerParams params) {

		LoanManagerReturn lmr = new LoanManagerReturn();
		Loan loan = null;
		try {
			logger.info("====================发标参数校验====================");
			if (params == null || params.getLoan() == null || params.getLoan().getId() == null)
				throw new UException(SystemEnum.PARAMS_ERROR, "参数接收异常");
			List<Loan> loans = loanService.queryLoanByParams(params.getLoan());
			if (loans == null)
				throw new UException(SystemEnum.DATA_REFUSED, "标的信息未查询到");

			logger.info("====================发标准备数据====================");

			loan = loans.get(0);
			logger.info("标的：" + loan.getId());
			logger.info("构建发标订单");
			BmOrder order = new BmOrder();

			User user = new User();
			if (params.getTask() != null && "task".equals(params.getTask())) {
				logger.info("定时发标");
				order.setRemark("定时发标");

			} else {
				Employee emp = (Employee) WebUtils.getSessionAttribute(request, "zycfLoginEmp");
				if (emp == null)
					throw new UException(SystemEnum.USER_NOLOGIN, "您没有登录");

				user.setMobile(emp.getMobile());
				user = userService.queryUserByParams(user);

				order.setUserId(user == null ? null : user.getId());
				order.setRemark("标的发布");

			}
			order.setOrderType(OrderType.LOAN_PUBLISH);
			try {
				order = bmOrderService.bmOrderPersistence(order);
			} catch (Exception e1) {
				logger.info("订单入库异常" + e1.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED, "订单入库异常");
			}
			logger.info("发标订单:" + order.toString());

			logger.info("构建发标请求存管系统参数");
			Date date = new Date();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("order_no", order.getOrderId());
			map.put("partner_trans_date", order.getOrderDate());
			map.put("partner_trans_time", order.getOrderTime());
			map.put("prod_id", loan.getId());
			map.put("prod_name", loan.getTitle());
			map.put("prod_type", 0);// prod_type M C(2) 产品类型（0周期性产品、1不定期出账产品）
			map.put("total_limit", loan.getAmount());
			map.put("value_type", 1);// value_type M C(2) 产品起息方式:
										// 0满额起息1成立起息2投标起息 3 成立审核后起息
			map.put("create_type", 1);// create_type M C(2) 成立方式: 0满额成立 1成立日成立
										// 2活期方式
			map.put("sell_date", FormatUtils.millisDateFormat(date));
			map.put("value_date", FormatUtils.millisDateFormat(date));
			Calendar calendar = Calendar.getInstance();
			calendar.clear();
			calendar.setTime(date);
			calendar.set(Calendar.MONTH, loan.getMonths());
			map.put("expire_date", FormatUtils.millisDateFormat(calendar.getTime()));
			map.put("cycle", loan.getMonths());
			map.put("cycle_unit", 3);// cycle_unit M C(2) 周期单位 1日 2周 3月 4季 5年
			map.put("ist_year", new BigDecimal(loan.getRate() + loan.getAddRate()).divide(new BigDecimal(100))
					.setScale(4, BigDecimal.ROUND_HALF_EVEN));// ist_year
			// 传
			// 0.089
			map.put("repay_type", 1);// repay_type M C(2) 还款方式 0-一次性还款 1-分期还款
			List<Cust> users = this.getLoanUserList(loan);

			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			for (Cust cust : users) {
				if (cust.getCust_no() == null)
					throw new UException(SystemEnum.PARAMS_ERROR, "借款人没有实名认证");

				if (cust.getWithdraw_account() == null)
					throw new UException(SystemEnum.PARAMS_ERROR, "借款人没有绑卡");

				try {
					cust.setWithdraw_account(cipher.decrypt(cust.getWithdraw_account()));
				} catch (GeneralSecurityException e) {
					e.printStackTrace();
					throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION, "银行卡号解密异常");
				}
			}

			map.put("financing_info_list", JSON.toJSONString(users));// 融资人信息
			map.put("chargeOff_auto", 0);
			map.put("overLimit", 1);// 超额限制
			map.put("over_total_limit", 0);
			map.put("pay_type", "0");

			if (loan.getComentSateUserId() != null&&!"".equals(loan.getComentSateUserId())) {
				BmAccount loanAccount = new BmAccount();
				loanAccount.setUserId(loan.getComentSateUserId());
				loanAccount = bmAccountService.queryBmAccountByParams(loanAccount);
				List<Compensation> compensations = new ArrayList<Compensation>();
				Compensation cp = new Compensation();
				cp.setPlatcust(loanAccount.getPlatcust());
				cp.setType("1");
				compensations.add(cp);
				map.put("compensation", JSON.toJSONString(compensations));
			}
			logger.info("发送发标请求到存管系统:" + map);
			Map<String, Object> sendRequest = null;
			try {
				sendRequest = RequestUtil.sendRequest(map, ZYCF_LOAN_PUBLISH);
			} catch (JSONException | IOException e) {
				e.printStackTrace();
				logger.info(e.fillInStackTrace());
				throw new UException(SystemEnum.NET_CONNET_EXCEPTION, "网络连接异常");
			}
			logger.info("发标请求返回结果：" + sendRequest.toString());

			String recode = (String) sendRequest.get("recode");
			order.setRecord(recode);
			String remsg = (String) sendRequest.get("remsg");
			order.setRemsg(remsg);

			logger.info("修改订单状态，订单号：" + order.getOrderId());

			if ("10000".equals(recode)) {
				order.setOrderStatus(OrderStatus.SUCCESSFUL);

				Loan modifyLoan = new Loan();
				modifyLoan.setId(loan.getId());
				modifyLoan.setTimeOpen(new Date());
				modifyLoan.setStatus(LoanStatus.OPENED);
				try {
					loanService.modifyLoanByparams(modifyLoan);
				} catch (Exception e1) {
					logger.info("标的状态修改异常异常" + e1.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED, "标的状态修改异常");
				}

				LoanTender tender = new LoanTender();
				tender.setLoanId(loan.getId());
				tender.setAmount(new BigDecimal(0));
				try {
					loanTenderService.addLoanTender(tender);
				} catch (Exception e1) {
					logger.info("标的账户添加异常" + e1.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED, "标的账户添加异常");
				}

				lmr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "发标成功"));
				logger.info("标的" + loan.getId() + "发标成功");
			} else {
				order.setOrderStatus(OrderStatus.FAILED);

				lmr.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(), "发标失败" + remsg));
				logger.info("标的" + loan.getId() + "发标失败" + remsg);
			}
			try {
				bmOrderService.bmOrderModify(order);
			} catch (Exception e1) {
				logger.info("订单状态修改异常" + e1.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED, "订单状态修改异常");
			}

			logger.info("===========================================");

		} catch (UException e) {
			logger.info("发标出现异常");
			logger.info(e.fillInStackTrace());
			lmr.setMessage(new Message(e.getCode().value(), e.getMessage()));
			return lmr;
		}

		return lmr;
	}

	List<Cust> getLoanUserList(Loan loan) {
		List<Cust> custs = new ArrayList<Cust>();
		BmAccount account = new BmAccount();
		account.setUserId(loan.getLoanUserId());
		BmAccount queryBmAccount = bmAccountService.queryBmAccountByParams(account);
		Cust cust = new Cust();
		cust.setCust_no(queryBmAccount.getPlatcust());
		Date date = new Date();
		cust.setReg_date(FormatUtils.simpleFormat(date));
		cust.setReg_time(FormatUtils.timeFormat(date));
		cust.setFinanc_int(new BigDecimal(loan.getRate() + loan.getAddRate()).divide(new BigDecimal(100)).setScale(4,
				BigDecimal.ROUND_HALF_EVEN));
		cust.setFee_int((loan.getLoanServiceFee().add(loan.getLoanRiskFee()).add(loan.getLoanGuaranteeFee())
				.add(loan.getLoanManageFee())).divide(new BigDecimal(100)).setScale(4, BigDecimal.ROUND_HALF_EVEN));
		cust.setOpen_branch(queryBmAccount.getOpenBranch());
		cust.setWithdraw_account(queryBmAccount.getCardNo());

		User user = new User();
		user.setId(loan.getLoanUserId());
		user = userService.queryUserByParams(user);
		if ("1".equals(user.getCustType()))
			cust.setAccount_type("1");// 卡类型(1-个人 2-企业)
		if ("2".equals(user.getCustType()))
			cust.setAccount_type("2");// 卡类型(1-个人 2-企业)

		cust.setPayee_name(loan.getTitle());
		cust.setFinanc_amt(loan.getAmount());
		custs.add(cust);
		return custs;
	}

	/**
	 * 标的结算
	 */
	@Override
	public LoanManagerReturn settlementLoan(LoanManagerParams params) {
		LoanManagerReturn lr = new LoanManagerReturn();
		try {

			logger.info("结算标的参数校验");
			if (params == null || params.getLoan() == null || params.getLoan().getId() == null)
				throw new UException(SystemEnum.PARAMS_ERROR, "参数错误");

			logger.info("结算标的参数校验通过，结算标的ID：" + params.getLoan().getId());

			BmSearchParams search = new BmSearchParams();
			search.setProd_id(params.getLoan().getId());
			search.setType("02");
			BmSearchManager searchRes = bmSearchBiz.searchLoanAccountBalance(search);
			if (searchRes.getMessage().getCode() != 1000)
				throw new UException(SystemEnum.OPRARION_FAILED, searchRes.getMessage().getMessage());

			BigDecimal balance = new BigDecimal(Double.parseDouble(searchRes.getLoanAccount().getBalance())).setScale(2,
					BigDecimal.ROUND_HALF_EVEN);

			List<Loan> res = loanService.queryLoanByParams(params.getLoan());
			Loan settleLoan = res.get(0);

			String[] dates = null;

			// 按月付息到期还款
			if (Method.MonthlyInterest.equals(settleLoan.getMethod())) {
				dates = RepaymentCalendar.getNextmonths(settleLoan.getMonths());// 调用还款日期生成控件

			}
			// 一次性还本付息
			if (Method.BulletRepayment.equals(settleLoan.getMethod())) {
				dates = RepaymentCalendar.getBulletmonths(settleLoan.getMonths());// 调用还款日期生成控件
			}

			logger.info("还款时间：" + JSON.toJSONString(dates));

			// 构造回款计划
			List<InvestRepayment> investRepayments = this.investRepayment(settleLoan, dates);

			/**
			 * 构造还款计划：还款计划每期的利息是对应的回款计划每期利息的总和,并且每期的利息和第一期都是相同的，
			 * 所以还款计划里每期的利息都等于回款计划里第一期利息的总和
			 */
			// 获得还款计划每期的利息
			BigDecimal investRestAmount = new BigDecimal(0);
			for (InvestRepayment investRep : investRepayments) {
				if (investRep.getCurrentPeriod() == 1)
					investRestAmount = investRestAmount.add(investRep.getAmountInterest());

			}
			List<LoanRepayment> loanRepayments = new ArrayList<LoanRepayment>();

			for (int i = 1; i <= settleLoan.getMonths(); i++) {
				LoanRepayment loanRepayment = new LoanRepayment();
				loanRepayment.setId(GetUUID.getUniqueKey());
				loanRepayment.setCurrentPeriod(i);
				loanRepayment.setLoanId(settleLoan.getId());
				loanRepayment.setStatus(LoanRepayMent.UNDUE);
				loanRepayment.setAmountInterest(investRestAmount);
				loanRepayment.setRepay(false);
				loanRepayment.setDueDate(FormatUtils.dateFormat(dates[i]));
				if (i != settleLoan.getMonths()) {
					loanRepayment.setAmountOutStanding(settleLoan.getAmount());
					loanRepayment.setAmountPrincipal(new BigDecimal(0));
				} else {
					loanRepayment.setAmountOutStanding(new BigDecimal(0));
					loanRepayment.setAmountPrincipal(settleLoan.getAmount());
				}
				loanRepayments.add(loanRepayment);
			}

			logger.info("还款计划:" + JSON.toJSONString(loanRepayments));

			BigDecimal loanServiceFee = settleLoan.getAmount().multiply(settleLoan.getLoanServiceFee())
					.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_EVEN);
			BigDecimal loanGuaranteeFee = settleLoan.getAmount().multiply(settleLoan.getLoanGuaranteeFee())
					.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_EVEN);
			BigDecimal loanRiskFee = settleLoan.getAmount().multiply(settleLoan.getLoanRiskFee())
					.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_EVEN);
			BigDecimal loanManageFee = settleLoan.getAmount().multiply(settleLoan.getLoanManageFee())
					.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_EVEN);

			BigDecimal totalFee = loanServiceFee.add(loanGuaranteeFee).add(loanRiskFee).add(loanManageFee);

			BmOrder order = new BmOrder();
			order.setOrderType(OrderType.LOAN);
			order.setRemark("标的结算");
			// 生成标的成立订单
			try {
				order = bmOrderService.bmOrderPersistence(order);
			} catch (Exception e1) {
				logger.info("订单入库异常" + e1.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED, "订单入库异常");
			}

			logger.info("标的结算订单" + order.toString());

			// 借款人生成资金记录
			List<FundRecord> records = this.createFundRecod(settleLoan, order);
			if (records.size() > 0) {
				try {
					fundRecordService.batchAddtFundRecord(records);
				} catch (Exception e1) {
					logger.info("添加资金记录异常" + e1.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED, "添加资金记录异常");
				}

			}
			logger.info("借款人资金记录" + JSON.toJSONString(records));

			// 添加标的交易记录
			List<LoanTransRecord> transRecords = this.createLoanTransRecord(settleLoan, order);
			if (transRecords.size() > 0) {
				try {
					loanTransRecordService.batchAddRecords(transRecords);
				} catch (Exception e1) {
					logger.info("批量添加标的交易记录异常" + e1.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED, "批量添加标的交易记录异常");
				}

			}
			logger.info("标的交易记录" + JSON.toJSONString(transRecords));

			// 平台添加手续费记录
			List<PlatFundRecord> platFundRecords = this.createPlatFundRecord(settleLoan, order);
			if (platFundRecords.size() > 0) {
				try {
					platFundRecordService.batchAddRecords(platFundRecords);
				} catch (Exception e1) {
					logger.info("批量添加平台资金记录异常" + e1.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED, "批量添加平台资金记录异常");
				}

			}
			logger.info("平台手续费记录" + JSON.toJSONString(platFundRecords));

			// 构造存管系统标的成立接口参数
			logger.info("构建成标存管系统参数");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("order_no", order.getOrderId());
			map.put("partner_trans_date", order.getOrderDate());
			map.put("partner_trans_time", order.getOrderTime());
			map.put("prod_id", settleLoan.getId());
			map.put("flag", 2);
			PayOut payOut = new PayOut();

			payOut.setPayout_plat_type(balance.subtract(totalFee).doubleValue() <= 0 ? "3" : "11");
			payOut.setPayout_amt(totalFee);
			map.put("funddata", JSON.toJSON(payOut));
			List<RepayLoanList> repayLoanList = new ArrayList<RepayLoanList>();
			BigDecimal totalAmount = new BigDecimal(0);// 还款总金额
			for (LoanRepayment loanRepayment : loanRepayments) {
				RepayLoanList repay = new RepayLoanList();
				repay.setRepay_amt(loanRepayment.getAmountInterest().add(loanRepayment.getAmountPrincipal()));
				repay.setRepay_date(FormatUtils.simpleFormat(loanRepayment.getDueDate()));
				repay.setRepay_fee(new BigDecimal(0));
				repay.setRepay_num(loanRepayment.getCurrentPeriod().toString());
				repayLoanList.add(repay);
				totalAmount = totalAmount.add(loanRepayment.getAmountInterest())
						.add(loanRepayment.getAmountPrincipal());
			}
			map.put("repay_plan_list", JSON.toJSON(repayLoanList));

			logger.info("成标存管系统参数" + JSON.toJSONString(map));
			Map<String, Object> sendRequest = null;
			try {
				sendRequest = RequestUtil.sendRequest(map, ZYCF_LOAN_ISTABLISH);
			} catch (JSONException | IOException e) {
				e.printStackTrace();
				logger.info(e.fillInStackTrace());
				throw new UException(SystemEnum.NET_CONNET_EXCEPTION, "网络连接异常");
			}
			logger.info("成标存管系统返回信息" + sendRequest);

			String recode = (String) sendRequest.get("recode");
			String remsg = (String) sendRequest.get("remsg");

			order.setRecord(recode);
			order.setRemsg(remsg);

			FundRecord updateFundRecord = new FundRecord();
			updateFundRecord.setOrderId(order.getOrderId());

			LoanTransRecord updateLoanTransRecord = new LoanTransRecord();
			updateLoanTransRecord.setOrderId(order.getOrderId());

			PlatFundRecord updatePlatFundRecord = new PlatFundRecord();
			updatePlatFundRecord.setOrderId(order.getOrderId());

			if ("10000".equals(recode)) {

				order.setOrderStatus(OrderStatus.SUCCESSFUL);

				updateFundRecord.setStatus(FundRecordStatus.SUCCESSFUL);

				updateLoanTransRecord.setStatus(FundRecordStatus.SUCCESSFUL);

				updatePlatFundRecord.setStatus(FundRecordStatus.SUCCESSFUL);

				// 添加还款计划
				logger.info("结算成功，添加还款计划" + JSON.toJSONString(loanRepayments));
				try {
					loanRepaymentService.batchAddLoanRepayment(loanRepayments);
				} catch (Exception e1) {
					logger.info("标的还款计划添加异常" + e1.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED, "标的还款计划添加异常");
				}

				// 添加回款计划
				logger.info("结算成功，添加还款计划" + JSON.toJSONString(investRepayments));
				try {
					investRepaymentService.batchAddInvestRepayment(investRepayments);
				} catch (Exception e1) {
					logger.info("回款还款计划添加异常" + e1.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED, "回款还款计划添加异常");
				}

				logger.info("结算成功，修改借款人账户资金,借款人：" + settleLoan.getLoanUserId());
				// 借款人账户资金增加，并改变待还金额
				UserFund userFund = new UserFund();
				userFund.setUserId(settleLoan.getLoanUserId());
				userFund.setAvailableAmount(settleLoan.getAmount().subtract(payOut.getPayout_amt()));
				userFund.setDueOutAmount(totalAmount);
				try {
					userFundService.modifyUserFundByParams(userFund);
				} catch (Exception e1) {
					logger.info("修改借款人账户资金异常" + e1.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED, "修改借款人账户资金异常");
				}

				// 修改标的账户余额
				logger.info("结算成功，修改标的账户，标的ID：" + settleLoan.getId());
				LoanTender tender = new LoanTender();
				tender.setLoanId(settleLoan.getId());
				tender.setAmount(settleLoan.getAmount().multiply(new BigDecimal(-1)));
				try {
					loanTenderService.modifyLoanTenderByParams(tender);
				} catch (Exception e1) {
					logger.info("修改标的账户异常" + e1.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED, "修改标的账户异常");
				}

				try {
					String mgs = "“" + settleLoan.getTitle() + "”" + "项目已于" + FormatUtils.simpleFormat(new Date())
							+ "开始记息";
					this.sendInfoToUser(settleLoan, mgs);
				} catch (GeneralSecurityException e) {
					logger.info("手机号解密异常");
				}
				Invest invest = new Invest();
				invest.setLoanId(settleLoan.getId());
				invest.setStatus(InvestStatus.AUDITING);

				List<Invest> result = investService.queryInvestsByParams(invest);
				
				// 修改投资状态
				logger.info("投资成功，修改投资责任资金记录为SETTLED状态");
				
				try {
					invest.setStatus(InvestStatus.SETTLED);
					investService.modifyInvestStatusSettled(invest);
				} catch (Exception e1) {
					logger.info("修改投资记录状态异常" + e1.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED, "修改投资记录状态异常");
				}

				// 修改标的状态
				logger.info("结算成功，修改标的状态为SETTLED，标的ID：" + settleLoan.getId());
				Loan updateLoan = new Loan();
				updateLoan.setId(settleLoan.getId());
				updateLoan.setStatus(LoanStatus.SETTLED);
				updateLoan.setTimeSettled(new Date());
				try {
					loanService.modifyLoanByparams(updateLoan);
				} catch (Exception e1) {
					logger.info("修改标的状态异常" + e1.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED, "修改标的状态异常");
				}

				// 结算成功投资人冻结资金解冻
				List<UserFund> funds = new ArrayList<UserFund>();
				for (Invest inv : result) {
					UserFund fund = new UserFund();
					fund.setUserId(inv.getUserId());
					fund.setFrozenAmount(inv.getAmount().multiply(new BigDecimal(-1)));
					fund.setDueInAmount(new BigDecimal(0));
					for (InvestRepayment ir : investRepayments) {
						if (ir.getInvestId().equals(inv.getId()))
							fund.setDueInAmount(
									fund.getDueInAmount().add(ir.getAmountInterest()).add(ir.getAmountPrincipal()));
					}
					funds.add(fund);
				}
				try {
					userFundService.batchModifyUserFunds(funds);
				} catch (Exception e1) {
					logger.info("修改投资人账户异常" + e1.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED, "修改投资人账户异常");
				}

				lr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "结算成功"));

			} else {

				order.setOrderStatus(OrderStatus.FAILED);

				updateFundRecord.setStatus(FundRecordStatus.FAILED);

				updateLoanTransRecord.setStatus(FundRecordStatus.FAILED);

				updatePlatFundRecord.setStatus(FundRecordStatus.FAILED);
				lr.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(), recode + remsg));
			}

			logger.info("修改结算订单：" + JSON.toJSONString(order));
			try {
				bmOrderService.bmOrderModify(order);
			} catch (Exception e1) {
				logger.info("修改订单状态异常" + e1.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED, "修改订单状态异常");
			}

			if (records.size() > 0) {
				logger.info("修改资金记录状态" + JSON.toJSONString(updateFundRecord));
				try {
					fundRecordService.modifyFundRecordByParams(updateFundRecord);
				} catch (Exception e1) {
					logger.info("修改资金记录状态异常" + e1.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED, "修改资金记录状态异常");
				}

			}

			if (transRecords.size() > 0) {
				logger.info("修改标的交易记录" + JSON.toJSONString(updateLoanTransRecord));

				try {
					loanTransRecordService.modifyLoanTransRecord(updateLoanTransRecord);
				} catch (Exception e1) {
					logger.info("修改标的交易记录状态异常" + e1.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED, "修改标的交易记录状态异常");
				}
			}

			if (platFundRecords.size() > 0) {
				logger.info("修改平台资金记录" + JSON.toJSONString(updatePlatFundRecord));
				try {
					platFundRecordService.modifyPlatFundRecord(updatePlatFundRecord);
				} catch (Exception e1) {
					logger.info("修改平台资金记录状态异常" + e1.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED, "修改平台资金记录状态异常");
				}

			}
		} catch (UException e) {
			logger.info("结算异常");
			logger.info(e.fillInStackTrace());
			lr.setMessage(new Message(e.getCode().value(), e.getMessage()));
		}

		return lr;
	}

	private List<InvestRepayment> investRepayment(Loan loan, String[] dates) throws UException {
		List<InvestRepayment> investRepayments = new ArrayList<InvestRepayment>();
		// 个人投资回款计划
		if (loan != null) {

			// 符合条件的标的投资记录
			Invest invest = new Invest();
			invest.setStatus(InvestStatus.AUDITING);
			invest.setLoanId(loan.getId());
			List<Invest> invests = investService.queryInvestsByParams(invest);
			BigDecimal totalInvestAmount = new BigDecimal(0);
			for (Invest in : invests) {
				totalInvestAmount = totalInvestAmount.add(in.getAmount());
			}

			if (loan.getAmount().subtract(totalInvestAmount).intValue() != 0)
				throw new UException(SystemEnum.DATA_REFUSED, "投资记录存在异常，不能结算");

			for (Invest inv : invests) {

				// 按月付息到期还款
				if (Method.MonthlyInterest.equals(loan.getMethod())) {
					List<InvestRepayment> investRepayment = makeInvestRepayment(inv, loan, dates);
					investRepayments.addAll(investRepayment);// 用户投资回款计划

				}
				if (Method.BulletRepayment.equals(loan.getMethod())) {
					InvestRepayment investRepayment = makeInvestRepaymentByBulletmonths(inv, loan, dates);
					investRepayments.add(investRepayment);// 用户投资回款计划
				}
			}

		}
		return investRepayments;

	}

	private List<InvestRepayment> makeInvestRepayment(Invest in, Loan loan, String[] dates) {
		List<InvestRepayment> list = new ArrayList<InvestRepayment>();
		try {
			int poid = 0;
			for (String str : dates) {

				if (str == null)
					continue;
				poid++;
				InvestRepayment investRepayment = new InvestRepayment();
				// 利息算法
				BigDecimal reteAmount = ArithmeticInterest
						.getArithmeticMonthInterest(loan.getRate() + loan.getAddRate(), in.getAmount());
				investRepayment.setId(GetUUID.getUniqueKey());
				investRepayment.setCurrentPeriod(poid);
				investRepayment.setStatus(LoanRepayMent.UNDUE);
				investRepayment.setAmountInterest(reteAmount);// 还款利息
				investRepayment.setInvestId(in.getId());// 投资主键
				if (poid == loan.getMonths()) {
					investRepayment.setAmountOutStanding(new BigDecimal(0.00));
					investRepayment.setAmountPrincipal(in.getAmount());
				} else {
					investRepayment.setAmountOutStanding(in.getAmount());
					investRepayment.setAmountPrincipal(new BigDecimal(0.00));
				}
				investRepayment.setDueDate(FormatUtils.dateFormat(str));// 到期时间
				list.add(investRepayment);
			}

		} catch (Exception e) {
			logger.info("按月付息到期还本生成数据储出错 :" + e);
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 一次性还本付息回款计划算法
	 * 
	 * @date 上午11:28:23
	 * @param in
	 * @param loanInfo
	 * @param date
	 * @return
	 * @author suzh
	 */
	private InvestRepayment makeInvestRepaymentByBulletmonths(Invest in, Loan loan, String[] dates) {
		InvestRepayment investRepayment = new InvestRepayment();
		try {
			BigDecimal reteAmount = ArithmeticInterest.getArithmeticInterest(loan.getRate() + loan.getAddRate(),
					in.getAmount(), loan.getMonths());// 利息算法
			investRepayment.setId(GetUUID.getUniqueKey());
			investRepayment.setCurrentPeriod(1);
			investRepayment.setStatus(LoanRepayMent.UNDUE);
			investRepayment.setAmountInterest(reteAmount);// 还款利息
			investRepayment.setInvestId(in.getId());// 投资主键
			investRepayment.setAmountOutStanding(in.getAmount());
			investRepayment.setAmountPrincipal(in.getAmount());
			investRepayment.setDueDate(FormatUtils.dateFormat(dates[1]));
		} catch (Exception e) {
			logger.info("按月付息到期还本生成数据储出错 :" + e);
			e.printStackTrace();
		}

		return investRepayment;
	}

	private List<FundRecord> createFundRecod(Loan settleLoan, BmOrder order) {
		/*
		 * loanServiceFee//服务费率 loanGuaranteeFee//担保费率 loanRiskFee//风险管理费率
		 * loanManageFee//借款管理费率
		 */
		BigDecimal loanServiceFee = settleLoan.getAmount().multiply(settleLoan.getLoanServiceFee())
				.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_EVEN);
		BigDecimal loanGuaranteeFee = settleLoan.getAmount().multiply(settleLoan.getLoanGuaranteeFee())
				.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_EVEN);
		BigDecimal loanRiskFee = settleLoan.getAmount().multiply(settleLoan.getLoanRiskFee())
				.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_EVEN);
		BigDecimal loanManageFee = settleLoan.getAmount().multiply(settleLoan.getLoanManageFee())
				.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_EVEN);

		UserFund userFund = new UserFund();
		userFund.setUserId(settleLoan.getLoanUserId());
		UserFund loanUserFund = userFundService.queryUserFundByParams(userFund);

		// 借款人资金记录
		List<FundRecord> records = new ArrayList<FundRecord>();

		Date date = new Date();
		// 借款人入账记录
		FundRecord record = new FundRecord();
		record.setId(GetUUID.getUniqueKey());
		record.setAmount(settleLoan.getAmount());
		record.setAviAmount(loanUserFund.getAvailableAmount().add(settleLoan.getAmount()));
		record.setDescription("借款放款");
		record.setOperation(FundRecordOperation.IN);
		record.setOrderId(order.getOrderId());
		record.setStatus(FundRecordStatus.PROCESSING);
		record.setTimeRecorded(date);
		record.setType(FundRecordType.LOAN);
		record.setUserId(loanUserFund.getUserId());
		records.add(record);

		// 借款服务费
		if (loanServiceFee.doubleValue() != 0) {
			FundRecord re = new FundRecord();
			re.setId(GetUUID.getUniqueKey());
			re.setAmount(loanServiceFee);
			re.setAviAmount(record.getAviAmount().subtract(loanServiceFee));
			re.setDescription("借款服务费");
			re.setOperation(FundRecordOperation.OUT);
			re.setOrderId(order.getOrderId());
			re.setStatus(FundRecordStatus.PROCESSING);
			re.setTimeRecorded(date);
			re.setType(FundRecordType.FEE_LOAN_SERVICE);
			re.setUserId(loanUserFund.getUserId());
			records.add(re);
		}
		// 担保费率
		if (loanGuaranteeFee.doubleValue() != 0) {
			FundRecord re = new FundRecord();
			re.setId(GetUUID.getUniqueKey());
			re.setAmount(loanGuaranteeFee);
			re.setAviAmount(record.getAviAmount().subtract(loanServiceFee).subtract(loanGuaranteeFee));
			re.setDescription("借款担保费");
			re.setOperation(FundRecordOperation.OUT);
			re.setOrderId(order.getOrderId());
			re.setStatus(FundRecordStatus.PROCESSING);
			re.setTimeRecorded(date);
			re.setType(FundRecordType.FEE_LOAN_GUARANTEE);
			re.setUserId(loanUserFund.getUserId());
			records.add(re);
		}
		// 风险管理费率
		if (loanRiskFee.doubleValue() != 0) {
			FundRecord re = new FundRecord();
			re.setId(GetUUID.getUniqueKey());
			re.setAmount(loanRiskFee);
			re.setAviAmount(
					record.getAviAmount().subtract(loanServiceFee).subtract(loanGuaranteeFee).subtract(loanRiskFee));
			re.setDescription("借款风险管理费");
			re.setOperation(FundRecordOperation.OUT);
			re.setOrderId(order.getOrderId());
			re.setStatus(FundRecordStatus.PROCESSING);
			re.setTimeRecorded(date);
			re.setType(FundRecordType.FEE_LOAN_GUARANTEE);
			re.setUserId(loanUserFund.getUserId());
			records.add(re);
		}
		// 借款管理费率
		if (loanManageFee.doubleValue() != 0) {
			FundRecord re = new FundRecord();
			re.setId(GetUUID.getUniqueKey());
			re.setAmount(loanManageFee);
			re.setAviAmount(record.getAviAmount().subtract(loanServiceFee).subtract(loanGuaranteeFee)
					.subtract(loanRiskFee).subtract(loanManageFee));
			re.setDescription("借款管理费");
			re.setOperation(FundRecordOperation.OUT);
			re.setOrderId(order.getOrderId());
			re.setStatus(FundRecordStatus.PROCESSING);
			re.setTimeRecorded(date);
			re.setType(FundRecordType.FEE_LOAN_MANAGE);
			re.setUserId(loanUserFund.getUserId());
			records.add(re);
		}
		return records;
	}

	private List<LoanTransRecord> createLoanTransRecord(Loan settleLoan, BmOrder order) {
		/*
		 * loanServiceFee//服务费率 loanGuaranteeFee//担保费率 loanRiskFee//风险管理费率
		 * loanManageFee//借款管理费率
		 */
		BigDecimal loanServiceFee = settleLoan.getAmount().multiply(settleLoan.getLoanServiceFee())
				.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_EVEN);
		BigDecimal loanGuaranteeFee = settleLoan.getAmount().multiply(settleLoan.getLoanGuaranteeFee())
				.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_EVEN);
		BigDecimal loanRiskFee = settleLoan.getAmount().multiply(settleLoan.getLoanRiskFee())
				.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_EVEN);
		BigDecimal loanManageFee = settleLoan.getAmount().multiply(settleLoan.getLoanManageFee())
				.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_EVEN);

		BigDecimal totalFee = loanServiceFee.add(loanGuaranteeFee).add(loanRiskFee).add(loanManageFee);

		UserFund userFund = new UserFund();
		userFund.setUserId(settleLoan.getLoanUserId());
		UserFund loanUserFund = userFundService.queryUserFundByParams(userFund);

		// 借款人资金记录
		List<LoanTransRecord> records = new ArrayList<LoanTransRecord>();

		// 标的出账记录---->出账到借款人
		LoanTransRecord record = new LoanTransRecord();
		record.setLoanId(settleLoan.getId());
		record.setId(GetUUID.getUniqueKey());
		record.setAmount(settleLoan.getAmount().subtract(totalFee));
		record.setOpration(FundRecordOperation.OUT);
		record.setOrderId(order.getOrderId());
		record.setStatus(FundRecordStatus.PROCESSING);
		record.setTransDate(order.getOrderDate());
		record.setTransTime(order.getOrderTime());
		record.setType(FundRecordType.LOAN);
		record.setUserId(loanUserFund.getUserId());
		record.setRemark("标的结算，借款人应收资金从标的账户流向借款人账户,虚拟记录");
		records.add(record);

		// 标的出账记录---->出账到平台自有账户
		// 借款服务费
		if (loanServiceFee.doubleValue() != 0) {
			LoanTransRecord re = new LoanTransRecord();
			re.setLoanId(settleLoan.getId());
			re.setId(GetUUID.getUniqueKey());
			re.setAmount(loanServiceFee);
			re.setOpration(FundRecordOperation.OUT);
			re.setOrderId(order.getOrderId());
			re.setStatus(FundRecordStatus.PROCESSING);
			re.setTransDate(order.getOrderDate());
			re.setTransTime(order.getOrderTime());
			re.setType(FundRecordType.FEE_LOAN_SERVICE);
			re.setRemark("标的结算，借款服务费流向平台自有账户,虚拟记录");
			records.add(re);
		}
		// 担保费率
		if (loanGuaranteeFee.doubleValue() != 0) {
			LoanTransRecord re = new LoanTransRecord();
			re.setLoanId(settleLoan.getId());
			re.setId(GetUUID.getUniqueKey());
			re.setAmount(loanGuaranteeFee);
			re.setOpration(FundRecordOperation.OUT);
			re.setOrderId(order.getOrderId());
			re.setStatus(FundRecordStatus.PROCESSING);
			re.setTransDate(order.getOrderDate());
			re.setTransTime(order.getOrderTime());
			re.setType(FundRecordType.FEE_LOAN_GUARANTEE);
			re.setRemark("标的结算，担保费流向平台自有账户,虚拟记录");
			records.add(re);
		}
		// 风险管理费率
		if (loanRiskFee.doubleValue() != 0) {
			LoanTransRecord re = new LoanTransRecord();
			re.setLoanId(settleLoan.getId());
			re.setId(GetUUID.getUniqueKey());
			re.setAmount(loanRiskFee);
			re.setOpration(FundRecordOperation.OUT);
			re.setOrderId(order.getOrderId());
			re.setStatus(FundRecordStatus.PROCESSING);
			re.setTransDate(order.getOrderDate());
			re.setTransTime(order.getOrderTime());
			re.setType(FundRecordType.FEE_LOAN_RISK);
			re.setRemark("标的结算，风险管理费流向平台自有账户,虚拟记录");
			records.add(re);
		}
		// 借款管理费率
		if (loanManageFee.doubleValue() != 0) {
			LoanTransRecord re = new LoanTransRecord();
			re.setLoanId(settleLoan.getId());
			re.setId(GetUUID.getUniqueKey());
			re.setAmount(loanManageFee);
			re.setOpration(FundRecordOperation.OUT);
			re.setOrderId(order.getOrderId());
			re.setStatus(FundRecordStatus.PROCESSING);
			re.setTransDate(order.getOrderDate());
			re.setTransTime(order.getOrderTime());
			re.setType(FundRecordType.FEE_LOAN_MANAGE);
			re.setRemark("标的结算，借款管理费流向平台自有账户,虚拟记录");
			records.add(re);
		}
		return records;
	}

	private List<PlatFundRecord> createPlatFundRecord(Loan settleLoan, BmOrder order) {
		/*
		 * loanServiceFee//服务费率 loanGuaranteeFee//担保费率 loanRiskFee//风险管理费率
		 * loanManageFee//借款管理费率
		 */
		BigDecimal loanServiceFee = settleLoan.getAmount().multiply(settleLoan.getLoanServiceFee())
				.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_EVEN);
		BigDecimal loanGuaranteeFee = settleLoan.getAmount().multiply(settleLoan.getLoanGuaranteeFee())
				.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_EVEN);
		BigDecimal loanRiskFee = settleLoan.getAmount().multiply(settleLoan.getLoanRiskFee())
				.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_EVEN);
		BigDecimal loanManageFee = settleLoan.getAmount().multiply(settleLoan.getLoanManageFee())
				.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_EVEN);

		// 平台资金记录
		List<PlatFundRecord> records = new ArrayList<PlatFundRecord>();

		// 标的出账记录---->出账到平台自有账户
		// 借款服务费
		if (loanServiceFee.doubleValue() != 0) {
			PlatFundRecord re = new PlatFundRecord();
			re.setId(GetUUID.getUniqueKey());
			re.setAccount("3");
			re.setAmount(loanServiceFee);
			re.setDescription("借款服务费");
			re.setOperation(FundRecordOperation.IN);
			re.setOrderId(order.getOrderId());
			re.setStatus(FundRecordStatus.PROCESSING);
			re.setTimeRecorded(new Date());
			re.setType(FundRecordType.FEE_LOAN_SERVICE);
			re.setRemark("标的结算，借款服务费流向平台自有账户,虚拟记录");
			re.setUserId(settleLoan.getLoanUserId());
			re.setUserName(settleLoan.getLoanUserName());
			records.add(re);
		}
		// 担保费率
		if (loanGuaranteeFee.doubleValue() != 0) {
			PlatFundRecord re = new PlatFundRecord();
			re.setId(GetUUID.getUniqueKey());
			re.setAccount("3");
			re.setAmount(loanGuaranteeFee);
			re.setDescription("担保费");
			re.setOperation(FundRecordOperation.IN);
			re.setOrderId(order.getOrderId());
			re.setStatus(FundRecordStatus.PROCESSING);
			re.setTimeRecorded(new Date());
			re.setType(FundRecordType.FEE_LOAN_GUARANTEE);
			re.setRemark("标的结算，担保费流向平台自有账户,虚拟记录");
			re.setUserId(settleLoan.getLoanUserId());
			re.setUserName(settleLoan.getLoanUserName());
			records.add(re);
		}
		// 风险管理费率
		if (loanRiskFee.doubleValue() != 0) {
			PlatFundRecord re = new PlatFundRecord();
			re.setId(GetUUID.getUniqueKey());
			re.setAmount(loanRiskFee);
			re.setAccount("3");
			re.setDescription("风险管理费");
			re.setOperation(FundRecordOperation.IN);
			re.setOrderId(order.getOrderId());
			re.setStatus(FundRecordStatus.PROCESSING);
			re.setTimeRecorded(new Date());
			re.setType(FundRecordType.FEE_LOAN_RISK);
			re.setRemark("标的结算，风险管理费流向平台自有账户,虚拟记录");
			re.setUserId(settleLoan.getLoanUserId());
			re.setUserName(settleLoan.getLoanUserName());
			records.add(re);
		}
		// 借款管理费率
		if (loanManageFee.doubleValue() != 0) {
			PlatFundRecord re = new PlatFundRecord();
			re.setId(GetUUID.getUniqueKey());
			re.setAccount("3");
			re.setAmount(loanManageFee);
			re.setDescription("借款管理费");
			re.setOperation(FundRecordOperation.IN);
			re.setOrderId(order.getOrderId());
			re.setStatus(FundRecordStatus.PROCESSING);
			re.setTimeRecorded(new Date());
			re.setType(FundRecordType.FEE_LOAN_MANAGE);
			re.setRemark("标的结算，借款管理费流向平台自有账户,虚拟记录");
			re.setUserId(settleLoan.getLoanUserId());
			re.setUserName(settleLoan.getLoanUserName());
			records.add(re);
		}
		return records;
	}

	private void sendInfoToUser(Loan loan, String mgs) throws GeneralSecurityException {

		Map<String, Object> map = new HashMap<String, Object>();
		Invest invest = new Invest();
		invest.setLoanId(loan.getId());
		invest.setStatus(InvestStatus.AUDITING);
		List<User> users = userService.queryUsersByInvestParams(invest);
		DESTextCipher cipher = DESTextCipher.getDesTextCipher();
		StringBuffer sb = new StringBuffer();
		if (users.size() > 0) {
			for (User user : users) {
				String mobile = user.getMobile();
				String mobiles = cipher.decrypt(mobile);
				sb.append(mobiles + ",");

			}
		}
		String mob = sb.toString();
		map.put("info", mgs);
		map.put("mobiles", mob.substring(0, mob.length() - 1));
		ShortMessage.getShortMessage().getSendToUserMsg(map);

	}

	@Override
	public LoanManagerReturn failedLoan(LoanManagerParams params) {
		LoanManagerReturn lr = new LoanManagerReturn();
		try {

			logger.info("流标标的参数校验");
			if (params == null || params.getLoan() == null || params.getLoan().getId() == null)
				throw new UException(SystemEnum.PARAMS_ERROR, "参数错误");

			logger.info("流标标的参数校验通过，流标标的ID：" + params.getLoan().getId());

			List<Loan> res = loanService.queryLoanByParams(params.getLoan());
			Loan loan = res.get(0);

			// 构造流标订单
			BmOrder order = new BmOrder();
			order.setOrderType(OrderType.LOAN_FAILED);
			order.setUserId("F2683775-060B-441B-AEBD-41DF9806C0FC");// 平台操作
			order.setRemark("流标");
			try {
				order = bmOrderService.bmOrderPersistence(order);
			} catch (Exception e1) {
				logger.info("订单入库异常" + e1.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED, "订单入库异常");
			}

			// 获得标的投资人并且添加流标返款资金记录
			List<LoanTransRecord> transRecords = new ArrayList<LoanTransRecord>();

			List<FundRecord> records = new ArrayList<FundRecord>();

			Invest invest = new Invest();
			invest.setLoanId(loan.getId());
			invest.setStatus(InvestStatus.AUDITING);
			List<UserFund> userFunds = userFundService.queryByInvest(invest);

			Date date = new Date();
			for (UserFund userFund : userFunds) {
				FundRecord record = new FundRecord();
				record.setId(GetUUID.getUniqueKey());
				record.setOrderId(order.getOrderId());
				record.setAmount(userFund.getRemarkAmount());
				record.setDescription("流标返款");
				record.setOperation(FundRecordOperation.IN);
				record.setStatus(FundRecordStatus.PROCESSING);
				record.setTimeRecorded(date);
				record.setType(FundRecordType.FAILED_LOAN_REPAY);
				record.setUserId(userFund.getUserId());
				records.add(record);

				// 标的出账记录---->流标返款给投资人
				LoanTransRecord trRecord = new LoanTransRecord();
				trRecord.setId(GetUUID.getUniqueKey());
				trRecord.setAmount(userFund.getRemarkAmount());
				trRecord.setOpration(FundRecordOperation.OUT);
				trRecord.setOrderId(order.getOrderId());
				trRecord.setStatus(FundRecordStatus.PROCESSING);
				trRecord.setTransDate(order.getOrderDate());
				trRecord.setTransTime(order.getOrderTime());
				trRecord.setType(FundRecordType.FAILED_LOAN_REPAY);
				trRecord.setUserId(userFund.getUserId());
				trRecord.setLoanId(loan.getId());
				trRecord.setRemark("流标返款给投资人，虚拟记录，一个流标订单，所有相应的用户全部返款");
				transRecords.add(trRecord);
			}
			if (records.size() != 0) {
				try {
					fundRecordService.batchAddtFundRecord(records);
				} catch (Exception e1) {
					logger.info("批量添加资金记录异常" + e1.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED, "批量添加资金记录异常");
				}

			}
			if (transRecords.size() != 0) {
				try {
					loanTransRecordService.batchAddRecords(transRecords);
				} catch (Exception e1) {
					logger.info("批量添加标的交易记录异常" + e1.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED, "批量添加标的交易记录异常");
				}
			}

			// 构造存管系统标的成立接口参数
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("order_no", order.getOrderId());
			map.put("partner_trans_date", order.getOrderDate());
			map.put("partner_trans_time", order.getOrderTime());
			map.put("prod_id", loan.getId());
			map.put("flag", 3);
			Map<String, Object> sendRequest = null;
			try {
				sendRequest = RequestUtil.sendRequest(map, ZYCF_LOAN_ISTABLISH);
			} catch (JSONException | IOException e) {
				e.printStackTrace();
				logger.info(e.fillInStackTrace());
				throw new UException(SystemEnum.NET_CONNET_EXCEPTION, "网络连接异常");
			}

			String recode = (String) sendRequest.get("recode");
			String remsg = (String) sendRequest.get("remsg");

			FundRecord updateFundRecord = new FundRecord();
			updateFundRecord.setOrderId(order.getOrderId());

			LoanTransRecord transRecord = new LoanTransRecord();
			transRecord.setOrderId(order.getOrderId());

			order.setRecord(recode);
			order.setRemsg(remsg);

			if ("10000".equals(recode)) {

				order.setOrderStatus(OrderStatus.SUCCESSFUL);

				BigDecimal totalAmount = new BigDecimal(0);
				if (records.size() != 0) {
					updateFundRecord.setStatus(FundRecordStatus.SUCCESSFUL);
					transRecord.setStatus(FundRecordStatus.SUCCESSFUL);

					// 用户冻结资金解冻
					for (UserFund fund : userFunds) {
						totalAmount = totalAmount.add(fund.getRemarkAmount());
						UserFund uf = new UserFund();
						uf.setUserId(fund.getUserId());
						uf.setAvailableAmount(fund.getRemarkAmount());
						uf.setFrozenAmount(fund.getRemarkAmount().multiply(new BigDecimal(-1)));

						try {
							userFundService.modifyUserFundByParams(uf);
						} catch (Exception e) {
							logger.info("账户解冻异常" + e.fillInStackTrace());
						}
					}

					try {
						String mgs = "“" + loan.getTitle() + "”" + "项目由于外部因素于" + FormatUtils.millisDateFormat(date)
								+ "流标，给您带来不便敬请谅解";
						this.sendInfoToUser(loan, mgs);
					} catch (GeneralSecurityException e) {
						logger.info("手机号解密异常");
					}

					// 修改投资状态
					invest.setLoanId(loan.getId());
					invest.setStatus(InvestStatus.FAILED);
					try {
						investService.modifyInvestStatusSettled(invest);
					} catch (Exception e1) {
						logger.info("修改投资状态异常" + e1.fillInStackTrace());
						throw new UException(SystemEnum.DATA_REFUSED, "修改投资状态异常");
					}

				}

				Loan updateLoan = new Loan();
				updateLoan.setId(loan.getId());
				updateLoan.setStatus(LoanStatus.FAILED);
				updateLoan.setTimeFailed(date);
				try {
					loanService.modifyLoanByparams(updateLoan);
				} catch (Exception e1) {
					logger.info("修改标的状态异常" + e1.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED, "修改标的状态异常");
				}

				LoanTender loanTender = new LoanTender();
				loanTender.setLoanId(loan.getId());
				loanTender.setAmount(totalAmount.multiply(new BigDecimal(-1)));
				try {
					loanTenderService.modifyLoanTenderByParams(loanTender);
				} catch (Exception e1) {
					logger.info("修改标的账户异常" + e1.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED, "修改标的账户异常");
				}

				Project project = new Project();
				project.setId(loan.getProjectId());
				project.setSurplusAmount(loan.getAmount());
				project.setPublishedAmount(loan.getAmount().multiply(new BigDecimal(-1)));
				projectService.failedLoanUpdatePaoject(project);

				lr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "流标成功"));
			} else {
				order.setOrderStatus(OrderStatus.FAILED);

				if (records.size() != 0) {
					updateFundRecord.setStatus(FundRecordStatus.FAILED);
					transRecord.setStatus(FundRecordStatus.FAILED);
				}
				lr.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(), "流标失败"));
			}
			try {
				bmOrderService.bmOrderModify(order);
			} catch (Exception e1) {
				logger.info("修改订单状态异常" + e1.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED, "修改订单状态异常");
			}

			if (records.size() != 0) {
				try {
					fundRecordService.modifyFundRecordByParams(updateFundRecord);
				} catch (Exception e1) {
					logger.info("修改资金记录状态异常" + e1.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED, "修改资金记录状态异常");
				}

			}
			if (transRecords.size() != 0) {
				try {
					loanTransRecordService.modifyLoanTransRecord(transRecord);
				} catch (Exception e1) {
					logger.info("修改标的交易状态异常" + e1.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED, "修改标的交易状态异常");
				}

			}
		} catch (UException e) {
			logger.info("流标失败");
			logger.info(e.fillInStackTrace());
			lr.setMessage(new Message(e.getCode().value(), e.getMessage()));
		}
		return lr;
	}

	@Override
	public LoanManagerReturn repayLoan(LoanManagerParams params) {
		LoanManagerReturn lr = new LoanManagerReturn();
		try {
			logger.info("还款标的参数校验");
			if (params == null || params.getLoanRepayment() == null || params.getLoanRepayment().getId() == null)
				throw new UException(SystemEnum.PARAMS_ERROR, "参数错误");

			logger.info("还款标的参数校验通过，还款标的ID：" + params.getLoanRepayment().getLoanId());

			List<LoanRepayment> loanRepayments = loanRepaymentService
					.queryLoanRepaymentsByParams(params.getLoanRepayment());
			if (loanRepayments == null || loanRepayments.size() != 1)
				throw new UException(SystemEnum.PARAMS_ERROR, "还款计划查询异常");

			LoanRepayment loanRepayment = loanRepayments.get(0);

			Loan loanParams = new Loan();
			loanParams.setId(loanRepayment.getLoanId());
			List<Loan> res = loanService.queryLoanByParams(loanParams);
			Loan loan = res.get(0);

			LoanRepayment repayment = (LoanRepayment) super.getZsessionObject(
					loanRepayment.getLoanId() + "-" + loanRepayment.getCurrentPeriod());
			if (repayment != null)
				throw new UException(SystemEnum.PARAMS_ERROR, "还款处理中，不可以重复还款");
			else
				super.setZsession(loanRepayment.getLoanId() + "-" + loanRepayment.getCurrentPeriod(), loanRepayment);

			if (LoanRepayMent.REPAYED.equals(loanRepayment.getStatus())
					|| LoanRepayMent.PREPAY.equals(loanRepayment.getStatus()))
				throw new UException(SystemEnum.PARAMS_ERROR, "该期已还完，不可以重复还款");

			if (!loanRepayment.isRepay())
				throw new UException(SystemEnum.PARAMS_ERROR, "借款人未还款，不允许做标的还款");

			LoanTender tender = new LoanTender();
			tender.setLoanId(loan.getId());
			LoanTender loanTender = loanTenderService.queryByParams(tender);

			BigDecimal totalAmount = loanRepayment.getAmountInterest().add(loanRepayment.getAmountPrincipal());
			if (loanTender.getAmount().subtract(totalAmount).doubleValue() < 0)
				throw new UException(SystemEnum.PARAMS_ERROR, "标的账户余额不足，不能还款");

			// 查询该期标的投资人回款计划
			List<InvestRepayment> investRepayments = investRepaymentService.queryByLoanRepayment(loanRepayment);

			// 构造标的还款订单
			BmOrder order = new BmOrder();
			order.setOrderType(OrderType.LOAN_REPAY);
			try {
				order = bmOrderService.bmOrderPersistence(order);
			} catch (Exception e1) {
				logger.info("订单入库异常" + e1.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED, "订单入库异常");
			}

			Date date = new Date();

			List<FundRecord> records = new ArrayList<FundRecord>();
			List<LoanTransRecord> transRecords = new ArrayList<LoanTransRecord>();
			List<PlatFundRecord> platFundRecords = new ArrayList<PlatFundRecord>();
			List<CustRepayList> custRepayList = new ArrayList<CustRepayList>();
			List<UserFund> userFunds = new ArrayList<UserFund>();
			BigDecimal trans_amt = new BigDecimal(0);
			for (InvestRepayment investRepayment : investRepayments) {
				if (!LoanRepayMent.UNDUE.equals(investRepayment.getStatus()))
					continue;
				BigDecimal amountInterest = investRepayment.getAmountInterest();
				BigDecimal AmountPrincipal = investRepayment.getAmountPrincipal();
				BigDecimal feeAmountInvest = investRepayment.getAmountInterest()
						.multiply(
								loan.getInvestInterestFee().divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_EVEN))
						.setScale(2, BigDecimal.ROUND_HALF_EVEN);

				// 投资人回款进账记录
				FundRecord record1 = new FundRecord();
				record1.setId(GetUUID.getUniqueKey());
				record1.setAmount(amountInterest.add(AmountPrincipal));
				record1.setAviAmount(investRepayment.getRemarkAmount().add(record1.getAmount()));
				record1.setDescription("投资回款");
				record1.setOperation(FundRecordOperation.IN);
				record1.setOrderId(order.getOrderId());
				record1.setStatus(FundRecordStatus.PROCESSING);
				record1.setTimeRecorded(date);
				record1.setType(FundRecordType.INVEST_REPAY);
				record1.setUserId(investRepayment.getUserId());
				records.add(record1);

				// 投资人回款利息管理费出账记录
				if (loan.getInvestInterestFee().doubleValue() != 0) {
					FundRecord record2 = new FundRecord();
					record2.setId(GetUUID.getUniqueKey());
					record2.setAmount(feeAmountInvest);
					record2.setAviAmount(record1.getAviAmount().subtract(record2.getAmount()));
					record2.setDescription("回款利息管理费");
					record2.setOperation(FundRecordOperation.OUT);
					record2.setOrderId(order.getOrderId());
					record2.setStatus(FundRecordStatus.PROCESSING);
					record2.setTimeRecorded(date);
					record2.setType(FundRecordType.FEE_INVEST_INTEREST);
					record2.setUserId(investRepayment.getUserId());
					records.add(record2);
				}

				// 做标的出账记录 标的账户-->投资人账户
				LoanTransRecord transRecord1 = new LoanTransRecord();
				transRecord1.setOrderId(order.getOrderId());
				transRecord1.setId(GetUUID.getUniqueKey());
				transRecord1.setAmount(record1.getAmount().subtract(feeAmountInvest));
				transRecord1.setOpration(FundRecordOperation.OUT);
				transRecord1.setOrderId(order.getOrderId());
				transRecord1.setStatus(FundRecordStatus.PROCESSING);
				transRecord1.setTransDate(order.getOrderDate());
				transRecord1.setTransTime(order.getOrderTime());
				transRecord1.setType(FundRecordType.LOAN_REPAY);
				transRecord1.setUserId(investRepayment.getUserId());
				transRecord1.setLoanId(loanRepayment.getLoanId());
				transRecord1.setRemark("标的还款，资金从标的账户流向投资人账户，此记录为平台虚拟记录真实记录看存管系统");
				transRecords.add(transRecord1);

				// 平台添加入账记录 标的账户---->平台自有账户
				if (loan.getInvestInterestFee().doubleValue() != 0) {

					PlatFundRecord re = new PlatFundRecord();
					re.setOrderId(order.getOrderId());
					re.setId(GetUUID.getUniqueKey());
					re.setAccount("3");// 利息管理费进入平台自有资金子账户
					re.setAmount(feeAmountInvest);
					re.setDescription("投资利息管理费");
					re.setOperation(FundRecordOperation.IN);
					re.setOrderId(order.getOrderId());
					re.setStatus(FundRecordStatus.PROCESSING);
					re.setTimeRecorded(new Date());
					re.setType(FundRecordType.FEE_INVEST_INTEREST);
					re.setRemark("标的还款，投资人利息管理费从标的账户流向平台自有账户,虚拟记录");
					re.setUserId(investRepayment.getUserId());
					re.setUserName(investRepayment.getRemark());
					platFundRecords.add(re);
				}

				// 存管系统还款明细
				CustRepayList repay = new CustRepayList();
				repay.setReal_repay_amt(record1.getAmount().add(feeAmountInvest));
				repay.setReal_repay_amount(investRepayment.getAmountPrincipal());
				repay.setExperience_amt(new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_EVEN));
				repay.setRates_amt(new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_EVEN));
				repay.setReal_repay_val(amountInterest);
				repay.setRepay_fee(feeAmountInvest);
				repay.setCust_no(investRepayment.getPlatCust());
				repay.setRepay_num(investRepayment.getCurrentPeriod());
				repay.setRepay_date(FormatUtils.simpleFormat(investRepayment.getDueDate()));
				repay.setReal_repay_date(FormatUtils.simpleFormat(date));
				custRepayList.add(repay);
				trans_amt = trans_amt.add(repay.getReal_repay_amt());

				// 准备还款成功时需要修改的回款计划数据
				investRepayment.setRepayAmount(record1.getAmount());
				investRepayment.setRepayDate(date);
				investRepayment.setSourceId(loan.getLoanUserId());
				Long time = investRepayment.getDueDate().getTime() - date.getTime();
				investRepayment.setStatus(time > 0 ? LoanRepayMent.PREPAY : LoanRepayMent.REPAYED);

				// 准备还款成功时投资人账户修改数据
				UserFund userFund = new UserFund();
				userFund.setUserId(investRepayment.getUserId());
				userFund.setAvailableAmount(record1.getAmount().subtract(feeAmountInvest));
				userFund.setDueInAmount(record1.getAmount().multiply(new BigDecimal(-1)));
				userFund.setAllRevenu(amountInterest);
				userFund.setTimeLastUpdate(date);
				userFunds.add(userFund);
			}
			if (records.size() > 0) {
				try {
					fundRecordService.batchAddtFundRecord(records);
				} catch (Exception e1) {
					logger.info("批量添加资金记录异常" + e1.fillInStackTrace());
					super.removeZsessionObject(loanRepayment.getLoanId() + "-" + loanRepayment.getCurrentPeriod());
					throw new UException(SystemEnum.DATA_REFUSED, "批量添加资金记录异常");
				}

			}

			if (transRecords.size() > 0) {
				try {
					loanTransRecordService.batchAddRecords(transRecords);
				} catch (Exception e1) {
					logger.info("批量添加标的交易记录异常" + e1.fillInStackTrace());
					super.removeZsessionObject(loanRepayment.getLoanId() + "-" + loanRepayment.getCurrentPeriod());
					throw new UException(SystemEnum.DATA_REFUSED, "批量添加标的交易记录异常");
				}

			}
			if (platFundRecords.size() > 0) {
				try {
					platFundRecordService.batchAddRecords(platFundRecords);
				} catch (Exception e1) {
					logger.info("批量添加平台资金记录异常" + e1.fillInStackTrace());
					super.removeZsessionObject(loanRepayment.getLoanId() + "-" + loanRepayment.getCurrentPeriod());
					throw new UException(SystemEnum.DATA_REFUSED, "批量添加平台资金记录异常");
				}
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("order_no", order.getOrderId());
			map.put("partner_trans_date", order.getOrderDate());
			map.put("partner_trans_time", order.getOrderTime());
			map.put("prod_id", loan.getId());
			map.put("repay_num", loanRepayment.getCurrentPeriod());
			if (loan.getMonths() == loanRepayment.getCurrentPeriod())
				map.put("is_payoff", 0);
			else
				map.put("is_payoff", 1);

			map.put("trans_amt", trans_amt);
			map.put("repay_flag", 0);

			FundData fundData = new FundData();
			fundData.setCustRepayList(custRepayList);
			map.put("funddata", JSON.toJSONString(fundData));

			Map<String, Object> sendRequest = null;
			try {
				sendRequest = RequestUtil.sendRequest(map, ZYCF_LOAN_REPAY);
			} catch (JSONException | IOException e) {
				e.printStackTrace();
				logger.info(e.fillInStackTrace());
				throw new UException(SystemEnum.NET_CONNET_EXCEPTION, "网络连接异常");
			}

			String recode = (String) sendRequest.get("recode");
			String remsg = (String) sendRequest.get("remsg");

			FundRecord updateRecord = new FundRecord();
			updateRecord.setOrderId(order.getOrderId());

			order.setRecord(recode);
			order.setRemsg(remsg);

			LoanTransRecord updateTransRecord = new LoanTransRecord();
			updateTransRecord.setOrderId(order.getOrderId());

			PlatFundRecord updatePlatRecord = new PlatFundRecord();
			updatePlatRecord.setOrderId(order.getOrderId());

			if ("10000".equals(recode)) {

				order.setOrderStatus(OrderStatus.SUCCESSFUL);

				updateRecord.setStatus(FundRecordStatus.SUCCESSFUL);

				updateTransRecord.setStatus(FundRecordStatus.SUCCESSFUL);

				updatePlatRecord.setStatus(FundRecordStatus.SUCCESSFUL);

				// 更新投资人账户信息
				try {
					userFundService.batchModifyUserFunds(userFunds);
				} catch (Exception e1) {
					logger.info("更新投资人账户异常" + e1.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED, "更新投资人账户异常");
				}

				// 更新标的账户信息
				tender.setAmount(totalAmount.multiply(new BigDecimal(-1)));
				try {
					loanTenderService.modifyLoanTenderByParams(tender);
				} catch (Exception e1) {
					logger.info("更新标的账户异常" + e1.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED, "更新标的账户异常");
				}

				// 更新还款计划状态
				loanRepayment.setRepayAmount(totalAmount);
				loanRepayment.setRepayDate(date);
				Long time = loanRepayment.getDueDate().getTime() - date.getTime();
				loanRepayment.setStatus(time > 0 ? LoanRepayMent.PREPAY : LoanRepayMent.REPAYED);
				try {
					loanRepaymentService.modifyLoanRepayment(loanRepayment);
				} catch (Exception e1) {
					logger.info("更新还款计划状态异常" + e1.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED, "更新还款计划状态异常");
				}

				// 更新回款计划
				try {
					investRepaymentService.batchModifyInvestRepayment(investRepayments);
				} catch (Exception e1) {
					logger.info("更新回款计划状态异常" + e1.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED, "更新回款计划状态异常");
				}

				// 最后一期更新投资状态、标的状态
				if (loan.getMonths() == loanRepayment.getCurrentPeriod()) {
					Loan updateLoan = new Loan();
					updateLoan.setId(loan.getId());
					updateLoan.setStatus(LoanStatus.CLEARED);
					updateLoan.setTimeCleared(date);
					try {
						loanService.modifyLoanByparams(updateLoan);
					} catch (Exception e1) {
						logger.info("更新标的态异常" + e1.fillInStackTrace());
						throw new UException(SystemEnum.DATA_REFUSED, "更新标的态异常");
					}

					Invest invest = new Invest();
					invest.setLoanId(loan.getId());
					invest.setStatus(InvestStatus.CLEARED);
					try {
						investService.modifyInvestStatusCleared(invest);
					} catch (Exception e1) {
						logger.info("更新投资记录态异常" + e1.fillInStackTrace());
						throw new UException(SystemEnum.DATA_REFUSED, "更新投资记录态异常");
					}

				}
				
				//发送短信提醒
				try {
					this.sendMessage(loanRepayment, loan);
				} catch (Exception e) {
					e.printStackTrace();
					logger.info(e.fillInStackTrace());
				}
				
				lr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "还款成功"));
			} else {
				order.setOrderStatus(OrderStatus.FAILED);

				updateRecord.setStatus(FundRecordStatus.FAILED);

				updateTransRecord.setStatus(FundRecordStatus.FAILED);
				updateTransRecord.setRemark(recode + remsg);

				updatePlatRecord.setStatus(FundRecordStatus.FAILED);
				updatePlatRecord.setRemark(recode + remsg);
				lr.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(), recode + remsg));
			}
			try {
				bmOrderService.bmOrderModify(order);
			} catch (Exception e1) {
				logger.info("修改订单状态异常" + e1.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED, "修改订单状态异常");
			}

			try {
				fundRecordService.modifyFundRecordByParams(updateRecord);
			} catch (Exception e1) {
				logger.info("修改资金记录状态异常" + e1.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED, "修改资金记录状态异常");
			}

			try {
				loanTransRecordService.modifyLoanTransRecord(updateTransRecord);
			} catch (Exception e1) {
				logger.info("修改标的交易记录状态异常" + e1.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED, "修改标的交易记录状态异常");
			}

			if (platFundRecords.size() > 0) {
				try {
					platFundRecordService.modifyPlatFundRecord(updatePlatRecord);
				} catch (Exception e1) {
					logger.info("修改平台资金记录状态异常" + e1.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED, "修改标平台资金记录状态异常");
				}

			}
			super.removeZsessionObject(loanRepayment.getLoanId() + "-" + loanRepayment.getCurrentPeriod());

		} catch (UException e) {
			logger.info("还款失败");
			logger.info(e.fillInStackTrace());
			lr.setMessage(new Message(e.getCode().value(), e.getMessage()));

		}
		return lr;
	}
	
	void sendMessage(LoanRepayment loanRepayment,Loan loan)throws Exception{
		if(loanRepayment==null||loanRepayment.getLoanId()==null||loanRepayment.getCurrentPeriod()==null)
			return;
		List<User> users = investService.queryUserInvestRepayment(loanRepayment);
		if(users==null||users.size()<=0)
			return;
		
		if(loan==null||loan.getTitle()==null)
			return;
		
		DESTextCipher cipher=DESTextCipher.getDesTextCipher();
		Map<String, Object> map=new HashMap<String, Object>();
		for (User user : users) {
			try {
				user.setMobile(cipher.decrypt(user.getMobile()));
			} catch (GeneralSecurityException e) {
				continue;
			}
			if(user.getRepayAmount()==null)
				continue;
			map.put("mobiles", user.getMobile());
			map.put("info", "尊敬的左右逢园客户,您投资的“"+loan.getTitle()+"项目--"+loanRepayment.getCurrentPeriod()+"期”回款￥"+
			user.getRepayAmount()+"元,请知悉！");
			String sendToUserMsg = ShortMessage.getShortMessage().sendToUserMsg(map);
			logger.info("短信发送结果"+sendToUserMsg);
		}
		
		
	}

	@Override
	public LoanManagerReturn modifyComentSateUserId(LoanManagerParams params) {
		LoanManagerReturn lmr = new LoanManagerReturn();

		try {
			if (params == null || params.getLoan() == null || params.getLoan().getId() == null
					|| params.getLoan().getComentSateUserId() == null)
				throw new UException(SystemEnum.PARAMS_ERROR, "参数错误");

			Loan loan = new Loan();
			loan.setId(params.getLoan().getId());
			List<Loan> res = loanService.queryLoanByParams(loan);
			if (res == null || res.size() == 0)
				throw new UException(SystemEnum.PARAMS_ERROR, "标的不存在");
			loan = res.get(0);
			if (params.getLoan().getComentSateUserId().equals(loan.getComentSateUserId()))
				throw new UException(SystemEnum.PARAMS_ERROR, "代偿人和已知代偿人为同一人");

			BmAccount account = new BmAccount();
			account.setUserId(params.getLoan().getComentSateUserId());
			account = bmAccountService.queryBmAccountByParams(account);

			// 构造变更订单
			BmOrder order = new BmOrder();
			order.setOrderType(OrderType.COMPENSATE_REPAY);
			try {
				order = bmOrderService.bmOrderPersistence(order);
			} catch (Exception e1) {
				logger.info("订单入库异常" + e1.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED, "订单入库异常");
			}

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("order_no", order.getOrderId());
			map.put("prod_id", params.getLoan().getId());
			List<Compensation> compensations = new ArrayList<Compensation>();
			Compensation cp = new Compensation();
			cp.setPlatcust(account.getPlatcust());
			cp.setType("1");
			compensations.add(cp);
			map.put("compensation", JSON.toJSONString(compensations));
			logger.info("发发哦那个到存管数据：" + map);

			Map<String, Object> sendRequest = null;
			try {
				sendRequest = RequestUtil.sendRequest(map, ZYCF_COMPENSATE_REPAY);
			} catch (JSONException | IOException e) {
				e.printStackTrace();
				logger.info(e.fillInStackTrace());
				throw new UException(SystemEnum.NET_CONNET_EXCEPTION, "网络连接异常");
			}
			logger.info("银行返回信息：" + sendRequest);
			String recode = sendRequest.get("recode").toString();
			String remsg = sendRequest.get("remsg").toString();

			order.setRecord(recode);
			order.setRemsg(remsg);

			if ("10000".equals(recode)) {
				order.setOrderStatus(OrderStatus.SUCCESSFUL);

				Loan updateLoan = new Loan();
				updateLoan.setId(loan.getId());
				updateLoan.setComentSateUserId(params.getLoan().getComentSateUserId());
				try {
					loanService.modifyLoanByparams(updateLoan);
				} catch (Exception e1) {
					logger.info("修改标的信息异常" + e1.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED, "修改标的信息异常");
				}

				lmr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "更新成功"));
			} else {
				order.setOrderStatus(OrderStatus.FAILED);
				lmr.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(), recode + remsg));
			}

		} catch (UException e) {
			logger.info(e.fillInStackTrace());
			lmr.setMessage(new Message(e.getCode().value(), e.getMessage()));
		}
		return lmr;
	}
}
