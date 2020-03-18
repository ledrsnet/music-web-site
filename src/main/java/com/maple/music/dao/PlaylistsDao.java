package com.maple.music.dao;

import com.maple.music.entity.CategoriesBigConfig;
import com.maple.music.entity.CategoriesConfig;
import com.maple.music.entity.Playlists;

import java.util.List;
import java.util.Map;

/**
 * @author LiangDong
 * @Date 2020/3/14
 */
public interface PlaylistsDao {
	/**
	 * 查询最大的分类
	 * @return
	 */
	List<CategoriesBigConfig> getBigCategories();

	/**
	 * 查询细分类
	 * @return
	 */
	List<CategoriesConfig> getCategoriesConfig();

	/**
	 * 根据分类及order查询歌单数据
	 * @param cat
	 * @param order
	 * @return
	 */
	List<Map<String,Object>> getPlaylists(String cat, String order);

	/**
	 * 查询歌单数据，默认update_time desc返回
	 * @return
	 */
	List<Map<String,Object>> getPlaylists();

}
