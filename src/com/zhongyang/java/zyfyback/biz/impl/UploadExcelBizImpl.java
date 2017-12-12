package com.zhongyang.java.zyfyback.biz.impl;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.uitl.DESTextCipher;
import com.zhongyang.java.system.uitl.DateUtil;
import com.zhongyang.java.system.uitl.FormatUtils;
import com.zhongyang.java.system.uitl.Message;
import com.zhongyang.java.system.uitl.UploadExcelUtil;
import com.zhongyang.java.system.uitl.Validator;
import com.zhongyang.java.zyfyback.biz.UploadExcelBiz;
import com.zhongyang.java.zyfyback.params.FundRecordParams;
import com.zhongyang.java.zyfyback.params.LoanRepaymentParams;
import com.zhongyang.java.zyfyback.params.PlatFormParams;
import com.zhongyang.java.zyfyback.params.UploadParams;
import com.zhongyang.java.zyfyback.pojo.FundRecord;
import com.zhongyang.java.zyfyback.pojo.Loan;
import com.zhongyang.java.zyfyback.pojo.PlatFundRecord;
import com.zhongyang.java.zyfyback.pojo.User;
import com.zhongyang.java.zyfyback.returndata.LoanRepayDetail;
import com.zhongyang.java.zyfyback.returndata.PlatFormReturn;
import com.zhongyang.java.zyfyback.returndata.UploadLoans;
import com.zhongyang.java.zyfyback.service.FundRecordService;
import com.zhongyang.java.zyfyback.service.LoanRepaymentService;
import com.zhongyang.java.zyfyback.service.LoanService;
import com.zhongyang.java.zyfyback.service.PlatFundRecordService;
import com.zhongyang.java.zyfyback.service.UserService;

@Service
public class UploadExcelBizImpl implements UploadExcelBiz {
	
	private static Logger logger = Logger.getLogger(UploadExcelBizImpl.class);

	@Autowired
	private LoanService loanService;
	
	@Autowired
	private PlatFundRecordService platFundRecordService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LoanRepaymentService loanRepaymentService;
	
