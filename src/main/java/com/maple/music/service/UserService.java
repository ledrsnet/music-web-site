package com.maple.music.service;


import com.maple.music.entity.User;

/**
 * @author LiangDong
 * @Date 2019/11/20
 */
public interface UserService {

	/**
	 * 获取用户
	 * @param uid
	 * @return
	 */
	User getUser(Long uid);

	/**
	 * 保存用户
	 * @param user
	 */
	void saveUser(User user);

	/**
	 * 根据用户名查找用户
	 * @param username
	 * @return
	 */
	User getUserByName(String username);

	/**
	 * quartz保活数据库连接使用
	 * @return
	 */
	Integer keepAlive();

	/**
	 * 检查昵称是否可用
	 * @param nickname
	 * @return
	 */
	boolean checkNickName(String nickname);
}
