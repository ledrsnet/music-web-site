package com.maple.music.service.impl;

import com.maple.music.dao.AlbumDao;
import com.maple.music.entity.Album;
import com.maple.music.service.AlbumService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

/**
 * @author LiangDong
 * @Date 2020/3/23
 */
@Service("albumService")
@Transactional(rollbackFor={Exception.class, RuntimeException.class})
public class AlbumServiceImpl implements AlbumService {

	@Resource
	private AlbumDao albumDao;

	@Override
	public List<BigInteger> getAlbumIdsFromSongs() {
		return albumDao.getAlbumIdsFromSongs();
	}

	@Override
	public boolean isAlbumExist(BigInteger id) {
		return albumDao.isAlbumExist(id);
	}

	@Override
	public void insertAlbum(Album album) {
		albumDao.insertAlbum(album);
	}
}
