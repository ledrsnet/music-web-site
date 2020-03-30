package com.maple.music.service;

import com.maple.music.entity.Singer;

import java.math.BigInteger;
import java.util.List;

/**
 * @author LiangDong
 * @Date 2020/3/23
 */
public interface SingerService {
	/**
	 * 插入歌手数据
	 * @param singer
	 */
	void insertSinger(Singer singer);

	/**
	 * 判断歌手是否存在
	 * @param id
	 * @return
	 */
	boolean isSingerExists(BigInteger id);

	/**
	 * 从歌曲表中查找所有歌手id
	 */
	List<String> getSingerIdsFromSong();
}
