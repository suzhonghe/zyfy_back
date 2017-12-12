package com.zhongyang.java.zyfyback.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhongyang.java.system.authority.Authorities;
import com.zhongyang.java.system.authority.FireAuthority;
import com.zhongyang.java.zyfyback.biz.UploadExcelBiz;
import com.zhongyang.java.zyfyback.params.FundRecordParams;
import com.zhongyang.java.zyfyback.params.LoanRepaymentParams;
import com.zhongyang.java.zyfyback.params.PlatFormParams;
import com.zhongyang.java.zyfyback.params.UploadParams;
/**
 * 
 *@package com.zhongyang.java.zyfyback.controller
 *@filename UploadExcelController.java
 *@date 2017年8月24日上午10:51:35
 *@author suzh
 */
@Controller
public class UploadExcelController extends BaseController{
	
	@Autowired
	private UploadExcelBiz uploadExcelBiz;
	
	@FireAuthority(authorities=Authorities.BIDLISTUPLOAD)
	@RequestMapping(value="/back/upload/uploadPublishedLoans")
	public ResponseEntity<byte[]> uploadPublishedLoans(HttpServletRequest request,UploadParams params){
 		return uploadExcelBiz.uploadPublishedLoans(request,params);
	}
	
	@FireAuthority(authorities=Authorities.PLATCAPITALRECORDS)
	@RequestMapping(value="/back/upload/uploadPlatFormRecord")
	public ResponseEntity<byte[]>  uploadPlatFormRecord(HttpServletRequest request,PlatFormParams params){
		return uploadExcelBiz.uploadPlatFormRecord(request,params);
	}
	
	@FireAuthority(authorities=Authorities.REPAYLIST)
	@RequestMapping(value="/back/upload/uploadLoanRepaymentList")
	public ResponseEntity<byte[]> uploadLoanRepaymentList(HttpServletRequest request,LoanRepaymentParams params){
		return uploadExcelBiz.uploadLoanRepaymentList(request,params);
	}
	
	@FireAuthority(authorities=Authorities.REPAYLIST)
	@RequestMapping(value="/back/upload/uploadClearLoanList")
	public ResponseEntity<byte[]> uploadClearLoanList(HttpServletRequest request,LoanRepaymentParams params){
		return uploadExcelBiz.uploadClearLoanList(request,params);
	}
	
	@FireAuthority(authorities=Authorities.FUNDRECORDLIST)
	@RequestMapping(value="/back/upload/uploadPersonFundRecordList")
	public ResponseEntity<byte[]> uploadPersonFundRecordList(HttpServletRequest request,FundRecordParams params){
		return uploadExcelBiz.uploadPersonFundRecordList(request,params);
	}
}
