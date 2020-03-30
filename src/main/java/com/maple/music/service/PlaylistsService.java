package com.maple.music.service;

import com.maple.music.entity.PlayerVo;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * @author LiangDong
 * @Date 2020/3/14
 */
public interface PlaylistsService {
	/**
	 * 查询分类信息
	 * @return
	 */
	Map<String,Object> getCategoriesInfo();

	/**
	 * 根据分类名和order查询歌单
	 * @param cat
	 * @param order
	 * @return
	 */
	List<Map<String,Object>> getPlaylists(String cat, String order);

	/**
	 * 默认查询歌单
	 * @return
	 */
	List<Map<String,Object>> getPlaylists();

	/**
	 * 获取歌单详情
	 * @param id
	 * @return
	 */
	Map<String, Object> getPlaylistDetail(BigInteger id);

	/**
	 * 获取歌单关联的所有歌曲信息
	 * @param id
	 * @return
	 */
	Map<String, Object> getSongsByIds(String ids);

	/**
	 * 获取推荐歌单
	 * @return
	 */
	Map<String, Object> getReCommendPlaylist();
}
