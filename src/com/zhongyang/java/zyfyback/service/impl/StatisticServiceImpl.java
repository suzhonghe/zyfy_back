package com.zhongyang.java.zyfyback.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.StatisticDao;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyback.returndata.InviteStatisticVo;
import com.zhongyang.java.zyfyback.returndata.MarkStatisticVo;
import com.zhongyang.java.zyfyback.service.StatisticService;
@Service
public class StatisticServiceImpl implements StatisticService {

	@Autowired
	private StatisticDao statisticDao;
	@Override
	public List<InviteStatisticVo> getAllInviteUser(Page<InviteStatisticVo> page) {
		
		return statisticDao.getAllInviteUser(page);
	}
	@Override
	public List<MarkStatisticVo> getMarkStatisticByOrg(Page<MarkStatisticVo> page) {
		
		return statisticDao.getMarkStatisticByOrg(page);
	}
	@Override
	public List<MarkStatisticVo> getEmployStatistic(Map<String,Object> params) {
		return statisticDao.getEmployStatistic(params);
	}

}
