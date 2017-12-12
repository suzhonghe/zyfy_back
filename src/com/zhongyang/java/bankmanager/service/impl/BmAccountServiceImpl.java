package com.zhongyang.java.bankmanager.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.bankmanager.entity.BmAccount;
import com.zhongyang.java.bankmanager.service.BmAccountService;
import com.zhongyang.java.dao.BmAccountDao;
import com.zhongyang.java.zyfyback.pojo.User;

/**
 *@package com.zhongyang.java.zyfyfront.service.impl
 *@filename BmAccountServiceImpl.java
 *@date 20172017年6月22日下午2:11:33
 *@author suzh
 */
@Service
public class BmAccountServiceImpl implements BmAccountService {
	
	private static Logger logger=Logger.getLogger(BmAccountServiceImpl.class);

	@Autowired
	private BmAccountDao bmAccountDao;
		
	@Override
	public BmAccount queryBmAccountByParams(BmAccount params) {
		return bmAccountDao.selectBmAccountByParams(params);
	}


	@Override
	public BmAccount queryBmAccountByUser(User user) {
		return bmAccountDao.selectBmAccountByUser(user);
	}
	@Override
	public int modifyBmAccountByParams(BmAccount account)throws Exception {
		int res=0;
		try{
			res=bmAccountDao.updateBmAccountByParams(account);
		}catch(Exception e){
			logger.info("修改账户异常"+e.fillInStackTrace());
			Thread.sleep(100);
			logger.info("修改账户第二次操作");
			try{
				res=bmAccountDao.updateBmAccountByParams(account);
			}catch(Exception e1){
				throw new Exception();
			}
		}
		return res;
	}

}
