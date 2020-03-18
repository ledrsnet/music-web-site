package com.maple.music.service;

import com.maple.music.entity.CategoriesConfig;
import com.maple.music.entity.Playlists;

/**
 * @author LiangDong
 * @Date 2020/3/2
 */
public interface CrawlerService {
	/**
	 * 插入分类数据
	 * @param categoriesConfig
	 */
	void insertCategory(CategoriesConfig categoriesConfig);

	/**
	 * 根据分类名查分了ID
	 * @param tag
	 * @return
	 */
	Integer getCategoryId(String tag);

	/**
	 * 插入歌单数据
	 * @param lists
	 */
	void insertPlaylists(Playlists lists);

	/**
	 * 判断歌单是否存在
	 * @param id
	 * @return
	 */
	boolean isPlaylistExists(long id);

	/**
	 * 判断用户是否存在
	 * @param id
	 * @return
	 */
	boolean isUserExists(long id);
}
