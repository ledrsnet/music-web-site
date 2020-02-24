package com.maple.music.service.impl;

import com.maple.music.dao.UserDao;
import com.maple.music.entity.User;
import com.maple.music.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author LiangDong
 * @Date 2019/11/20
 */
@Service("userService")
@Transactional(rollbackFor={Exception.class, RuntimeException.class})
public class UserServiceImpl implements UserService {

	@Resource(name = "userDao")
	private UserDao userDao;

	public User getUser(Long uid) {
		return userDao.getUser(uid);
	}

	public void saveUser(User user) {
		userDao.saveUser(user);
	}

	@Override
	public User getUserByName(String username) {
		return userDao.getUserByName(username);
	}

	@Override
	public Integer keepAlive() {
		return userDao.keepAlive();
	}
}
