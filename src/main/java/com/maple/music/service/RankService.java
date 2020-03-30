package com.maple.music.service;

import java.util.Map;

/**
 * @author LiangDong
 * @Date 2020/3/29
 */
public interface RankService {
	Map<String, Object> getRankInfo(String rankType);

	Map<String, Object> getSingerRankInfo(String ids);

	Map<String, Object> getSongsByType(String rank_type);
}
