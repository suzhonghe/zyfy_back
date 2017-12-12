package com.zhongyang.java.zyfyback.params;

import java.io.Serializable;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyback.pojo.User;

/**
 *@package com.zhongyang.java.zyfyback.params
 *@filename UserParams.java
 *@date 20172017年6月28日上午10:16:00
 *@author suzh
 */
public class UserParams implements Serializable{
	
	private User user;
	
	private Page<User>page;
	
	private String userMobile;
	
	private String refMobile;
	
	private String oriMobile;
	
	private String nowMobile;
	
	private String startTime;
	
	private String endTime;	
	
	private String sendTime;
	
	private String message;
	
	private String mobiles;
	
	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMobiles() {
		return mobiles;
	}

	public void setMobiles(String mobiles) {
		this.mobiles = mobiles;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getOriMobile() {
		return oriMobile;
	}

	public void setOriMobile(String oriMobile) {
		this.oriMobile = oriMobile;
	}

	public String getNowMobile() {
		return nowMobile;
	}

	public void setNowMobile(String nowMobile) {
		this.nowMobile = nowMobile;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getRefMobile() {
		return refMobile;
	}

	public void setRefMobile(String refMobile) {
		this.refMobile = refMobile;
	}

	public Page<User> getPage() {
		return page;
	}

	public void setPage(Page<User> page) {
		this.page = page;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
