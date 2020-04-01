package com.maple.music.dao;

import com.maple.music.entity.*;

import java.math.BigInteger;
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

	/**
	 * 获取歌单信息
	 * @param id
	 * @return
	 */
	Playlists getPlaylistInfo(BigInteger id);

	/**
	 * 获取歌单里所有歌曲的ID
	 * @param id
	 * @return
	 */
	List<BigInteger> getSongIds(BigInteger id);

	/**
	 * 获取歌单里所有歌曲的信息
	 * @param ids
	 * @return
	 */
	List<Map<String,Object>> getSongsByIds(String ids);

	/**
	 * 获取推荐歌单
	 * @return
	 */
	List<Map<String, Object>> getReCommendPlaylist();

	/**
	 * 获取指定歌单的评论
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> getCommentForPlaylist(String id);

	/**
	 * 添加歌单评论
	 * @param txt
	 * @param playlistId
	 * @param userId
	 * @return
	 */
	int addComment(String txt, BigInteger playlistId, Long userId);

	/**
	 * 收藏歌单
	 * @param userFavorite
	 * @return
	 */
	int addFavorite(UserFavorite userFavorite);
}