	@Autowired
	private FundRecordService fundRecordService;
	
	
	@Override
	public ResponseEntity<byte[]> uploadPublishedLoans(HttpServletRequest request,UploadParams params) {
		try {
			List<String[]> headNames = new ArrayList<String[]>();
			headNames.add(new String[] { "标的名称", "产品类型", "关联企业", "标的金额（元）", "状态" ,"发布时间","结算时间"});
			List<String[]> fieldNames = new ArrayList<String[]>();
			fieldNames.add(new String[] { "loanName", "productName", "guaranteeRealm", "amount", "status","timeOpen","timeSettled"});
			List<UploadLoans> uploadLoan=new ArrayList<UploadLoans>();

			if(params.getPage().getStrStartTime()!=null&&!"".equals(params.getPage().getStrStartTime()))
				params.getPage().setStartTime(FormatUtils.millisFormat(params.getPage().getStrStartTime()+" 00:00:00"));
			
			if(params.getPage().getStrEndTime()!=null&&!"".equals(params.getPage().getStrEndTime()))
				params.getPage().setEndTime(FormatUtils.millisFormat(params.getPage().getStrEndTime()+" 23:59:59"));
			
			params.getPage().setPageNo(1);
			params.getPage().setPageSize(Integer.MAX_VALUE);
			
			List<Loan>loans = loanService.queryLoanByPage(params.getPage());
			for (Loan loan : loans) {
				UploadLoans ul=new UploadLoans();
				ul.setLoanName(loan.getTitle());
				ul.setProductName(loan.getProductName());
				ul.setGuaranteeRealm(loan.getGuaranteeRealm());
				ul.setAmount(loan.getAmount().toString());
				ul.setStatus(loan.getStatus().toString());
				
				if(loan.getTimeOpen()!=null){
					ul.setTimeOpen(FormatUtils.millisDateFormat(loan.getTimeOpen()));
				}
				if(loan.getTimeSettled()!=null){
					ul.setTimeSettled(FormatUtils.millisDateFormat(loan.getTimeSettled()));
				}
				else{
					ul.setTimeSettled("未结算");
				}
				uploadLoan.add(ul);
			}
			String name="标的发布统计";
			ResponseEntity<byte[]> responseEntity = UploadExcelUtil.upload(request, headNames, fieldNames, uploadLoan, name);
			return responseEntity;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "导出失败");
		} 
	}


	@Override
	public ResponseEntity<byte[]> uploadPlatFormRecord(HttpServletRequest request,PlatFormParams params) {
		List<String[]> headNames = new ArrayList<String[]>();
		headNames.add(new String[] { "订单号", "账户", "用户名", "真实姓名" ,"类型","操作","金额(元)","状态","日期","备注"});
		List<String[]> fieldNames = new ArrayList<String[]>();
		fieldNames.add(new String[] {"orderId", "account","mobile", "userName", "strType","strOpreation","amount","strStatus","strTimeRecorded","description"});
		
		PlatFormReturn pfr=new PlatFormReturn();
		try{
			
			Page<PlatFundRecord> page = params.getPage();
			if(params==null || page==null)
				throw new UException(SystemEnum.PARAMS_ERROR,"参数错误");
			
			if(page.getStrStartTime()!=null&&!"".equals(page.getStrStartTime()))
				page.setStartTime(FormatUtils.millisFormat(page.getStrStartTime()+" 00:00:00"));
			
			if(page.getStrEndTime()!=null&&!"".equals(page.getStrEndTime()))
				page.setEndTime(FormatUtils.millisFormat(page.getStrEndTime()+" 23:59:59"));
			
			DESTextCipher cipher=DESTextCipher.getDesTextCipher();
			if(page.getParams().containsKey("mobile")&&!"".equals(page.getParams().get("mobile").toString())){
				try {
					String mobile=cipher.encrypt(page.getParams().get("mobile").toString());
					User user=new User();
					user.setMobile(mobile);
					user=userService.queryUserByParams(user);
					page.getParams().put("userId", user.getId());
				} catch (GeneralSecurityException e) {
					e.printStackTrace();
					throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION,"数据加密异常");
				}
			}
			page.setPageSize(Integer.MAX_VALUE);	
			
			List<PlatFundRecord> res = platFundRecordService.queryByPage(page);
			for (PlatFundRecord platFundRecord : res) {
				platFundRecord.setStrStatus(platFundRecord.getStatus().getKey());
				platFundRecord.setStrType(platFundRecord.getType().getKey());
				platFundRecord.setStrTimeRecorded(FormatUtils.millisDateFormat(platFundRecord.getTimeRecorded()));
				platFundRecord.setStrOpreation(platFundRecord.getOperation().getKey());
				platFundRecord.setAccount(this.getPlatAccountType(platFundRecord.getAccount()));
				if(platFundRecord.getMobile()!=null){
					try {
						String mobile=cipher.decrypt(platFundRecord.getMobile());
						platFundRecord.setMobile(mobile.substring(0, 3)+"****"+mobile.substring(mobile.length()-4,mobile.length()));
					} catch (GeneralSecurityException e) {
						e.printStackTrace();
						throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION,"数据解密异常");
					}
				}
				
			}
			String name="平台资金记录";
			ResponseEntity<byte[]> responseEntity=null;
			try {
				responseEntity = UploadExcelUtil.upload(request, headNames, fieldNames, res, name);
			} catch (IllegalArgumentException | IllegalAccessException | IOException e) {
				e.printStackTrace();
			} 
			return responseEntity;
			
		}catch(UException e){
			logger.info("平台资金记录查询异常");
			logger.info(e.fillInStackTrace());
			pfr.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		
		return null;
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
		default:accountName="中阳财富"; break;
		}
		return accountName;
	}


	@Override
	public ResponseEntity<byte[]> uploadLoanRepaymentList(HttpServletRequest request, LoanRepaymentParams params) {
		List<String[]> headNames = new ArrayList<String[]>();
		headNames.add(new String[] { "到期日", "标的名称", "借款人", "期数" ,"待还金额（元）","标的账户余额（元）","标的还款状态","借款人是否还款"});
		List<String[]> fieldNames = new ArrayList<String[]>();
		fieldNames.add(new String[] {"dueDate", "title","userName", "currentPeriod", "amount","account_aviamount","strStatus","strRepay"});
		try{
			if(params==null||params.getPage()==null)
				throw new UException(SystemEnum.PARAMS_ERROR,"参数错误");
			String str=null;
			if(params.getPage().getParams().containsKey("loanUser")) 
				str = params.getPage().getParams().get("loanUser").toString();
			
			DESTextCipher cipher=DESTextCipher.getDesTextCipher();
			if(str!=null&&Validator.isMobile(str))
				try {
					params.getPage().getParams().put("mobile", cipher.encrypt(str));
				} catch (GeneralSecurityException e) {
					e.printStackTrace();
					logger.info("手机号加密异常");
					logger.info(e.fillInStackTrace());
					throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION,"手机号加密异常");
				}
			else
				params.getPage().getParams().put("userName", str);
			
			String flag=null;
			if(params.getPage().getParams().containsKey("unrepay"))
				flag=params.getPage().getParams().get("unrepay").toString();
			
			if(flag!=null &&"yes".equals(flag)&&(params.getPage().getStrStartTime()==null||"".equals(params.getPage().getStrStartTime()))){
				Date date=new Date();
		        Calendar calendar = Calendar.getInstance();
		        calendar.setTime(date);
		        calendar.add(Calendar.DAY_OF_YEAR, 1);
				params.getPage().setStrStartTime(FormatUtils.simpleFormat(calendar.getTime()));
			}
			if(flag!=null &&"no".equals(flag)&&(params.getPage().getStrEndTime()==null||"".equals(params.getPage().getStrEndTime()))){
				Date date=new Date();
		        Calendar calendar = Calendar.getInstance();
		        calendar.setTime(date);
		        calendar.add(Calendar.DAY_OF_YEAR, -1);
				params.getPage().setStrEndTime(FormatUtils.simpleFormat(calendar.getTime()));
			}
			params.getPage().setPageSize(Integer.MAX_VALUE);
			List<LoanRepayDetail> res = loanRepaymentService.queryLoanReoaymentByPage(params.getPage());
			for (LoanRepayDetail loanRepayDetail : res) {
				loanRepayDetail.setStrStatus(loanRepayDetail.getStatus().getKey());
				loanRepayDetail.setAmount(loanRepayDetail.getAmountInterest().add(loanRepayDetail.getAmountPrincipal()));
				loanRepayDetail.setStrRepay(loanRepayDetail.getIsRepay()?"已还":"未还");
			}
			String name="还款计划列表";
			ResponseEntity<byte[]> responseEntity=null;
			try {
				responseEntity = UploadExcelUtil.upload(request, headNames, fieldNames, res, name);
			} catch (IllegalArgumentException | IllegalAccessException | IOException e) {
				e.printStackTrace();
			} 
			return responseEntity;
			
		}catch(UException e){
			logger.info("还款计划导出失败");
			logger.info(e.fillInStackTrace());
		}
		return null;
	}


	@Override
	public ResponseEntity<byte[]> uploadClearLoanList(HttpServletRequest request, LoanRepaymentParams params) {
		List<String[]> headNames = new ArrayList<String[]>();
		headNames.add(new String[] { "标的名称", "借款人", "期数" ,"待还金额（元）","标的账户余额（元）","到期日","还款时间","还款状态"});
		List<String[]> fieldNames = new ArrayList<String[]>();
		fieldNames.add(new String[] {"title","userName", "currentPeriod", "amount","account_aviamount","dueDate","repayDate","strStatus"});
		try{
			if(params==null||params.getPage()==null)
				throw new UException(SystemEnum.PARAMS_ERROR,"参数错误");
			
		
			
			if(params.getPage().getStrStartTime()==null||"".equals(params.getPage().getStrStartTime()))
				params.getPage().setStrStartTime( DateUtil.getFirstDayOfMonth());
			
			if(params.getPage().getStrEndTime()==null||"".equals(params.getPage().getStrEndTime()))
				params.getPage().setStrEndTime(DateUtil.getLastDayOfMonth());
			
			params.getPage().setPageSize(Integer.MAX_VALUE);
			List<LoanRepayDetail> res = loanRepaymentService.queryLoanReoaymentByPage(params.getPage());
			for (LoanRepayDetail loanRepayDetail : res) {
				loanRepayDetail.setStrStatus(loanRepayDetail.getStatus().getKey());
				loanRepayDetail.setAmount(loanRepayDetail.getAmountInterest().add(loanRepayDetail.getAmountPrincipal()));
				loanRepayDetail.setStrRepay(loanRepayDetail.getIsRepay()?"已还":"未还");
			}
			String name="已还清列表";
			ResponseEntity<byte[]> responseEntity=null;
			try {
				responseEntity = UploadExcelUtil.upload(request, headNames, fieldNames, res, name);
			} catch (IllegalArgumentException | IllegalAccessException | IOException e) {
				e.printStackTrace();
			} 
			return responseEntity;
			
		}catch(UException e){
			logger.info("已还清列表导出失败");
			logger.info(e.fillInStackTrace());
		}
		return null;
	}


	@Override
	public ResponseEntity<byte[]> uploadPersonFundRecordList(HttpServletRequest request, FundRecordParams params) {
		List<String[]> headNames = new ArrayList<String[]>();
		headNames.add(new String[] { "订单号", "金额（元）", "类型" ,"状态	","时间","备注"});
		List<String[]> fieldNames = new ArrayList<String[]>();
		fieldNames.add(new String[] {"orderId","amount", "strType", "strStatus","strTimeRecorded","description"});
		try{
				if(params==null||params.getPage()==null)
					throw new UException(SystemEnum.PARAMS_ERROR,"参数错误");
				if(params.getPage().getStrStartTime()!=null&&!"".equals(params.getPage().getStrStartTime()))
					params.getPage().setStartTime(FormatUtils.millisFormat(params.getPage().getStrStartTime()+" 00:00:00"));
				
				if(params.getPage().getStrEndTime() !=null&&!"".equals(params.getPage().getStrEndTime()))
					params.getPage().setEndTime(FormatUtils.millisFormat(params.getPage().getStrEndTime()+" 23:59:59"));
				
				params.getPage().setPageSize(Integer.MAX_VALUE);
				List<FundRecord> fundRecords = fundRecordService.queryFundRecordByPage(params.getPage());
				for (FundRecord fundRecord : fundRecords) {
					fundRecord.setStrStatus(fundRecord.getStatus().getKey());
					fundRecord.setStrType(fundRecord.getType().getKey());
				}
				
			String name="个人资金记录明细";
			ResponseEntity<byte[]> responseEntity=null;
			try {
				responseEntity = UploadExcelUtil.upload(request, headNames, fieldNames, fundRecords, name);
			} catch (IllegalArgumentException | IllegalAccessException | IOException e) {
				e.printStackTrace();
			} 
			return responseEntity;
			
		}catch(UException e){
			logger.info("个人资金记录明细导出失败");
			logger.info(e.fillInStackTrace());
		}
		return null;
	}
	
}
