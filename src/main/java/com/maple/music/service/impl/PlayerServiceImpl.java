package com.maple.music.service.impl;

import com.maple.music.dao.PlayerDao;
import com.maple.music.entity.PlayerVo;
import com.maple.music.service.PlayerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author LiangDong
 * @Date 2020/3/27
 */
@Service("playerService")
@Transactional(rollbackFor={Exception.class, RuntimeException.class})
public class PlayerServiceImpl implements PlayerService {

	private static final String preFixPath ="http://121.36.244.33:8080/";
	private static final String lrcPreFixPath ="playerAction_lrc?songId=";
	@Resource
	private PlayerDao playerDao;

	@Override
	public String getLrcBySongId(BigInteger id) {
		return playerDao.getLrcBySongId(id);
	}

	@Override
	public List<PlayerVo> getSongsByPlaylistId(BigInteger id) {
		List<Map<String, Object>> list = playerDao.getSongsByPlaylistId(id);
		return transToPlayerVo(list);
	}

	@Override
	public List<PlayerVo> getSongsBySongId(BigInteger id) {
		List<Map<String,Object>> list = playerDao.getSongsBySongId(id);
		return transToPlayerVo(list);
	}

	@Override
	public List<PlayerVo> getSongsBySingerId(BigInteger id) {
		List<Map<String, Object>> list = playerDao.getSongsBySingerId(id);
		return transToPlayerVo(list);
	}

	@Override
	public List<PlayerVo> getSongsByRank(String cat) {
		List<Map<String, Object>> list = playerDao.getSongsByRank(cat);;
		return transToPlayerVo(list);
	}

	@Override
	public List<PlayerVo> getSongsByAlbumId(BigInteger id) {
		List<Map<String, Object>> list = playerDao.getSongsByAlbumId(id);;
		return transToPlayerVo(list);
	}

	private List<PlayerVo> transToPlayerVo(List<Map<String,Object>> list){
		List<PlayerVo> lists = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			PlayerVo playerVo = new PlayerVo();
			playerVo.setTitle((String) list.get(i).get("name"));
			playerVo.setAuthor((String) list.get(i).get("avator_names"));
			playerVo.setUrl((String) list.get(i).get("song_url"));
			playerVo.setPic(preFixPath+list.get(i).get("pic_url"));
			playerVo.setLrc(lrcPreFixPath+list.get(i).get("song_id"));
			lists.add(playerVo);
		}
		return lists;
	}
}
