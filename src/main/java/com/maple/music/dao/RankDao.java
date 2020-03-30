package com.maple.music.dao;


import com.maple.music.entity.RankNew;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * @author LiangDong
 * @Date 2020/3/29
 */
public interface RankDao {

	List<RankNew> getRankInfo(String rankType);

	List<Map<String, Object>> getSingerRankInfo(String ids);

	List<Map<String, Object>> getSongsByType(String rank_type);
}
