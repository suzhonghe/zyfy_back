package com.zhongyang.java.zyfyback.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class User implements Serializable{
	
	private String strmobile;
	
	private String strid;
	
	private int sex;
	
	
    public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getStrmobile() {
		return strmobile;
	}

	public void setStrmobile(String strmobile) {
		this.strmobile = strmobile;
	}

	public String getStrid() {
		return strid;
	}

	public void setStrid(String strid) {
		this.strid = strid;
	}

	private String id;

    private Boolean enabled;

    private Date lastLoginDate;
    
    private String strLastLoginDate;
    
    private Date lastModifyDate;
    
    private String loginName;
    
    private String mobile;
    
    private String passphrase;
    
    private Date registerDate;
    
    private String strRegisterDate;
    
    private String referralId;
    
    private String salt;
    
    private Integer userType;
    
    private Integer custType;
    
    private Date allowTime; //允许登录时间
    
    private String userName;
    
    private String idCode;//身份证号
    
    private String refMobile;//推荐人手机号
    
    private Boolean isLock;//锁定状态，true锁定，false未锁定
    
    private String oriReferralId;//原推荐人ID
    
    private Date birthDate;
    
    private BigDecimal repayAmount;//回款金额
                
	public BigDecimal getRepayAmount() {
		return repayAmount;
	}

	public void setRepayAmount(BigDecimal repayAmount) {
		this.repayAmount = repayAmount;
	}

	public String getStrLastLoginDate() {
		return strLastLoginDate;
	}

	public void setStrLastLoginDate(String strLastLoginDate) {
		this.strLastLoginDate = strLastLoginDate;
	}

	public String getStrRegisterDate() {
		return strRegisterDate;
	}

	public void setStrRegisterDate(String strRegisterDate) {
		this.strRegisterDate = strRegisterDate;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Integer getCustType() {
		return custType;
	}

	public void setCustType(Integer custType) {
		this.custType = custType;
	}

	public String getOriReferralId() {
		return oriReferralId;
	}

	public void setOriReferralId(String oriReferralId) {
		this.oriReferralId = oriReferralId;
	}

	public Boolean getIsLock() {
		return isLock;
	}

	public void setIsLock(Boolean isLock) {
		this.isLock = isLock;
	}

	public String getRefMobile() {
		return refMobile;
	}

	public void setRefMobile(String refMobile) {
		this.refMobile = refMobile;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Date getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassphrase() {
		return passphrase;
	}

	public void setPassphrase(String passphrase) {
		this.passphrase = passphrase;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerdate) {
		this.registerDate = registerdate;
	}

	public String getReferralId() {
		return referralId;
	}

	public void setReferralId(String referralId) {
		this.referralId = referralId;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Date getAllowTime() {
		return allowTime;
	}

	public void setAllowTime(Date allowTime) {
		this.allowTime = allowTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIdCode() {
		return idCode;
	}

	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", enabled=" + enabled + ", lastLoginDate=" + lastLoginDate + ", lastModifyDate="
				+ lastModifyDate + ", loginName=" + loginName + ", mobile=" + mobile + ", passphrase=" + passphrase
				+ ", registerDate=" + registerDate + ", referralId=" + referralId + ", salt=" + salt + ", userType="
				+ userType + ", allowTime=" + allowTime + ", userName=" + userName + ", idCode=" + idCode + "]";
	}

}