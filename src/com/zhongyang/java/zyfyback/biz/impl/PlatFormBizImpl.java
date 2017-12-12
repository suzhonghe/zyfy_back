package com.zhongyang.java.zyfyback.biz.impl;

import java.security.GeneralSecurityException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.uitl.DESTextCipher;
import com.zhongyang.java.system.uitl.FormatUtils;
import com.zhongyang.java.system.uitl.Message;
import com.zhongyang.java.zyfyback.biz.PlatFormBiz;
import com.zhongyang.java.zyfyback.params.PlatFormParams;
import com.zhongyang.java.zyfyback.pojo.PlatFundRecord;
import com.zhongyang.java.zyfyback.pojo.User;
import com.zhongyang.java.zyfyback.returndata.PlatFormReturn;
import com.zhongyang.java.zyfyback.service.PlatFundRecordService;
import com.zhongyang.java.zyfyback.service.UserService;

/**
 *@package com.zhongyang.java.zyfyback.biz.impl
 *@filename PlatFormBizImpl.java
 *@date 2017年7月24日上午9:03:00
 *@author suzh
 */
@Service
public class PlatFormBizImpl implements PlatFormBiz {
	
	private static Logger logger=Logger.getLogger(PlatFormBizImpl.class);

	@Autowired
	private PlatFundRecordService platFundRecordService;
	
	@Autowired
	private UserService userService;

	@Override
	public PlatFormReturn searchPlatFormRecord(PlatFormParams params) {
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
			page.setResults(res);
			pfr.setPage(page);
			pfr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"平台资金记录查询成功"));
			
		}catch(UException e){
			logger.info("平台资金记录查询异常");
			logger.info(e.fillInStackTrace());
			pfr.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return pfr;
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
}
