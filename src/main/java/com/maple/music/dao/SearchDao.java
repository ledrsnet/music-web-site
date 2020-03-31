package com.maple.music.dao;

import java.util.List;
import java.util.Map;

/**
 * @author LiangDong
 * @Date 2020/3/30
 */
public interface SearchDao {
	List<Map<String, Object>> searchSingleSongByKey(List<String> keywords);

	List<Map<String, Object>> searchAlbumByKey(List<String> keywords);

	List<Map<String, Object>> searchPlaylistByKey(List<String> keys);

	List<Map<String, Object>> searchSingerByKey(List<String> keys);
}
