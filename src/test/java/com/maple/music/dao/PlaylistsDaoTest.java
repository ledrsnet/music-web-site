package com.maple.music.dao;

import com.google.gson.Gson;
import com.maple.music.util.ResultUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author LiangDong
 * @Date 2020/3/16
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring/applicationContext.xml")
@Transactional(rollbackFor={Exception.class, RuntimeException.class})
public class PlaylistsDaoTest {
	@Resource
	private PlaylistsDao playlistsDao;

	@Test
	public void testGetPlaylists(){
		//List<Map<String, Object>> playlists = playlistsDao.getPlaylists("华语", "hot");
		List<Map<String, Object>> playlists = playlistsDao.getPlaylists();
		Gson gson = new Gson();
		String result = gson.toJson(playlists);
		System.out.println(result);
	}
}
