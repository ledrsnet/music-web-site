package com.maple.music.service.impl;

import com.google.gson.Gson;
import com.maple.music.dao.SearchDao;
import com.maple.music.entity.*;
import com.maple.music.service.SearchService;
import com.maple.music.util.HttpClientUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LiangDong
 * @Date 2020/3/30
 */
@Service("searchService")
@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
public class SearchServiceImpl implements SearchService {

	@Resource
	private SearchDao searchDao;

	@Override
	public Map<String, Object> searchSingleSongByKey(String keywords) {
		Map<String, Object> map = new HashMap<>();
		// ik分词
		String resJson = HttpClientUtils.doGetHtml("http://121.36.244.33:9200/_analyze?analyzer=ik_max_word&text=" + keywords);
		if (StringUtils.isNotBlank(resJson)) {
			Gson gson = new Gson();
			TokensDto tokensDto = gson.fromJson(resJson, TokensDto.class);
			List<TokenDto> tokens = tokensDto.getTokens();
			List<String> keys = new ArrayList<>();
			keys.add(keywords);
			for (int i = 0; i < tokens.size(); i++) {
				if (tokens.get(i).getType().equals("CN_WORD")) {
					if (!keys.contains(tokens.get(i).getToken())) {
						keys.add(tokens.get(i).getToken());
					}
				} else if (tokens.get(i).getType().equals("ENGLISH") && tokens.get(i).getEnd_offset() - tokens.get(i).getStart_offset() >= 2) {
					if (!keys.contains(tokens.get(i).getToken())) {
						keys.add(tokens.get(i).getToken());
					}
				}
			}
			// 分词 END
			List<Map<String, Object>> songs = searchDao.searchSingleSongByKey(keys);
			List<SongsDto> songsDtos = new ArrayList<>();
			if (songs != null && songs.size() > 0) {
				for (int i = 0; i < songs.size(); i++) {
					SongsDto songsDto = new SongsDto();
					songsDto.setAlbum(new Album((BigInteger) songs.get(i).get("albumId"), (String) songs.get(i).get("albumName")));
					songsDto.setId((BigInteger) songs.get(i).get("songId"));
					songsDto.setName((String) songs.get(i).get("songName"));
					songsDto.setDateTime((Integer) songs.get(i).get("dt"));
					String alia = (String) songs.get(i).get("alia");
					String[] split = new String[0];
					if (StringUtils.isNotBlank(alia)) {
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
						if (i1 < singerNames.length) {
							singer.setName(singerNames[i1]);
						} else {
							singer.setName("");
						}
						singerList.add(singer);
					}
					songsDto.setSingers(singerList);
					songsDtos.add(songsDto);
				}
				map.put("songs", songsDtos);
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> searchAlbumByKey(String keywords) {
		Map<String, Object> map = new HashMap<>();
		// ik分词
		String resJson = HttpClientUtils.doGetHtml("http://121.36.244.33:9200/_analyze?analyzer=ik_max_word&text=" + keywords);
		if (StringUtils.isNotBlank(resJson)) {
			Gson gson = new Gson();
			TokensDto tokensDto = gson.fromJson(resJson, TokensDto.class);
			List<TokenDto> tokens = tokensDto.getTokens();
			List<String> keys = new ArrayList<>();
			keys.add(keywords);
			for (int i = 0; i < tokens.size(); i++) {
				if (tokens.get(i).getType().equals("CN_WORD")) {
					if (!keys.contains(tokens.get(i).getToken())) {
						keys.add(tokens.get(i).getToken());
					}
				} else if (tokens.get(i).getType().equals("ENGLISH") && tokens.get(i).getEnd_offset() - tokens.get(i).getStart_offset() >= 2) {
					if (!keys.contains(tokens.get(i).getToken())) {
						keys.add(tokens.get(i).getToken());
					}
				}
			}
			// 分词 END
			List<Map<String, Object>> albums = searchDao.searchAlbumByKey(keys);
			JSONArray jsonArray = new JSONArray();
			if (albums != null && albums.size() > 0) {
				for (int i = 0; i < albums.size(); i++) {
					JSONObject jo = new JSONObject();
					jo.put("id", albums.get(i).get("id"));
					jo.put("name", albums.get(i).get("name"));
					jo.put("blurPicUrl", albums.get(i).get("picUrl"));
					JSONObject artist = new JSONObject();
					artist.put("singerId", albums.get(i).get("singerId"));
					artist.put("singerName", albums.get(i).get("singerName"));
					jo.put("artist", artist);
					jsonArray.add(i, jo);
				}
				map.put("albums", jsonArray);
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> searchSingerByKey(String keywords) {
		Map<String, Object> map = new HashMap<>();
		// ik分词
		String resJson = HttpClientUtils.doGetHtml("http://121.36.244.33:9200/_analyze?analyzer=ik_max_word&text=" + keywords);
		if (StringUtils.isNotBlank(resJson)) {
			Gson gson = new Gson();
			TokensDto tokensDto = gson.fromJson(resJson, TokensDto.class);
			List<TokenDto> tokens = tokensDto.getTokens();
			List<String> keys = new ArrayList<>();
			keys.add(keywords);
			// 先填匹配字数多的参数
			for (int i = 0; i < tokens.size(); i++) {
				if (!keys.contains(tokens.get(i).getToken())) {
					if(tokens.get(i).getEnd_offset() - tokens.get(i).getStart_offset() >= 2){
						keys.add(tokens.get(i).getToken());
					}
				}
			}
			// 在填充字数少的参数
			for (int i = 0; i < tokens.size(); i++) {
				if (!keys.contains(tokens.get(i).getToken())) {
					if(tokens.get(i).getEnd_offset() - tokens.get(i).getStart_offset() < 2){
						keys.add(tokens.get(i).getToken());
					}
				}
			}
			// 分词 END
			List<Map<String, Object>> singers = searchDao.searchSingerByKey(keys);
			JSONArray jsonArray = new JSONArray();
			if (singers != null && singers.size() > 0) {
				for (int i = 0; i < singers.size(); i++) {
					JSONObject jo = new JSONObject();
					jo.put("id",singers.get(i).get("id"));
					jo.put("name",singers.get(i).get("name"));
					jo.put("picUrl",singers.get(i).get("pic_url"));
					jsonArray.add(jo);
				}
				map.put("singers", jsonArray);
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> searchPlaylistByKey(String keywords) {
		Map<String, Object> map = new HashMap<>();
		// ik分词
		String resJson = HttpClientUtils.doGetHtml("http://121.36.244.33:9200/_analyze?analyzer=ik_max_word&text=" + keywords);
		if (StringUtils.isNotBlank(resJson)) {
			Gson gson = new Gson();
			TokensDto tokensDto = gson.fromJson(resJson, TokensDto.class);
			List<TokenDto> tokens = tokensDto.getTokens();
			List<String> keys = new ArrayList<>();
			keys.add(keywords);
			// 先填匹配字数多的参数
			for (int i = 0; i < tokens.size(); i++) {
				if (!keys.contains(tokens.get(i).getToken())) {
					if(tokens.get(i).getEnd_offset() - tokens.get(i).getStart_offset() >= 2){
						keys.add(tokens.get(i).getToken());
					}
				}
			}
			// 在填充字数少的参数
			for (int i = 0; i < tokens.size(); i++) {
				if (!keys.contains(tokens.get(i).getToken())) {
					if(tokens.get(i).getEnd_offset() - tokens.get(i).getStart_offset() < 2){
						keys.add(tokens.get(i).getToken());
					}
				}
			}
			// 分词 END
			List<Map<String, Object>> playlists = searchDao.searchPlaylistByKey(keys);
			JSONArray jsonArray = new JSONArray();
			if (playlists != null && playlists.size() > 0) {
				for (int i = 0; i < playlists.size(); i++) {
					JSONObject jo = new JSONObject();
					jo.put("id",playlists.get(i).get("id"));
					jo.put("name",playlists.get(i).get("name"));
					jo.put("nickname",playlists.get(i).get("nickname"));
					jo.put("createTime",playlists.get(i).get("create_time"));
					jo.put("playCount",playlists.get(i).get("play_count"));
					jo.put("coverImgUrl",playlists.get(i).get("cover_img_url"));
					jsonArray.add(jo);
				}
				map.put("playlists", jsonArray);
			}
		}
		return map;
	}

}
