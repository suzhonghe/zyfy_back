package com.zhongyang.java.zyfyback.params;

import java.io.Serializable;

import com.zhongyang.java.system.enumtype.LoanStatus;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyback.pojo.Project;
import com.zhongyang.java.zyfyback.returndata.ProjectDea;

public class ProjectParams implements Serializable{
	
	private Project project;
	
	private String userId;
	
	private String[] legalPersonPhoto;
	
	private String[]enterpriseInfoPhoto;
	
	private String[]contractPhoto;
	
	private String[]assetsPhoto;
	
	private String[]othersPhoto;
	
	private LoanStatus status;
	
	private Page<ProjectDea>page;

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String[] getLegalPersonPhoto() {
		return legalPersonPhoto;
	}

	public void setLegalPersonPhoto(String[] legalPersonPhoto) {
		this.legalPersonPhoto = legalPersonPhoto;
	}

	public String[] getEnterpriseInfoPhoto() {
		return enterpriseInfoPhoto;
	}

	public void setEnterpriseInfoPhoto(String[] enterpriseInfoPhoto) {
		this.enterpriseInfoPhoto = enterpriseInfoPhoto;
	}

	public String[] getContractPhoto() {
		return contractPhoto;
	}

	public void setContractPhoto(String[] contractPhoto) {
		this.contractPhoto = contractPhoto;
	}

	public String[] getAssetsPhoto() {
		return assetsPhoto;
	}

	public void setAssetsPhoto(String[] assetsPhoto) {
		this.assetsPhoto = assetsPhoto;
	}

	public String[] getOthersPhoto() {
		return othersPhoto;
	}

	public void setOthersPhoto(String[] othersPhoto) {
		this.othersPhoto = othersPhoto;
	}

	public LoanStatus getStatus() {
		return status;
	}

	public void setStatus(LoanStatus status) {
		this.status = status;
	}

	public Page<ProjectDea> getPage() {
		return page;
	}

	public void setPage(Page<ProjectDea> page) {
		this.page = page;
	}
	
}
