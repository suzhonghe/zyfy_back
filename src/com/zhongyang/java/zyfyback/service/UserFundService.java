package com.zhongyang.java.zyfyback.service;

import java.math.BigDecimal;
import java.util.List;

import com.zhongyang.java.zyfyback.pojo.Invest;
import com.zhongyang.java.zyfyback.pojo.UserFund;
/**
 * 
* @Title: UserFundService.java 
* @Package com.zhongyang.java.service 
* @Description: UserFundService 
* @author 苏忠贺   
* @date 2017年6月2日 下午1:44:11 
* @version V1.0
 */
public interface UserFundService {
    
	/*
	 * 修改用户资金信息
	 */
	public void modifyUserFundByParams(UserFund userFund)throws Exception;
	
	/*
	 * 查询用户资金信息
	 */
	public UserFund queryUserFundByParams(UserFund UserFund);
	
	public List<UserFund>queryByInvest(Invest invest);
	
	public int batchModifyUserFunds(List<UserFund>list)throws Exception;
	
	public BigDecimal queryDuInAmount(String userId);
	
	public BigDecimal queryDuOutAmount(String userId);
} 
