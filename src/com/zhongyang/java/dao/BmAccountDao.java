package com.zhongyang.java.dao;

import com.zhongyang.java.bankmanager.entity.BmAccount;
import com.zhongyang.java.zyfyback.pojo.User;

/**
 *@package com.zhongyang.java.dao
 *@filename BmAccountDao.java
 *@date 20172017年6月22日上午11:29:20
 *@author suzh
 */
public interface BmAccountDao {
	
	BmAccount selectBmAccountByParams(BmAccount params);
	
	BmAccount selectBmAccountByUser(User user);
	
	int updateBmAccountByParams(BmAccount account);
}
