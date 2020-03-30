package com.maple.music.service.impl;

import com.maple.music.dao.CrawlerDao;
import com.maple.music.entity.*;
import com.maple.music.service.CrawlerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * @author LiangDong
 * @Date 2020/3/2
 */
@Service("crawlerService")
@Transactional(rollbackFor={Exception.class, RuntimeException.class})
public class CrawlerServiceImpl implements CrawlerService {
	@Resource
	private CrawlerDao crawlerDao;
	@Transactional
	@Override
	public void insertCategory(CategoriesConfig categoriesConfig) {
		crawlerDao.insertCategory(categoriesConfig);
	}

	@Override
	public Integer getCategoryId(String tag) {
		return crawlerDao.getCategoryId(tag);
	}

	@Override
	public void insertPlaylists(Playlists lists) {
		crawlerDao.insertPlaylists(lists);
	}

	@Override
	public boolean isPlaylistExists(BigInteger id) {
		return crawlerDao.isPlaylistExists(id);
	}

	@Override
	public boolean isUserExists(long id) {
		return crawlerDao.isUserExists(id);
	}

	@Override
	public List<BigInteger> getAllPlaylistId() {
		return crawlerDao.getAllPlaylistId();
	}

	@Override
	public void insertSongs(Songs songs) {
		crawlerDao.insertSongs(songs);
	}

	@Override
	public boolean isSongsExists(BigInteger id) {
		return crawlerDao.isSongsExists(id);
	}

	@Override
	public List<Integer> getAllSingerCatIds() {
		return crawlerDao.getAllSingerCatIds();
	}


	@Override
	public void insertPlaylistSong(PlaylistSong playlistSong) {
		crawlerDao.insertPlaylistSong(playlistSong);
	}

	@Override
	public boolean isPlaylistSongExists(BigInteger playlistId, BigInteger songId) {
		return crawlerDao.isPlaylistSongExists(playlistId,songId);
	}

	@Override
	public void updateSingerName() {
		List<Map<String, Object>> list = crawlerDao.getSingerIdFromSong();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			List<String> names = crawlerDao.getNameBySingerId((String) list.get(i).get("avator_id"));
			for (int i1 = 0; i1 < names.size(); i1++) {
				if(i1==names.size()-1){
					sb.append(names.get(i1));
				}else{
					sb.append(names.get(i1)+",");
				}
			}
			crawlerDao.updateNameById((BigInteger) list.get(i).get("id"),sb.toString());
			sb.delete(0,sb.length());
		}
	}

	@Override
	public void updateTagsName() {
		List<Map<String, Object>> list = crawlerDao.getTagsIdfromPlaylist();
		for (int i = 0; i < list.size(); i++) {
			String names = crawlerDao.getNameByCategoryId((String) list.get(i).get("tags"));
			crawlerDao.updateNameByIdFromPlaylist((BigInteger) list.get(i).get("id"),names);
		}
	}

	@Override
	public void updateSingerNameByid(BigInteger id, String name) {
		crawlerDao.updateNameById(id,name);
	}
}
