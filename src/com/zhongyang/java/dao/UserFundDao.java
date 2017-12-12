package com.zhongyang.java.dao;

import java.math.BigDecimal;
import java.util.List;

import com.zhongyang.java.zyfyback.pojo.Invest;
import com.zhongyang.java.zyfyback.pojo.UserFund;
/**
 * 
* @Title: UserFundDao.java 
* @Package com.zhongyang.java.dao 
* @Description:用户资金dao
* @author 苏忠贺   
* @date 2017年6月2日 上午9:11:49 
* @version V1.0
 */
public interface UserFundDao {
    
	/*
	 * 修改用户资金信息
	 */
	public int updateUserFundByParams(UserFund userFund);
	
	/*
	 * 查询用户资金信息
	 */
	public UserFund selectUserFundByParams(UserFund UserFund);
	
	public List<UserFund>selectByInvest(Invest invest);
	
	public int batchUpdateUserFunds(List<UserFund>list);
	
	public BigDecimal selectDuInAmount(String userId);
	
	public BigDecimal selectDuOutAmount(String userId);
} 
