package com.maple.music.dao;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.maple.music.entity.TokenDto;
import com.maple.music.entity.TokensDto;
import com.maple.music.service.CrawlerService;
import com.maple.music.service.SingerService;
import com.maple.music.util.FileUtils;
import com.maple.music.util.HttpClientUtils;
import com.sun.tools.javac.parser.Tokens;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
	@Test
	public void testIkAnalyze(){
		String resJson = HttpClientUtils.doGetHtml("http://121.36.244.33:9200/_analyze?analyzer=ik_smart&text=" + "最熟悉的陌生人");
		Gson gson = new Gson();
		TokensDto tokensDto = gson.fromJson(resJson, TokensDto.class);
		for (int i = 0; i < tokensDto.getTokens().size(); i++) {
			System.out.println(tokensDto.getTokens().get(i).toString());
		}
	}
}
