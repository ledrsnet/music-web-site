package com.maple.music.service;


import com.maple.music.entity.User;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

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

	/**
	 * 查询用户收藏的歌单
	 * @param userId
	 * @return
	 */
	List<Map<String, Object>> getUserFavorite(long userId);

	/**
	 * 取消用户收藏的指定歌单
	 * @param id
	 */
	int deSubscrib(Long userId,BigInteger id);
}
