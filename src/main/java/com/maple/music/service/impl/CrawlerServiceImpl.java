package com.maple.music.service.impl;

import com.maple.music.dao.CrawlerDao;
import com.maple.music.entity.CategoriesConfig;
import com.maple.music.entity.Playlists;
import com.maple.music.service.CrawlerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

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
	public boolean isPlaylistExists(long id) {
		return crawlerDao.isPlaylistExists(id);
	}

	@Override
	public boolean isUserExists(long id) {
		return crawlerDao.isUserExists(id);
	}
}
