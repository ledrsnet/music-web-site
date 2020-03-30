package com.maple.music.dao;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.maple.music.service.CrawlerService;
import com.maple.music.service.SingerService;
import com.maple.music.util.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * @author LiangDong
 * @Date 2020/3/20
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring/applicationContext.xml")
public class CommonDaoTest {

	@Resource
	private CrawlerService crawlerService;

	@Resource
	private FastFileStorageClient fastFileStorageClient;

	@Resource
	private SingerService singerService;


	@Test
	public void testGetSingerTypeIds(){
		List<Integer> allSingerCatIds = crawlerService.getAllSingerCatIds();
		System.out.println(allSingerCatIds);
	}


	@Test
	public void testSplit(){
		String str = "6452";
		String[] split = str.split(",");
		System.out.println(split.length);
		for (int i = 0; i < split.length; i++) {
			System.out.println(split[i]);
		}
	}

	@Test
	public void testIsSingerExists(){
		boolean singerExists = singerService.isSingerExists(new BigInteger("887066"));
		System.out.println(singerExists);
		System.out.println(Long.valueOf("887066"));
	}

	@Test
	@Rollback(false)
	@Transactional
	public void testUpdateSingerName(){
		crawlerService.updateSingerName();
	}

	@Test
	@Rollback(false)
	@Transactional
	public void testUpdateTagsName(){
		crawlerService.updateTagsName();
	}

	@Test
	@Rollback(false)
	public void testUpdate(){
		crawlerService.updateSingerNameByid(BigInteger.valueOf(2637),"曾一鸣");
	}
}
