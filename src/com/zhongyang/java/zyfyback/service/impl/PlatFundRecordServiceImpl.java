package com.zhongyang.java.zyfyback.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhongyang.java.dao.PlatFundRecordDao;
import com.zhongyang.java.system.enumtype.FundRecordStatus;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.uitl.GetUUID;
import com.zhongyang.java.zyfyback.pojo.PlatFundRecord;
import com.zhongyang.java.zyfyback.service.PlatFundRecordService;

/**
 *@package com.zhongyang.java.service.impl
 *@filename PlatFundRecordServiceImpl.java
 *@date 2017年7月7日下午2:45:03
 *@author suzh
 */
@Service
public class PlatFundRecordServiceImpl implements PlatFundRecordService {

	private static Logger logger=Logger.getLogger(PlatFundRecordServiceImpl.class);
	
	@Autowired
	private PlatFundRecordDao platFundRecordDao;
	
	@Override
	@Transactional
	public PlatFundRecord addPlatFundRecord(PlatFundRecord record) throws Exception {
		record.setId(GetUUID.getUniqueKey());
		record.setStatus(FundRecordStatus.PROCESSING);
		record.setTimeRecorded(new Date());
		logger.info("=================添加平台资金记录入库====================");
		try{
			platFundRecordDao.insertPlatFundRecord(record);
		}catch(Exception e){
			logger.info("添加平台资金记录异常"+e.fillInStackTrace());
			Thread.sleep(100);
			logger.info("添加平台资金记录第二次操作");
			try{
				platFundRecordDao.insertPlatFundRecord(record);
			}catch(Exception e1){
				throw new Exception();
			}
		}
			
		return record;
	}

	
	@Override
	@Transactional
	public void modifyPlatFundRecord(PlatFundRecord record) throws Exception {
		logger.info("=================修改平台资金记录入库====================");
		try{
			platFundRecordDao.updatePlatFundRecord(record);
		}catch(Exception e){
			logger.info("修改平台资金记录异常"+e.fillInStackTrace());
			Thread.sleep(100);
			logger.info("修改平台资金记录第二次操作");
			try{
				platFundRecordDao.updatePlatFundRecord(record);
			}catch(Exception e1){
				throw new Exception();
			}
		}
			
	}

	
	@Override
	public List<PlatFundRecord> queryByPage(Page<PlatFundRecord> page) {
		return platFundRecordDao.selectByPage(page);
	}

	@Override
	public PlatFundRecord queyByParams(PlatFundRecord record) {
		return platFundRecordDao.selectByParams(record);
	}


	@Override
	@Transactional
	public int batchAddRecords(List<PlatFundRecord> list)throws Exception {
		int res=0;
		try{
			res=platFundRecordDao.batchInsertRecords(list);
		}catch(Exception e){
			logger.info("批量修改平台资金记录异常"+e.fillInStackTrace());
			Thread.sleep(100);
			logger.info("批量修改平台资金记录第二次操作");
			try{
				res=platFundRecordDao.batchInsertRecords(list);
			}catch(Exception e1){
				throw new Exception();
			}
		}
		return res;
	}

}
