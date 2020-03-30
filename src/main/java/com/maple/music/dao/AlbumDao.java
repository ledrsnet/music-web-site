package com.maple.music.dao;

import com.maple.music.entity.Album;

import java.math.BigInteger;
import java.util.List;

/**
 * @author LiangDong
 * @Date 2020/3/23
 */
public interface AlbumDao {
	/**
	 * 从歌曲表中查出所有的专辑ID
	 * @return
	 */
	List<BigInteger> getAlbumIdsFromSongs();

	/**
	 * 判断该专辑是否存在
	 * @param id
	 * @return
	 */
	boolean isAlbumExist(BigInteger id);

	/**
	 * 插入专辑到数据库
	 * @param album
	 */
	void insertAlbum(Album album);

	/**
	 * 根据ID查找专辑
	 * @param albumId
	 * @return
	 */
	Album getAlbumById(BigInteger albumId);
}
