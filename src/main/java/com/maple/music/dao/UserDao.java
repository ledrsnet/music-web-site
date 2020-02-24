package com.maple.music.dao;

import com.maple.music.entity.User;

/**
 * @author LiangDong
 * @Date 2019/11/20
 */
public interface UserDao {
	/**
	 * 查询用户
	 * @param uid 用户id
	 * @return User对象
	 */
	User getUser(Long uid);

	/**
	 * 保存用户
	 * @param user 用户对象
	 */
	void saveUser(User user);

	User getUserByName(String username);

	Integer keepAlive();
}
