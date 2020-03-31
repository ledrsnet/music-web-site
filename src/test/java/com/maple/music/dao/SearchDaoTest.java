package com.maple.music.dao;

import com.maple.music.service.SearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author LiangDong
 * @Date 2020/3/30
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring/applicationContext.xml")
public class SearchDaoTest {

	@Resource
	private SearchService searchService;

	@Test
	public void testSearch(){
		Map<String, Object> map = searchService.searchSingleSongByKey("海阔天空");
		System.out.println(map.toString());
	}
	@Test
	public void testSearchAlbumByKey(){
		Map<String, Object> map = searchService.searchAlbumByKey("海阔天空");
		System.out.println(map.toString());
	}
}
