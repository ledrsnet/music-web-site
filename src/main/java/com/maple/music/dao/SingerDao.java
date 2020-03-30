package com.maple.music.dao;

import com.maple.music.entity.Singer;

import java.math.BigInteger;
import java.util.List;

/**
 * @author LiangDong
 * @Date 2020/3/23
 */
public interface SingerDao {
	void insertSinger(Singer singer);
	boolean isSingerExists(BigInteger id);

	/**
	 * 根据ID查询歌手数据
	 * @param avatorId
	 * @return
	 */
	List<Singer> getSingerByIds(String avatorId);

	/**
	 * 从歌曲表中查出所有歌手id
	 */
	List<String> getSingerIdsFromSongs();
}
