package com.maple.music.service;

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
}
