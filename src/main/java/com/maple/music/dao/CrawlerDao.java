package com.maple.music.dao;

import com.maple.music.entity.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * @author LiangDong
 * @Date 2020/3/2
 */
public interface CrawlerDao {

	/**
	 * 插入分类数据
	 * @param categoriesConfig
	 */
	void insertCategory(CategoriesConfig categoriesConfig);

	/**
	 * 根据分类名得到分类ID
	 * @param name
	 * @return
	 */
	Integer getCategoryId(String name);

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
	boolean isPlaylistExists(BigInteger id);

	/**
	 * 判断用户是否存在
	 * @param id
	 * @return
	 */
	boolean isUserExists(long id);

	/**
	 * 获取所有歌单ID
	 * @return
	 */
	List<BigInteger> getAllPlaylistId();

	/**
	 * 爬取并批量插入歌曲
	 * @param songs
	 */
	void insertSongs(Songs songs);

	/**
	 * 判断歌曲是否存在
	 * @param id
	 * @return
	 */
	boolean isSongsExists(BigInteger id);

	/**
	 * 获取所有歌手分类ID
	 * @return
	 */
	List<Integer> getAllSingerCatIds();


	/**
	 * 插入歌单与歌的映射表
	 * @param playlistSong
	 */
	void insertPlaylistSong(PlaylistSong playlistSong);

	/**
	 * 判断歌单和歌的映射关系是否存在
	 * @param playlistId
	 * @param songId
	 * @return
	 */
	boolean isPlaylistSongExists(BigInteger playlistId, BigInteger songId);

	List<Map<String,Object>> getSingerIdFromSong();

	List<String> getNameBySingerId(String avator_id);

	void updateNameById(BigInteger id, String name);

	List<Map<String, Object>> getTagsIdfromPlaylist();

	String getNameByCategoryId(String tags);

	void updateNameByIdFromPlaylist(BigInteger id, String names);
}
