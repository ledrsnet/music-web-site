package com.maple.music.service;

import com.maple.music.util.PinYinUtil;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author LiangDong
 * @Date 2020/3/17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring/applicationContext.xml")
public class PlaylistsServiceImplTest {

	@Resource
	private PlaylistsService playlistsService;

	@Test
	public void testGetCategoriesInfo(){
		Map<String, Object> categoriesInfo = playlistsService.getCategoriesInfo();
		System.out.println(categoriesInfo);
	}

	@Test
	public void testPinyin(){
		System.out.println(PinYinUtil.getFirstSpell("华语")); // hy
		System.out.println(PinYinUtil.getFullSpell("华语")); // huayu
		System.out.println(PinYinUtil.getPingYin("华语")); // huayu
	}

	@Test
	public void testGetPlaylists(){
		//List<Map<String, Object>> playlists = playlistsService.getPlaylists("华语", "new");
		List<Map<String, Object>> playlists = playlistsService.getPlaylists();
		System.out.println(playlists);
	}
}
