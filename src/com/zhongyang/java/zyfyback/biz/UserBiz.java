package com.zhongyang.java.zyfyback.biz;

import javax.servlet.http.HttpServletRequest;

import com.zhongyang.java.zyfyback.params.UserParams;
import com.zhongyang.java.zyfyback.pojo.User;
import com.zhongyang.java.zyfyback.returndata.UserReturn;

/**
 *@package com.zhongyang.java.zyfyback.biz
 *@filename UserBiz.java
 *@date 20172017年6月28日上午10:13:31
 *@author suzh
 */
public interface UserBiz {
	
	public UserReturn searchUserByParams(UserParams params);
	
	public UserReturn searchUserList(UserParams params);
	
	public UserReturn modifyUser(UserParams params);
	
	public UserReturn modifyReferral(String userMobile,String refMobile);
	
	public UserReturn batchModifyReferral(String oriMobile,String nowMobile);
	
	public UserReturn searchUserDetail(User user);
	
	public UserReturn getUserMobiles(HttpServletRequest req,UserParams params);
	
	public UserReturn queryBirthDateUsers(UserParams params);
	
	public UserReturn queryInvestUsers(UserParams params);
	
	public UserReturn sensMessageToUser(UserParams params);
}
