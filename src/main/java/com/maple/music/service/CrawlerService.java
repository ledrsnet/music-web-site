package com.maple.music.service;

import com.maple.music.entity.*;

import java.math.BigInteger;
import java.util.List;

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
	boolean isPlaylistExists(BigInteger id);

	/**
	 * 判断用户是否存在
	 * @param id
	 * @return
	 */
	boolean isUserExists(long id);

	/**
	 * 获取所有歌单的ID
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
	 * 获取所有歌手分类的ID，1001，1002，2001，2002====
	 * @return
	 */
	List<Integer> getAllSingerCatIds();


	/**
	 * 插入歌单与歌映射表
	 * @param playlistSong
	 */
	void insertPlaylistSong(PlaylistSong playlistSong);

	/**
	 * 判断歌单和歌映射关系是否存在
	 * @param playlistId
	 * @param songId
	 * @return
	 */
	boolean isPlaylistSongExists(BigInteger playlistId, BigInteger songId);

	/**
	 * 表设计考虑不周，数据库新增字段avator_names，更新该字段内容，以便优化sql，提高效率
	 */
	void updateSingerName();

	/**
	 * 表设计考虑不周，数据库新增字段tag_text，更新该字段内容，以便优化sql，提高效率
	 */
	void updateTagsName();
	/**
	 * 测试
	 * @param id
	 * @param name
	 */
	void updateSingerNameByid(BigInteger id,String name);
}
