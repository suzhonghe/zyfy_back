package com.zhongyang.java.zyfyback.returndata;

import java.io.Serializable;
import java.util.List;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.uitl.Message;
import com.zhongyang.java.zyfyback.pojo.User;

/**
 *@package com.zhongyang.java.zyfyback.returndata
 *@filename UserReturn.java
 *@date 20172017年6月28日上午10:14:13
 *@author suzh
 */
public class UserReturn implements Serializable{

	private User user;
	
	private Message message;
	
	private Page<User>page;
	
	private UserDetail userDetail;
	
	private List<String>mobiles;
			
	public List<String> getMobiles() {
		return mobiles;
	}

	public void setMobiles(List<String> mobiles) {
		this.mobiles = mobiles;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
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

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}
	
}
