package com.maple.music.service;

import com.maple.music.entity.PlayerVo;

import java.math.BigInteger;
import java.util.List;

/**
 * @author LiangDong
 * @Date 2020/3/27
 */
public interface PlayerService {
	/**
	 * 查询指定歌单的歌曲信息，播放器使用
	 * @param id
	 * @return
	 */
	List<PlayerVo> getSongsByPlaylistId(BigInteger id);

	/**
	 * 根据指定歌曲id查询
	 * @param id
	 * @return
	 */
	List<PlayerVo> getSongsBySongId(BigInteger id);

	/**
	 * 获取指定歌曲信息
	 * @param id
	 * @return
	 */
	String getLrcBySongId(BigInteger id);

	/**
	 * 获取歌手的热歌
	 * @param id
	 * @return
	 */
	List<PlayerVo> getSongsBySingerId(BigInteger id);

	/**
	 * 获取排行榜音乐
	 * @param cat
	 * @return
	 */
	List<PlayerVo> getSongsByRank(String cat);
}
