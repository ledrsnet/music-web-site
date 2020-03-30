package com.maple.music.service;

import com.maple.music.entity.Album;

import java.math.BigInteger;
import java.util.List;

/**
 * @author LiangDong
 * @Date 2020/3/23
 */
public interface AlbumService {
	/**
	 * 从歌曲表中查出所有的专辑ID
	 * @return
	 */
	List<BigInteger> getAlbumIdsFromSongs();

	/**
	 * 判断专辑表中是否有该专辑
	 * @param id
	 * @return
	 */
	boolean isAlbumExist(BigInteger id);

	/**
	 * 插入专辑数据
	 * @param album
	 */
	void insertAlbum(Album album);
}
