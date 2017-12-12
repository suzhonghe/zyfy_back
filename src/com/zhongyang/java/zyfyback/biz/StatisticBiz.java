package com.zhongyang.java.zyfyback.biz;


import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyback.params.StaticParams;
import com.zhongyang.java.zyfyback.returndata.InviteStatisticVo;
import com.zhongyang.java.zyfyback.returndata.MarkStatisticVo;
import com.zhongyang.java.zyfyback.returndata.ReturnManagerData;
import com.zhongyang.java.zyfyback.returndata.StaticReturn;

public interface StatisticBiz {

	/**
	 * 根据分公司查询获得员工邀请人数及邀请的人年化投资金额数据
	 * @return
	 */
	public StaticReturn getAllInviteUser(StaticParams staticParams);
	/**
	 * 公司员工邀请数据导出
	 * @param request
	 * @param page
	 * @return
	 */
	public ResponseEntity<byte[]> exportInviteUserData(HttpServletRequest request,Page<InviteStatisticVo> page);
	
	/**
	 * 根据机构统计投资金额
	 * @param page
	 * @return
	 */
	public StaticReturn getMarkStatisticByOrg(HttpServletRequest request,StaticParams staticParams);
	
	/**
	 * 公司员工邀请数据导出
	 * @param request
	 * @param page
	 * @return
	 */
	public ResponseEntity<byte[]> exportMarkStatisticData(HttpServletRequest request,Page<MarkStatisticVo> page);
	
	public ReturnManagerData queryManagerData(String date);
}
