package com.zhongyang.java.zyfyback.returndata;

import java.io.Serializable;
import java.util.List;

import com.zhongyang.java.system.uitl.Message;
import com.zhongyang.java.zyfyback.pojo.Organization;

/**
 *@package com.zhongyang.java.zyfyback.returndata
 *@filename ReturnOrganization.java
 *@date 20172017年6月22日下午3:50:28
 *@author suzh
 */
public class ReturnOrganization implements Serializable{
	
	private Message message;
	
	private List<Organization> list;
	
	private Organization organization;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public List<Organization> getList() {
		return list;
	}

	public void setList(List<Organization> list) {
		this.list = list;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	
}
