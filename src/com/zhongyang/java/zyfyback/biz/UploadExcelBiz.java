package com.zhongyang.java.zyfyback.biz;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.zhongyang.java.zyfyback.params.FundRecordParams;
import com.zhongyang.java.zyfyback.params.LoanRepaymentParams;
import com.zhongyang.java.zyfyback.params.PlatFormParams;
import com.zhongyang.java.zyfyback.params.UploadParams;

/**
 * 
* @Title: UploadExcel.java 
* @Package com.zhongyang.java.biz 
* @Description:导出excel
* @author 苏忠贺   
* @date 2016年1月25日 上午10:29:39 
* @version V1.0
 */
public interface UploadExcelBiz {
	
	public ResponseEntity<byte[]> uploadPublishedLoans(HttpServletRequest request,UploadParams params);
	
	public ResponseEntity<byte[]> uploadPlatFormRecord(HttpServletRequest request,PlatFormParams params);
	
	public ResponseEntity<byte[]> uploadLoanRepaymentList(HttpServletRequest request,LoanRepaymentParams params);

	public ResponseEntity<byte[]> uploadClearLoanList(HttpServletRequest request,LoanRepaymentParams params);

	public ResponseEntity<byte[]> uploadPersonFundRecordList(HttpServletRequest request,FundRecordParams params);
}
