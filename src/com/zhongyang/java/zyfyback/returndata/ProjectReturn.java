package com.zhongyang.java.zyfyback.returndata;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.uitl.Message;
import com.zhongyang.java.zyfyback.pojo.CorporationUser;
import com.zhongyang.java.zyfyback.pojo.Loan;
import com.zhongyang.java.zyfyback.pojo.Product;
import com.zhongyang.java.zyfyback.pojo.Project;

public class ProjectReturn implements Serializable{
	
	private Message message;
	
	private Project project;
	
	private String legalPersonPhotoUrl;//法人身份证
	
	private String enterpriseInfoPhotoUrl;//企业信息
	
	private String assetsPhotoUrl;//合同文件
	
	private String contractPhotoUrl;//资产信息
	
	private String othersPhotoUrl;//其他资料
	
	private List<Product>products=new ArrayList<Product>();
	
	private List<CorporationUser>corporationUsers=new ArrayList<CorporationUser>();
	
	private BigDecimal totalAmount;
	
	private List<Loan> loans;
	
	private Page<ProjectDea> page;
	
	public Page<ProjectDea> getPage() {
		return page;
	}

	public void setPage(Page<ProjectDea> page) {
		this.page = page;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

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

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<CorporationUser> getCorporationUsers() {
		return corporationUsers;
	}

	public void setCorporationUsers(List<CorporationUser> corporationUsers) {
		this.corporationUsers = corporationUsers;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<Loan> getLoans() {
		return loans;
	}

	public void setLoans(List<Loan> loans) {
		this.loans = loans;
	}
}
