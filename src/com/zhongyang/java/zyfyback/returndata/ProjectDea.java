package com.zhongyang.java.zyfyback.returndata;

import com.zhongyang.java.zyfyback.pojo.Project;

/**
 *@package com.zhongyang.java.zyfyback.returndata
 *@filename ProjectDea.java
 *@date 2017年7月27日上午11:31:32
 *@author suzh
 */
public class ProjectDea extends Project{
	
	private String legalPersonPhotoUrl;//法人身份证
	
	private String enterpriseInfoPhotoUrl;//企业信息
	
	private String assetsPhotoUrl;//合同文件
	
	private String contractPhotoUrl;//资产信息
	
	private String othersPhotoUrl;//其他资料

	public String getLegalPersonPhotoUrl() {
		return legalPersonPhotoUrl;
	}

	public void setLegalPersonPhotoUrl(String legalPersonPhotoUrl) {
		this.legalPersonPhotoUrl = legalPersonPhotoUrl;
	}

	public String getEnterpriseInfoPhotoUrl() {
		return enterpriseInfoPhotoUrl;
	}

	public void setEnterpriseInfoPhotoUrl(String enterpriseInfoPhotoUrl) {
		this.enterpriseInfoPhotoUrl = enterpriseInfoPhotoUrl;
	}

	public String getAssetsPhotoUrl() {
		return assetsPhotoUrl;
	}

	public void setAssetsPhotoUrl(String assetsPhotoUrl) {
		this.assetsPhotoUrl = assetsPhotoUrl;
	}

	public String getContractPhotoUrl() {
		return contractPhotoUrl;
	}

	public void setContractPhotoUrl(String contractPhotoUrl) {
		this.contractPhotoUrl = contractPhotoUrl;
	}

	public String getOthersPhotoUrl() {
		return othersPhotoUrl;
	}

	public void setOthersPhotoUrl(String othersPhotoUrl) {
		this.othersPhotoUrl = othersPhotoUrl;
	}
	
}
