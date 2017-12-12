package com.zhongyang.java.zyfyback.service;

import java.util.List;
import java.util.Map;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyback.pojo.Invest;
import com.zhongyang.java.zyfyback.pojo.User;
import com.zhongyang.java.zyfyback.returndata.UserDetail;

public interface UserService {

	public User queryUserByParams(User user);
	
	public List<User>queryUsersByInvestParams(Invest invest);
	
	public int modifyUserByParams(User user);
	
	public List<User>queryUserByPage(Page<User>page);
	
	public UserDetail queryUserDetailByParams(User user);
	
	public List<User> getUserMobiles(Map<String, Object> map);
	
	public List<User>queryUserBirthdayByPage(Page<User>page);
	
	public List<User> queryInvestUsers(Page<User>page);
}
