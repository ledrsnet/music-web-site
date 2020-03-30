package com.maple.music.service;

import java.util.Map;

/**
 * @author LiangDong
 * @Date 2020/3/29
 */
public interface RankService {
	Map<String, Object> getRankInfo(String rankType);

	Map<String, Object> getSingerRankInfoByIds(String ids);

	Map<String, Object> getSingerRankInfoByType(String type);

	Map<String, Object> getSongsByType(String rank_type);
}
