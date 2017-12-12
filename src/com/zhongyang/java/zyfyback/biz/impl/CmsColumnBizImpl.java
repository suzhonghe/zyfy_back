package com.zhongyang.java.zyfyback.biz.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.uitl.FormatUtils;
import com.zhongyang.java.system.uitl.Message;
import com.zhongyang.java.zyfyback.biz.CmsColumnBiz;
import com.zhongyang.java.zyfyback.pojo.CmsColumn;
import com.zhongyang.java.zyfyback.returndata.CmsColumnVO;
import com.zhongyang.java.zyfyback.returndata.CmsReturn;
import com.zhongyang.java.zyfyback.service.CmsColumnService;

@Service
public class CmsColumnBizImpl implements CmsColumnBiz {

	@Autowired
	private CmsColumnService cmsColumnService;

	@Override
	@Transactional
	public CmsReturn addCmsColumn(CmsColumn cmsColumn) {
		CmsReturn cr = new CmsReturn();

		cmsColumn.setId(UUID.randomUUID().toString().toUpperCase());
		cmsColumn.setTime(new Date());
		cmsColumnService.addCmsColumn(cmsColumn);

		cr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "添加栏目成功"));

		return cr;
	}

	@Override
	@Transactional
	public CmsReturn modifyByParams(CmsColumn cmsColumn) {
		CmsReturn cr = new CmsReturn();
		try {
			if (cmsColumn==null||cmsColumn.getId() == null)
				throw new UException(SystemEnum.PARAMS_ERROR, "未获得需要修改的栏目ID");

			int count = cmsColumnService.modifyByParams(cmsColumn);
			if (count > 0)
				cr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "修改栏目成功"));

		} catch (UException e) {
			e.printStackTrace();
			cr.setMessage(new Message(e.getCode().value(), e.getMessage()));
		}
		return cr;
	}

	@Override
	@Transactional
	public CmsReturn deleteByParams(CmsColumn cmsColumn) {
		CmsReturn cr = new CmsReturn();
		try {
			if (cmsColumn==null||cmsColumn.getId()== null)
				throw new UException(SystemEnum.PARAMS_ERROR, "参数接收失败");

			int count = cmsColumnService.deleteByParams(cmsColumn);
			if (count > 0)
				cr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "删除栏目成功"));
		} catch (UException e) {
			e.printStackTrace();
			cr.setMessage(new Message(e.getCode().value(), e.getMessage()));
		}
		return cr;
	}

	@Override
	public CmsReturn queryByParams(CmsColumn cmsColumn) {
		CmsReturn cr = new CmsReturn();
		List<CmsColumnVO> list = new ArrayList<CmsColumnVO>();

		List<CmsColumn> results = cmsColumnService.queryByParams(cmsColumn);
		for (CmsColumn ccl : results) {
			CmsColumnVO ccv = new CmsColumnVO();
			ccv.setId(ccl.getId());
			ccv.setName(ccl.getName());
			ccv.setTime(FormatUtils.simpleFormat(ccl.getTime()));
			list.add(ccv);
		}
		cr.setColumnvos(list);
		cr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "删除栏目成功"));

		return cr;
	}

}
