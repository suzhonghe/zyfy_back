package com.zhongyang.java.zyfyback.biz.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.uitl.Message;
import com.zhongyang.java.zyfyback.biz.CorporationUserBiz;
import com.zhongyang.java.zyfyback.pojo.CorporationUser;
import com.zhongyang.java.zyfyback.returndata.CorporationReturn;
import com.zhongyang.java.zyfyback.service.CorporationUserService;
import com.zhongyang.java.zyfyback.vo.CorporationUserVo;

@Service
public class CorporationUserBizImpl implements CorporationUserBiz {

	@Autowired
	private CorporationUserService corporationUserService;

	@Override
	public CorporationReturn queryAllCorporationUser(CorporationUser corporationUser) {
		CorporationReturn cr = new CorporationReturn();

		List<CorporationUserVo> corporationUserVo = new ArrayList<CorporationUserVo>();
		List<CorporationUser> CorporationUsers = corporationUserService.queryAllCorporationUser(corporationUser);
		for (CorporationUser cu : CorporationUsers) {
			CorporationUserVo cuv = new CorporationUserVo();
			cuv.setUserId(cu.getUserId());
			cuv.setName(cu.getName());
			cuv.setShortname(cu.getShortname());
			cuv.setType(cu.getType());
			corporationUserVo.add(cuv);
		}
		cr.setCorUsers(corporationUserVo);
		cr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "查询成功"));

		return cr;
	}

}
