package com.maple.music.service;

import java.util.Map;

/**
 * @author LiangDong
 * @Date 2020/3/30
 */
public interface SearchService {

	Map<String, Object> searchSingleSongByKey(String keywords);

	Map<String, Object> searchAlbumByKey(String keywords);

	Map<String, Object> searchSingerByKey(String keywords);

	Map<String, Object> searchPlaylistByKey(String keywords);
}
