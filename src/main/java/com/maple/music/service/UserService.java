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

	User getUserByName(String username);

	Integer keepAlive();

	boolean checkNickName(String nickname);
}
