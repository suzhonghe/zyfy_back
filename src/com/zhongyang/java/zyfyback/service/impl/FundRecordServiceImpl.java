package com.zhongyang.java.zyfyback.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhongyang.java.dao.FundRecordDao;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.uitl.GetUUID;
import com.zhongyang.java.zyfyback.pojo.FundRecord;
import com.zhongyang.java.zyfyback.service.FundRecordService;

/**
 *@package com.zhongyang.java.zyfyfront.service.impl
 *@filename FundRecordServiceImpl.java
 *@date 2017年7月5日上午10:44:10
 *@author suzh
 */
@Service
public class FundRecordServiceImpl implements FundRecordService {
	
	private static Logger logger=Logger.getLogger(FundRecordServiceImpl.class);
	
	@Autowired
	private FundRecordDao fundRecordDao;

	@Override
	@Transactional
	public FundRecord addFundRecord(FundRecord fundRecord)throws Exception {
		fundRecord.setId(GetUUID.getUniqueKey());
		fundRecord.setTimeRecorded(new Date());
		logger.info("资金记录详情：" + fundRecord.toString());
		logger.info("资金记录入库,订单号：" + fundRecord.getOrderId());
		try{
			fundRecordDao.insertFundRecord(fundRecord);
		}catch(Exception e){
			logger.info("资金记录入库异常"+e.fillInStackTrace());
			Thread.sleep(100);
			logger.info("资金记录入库第二次操作");
			try{
				fundRecordDao.insertFundRecord(fundRecord);
			}catch(Exception e1){
				throw new Exception();
			}
		}
		return fundRecord;
	}

	@Override
	public void modifyFundRecordByParams(FundRecord fundRecord)throws Exception {
		logger.info("修改资金记录：" + fundRecord.getOrderId());
		try{
			fundRecordDao.updateFundRecordByParams(fundRecord);
		}catch(Exception e){
			logger.info("资金记录修改异常"+e.fillInStackTrace());
			Thread.sleep(100);
			logger.info("资金记录修改第二次操作");
			try{
				fundRecordDao.updateFundRecordByParams(fundRecord);
			}catch(Exception e1){
				throw new Exception();
			}
		}
			
	}

	@Override
	public FundRecord queryFundRecordByParams(FundRecord fundRecord) {
		return fundRecordDao.selectFundRecordByParams(fundRecord);
	}

	@Override
	@Transactional
	public int batchAddtFundRecord(List<FundRecord> list) {
		return fundRecordDao.batchInsertFundRecord(list);
	}

	@Override
	public List<FundRecord> queryFundRecordByPage(Page<FundRecord> page) {
		return fundRecordDao.selectFundRecordByPage(page);
	}

	@Override
	public List<FundRecord> queryPersonFundRecordByPage(Page<FundRecord> page) {
		return fundRecordDao.selectPersonFundRecordByPage(page);
	}

}
