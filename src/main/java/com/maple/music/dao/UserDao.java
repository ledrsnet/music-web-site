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

	/**
	 * 根据用户名查找用户
	 * @param username
	 * @return
	 */
	User getUserByName(String username);

	/**
	 * quartz保活数据库连接用
	 * @return
	 */
	Integer keepAlive();

	/**
	 * 检查昵称是否存在
	 * @param nickname
	 * @return
	 */
	boolean checkNickName(String nickname);
}
