package com.maple.music.service;

import com.maple.music.entity.GuessRank;

import java.math.BigInteger;
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

	Map<String, Object> getGuessRank();

	int addGuessRankInfo(GuessRank guessRank);

	Map<String, Object> getGuessSongsInfo();

	boolean isInsertGuessRank(BigInteger userId, int obNum);
}
