package com.zhongyang.java.bankmanager.service;

import com.zhongyang.java.bankmanager.entity.BmAccount;
import com.zhongyang.java.zyfyback.pojo.User;

/**
 *@package com.zhongyang.java.zyfyfront.service
 *@filename BmAccountService.java
 *@date 20172017年6月22日下午2:10:40
 *@author suzh
 */
public interface BmAccountService {
	
	public BmAccount queryBmAccountByParams(BmAccount params);
	
	public BmAccount queryBmAccountByUser(User user);
	
	public int modifyBmAccountByParams(BmAccount account)throws Exception;
}
