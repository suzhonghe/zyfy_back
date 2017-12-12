package com.zhongyang.java.zyfyback.returndata;

import java.io.Serializable;

import com.zhongyang.java.system.uitl.Message;

/**
 *@package com.zhongyang.java.zyfyback.returndata
 *@filename UserFundReturn.java
 *@date 2017年7月21日上午9:00:07
 *@author suzh
 */
public class UserFundReturn implements Serializable{
	
	private Message message;
	
	private UserFundDetail userFundDetail;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public UserFundDetail getUserFundDetail() {
		return userFundDetail;
	}

	public void setUserFundDetail(UserFundDetail userFundDetail) {
		this.userFundDetail = userFundDetail;
	}
	
}
