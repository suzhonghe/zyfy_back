package com.zhongyang.java.dao;

import java.util.List;
import java.util.Map;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyback.pojo.Invest;
import com.zhongyang.java.zyfyback.pojo.User;
import com.zhongyang.java.zyfyback.returndata.UserDetail;

public interface UserDao {

	User selectUserByParams(User user);
	
	List<User>selectUsersByInvestParams(Invest invest);
	
	int updateUserByParams(User user);
	
	List<User>selectUserByPage(Page<User>page);
	
	UserDetail selectUserDetailByParams(User user);
	
	List<User> getUserMobiles(Map<String, Object> map);
	
	List<User> queryUserBirthdayByPage(Page<User> page);
	
	List<User> selectInvestUsers(Page<User> page);
}
