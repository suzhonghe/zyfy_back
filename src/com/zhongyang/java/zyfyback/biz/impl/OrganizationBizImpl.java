package com.zhongyang.java.zyfyback.biz.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.uitl.GetUUID;
import com.zhongyang.java.system.uitl.Message;
import com.zhongyang.java.zyfyback.biz.OrganizationBiz;
import com.zhongyang.java.zyfyback.pojo.Organization;
import com.zhongyang.java.zyfyback.returndata.ReturnOrganization;
import com.zhongyang.java.zyfyback.service.OrganizationService;

@Service
public class OrganizationBizImpl implements OrganizationBiz {

	private static Logger logger = Logger.getLogger(OrganizationBizImpl.class);

	@Autowired
	private OrganizationService organizationService;

	@Override
	public ReturnOrganization addOrganization(Organization organization) {
		ReturnOrganization ro = new ReturnOrganization();
		try {
			organization.setId(GetUUID.getUniqueKey());
			organization.setCreateTime(new Date());
			organization.setLevel(organization.getLevel() + 1);
			organization.setDel(false);
			int count = organizationService.addOrganization(organization);
			if (count > 0)
				ro.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "添加成功"));
			else
				ro.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(), "添加失败"));

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("--------添加机构异常---------");
			logger.info(e, e.fillInStackTrace());
		}
		return ro;
	}

	@Override
	public ReturnOrganization modifyOrganization(Organization organization) {
		ReturnOrganization ro = new ReturnOrganization();
		try {
			if (organization == null || organization.getId() == null)
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "参数接收异常");

			organizationService.modifyOrganization(organization);

			ro.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "修改成功"));
		} catch (UException e) {
			logger.info("修改机构异常");
			logger.info(e.fillInStackTrace());
			ro.setMessage(new Message(e.getCode().value(), e.getMessage()));
		}
		return ro;
	}

	@Override
	public ReturnOrganization queryOrganizationByParams(Organization organization) {
		ReturnOrganization ro = new ReturnOrganization();

		ro.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "操作成功"));
		Organization res = organizationService.queryOrganizationByParams(organization);
		List<Organization> data = new ArrayList<Organization>();
		data.add(res);
		ro.setList(data);
		return ro;

	}

	@Override
	public ReturnOrganization queryAllOrganizations() {
		ReturnOrganization ro=new ReturnOrganization();
		Organization organization = new Organization();
		organization.setLevel(0);
		List<Organization> result = organizationService.queryAllOrganizations(organization);
		ro.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"查询成功"));
		ro.setOrganization(result.get(0));
		return ro;

	}
}
