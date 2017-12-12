package com.zhongyang.java.zyfyback.returndata;

import java.io.Serializable;

/**
 *@package com.zhongyang.java.zyfyback.returndata
 *@filename UserDetail.java
 *@date 2017年7月20日下午3:59:54
 *@author suzh
 */
public class UserDetail implements Serializable{
	
	private String mobile;
	
	private String userName;
	
	private String loginName;
	
	private String refferral;//推荐人
	
	private String registerDate;
	
	private String idCode;
	
	private String cardNo;
	
	private String lastLoginDate;
	
	private String userType;//客户类型

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getRefferral() {
		return refferral;
	}

	public void setRefferral(String refferral) {
		this.refferral = refferral;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	public String getIdCode() {
		return idCode;
	}

	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
}
