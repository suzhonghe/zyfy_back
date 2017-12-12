package com.zhongyang.java.zyfyback.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.UserDao;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyback.pojo.Invest;
import com.zhongyang.java.zyfyback.pojo.User;
import com.zhongyang.java.zyfyback.returndata.UserDetail;
import com.zhongyang.java.zyfyback.service.UserService;
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;

	@Override
	public User queryUserByParams(User user) {
		return userDao.selectUserByParams(user);
	}

	@Override
	public List<User> queryUsersByInvestParams(Invest invest) {
		return userDao.selectUsersByInvestParams(invest);
	}

	@Override
	public int modifyUserByParams(User user) {
		return userDao.updateUserByParams(user);
	}

	@Override
	public List<User> queryUserByPage(Page<User> page) {
		return userDao.selectUserByPage(page);
	}

	@Override
	public UserDetail queryUserDetailByParams(User user) {
		return userDao.selectUserDetailByParams(user);
	}

	@Override
	public List<User> getUserMobiles(Map<String, Object> map) {
		return userDao.getUserMobiles(map);
	}

	@Override
	public List<User> queryUserBirthdayByPage(Page<User> page) {
		return userDao.queryUserBirthdayByPage(page);
	}

	@Override
	public List<User> queryInvestUsers(Page<User> page) {
		return userDao.selectInvestUsers(page);
	}

}
