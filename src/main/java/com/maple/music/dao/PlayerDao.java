package com.maple.music.dao;

import com.maple.music.entity.PlayerVo;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * @author LiangDong
 * @Date 2020/3/27
 */
public interface PlayerDao {

	List<Map<String,Object>> getSongsByPlaylistId(BigInteger id);

	List<Map<String, Object>> getSongsBySongId(BigInteger id);

	String getLrcBySongId(BigInteger id);

	List<Map<String, Object>> getSongsBySingerId(BigInteger id);

	List<Map<String, Object>> getSongsByRank(String cat);

    List<Map<String, Object>> getSongsByAlbumId(BigInteger id);
}
