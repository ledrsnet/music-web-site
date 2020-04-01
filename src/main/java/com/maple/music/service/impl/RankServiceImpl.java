package com.maple.music.service.impl;

import com.maple.music.dao.RankDao;
import com.maple.music.entity.*;
import com.maple.music.service.RankService;
import org.apache.commons.lang.StringUtils;
import org.hibernate.exception.DataException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author LiangDong
 * @Date 2020/3/29
 */
@Service("rankService")
@Transactional(rollbackFor={Exception.class, RuntimeException.class})
public class RankServiceImpl implements RankService {

	@Resource
	private RankDao rankDao;

	@Override
	public Map<String, Object> getRankInfo(String rankType) {
		List<RankNew> list = rankDao.getRankInfo(rankType);
		Map<String,Object> map = new HashMap<>();
		List<BigInteger> ids = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			ids.add(list.get(i).getSongId());
		}
		if("soar".equals(rankType)){
			map.put("name","飙升榜");
			map.put("coverImgUrl","/assert/img/rank/飙升榜400y400.jpg");
		}else if("new".equals(rankType)){
			map.put("name","新歌榜");
			map.put("coverImgUrl","/assert/img/rank/新歌榜400y400.jpg");
		}else if("hot".equals(rankType)){
			map.put("name","热歌榜");
			map.put("coverImgUrl","/assert/img/rank/热歌榜400y400.jpg");
		}else if("singer".equals(rankType)){
			map.put("name","歌手榜");
			map.put("coverImgUrl","/assert/img/rank/热歌榜400y400.jpg");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(list.get(0).getUpdateTime());
		map.put("updateTime",date);
		map.put("trackIds",ids);
		return map;
	}

	@Override
	public Map<String, Object> getSingerRankInfoByIds(String ids) {
		List<Map<String,Object>> list = rankDao.getSingerRankInfo(ids);
		Map<String,Object> map = new HashMap<>();
		map.put("songs",list);
		return map;
	}

	@Override
	public Map<String, Object> getSingerRankInfoByType(String type) {
		List<Map<String,Object>> list = rankDao.getSingerRankInfoByType(type);
		Map<String,Object> map = new HashMap<>();
		map.put("songs",list);
		return map;
	}

	@Override
	public Map<String, Object> getSongsByType(String rank_type) {
		List<Map<String, Object>> songs = rankDao.getSongsByType(rank_type);
		List<SongsDto> songsDtos = new ArrayList<>();
		Map<String,Object> map = new HashMap<>();
		if(songs!=null&&songs.size()>0){
			for (int i = 0; i < songs.size(); i++) {
				SongsDto songsDto = new SongsDto();
				songsDto.setAlbum(new Album((BigInteger) songs.get(i).get("albumId"),(String)songs.get(i).get("albumName")));
				songsDto.setId((BigInteger) songs.get(i).get("songId"));
				songsDto.setName((String) songs.get(i).get("songName"));
				songsDto.setDateTime((Integer) songs.get(i).get("dt"));
				String alia = (String) songs.get(i).get("alia");
				String[] split = new String[0];
				if(StringUtils.isNotBlank(alia)){
					split = alia.split(",");
				}
				songsDto.setAlia(split);
				String singerId = (String) songs.get(i).get("singerId");
				String singerName = (String) songs.get(i).get("singerName");
				String[] singerIds = singerId.split(",");
				String[] singerNames = singerName.split(",");
				List<Singer> singerList = new ArrayList<>();
				for (int i1 = 0; i1 < singerIds.length; i1++) {
					Singer singer = new Singer();
					singer.setId(new BigInteger(singerIds[i1]));
					if(i1<singerNames.length){
						singer.setName(singerNames[i1]);
					}else{
						singer.setName("");
					}
					singerList.add(singer);
				}
				songsDto.setSingers(singerList);
				songsDtos.add(songsDto);
			}
			map.put("songs",songsDtos);
			map.put("code",200);
		}else{
			map.put("code",500);
		}
		return map;
	}

	@Override
	public Map<String, Object> getGuessRank() {
		Map<String,Object> map = new HashMap<>();
		List<Map<String, Object>> list = rankDao.getGuessRank();
		map.put("list",list);
		return map;
	}

	@Override
	public int addGuessRankInfo(GuessRank guessRank) {
		return rankDao.addGuessRankInfo(guessRank);
	}


	@Override
	public Map<String, Object> getGuessSongsInfo() {
		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> list = rankDao.getGuessSongsInfo();
		if(list!=null&&list.size()>0){
			map.put("list",list);
		}
		return map;
	}

	@Override
	public boolean isInsertGuessRank(BigInteger userId, int obNum) {
		return rankDao.isInsertGuessRank(userId,obNum);
	}
}
