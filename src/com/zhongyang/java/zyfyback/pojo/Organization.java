package com.zhongyang.java.zyfyback.pojo;

import java.util.Date;
import java.util.List;

/**
 * 
* @Title: Organization.java 
* @Package com.zhongyang.java.pojo 
* @Description:机构实体
* @author 苏忠贺   
* @date 2016年3月9日 下午4:16:13 
* @version V1.0
 */
public class Organization {
	
	private String id;
	
	private String orgName;
	
	private String description;
	
	private Date createTime;
	
	private Integer level;
	
	private String parantOrgId;
	
	private String address;
	
	private String telphone;
	
	private boolean del;
	
	private List<Organization>children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getParantOrgId() {
		return parantOrgId;
	}

	public void setParantOrgId(String parantOrgId) {
		this.parantOrgId = parantOrgId;
	}
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public boolean isDel() {
		return del;
	}

	public void setDel(boolean del) {
		this.del = del;
	}

	public List<Organization> getChildren() {
		return children;
	}

	public void setChildren(List<Organization> children) {
		this.children = children;
	}
}
