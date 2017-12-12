package com.zhongyang.java.bankmanager.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhongyang.java.bankmanager.entity.UntredtedApplication;
import com.zhongyang.java.bankmanager.service.UntredtedApplicationService;
import com.zhongyang.java.dao.UntredtedApplicationDao;

/**
 *@package com.zhongyang.java.bankmanager.service.impl
 *@filename UntredtedApplicationServiceImpl.java
 *@date 2017年8月8日下午5:56:35
 *@author suzh
 */
@Service
public class UntredtedApplicationServiceImpl implements UntredtedApplicationService {
	
	private static Logger logger=Logger.getLogger(UntredtedApplicationServiceImpl.class);
	
	@Autowired
	private UntredtedApplicationDao untredtedApplicationDao;

	@Override
	@Transactional
	public int addUntredtedApplication(UntredtedApplication untredtedApplication) throws Exception{
		int res=0;
		try{
			res=untredtedApplicationDao.insertUntredtedApplication(untredtedApplication);
		}catch(Exception e){
			logger.info("添加转账申请异常"+e.fillInStackTrace());
			Thread.sleep(100);
			logger.info("添加转账申请第二次操作");
			try{
				res=untredtedApplicationDao.insertUntredtedApplication(untredtedApplication);
			}catch(Exception e1){
				throw new Exception();
			}
		}
		
		return res;
	}

	@Override
	public List<UntredtedApplication> queryUntredtedApplicationByParams(UntredtedApplication untredtedApplication) {
		return untredtedApplicationDao.selectUntredtedApplicationByParams(untredtedApplication);
	}
	
	@Override
	public int modifyUntredtedApplicationByParams(UntredtedApplication untredtedApplication)throws Exception {
		int res=0;
		try{
			res=untredtedApplicationDao.updateUntredtedApplicationByParams(untredtedApplication);
		}catch(Exception e){
			logger.info("修改转账申请异常"+e.fillInStackTrace());
			Thread.sleep(100);
			logger.info("修改转账申请第二次操作");
			try{
				res=untredtedApplicationDao.updateUntredtedApplicationByParams(untredtedApplication);
			}catch(Exception e1){
				throw new Exception();
			}
		}
		
		return res;
	}

}
